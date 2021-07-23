package br.com.compasso.uol.products.dto;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import br.com.compasso.uol.products.entity.Produto;

public class ProdutoRequestDTO {

	private UUID id;
	@NotBlank(message = "{name.not.blank}")
	private String name;
	@NotBlank(message = "{description.not.blank}")
	private String description;
	@Positive(message = "{price.min.value}")
	private BigDecimal price;

	public ProdutoRequestDTO(String name, String description, BigDecimal price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public UUID getId() {
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

	public Produto converter() {
		return new Produto(this.name, this.description, this.price);
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
