package com.example.examplemod.ovae;

import com.jcraft.jorbis.Block;
import com.minespaceships.mod.blocks.EngineBlock;
import com.minespaceships.mod.blocks.NavigatorBlock;
import com.minespaceships.mod.blocks.PhaserBlock;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Shipyard;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStrongholdPieces.Library;
import net.minecraft.world.gen.structure.StructureVillagePieces.Church;

public class terminalMenu {

	/**
	 * 
	 * @param command
	 * @param player
	 * @param chatEntity
	 * @param chat
	 */
	public static void onCommand(String command, EntityPlayer player, ChatRegisterEntity chatEntity, CustomGuiChat chat){
		//define a very first command to see if it works.
		if(command.equals("hello")){
			//send something to the player to see if we get a feedback from our command.
			player.addChatComponentMessage(new ChatComponentText("I love you!"));
		}
		if(command.equals("what are you?")){
			player.addChatComponentMessage(new ChatComponentText("A cube."));
		}
		if(command.equals("rm Ex")){
			player.addExperience(-1000);
		}
		if(command.equals("add Ex")){
			player.addExperience(1000);
		}
		if(command.equals("jump")){
			player.jump();
		}
		if(command.equals("info")){
			player.addChatComponentMessage(new ChatComponentText(player.toString()));
		}
		if(command.equals("melon")){
			World world = chatEntity.getRemoteWorld();
			BlockPos pos = player.getPosition();
			int size=5;
			for(int x=0;x<size;x++){
				for(int y=0;y<size;y++){
					//Place the wool blocks
					if(!((x==0 && y==0) || (x==0 && y==size-1) || (x==size-1 && y==0) || (x==size-1 && y==4) || (x==0) && (y==2))){
						BlockPos newPos = new BlockPos(pos.getX()+x, pos.getY()+3, pos.getZ()+y);
						world.setBlockState(newPos, Blocks.wool.getDefaultState());
					}

					//Place the navigator block
					if((x==3) && (y==2)){
						BlockPos newPos = new BlockPos(pos.getX()+x, pos.getY()+4, pos.getZ()+y);
						world.setBlockState(newPos, NavigatorBlock.getStateById(198));
					}

					/*//Place the phaser
					if((x==size-1) && (y==1) || (x==size-1) && (y==3)){
						BlockPos newPos = new BlockPos(pos.getX()+x, pos.getY()+4, pos.getZ()+y);
						world.setBlockState(newPos, PhaserBlock.getStateById(200));
					}

					//Place EngineBlocks
					if((x==0) && (y==1) || (x==0) && (y==size)){
						BlockPos newPos = new BlockPos(pos.getX()+x, pos.getY()+3, pos.getZ()+y-1);
						world.setBlockState(newPos, EngineBlock.getStateById(201));
					}*/

					//Place the redstone_block
					if((x==3) && (y==2)){
						BlockPos newPos = new BlockPos(pos.getX()+x, pos.getY()+3, pos.getZ()+y);
						world.setBlockState(newPos, Blocks.redstone_block.getDefaultState());
					}
				}
			}
			chat.clearChat();
			player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Basic spaceship spawned."));
			chat.display("\nPress 'm' to get back.", false);
		}
	}

}
