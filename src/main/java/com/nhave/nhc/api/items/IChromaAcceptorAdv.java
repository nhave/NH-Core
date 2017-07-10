package com.nhave.nhc.api.items;

import net.minecraft.item.ItemStack;

public interface IChromaAcceptorAdv extends IChromaAcceptor
{
	public boolean supportsChroma(ItemStack stack);
}