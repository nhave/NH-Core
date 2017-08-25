package com.nhave.nhc.proxy;

import java.io.File;

import com.nhave.nhc.eventhandlers.ToolStationEventHandler;
import com.nhave.nhc.registry.ModConfig;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void setupConfig(File configFile)
	{
		MinecraftForge.EVENT_BUS.register(new ModConfig(false));
		ModConfig.init(configFile);
	}

	public void preInit(FMLPreInitializationEvent event) {}
	
    public void registerKeybindings() {}
	
	public void registerRenders() {}
	
	public void registerEventHandlers()
	{
    	MinecraftForge.EVENT_BUS.register(new ToolStationEventHandler());
	}
}