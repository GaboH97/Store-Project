package model.dao;

import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;
import javax.swing.JButton;
import model.entity.Administrator;
import model.entity.Category;
import model.entity.Product;
import model.entity.User;
import models.exceptions.EmptyQueryWithGivenParametersException;
import myExeption.ExceptionVerifyId;
import myExeption.ProductNotFoundException;

public class Store {

	private ArrayList<Product> productsList;
	private double income;
	private double expense;
	private ArrayList<Administrator> administrators;
	private ArrayList<User> users;
	private transient int pageCount;
	private transient int page;

	public Store() {
		productsList = new ArrayList<>();
		income = 10000;
		expense = 0;
		pageCount = 0;
	}

	public static Product createProduct(String name, double factoryPrice, Category category, String description,
			int quantity, BufferedImage illustration) {
		return new Product(name, factoryPrice, category, description, quantity, illustration);

	}

	public void addProduct(Product product) throws ExceptionVerifyId {
		if (verifyId(product.getId()) == -1) {
			if (productsList.contains(product)) {
				productsList.get(verifyId(product.getId())).updateQuantity(product.getQuantity());
			} else {
				productsList.add(product);
			}
		} else {
			throw new ExceptionVerifyId();
		}
	}

	public int verifyId(int id) {
		for (int i = 0; i < productsList.size(); i++) {
			if (productsList.get(i).getId() == id) {
				return i;
			}
		}
		return -1;
	}

	public int changeViewPage(JButton source) throws FontFormatException, IOException {
		if (source.getName() == "back") {
			if (page <= 0) {
				page = pageCount;
			} else {
				page--;
			}
		} else if (source.getName() == "forward") {

			if (page >= pageCount) {
				page = 0;
			} else {
				page++;
			}
		}
		return page;
	}

	public ArrayList<Product> displayComponents(int auxPage) {
		return new ArrayList<>(
				(((auxPage + 1) * 16 > productsList.size()) ? productsList.subList(auxPage * 16, productsList.size())
						: productsList.subList(16 * auxPage, 16 * (auxPage + 1))));
	}

	public ArrayList<Product> getCurrentProducts() {
		return productsList;
	}

	public boolean deleteProduct(int id) {
		for (Product product : productsList) {
			if (product.getId() == id) {
				productsList.remove(product);
				return true;
			}
		}
		return false;
	}

	public void editProduct(Product product) throws ExceptionVerifyId {
		if (verifyId(product.getId()) != -1) {
			productsList.set(verifyId(product.getId()), product);
		} else {
			throw new ExceptionVerifyId();
		}

	}

	public ArrayList<Product> applyFilters(ArrayList<String> parameters) throws EmptyQueryWithGivenParametersException {

		/*
		 * PARAMETERS APPLIED: NAME, PRICE RANGE AND CATEGORY. EMPTY
		 * PARARAMETERS ARE DISMISSED
		 */

		System.out.println(parameters);
		int aCase;

		if (parameters.get(0) == "Empty" && parameters.get(1) == "Empty" && parameters.get(2) == "Empty"
				&& parameters.get(3) == "Empty") {
			System.out.println(0);
			aCase = 0;
		} else if (parameters.get(0) == "Empty" && parameters.get(1) == "Empty" && parameters.get(2) == "Empty"
				&& parameters.get(3) != "Empty") {
			System.out.println(1);
			aCase = 1;
		} else if (parameters.get(0) == "Empty" && parameters.get(1) != "Empty" && parameters.get(2) != "Empty"
				&& parameters.get(3) != "Empty") {
			System.out.println(2);
			aCase = 2;
		} else if (parameters.get(0) != "Empty" && parameters.get(1) != "Empty" && parameters.get(2) != "Empty"
				&& parameters.get(3) != "Empty") {
			System.out.println(3);
			aCase = 3;
		} else if (parameters.get(0) != "Empty" && parameters.get(1) != "Empty" && parameters.get(2) != "Empty"
				&& parameters.get(3) == "Empty") {
			System.out.println(4);
			aCase = 4;
		} else if (parameters.get(0) != "Empty" && parameters.get(1) == "Empty" && parameters.get(2) == "Empty"
				&& parameters.get(3) == "Empty") {
			System.out.println(5);
			aCase = 5;
		} else if (parameters.get(0) == "Empty" && parameters.get(1) != "Empty" && parameters.get(2) != "Empty"
				&& parameters.get(3) == "Empty") {
			System.out.println(6);
			aCase = 6;
		} else {
			System.out.println(7);
			aCase = 7;
		}

		ArrayList<Product> filteredProducts = new ArrayList<Product>();
		for (Product product : productsList) {
			switch (aCase) {
			case 0:
				filteredProducts.addAll(productsList);
				break;
			case 1:
				if (Objects.equals(product.getCategory2(), Category.valueOf(parameters.get(3).toUpperCase()))) {
					System.out.println("Ya penetró");
					filteredProducts.add(product);
				}
				break;
			case 2:
				if ((product.getStorePrice() >= Double.parseDouble(parameters.get(1)))
						&& (product.getStorePrice() >= Double.parseDouble(parameters.get(2))) && (Objects
								.equals(product.getCategory2(), Category.valueOf(parameters.get(3).toUpperCase())))) {
					filteredProducts.add(product);
				}
				break;
			case 3:
				if ((product.getName().equalsIgnoreCase(parameters.get(0).toLowerCase()))
						&& (product.getStorePrice() >= Double.parseDouble(parameters.get(1)))
						&& (product.getStorePrice() >= Double.parseDouble(parameters.get(2))) && (Objects
								.equals(product.getCategory2(), Category.valueOf(parameters.get(3).toUpperCase())))) {
					filteredProducts.add(product);
				}
				break;
			case 4:
				if ((product.getName().equalsIgnoreCase(parameters.get(0).toLowerCase()))
						&& (product.getStorePrice() >= Double.parseDouble(parameters.get(1)))
						&& (product.getStorePrice() >= Double.parseDouble(parameters.get(2)))) {
					filteredProducts.add(product);
				}
				break;
			case 5:
				if (product.getName().equalsIgnoreCase(parameters.get(0).toLowerCase())) {
					filteredProducts.add(product);
				}
				break;
			case 6:
				if ((product.getStorePrice() >= Double.parseDouble(parameters.get(1)))
						&& (product.getStorePrice() >= Double.parseDouble(parameters.get(2)))) {
					filteredProducts.add(product);
				}
				break;
			default:
				break;
			}
		}

		System.out.println(filteredProducts);
		if (filteredProducts.isEmpty()) {
			throw new EmptyQueryWithGivenParametersException();
		} else {

			return filteredProducts;
		}
	}

	public Product searchProduct(int id) throws ProductNotFoundException {
		for (Product product : productsList) {
			if (product.getId() == id) {
				return product;
			}
		}
		throw new ProductNotFoundException();
	}

	public TreeMap<String, Integer> getCategoriesFrequencies() {
		TreeMap<String, Integer> categoriesFrequencies = new TreeMap<>();
		for (Category category : Category.values()) {
			int quantity = 0;
			for (Product product : productsList) {
				if (Objects.equals(product.getCategory2(), category)) {
					quantity++;
				}
			}
			if (quantity > 0) {
				categoriesFrequencies.put(category.toString(), quantity);
			}

		}
		return categoriesFrequencies;
	}
}
