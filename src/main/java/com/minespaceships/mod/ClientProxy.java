package com.minespaceships.mod;

import com.minespaceships.mod.events.PlayerTracker;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    	MineSpaceships.network.registerMessage(CommandMessage.HandlerClient.class, CommandMessage.class, 0, Side.CLIENT);
    	MineSpaceships.blockChangeEvents.registerMessage(BlockHandlerClient.class, CommandMessage.class, 0, Side.CLIENT);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
