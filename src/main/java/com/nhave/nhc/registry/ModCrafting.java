package com.nhave.nhc.registry;

import com.nhave.nhc.helpers.ItemNBTHelper;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModCrafting
{
	public static final String[] OREDICT = new String[] {"dyeBlack", "dyeRed",  "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
	
	public static void init()
	{
		if (ModConfig.craftChromas)
		{
			for (int i = 0; i < OREDICT.length; ++i)
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(ItemNBTHelper.setString(new ItemStack(ModItems.itemChroma), "CHROMAS", "CHROMA", ModItems.COLORNAMES[i]),
					new Object[] {"XYX", "YZY", "XYX",
					'X', "nuggetIron",
					'Y', OREDICT[i],
					'Z', Items.GLOWSTONE_DUST}));
			}
			if (ModConfig.craftChromaRainbow)
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(ItemNBTHelper.setString(new ItemStack(ModItems.itemChroma), "CHROMAS", "CHROMA", "rainbow"),
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
				GameRegistry.addRecipe(new ShapedOreRecipe(ItemNBTHelper.setString(new ItemStack(ModItems.itemChroma), "CHROMAS", "CHROMA", "tracker"),
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
		GameRegistry.addRecipe(dataGlass,
			new Object[] {"  X", "YZA", " AA",
			'X', ModItems.itemEnergyPearl,
			'Y', Blocks.GLASS_PANE,
			'Z', Items.COMPARATOR,
			'A', Items.IRON_INGOT});
		
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemWrench,
			new Object[] {"X X", " X ", "X X",
			'X', Items.IRON_INGOT}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemKey,
			new Object[] {"YYX", "YY ",
			'X', Items.GOLD_INGOT,
			'Y', Items.GOLD_NUGGET}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemLock,
			new Object[] {"XXX", "X X", "YYY",
			'X', "nuggetIron",
			'Y', Items.GOLD_INGOT}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.itemToken,
			new Object[] {"XYX", "YZY", "XYX",
			'X', "nuggetIron",
			'Y', Items.IRON_INGOT,
			'Z', ModItems.itemEnergyPearl}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.itemEnergyPearl, new Object[] {Items.REDSTONE, Items.REDSTONE, Items.ENDER_PEARL, Items.GLOWSTONE_DUST}));
		
		//Blocks
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.blockMachineFrame,
			new Object[] {"XYX", "YZY", "XYX",
			'X', Items.IRON_INGOT,
			'Y', Blocks.IRON_BARS,
			'Z', Items.REDSTONE}));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockToolStation),
			new Object[] {"XBX", "YZY", "XAX",
			'X', Items.IRON_INGOT,
			'Y', Blocks.PISTON,
			'Z', Items.COMPARATOR,
			'A', ModBlocks.blockMachineFrame,
			'B', Blocks.GLASS_PANE});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockDisplay),
			new Object[] {"XYX", "ZAZ",
			'X', Items.GLOWSTONE_DUST,
			'Y', ModItems.itemEnergyPearl,
			'Z', Items.IRON_INGOT,
			'A', ModBlocks.blockMachineFrame});
	}
}