package com.stock.stocktrade.zuulgatewayapplication.service;

import com.stock.stocktrade.zuulgatewayapplication.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;



public interface UserService extends UserDetailsService
{
	public UserDetails loadUserByUsername(String username);

}
