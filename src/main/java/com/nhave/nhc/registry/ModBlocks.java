package com.nhave.nhc.registry;

import com.nhave.nhc.Reference;
import com.nhave.nhc.blocks.BlockDisplay;
import com.nhave.nhc.blocks.BlockMachineBase;
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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks
{
	public static Block blockMachineFrame;
	public static Block blockToolStation;
	public static Block blockDisplay;
	
	public static void init()
	{
		blockMachineFrame = new BlockMachineBase("frame");
		blockToolStation = new BlockToolStation("toolstation");
		blockDisplay = new BlockDisplay("display");
		
		GameRegistry.registerTileEntity(com.nhave.nhc.tiles.TileEntityToolStation.class, "TileToolStation");
		GameRegistry.registerTileEntity(com.nhave.nhc.tiles.TileEntityDisplay.class, "TileDisplay");
	}
	
	public static void register(Register<Block> event)
	{
		event.getRegistry().register(blockMachineFrame);
		event.getRegistry().register(blockToolStation);
		event.getRegistry().register(blockDisplay);
		
		/*registerBlock(blockMachineFrame);
		registerBlock(blockToolStation);
		registerBlock(blockDisplay);*/
	}
	
	public static void registerItemBlocks(Register<Item> event)
	{
		registerItemBlock(event, blockMachineFrame);
		registerItemBlock(event, blockToolStation);
		registerItemBlock(event, blockDisplay);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		registerRender(blockMachineFrame);
		registerRender(blockToolStation);
		registerRender(blockDisplay);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenderData()
	{
		
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityToolStation.class, new RenderTileToolStation());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplay.class, new RenderTileDisplay());
	}
	
	public static void registerItemBlock(Register<Item> event, Block block)
	{
		event.getRegistry().register(new ItemBlockBase(block, StringUtils.LIGHT_BLUE).setRegistryName(block.getRegistryName()));
	}
	
	/*public static void registerBlock(Register<Block> event, Block block)
	{
		//registerBlock(event, block, new ItemBlockBase(block, StringUtils.LIGHT_BLUE));
	}
	
	public static void registerBlock(Register<Block> event, Block block, ItemBlock itemBlock)
	{
		//event.getRegistry().register(block);
		//event.getRegistry().register(itemBlock, block.getRegistryName());
	}*/
	
	@SideOnly(Side.CLIENT)
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		//renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
	}
}