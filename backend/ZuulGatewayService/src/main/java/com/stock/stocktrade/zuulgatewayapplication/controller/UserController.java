package com.stock.stocktrade.zuulgatewayapplication.controller;

import com.stock.stocktrade.zuulgatewayapplication.config.JwtTokenUtil;
import com.stock.stocktrade.zuulgatewayapplication.dto.SigninRequest;
import com.stock.stocktrade.zuulgatewayapplication.dto.SigninResponse;
import com.stock.stocktrade.zuulgatewayapplication.dto.UserDto;
import com.stock.stocktrade.zuulgatewayapplication.exception.InvalidTokenException;
import com.stock.stocktrade.zuulgatewayapplication.exception.UserAlreadyExistsException;
import com.stock.stocktrade.zuulgatewayapplication.exception.UserNotFoundException;
import com.stock.stocktrade.zuulgatewayapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class UserController 
{
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;
	

	
	@PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> login(@RequestBody SigninRequest signinRequest)
	{
		final UserDetails userDetails = userService.loadUserByUsername(signinRequest.getUsername());
		if(userDetails == null || !passwordEncoder.matches(signinRequest.getPassword(), userDetails.getPassword())) {
			throw new UsernameNotFoundException("User not found... Invalid Credentials...!!");
		}
		final String token = jwtTokenUtil.generateToken(userDetails);
		final long expiresIn = (jwtTokenUtil.getExpirationDateFromToken(token).getTime()-(new Date()).getTime());
		final boolean isAdmin = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		return ResponseEntity.ok(new SigninResponse(userDetails.getUsername(), token, isAdmin, expiresIn));
	}
	
	@PostMapping(path = "/signup", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> signup(@RequestBody UserDto userDto, HttpServletRequest request)
			throws UserAlreadyExistsException
	{
		userDto = userService.signup(userDto);
		if(userDto == null) {
			throw new UserAlreadyExistsException("User already exists with given username or email!!");
		}
		
		String appUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
//		Mail mail = new Mail();
//		mail.setMailFrom("adikshettri1623@gmail.com");
//		mail.setMailTo(userDto.getEmail());
//		mail.setMailSubject("Email Confirmation");
//		mail.setMailContent("To confirm you email-address, please click the link below:\n"
//				+ appUrl + "/auth/confirm?token=" + userDto.getConfirmationToken());
//		mailService.sendEmail(mail);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(userDto);
	}
	
	@GetMapping(path = "/confirm")
	public ResponseEntity<?> activate(@RequestParam String token)
			throws InvalidTokenException
	{
		UserDto userDto = userService.activate(token);
		if(userDto == null) {
			throw new InvalidTokenException("Invalid token : " + token);
		}
		return ResponseEntity.ok("Email Successfully activated");
	}
	
	@PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> update(@RequestBody UserDto userDto)throws UserNotFoundException
	{
		UserDto updatedUserDto = userService.update(userDto);
		if(updatedUserDto == null) {
			throw new UserNotFoundException("User not found with id : " + userDto.getId());
		}
		return ResponseEntity.ok(updatedUserDto);
	}
}
