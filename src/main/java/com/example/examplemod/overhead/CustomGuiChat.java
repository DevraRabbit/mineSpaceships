package com.example.examplemod.overhead;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
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
	protected void keyTyped(char typedChar, int keyCode) throws IOException{
		if(keyCode != 28 && keyCode != 156 && keyCode != 1){
			super.keyTyped(typedChar, keyCode);
		} else {
			if(keyCode == 1){
				BlockChatHandler.removeChatlock(player);
				this.mc.displayGuiScreen((GuiScreen)null);
			} else {
				BlockChatHandler.onChat(this.inputField.getText().trim(), player);
				this.inputField.setText("");
			}
		}
	}
}
