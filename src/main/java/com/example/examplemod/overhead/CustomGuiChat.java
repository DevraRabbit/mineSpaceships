package com.example.examplemod.overhead;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class CustomGuiChat extends GuiChat{
	private EntityPlayer player;
	private boolean isClosing = true;
	public CustomGuiChat(EntityPlayer player) {
		super();
		this.player = player;
	}
	public void preventReopening(){
		isClosing = false;
	}
	
	@Override
	public void onGuiClosed(){	
		if(isClosing){		
			String message = super.inputField.getText();
			super.onGuiClosed();
			isClosing = false;
			BlockChatHandler.instance().onChat(message, player);
		}
	}
}
