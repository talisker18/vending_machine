package com.henz.vending_machine.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.henz.vending_machine.model.AddToVendingMachineDto;
import com.henz.vending_machine.model.MachineInteraction;
import com.henz.vending_machine.model.MachineInteraction.MachineAction;
import com.henz.vending_machine.model.Product;

@Repository
public class ProductRepository {
	
	private Map<String, Product> productMap = new HashMap<>();
	private Map<AddToVendingMachineDto, Integer> lanes = new HashMap<>();
	
	public final static int PRODUCT_CAPACITY_PER_LANE = 10;
	public final static int VENDING_MACHINE_MAX_LANES = 20;
	private final static String AMOUNT_IS_TOO_BIG = "Amount is too big. Decrease the amount!";
	
	private List<MachineInteraction> currentAction = new ArrayList<>();
	private final List<Integer> allowedCoinsInRappen = new ArrayList<>();
	
	{
		allowedCoinsInRappen.add(10);
		allowedCoinsInRappen.add(20);
		allowedCoinsInRappen.add(50);
		allowedCoinsInRappen.add(100);
		allowedCoinsInRappen.add(200);
	}
	
	public String createProduct(Product newProduct) {
		Product wasAdded = this.productMap.putIfAbsent(newProduct.getProductKey(), newProduct);
		
		if(wasAdded == null) {
			return "Created new product: " + newProduct.toString();
		} else {
			return "Product was already created!";
		}
	}
	
	public String getProducts() {
		StringBuilder builder = new StringBuilder("Printing created products: \n");
		
		for (Map.Entry<String, Product> entry : productMap.entrySet()) {
		    builder.append("- Product: " + entry.getValue() + "\n");
		}
		
		return this.productMap.toString();
	}
	
	public String updateProduct(Product product) {
		Product p = this.productMap.get(product.getProductKey());
		
		if(p == null) {
			return "Product not found! Create the product first";
		} else {
			this.productMap.put(product.getProductKey(), product);
			return "Product updated";
		}
	}	
	
	public String deleteProductByKey(String key) {
		Product p = this.productMap.remove(key);
		
		if(p == null) {
			return "Product not found!";
		} else {
			return "Product removed";
		}
	}
	
	public String add(AddToVendingMachineDto dto) {
		String response = "";
		
		Product p = this.productMap.get(dto.getProductKey());
		
		if(p == null) {
			response = "Product does not exist. You have to create it first before adding to the vending machine";
		} else {
			if(dto.getLane() >= 1 && dto.getLane() <= VENDING_MACHINE_MAX_LANES) {
				Integer currentNumber = lanes.get(dto);
				
				if(currentNumber == null) {
					
					boolean isAlreadyAssigned = this.checkIfLaneIsAssigned(dto);
					
					if(isAlreadyAssigned) {
						response = "Lane already assigned to other product";
					} else {
						if(dto.getAmount() <= PRODUCT_CAPACITY_PER_LANE) {
							response = "Added successfully " + dto.getAmount() + " " + dto.getProductKey() + " to lane " + dto.getLane();
							lanes.put(dto, dto.getAmount());
						} else {
							response = AMOUNT_IS_TOO_BIG;
						}
					}
				} else {
					if(currentNumber + dto.getAmount() <= PRODUCT_CAPACITY_PER_LANE) {
						response = "Added successfully " + dto.getAmount() + " " + dto.getProductKey() + " to lane " + dto.getLane();
						lanes.put(dto, currentNumber + dto.getAmount());
					} else {
						response = AMOUNT_IS_TOO_BIG;
					}
				}
			} else {
				response = "Invalid lane!";
			}
		}
		
		return response;
	}
	
	private boolean checkIfLaneIsAssigned(AddToVendingMachineDto dto) {
		//check if lane is already assigned to another productKey
		boolean isAlreadyAssigned = false;
		
		for (Map.Entry<AddToVendingMachineDto, Integer> entry : lanes.entrySet()) {
			
			AddToVendingMachineDto current = entry.getKey();
			
		    if(dto.getLane() == current.getLane() && !dto.getProductKey().equals(current.getProductKey())) {
		    	isAlreadyAssigned = true;
		    }
		}
		
		return isAlreadyAssigned;
	}
	
	public String getVendingMachineInventory() {
		StringBuilder builder = new StringBuilder("Get all products of vending machine:\n");
		
		for (Map.Entry<AddToVendingMachineDto, Integer> entry : lanes.entrySet()) {
			AddToVendingMachineDto dto = entry.getKey();
			Product p = this.productMap.get(dto.getProductKey());
			
		    builder.append("- Lane " + dto.getLane() + ": " + p.getProductKey() + " ("+p.getProductDescription()+") for a price of " 
		    + p.getProductPrice() + " per unit; current amount " + entry.getValue() + "\n");
		}
		
		return builder.toString();
	}

	public String interactWithMachine(MachineInteraction interaction) {
		String response = "";
		
		switch(interaction.getAction()) {
			case BUY:
				if(this.currentAction.isEmpty()) {
					response = "Provide first some coins!";
				} else {
					int lane = interaction.getLane();
					
					AddToVendingMachineDto entryToUpdate = null;
					int newNumber = 0;
					
					for (Map.Entry<AddToVendingMachineDto, Integer> entry : this.lanes.entrySet()) {
						AddToVendingMachineDto dto = entry.getKey();
						int availableNumber = entry.getValue();
						
						if(dto.getLane() == lane) {
							Product p = this.productMap.get(dto.getProductKey());
							
							//get all interactions of type ADD_COIN and sum up all added coins
							int coinsAdded = this.currentAction.stream()
									.filter(e -> e.getAction().equals(MachineAction.ADD_COIN))
									.mapToInt(e -> e.getCoin_in_rappen())
									.sum();
							
							if(availableNumber > 0 && ((p.getProductPrice() * 100) <= coinsAdded)) {
								response = "Thanks for your buy. Have a nice day!";
								this.currentAction.clear();
								newNumber = availableNumber - 1;
								entryToUpdate = dto;
							} else {
								response = "Not enough added coins or empty slot";
							}
							
							break;
						}
					}
					
					if(entryToUpdate != null) {
						this.lanes.put(entryToUpdate, newNumber);
					} else {
						response = "Not enough added coins or empty slot";
					}
				}
				
				break;
			case ADD_COIN:
				if(this.allowedCoinsInRappen.contains(interaction.getCoin_in_rappen())) {
					this.currentAction.add(interaction);
					response = "Successfully added " + interaction.getCoin_in_rappen() + " rappen";
				} else {
					response = "Only following coins allowed (in rappen): " + this.allowedCoinsInRappen.toString();
				}
				
				break;
			case ABORT:
				int coinsAdded = this.currentAction.stream()
					.filter(e -> e.getAction().equals(MachineAction.ADD_COIN))
					.mapToInt(e -> e.getCoin_in_rappen())
					.sum();
				
				this.currentAction.clear();
				response = "Aborted interaction. You may take your money back ("+coinsAdded+" rappen)";
				break;
		}
		
		return response;
	}

	public String getCurrentBalance() {
		int coinsAdded = this.currentAction.stream()
				.filter(e -> e.getAction().equals(MachineAction.ADD_COIN))
				.mapToInt(e -> e.getCoin_in_rappen())
				.sum();
		
		return "Your balance is: " + coinsAdded;
	}
}
