package fr.fms.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

@Service
public class IShopJPAImpl implements IShopJPA {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public List<Article> displayAllArticles() {
		List<Article> articles = articleRepository.findAll();
		return articles;

	}

	@Override
	public boolean createArticle(String articleBrand, String articleDescription, Double articlePrice,
			Category choicedCategory) {

		try {
			articleRepository.save(new Article(articleBrand, articleDescription, articlePrice, choicedCategory));
			return true;

		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public Article readArticle(Long articleId) {
		Optional<Article> OptionalArticle = articleRepository.findById(articleId);

		if (OptionalArticle.isPresent()) {
			return OptionalArticle.get();
		} else {
			return null;
		}
	}

	@Override
	public boolean updateArticle(Long articleId, String articleBrand, String articleDescription, Double articlePrice,
			Category choicedCategory) {

		try {
			articleRepository.updateArticleById(articleId, articleBrand, articleDescription, articlePrice,
					choicedCategory);

			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean deleteArticle(Long articleId) {

		if (articleRepository.existsById(articleId)) {
			articleRepository.deleteById(articleId);
			if (!articleRepository.existsById(articleId)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public Category getCategory(Long catId) {
		Optional<Category> optionalCategory = categoryRepository.findById(catId);

		if (optionalCategory.isPresent()) {
			return optionalCategory.get();
		}
		return null;
	}

	@Override
	public List<Category> displayAllCategorys() {
		List<Category> category = categoryRepository.findAll();
		return category;
	}

	@Override
	public boolean createCategory(String categoryName) {

		if (!categoryRepository.existsByName(categoryName)) {

			try {
				categoryRepository.save(new Category(categoryName));

				return true;

			} catch (Exception e) {
				System.out.println("Erreur avec la creation de categorie : " + e.getMessage());
				return false;
			}
		}
		return false;

	}

	@Override
	public boolean deleteCategory(Long categoryId) {

		if (categoryRepository.existsById(categoryId)) {
			categoryRepository.deleteById(categoryId);
			if (!categoryRepository.existsById(categoryId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean updateCategory(Long categoryId, String categoryName) {
		try {
			categoryRepository.updateCategoryById(categoryId, categoryName);
			return true;
		} catch (Exception e) {
			System.out.println("Erreur update category : " + e.getMessage());
			return false;
		}
	}

	@Override
	public List<Article> getArticlesByCategoryName(String categoryName) {
		return articleRepository.findByCategoryName(categoryName);
	}

	@Override
	public Page<Article> getArticlesByPage(int numberOfPages, int numberOfArticlesPerPage) {
		PageRequest pageRequest = PageRequest.of(numberOfPages, numberOfArticlesPerPage, Sort.by("id").ascending());
		return articleRepository.findAll(pageRequest);
	}

}
