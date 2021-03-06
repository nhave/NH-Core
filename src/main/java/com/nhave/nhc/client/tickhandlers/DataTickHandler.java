package com.nhave.nhc.client.tickhandlers;

import java.util.ArrayList;
import java.util.List;

import com.nhave.nhc.api.client.ITickingData;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class DataTickHandler
{
	public static final List<ITickingData> TICKDATA = new ArrayList<ITickingData>();
	
	@SubscribeEvent
    public void onRenderTick(ClientTickEvent evt)
    {
        if (evt.phase == Phase.START)
        {
        	for (ITickingData data: TICKDATA)
			{
        		data.onTick();
			}
        }
    }
}