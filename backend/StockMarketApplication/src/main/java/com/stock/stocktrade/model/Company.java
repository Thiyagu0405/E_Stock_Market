package com.stock.stocktrade.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("companies")
public class Company {

    @Id
    private String companyId;
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
