package com.nhave.nhc.registry;

import com.nhave.nhc.Reference;
import com.nhave.nhc.api.client.ITickingData;
import com.nhave.nhc.chroma.Chroma;
import com.nhave.nhc.chroma.ChromaRainbow;
import com.nhave.nhc.chroma.ChromaRegistry;
import com.nhave.nhc.client.tickhandlers.DataTickHandler;
import com.nhave.nhc.client.widget.TooltipWidget;
import com.nhave.nhc.client.widget.WidgetInventory;
import com.nhave.nhc.items.ItemChroma;
import com.nhave.nhc.items.ItemDataglass;
import com.nhave.nhc.items.ItemWrench;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems
{
	public static final int[] COLORCODES = new int[] {1644825, 16711680, 65280, 6704179, 255, 11685080, 5013401, 10066329, 6710886, 15892389, 8388371, 15059968, 6730495, 15027416, 16757299, 16777215};
	public static final String[] COLORNAMES = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "lightGray", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
	
	public static Item itemDataGlass;
	public static Item itemWrench;
	public static Item itemShaderRemover;
	public static Item itemChroma;
	
	public static Chroma[] chromaBasic = new Chroma[16];
	public static Chroma chromaRainbow;
	
	public static ArmorMaterial materialNoArmor = EnumHelper.addArmorMaterial("NOARMOR", "NOARMOR", 0, new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	
	public static void init()
	{
		itemDataGlass = new ItemDataglass("data_glass");
		itemWrench = new ItemWrench("wrench");
		itemChroma = new ItemChroma("chroma");
		
		for (int i = 0; i < chromaBasic.length; ++i)
		{
			chromaBasic[i] = ChromaRegistry.registerChroma(COLORNAMES[i], new Chroma(COLORCODES[i]));
		}
		chromaRainbow = ChromaRegistry.registerChroma("rainbow", new ChromaRainbow());
		DataTickHandler.TICKDATA.add((ITickingData) chromaRainbow);
	}
	
	public static void register()
	{
		GameRegistry.register(itemDataGlass);
		GameRegistry.register(itemWrench);
		GameRegistry.register(itemChroma);
	}
	
	public static void registerRenders()
	{
		registerRender(itemDataGlass);
		registerRender(itemWrench);
		registerRender(itemChroma);
		
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((IItemColor)itemDataGlass, itemDataGlass);		
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler((IItemColor)itemChroma, itemChroma);
	}
	
	public static void registerWidgets()
	{
		TooltipWidget.register(new WidgetInventory());
	}
	
	public static void registerRender(Item item)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
	}
}