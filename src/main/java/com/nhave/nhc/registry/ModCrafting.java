package com.nhave.nhc.registry;

import com.nhave.nhc.helpers.ItemNBTHelper;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

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
					'C', Items.ENDER_EYE}));
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
					'C', Items.ENDER_EYE}));
			}
		}
		
		ItemStack dataGlass = new ItemStack(ModItems.itemDataGlass);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("theoneprobe", 1);
        dataGlass.setTagCompound(tag);
		GameRegistry.addRecipe(dataGlass,
			new Object[] {"  X", "YZA", " AA",
			'X', Items.ENDER_PEARL,
			'Y', Blocks.GLASS_PANE,
			'Z', Items.COMPARATOR,
			'A', Items.IRON_INGOT});
		
		GameRegistry.addRecipe(new ItemStack(ModItems.itemWrench),
			new Object[] {"X X", " X ", "X X",
			'X', Items.IRON_INGOT});
		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockToolStation),
			new Object[] {"XXX", "YZY", "XAX",
			'X', Items.IRON_INGOT,
			'Y', Blocks.PISTON,
			'Z', Blocks.IRON_BARS,
			'A', Blocks.REDSTONE_BLOCK});
	}
}