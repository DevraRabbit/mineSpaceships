package com.example.examplemod.overhead;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockChatHandler {
	private static Map<EntityPlayer, ChatRegisterEntity> lockedChats;
	
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
			if(event.player == event.player.worldObj.getPlayerEntityByUUID(Minecraft.getMinecraft().thePlayer.getPersistentID())){
				Minecraft.getMinecraft().displayGuiScreen(new GuiChat());
			}
		}
	}
}
