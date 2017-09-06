package de.appblocks.microservice.purchaseoffer.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class Supplier {
	@Id
	@NotNull
	private Integer id;
	private String name;
	@Column(unique=true)
	private String abr;
	private String email;
	@OneToMany(mappedBy="supplier")
	private List<PurchaseOffer> offers;
}