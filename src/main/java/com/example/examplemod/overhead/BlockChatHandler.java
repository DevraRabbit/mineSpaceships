package com.example.examplemod.overhead;

import java.util.HashMap;
import java.util.Map;

import com.example.examplemod.ExampleMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class BlockChatHandler {
	private static Map<EntityPlayer, ChatRegisterEntity> lockedChats;
	private boolean ChatOpened; 
	
	public BlockChatHandler(){
		lockedChats = new HashMap<EntityPlayer, ChatRegisterEntity>();
	}
	
	public static void registerChatlock(EntityPlayer playerIn, ChatRegisterEntity entity){
		lockedChats.put(playerIn, entity);
	}
	@SubscribeEvent
	public void onChat(ServerChatEvent event){
		if(lockedChats.containsKey(event.player)){
			String message = event.message;	
			ChatRegisterEntity entity = lockedChats.get(event.player);
			lockedChats.remove(event.player);
			if(!message.isEmpty()){
				entity.Activate(event.player);
			}
		}
	}
	@SubscribeEvent
	public void onChatOpen(GuiOpenEvent event){
		if(event.gui instanceof GuiChat){
			ChatOpened = true;
		} else if(ChatOpened) {
			ChatOpened = false;
			Minecraft mc = Minecraft.getMinecraft();
			if(mc.ingameGUI != null &&
					(mc.ingameGUI.getChatGUI().getChatComponent(0, 0) == null ||
					(mc.ingameGUI.getChatGUI().getChatComponent(0, 0) != null &&
					mc.ingameGUI.getChatGUI().getChatComponent(0, 0).getFormattedText().isEmpty()))){
				mc.thePlayer.sendChatMessage("closed Console");
				lockedChats.remove(mc.thePlayer);			
			}
		}
	}
}
