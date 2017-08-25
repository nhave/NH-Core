package com.nhave.nhc.items;

import java.util.List;
import java.util.Map.Entry;

import com.nhave.nhc.NHCore;
import com.nhave.nhc.api.items.IItemQuality;
import com.nhave.nhc.chroma.Chroma;
import com.nhave.nhc.chroma.ChromaRegistry;
import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemChroma extends ItemBase implements IItemQuality
{
	public ItemChroma(String name)
	{
		super(name);
		this.setHasSubtypes(true);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		if (getChroma(stack) != null) return StringUtils.localize("color.nhc." + ItemNBTHelper.getString(stack, "CHROMAS", "CHROMA")) + " " + super.getItemStackDisplayName(stack);
		else return super.getItemStackDisplayName(stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (getChroma(stack) == null) tooltip.add(StringUtils.RED + I18n.translateToLocal("tooltip.nhc.error.missingnbt"));
		else
		{
			if (StringUtils.isShiftKeyDown())
			{
				String info = "tooltip.nhc.chroma." + ItemNBTHelper.getString(stack, "CHROMAS", "CHROMA");
				if (StringUtils.localize(info).equals(info)) TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.nhc.chroma").replace("%color%", StringUtils.localize("color.nhc." + ItemNBTHelper.getString(stack, "CHROMAS", "CHROMA")).toLowerCase()), ";", StringUtils.GRAY);
				else TooltipHelper.addSplitString(tooltip, StringUtils.localize(info), ";", StringUtils.GRAY);
				
			}
			else tooltip.add(StringUtils.shiftForInfo);
		}
	}
	
	public Chroma getChroma(ItemStack stack)
	{
		String name = ItemNBTHelper.getString(stack, "CHROMAS", "CHROMA");
		if (name != null && ChromaRegistry.getChroma(name) != null) return ChromaRegistry.getChroma(name);
		return null;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (tab != NHCore.CREATIVETAB) return;
		if (!ChromaRegistry.isEmpty())
		{
			for(Entry<String, Chroma> entry : ChromaRegistry.CHROMAS.entrySet())
			{
				String key = entry.getKey();
				items.add(ItemNBTHelper.setString(new ItemStack(this), "CHROMAS", "CHROMA", key));
			}
		}
		else items.add(new ItemStack(this));
	}

	@Override
	public String getQualityColor(ItemStack stack)
	{
		if (getChroma(stack) != null) return getChroma(stack).getQualityColor();
		return "";
	}
}