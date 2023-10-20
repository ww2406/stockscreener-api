package com.ww.stockscreener.controllers;

import an.awesome.pipelinr.Pipeline;
import com.ww.stockscreener.core.StockInfo;
import com.ww.stockscreener.core.models.StockBar;
import com.ww.stockscreener.data.pipelinr.commands.GetEntireMarketDataBarsCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StocksController {
    private final Pipeline pipeline;

    public StocksController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping("/bars")
    public ResponseEntity<List<StockBar>> getStocks(@RequestParam LocalDate startDate,
                                              @RequestParam LocalDate endDate) {
        var results = pipeline.send(new GetEntireMarketDataBarsCommand(startDate, endDate));
        return ResponseEntity.ok(results);
    }

    @GetMapping("/stocks/cap")
    public List<StockInfo> getStockInfo() {
        return null;
    }
}
