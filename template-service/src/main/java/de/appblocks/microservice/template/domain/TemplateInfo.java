package de.appblocks.microservice.template.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of={"description"})
public class TemplateInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotBlank
	@Column(unique=true)
	private String name;
	
	private String description;
	
	@NotBlank
	private String content;
	
	public TemplateInfo() {	}

	public TemplateInfo(Integer id) { this.id=id; }
	
	private void setId(Integer id) {
		this.id = id;
	}
}
