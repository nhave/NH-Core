package com.nhave.nhc.client.render;

import org.lwjgl.opengl.GL11;

import com.nhave.nhc.tiles.TileEntityDisplay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderTileDisplay extends TileEntitySpecialRenderer
{
	private final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
	
	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		
		renderTileEntityAt(te, x, y, z);
	}
	
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z)
	{
		TileEntityDisplay tileEntity = (TileEntityDisplay) tile;
		if (tileEntity.getItemStack() != null)
		{
			int meta = tileEntity.getBlockMetadata();
			ItemStack stack = tileEntity.getItemStack();
			
			GL11.glPushMatrix();
			
			GL11.glTranslatef((float)x + 0.5F, (float)y + 0.75F, (float)z + 0.5F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			
			GL11.glRotatef(tileEntity.itemRotaion, 0F, 1F, 0F);
			
			this.itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
			GL11.glPopMatrix();
		}
	}
}