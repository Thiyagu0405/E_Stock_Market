package com.stock.stocktrade.zuulgatewayapplication.dao;

import com.stock.stocktrade.zuulgatewayapplication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String>
{
	public User findByUsername(String username);
	public User findByUsernameOrEmail(String username, String email);
	public User findByConfirmationToken(String token);
}
