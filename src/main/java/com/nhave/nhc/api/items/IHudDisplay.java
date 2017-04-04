package com.nhave.nhc.api.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHudDisplay
{
	@SideOnly(Side.CLIENT)
	public boolean isHudActive(ItemStack stack);
}