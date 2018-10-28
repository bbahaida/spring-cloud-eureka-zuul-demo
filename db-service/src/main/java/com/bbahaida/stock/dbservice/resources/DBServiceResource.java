package com.bbahaida.stock.dbservice.resources;

import com.bbahaida.stock.dbservice.domain.Quote;
import com.bbahaida.stock.dbservice.domain.Quotes;
import com.bbahaida.stock.dbservice.repositories.QuoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/rest/db")
public class DBServiceResource {

    private QuoteRepository quoteRepository;

    public DBServiceResource(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @GetMapping("/{username}")
    public Set<String> getQuotes(@PathVariable("username") final String username){

        return getQuotesByUsername(username);
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Set<String> addQuotes(@RequestBody final Quotes quotes){

        quotes.getQuotes()
                .stream()
                .map(quote -> new Quote(quote, quotes.getUsername()))
                .forEach(quote -> quoteRepository.save(quote));

        return getQuotesByUsername(quotes.getUsername());
    }

    @DeleteMapping("/delete/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuotes(@PathVariable("username") final String username) throws Exception{
        quoteRepository.delete(quoteRepository.findAllByUsername(username));
        if (getQuotesByUsername(username).size() > 0){
            throw new Exception("error while deleting quotes of "+username);
        }
    }


    private Set<String> getQuotesByUsername(final String username) {
        return quoteRepository.findAllByUsername(username)
                .stream()
                .map(Quote::getQuote)
                .collect(Collectors.toSet());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String return500(Exception ex){
        return ex.getMessage();
    }
}
