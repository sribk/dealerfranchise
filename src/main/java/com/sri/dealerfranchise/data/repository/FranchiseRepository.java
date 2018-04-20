package com.sri.dealerfranchise.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sri.dealerfranchise.data.model.Franchise;

@Repository
public interface FranchiseRepository extends CrudRepository<Franchise, String> {

}
