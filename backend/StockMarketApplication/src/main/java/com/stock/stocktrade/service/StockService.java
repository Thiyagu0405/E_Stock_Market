package com.stock.stocktrade.service;

import com.stock.stocktrade.dto.CompanyDto;
import com.stock.stocktrade.dto.StockPriceDto;

import java.text.ParseException;
import java.util.List;

public interface StockService {

    public List<CompanyDto> getAllStocks();
    public String deleteByCompanyCode(int companyCode);
    public CompanyDto addNewCompany(CompanyDto companyDto);
    public StockPriceDto addStockPrice(StockPriceDto stockPriceDto);
    public List<CompanyDto> getCompanyByCode(int companyCode);
    public List<StockPriceDto> getCompanyByDate(int companyCode, String startDate,String endDate) throws ParseException;

}
