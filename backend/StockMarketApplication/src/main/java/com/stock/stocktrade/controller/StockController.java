package com.stock.stocktrade.controller;

import com.mongodb.DuplicateKeyException;
import com.stock.stocktrade.dto.CompanyDto;
import com.stock.stocktrade.dto.StockPriceDto;
import com.stock.stocktrade.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin
@EnableMongoAuditing
@RestController
@RequestMapping(value="/market")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping(value="/company/register")
    public ResponseEntity<?> addNewCompany(@RequestBody CompanyDto companyDto) throws DuplicateKeyException {

        return  ResponseEntity.status(HttpStatus.CREATED).body(stockService.addNewCompany(companyDto));
    }

    @GetMapping(path="/company/getall")
    public ResponseEntity<List<CompanyDto>> allStocksList(){
        return  ResponseEntity.ok(stockService.getAllStocks());

    }
    @DeleteMapping(path="/company/delete/{companyCode}")
    public String deleteById(@PathVariable int companyCode){
        stockService.deleteByCompanyCode(companyCode);
        return "Deleted Successfully";
    }

    @PostMapping(path="/stock/add/{companyCode}")
    public void addStockPrice(@RequestBody StockPriceDto stockPriceDto){
        stockService.addStockPrice(stockPriceDto);
    }

     @GetMapping(path="/company/info/{companyCode}")
    public ResponseEntity<List<CompanyDto>> getCompanyByCode(@PathVariable int companyCode){
         return  ResponseEntity.ok(stockService.getCompanyByCode(companyCode));

    }

    @GetMapping(path="/stock/get/{companyCode}/{startDate}/{endDate}")
    public ResponseEntity<List<StockPriceDto>> getCompanyByDate(@PathVariable int companyCode,@PathVariable String startDate, @PathVariable String endDate ) throws ParseException {
        return ResponseEntity.ok(stockService.getCompanyByDate(companyCode,startDate,endDate));
    }
}
