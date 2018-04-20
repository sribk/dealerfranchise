package com.sri.dealerfranchise.controller;

import com.sri.dealerfranchise.data.model.Franchise;
import com.sri.dealerfranchise.data.service.FranchiseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FranchiseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private FranchiseService FranchiseService;


    @CrossOrigin
    @RequestMapping(
            value = "/franchise",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<Franchise> add(@Valid @RequestBody Franchise Franchise) {
        logger.debug("Add Franchise: " + Franchise.toString());
        Franchise = FranchiseService.save(Franchise);

        return new ResponseEntity<>(Franchise, HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/franchise",
            method = RequestMethod.PUT,
            produces = "application/json")
    public ResponseEntity<Franchise> update(@Valid @RequestBody Franchise Franchise) {
        logger.debug("Update Franchise: " + Franchise.toString());
        Franchise = FranchiseService.save(Franchise);

        return new ResponseEntity<>(Franchise, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/franchise/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<Franchise> get(@PathVariable String id) {
        logger.debug("Get Franchise: " + id);
        Franchise Franchise = FranchiseService.get(id);

        return new ResponseEntity<>(Franchise, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/franchise",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<Franchise>> getAll() {
        logger.debug("Get all dealer");
        List<Franchise> Franchises = FranchiseService.getAll();

        return new ResponseEntity<>(Franchises, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/franchise/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    public ResponseEntity<String> delete(@PathVariable String id) {
        logger.debug("Delete Franchise: " + id);
        FranchiseService.delete(id);

        return new ResponseEntity<>(" { \"status\": \"OK\" } ", HttpStatus.OK);
    }
}
