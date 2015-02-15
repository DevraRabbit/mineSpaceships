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
	private static Map<EntityPlayer, ChatRegisterEntity> lockedChats = new HashMap<EntityPlayer, ChatRegisterEntity>();
	private static BlockChatHandler singleton;
	
	private BlockChatHandler(){
	}
	public static BlockChatHandler instance(){
		if(singleton == null){
			singleton = new BlockChatHandler();
		}
		return singleton;
	}
	
	public static void registerChatlock(EntityPlayer playerIn, ChatRegisterEntity entity){
		lockedChats.put(playerIn, entity);
	}
	public static void removeChatlock(EntityPlayer playerIn){
		lockedChats.remove(playerIn);
	}
	public static void onChat(String message, EntityPlayer player){
		if(lockedChats.containsKey(player)){
			lockedChats.get(player).onCommand(message);
			lockedChats.get(player).onCommand(message, player);
		}
	}
}
