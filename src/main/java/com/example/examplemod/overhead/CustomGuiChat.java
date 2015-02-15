package com.example.examplemod.overhead;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;

public class CustomGuiChat extends GuiChat{
	private EntityPlayer player;
	public CustomGuiChat(EntityPlayer player) {
		super();
		this.player = player;
	}
	
	@Override
	public void onGuiClosed(){
		String message = super.inputField.getText();
		super.onGuiClosed();
		if(!message.isEmpty()){
			BlockChatHandler.instance().onChat(message, player);
		} else {
			BlockChatHandler.instance().removeChatlock(player);
		}
	}
}
