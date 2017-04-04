package com.nhave.nhc.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public class ModTweaks
{
	public static void postInit()
	{
		if (ModConfig.tweakCommandBlock)
		{
			Blocks.COMMAND_BLOCK.setCreativeTab(CreativeTabs.REDSTONE);
			Blocks.CHAIN_COMMAND_BLOCK.setCreativeTab(CreativeTabs.REDSTONE);
			Blocks.REPEATING_COMMAND_BLOCK.setCreativeTab(CreativeTabs.REDSTONE);
		}
		if (ModConfig.tweakStructureBlocks)
		{
			Blocks.STRUCTURE_BLOCK.setCreativeTab(CreativeTabs.REDSTONE);
			Blocks.STRUCTURE_VOID.setCreativeTab(CreativeTabs.REDSTONE);
		}
		if (ModConfig.tweakBarrierBlock) Blocks.BARRIER.setCreativeTab(CreativeTabs.REDSTONE);
		if (ModConfig.tweakCommandCart) Items.COMMAND_BLOCK_MINECART.setCreativeTab(CreativeTabs.TRANSPORTATION);
		if (ModConfig.tweakSnowBalls) Items.SNOWBALL.setMaxStackSize(64);
		if (ModConfig.tweakEnderPearls) Items.ENDER_PEARL.setMaxStackSize(64);
	}
}