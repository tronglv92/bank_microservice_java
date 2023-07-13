/**
 * 
 */
package com.tronglv.cards.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tronglv.cards.config.CardsServiceConfig;
import com.tronglv.cards.model.Cards;
import com.tronglv.cards.model.Customer;
import com.tronglv.cards.model.Properties;
import com.tronglv.cards.repository.CardsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Eazy Bytes
 *
 */

@RestController
public class CardsController {

	@Autowired
	private CardsRepository cardsRepository;
	@Autowired
	CardsServiceConfig cardsConfig;

	private static final Logger logger= LoggerFactory.getLogger(CardsController.class);
	@PostMapping("/myCards")
	public List<Cards> getCardDetails(@RequestHeader("tronglv-correlation-id") String correlationid, @RequestBody Customer customer) {
		logger.debug("correlationid::",correlationid);
		List<Cards> cards = cardsRepository.findByCustomerId(customer.getCustomerId());
		if (cards != null) {
			return cards;
		} else {
			return null;
		}

	}
	@GetMapping("/cards/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(cardsConfig.getMsg(), cardsConfig.getBuildVersion(),
				cardsConfig.getMailDetails(), cardsConfig.getActiveBranches());
		String jsonStr = ow.writeValueAsString(properties);
		return jsonStr;
	}

}
