package com.nhave.nhc.client.widget;

import net.minecraft.item.ItemStack;

public abstract class WidgetBase
{
	public abstract int getSizeX(ItemStack stack);
	
	public abstract int getSizeY(ItemStack stack);
	
	public abstract void drawWidget(ItemStack stack, int x, int y);
	
	public abstract boolean shouldDraw(ItemStack stack);
}