package com.bbahaida.stock.dbservice.repositories;

import com.bbahaida.stock.dbservice.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    Set<Quote> findAllByUsername(String username);

}
