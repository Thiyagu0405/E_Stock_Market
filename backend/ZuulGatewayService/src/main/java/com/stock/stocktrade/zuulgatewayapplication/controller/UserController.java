package com.stock.stocktrade.zuulgatewayapplication.controller;

import com.stock.stocktrade.zuulgatewayapplication.config.JwtTokenUtil;
import com.stock.stocktrade.zuulgatewayapplication.dto.SigninRequest;
import com.stock.stocktrade.zuulgatewayapplication.dto.SigninResponse;
import com.stock.stocktrade.zuulgatewayapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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



}
