package com.ww.stockscreener.configs;

import com.ww.stockscreener.core.repos.StockProviderRepo;
import com.ww.stockscreener.data.repos.PolygonStockProviderRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public StockProviderRepo stockProviderRepo() {
        return new PolygonStockProviderRepo();
    }
}
