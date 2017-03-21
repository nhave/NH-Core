package com.nhave.nhc.api.items;

import net.minecraft.item.ItemStack;

public interface IItemShader
{
	public Object getShaderData(ItemStack shader, ItemStack Owner, String tag);
	
	public boolean canApplyTo(ItemStack shader, ItemStack shadeable);
}