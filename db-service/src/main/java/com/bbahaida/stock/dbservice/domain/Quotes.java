package com.bbahaida.stock.dbservice.domain;

import java.io.Serializable;
import java.util.Set;

public class Quotes implements Serializable {
    private String username;
    private Set<String> quotes;


    public Quotes() {
    }

    public Quotes(String username, Set<String> quotes) {
        this.username = username;
        this.quotes = quotes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getQuotes() {
        return quotes;
    }

    public void setQuotes(Set<String> quotes) {
        this.quotes = quotes;
    }
}
