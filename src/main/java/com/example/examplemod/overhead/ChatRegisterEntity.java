package com.example.examplemod.overhead;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

public class ChatRegisterEntity extends TileEntity {
	public void registerChat(EntityPlayer player){
		BlockChatHandler.registerChatlock(player, this);
	}
}
