package com.nhave.nhc.items;

import java.util.ArrayList;
import java.util.List;

import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemToken extends ItemBase
{
	private static final String[] ACCEPTED_USERS = new String[] {"nhave", "voxel_friend", "math992e"};
	private static final String TOKEN_NBT = "TOKEN_ACTIVE";
	public static final List ITEMS = new ArrayList();
	
	public ItemToken(String name)
	{
		super(name);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
	}
	
	@Override
	public String getUnlocalizedNameInefficiently(ItemStack stack)
	{
		return (super.getUnlocalizedNameInefficiently(stack) + (isActive(stack) ? "_active" : ""));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (StringUtils.isShiftKeyDown())
		{
			if (isActive(stack))
			{
				tooltip.add(StringUtils.format(StringUtils.localize("tooltip.nhc.shader"), StringUtils.GREEN, StringUtils.ITALIC));
				TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.nhc.token"), ";", StringUtils.GRAY);
				if (ITEMS.size() > 0)
				{
					tooltip.add(StringUtils.localize("tooltip.nhc.shader.appliesto") + ":");
					for (int i = 0; i < ITEMS.size(); ++i)
					{
						if (ITEMS.get(i) instanceof String) tooltip.add("  " + StringUtils.format(StringUtils.localize((String) ITEMS.get(i)), StringUtils.YELLOW, StringUtils.ITALIC));
						else if (ITEMS.get(i) instanceof Item) tooltip.add("  " + StringUtils.format(new ItemStack((Item) ITEMS.get(i)).getDisplayName(), StringUtils.YELLOW, StringUtils.ITALIC));
						else if (ITEMS.get(i) instanceof ItemStack) tooltip.add("  " + StringUtils.format(((ItemStack) ITEMS.get(i)).getDisplayName(), StringUtils.YELLOW, StringUtils.ITALIC));
					}
				}
				else tooltip.add(StringUtils.format(StringUtils.localize("tooltip.nhc.shader.nomods"), StringUtils.RED));
			}
			else
			{
				String desc = StringUtils.localize("tooltip.nhc.token.deny");
				String color = StringUtils.RED;
				for (int i = 0; i < ACCEPTED_USERS.length; ++i)
				{
					if (Minecraft.getMinecraft().player.getName().toLowerCase().equals(ACCEPTED_USERS[i]))
					{
						desc = StringUtils.localize("tooltip.nhc.token.access");
						color = StringUtils.GREEN;
					}
				}
				TooltipHelper.addSplitString(tooltip, desc, ";", color + StringUtils.ITALIC);
			}
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		EnumActionResult res = EnumActionResult.PASS;
		if (hand == EnumHand.MAIN_HAND && !isActive(stack))
		{
			for (int i = 0; i < ACCEPTED_USERS.length; ++i)
			{
				if (player.getName().toLowerCase().equals(ACCEPTED_USERS[i]))
				{
					setActive(stack);
					res = EnumActionResult.SUCCESS;
					if (world.isRemote)
					{
						player.swingArm(hand);
						player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1F, 1F);
					}
				}
			}
		}
		return new ActionResult(res, player.getHeldItem(hand));
	}
	
	public void setActive(ItemStack stack)
	{
		NBTTagCompound compound = stack.getTagCompound();
		if (compound == null)
		{
			compound = new NBTTagCompound();
		}
		compound.setBoolean(TOKEN_NBT, true);
		stack.setTagCompound(compound);
	}
	
	public boolean isActive(ItemStack stack)
	{
		NBTTagCompound compound = stack.getTagCompound();
		if (compound != null && compound.hasKey(TOKEN_NBT))
		{
			return compound.getBoolean(TOKEN_NBT);
		}
		return false;
	}

	@Override
	public String getQualityColor(ItemStack stack)
	{
		return (isActive(stack) ? StringUtils.BRIGHT_BLUE : StringUtils.PURPLE);
	}
}