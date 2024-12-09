package com.henz.vending_machine.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henz.vending_machine.model.AddToVendingMachineDto;
import com.henz.vending_machine.model.MachineInteraction;
import com.henz.vending_machine.model.Product;
import com.henz.vending_machine.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductResource {

	private final ProductService productService;
	
	public ProductResource(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/all/created")
	public ResponseEntity<String> getProducts() {
		return new ResponseEntity<>(this.productService.getAllCreatedProducts(), HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createProduct(@Valid @RequestBody Product product) {
		String response = this.productService.createProduct(product);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateProduct(@Valid @RequestBody Product product) {
		String response = this.productService.updateProduct(product);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("delete/{key}")
	public ResponseEntity<String> deleteProduct(@PathVariable String key) {
		String response = this.productService.deleteProduct(key);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/all/vendingMachine")
	public ResponseEntity<String> getProductsInVendingMachine() {
		return new ResponseEntity<>(this.productService.getAllProducts(), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addToVendingMachine(@Valid @RequestBody AddToVendingMachineDto dto) {
		String response = this.productService.addProduct(dto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/coinslot")
	public ResponseEntity<String> interactWithMachine(@RequestBody MachineInteraction interaction) {
		String response = this.productService.interactWithMachine(interaction);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/coinslot")
	public ResponseEntity<String> getCurrentBalance() {
		String response = this.productService.getCurrentBalance();
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
