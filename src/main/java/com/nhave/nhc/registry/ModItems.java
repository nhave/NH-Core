package com.nhave.nhc.registry;

import com.nhave.nhc.Reference;
import com.nhave.nhc.client.widget.TooltipWidget;
import com.nhave.nhc.client.widget.WidgetInventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ModItems
{
	//public static Item itemDebug;
	
	public static void init()
	{
		//itemDebug = new ItemDebug("debugger");
	}
	
	public static void register()
	{
		//GameRegistry.register(itemDebug);
	}
	
	public static void registerWidgets()
	{
		TooltipWidget.register(new WidgetInventory());
	}
	
	public static void registerRenders()
	{
		//registerRender(itemDebug);
	}
	
	public static void registerRender(Item item)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
	}
}