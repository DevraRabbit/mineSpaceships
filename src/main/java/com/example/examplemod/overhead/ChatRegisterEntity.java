package com.example.examplemod.overhead;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ChatRegisterEntity extends TileEntity {
	CustomGuiChat oldChat;
	public void registerChat(EntityPlayer player){
		BlockChatHandler.registerChatlock(player, this);		
	}
	public void Activate(EntityPlayer player){
		if(player.equals(Minecraft.getMinecraft().thePlayer)){					
			Minecraft.getMinecraft().displayGuiScreen(new CustomGuiChat(player));
			registerChat(player);
		}
	}
	public void onCommand(String command){
	}
	public void onCommand(String command, EntityPlayer player){
		if(command.equals("hello")){
			player.addChatComponentMessage(new ChatComponentText("I love you!"));
		}
	}
}
