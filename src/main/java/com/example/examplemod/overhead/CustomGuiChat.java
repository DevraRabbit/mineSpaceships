package com.example.examplemod.overhead;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class CustomGuiChat extends GuiChat{
	private EntityPlayer player;
	private ChatRegisterEntity entity;
	public CustomGuiChat(EntityPlayer player, ChatRegisterEntity entity) {
		super();
		this.player = player;
		this.entity = entity;
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException{
		if(keyCode != 28 && keyCode != 156 && keyCode != 1){
			super.keyTyped(typedChar, keyCode);
		} else {
			if(keyCode == 1){
				this.mc.displayGuiScreen((GuiScreen)null);
			} else {
				String command = this.inputField.getText().trim();
				entity.onCommand(command);
				entity.onCommand(command, player);
				this.inputField.setText("");
			}
		}
	}
}
