package com.henz.vending_machine.model;

import jakarta.validation.constraints.NotNull;

public class MachineInteraction {
	
	public enum MachineAction {
		ADD_COIN, BUY, ABORT
	}
	
	@NotNull private MachineAction action;
	@NotNull private int coin_in_rappen;
	@NotNull private int lane;
	
	public MachineInteraction(MachineAction action, int coin_in_rappen, int lane) {
		super();
		this.action = action;
		this.coin_in_rappen = coin_in_rappen;
		this.lane = lane;
	}
	
	public MachineInteraction() {}

	public MachineAction getAction() {
		return action;
	}
	
	public void setAction(MachineAction action) {
		this.action = action;
	}
	public int getCoin_in_rappen() {
		return coin_in_rappen;
	}
	public void setCoin_in_rappen(int coin_in_rappen) {
		this.coin_in_rappen = coin_in_rappen;
	}
	public int getLane() {
		return lane;
	}
	public void setLane(int lane) {
		this.lane = lane;
	}
}
