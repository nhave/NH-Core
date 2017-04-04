package com.nhave.nhc.items;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nhave.nhc.Reference;
import com.nhave.nhc.api.items.IChromaAcceptor;
import com.nhave.nhc.api.items.IHudDisplay;
import com.nhave.nhc.api.items.IInventoryItem;
import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.registry.ModItems;
import com.nhave.nhc.shaders.ShaderManager;
import com.nhave.nhc.util.ItemUtil;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemDataglass extends ItemArmorBase implements IHudDisplay, IItemColor, IInventoryItem, IChromaAcceptor
{
	private String name;
	
	public ItemDataglass(String name)
	{
		super(name, ModItems.materialNoArmor, 0, EntityEquipmentSlot.HEAD);
		this.setNoRepair();
		this.name = name;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		TooltipHelper.addHiddenTooltip(tooltip, "tooltip.nhc." + this.name, ";");
	}
	
	@Override
	public boolean hasOverlay(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot equipmentSlot, String armorTexture)
	{
		return Reference.MODID + ":textures/armor/" + stack.getItem().getRegistryName().getResourcePath() + (armorTexture == "overlay" ? "_0" : "_1") + ".png";
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		return HashMultimap.<String, AttributeModifier>create();
	}

	@Override
	public boolean isHudActive(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public boolean hasColor(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public int getColor(ItemStack stack)
	{
		return getColorFromItemstack(stack, 1);
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int pass)
	{
		return pass == 1 ? ShaderManager.getChroma(stack).getColor() : 16777215;
	}

	@Override
	public int getInventoryX(ItemStack stack)
	{
		return 1;
	}

	@Override
	public int getInventoryY(ItemStack stack)
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(ItemStack stack, int slot)
	{
		ItemStack slotItem = ItemUtil.getItemFromStack(stack, "CHROMA");
		if (slotItem == null) slotItem = ItemNBTHelper.setString(new ItemStack(ModItems.itemChroma), "CHROMAS", "CHROMA", "white");
		return slotItem;
	}
}
