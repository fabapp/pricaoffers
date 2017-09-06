package de.appblocks.microservice.contact.domain;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of={"name", "email"})
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotBlank
	@Column(unique=true, nullable=false)
	private String name;
	
	@ElementCollection(targetClass=ContactRelation.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="contact_relation_types")
    @Column(name="relation")
	Set<ContactRelation> relationTypes;
	
	@Email
	@NotBlank
	@Column(unique=true, nullable=false)		
	private String email;	
	
	private String description;
	
	public Contact() {	}

	public Contact(Integer id) { this.id=id; }
	
	private void setId(Integer id) {
		this.id = id;
	}
}
