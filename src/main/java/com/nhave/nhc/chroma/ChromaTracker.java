package com.nhave.nhc.chroma;

import java.util.List;

import com.nhave.nhc.api.client.ITickingData;
import com.nhave.nhc.util.StringUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChromaTracker extends Chroma implements ITickingData
{
	private static final int RATE = 5;
	private int tick = 0;
	private int step = 0;
	private int[] colorCodes = new int[] {16711680, 1644825};
	
	public ChromaTracker()
	{
		super(0);
	}
	
	@Override
	public int getColor()
	{
		if (isHostileNearby(3D)) return this.colorCodes[this.step];
		else if (isHostileNearby(5D)) return 16711680;
		else if (isHostileNearby(6D)) return 16724736;
		else if (isHostileNearby(7D)) return 16737792;
		else if (isHostileNearby(8D)) return 16750848;
		else if (isHostileNearby(9D)) return 16763904;
		else if (isHostileNearby(10D)) return 16776960;
		else if (isHostileNearby(11D)) return 13434624;
		else if (isHostileNearby(12D)) return 9895680;
		else if (isHostileNearby(13D)) return 6749952;
		else if (isHostileNearby(14D)) return 3407616;
		
		return 65280;
	}
	
	@Override
	public String getQualityColor()
	{
		return StringUtils.LIGHT_BLUE;
	}

	@SideOnly(Side.CLIENT)
	public boolean isHostileNearby(double range)
	{
		Minecraft mc = Minecraft.getMinecraft();
		List<Entity> entities = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, mc.player.getEntityBoundingBox().expand(range, range, range));
		for (int i = 0; i < entities.size(); ++i)
		{
			if (entities.get(i) instanceof EntityMob) return true;
		}
		return false;
	}
	
	@Override
	public void onTick()
	{
		++this.tick;
		if (tick >= RATE)
		{
			this.tick = 0;
			++this.step;
			if (this.step < 0 || this.step >= colorCodes.length) this.step = 0;
		}
	}
}