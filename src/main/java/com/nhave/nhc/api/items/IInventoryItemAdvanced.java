package com.nhave.nhc.api.items;

import net.minecraft.item.ItemStack;

public interface IInventoryItemAdvanced extends IInventoryItem
{
	public int getCountInSlot(ItemStack stack, int slot);
	
	public boolean renderStackOverlay(ItemStack stack, int slot);
}