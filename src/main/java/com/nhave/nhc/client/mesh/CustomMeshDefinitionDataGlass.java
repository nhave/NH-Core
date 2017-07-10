package com.nhave.nhc.client.mesh;

import com.nhave.nhc.Reference;

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
		return new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":" + textureName), "inventory");
	}
}
