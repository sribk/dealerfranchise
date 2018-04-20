package com.sri.dealerfranchise.controller;

import com.sri.dealerfranchise.data.model.Dealer;
import com.sri.dealerfranchise.data.service.DealerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DealerController {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private DealerService dealerService;


    @CrossOrigin
    @RequestMapping(
            value = "/ping",
            method = RequestMethod.GET,
            produces = "plain/text")
    public ResponseEntity<String> ping() {
        logger.debug("Ping");
        String response = "Ping Success";

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @CrossOrigin
    @RequestMapping(
            value = "/dealer",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<Dealer> add(@Valid @RequestBody Dealer dealer) {
        logger.debug("Add dealer: " + dealer.toString());
        dealer = dealerService.save(dealer);

        return new ResponseEntity<>(dealer, HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/dealer",
            method = RequestMethod.PUT,
            produces = "application/json")
    public ResponseEntity<Dealer> update(@Valid @RequestBody Dealer dealer) {
        logger.debug("Add dealer: " + dealer.toString());
        dealer = dealerService.save(dealer);

        return new ResponseEntity<>(dealer, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/dealer/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<Dealer> get(@PathVariable String id) {
        logger.debug("Get dealer: " + id);
        Dealer dealer = dealerService.get(id);

        return new ResponseEntity<>(dealer, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/dealer",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<Dealer>> getAll() {
        logger.debug("Get all dealer");
        List<Dealer> dealers = dealerService.getAll();

        return new ResponseEntity<>(dealers, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/dealer/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    public ResponseEntity<String> delete(@PathVariable String id) {
        logger.debug("Delete dealer: " + id);
        dealerService.delete(id);

        return new ResponseEntity<>(" { \"status\": \"OK\" } ", HttpStatus.OK);
    }
}
