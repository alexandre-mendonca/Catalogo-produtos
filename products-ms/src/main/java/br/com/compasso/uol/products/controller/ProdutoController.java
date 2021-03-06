package br.com.compasso.uol.products.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.uol.products.dto.ProdutoRequestDTO;
import br.com.compasso.uol.products.dto.ProdutoResponseDTO;
import br.com.compasso.uol.products.service.ProdutoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/products")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/{id}")
	@ApiOperation(value = "Busca um produto pelo id")
	public ResponseEntity<ProdutoResponseDTO> findById(@PathVariable String id) {
		return produtoService.finById(id);
	}

	@GetMapping
	@ApiOperation(value = "Busca todos os produtos.")
	public Page<ProdutoResponseDTO> findAll(
			@PageableDefault(sort = "name", direction = Direction.ASC, size = 15) Pageable paginacao) {
		return produtoService.findAll(paginacao);
	}

	@GetMapping("/search")
	@ApiOperation(value = "Busca um produto de acordo com o intervalo de preço, nome ou descrição.")
	public ResponseEntity<List<ProdutoResponseDTO>> findSearch(@RequestParam(required = false) BigDecimal minPrice,
			@RequestParam(required = false) BigDecimal maxPrice, @RequestParam(required = false) String q) {
		return produtoService.findSearch(minPrice, maxPrice, q);

	}

	@PostMapping
	@Transactional
	@ApiOperation(value = "Cria um novo produto.")
	public ResponseEntity<ProdutoResponseDTO> post(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO,
			UriComponentsBuilder uriBuilder) {
		return produtoService.post(produtoRequestDTO, uriBuilder);

	}

	@DeleteMapping("/{id}")
	@Transactional
	@ApiOperation(value = "Remove um produto de acordo com o id informado.")
	public ResponseEntity<ProdutoResponseDTO> delete(@PathVariable String id) {
		return produtoService.delete(id);

	}

	@PutMapping("/{id}")
	@Transactional
	@ApiOperation(value = "Atualiza um produto de acordo com o id informado.")
	public ResponseEntity<ProdutoResponseDTO> update(@PathVariable String id,
			@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) {
		return produtoService.put(id, produtoRequestDTO);

	}
}
