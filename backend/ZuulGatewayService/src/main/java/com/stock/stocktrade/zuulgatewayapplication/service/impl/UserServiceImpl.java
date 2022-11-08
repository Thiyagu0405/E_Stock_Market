package com.stock.stocktrade.zuulgatewayapplication.service.impl;

import com.stock.stocktrade.zuulgatewayapplication.dao.UserRepository;
import com.stock.stocktrade.zuulgatewayapplication.dto.UserDto;
import com.stock.stocktrade.zuulgatewayapplication.mapper.UserMapper;
import com.stock.stocktrade.zuulgatewayapplication.model.User;
import com.stock.stocktrade.zuulgatewayapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user = userRepository.findByUsername(username);
		if(user == null || !user.isConfirmed())
			return null;
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
	}


}
