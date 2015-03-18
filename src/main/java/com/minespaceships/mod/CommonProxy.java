package com.minespaceships.mod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.minespaceships.mod.Generator.WorldGenFloatingIslandsSmall;
import com.minespaceships.mod.Generator.WorldGeneratorAsteroid;
import com.minespaceships.mod.Generator.WorldGeneratorTest;
import com.minespaceships.mod.WorldProviderSpace.WorldProviderSpace;
import com.minespaceships.mod.blocks.EnergyBlock;
import com.minespaceships.mod.blocks.EngineBlock;
import com.minespaceships.mod.blocks.GateBlock;
import com.minespaceships.mod.blocks.NavigatorBlock;
import com.minespaceships.mod.blocks.PhaserBlock;
import com.minespaceships.mod.blocks.ShieldBlock;
import com.minespaceships.mod.events.BlockEvent;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.spaceship.Shipyard;

public class CommonProxy {
	
	
	 private Item ItemTest;
	    
	 private static WorldGeneratorTest worldGen = new WorldGeneratorTest(Blocks.gold_block);
	 private static WorldGeneratorAsteroid worldGenAsteroid = new WorldGeneratorAsteroid(Blocks.grass);
	 private static WorldGenFloatingIslandsSmall worldGenFloatingIslands = new WorldGenFloatingIslandsSmall(Blocks.grass, true);
	    
	 
    /**
     * Event that gets called in an early initialization state of Minecraft
     * @param event
     */
	
	
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
     
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	//register our game objects so Minecraft knows how to use them.
    	GameRegistry.registerBlock(new NavigatorBlock(), "NavigatorBlock").setCreativeTab(GoldTab);
        GameRegistry.registerBlock(new EnergyBlock(), "EnergyBlock").setCreativeTab(GoldTab);
        GameRegistry.registerBlock(new PhaserBlock(), "PhaserBlock").setCreativeTab(GoldTab);
        GameRegistry.registerBlock(new EngineBlock(), "EngineBlock").setCreativeTab(GoldTab);
        GameRegistry.registerBlock(new ShieldBlock(), "ShieldBlock").setCreativeTab(GoldTab);
        GameRegistry.registerTileEntity(ChatRegisterEntity.class, "ChatRegisterEntity");
        GameRegistry.registerBlock(new GateBlock(),"GateBlock").setCreativeTab(BarrierTab);
        // Register event listener
        // http://www.minecraftforum.net/forums/archive/tutorials/931112-forge-4-x-events-howto
        MinecraftForge.EVENT_BUS.register(new BlockEvent());
        MinecraftForge.EVENT_BUS.register(Shipyard.getShipyard());
        
        GameRegistry.addShapelessRecipe(new ItemStack (Blocks.diamond_ore,1), Blocks.stone,Items.diamond,Items.diamond,Items.diamond);

        GameRegistry.addShapelessRecipe(new ItemStack (Blocks.gold_ore,1), Blocks.stone,Items.gold_nugget,Items.gold_nugget,Items.gold_nugget);

        GameRegistry.addShapelessRecipe(new ItemStack (ItemTest,1), Blocks.ice,Blocks.fire,Items.blaze_rod);

        GameRegistry.addShapelessRecipe(new ItemStack (Blocks.redstone_ore,1), Blocks.stone,Items.redstone,Items.redstone,Items.redstone);

        GameRegistry.addShapelessRecipe(new ItemStack (Blocks.diamond_block,1), Blocks.coal_block,Blocks.coal_block,Blocks.coal_block,Blocks.coal_block);
    
        
        
        
        DimensionManager.registerProviderType(2, WorldProviderSpace.class,false);
        DimensionManager.registerDimension(2, 2);
        
        ItemTest = new com.minespaceships.mod.items.ItemTest().setCreativeTab(BarrierTab);   
        GameRegistry.registerItem(ItemTest, "TestItem");
        
        GameRegistry.registerWorldGenerator(worldGen, 0);
        GameRegistry.registerWorldGenerator(worldGenAsteroid, 3);
        GameRegistry.registerWorldGenerator(worldGenFloatingIslands, 0);
    	
    	
    	// Register event listener
    	// http://www.minecraftforum.net/forums/archive/tutorials/931112-forge-4-x-events-howto
    	MinecraftForge.EVENT_BUS.register(new BlockEvent());
    	MinecraftForge.EVENT_BUS.register(Shipyard.getShipyard());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)    
    {
    	
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	
    }
}
