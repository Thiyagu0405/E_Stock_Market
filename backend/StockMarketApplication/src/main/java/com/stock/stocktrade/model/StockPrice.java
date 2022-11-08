package com.stock.stocktrade.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("stock_price")
public class StockPrice {

    private String Id;
    private Integer companyCode;
    private double  stockPrice;
    @DateTimeFormat(style = "M-")
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date endDate;
    @CreatedDate
    private Date time;
    private String exchange;

}
