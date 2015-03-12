package com.minespaceships.mod.overhead;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;

public class CustomGuiChat extends GuiChat{
	private EntityPlayer player;
	private ChatRegisterEntity entity;
	
	/**
	 * Creates a custom GUI that opens a steady interface for the player to communicate with the entity
	 * @param player
	 * @param entity
	 */
	public CustomGuiChat(EntityPlayer player, ChatRegisterEntity entity) {
		super();
		this.player = player;
		this.entity = entity;
	}

	/**
	 * Override the keyTyped method to prevent the chat from closing when we hit enter or escape
	 */
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException{
		//check if the keycode matches ESC or one of the two Enter-keys...
		
		//if not, run the default code
		if(keyCode != 28 && keyCode != 156 && keyCode != 1){
			super.keyTyped(typedChar, keyCode);
		//else, run custom code
		} else {
			//on Escape
			if(keyCode == 1){
				//kill the Gui-screen and let minecraft display the normal game
				this.mc.displayGuiScreen((GuiScreen)null);
				this.clearChat();
			}
			//on Enter
			else if(keyCode == 28 || keyCode == 156) {
				//to commit a command, we send it to the entity and clear the input
				String command = this.inputField.getText().trim();
				entity.onCommand(command);
				entity.onCommand(command, player);
				this.inputField.setText("");
			}
		}
	}

	/**
	 * Prints a message into the chat console.
	 * if clear is set to true, the console is going to clear all messages at the end.
	 * @param message String
	 * @param clear boolean
	 */
	public void display(final String message, final boolean clear){
		if(clear){
			this.clearChat();
		}
		
		this.mc.thePlayer.addChatComponentMessage(new ChatComponentText(message));
	}
	/**
	 * Clears the chat.
	 */
	public void clearChat(){
		this.mc.ingameGUI.getChatGUI().clearChatMessages();
	}
	
	/**
	 * @return ChatRegisterEntity entity
	 */
	public ChatRegisterEntity getChatRegisterEntity(){
		return entity;
	}

	/**
	 * 
	 * @return GuiTextField inputField
	 */
	public GuiTextField getInputField(){
		return inputField;
	}

	/**
	 * 
	 */
	public void setTileEntity(TileEntity tileEntity){
		this.entity = (ChatRegisterEntity) tileEntity;
	}
}
