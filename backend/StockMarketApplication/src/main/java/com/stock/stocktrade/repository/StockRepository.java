package com.stock.stocktrade.repository;

import com.stock.stocktrade.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StockRepository extends MongoRepository<Company, String> {

    void deleteByCompanyCode(int companyCode);
}
