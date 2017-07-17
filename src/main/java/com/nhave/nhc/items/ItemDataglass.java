package com.nhave.nhc.items;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nhave.nhc.Reference;
import com.nhave.nhc.api.items.IChromaAcceptor;
import com.nhave.nhc.api.items.IHudDisplay;
import com.nhave.nhc.api.items.IInventoryItem;
import com.nhave.nhc.api.items.IItemQuality;
import com.nhave.nhc.api.items.IToolStationHud;
import com.nhave.nhc.client.render.ItemColorHandler;
import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.registry.ModIntegration;
import com.nhave.nhc.registry.ModItems;
import com.nhave.nhc.util.ItemUtil;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public class ItemDataglass extends ItemArmorBase implements IHudDisplay, IInventoryItem, IChromaAcceptor, IItemQuality, IToolStationHud
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
		//tooltip.add("" + ItemUtil.getItemFromStack(stack, "TOKEN"));
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addHiddenTooltip(tooltip, "tooltip.nhc." + this.name, ";", StringUtils.GRAY);
			tooltip.add(StringUtils.localize("tooltip.nhc.chroma.current") + ": §e§o" + getStackInSlot(stack, 0).getDisplayName() + "§r");
		}
		else
		{
			if (ModIntegration.quarkLoaded) tooltip.add("");
			tooltip.add(StringUtils.shiftForInfo);
		}
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
	{
		ItemStack stack = new ItemStack(itemIn);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("theoneprobe", 1);
        stack.setTagCompound(tag);
        subItems.add(stack);
	}
	
	@Override
	public boolean hasOverlay(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot equipmentSlot, String armorTexture)
	{
		String name = stack.getDisplayName().toLowerCase().trim();
		String textureName = (name.equals("focus") ? "focus" : stack.getItem().getRegistryName().getResourcePath());
		return Reference.MODID + ":textures/armor/" + textureName + (armorTexture != null && armorTexture == "overlay" ? "_0" : "_1") + ".png";
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
		return ItemColorHandler.INSTANCE.getColorFromItemstack(stack, 1);
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

	@Override
	public String getQualityColor(ItemStack stack)
	{
		return StringUtils.LIGHT_BLUE;
	}

	@Override
	public void addToolStationInfo(ItemStack stack, List list)
	{
		list.add(StringUtils.localize("tooltip.nhc.toolstation.chroma") + ": " + StringUtils.format(getStackInSlot(stack, 0).getDisplayName(), StringUtils.YELLOW, StringUtils.ITALIC));
	}
}
