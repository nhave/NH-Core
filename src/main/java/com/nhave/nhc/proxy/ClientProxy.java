package com.nhave.nhc.proxy;

import java.io.File;

import com.nhave.nhc.client.eventhandlers.ClientEventHandler;
import com.nhave.nhc.client.eventhandlers.KeyInputEventHandler;
import com.nhave.nhc.client.tickhandlers.DataTickHandler;
import com.nhave.nhc.client.tickhandlers.HudTickHandler;
import com.nhave.nhc.client.widget.TooltipWidget;
import com.nhave.nhc.network.KeyBinds;
import com.nhave.nhc.registry.ClientRegistryHandler;
import com.nhave.nhc.registry.ModBlocks;
import com.nhave.nhc.registry.ModConfig;
import com.nhave.nhc.registry.ModItems;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void setupConfig(File configFile)
	{
		MinecraftForge.EVENT_BUS.register(new ModConfig(true));
		ModConfig.init(configFile);
	}
	
	@Override
    public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new ClientRegistryHandler());
    }
	
	@Override
    public void registerKeybindings()
	{
		ClientRegistry.registerKeyBinding(KeyBinds.toggle);
	}
	
	@Override
	public void registerRenders()
	{
		FMLCommonHandler.instance().bus().register(new HudTickHandler());
		FMLCommonHandler.instance().bus().register(new DataTickHandler());
		
        ModItems.registerRenderData();
		ModItems.registerWidgets();
		ModBlocks.registerRenderData();
	}
	
	@Override
	public void registerEventHandlers()
	{
		super.registerEventHandlers();
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		MinecraftForge.EVENT_BUS.register(new KeyInputEventHandler());
		MinecraftForge.EVENT_BUS.register(new TooltipWidget());
	}
}