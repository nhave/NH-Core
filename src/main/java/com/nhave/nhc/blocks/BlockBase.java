package com.nhave.nhc.blocks;

import com.nhave.nhc.NHCore;
import com.nhave.nhc.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block
{
	public BlockBase(String name, Material materialIn)
	{
		super(materialIn);
		this.setRegistryName(name);
		this.setCreativeTab(NHCore.CREATIVETAB);
		this.setUnlocalizedName(Reference.MODID + "." + name);
	}
}