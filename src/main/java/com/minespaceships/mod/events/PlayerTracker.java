package com.minespaceships.mod.events;

import com.minespaceships.mod.CommandMessage;
import com.minespaceships.mod.MineSpaceships;
import com.minespaceships.mod.spaceship.Shipyard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerTracker {
	private static int ticks = 0;
	@SubscribeEvent
	@SideOnly(Side.SERVER)
	public void onPlayerJoin(EntityJoinWorldEvent event){
		if(event.entity instanceof EntityPlayer){
			Shipyard.getShipyard().safe(new Save(event.world));
			String ships = Shipyard.getShipyard().loadShips(event.world);
			MineSpaceships.spaceshipNetwork.sendTo(new CommandMessage(ships), (EntityPlayerMP)event.entity);
			//sendShipRequest();
		}
	}
	@SubscribeEvent
	@SideOnly(Side.SERVER)
	public void onPlayerLeave(PlayerLoggedOutEvent event){
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
