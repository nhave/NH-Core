package com.nhave.nhc.items;

import java.util.List;

import com.nhave.nhc.NHCore;
import com.nhave.nhc.Reference;
import com.nhave.nhc.api.items.IItemQuality;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemBase extends Item implements IItemQuality
{
	private String rarity = "";
	private boolean sneakUse = false;
	private boolean shiftForDetails = false;
	
	public ItemBase(String name)
	{
		this.setRegistryName(name);
		this.setCreativeTab(NHCore.CREATIVETAB);
		this.setUnlocalizedName(Reference.MODID + "." + name);
	}
	
	public String getItemName(ItemStack stack)
	{
		return stack.getItem().getRegistryName().getResourcePath();
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (this.shiftForDetails)
		{
			if (StringUtils.isShiftKeyDown()) TooltipHelper.addHiddenTooltip(tooltip, "tooltip.nhc." + getRegistryName().getResourcePath(), ";", StringUtils.GRAY);
			else tooltip.add(StringUtils.shiftForInfo);
		}
		else TooltipHelper.addHiddenTooltip(tooltip, "tooltip.nhc." + getRegistryName().getResourcePath(), ";", StringUtils.GRAY);
	}
	
	public ItemBase setShiftForDetails()
	{
		this.shiftForDetails = true;
		return this;
	}
	
	public ItemBase setSneakBypassUse()
	{
		this.sneakUse = true;
		return this;
	}
	
	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player)
	{
		return this.sneakUse;
	}
	
	public ItemBase setQualityColor(String color)
	{
		this.rarity = color;
		return this;
	}

	@Override
	public String getQualityColor(ItemStack stack)
	{
		return this.rarity;
	}
}