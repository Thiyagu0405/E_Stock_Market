package com.stock.stocktrade.repository;

import com.stock.stocktrade.model.StockPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPriceRepository extends MongoRepository<StockPrice, String> {


    void deleteByCompanyCode(int companyCode);
}
