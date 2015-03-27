package com.minespaceships.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.minespaceships.mod.Generator.WorldGenFloatingIslandsSmall;
import com.minespaceships.mod.Generator.WorldGeneratorTest;
import com.minespaceships.mod.blocks.EnergyBlock;
import com.minespaceships.mod.blocks.EngineBlock;
import com.minespaceships.mod.blocks.GateBlock;
import com.minespaceships.mod.blocks.NavigatorBlock;
import com.minespaceships.mod.blocks.PhaserBlock;
import com.minespaceships.mod.blocks.ShieldBlock;
import com.minespaceships.mod.events.BlockEvent;
import com.minespaceships.mod.events.PlayerTracker;
import com.minespaceships.mod.items.ItemPhaser;
import com.minespaceships.mod.items.ItemTest;
import com.minespaceships.mod.menu.MenuDisplay;
import com.minespaceships.mod.menu.NoSpaceshipEntityMenu;
import com.minespaceships.mod.menu.SpaceshipMenu;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.AllShipyards;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.target.PhaserTileEntity;

public abstract class CommonProxy {
	 
	private static WorldGeneratorTest worldGen = new WorldGeneratorTest(Blocks.gold_block);
	private static WorldGenFloatingIslandsSmall worldGenFloatingIslands = new WorldGenFloatingIslandsSmall(Blocks.grass, true);
	    
	 
	 
	 @Instance(value="minespaceships")
     public static MineSpaceships instance;
     public static CreativeTabs BarrierTab = new CreativeTabs("BarrierTab"){

       
       public Item getTabIconItem() {
         
         return new ItemStack(Blocks.barrier).getItem();
         
       }
     
     };
     
     public static CreativeTabs GoldTab = new CreativeTabs("GoldTab"){

       
       public Item getTabIconItem() {
         
         return new ItemStack(Blocks.gold_block).getItem();
         
       }
     };
	 
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
    	GameRegistry.registerBlock(new NavigatorBlock(), "NavigatorBlock").setCreativeTab(GoldTab);
        GameRegistry.registerBlock(new EnergyBlock(), "EnergyBlock").setCreativeTab(GoldTab);
        GameRegistry.registerBlock(new PhaserBlock(), "PhaserBlock").setCreativeTab(GoldTab);
        GameRegistry.registerBlock(new EngineBlock(), "EngineBlock").setCreativeTab(GoldTab);
        GameRegistry.registerBlock(new ShieldBlock(), "ShieldBlock").setCreativeTab(GoldTab);
        GameRegistry.registerBlock(new GateBlock(),"GateBlock").setCreativeTab(BarrierTab);
    	GameRegistry.registerTileEntity(ChatRegisterEntity.class, "ChatRegisterEntity");
    	GameRegistry.registerTileEntity(PhaserTileEntity.class, "PhaserTileEntity");
    	GameRegistry.registerItem(new ItemTest(),"TestItem");
    	GameRegistry.registerItem(new ItemPhaser(), "Phaser");
    	    	
    	// Register event listener
    	MinecraftForge.EVENT_BUS.register(new BlockEvent());
    	MinecraftForge.EVENT_BUS.register(new PlayerTracker());
    	FMLCommonHandler.instance().bus().register(new AllShipyards());    	
    	
    	 GameRegistry.addShapelessRecipe(new ItemStack (new NavigatorBlock(),1), Blocks.iron_block,Blocks.stone_button,Items.clock,Items.redstone);

         GameRegistry.addShapelessRecipe(new ItemStack (new EnergyBlock(),1), Blocks.coal_block,Blocks.redstone_block,Items.blaze_rod);

         GameRegistry.addShapelessRecipe(new ItemStack (new ItemTest(),1), Blocks.ice,Blocks.fire,Items.blaze_rod);

         GameRegistry.addShapelessRecipe(new ItemStack (new PhaserBlock(),1), Blocks.dispenser,Items.blaze_rod,Items.iron_ingot,Items.iron_ingot);

         GameRegistry.addShapelessRecipe(new ItemStack (new EngineBlock(),1), new EnergyBlock(),Items.emerald,Blocks.hopper);
     
//         GameRegistry.addShapedRecipe(new ItemStack(new ShieldBlock(),1) , "ggg",
//        		 														     "g#g",
//        		 														     "ggg",Blocks.glass,"g",Blocks.prismarine,"#");
    
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
