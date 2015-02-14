package com.example.examplemod.overhead;

import java.util.HashMap;
import java.util.Map;

import com.example.examplemod.ExampleMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class BlockChatHandler {
	private static Map<EntityPlayer, ChatRegisterEntity> lockedChats;
	private static GuiChat chat = new GuiChat();
	
	public BlockChatHandler(){
		lockedChats = new HashMap<EntityPlayer, ChatRegisterEntity>();
	}
	
	public static void registerChatlock(EntityPlayer playerIn, ChatRegisterEntity entity){
		if(!lockedChats.containsKey(playerIn)){
			lockedChats.put(playerIn, entity);
		}
	}
	@SubscribeEvent
	public void onChat(ServerChatEvent event){
		if(lockedChats.containsKey(event.player)){
			String message = event.message;	
			
			FMLCommonHandler handler = FMLCommonHandler.instance();
			if(handler.getSide().equals(Side.CLIENT)){
				handler.showGuiScreen(chat);
				//Minecraft.getMinecraft().displayGuiScreen(chat);
			}			
		}
	}
}
