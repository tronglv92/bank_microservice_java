/**
 *
 */
package com.tronglv.loans.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tronglv.loans.config.LoansServiceConfig;
import com.tronglv.loans.model.Customer;
import com.tronglv.loans.model.Loans;
import com.tronglv.loans.model.Properties;
import com.tronglv.loans.repository.LoansRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Eazy Bytes
 *
 */

@RestController
public class LoansController {

    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    LoansServiceConfig loansConfig;
    private static final Logger logger= LoggerFactory.getLogger(LoansController.class);
    @PostMapping("/myLoans")
    public List<Loans> getLoansDetails(@RequestHeader("tronglv-correlation-id") String correlationid, @RequestBody Customer customer) {
        logger.info("getLoansDetails() method started");

        List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
        logger.info("getLoansDetails() method end");
        if (loans != null) {
            return loans;
        } else {
            return null;
        }

    }

    @GetMapping("/loans/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(loansConfig.getMsg(), loansConfig.getBuildVersion(),
                loansConfig.getMailDetails(), loansConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

}
