package com.stock.stocktrade.dto;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    @NonNull
    @Indexed(unique = true)
    private Integer companyCode;
    @NonNull
    private String companyName;
    @NonNull
    private String companyCEO;
    @NonNull
    private Long companyTurnOver;
    @NonNull
    private String companyWebsite;
    @NonNull
    private String exchange;

    private Object stockPrices;
}
