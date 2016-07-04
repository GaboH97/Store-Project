package model.entity;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Product {

	private static final double TAX = 0.20;
	private static int BASE_ID = 0;
	private int id;
	private String name;
	private double factoryPrice;
	private Category category;
	private String description;
	private boolean isInStock;
	private int quantity;
	private short quality;
	private String ImagePath;
	private transient BufferedImage illustration;
	private ArrayList<Double> rates;

	public Product(String name, double factoryPrice, Category category, String description, int quantity,
			BufferedImage illustration) {
		this.id = BASE_ID++;
		this.name = name;
		this.factoryPrice = factoryPrice;
		this.category = category;
		this.description = description;
		this.isInStock = true;
		this.quantity = quantity;
		this.illustration = illustration;
		rates = new ArrayList<>();
	}

	public Product(int id, String name, double factoryPrice, Category category, String description, int quantity, String ImagePath) {
		this.id = id;
		this.name = name;
		this.factoryPrice = factoryPrice;
		this.category = category;
		this.description = description;
		this.isInStock = true;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public double getQuantity() {
		return quantity;
	}

	public void updateQuantity(double quantity2) {
		quantity += quantity2;
	}

	public Object[] fieldsForColumns() throws IOException {
		return new Object[] { id, name, factoryPrice, category.toString(),
				(isInStock) ? coverToBytes(ImageIO.read(getClass().getResourceAsStream("/img/InStock.png")))
						: coverToBytes(ImageIO.read(getClass().getResourceAsStream("/img/OutOfStock.png"))),
				quantity };
	}

	private byte[] coverToBytes(BufferedImage image) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}

	public String getName() {
		return name;
	}

	public Image getIllustration() {
		return illustration;
	}

	public boolean isInStock() {
		return isInStock;
	}

	public double getStorePrice() {
		return factoryPrice + (factoryPrice * TAX);
	}

	public String getCategory() {
		return category.toString();
	}
	
	public Category getCategory2(){
		return category;
	}

	public double getRates() {
		double averageRate = 0;
		for (Double rate : rates) {
			averageRate += rate;
		}
		return new BigDecimal((averageRate / rates.size())).setScale(2, RoundingMode.CEILING).doubleValue();
	}

	public void checkAvailability() {
		isInStock = (quantity >= 1) ? true : false;
	}
	
	@Override
	public String toString() {
		return name+"%"+id+"&&&&&"+category.toString();
	}

	public String getDescription() {
		return description;
	}

}
