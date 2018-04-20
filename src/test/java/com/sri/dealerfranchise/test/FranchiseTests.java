package com.sri.dealerfranchise.test;

import com.sri.dealerfranchise.Application;
import com.sri.dealerfranchise.config.JsonMarshaller;
import com.sri.dealerfranchise.data.model.Franchise;
import com.sri.dealerfranchise.data.service.FranchiseService;

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

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Transactional
public class FranchiseTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @Autowired
    private FranchiseService FranchiseService;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void addFranchise() throws Exception {
        Franchise Franchise = new Franchise();
        Franchise.setName("New Franchise");

        mockMvc.perform(post("/franchise")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(Franchise))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(Franchise.getName()))
                .andDo(print())
                .andReturn();
    }


    @Test
    public void updateFranchise() throws Exception {
        String name = "Update Franchise";
        Franchise Franchise = new Franchise();
        Franchise.setName(name);
        Franchise = FranchiseService.save(Franchise);

        assertNotNull(Franchise.getId());
        String originalId = Franchise.getId();

        Assert.assertEquals(Franchise.getName(), name);

        String newName = "Changed Update Test Franchise";
        Franchise.setName(newName);

        mockMvc.perform(put("/franchise")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(Franchise))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(originalId))
                .andExpect(jsonPath("$.name").value(newName))
                .andDo(print())
                .andReturn();
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteFranchise() throws Exception {
        String name = "Delete Test Franchise";
        Franchise Franchise = new Franchise();
        Franchise.setName(name);
        Franchise = FranchiseService.save(Franchise);

        mockMvc.perform(delete("/franchise/" + Franchise.getId()))
                .andExpect(status().isOk())
                .andDo(print());

        FranchiseService.get(Franchise.getId());
    }

    @Test
    public void listFranchises() throws Exception {
        String name = "List Test Franchise";

        int numberFranchises = 10;
        for(int i=1; i<=numberFranchises; i++) {
            Franchise Franchise = new Franchise();
            Franchise.setName(i + " " + name);
            FranchiseService.save(Franchise);
        }

        mockMvc.perform(get("/franchise")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(numberFranchises))
                .andDo(print())
                .andReturn();
    }

}
