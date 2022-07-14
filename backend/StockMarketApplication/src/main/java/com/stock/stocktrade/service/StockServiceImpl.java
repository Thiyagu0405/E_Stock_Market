package com.stock.stocktrade.service;


import com.stock.stocktrade.dto.CompanyDto;
import com.stock.stocktrade.dto.StockPriceDto;
import com.stock.stocktrade.mapper.CompanyMapper;
import com.stock.stocktrade.mapper.StockPriceMapper;
import com.stock.stocktrade.model.Company;
import com.stock.stocktrade.model.StockPrice;
import com.stock.stocktrade.repository.StockPriceRepository;
import com.stock.stocktrade.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockPriceRepository stockPriceRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private StockPriceMapper stockPriceMapper;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<CompanyDto> getAllStocks()
    {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("stock_price").localField("companyCode").foreignField("companyCode").as("stockPrices");
        TypedAggregation<CompanyDto> agg = Aggregation.newAggregation(CompanyDto.class,lookupOperation);
        AggregationResults<CompanyDto> results = mongoTemplate.aggregate(agg,"companies",CompanyDto.class);
        return results.getMappedResults();

//        List<Company> stockExchanges = stockRepository.findAll();
//        return companyMapper.toCompanyDtos(stockExchanges);
    }


    @Override
    public String deleteByCompanyCode(int companyCode) {
      stockRepository.deleteByCompanyCode(companyCode);
      stockPriceRepository.deleteByCompanyCode(companyCode);
      return "Deleted Successfully";
    }

    @Override
    public CompanyDto addNewCompany(CompanyDto companyDto){
        Company company = new Company();
        try {
             company = companyMapper.toCompany(companyDto);
            company = stockRepository.save(company);

        } catch (DuplicateKeyException e) {
            System.out.println(e);

        }
        return companyMapper.toCompanyDto(company);
    }

    @Override
    public StockPriceDto addStockPrice(StockPriceDto stockPriceDto) {
        StockPrice stockPrice = stockPriceMapper.toStockPrice(stockPriceDto);
        stockPrice = stockPriceRepository.save(stockPrice);
        return stockPriceMapper.toStockPriceDto(stockPrice);
    }

    @Override
    public List<CompanyDto> getCompanyByCode(int companyCode) {
        AggregationOperation aggregation = Aggregation.match(Criteria.where("companyCode").is(companyCode));
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("stock_price").localField("companyCode").foreignField("companyCode").as("stockPrices");
        TypedAggregation<CompanyDto> agg = Aggregation.newAggregation(CompanyDto.class,lookupOperation,aggregation);

        AggregationResults<CompanyDto> results = mongoTemplate.aggregate(agg,"companies",CompanyDto.class);
        return results.getMappedResults();

    }
    @Override
    public List<StockPriceDto> getCompanyByDate(int companyCode, String startDate, String endDate) throws ParseException {
        Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date toDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

        AggregationOperation aggregation = Aggregation.match(Criteria.where("companyCode").is(companyCode));
        AggregationOperation aggregationDate = Aggregation.match(Criteria.where("createdDate").gte(fromDate)
                .andOperator(Criteria.where("endDate").lt(toDate)));
        TypedAggregation<StockPriceDto> agg = Aggregation.newAggregation(StockPriceDto.class,aggregationDate,aggregation);

        AggregationResults<StockPriceDto> results = mongoTemplate.aggregate(agg,"stock_price",StockPriceDto.class);
        return results.getMappedResults();

    }
}
