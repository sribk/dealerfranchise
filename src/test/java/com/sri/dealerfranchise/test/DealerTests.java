package com.sri.dealerfranchise.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.sri.dealerfranchise.Application;
import com.sri.dealerfranchise.config.JsonMarshaller;
import com.sri.dealerfranchise.data.model.Dealer;
import com.sri.dealerfranchise.data.model.Franchise;
import com.sri.dealerfranchise.data.service.DealerService;
import com.sri.dealerfranchise.data.service.FranchiseService;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Transactional
public class DealerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @Autowired
    private DealerService dealerService;

    @Autowired
    private FranchiseService franchiseService;
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void addDealer() throws Exception {
        Franchise franchise = new Franchise();
        franchise.setName("New Dealer Franchise");
        franchiseService.save(franchise);

        Dealer dealer = new Dealer();
        dealer.setName("New  Name");
        dealer.setFranchise(franchise);

        mockMvc.perform(post("/dealer")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(dealer))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.franchise.name").value(franchise.getName()))
                .andExpect(jsonPath("$.franchise.id").value(franchise.getId()))
                .andDo(print());
    }


    @Test
    public void updateDealer() throws Exception {
        String firstName = "Update  Name";

        Franchise franchise = new Franchise();
        franchise.setName("Update Dealer Franchise");
        franchiseService.save(franchise);

        Dealer dealer = new Dealer();
        dealer.setName(firstName);
        dealer.setFranchise(franchise);
        dealer = dealerService.save(dealer);

        assertNotNull(dealer.getId());
        String originalId = dealer.getId();

        Assert.assertEquals(dealer.getName(), firstName);

        String newName = "Changed Update name";
        dealer.setName(newName);

        mockMvc.perform(put("/dealer")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(dealer))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(originalId))
                .andExpect(jsonPath("$.name").value(newName))
                .andDo(print())
                .andReturn();
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteDealer() throws Exception {
        String name = "Delete Test Dealer";

        Franchise franchise = new Franchise();
        franchise.setName("Delete Dealer Franchise");
        franchiseService.save(franchise);

        Dealer dealer = new Dealer();
        dealer.setName(name);
        dealer.setFranchise(franchise);
        dealer = dealerService.save(dealer);

        Assert.assertNotNull(dealer.getId());
        Assert.assertNotNull(dealerService.get(dealer.getId()));

        mockMvc.perform(delete("/dealer/" + dealer.getId()))
                .andExpect(status().isOk())
                .andDo(print());

        dealerService.get(dealer.getId());
    }

    public void errorOnNonExistentDealer() throws Exception {
        mockMvc.perform(delete("/dealer/" + UUID.randomUUID().toString()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void listDealers() throws Exception {
        String name = "List Test Dealer";

        Franchise franchise = new Franchise();
        franchise.setName("List Dealer Franchise");
        franchiseService.save(franchise);

        int numberDealers = 10;
        for(int i=1; i<=numberDealers; i++) {
            Dealer dealer = new Dealer();
            dealer.setName(i + " " + name);
            dealer.setFranchise(franchise);
            dealerService.save(dealer);
        }

        mockMvc.perform(get("/dealer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(numberDealers))
                .andDo(print())
                .andReturn();
    }

}
