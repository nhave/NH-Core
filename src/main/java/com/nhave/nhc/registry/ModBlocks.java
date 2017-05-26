package com.nhave.nhc.registry;

import com.nhave.nhc.Reference;
import com.nhave.nhc.blocks.BlockToolStation;
import com.nhave.nhc.client.render.RenderTileToolStation;
import com.nhave.nhc.itemblocks.ItemBlockBase;
import com.nhave.nhc.tiles.TileEntityToolStation;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
	public static Block blockToolStation;
	
	public static void init()
	{
		blockToolStation = new BlockToolStation("toolstation");
		
		GameRegistry.registerTileEntity(com.nhave.nhc.tiles.TileEntityToolStation.class, "TileToolStation");
	}
	
	public static void register()
	{
		registerBlock(blockToolStation);
	}
	
	public static void registerRenders()
	{
		registerRender(blockToolStation);
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityToolStation.class, new RenderTileToolStation());
	}
	
	public static void registerBlock(Block block)
	{
		registerBlock(block, new ItemBlockBase(block, StringUtils.LIGHT_BLUE));
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