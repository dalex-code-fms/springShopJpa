package fr.fms.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.fms.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByName(String name);

	@Modifying
	@Transactional // obligatoire pour les requetes DML (Data Manipulation Language)
	@Query("update Category A set A.name = :a where A.id = :id")
	public void updateCategoryById(@Param("id") Long id, @Param("a") String brand);
}
