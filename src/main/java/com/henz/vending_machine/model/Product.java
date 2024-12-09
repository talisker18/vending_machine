package com.henz.vending_machine.model;

import java.util.Objects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Product {
	
	private enum ProductType {
		BEVERAGE,SNACK,OTHER
	}
	
	@NotEmpty private String productKey;
	@NotNull private String productDescription;
	@NotNull private double productPrice;
	@NotNull private ProductType productType;
	
	public Product() {}
	
	public Product(String productKey, String productDescription, double productPrice, ProductType productType) {
		super();
		this.productKey = productKey;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.productType = productType;
	}

	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	@Override
	public String toString() {
		return "Product [productKey=" + productKey + ", productDescription=" + productDescription + ", productPrice="
				+ productPrice + ", productType=" + productType + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(productDescription, productKey, productPrice, productType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(productDescription, other.productDescription) && productKey == other.productKey
				&& Double.doubleToLongBits(productPrice) == Double.doubleToLongBits(other.productPrice)
				&& productType == other.productType;
	}
}
