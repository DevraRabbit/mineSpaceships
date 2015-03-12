package com.minespaceships.mod.overhead;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SpaceshipCommandEvent extends Event {
	public String command;
	public EntityPlayer player;
	public SpaceshipCommandEvent(String command, EntityPlayer player){
		this.command = command;
		this.player = player;
	}
}
