package com.bbahaida.stock.stockservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/stock")
public class StockResource {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/{username}")
    public Set<Stock> getStock(@PathVariable("username") final String username){


        ResponseEntity<Set<String>> quotesResponse = restTemplate.exchange("http://localhost:8300/rest/db/" + username, HttpMethod.GET,
                null, new ParameterizedTypeReference<Set<String>>() {});

        Set<String> quotes = quotesResponse.getBody();

        return quotes.stream()
                .map(this::getStockPrice)
                .collect(Collectors.toSet());
    }

    private Stock getStockPrice(String quote) {
        try {
            return YahooFinance.get(quote);
        } catch (IOException e) {
            throw new RuntimeException("Yahoo API error");
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public String return500(RuntimeException ex){
        return ex.getMessage();
    }


}
