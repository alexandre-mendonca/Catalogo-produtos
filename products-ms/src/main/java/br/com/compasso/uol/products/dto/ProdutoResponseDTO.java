package br.com.compasso.uol.products.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.compasso.uol.products.entity.Produto;

public class ProdutoResponseDTO {

	private String id;
	private String name;
	private String description;
	private BigDecimal price;

	public ProdutoResponseDTO(Produto produto) {
		this.id = produto.getId();
		this.name = produto.getName();
		this.description = produto.getDescription();
		this.price = produto.getPrice();
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

	public static Page<ProdutoResponseDTO> converter(Page<Produto> produtos) {
		return produtos.map(ProdutoResponseDTO::new);
	}

	public static List<ProdutoResponseDTO> converter(List<Produto> produtos) {
		return produtos.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
	}

}
