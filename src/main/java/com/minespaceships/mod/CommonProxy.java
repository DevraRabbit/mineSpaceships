package com.minespaceships.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.minespaceships.mod.blocks.EnergyBlock;
import com.minespaceships.mod.blocks.EngineBlock;
import com.minespaceships.mod.blocks.NavigatorBlock;
import com.minespaceships.mod.blocks.PhaserBlock;
import com.minespaceships.mod.blocks.ShieldBlock;
import com.minespaceships.mod.events.BlockEvent;
import com.minespaceships.mod.events.PlayerTracker;
import com.minespaceships.mod.menu.MenuDisplay;
import com.minespaceships.mod.menu.NoSpaceshipEntityMenu;
import com.minespaceships.mod.menu.SpaceshipMenu;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.AllShipyards;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.target.PhaserTileEntity;

public abstract class CommonProxy {
	 
    /**
     * Event that gets called in an early initialization state of Minecraft
     * @param event
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	MineSpaceships.network = NetworkRegistry.INSTANCE.newSimpleChannel("MyChannel");
    	MineSpaceships.spaceshipNetwork = NetworkRegistry.INSTANCE.newSimpleChannel("Spaceships");
    	MineSpaceships.blockChangeEvents = NetworkRegistry.INSTANCE.newSimpleChannel("BlockChanges");
    	//register our game objects so Minecraft knows how to use them.
    	GameRegistry.registerBlock(new NavigatorBlock(), "NavigatorBlock");
    	GameRegistry.registerBlock(new EnergyBlock(), "EnergyBlock");
    	GameRegistry.registerBlock(new PhaserBlock(), "PhaserBlock");
    	GameRegistry.registerBlock(new EngineBlock(), "EngineBlock");
    	GameRegistry.registerBlock(new ShieldBlock(), "ShieldBlock");
    	GameRegistry.registerTileEntity(ChatRegisterEntity.class, "ChatRegisterEntity");
    	GameRegistry.registerTileEntity(PhaserTileEntity.class, "PhaserTileEntity");
    	    	
    	// Register event listener
    	MinecraftForge.EVENT_BUS.register(new BlockEvent());
    	MinecraftForge.EVENT_BUS.register(new PlayerTracker());
    	FMLCommonHandler.instance().bus().register(new AllShipyards());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)    
    {
    	
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	
    }
    
    public abstract IMenuInterface makeTerminal(EntityPlayer player, ChatRegisterEntity entity);
    public void setupTerminal(EntityPlayer player, ChatRegisterEntity entity, IMenuInterface menu){
		SpaceshipMenu.initMenu(menu);
		NoSpaceshipEntityMenu.initMenu();

		//initialise the menu display.
		MenuDisplay spaceshipMenu = new MenuDisplay(SpaceshipMenu.getRootMenu(), menu);
		MenuDisplay noSpaceshipMenu = new MenuDisplay(NoSpaceshipEntityMenu.getRootMenu(), menu);
		entity.setSpaceshipMenu(spaceshipMenu);
		entity.setNoSpaceshipMenu(noSpaceshipMenu);
    }
    
}
