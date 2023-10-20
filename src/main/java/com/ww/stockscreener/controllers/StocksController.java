package com.ww.stockscreener.controllers;

import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.ww.stockscreener.core.StockInfo;
import com.ww.stockscreener.core.models.StockBar;
import com.ww.stockscreener.data.pipelinr.commands.GetEntireMarketDataBarsCommand;
import io.polygon.kotlin.sdk.rest.GroupedDailyParameters;
import io.polygon.kotlin.sdk.rest.PolygonRestClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
        String polygonKey = System.getenv("POLYGON");
        PolygonRestClient client = new PolygonRestClient(polygonKey);
        var results = client.getGroupedDailyAggregatesBlocking(new GroupedDailyParameters("us","stocks", LocalDateTime.now().minusDays(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),false));
        var infos = results.getResults().stream().map(dto -> new StockInfo(dto.getTicker(), dto.getClose() * dto.getVolume())).collect(Collectors.toList());
        return infos;
    }
}
