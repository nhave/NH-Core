package com.nhave.nhc.registry;

import com.nhave.nhc.Reference;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
	public static void init() {}
	
	public static void register() {}
	
	public static void registerRenders() {}
	
	public static void registerBlock(Block block)
	{
		registerBlock(block, new ItemBlock(block));
	}
	
	public static void registerBlock(Block block, ItemBlock itemBlock)
	{
		GameRegistry.register(block);
		GameRegistry.register(itemBlock, block.getRegistryName());
	}
	
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
	}
}