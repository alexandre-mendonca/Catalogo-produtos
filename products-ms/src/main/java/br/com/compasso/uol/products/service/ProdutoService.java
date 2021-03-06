package br.com.compasso.uol.products.service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.uol.products.dto.ProdutoRequestDTO;
import br.com.compasso.uol.products.dto.ProdutoResponseDTO;
import br.com.compasso.uol.products.entity.Produto;
import br.com.compasso.uol.products.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public ResponseEntity<ProdutoResponseDTO> finById(String id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isPresent()) {
			return ResponseEntity.ok(new ProdutoResponseDTO(produto.get()));
		}
		return ResponseEntity.notFound().build();

	}

	public Page<ProdutoResponseDTO> findAll(Pageable paginacao) {
		Page<Produto> produtos = produtoRepository.findAll(paginacao);
		return ProdutoResponseDTO.converter(produtos);

	}

	public ResponseEntity<List<ProdutoResponseDTO>> findSearch(BigDecimal minPrice, BigDecimal maxPrice, String q) {
		List<Produto> produtos = produtoRepository.findBySearch(minPrice, maxPrice, q);
		if (!produtos.isEmpty()) {
			return ResponseEntity.ok(ProdutoResponseDTO.converter(produtos));
		}
		return ResponseEntity.ok().build();
	}

	public ResponseEntity<ProdutoResponseDTO> post(@Valid ProdutoRequestDTO produtoRequestDTO,
			UriComponentsBuilder uriBuilder) {
		Produto produto = produtoRequestDTO.converter();
		produtoRepository.save(produto);

		URI uri = uriBuilder.path("/produto/{id}").buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProdutoResponseDTO(produto));

	}

	public ResponseEntity<ProdutoResponseDTO> delete(String id) {
		Optional<Produto> optional = produtoRepository.findById(id);
		if (optional.isPresent()) {
			produtoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<ProdutoResponseDTO> put(String id, ProdutoRequestDTO produtoRequestDTO) {
		Optional<Produto> optional = produtoRepository.findById(id);
		if (optional.isPresent()) {
			Produto produto = optional.get();
			produto.setName(produtoRequestDTO.getName());
			produto.setDescription(produtoRequestDTO.getDescription());
			produto.setPrice(produtoRequestDTO.getPrice());

			return ResponseEntity.ok(new ProdutoResponseDTO(produto));
		}
		return ResponseEntity.notFound().build();
	}

}
