package com.nhave.nhc.client.widget;

import com.nhave.nhc.api.items.IInventoryItem;
import com.nhave.nhc.api.items.IInventoryItemAdvanced;
import com.nhave.nhc.util.PrefixUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WidgetInventory extends WidgetBase
{
	public static ResourceLocation WIDGET_RESOURCE = new ResourceLocation("nhc", "textures/misc/widget.png");

	@Override
	public int getSizeX(ItemStack stack)
	{
		IInventoryItem item = (IInventoryItem) stack.getItem();
		return (18 * item.getInventoryX(stack)) + 10;
	}

	@Override
	public int getSizeY(ItemStack stack)
	{
		IInventoryItem item = (IInventoryItem) stack.getItem();
		return (18 * item.getInventoryY(stack)) + 10;
	}

	@Override
	public void drawWidget(ItemStack stack, int x, int y)
	{
		IInventoryItem item = (IInventoryItem) stack.getItem();
		
		Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(WIDGET_RESOURCE);
		
		int maxX = item.getInventoryX(stack);
		int maxY = item.getInventoryY(stack);
		
		for (int coordY = 0; coordY < maxY; ++coordY)
		{
			for (int coordX = 0; coordX < maxX; ++coordX)
			{
				Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 5, y + (18 * coordY) + 5, 5, 5, 18, 18, 256, 256);
				
				if (coordX == 0) Gui.drawModalRectWithCustomSizedTexture(x, y + (18 * coordY) + 5, 0, 5, 5, 18, 256, 256);
				if (coordX == maxX - 1) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 23, y + (18 * coordY) + 5, 23, 5, 5, 18, 256, 256);
				if (coordY == 0) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 5, y + (18 * coordY), 5, 0, 18, 5, 256, 256);
				if (coordY == maxY - 1) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 5, y + (18 * coordY) + 23, 5, 23, 18, 5, 256, 256);
				if (coordY == 0 && coordX == 0) Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 5, 5, 256, 256);
				if (coordY == 0 && coordX == maxX - 1) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 23, y, 23, 0, 5, 5, 256, 256);
				if (coordY == maxY - 1 && coordX == 0) Gui.drawModalRectWithCustomSizedTexture(x, y + (18 * coordY) + 23, 0, 23, 5, 5, 256, 256);
				if (coordY == maxY - 1 && coordX == maxX - 1) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 23, y + (18 * coordY) + 23, 23, 23, 5, 5, 256, 256);
			}
		}
		
		RenderItem render = mc.getRenderItem();
		
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableDepth();
		int slot = 0;
		for (int coordY = 0; coordY < maxY; ++coordY)
		{
			for (int coordX = 0; coordX < maxX; ++coordX)
			{
				ItemStack invItem = item.getStackInSlot(stack, slot);
				if (invItem != null)
				{
					render.renderItemAndEffectIntoGUI(invItem, x + (18 * coordX) + 6, y + (18 * coordY) + 6);
					int amount = item instanceof IInventoryItemAdvanced ? ((IInventoryItemAdvanced)item).getCountInSlot(stack, slot) : invItem.getCount();
					String text = amount > 999 ? PrefixUtil.getDisplayShort(amount) : String.valueOf(amount);

					render.renderItemOverlayIntoGUI(mc.fontRendererObj, invItem, (int) ((x + (18 * coordX) + 6) * 1D), (int) ((y + (18 * coordY) + 6) * 1D), "");
					boolean advanced = item instanceof IInventoryItemAdvanced && ((IInventoryItemAdvanced)item).renderStackOverlay(stack, slot);
					if (amount > 1 || advanced)
					{
						GlStateManager.pushMatrix();
						GlStateManager.disableLighting();
				        GlStateManager.disableRescaleNormal();
				        GlStateManager.disableDepth();
						if (text.length() > 2)
						{
							GlStateManager.scale(0.8f, 0.8f, 1);
							mc.fontRendererObj.drawStringWithShadow(text, ((x + (18 * coordX)) * 1.25F) + 29 - mc.fontRendererObj.getStringWidth(text), ((y + (18 * coordY)) * 1.25F) + 21, 16777215);
						}
						else mc.fontRendererObj.drawStringWithShadow(text, ((x + (18 * coordX))) + 23 - mc.fontRendererObj.getStringWidth(text), ((y + (18 * coordY))) + 15, 16777215);
						GlStateManager.enableDepth();
				        GlStateManager.enableLighting();
				        GlStateManager.disableBlend();
						GlStateManager.popMatrix();
					}
				}
				++slot;
			}
		}
		GlStateManager.disableDepth();
	}
	
	@Override
	public boolean shouldDraw(ItemStack stack)
	{
		return stack.getItem() instanceof IInventoryItem && ((IInventoryItem) stack.getItem()).getInventoryX(stack) > 0 && ((IInventoryItem) stack.getItem()).getInventoryY(stack) > 0;
	}
}