package com.stock.stocktrade.zuulgatewayapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvalidTokenException extends Throwable
{
	private String message;
}
