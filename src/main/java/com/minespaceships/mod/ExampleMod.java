package com.minespaceships.mod;

import com.minespaceships.mod.blocks.EnergyBlock;
import com.minespaceships.mod.blocks.EngineBlock;
import com.minespaceships.mod.blocks.NavigatorBlock;
import com.minespaceships.mod.blocks.PhaserBlock;
import com.minespaceships.mod.blocks.ShieldBlock;
import com.minespaceships.mod.events.BlockEvent;
import com.minespaceships.mod.overhead.ChatRegisterEntity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
	private static ExampleMod Singleton;
    public static final String MODID = "minespaceships";
    public static final String VERSION = "1.0";
    
    private static int navigatorBlockId = 1000;
    
    /**
     * Event that gets called in an early initialization state of Minecraft
     * @param event
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {   	
    	Singleton = this;    	
    	//register our game objects so Minecraft knows how to use them.
    	GameRegistry.registerBlock(new NavigatorBlock(), "NavigatorBlock");
    	GameRegistry.registerBlock(new EnergyBlock(), "EnergyBlock");
    	GameRegistry.registerBlock(new PhaserBlock(), "PhaserBlock");
    	GameRegistry.registerBlock(new EngineBlock(), "EngineBlock");
    	GameRegistry.registerBlock(new ShieldBlock(), "ShieldBlock");
    	GameRegistry.registerTileEntity(ChatRegisterEntity.class, "ChatRegisterEntity");
    	
    	// Register event listener
    	// http://www.minecraftforum.net/forums/archive/tutorials/931112-forge-4-x-events-howto
    	MinecraftForge.EVENT_BUS.register(new BlockEvent());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)    
    {
    	
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	
    }
    
    public static ExampleMod instance(){
    	return Singleton;
    }
}