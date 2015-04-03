package com.minespaceships.mod.blocks;

import com.minespaceships.mod.MineSpaceships;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.spaceship.ISpaceshipPart;
import com.minespaceships.mod.spaceship.Shipyard;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStone;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class NavigatorBlock extends BlockStairs implements ITileEntityProvider, ISpaceshipPart{
	/**
	 * Block to implement an interface for the Spaceships
	 * @param name The display name of the block
	 */
    public NavigatorBlock() {
    	//Make a Block with certain attributes. Sponge for example is a quite soft block. Does not affect
    	//the texture of the block.
    	super(new BlockPlanks().getDefaultState());
    	this.setUnlocalizedName("NavigatorBlock");
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    /**
     * Method that is called when the block is rightclicked
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		//Get the TileEntity from the world, which is the object that is associated to the position
		//of our block and that can execute extra code. In our example this is opening a console.
		TileEntity entity = worldIn.getTileEntity(pos);
		if(entity instanceof ChatRegisterEntity){
			//Activate the entity
			((ChatRegisterEntity)entity).Activate(playerIn);
		}
    	return true;
    }
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){    	
    	return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
    }
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
    	worldIn.removeTileEntity(pos);
    	Shipyard.getShipyard(worldIn).removeBlock(pos, worldIn);
    	super.breakBlock(worldIn, pos, state);
    }
    @Override
    public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest){
    	world.removeTileEntity(pos);
    	Shipyard.getShipyard(world).removeBlock(pos, world);
    	return super.removedByPlayer(world, pos, player, willHarvest);
    }

    /**
     * @returns the TileEntity associated with this block. This way Minecraft can register it into the world
     */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		//As this block has the tileEntity that opens our console we return it so it gets placed wherever
		//the Block is placed.
		return new ChatRegisterEntity();
	}
	
	@Override
    public boolean isFullBlock()
    {
        return false;
    }

	@Override
	public boolean isFullCube()
    {
        return false;
    }	

}
