package com.stock.stocktrade.zuulgatewayapplication.initializer;

import com.stock.stocktrade.zuulgatewayapplication.dao.UserRepository;
import com.stock.stocktrade.zuulgatewayapplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserServiceApplicationInitializer implements CommandLineRunner
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception 
	{
		userRepository.deleteAll();
		User admin = new User("admin", passwordEncoder.encode("password"), "thiyagu170@gmail.com", "9500939285");
		admin.setRole("ADMIN");
		admin.setConfirmed(true);
		userRepository.save(admin);
	}
}
