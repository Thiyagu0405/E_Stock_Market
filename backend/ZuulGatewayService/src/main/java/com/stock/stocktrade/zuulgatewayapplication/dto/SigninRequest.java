package com.stock.stocktrade.zuulgatewayapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest
{
	private String username;
	private String password;
}
