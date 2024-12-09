package com.henz.vending_machine.service;

import org.springframework.stereotype.Service;

import com.henz.vending_machine.model.AddToVendingMachineDto;
import com.henz.vending_machine.model.MachineInteraction;
import com.henz.vending_machine.model.Product;
import com.henz.vending_machine.repository.ProductRepository;

@Service
public class ProductService {
	
	private ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public String createProduct(Product product) {
		return this.productRepository.createProduct(product);
	}
	
	public String deleteProduct(String key) {
		return this.productRepository.deleteProductByKey(key);
	}
	
	public String addProduct(AddToVendingMachineDto dto) {
		return this.productRepository.add(dto);
	}
	
	public String getAllCreatedProducts() {
		return this.productRepository.getProducts();
	}
	
	public String getAllProducts() {
		return this.productRepository.getVendingMachineInventory();
	}
	
	public String updateProduct(Product product) {
		return this.productRepository.updateProduct(product);
	}
	
	public String interactWithMachine(MachineInteraction interaction) {
		return this.productRepository.interactWithMachine(interaction);
	}

	public String getCurrentBalance() {
		return this.productRepository.getCurrentBalance();
	}
}
