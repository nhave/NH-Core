package com.nhave.nhc.api.items;

import java.util.List;

import com.nhave.nhc.client.widget.WidgetBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IWidgetControl
{
	@SideOnly(Side.CLIENT)
	public void addWidgets(ItemStack stack, List<WidgetBase> list);
}