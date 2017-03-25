package com.nhave.nhc.items;

import java.util.List;
import java.util.Map.Entry;

import com.nhave.nhc.chroma.Chroma;
import com.nhave.nhc.chroma.ChromaRegistry;
import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;

public class ItemChroma extends ItemBase implements IItemColor
{
	public ItemChroma(String name)
	{
		super(name);
		this.setHasSubtypes(true);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		if (getChroma(stack) != null) return I18n.translateToLocal("color.nhc." + ItemNBTHelper.getString(stack, "CHROMAS", "CHROMA")) + " " + super.getItemStackDisplayName(stack);
		else return super.getItemStackDisplayName(stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag)
	{
		if (getChroma(stack) == null) list.add(StringUtils.RED + I18n.translateToLocal("tooltip.nhc.error.missingnbt"));
	}
	
	public Chroma getChroma(ItemStack stack)
	{
		String name = ItemNBTHelper.getString(stack, "CHROMAS", "CHROMA");
		if (name != null && ChromaRegistry.getChroma(name) != null) return ChromaRegistry.getChroma(name);
		return null;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		if (!ChromaRegistry.isEmpty())
		{
			for(Entry<String, Chroma> entry : ChromaRegistry.CHROMAS.entrySet())
			{
				String key = entry.getKey();
				list.add(ItemNBTHelper.setStackString(new ItemStack(item), "CHROMAS", "CHROMA", key));
			}
		}
		else list.add(new ItemStack(item));
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int pass)
	{
		if (getChroma(stack) != null && pass == 1) return getChroma(stack).getColor();
		return 16777215;
	}
}