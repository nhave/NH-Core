package com.nhave.nhc.registry;

import com.nhave.nhc.Reference;
import com.nhave.nhc.api.client.ITickingData;
import com.nhave.nhc.chroma.Chroma;
import com.nhave.nhc.chroma.ChromaRainbow;
import com.nhave.nhc.chroma.ChromaRegistry;
import com.nhave.nhc.chroma.ChromaTracker;
import com.nhave.nhc.client.mesh.CustomMeshDefinitionDataGlass;
import com.nhave.nhc.client.mesh.CustomMeshDefinitionToken;
import com.nhave.nhc.client.render.ItemColorHandler;
import com.nhave.nhc.client.tickhandlers.DataTickHandler;
import com.nhave.nhc.client.widget.TooltipWidget;
import com.nhave.nhc.client.widget.WidgetInventory;
import com.nhave.nhc.items.ItemBase;
import com.nhave.nhc.items.ItemChroma;
import com.nhave.nhc.items.ItemDataglass;
import com.nhave.nhc.items.ItemToken;
import com.nhave.nhc.items.ItemWrench;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems
{
	public static final int[] COLORCODES = new int[] {1644825, 16711680, 65280, 6704179, 255, 11685080, 5013401, 10066329, 6710886, 15892389, 8388371, 15059968, 6730495, 15027416, 16757299, 16777215};
	public static final String[] COLORNAMES = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "lightGray", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
	
	public static Item itemDataGlass;
	public static Item itemWrench;
	public static Item itemShaderRemover;
	public static Item itemChroma;
	public static Item itemToken;
	public static Item itemLock;
	public static Item itemPublicLock;
	public static Item itemKey;
	public static Item itemMasterKey;
	public static Item itemEnergyPearl;
	
	public static Chroma[] chromaBasic = new Chroma[16];
	public static Chroma chromaRainbow;
	public static Chroma chromaTracker;
	
	public static ArmorMaterial materialNoArmor = EnumHelper.addArmorMaterial("NOARMOR", "NOARMOR", 0, new int[] {0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	
	public static void init()
	{
		itemDataGlass = new ItemDataglass("data_glass");
		itemWrench = new ItemWrench("wrench");
		itemChroma = new ItemChroma("chroma");
		itemToken = new ItemToken("token");
		itemLock = new ItemBase("lock").setQualityColor(StringUtils.LIGHT_BLUE).setSneakBypassUse().setShiftForDetails();
		itemPublicLock = new ItemBase("publiclock").setQualityColor(StringUtils.LIGHT_BLUE).setSneakBypassUse().setShiftForDetails();
		itemKey = new ItemBase("key").setQualityColor(StringUtils.LIGHT_BLUE).setSneakBypassUse().setShiftForDetails().setMaxStackSize(1);
		itemMasterKey = new ItemBase("masterkey").setQualityColor(StringUtils.PINK).setSneakBypassUse().setShiftForDetails().setMaxStackSize(1);
		itemEnergyPearl = new ItemBase("energypearl").setQualityColor(StringUtils.LIGHT_BLUE).setShiftForDetails();
		
		//ItemToken.ITEMS.add(itemDataGlass);
		
		for (int i = 0; i < chromaBasic.length; ++i)
		{
			chromaBasic[i] = ChromaRegistry.registerChroma(COLORNAMES[i], new Chroma(COLORCODES[i]));
		}
		chromaRainbow = ChromaRegistry.registerChroma("rainbow", new ChromaRainbow());
		chromaTracker = ChromaRegistry.registerChroma("tracker", new ChromaTracker());
	}
	
	public static void register(Register<Item> event)
	{
		event.getRegistry().register(itemDataGlass);
		event.getRegistry().register(itemWrench);
		event.getRegistry().register(itemChroma);
		event.getRegistry().register(itemToken);
		event.getRegistry().register(itemLock);
		event.getRegistry().register(itemPublicLock);
		event.getRegistry().register(itemKey);
		event.getRegistry().register(itemMasterKey);
		event.getRegistry().register(itemEnergyPearl);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		registerRenderMesh(itemDataGlass, new CustomMeshDefinitionDataGlass());
		ModelLoader.registerItemVariants(itemDataGlass, new ResourceLocation(Reference.MODID + ":" + itemDataGlass.getRegistryName().getResourcePath()), new ResourceLocation(Reference.MODID + ":" + itemDataGlass.getRegistryName().getResourcePath() + "_gold"), new ResourceLocation(Reference.MODID + ":" + "focus"));
		
		registerRenderMesh(itemToken, new CustomMeshDefinitionToken());
		ModelLoader.registerItemVariants(itemToken, new ResourceLocation(Reference.MODID + ":" + itemToken.getRegistryName().getResourcePath()), new ResourceLocation(Reference.MODID + ":" + "token_active"));
		
		registerRender(itemWrench);
		registerRender(itemChroma);
		registerRender(itemLock);
		registerRender(itemPublicLock);
		registerRender(itemKey);
		registerRender(itemMasterKey);
		registerRender(itemEnergyPearl);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenderData()
	{
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(ItemColorHandler.INSTANCE, itemDataGlass);		
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(ItemColorHandler.INSTANCE, itemChroma);
		
		DataTickHandler.TICKDATA.add((ITickingData) chromaRainbow);
		DataTickHandler.TICKDATA.add((ITickingData) chromaTracker);
	}

	@SideOnly(Side.CLIENT)
	public static void registerWidgets()
	{
		TooltipWidget.register(new WidgetInventory());
	}

	@SideOnly(Side.CLIENT)
	public static void registerRender(Item item)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		//renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenderMesh(Item item, ItemMeshDefinition mesh)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		//renderItem.getItemModelMesher().register(item, mesh);
		ModelLoader.setCustomMeshDefinition(item, mesh);
	}
}