package de.appblocks.microservice.purchaseoffer.integration.topics;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import de.appblocks.microservice.purchaseoffer.domain.PurchaseOffer;

@Component
@Aspect
public class PurchaseOfferTopicAdapter {
	
	@Autowired
    private KafkaTemplate<String, PurchaseOffer> purchaseOffer;
	
	@AfterReturning(
	        pointcut="execution (* de.appblocks.microservice.purchaseoffer.web.PurchaseOfferController.saveOffer(de.appblocks.microservice.purchaseoffer.domain.PurchaseOffer))",
	        returning="info")
	public void listen(PurchaseOffer info) throws Exception {
		this.purchaseOffer.send(PurchaseOfferTopic.PURCHASE_OFFER_V1_OFFER_NEW, info);
	}

}
