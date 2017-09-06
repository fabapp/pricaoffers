package de.appblocks.microservice.purchaseoffer.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOfferRepository extends JpaRepository<PurchaseOffer, Integer> {

	List<PurchaseOffer> findByProductName(String productName);

}
