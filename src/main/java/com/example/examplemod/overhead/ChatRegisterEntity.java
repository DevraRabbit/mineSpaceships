package com.example.examplemod.overhead;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ChatRegisterEntity extends TileEntity {
	public void registerChat(EntityPlayer player){
		BlockChatHandler.registerChatlock(player, this);		
	}
	public void Activate(EntityPlayer player){
		registerChat(player);
		if(player.equals(Minecraft.getMinecraft().thePlayer)){
			Minecraft.getMinecraft().displayGuiScreen(new CustomGuiChat(player));
		}
	}
}
