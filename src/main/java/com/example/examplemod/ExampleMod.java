package com.example.examplemod;

import com.example.examplemod.blocks.NavigatorBlock;
import com.example.examplemod.overhead.BlockChatHandler;
import com.example.examplemod.overhead.ChatGuiHandler;
import com.example.examplemod.overhead.ChatRegisterEntity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";
    
    private static int navigatorBlockId = 1000;
    
    public static Block commandBlock = new NavigatorBlock(navigatorBlockId, "NavigatorBlock");
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {   	
    	Singleton = this;    	
    	
    	GameRegistry.registerBlock(commandBlock, "NavigatorBlock");
    	GameRegistry.registerTileEntity(ChatRegisterEntity.class, "ChatRegisterEntity");
    	
    	BlockChatHandler handler = new BlockChatHandler();
    	MinecraftForge.EVENT_BUS.register(handler);
    	FMLCommonHandler.instance().bus().register(handler);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)    
    {
    	//NetworkRegistry.INSTANCE.registerGuiHandler(this, new ChatGuiHandler());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	
    }
    
    public static ExampleMod instance(){
    	return Singleton;
    }
}