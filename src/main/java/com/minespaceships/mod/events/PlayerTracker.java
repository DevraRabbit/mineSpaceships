package com.minespaceships.mod.events;

import com.minespaceships.mod.CommandMessage;
import com.minespaceships.mod.MineSpaceships;
import com.minespaceships.mod.spaceship.Shipyard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerTracker {
	private static int ticks = 0;
	@SubscribeEvent
	@SideOnly(Side.SERVER)
	public void onPlayerJoin(EntityJoinWorldEvent event){
		if(event.entity instanceof EntityPlayer){
			String ships = Shipyard.getShipyard().loadShips(event.world);
			MineSpaceships.spaceshipNetwork.sendTo(new CommandMessage(ships), (EntityPlayerMP)event.entity);
			//sendShipRequest();
		}
	}
//	@SubscribeEvent
//	public void onTick(TickEvent.ClientTickEvent event){
//		if(ticks%500 == 0){
//			ticks = 0;
//			sendShipRequest();
//		}
//		ticks++;
//	}
	
	public static void sendShipRequest(){
		MineSpaceships.spaceshipNetwork.sendToServer(new CommandMessage(Shipyard.getShipyard().getShipCount()+""));
	}
}
