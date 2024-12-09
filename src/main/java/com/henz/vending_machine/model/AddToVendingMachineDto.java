package com.henz.vending_machine.model;

import java.util.Objects;
import jakarta.validation.constraints.NotNull;

public class AddToVendingMachineDto {
	@NotNull private int lane;
	@NotNull private String productKey;
	@NotNull private int amount;
	
	public AddToVendingMachineDto(int lane, String productKey, int amount) {
		this.lane = lane;
		this.productKey = productKey;
		this.amount = amount;
	}

	public AddToVendingMachineDto() {}

	public int getLane() {
		return lane;
	}

	public void setLane(int lane) {
		this.lane = lane;
	}

	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(lane, productKey);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddToVendingMachineDto other = (AddToVendingMachineDto) obj;
		return lane == other.lane && Objects.equals(productKey, other.productKey);
	}
}
