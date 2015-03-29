package com.minespaceships.mod.overhead;

import net.minecraft.entity.player.EntityPlayer;

public interface IMenuInterface {
	/**
	 * @return ChatRegisterEntity entity
	 */
	public ChatRegisterEntity getChatRegisterEntity();
	/**
	 * Returns the player entity.
	 * @return player
	 */
	public EntityPlayer getPlayerEntity();
	
	public void display(String command, EntityPlayer player, boolean clear);
	public void clearChat();
}
