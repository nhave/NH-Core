package com.nhave.nhc.api.items;

import net.minecraft.item.ItemStack;

public interface IInventoryItem
{
	public int getInventoryX(ItemStack stack);

	public int getInventoryY(ItemStack stack);
	
	public ItemStack getStackInSlot(ItemStack stack, int slot);
}