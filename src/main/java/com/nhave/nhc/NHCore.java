package com.nhave.nhc;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.nhave.nhc.network.PacketHandler;
import com.nhave.nhc.proxy.CommonProxy;
import com.nhave.nhc.registry.ModBlocks;
import com.nhave.nhc.registry.ModIntegration;
import com.nhave.nhc.registry.ModTweaks;
import com.nhave.nhc.registry.RegistryHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUIFACTORY)
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
		
		MinecraftForge.EVENT_BUS.register(new RegistryHandler());
    	
    	ModIntegration.preInit();
    	
    	proxy.preInit(event);
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
    	//ModCrafting.init();
    	ModTweaks.postInit();
    	ModIntegration.postInit();
    }
    
    public static final CreativeTabs CREATIVETAB = new CreativeTabs("nhcore")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ModBlocks.blockToolStation);
		}
	};
}