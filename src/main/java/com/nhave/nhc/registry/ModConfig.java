package com.nhave.nhc.registry;

import java.io.File;

import com.nhave.nhc.Reference;
import com.nhave.nhc.client.util.RenderUtils.HUDPositions;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModConfig
{
	public static boolean isClientConfig;
	
	public static Configuration config;

    public static boolean tweakCommandBlock = Defaults.tweakCommandBlock;
    public static boolean tweakCommandCart = Defaults.tweakCommandCart;
    public static boolean tweakStructureBlocks = Defaults.tweakStructureBlocks;
    public static boolean tweakBarrierBlock = Defaults.tweakBarrierBlock;
    public static boolean tweakSnowBalls = Defaults.tweakSnowBalls;
    public static boolean tweakEnderPearls = Defaults.tweakEnderPearls;
    
    public static int hudPosition = Defaults.hudPosition;
    public static int hudOffsetX = Defaults.hudOffsetX;
    public static int hudOffsetY = Defaults.hudOffsetY;
    public static double hudScale = Defaults.hudScale;
    public static boolean showHudWhileChatting = Defaults.showHudWhileChatting;
    public static boolean postModeToChat = Defaults.postModeToChat;
    public static boolean forceShowHud = Defaults.forceShowHud;
    public static boolean enableWidgets = Defaults.enableWidgets;
	
	public ModConfig(boolean isClient)
	{
		this.isClientConfig = isClient;
	}

	public static void init(File configFile)
	{
		config = new Configuration(configFile);
		loadConfig(false);
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if(eventArgs.getModID().equalsIgnoreCase(Reference.MODID))
		{
			loadConfig(false);
		}
	}
	
	public static void loadConfig(boolean load)
	{
		loadCommonConfig();
		if (isClientConfig) loadClientConfig();
		
		if (!config.hasChanged()) return;
		config.save();
	}
	
	public static void loadCommonConfig()
	{
		config.setCategoryComment("common", "Configuration for all Common configs");
		tweakCommandBlock = config.get("common", "TweakCommandBlock", Defaults.tweakCommandBlock, "Adds the Command Blocks to the Redstone Creative Tab").getBoolean(Defaults.tweakCommandBlock);
		tweakCommandCart = config.get("common", "TweakCommandCart", Defaults.tweakCommandCart, "Adds the Command Cart to the Transport Creative Tab").getBoolean(Defaults.tweakCommandCart);
		tweakStructureBlocks = config.get("common", "TweakStructureBlocks", Defaults.tweakStructureBlocks, "Adds the Structure Blocks to the Redstone Creative Tab").getBoolean(Defaults.tweakStructureBlocks);
		tweakBarrierBlock = config.get("common", "TweakBarrierBlock", Defaults.tweakBarrierBlock, "Adds the Barrier Block to the Redstone Creative Tab").getBoolean(Defaults.tweakBarrierBlock);
		
		tweakSnowBalls = config.get("common", "TweakSnowBalls", Defaults.tweakSnowBalls, "Increases stacksize of Snow Balls 64").getBoolean(Defaults.tweakSnowBalls);
		tweakEnderPearls = config.get("common", "TweakEnderPearls", Defaults.tweakEnderPearls, "Increases stacksize of Ender Pearls 64").getBoolean(Defaults.tweakEnderPearls);
	}
	
	public static void loadClientConfig()
	{
		config.setCategoryComment("client", "Configuration for all Client configs");
		hudPosition = config.get("client", "HUDBasePosition", Defaults.hudPosition, "The base position of the HUD on the screen. 0 = top left, 1 = top center, 2 = top right, 3 = left, 4 = right, 5 = bottom left, 6 = bottom right").setMinValue(0).setMaxValue(HUDPositions.values().length - 1).getInt(Defaults.hudPosition);
        hudOffsetX = config.get("client", "HUDOffset-X", Defaults.hudOffsetX, "The HUD display will be shifted horizontally by this value. This value may be negative.").getInt(Defaults.hudOffsetX);
        hudOffsetY = config.get("client", "HUDOffset-Y", Defaults.hudOffsetY, "The HUD display will be shifted vertically by this value. This value may be negative.").getInt(Defaults.hudOffsetY);
        hudScale = Math.abs(config.get("client", "HUDScale", Defaults.hudScale, "How large the HUD will be rendered. Default is 1.0, can be bigger or smaller").setMinValue(0.001D).getDouble(Defaults.hudScale));
        showHudWhileChatting = config.get("client", "ShowHUDwhilechatting", Defaults.showHudWhileChatting, "When enabled, the HUD will display even when the chat window is opened.").getBoolean(Defaults.showHudWhileChatting);
        postModeToChat = config.get("client", "PostModeToChat", Defaults.postModeToChat, "Set to true if you have too much stuff on your HUD").getBoolean(Defaults.postModeToChat);
        forceShowHud = config.get("client", "ForceShowHud", Defaults.forceShowHud, "Set to true to always show the HUD").getBoolean(Defaults.forceShowHud);
        enableWidgets = config.get("client", "EnableWidgets", Defaults.enableWidgets, "Set to true to enable the experimental Widget System").getBoolean(Defaults.enableWidgets);
	}
}