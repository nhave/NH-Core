package com.nhave.nhc.registry;

import com.nhave.nhc.Reference;
import com.nhave.nhc.blocks.BlockDisplay;
import com.nhave.nhc.blocks.BlockToolStation;
import com.nhave.nhc.client.render.RenderTileDisplay;
import com.nhave.nhc.client.render.RenderTileToolStation;
import com.nhave.nhc.itemblocks.ItemBlockBase;
import com.nhave.nhc.tiles.TileEntityDisplay;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks
{
	public static Block blockToolStation;
	public static Block blockDisplay;
	
	public static void init()
	{
		blockToolStation = new BlockToolStation("toolstation");
		blockDisplay = new BlockDisplay("display");

		GameRegistry.registerTileEntity(com.nhave.nhc.tiles.TileEntityToolStation.class, "TileToolStation");
		GameRegistry.registerTileEntity(com.nhave.nhc.tiles.TileEntityDisplay.class, "TileDisplay");
	}
	
	public static void register()
	{
		registerBlock(blockToolStation);
		registerBlock(blockDisplay);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		registerRender(blockToolStation);
		registerRender(blockDisplay);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityToolStation.class, new RenderTileToolStation());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplay.class, new RenderTileDisplay());
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

	@SideOnly(Side.CLIENT)
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
	}
}