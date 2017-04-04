package com.nhave.nhc.registry;

import com.nhave.nhc.helpers.ItemNBTHelper;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModCrafting
{
	public static final String[] OREDICT = new String[] {"dyeBlack", "dyeRed",  "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
	
	public static void init()
	{
		for (int i = 0; i < OREDICT.length; ++i)
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(ItemNBTHelper.setString(new ItemStack(ModItems.itemChroma), "CHROMAS", "CHROMA", ModItems.COLORNAMES[i]),
				new Object[] {"XYX", "YZY", "XYX",
				'X', "nuggetIron",
				'Y', OREDICT[i],
				'Z', Items.GLOWSTONE_DUST}));
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemNBTHelper.setString(new ItemStack(ModItems.itemChroma), "CHROMAS", "CHROMA", "rainbow"),
			new Object[] {"XYX", "ZCA", "XBX",
			'X', "nuggetIron",
			'Y',"dyeRed",
			'Z', "dyeBlue",
			'A', "dyeGreen",
			'B', "dyeYellow",
			'C', Items.ENDER_EYE}));
		GameRegistry.addRecipe(new ItemStack(ModItems.itemDataGlass),
			new Object[] {"  X", "YZA", " AA",
			'X', Items.ENDER_PEARL,
			'Y', Blocks.GLASS_PANE,
			'Z', Items.COMPARATOR,
			'A', Items.IRON_INGOT});
		GameRegistry.addRecipe(new ItemStack(ModItems.itemWrench),
			new Object[] {"X X", " X ", "X X",
			'X', Items.IRON_INGOT});
	}
}