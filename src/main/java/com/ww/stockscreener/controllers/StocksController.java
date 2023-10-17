package com.ww.stockscreener.controllers;

import com.ww.stockscreener.core.StockInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StocksController {
    @GetMapping("/stocks")
    public String getStocks() {
        return "hello, world";
    }

    @GetMapping("/stocks/cap")
    public StockInfo getStockInfo() {
        return new StockInfo("AAPL",100);
    }
}
