package com.minespaceships.mod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.util.PhaserUtils;
import com.minespaceships.util.Vec3Op;

/**
 * @author ovae.
 * @version 20150331.
 */
public class PhaserHandler implements IMessageHandler<CommandMessage, IMessage>{

	@Override
	public IMessage onMessage(CommandMessage message, MessageContext ctx) {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern.compile("([-+]?[0-9]+),([-+]?[0-9]+),([-+]?[0-9.]+),([-+]?[0-9]+),([-+]?[0-9]+)");
		Matcher matcher = pattern.matcher(message.getText());

		if(matcher.matches()){
			long msgLong = Long.parseLong(matcher.group(1));
			BlockPos origin = BlockPos.fromLong(msgLong);
			long msgLong2 = Long.parseLong(matcher.group(2));
			BlockPos target = BlockPos.fromLong(msgLong2);
			double strength = Double.parseDouble(matcher.group(3));
			int range = Integer.parseInt(matcher.group(4));
			int dimension = Integer.parseInt(matcher.group(5));

			if(Minecraft.getMinecraft().theWorld.provider.getDimensionId() == dimension){				
				PhaserUtils.shootClient(origin, target, strength, range, Minecraft.getMinecraft().theWorld);
			}
		}
		return null; //For no response
	}

}
