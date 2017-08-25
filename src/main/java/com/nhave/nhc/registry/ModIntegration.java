package com.nhave.nhc.registry;

import com.nhave.nhc.integration.top.TOPCompatibility;

import net.minecraftforge.fml.common.Loader;

public class ModIntegration
{
	public static boolean quarkLoaded = false;
	
	public static void preInit()
	{
		/*if ((Loader.isModLoaded("waila") || Loader.isModLoaded("Waila")) && ModConfig.integrationWAILA)
		{
            WailaCompatibility.register();
        }*/
		if (Loader.isModLoaded("theoneprobe") && ModConfig.integrationTOP)
		{
            TOPCompatibility.register();
        }
	}
	
	public static void postInit()
	{
		if (Loader.isModLoaded("quark")) quarkLoaded = true;
	}
}