package com.nhave.nhc.client.mesh;

import com.nhave.nhc.Reference;
import com.nhave.nhc.util.ItemUtil;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CustomMeshDefinitionDataGlass implements ItemMeshDefinition
{
	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack)
	{
		String name = stack.getDisplayName().toLowerCase().trim();
		String textureName = (name.equals("focus") ? "focus" : stack.getItem().getRegistryName().getResourcePath());
		
		ItemStack tokenStack = ItemUtil.getItemFromStack(stack, "TOKEN");
		if (tokenStack != null && !tokenStack.isEmpty()) textureName = (stack.getItem().getRegistryName().getResourcePath() + "_gold");
		
		return new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":" + textureName), "inventory");
	}
}
