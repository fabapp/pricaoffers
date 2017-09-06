package de.appblocks.microservice.purchaseoffer.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.appblocks.microservice.purchaseoffer.domain.PurchaseOffer;
import de.appblocks.microservice.purchaseoffer.domain.Supplier;
import de.appblocks.microservice.purchaseoffer.service.PurchaseOfferService;

@RestController
@RequestMapping(path="/v1,",
				consumes={"application/json;charset=UTF-8"}, 
				produces={"application/json;charset=UTF-8"})
public class PurchaseOfferController {

	@Autowired
	private PurchaseOfferService purchaseOfferService;

	@GetMapping("/purchaseoffers/{offerId}")
	public PurchaseOffer getOffer(@PathVariable("offerId") Integer offerId) {
		return purchaseOfferService.getOffer(offerId);
	}
	
	@GetMapping("/purchaseoffers/?n={productName}")
	public List<PurchaseOffer> getOffer(@PathVariable("productName") String productName) {
		return purchaseOfferService.findOfferByProduct(productName);
	}

	@PostMapping("/purchaseoffers/")
	public PurchaseOffer saveOffer(@RequestBody PurchaseOffer purchaseOffer) {
		return purchaseOfferService.saveOffer(purchaseOffer);
	}
	
	@GetMapping("/suppliers/")
	public List<Supplier> getSuppliers() {
		return purchaseOfferService.getSuppliers();
	}
}
