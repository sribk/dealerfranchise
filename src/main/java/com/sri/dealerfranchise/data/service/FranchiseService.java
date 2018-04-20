package com.sri.dealerfranchise.data.service;

import com.sri.dealerfranchise.data.model.Franchise;
import com.sri.dealerfranchise.data.repository.FranchiseRepository;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class FranchiseService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private FranchiseRepository repository;

    public Franchise save(Franchise Franchise) {
        Franchise newFranchise = repository.save(Franchise);

        return newFranchise;
    }

    public Franchise get(String id) {
        Optional<Franchise> optionalFranchise = repository.findById(id);
        return optionalFranchise.orElseThrow(() -> new EntityNotFoundException("No Franchise found with id " + id));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<Franchise> getAll() {
        return IteratorUtils.toList(repository.findAll().iterator());
    }

}
