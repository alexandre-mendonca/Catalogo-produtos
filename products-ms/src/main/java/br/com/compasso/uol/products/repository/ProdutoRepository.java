package br.com.compasso.uol.products.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.compasso.uol.products.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, String> {

	@Query(value = "select p from Produto p where p.price >= :minPrice and p.price <= :maxPrice "
			+ "or ((p.name like %:q% or p.description like %:q%))")
	public List<Produto> findBySearch(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice,
			@Param("q") String q);

}
