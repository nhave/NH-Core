package com.nhave.nhc.items;

import java.util.List;

import com.nhave.nhc.NHCore;
import com.nhave.nhc.Reference;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class ItemBase extends Item
{
	private EnumRarity rarity = EnumRarity.COMMON;
	private boolean sneakUse = false;
	private boolean shiftForDetails = false;
	
	public ItemBase(String name)
	{
		this.setRegistryName(name);
		this.setCreativeTab(NHCore.CREATIVETAB);
		this.setUnlocalizedName(Reference.MODID + "." + name);
	}
	
	public ItemBase(String name, Object... obj)
	{
		this(name);
		for (int i = 0; i < obj.length; ++i)
		{
			if (obj[i] instanceof String)
			{
				if (((String) obj[i]).equals("TAB_HIDDEN")) this.setCreativeTab(null);
			}
			else if (obj[i] instanceof EnumRarity) this.rarity = (EnumRarity) obj[i];
			else if (obj[i] instanceof CreativeTabs) this.setCreativeTab((CreativeTabs) obj[i]);
		}
	}
	
	public String getItemName(ItemStack stack)
	{
		return stack.getItem().getRegistryName().getResourcePath();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		if (this.shiftForDetails)
		{
			if (StringUtils.isShiftKeyDown()) TooltipHelper.addHiddenTooltip(tooltip, "tooltip.nhc." + getRegistryName().getResourcePath(), ";", StringUtils.GRAY);
			else tooltip.add(StringUtils.shiftForInfo);
		}
		else TooltipHelper.addHiddenTooltip(tooltip, "tooltip.nhc." + getRegistryName().getResourcePath(), ";", StringUtils.GRAY);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return this.rarity;
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
}