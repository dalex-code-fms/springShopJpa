package fr.fms.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.fms.entities.Article;
import fr.fms.entities.Category;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	public List<Article> findByBrand(String brand);

	public List<Article> findByCategoryName(String categoryName);

	@Query("select A from Article A where A.brand like %:x% and A.price > :y")
	public List<Article> searchArticles(@Param("x") String kw, @Param("y") double price);

	@Query("select A from Article A where A.description = :x and A.brand = :y")
	public List<Article> searchArticlesByDescriptionAndBrand(@Param("x") String ka, @Param("y") String kb);

	@Query("select A from Article A join A.category C order by C.name desc")
	public List<Article> findArticlesCatNamesOrderByDesc();

	@Query("select A from Article A join A.category C order by C.name Asc")
	public List<Article> findArticlesCatNamesOrderByAsc();

	@Modifying
	@Transactional // obligatoire pour les requetes DML (Data Manipulation Language)
	@Query("update Article A set A.brand = :a, A.description = :b, A.price = :c, A.category = :d where A.id = :id")
	public void updateArticleById(@Param("id") Long id, @Param("a") String brand, @Param("b") String description,
			@Param("c") Double price, @Param("d") Category category);
}
