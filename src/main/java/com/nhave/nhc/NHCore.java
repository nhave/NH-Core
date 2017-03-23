package com.nhave.nhc;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.nhave.nhc.network.PacketHandler;
import com.nhave.nhc.proxy.CommonProxy;
import com.nhave.nhc.registry.ModBlocks;
import com.nhave.nhc.registry.ModCrafting;
import com.nhave.nhc.registry.ModItems;
import com.nhave.nhc.registry.ModTweaks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MCVERSIONS, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUIFACTORY)
public class NHCore
{
    public static Logger logger;
    
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;
    
    @Mod.Instance(Reference.MODID)
	public static NHCore instance = new NHCore();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
		proxy.setupConfig(new File(event.getModConfigurationDirectory(), "nhcore.cfg"));
		
		PacketHandler.init();
		proxy.registerKeybindings();
		
    	ModItems.init();
    	ModItems.register();
    	ModBlocks.init();
    	ModBlocks.register();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerRenders();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	proxy.registerEventHandlers();
    	ModCrafting.init();
    	ModTweaks.postInit();
    }
    
    public static final CreativeTabs CREATIVETAB = new CreativeTabs("nhcore")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(Items.APPLE);
		}
	};
}