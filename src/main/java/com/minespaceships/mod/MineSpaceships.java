package com.minespaceships.mod;

import java.io.File;

import com.minespaceships.mod.blocks.EnergyBlock;
import com.minespaceships.mod.blocks.EngineBlock;
import com.minespaceships.mod.blocks.NavigatorBlock;
import com.minespaceships.mod.blocks.PhaserBlock;
import com.minespaceships.mod.blocks.ShieldBlock;
import com.minespaceships.mod.events.BlockEvent;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.spaceship.Shipyard;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = MineSpaceships.MODID, version = MineSpaceships.VERSION)
public class MineSpaceships
{
	private static MineSpaceships Singleton;
    public static final String MODID = "minespaceships";
    public static final String VERSION = "1.0";
    public static final String SpaceshipSavePath = "./Spaceships/";
    
    private static int navigatorBlockId = 1000;
    
    @SidedProxy(clientSide="com.minespaceships.mod.ClientProxy", serverSide="com.minespaceships.mod.ServerProxy")
    public static CommonProxy proxy;
    
    /**
     * Event that gets called in an early initialization state of Minecraft
     * @param event
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	
    	Singleton = this;    	
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)    
    {
    	proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	proxy.postInit(event);
    }
    
    public static MineSpaceships instance(){
    	return Singleton;
    }
}