package de.appblocks.microservice.purchaseoffer.domain;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of={"supplier", "product"})
public class PurchaseOffer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(optional=false)
	private Supplier supplier;

	@ManyToOne(optional=false)
	private SupplierProduct product;

	@Column(precision=4)
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal price;
	
	public PurchaseOffer() {	}

	public PurchaseOffer(Integer id) { this.id=id; }
	
	@SuppressWarnings("unused")
	private void setId(Integer id) { this.id = id; }
}
