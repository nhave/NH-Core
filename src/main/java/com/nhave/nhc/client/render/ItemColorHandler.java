package com.nhave.nhc.client.render;

import com.nhave.nhc.items.ItemChroma;
import com.nhave.nhc.registry.ModItems;
import com.nhave.nhc.shaders.ShaderManager;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemColorHandler implements IItemColor
{
	public static final ItemColorHandler INSTANCE = new ItemColorHandler();
	
	@Override
	public int getColorFromItemstack(ItemStack stack, int pass)
	{
		if (stack.getItem() == ModItems.itemDataGlass)
		{
			return pass == 1 ? ShaderManager.getChroma(stack).getColor() : 16777215;
		}
		else if (stack.getItem() instanceof ItemChroma)
		{
			ItemChroma chroma = (ItemChroma) stack.getItem();
			return (chroma.getChroma(stack) != null && pass == 1) ? chroma.getChroma(stack).getColor() : 16777215;
		}
		else return 16777215;
	}
}