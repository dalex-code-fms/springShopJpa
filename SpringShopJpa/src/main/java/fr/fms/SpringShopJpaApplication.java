package fr.fms;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;

import fr.fms.business.IShopJPAImpl;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

@SpringBootApplication
public class SpringShopJpaApplication implements CommandLineRunner {

	final String RESET = "\u001B[0m";
	final String RED = "\u001B[31m";
	final String GREEN = "\u001B[32m";
	final String YELLOW = "\u001B[33m";
	final String BLUE = "\u001B[34m";

	@Autowired
	private IShopJPAImpl job;

	private Scanner scanner;
	private Integer userMenuChoice;

	public static void main(String[] args) {
		SpringApplication.run(SpringShopJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		scanner = new Scanner(System.in);
		userMenuChoice = 0;
		displayPrincipalMenu();

	}

	public void displayArticlesByCategory() {
		System.out.println("Saissisez le nom de la categorie : ");
		String categoryName = scanner.nextLine();

		for (Article article : job.getArticlesByCategoryName(categoryName)) {
			System.out.println(article);
		}

		handleGoBackOrExit();
	}

	public void handleModifyCategory() {
		System.out.println("**********************************************");
		System.out.println("PAGE DE MODIFICATION DE CATEGORIE");

		System.out.println("Saissisez l'id de la categorie à modifier : ");

		Long categoryId = scanner.nextLong();
		scanner.nextLine();

		Category category = job.getCategory(categoryId);

		if (category != null) {
			System.out.println("Entrez le nouveau nom : ");
			String newCategoryName = scanner.nextLine();

			if (job.updateCategory(category.getId(), newCategoryName)) {
				System.out.println("Categorie modifié.");
			}

		} else {
			System.out.println("Cette categorie n'hexiste pas.");
		}

		handleGoBackOrExit();
	}

	public void handleDeleteCategory() {
		System.out.println("Saissisez la categorie à supprimer : ");

		Long categorieId = scanner.nextLong();
		scanner.nextLine();

		if (job.deleteCategory(categorieId)) {

			System.out.println("La categorie à bien ete supprimé !");
		} else {
			System.out.println("Cette categorie n'hexiste pas.");
		}

		handleGoBackOrExit();
	}

	public void handleReadCategory() {
		System.out.println("**********************************************");
		System.out.println("Saissisez l'id de la categorie à afficher : ");

		Long categorieId = scanner.nextLong();
		scanner.nextLine();

		Category choicedCategory = job.getCategory(categorieId);

		if (choicedCategory != null) {
			System.out.println(choicedCategory);
		} else {
			System.out.println("Cette Categorie n'hexiste pas.");
		}

		handleGoBackOrExit();
	}

	public void handleCreateCategory() {
		System.out.println("**********************************************");
		System.out.println("PAGE DE CREATION DE CATEGORIE");

		System.out.print("Entrez le nom de la catgorie : ");
		String categoryName = scanner.nextLine();

		boolean categoryCreated = job.createCategory(categoryName);

		if (categoryCreated) {
			System.out.println("Categorie ajouté avec success !");
		} else {
			System.out.println("Cette categorie exist deja ");
		}

		handleGoBackOrExit();

	}

	public void handleModifyArticle() {
		System.out.println("**********************************************");
		System.out.println("PAGE DE MODIFICATION D'ARTICLE");

		System.out.println("Saissisez l'id de l'article à modifier : ");

		Long articleId = scanner.nextLong();
		scanner.nextLine();

		Article article = job.readArticle(articleId);

		if (article != null) {

			System.out.print("Modifier la 'brand' de l'article : ");
			String articleBrand = scanner.nextLine();
			System.out.print("Modifier la 'description' de l'article : ");
			String articleDescription = scanner.nextLine();
			System.out.print("Modifier le 'price' de l'article : ");
			Double articlePrice = scanner.nextDouble();

			System.out.println("Modifier la categorie en saissisant son numero :");

			for (Category cat : job.displayAllCategorys()) {
				System.out.println(String.format("%d : %s", cat.getId(), cat.getName()));
			}

			Long choicedCatID = scanner.nextLong();
			scanner.nextLine();

			Category choicedCategory = job.getCategory(choicedCatID);

			if (choicedCategory != null) {

				boolean updated = job.updateArticle(articleId, articleBrand, articleDescription, articlePrice,
						choicedCategory);
				if (updated) {

					System.out.println("L'article à bien ete modifie !");

				} else {
					System.out.println("Erreur en modifiant l'article ");
				}
			}

			else {
				System.out.println("Categorie inexistante !");
			}

		} else {
			System.out.println("Cette article est inconu");
		}

		handleGoBackOrExit();

	}

	public void handleDeleteArticle() {
		System.out.println("Saissisez l'article à supprimer : ");

		Long articleId = scanner.nextLong();
		scanner.nextLine();

		if (job.deleteArticle(articleId)) {

			System.out.println("L'article à bien ete supprimé !");
		} else {
			System.out.println("Cette article n'hexiste pas.");
		}

		handleGoBackOrExit();
	}

	public void displayArticle() {
		System.out.println("**********************************************");
		System.out.println("Saissisez l'article à afficher : ");

		Long articleId = scanner.nextLong();
		scanner.nextLine();

		Article article = job.readArticle(articleId);

		if (article != null) {
			System.out.println(article);
		} else {
			System.out.println("Article non disponible.");
		}

		handleGoBackOrExit();

	}

	public void handleCreateArticle() {

		System.out.println("**********************************************");
		System.out.println("PAGE DE CREATION D'ARTICLE");

		System.out.print("Entrez le nom de l'article : ");
		String articleBrand = scanner.nextLine();
		System.out.print("Entrez la description de l'article : ");
		String articleDescription = scanner.nextLine();
		System.out.print("Entrez le prix de l'article : ");
		Double articlePrice = scanner.nextDouble();

		System.out.println("Choissisez une categorie en saissisant son numero :");

		for (Category cat : job.displayAllCategorys()) {
			System.out.println(String.format("%d : %s", cat.getId(), cat.getName()));
		}

		Long choicedCatID = scanner.nextLong();
		scanner.nextLine();
		Category choicedCategory = job.getCategory(choicedCatID);

		if (choicedCategory != null) {

			if (job.createArticle(articleBrand, articleDescription, articlePrice, choicedCategory)) {
				System.out.println("L'article à bien ete crée.");
			} else {
				System.out.println("Erreur avec la creation de l'article : ");
			}

		} else {
			System.out.println("Categorie inexistante !");
		}

		handleGoBackOrExit();

	}

	public void handleGoBackOrExit() {
		System.out.printf("| %34s | %-35s |%n", "REVENIR", "EXIT");
		System.out.printf("+--------------------------------------------------------------------------+%n");
		String goBack = scanner.nextLine();

		if (goBack.equalsIgnoreCase("REVENIR")) {
			displayPrincipalMenu();
		} else if (goBack.equalsIgnoreCase("EXIT")) {
			System.out.println("Bye Bye");
			System.exit(0);
		}
	}

	public void displayWithPagination() {
		System.out.println("Combien d'articles voulez-vous afficher par page? ");
		Integer numberOfArticlesPerPage = scanner.nextInt();
		scanner.nextLine();
		int actualPageNumber = 0;
		boolean havePagesLeft = true;

		while (havePagesLeft) {
			Page<Article> page = job.getArticlesByPage(actualPageNumber, numberOfArticlesPerPage);

			int numberOfPages = page.getTotalPages();

			System.out.printf("%s+--------------------------------------------------------------------------+%n",
					YELLOW);
			System.out.printf("%s| %s%s %-50d%s |%s%n", YELLOW, BLUE, "LISTE D'ARTICLES PAR ", numberOfArticlesPerPage,
					YELLOW, RESET);

			System.out.printf("%s+--------------------------------------------------------------------------+%n",
					YELLOW);
			System.out.printf("| %s%-3s%s | %s%-10s%s | %s%-20s%s | %s%-10s%s | %s%-17s%s |%n", GREEN, "ID", RESET,
					GREEN, "MARQUE", RESET, GREEN, "DESCRIPTION", RESET, GREEN, "PRIX", RESET, GREEN, "CATEGORIE",
					RESET);
			System.out.printf("%s+--------------------------------------------------------------------------+%n",
					YELLOW);

			page.getContent()
					.forEach(article -> System.out.printf("| %s%-3s%s | %-10s | %-20s | %-10s | %-17s |%n", RED,
							article.getId(), RESET, article.getBrand(), article.getDescription(), article.getPrice(),
							article.getCategory()));
			System.out.printf("%s+--------------------------------------------------------------------------+%n",
					YELLOW);
			displayPaginationMenu(numberOfPages, actualPageNumber);

			String userChoice = scanner.nextLine();

			if ("PREV".equalsIgnoreCase(userChoice) && !page.isFirst()) {
				actualPageNumber--;
			} else if ("NEXT".equalsIgnoreCase(userChoice) && !page.isLast()) {
				actualPageNumber++;
			} else if ("EXIT".equalsIgnoreCase(userChoice)) {
				havePagesLeft = false;
				displayPrincipalMenu();
			} else if ("PAGE".equalsIgnoreCase(userChoice)) {
				System.out.println("Veuillez entrer le nombre d'articles par page : ");

				numberOfArticlesPerPage = scanner.nextInt();
				scanner.nextLine();
			}
		}

	}

	public void displayPaginationMenu(int numberOfPages, int actualPageNumber) {
		String menu = "PREV [ ";

		for (int i = 0; i < numberOfPages; i++) {

			if (i > 0) {
				menu += ", ";
			}

			if (i == actualPageNumber) {
				menu += String.format("{ %d } ", (i + 1));
			} else {
				menu += String.format("%d", (i + 1));
			}
		}

		menu += " ] NEXT | PAGE | EXIT";
		System.out.printf("+--------------------------------------------------------------------------+%n");
		System.out.printf("| %-72s |%n", menu);
		System.out.printf("+--------------------------------------------------------------------------+%n");
	}

	public void displayWithoutPagination() {
		System.out.printf("+--------------------------------------------------------------------------+%n");
		System.out.printf("| %-72s |%n", "LISTE D'ARTICLES DISPONIBLES");
		System.out.printf("+--------------------------------------------------------------------------+%n");
		System.out.printf("+--------------------------------------------------------------------------+%n");
		System.out.printf("| %-3s | %-10s | %-20s | %-10s | %-17s |%n", "ID", "MARQUE", "DESCRIPTION", "PRIX",
				"CATEGORIE");
		System.out.printf("+--------------------------------------------------------------------------+%n");

		List<Article> articles = job.displayAllArticles();

		if (articles.isEmpty()) {
			System.out.println("Aucun article disponible.");
		} else {
			for (Article article : articles) {
				System.out.printf("| %-3s | %-10s | %-20s | %-10s | %-17s |%n", article.getId(), article.getBrand(),
						article.getDescription(), article.getPrice(), article.getCategory());
			}
			System.out.printf("+--------------------------------------------------------------------------+%n");
		}
		handleGoBackOrExit();
	}

	public void displayMenu() {
		System.out.printf("+-------------------------------------------------------------+%n");
		System.out.printf("|   %-57s |%n", "Bienvenue dans notre application de gestion d'articles");
		System.out.printf("+-------------------------------------------------------------+%n");
		System.out.printf("|  %-4s| %-52s |%n", "N°", "Option");
		System.out.printf("+-------------------------------------------------------------+%n");
		System.out.printf("|  %-4d| %-52s |%n", 1, "Afficher tous les articles sans pagination");
		System.out.printf("|  %-4d| %-52s |%n", 2, "Afficher tous les articles avec pagination");
		System.out.printf("+-------------------------------------------------------------+%n");
		System.out.printf("|  %-4d| %-52s |%n", 3, "Ajouter un article");
		System.out.printf("|  %-4d| %-52s |%n", 4, "Afficher un article");
		System.out.printf("|  %-4d| %-52s |%n", 5, "Supprimer un article");
		System.out.printf("|  %-4d| %-52s |%n", 6, "Modifier un article");
		System.out.printf("+-------------------------------------------------------------+%n");
		System.out.printf("|  %-4d| %-52s |%n", 7, "Ajouter une catégorie");
		System.out.printf("|  %-4d| %-52s |%n", 8, "Afficher une catégorie");
		System.out.printf("|  %-4d| %-52s |%n", 9, "Supprimer une catégorie");
		System.out.printf("|  %-4d| %-52s |%n", 10, "Modifier une catégorie");
		System.out.printf("|  %-4d| %-52s |%n", 11, "Afficher tous les articles d'une catégorie");
		System.out.printf("+-------------------------------------------------------------+%n");
		System.out.printf("|  %-4d| %-52s |%n", 12, "Sortir du programme");
		System.out.printf("+-------------------------------------------------------------+%n");
	}

	public void displayPrincipalMenu() {

		while (userMenuChoice != 12) {
			displayMenu();
			System.out.printf("+-------------------------------------------------------------+%n");
			System.out.printf("| %-59s |%n", "Veuillez choisir une option en ecrivant son numero :");
			System.out.printf("+-------------------------------------------------------------+%n");

			userMenuChoice = scanner.nextInt();
			scanner.nextLine();

			switch (userMenuChoice) {
			case 1:
				displayWithoutPagination();
				break;
			case 2:
				displayWithPagination();
				break;
			case 3:
				handleCreateArticle();
				break;
			case 4:
				displayArticle();
				break;
			case 5:
				handleDeleteArticle();
				break;
			case 6:
				handleModifyArticle();
				break;
			case 7:
				handleCreateCategory();
				break;
			case 8:
				handleReadCategory();
				break;
			case 9:
				handleDeleteCategory();
				break;
			case 10:
				handleModifyCategory();
				break;
			case 11:
				displayArticlesByCategory();
				break;
			case 12:
				System.out.println("by by");
				break;
			default:
				System.out.println("Option invalide, veuillez reessayer.");
				break;
			}
		}

	}

}
