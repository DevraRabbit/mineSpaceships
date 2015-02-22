package com.minespaceships.mod.blocks;

import com.minespaceships.mod.ExampleMod;
import com.minespaceships.mod.overhead.ChatRegisterEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class NavigatorBlock extends Block implements ITileEntityProvider{
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	/**
	 * Block to implement an interface for the Spaceships
	 * @param name The display name of the block
	 */
    public NavigatorBlock() {
    	//Make a Block with certain attributes. Sponge for example is a quite soft block. Does not affect
    	//the texture of the block.
    	super(new Material(MapColor.blueColor));
    	this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    	this.setUnlocalizedName("NavigatorBlock");
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    /**
     * Method that is called when the block is rightclicked
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {    	 
    	//Check if this code is executed on the client. It is sometimes important to check if
    	//we do things locally or on the server. In this case we act on the client.
    	if(worldIn.isRemote){
    		//Send the player a message. This is the common way of sending texts to a single player using the chat.
			ChatComponentText text = new ChatComponentText("Opening Console");
			playerIn.addChatComponentMessage(text);	 
			//Get the TileEntity from the world, which is the object that is associated to the position
			//of our block and that can execute extra code. In our example this is opening a console.
			TileEntity entity = worldIn.getTileEntity(pos);
			if(entity instanceof ChatRegisterEntity){
				//Activate the entity
				((ChatRegisterEntity)entity).Activate(playerIn);
			}
    	}
    	//returns true to prevent placing a block (which would be the default behavior for rightclicking)
    	return true;
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
	@Override
	protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING});
    }
	public int getMetaFromState(IBlockState state)
    {
        int i;

        switch (NavigatorBlock.SwitchEnumFacing.FACING_LOOKUP[((EnumFacing)state.getValue(FACING)).ordinal()])
        {
            case 1:
                i = 1;
                break;
            case 2:
                i = 2;
                break;
            case 3:
                i = 3;
                break;
            case 4:
                i = 4;
                break;
            case 5:
            default:
                i = 5;
                break;
            case 6:
                i = 0;
        }

        return i;
    }

    static final class SwitchEnumFacing
    {
        static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];
        private static final String __OBFID = "CL_00002131";

        static
        {
            try
            {
                FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError var6)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError var5)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.UP.ordinal()] = 5;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 6;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing, true) ? this.getDefaultState().withProperty(FACING, facing) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
    }

}
