package com.stock.stocktrade.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockPriceDto {

    @Id
    private String id;
    @NonNull
    private Integer companyCode;
    @NonNull
    private Double stockPrice;
    @DateTimeFormat(style = "M-")
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date endDate;
    @CreatedDate
    private Date time;

}
