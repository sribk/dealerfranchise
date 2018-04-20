package com.sri.dealerfranchise.data.service;

import com.sri.dealerfranchise.data.model.Dealer;
import com.sri.dealerfranchise.data.repository.DealerRepository;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DealerService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private DealerRepository repository;

    public Dealer save(Dealer dealer) {
        return repository.save(dealer);
    }

    public Dealer get(String id) {
        Optional<Dealer> optionalDealer = repository.findById(id);
        return optionalDealer.orElseThrow(() -> new EntityNotFoundException("No dealer found with id " + id));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<Dealer> getAll() {
        return IteratorUtils.toList(repository.findAll().iterator());
    }


}
