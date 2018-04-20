package com.sri.dealerfranchise.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sri.dealerfranchise.data.model.Dealer;

@Repository
public interface DealerRepository extends PagingAndSortingRepository<Dealer, String> {

}
