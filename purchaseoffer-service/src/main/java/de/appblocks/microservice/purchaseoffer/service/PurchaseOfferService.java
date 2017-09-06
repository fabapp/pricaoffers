package de.appblocks.microservice.purchaseoffer.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.appblocks.microservice.purchaseoffer.domain.PurchaseOffer;
import de.appblocks.microservice.purchaseoffer.domain.PurchaseOfferRepository;
import de.appblocks.microservice.purchaseoffer.domain.Supplier;
import de.appblocks.microservice.purchaseoffer.domain.SupplierRepository;

@Service
public class PurchaseOfferService {

	@Autowired
	private PurchaseOfferRepository purchaseOfferRepository;
	@Autowired
	private SupplierRepository supplierRepository;

	public PurchaseOffer getOffer(Integer id) {
		return purchaseOfferRepository.findOne(id);
	}

	@Transactional
	public PurchaseOffer saveOffer(PurchaseOffer purchaseOffer) {
		
		return purchaseOfferRepository.save(purchaseOffer);
	}

	public List<Supplier> getSuppliers() {
		return supplierRepository.findAll();
	}

	public List<PurchaseOffer> findOfferByProduct(String productName) {
		return purchaseOfferRepository.findByProductName(productName);
	}
}
