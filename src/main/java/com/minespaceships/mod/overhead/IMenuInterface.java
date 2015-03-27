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
	
	public void display(String command, boolean clear);
	public void clearChat();
}
