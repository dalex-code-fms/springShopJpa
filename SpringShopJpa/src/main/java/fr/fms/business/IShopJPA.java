package fr.fms.business;

import java.util.List;

import org.springframework.data.domain.Page;

import fr.fms.entities.Article;
import fr.fms.entities.Category;

public interface IShopJPA {
	public List<Article> displayAllArticles();

	public boolean createArticle(String articleBrand, String articleDescription, Double articlePrice,
			Category choicedCategory);

	public Article readArticle(Long articleId);

	public boolean updateArticle(Long articleId, String articleBrand, String articleDescription, Double articlePrice,
			Category choicedCategory);

	public boolean deleteArticle(Long articleId);

	public List<Category> displayAllCategorys();

	public Category getCategory(Long catId);

	public boolean createCategory(String categoryName);

	public boolean deleteCategory(Long categoryId);

	public boolean updateCategory(Long categoryId, String categoryName);

	public List<Article> getArticlesByCategoryName(String categoryName);

	public Page<Article> getArticlesByPage(int numberOfPages, int numberOfArticlesPerPage);
}
