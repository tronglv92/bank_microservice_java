package com.tronglv.accounts.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tronglv.accounts.config.AccountsServiceConfig;
import com.tronglv.accounts.model.Accounts;
import com.tronglv.accounts.model.Customer;
import com.tronglv.accounts.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.tronglv.accounts.model.Properties;


@RestController
public class AccountsController {
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    AccountsServiceConfig accountsConfig;

    @PostMapping("/myAccount")
    public Accounts getAccountDetails(@RequestBody Customer customer) {

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        if (accounts != null) {
            return accounts;
        } else {
            return null;
        }

    }
    @GetMapping("/accounts/properties")
    public String getPropertyDetails() throws JsonProcessingException{
        ObjectWriter ow=new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties=new Properties(accountsConfig.getMsg(),accountsConfig.getBuildVersion(),accountsConfig.getMailDetails(),accountsConfig.getActiveBranches());
        String jsonStr=ow.writeValueAsString(properties);
        return jsonStr;
    }
}
