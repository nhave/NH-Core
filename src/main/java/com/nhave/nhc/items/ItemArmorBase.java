package com.nhave.nhc.items;

import com.nhave.nhc.NHCore;
import com.nhave.nhc.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorBase extends ItemArmor
{
	public ItemArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(Reference.MODID + "." + name);
		this.setRegistryName(name);
		this.setCreativeTab(NHCore.CREATIVETAB);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot equipmentSlot, String armorTexture)
	{
		return Reference.MODID + ":textures/armor/" + stack.getItem().getRegistryName().getResourcePath() + ".png";
	}
	
	public String getItemName(ItemStack stack)
	{
		return stack.getItem().getRegistryName().getResourcePath();
	}
}