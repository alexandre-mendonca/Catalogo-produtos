package br.com.compasso.uol.productsms;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.compasso.uol.products.ProductsMsApplication;
import br.com.compasso.uol.products.dto.ProdutoRequestDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductsMsApplication.class)
@AutoConfigureMockMvc
public class ProductsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("Recuperando produto com id existente.")
	public void findById() throws Exception {
		mockMvc.perform(get("/products/{id}", 2).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@DisplayName("Recuperando produto com id inexistente.")
	public void findByIdInexistente() throws Exception {
		mockMvc.perform(get("/products/{id}", 200).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andReturn();
	}

	@Test
	@DisplayName("Recuperando todos os produtos utilizando paginação.")
	public void findByAll() throws Exception {
		mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
	}

	@Test
	@DisplayName("Recuperando produtos através do findSearch informando os parâmetros.")
	public void findSearch() throws Exception {
		mockMvc.perform(get("/products/search").accept(MediaType.APPLICATION_JSON).param("minPrice", "12.65")
				.param("maxPrice", "30.00").param("q", "Bolacha")).andExpect(status().isOk()).andReturn();
	}

	@Test
	@DisplayName("Recuperando produtos através do findSearch vazio")
	public void findSearchVazio() throws Exception {
		mockMvc.perform(get("/products/search").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@DisplayName("Removendo um produto com id existente.")
	public void deleteIdExistente() throws Exception {
		mockMvc.perform(delete("/products/{id}", 1).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@DisplayName("Removendo um produto com id inexistente.")
	public void deleteIdInexistente() throws Exception {
		mockMvc.perform(delete("/products/{id}", 100).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	@DisplayName("Inserindo um novo produto.")
	public void postProduto() throws Exception {
		ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO("Azeite", "Azeite", new BigDecimal(15.90));
		mockMvc.perform(MockMvcRequestBuilders.post("/products")
				.content(objectMapper.writeValueAsString(produtoRequestDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

	}

	@Test
	@DisplayName("Alterando um produto com id existente.")
	public void putIdExistente() throws Exception {
		ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO("Farinha", "Farinha de Mandioca",
				new BigDecimal(5.90));
		mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", 4)
				.content(objectMapper.writeValueAsString(produtoRequestDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Alterando um produto com id inexistente.")
	public void putIdInexistente() throws Exception {
		ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO("Farinha", "Farinha de Mandioca",
				new BigDecimal(5.90));
		mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", 50)
				.content(objectMapper.writeValueAsString(produtoRequestDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

	@Test
	@DisplayName("Inserindo um produto com o name inválido.")
	public void postNameInvalido() throws Exception {
		ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO(" ", "Azeite", new BigDecimal(15.90));
		mockMvc.perform(MockMvcRequestBuilders.post("/products")
				.content(objectMapper.writeValueAsString(produtoRequestDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Inserindo um produto com a description inválida.")
	public void postDescriptionInvalido() throws Exception {
		ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO("Azeite", " ", new BigDecimal(15.90));
		mockMvc.perform(MockMvcRequestBuilders.post("/products")
				.content(objectMapper.writeValueAsString(produtoRequestDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	@DisplayName("Inserindo um produto com o price inválido.")
	public void postPriceInvalido() throws Exception {
		ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO("Azeite", "Azeite", new BigDecimal(-2.00));
		mockMvc.perform(MockMvcRequestBuilders.post("/products")
				.content(objectMapper.writeValueAsString(produtoRequestDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

}
