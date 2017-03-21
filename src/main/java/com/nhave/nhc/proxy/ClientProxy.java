package com.nhave.nhc.proxy;

import java.io.File;

import com.nhave.nhc.client.widget.TooltipWidget;
import com.nhave.nhc.registry.ModConfig;
import com.nhave.nhc.registry.ModItems;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void setupConfig(File configFile)
	{
		FMLCommonHandler.instance().bus().register(new ModConfig(true));
		ModConfig.init(configFile);
	}
	
	@Override
	public void registerRenders()
	{
		ModItems.registerRenders();
		ModItems.registerWidgets();
		//ModBlocks.registerRenders();
	}
	
	@Override
	public void registerEventHandlers()
	{
		super.registerEventHandlers();
		MinecraftForge.EVENT_BUS.register(new TooltipWidget());
	}
}