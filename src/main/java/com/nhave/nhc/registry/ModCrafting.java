package com.nhave.nhc.registry;

import com.nhave.nhc.Reference;
import com.nhave.nhc.helpers.ItemNBTHelper;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModCrafting
{
	public static final String[] OREDICT = new String[] {"dyeBlack", "dyeRed",  "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
	private static int recipe = 0;
	
	public static void register(Register<IRecipe> event)
	{
		if (ModConfig.craftChromas)
		{
			for (int i = 0; i < OREDICT.length; ++i)
			{
				addRecipe(event, new ShapedOreRecipe(null, ItemNBTHelper.setString(new ItemStack(ModItems.itemChroma), "CHROMAS", "CHROMA", ModItems.COLORNAMES[i]),
					new Object[] {"XYX", "YZY", "XYX",
					'X', "nuggetIron",
					'Y', OREDICT[i],
					'Z', Items.GLOWSTONE_DUST}));
			}
			if (ModConfig.craftChromaRainbow)
			{
				addRecipe(event, new ShapedOreRecipe(new ResourceLocation(Reference.MODID, "chromaRainbow"), ItemNBTHelper.setString(new ItemStack(ModItems.itemChroma), "CHROMAS", "CHROMA", "rainbow"),
					new Object[] {"XYX", "ZCA", "XBX",
					'X', "nuggetIron",
					'Y', "dyeRed",
					'Z', "dyeBlue",
					'A', "dyeGreen",
					'B', "dyeYellow",
					'C', ModItems.itemEnergyPearl}));
			}
			if (ModConfig.craftChromaTracker)
			{
				addRecipe(event, new ShapedOreRecipe(new ResourceLocation(Reference.MODID, "chromaTracker"), ItemNBTHelper.setString(new ItemStack(ModItems.itemChroma), "CHROMAS", "CHROMA", "tracker"),
					new Object[] {"XYX", "ZCA", "XBX",
					'X', "nuggetIron",
					'Y', new ItemStack(Items.SKULL, 1, 0),
					'Z', new ItemStack(Items.SKULL, 1, 2),
					'A', new ItemStack(Items.SKULL, 1, 4),
					'B', new ItemStack(Items.SKULL, 1, 1),
					'C', ModItems.itemEnergyPearl}));
			}
		}
		
		ItemStack dataGlass = new ItemStack(ModItems.itemDataGlass);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("theoneprobe", 1);
        dataGlass.setTagCompound(tag);
        addRecipe(event, new ShapedOreRecipe(new ResourceLocation(Reference.MODID, "dataGlass"), dataGlass,
			new Object[] {"  X", "YZA", " AA",
			'X', ModItems.itemEnergyPearl,
			'Y', Blocks.GLASS_PANE,
			'Z', Items.COMPARATOR,
			'A', Items.IRON_INGOT}));
		
		addRecipe(event, new ShapedOreRecipe(new ResourceLocation(Reference.MODID, "wrench"), ModItems.itemWrench,
			new Object[] {"X X", " X ", "X X",
			'X', Items.IRON_INGOT}));
		addRecipe(event, new ShapedOreRecipe(new ResourceLocation(Reference.MODID, "key"), ModItems.itemKey,
			new Object[] {"YYX", "YY ",
			'X', Items.GOLD_INGOT,
			'Y', Items.GOLD_NUGGET}));
		addRecipe(event, new ShapedOreRecipe(new ResourceLocation(Reference.MODID, "lock"), ModItems.itemLock,
			new Object[] {"XXX", "X X", "YYY",
			'X', "nuggetIron",
			'Y', Items.GOLD_INGOT}));
		addRecipe(event, new ShapelessOreRecipe(new ResourceLocation(Reference.MODID, "publiclock"), ModItems.itemPublicLock,  new Object[] {ModItems.itemLock, Blocks.LEVER}));
		addRecipe(event, new ShapedOreRecipe(new ResourceLocation(Reference.MODID, "token"), ModItems.itemToken,
			new Object[] {"XYX", "YZY", "XYX",
			'X', "nuggetIron",
			'Y', Items.IRON_INGOT,
			'Z', ModItems.itemEnergyPearl}));
		addRecipe(event, new ShapelessOreRecipe(new ResourceLocation(Reference.MODID, "energyPearl"), ModItems.itemEnergyPearl, new Object[] {Items.REDSTONE, Items.REDSTONE, Items.ENDER_PEARL, Items.GLOWSTONE_DUST}));
		
		//Blocks
		addRecipe(event, new ShapedOreRecipe(new ResourceLocation(Reference.MODID, "machineFrame"), ModBlocks.blockMachineFrame,
			new Object[] {"XYX", "YZY", "XYX",
			'X', Items.IRON_INGOT,
			'Y', Blocks.IRON_BARS,
			'Z', Items.REDSTONE}));
		
		addRecipe(event, new ShapedOreRecipe(new ResourceLocation(Reference.MODID, "toolStation"), new ItemStack(ModBlocks.blockToolStation),
			new Object[] {"XBX", "YZY", "XAX",
			'X', Items.IRON_INGOT,
			'Y', Blocks.PISTON,
			'Z', Items.COMPARATOR,
			'A', ModBlocks.blockMachineFrame,
			'B', Blocks.GLASS_PANE}));
		addRecipe(event, new ShapedOreRecipe(new ResourceLocation(Reference.MODID, "display"), new ItemStack(ModBlocks.blockDisplay),
			new Object[] {"XYX", "ZAZ",
			'X', Items.GLOWSTONE_DUST,
			'Y', ModItems.itemEnergyPearl,
			'Z', Items.IRON_INGOT,
			'A', ModBlocks.blockMachineFrame}));
	}
	
	public static void addRecipe(Register<IRecipe> event, IRecipe rec)
	{
		event.getRegistry().register(rec.setRegistryName(new ResourceLocation(Reference.MODID, "recipe" + recipe)));
		++recipe;
	}
}