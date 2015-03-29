package com.minespaceships.mod.overhead;

import net.minecraft.entity.player.EntityPlayer;

public class ServerMenuConnection implements IMenuInterface {
	private EntityPlayer player;
	private ChatRegisterEntity entity;
	
	public ServerMenuConnection(EntityPlayer player, ChatRegisterEntity entity){
		this.player = player;
		this.entity = entity;
	}
	@Override
	public ChatRegisterEntity getChatRegisterEntity() {
		return entity;
	}
	@Override
	public EntityPlayer getPlayerEntity() {
		return player;
	}
	@Override
	public void display(String command, EntityPlayer player, boolean clear) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void clearChat() {
		// TODO Auto-generated method stub
		
	}
}
