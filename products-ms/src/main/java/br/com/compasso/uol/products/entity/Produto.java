package br.com.compasso.uol.products.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Table(name = "PRODUTOS")
@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@NotBlank
	@Column(name = "NAME", nullable = false, length = 50)
	private String name;

	@NotBlank
	@Column(name = "DESCRIPTION", nullable = false, length = 250)
	private String description;

	@NotNull
	@Column(name = "PRICE", nullable = false)
	@Min(value = 0, message = "Necessário que o preço seja positivo.")
	private BigDecimal price;

	public Produto(String name, String description, BigDecimal price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public Produto() {

	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
