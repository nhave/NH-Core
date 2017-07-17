package com.nhave.nhc.client.mesh;

import com.nhave.nhc.Reference;
import com.nhave.nhc.items.ItemToken;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CustomMeshDefinitionToken implements ItemMeshDefinition
{
	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack)
	{
		boolean active = ((ItemToken) stack.getItem()).isActive(stack);
		return new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":" + stack.getItem().getRegistryName().getResourcePath() + (active ? "_active" : "")), "inventory");
	}
}
