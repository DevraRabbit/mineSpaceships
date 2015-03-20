package com.minespaceships.mod.worldanalysation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldMock extends World {

	private World world;
	private ArrayList<BlockPos> setBlocks;
	private ArrayList<BlockPos> removedBlocks;
	private ArrayList<BlockPos> lightChangedBlocks;
	
	public WorldMock(World infoContainer) {
		super(null, null, null, null, true);
		world = infoContainer;
		setBlocks = new ArrayList<BlockPos>();
		removedBlocks = new ArrayList<BlockPos>();
		lightChangedBlocks = new ArrayList<BlockPos>();
	}	
	
	public ArrayList<BlockPos> nextSetBlocks(){
		ArrayList<BlockPos> out = (ArrayList<BlockPos>) setBlocks.clone();
		setBlocks.clear();
		return out;
	}
	public ArrayList<BlockPos> nextRemovedBlocks(){
		ArrayList<BlockPos> out = (ArrayList<BlockPos>) removedBlocks.clone();
		removedBlocks.clear();
		return out;
	}
	public ArrayList<BlockPos> nextLightChangedBlocks(){
		ArrayList<BlockPos> out = (ArrayList<BlockPos>) lightChangedBlocks.clone();
		lightChangedBlocks.clear();
		return out;
	}
	
	@Override
    public BiomeGenBase getBiomeGenForCoords(final BlockPos pos)
    {
        return world.provider.getBiomeGenForCoords(pos);
    }
	@Override
    public BiomeGenBase getBiomeGenForCoordsBody(final BlockPos pos)
    {
        return world.getBiomeGenForCoordsBody(pos);
    }
	@Override
    public WorldChunkManager getWorldChunkManager()
    {
        return world.provider.getWorldChunkManager();
    }
	@Override
    public void initialize(WorldSettings settings){}

	@Override
    @SideOnly(Side.CLIENT)
    public void setInitialSpawnLocation(){}
	@Override
    public Block getGroundAboveSeaLevel(BlockPos pos)
    {
        return world.getGroundAboveSeaLevel(pos);
    }
	@Override
    public boolean isAirBlock(BlockPos pos)
    {
        return world.isAirBlock(pos);
    }
	@Override
    public boolean isBlockLoaded(BlockPos pos)
    {
        return world.isBlockLoaded(pos);
    }
	@Override
    public boolean isBlockLoaded(BlockPos pos, boolean allowEmpty)
    {
        return world.isBlockLoaded(pos);
    }
	@Override
    public boolean isAreaLoaded(BlockPos center, int radius)
    {
        return world.isAreaLoaded(center, radius);
    }
	@Override
    public boolean isAreaLoaded(BlockPos center, int radius, boolean allowEmpty)
    {
        return world.isAreaLoaded(center, radius, allowEmpty);
    }
	@Override
    public boolean isAreaLoaded(BlockPos from, BlockPos to)
    {
        return world.isAreaLoaded(from, to);
    }
	@Override
    public boolean isAreaLoaded(BlockPos from, BlockPos to, boolean allowEmpty)
    {
        return world.isAreaLoaded(from, to, allowEmpty);
    }
	@Override
    public boolean isAreaLoaded(StructureBoundingBox box)
    {
        return world.isAreaLoaded(box);
    }
    @Override
    public boolean isAreaLoaded(StructureBoundingBox box, boolean allowEmpty)
    {
        return world.isAreaLoaded(box, allowEmpty);
    }
    @Override
    public Chunk getChunkFromBlockCoords(BlockPos pos)
    {
        return world.getChunkFromBlockCoords(pos);
    }
    @Override
    public Chunk getChunkFromChunkCoords(int chunkX, int chunkZ)
    {
        return world.getChunkFromChunkCoords(chunkX, chunkZ);
    }
    @Override
    public boolean setBlockState(BlockPos pos, IBlockState newState, int flags)
    {
        setBlocks.add(pos);
        return true;
    }

    @Override
    public void markAndNotifyBlock(BlockPos pos, Chunk chunk, IBlockState old, IBlockState new_, int flags){}
    @Override
    public boolean setBlockToAir(BlockPos pos)
    {
        removedBlocks.add(pos);
    }
    @Override
    public boolean destroyBlock(BlockPos pos, boolean dropBlock)
    {
    	removedBlocks.add(pos);
    }
    @Override
    public boolean setBlockState(BlockPos pos, IBlockState state)
    {
        return this.setBlockState(pos, state, 3);
    }
    @Override
    public void markBlockForUpdate(BlockPos pos){}
    @Override
    public void notifyNeighborsRespectDebug(BlockPos pos, Block blockType){}
    @Override
    public void markBlocksDirtyVertical(int x1, int z1, int x2, int z2){}
    @Override
    public void markBlockRangeForRenderUpdate(BlockPos rangeMin, BlockPos rangeMax){}
    @Override
    public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2){}   
    @Override
    public boolean canSeeSky(BlockPos pos)
    {
        return world.canSeeSky(pos);
    }
    @Override
    public boolean canBlockSeeSky(BlockPos pos)
    {
       return world.canBlockSeeSky(pos);
    }
    @Override
    public int getLight(BlockPos pos)
    {
        return world.getLight(pos);
    }
    @Override
    public int getLightFromNeighbors(BlockPos pos)
    {
        return world.getLightFromNeighbors(pos);
    }
    @Override
    public int getLight(BlockPos pos, boolean checkNeighbors)
    {
        return world.getLight(pos, checkNeighbors);
    }
    @Override
    public BlockPos getHorizon(BlockPos pos)
    {
        return world.getHorizon(pos);
    }
    @Override
    public int getChunksLowestHorizon(int x, int z)
    {
        return world.getChunksLowestHorizon(x, z);
    }
    @Override
    public int getLightFor(EnumSkyBlock type, BlockPos pos)
    {
        return world.getLightFor(type, pos);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos p_175705_2_)
    {
        return world.getLightFromNeighborsFor(type, p_175705_2_);
    }
    @Override
    public void setLightFor(EnumSkyBlock type, BlockPos pos, int lightValue)
    {
        lightChangedBlocks.add(pos);
    }
    @Override
    public void notifyLightSet(BlockPos pos){}
    @Override
    @SideOnly(Side.CLIENT)
    public int getCombinedLight(BlockPos pos, int p_175626_2_)
    {
        return world.getCombinedLight(pos, p_175626_2_);
    }
    @Override
    public float getLightBrightness(BlockPos pos)
    {
        return world.getLightBrightness(pos);
    }
    @Override
    public IBlockState getBlockState(BlockPos pos)
    {
        return world.getBlockState(pos);
    }
    @Override
    public boolean isDaytime()
    {
        return world.isDaytime();
    }
    @Override
    public MovingObjectPosition rayTraceBlocks(Vec3 p_72933_1_, Vec3 p_72933_2_)
    {
        return world.rayTraceBlocks(p_72933_1_, p_72933_2_);
    }
    @Override
    public MovingObjectPosition rayTraceBlocks(Vec3 p_72901_1_, Vec3 p_72901_2_, boolean p_72901_3_)
    {
        return world.rayTraceBlocks(p_72901_1_, p_72901_2_, p_72901_3_);
    }
    @Override
    public MovingObjectPosition rayTraceBlocks(Vec3 p_147447_1_, Vec3 p_147447_2_, boolean p_147447_3_, boolean p_147447_4_, boolean p_147447_5_)
    {
        return world.rayTraceBlocks(p_147447_1_, p_147447_2_, p_147447_3_, p_147447_4_, p_147447_5_);
    }
    @Override
    public void playSoundAtEntity(Entity p_72956_1_, String p_72956_2_, float p_72956_3_, float p_72956_4_){}
    @Override
    public void playSoundToNearExcept(EntityPlayer p_85173_1_, String p_85173_2_, float p_85173_3_, float p_85173_4_){}
    @Override
    public void playSoundEffect(double x, double y, double z, String soundName, float volume, float pitch){}
    @Override
    public void playSound(double x, double y, double z, String soundName, float volume, float pitch, boolean distanceDelay) {}
    @Override
    public void playRecord(BlockPos p_175717_1_, String p_175717_2_){}
    @Override
    public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int ... p_175688_14_){}
    @Override
    @SideOnly(Side.CLIENT)
    public void spawnParticle(EnumParticleTypes particleType, boolean p_175682_2_, double p_175682_3_, double p_175682_5_, double p_175682_7_, double p_175682_9_, double p_175682_11_, double p_175682_13_, int ... p_175682_15_){}
    @Override
    public boolean addWeatherEffect(Entity p_72942_1_){}
    @Override
    public boolean spawnEntityInWorld(Entity p_72838_1_){/*TODO: make entity list!*/}
    @Override
    public void onEntityAdded(Entity p_72923_1_){}
    @Override
    public void onEntityRemoved(Entity p_72847_1_){/*TODO: make removed entity list!*/}
    @Override
    public void removeEntity(Entity p_72900_1_){/*TODO: make removed entity list!*/}
    @Override
    public void removePlayerEntityDangerously(Entity p_72973_1_){}
    @Override
    public void addWorldAccess(IWorldAccess p_72954_1_){}
    @Override
    public List getCollidingBoundingBoxes(Entity p_72945_1_, AxisAlignedBB p_72945_2_)
    {
    	return world.getCollidingBoundingBoxes(p_72945_1_, p_72945_2_);
    }
    @Override
    public boolean isInsideBorder(WorldBorder p_175673_1_, Entity p_175673_2_)
    {
        return world.isInsideBorder(p_175673_1_, p_175673_2_);
    }
    @Override
    public List func_147461_a(AxisAlignedBB p_147461_1_)
    {
    	return world.func_147461_a(p_147461_1_);
    }
    @Override
    public int calculateSkylightSubtracted(float p_72967_1_)
    {
        return world.calculateSkylightSubtracted(p_72967_1_);
    }

    /**
     * The current sun brightness factor for this dimension.
     * 0.0f means no light at all, and 1.0f means maximum sunlight.
     * Highly recommended for sunlight detection like solar panel.
     *
     * @return The current brightness factor
     * */
    @Override
    public float getSunBrightnessFactor(float p_72967_1_)
    {
        return world.getSunBrightnessFactor(p_72967_1_);
    }
    @Override
    public void removeWorldAccess(IWorldAccess p_72848_1_)
    {
        this.worldAccesses.remove(p_72848_1_);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float p_72971_1_)
    {
        return world.getSunBrightness(p_72971_1_);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightnessBody(float p_72971_1_)
    {
	    return world.getSunBrightnessBody(p_72971_1_);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 getSkyColor(Entity p_72833_1_, float p_72833_2_)
    {
        return world.getSkyColor(p_72833_1_, p_72833_2_);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 getSkyColorBody(Entity p_72833_1_, float p_72833_2_)
    {
        return world.getSkyColorBody(p_72833_1_, p_72833_2_);
    }
    @Override
    public float getCelestialAngle(float p_72826_1_)
    {
        return world.getCelestialAngle(p_72826_1_);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public int getMoonPhase()
    {
        return world.getMoonPhase();
    }
    @Override
    public float getCurrentMoonPhaseFactor()
    {
        return world.getCurrentMoonPhaseFactor();
    }
    @Override
    public float getCurrentMoonPhaseFactorBody()
    {
        return world.getCurrentMoonPhaseFactorBody();
    }
    @Override
    public float getCelestialAngleRadians(float p_72929_1_)
    {
        return world.getCelestialAngleRadians(p_72929_1_);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 getCloudColour(float p_72824_1_)
    {
        return world.getCloudColour(p_72824_1_);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 drawCloudsBody(float p_72824_1_){}
    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float p_72948_1_)
    {
        return world.getFogColor(p_72948_1_);
    }
    @Override
    public BlockPos getPrecipitationHeight(BlockPos p_175725_1_)
    {
        return world.getPrecipitationHeight(p_175725_1_);
    }
    @Override
    public BlockPos getTopSolidOrLiquidBlock(BlockPos pos)
    {
	    return world.getTopSolidOrLiquidBlock(pos);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float p_72880_1_)
    {
        return world.getStarBrightness(p_72880_1_);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightnessBody(float p_72880_1_)
    {
        return world.getStarBrightnessBody(p_72880_1_);
    }
    @Override
    public void scheduleUpdate(BlockPos pos, Block blockIn, int delay) {}
    @Override
    public void updateBlockTick(BlockPos p_175654_1_, Block p_175654_2_, int p_175654_3_, int p_175654_4_) {}
    @Override
    public void func_180497_b(BlockPos pos, Block p_180497_2_, int p_180497_3_, int p_180497_4_) {}
    @Override
    public void updateEntities(){}

    public boolean addTileEntity(TileEntity tile)
    {
        List dest = processingLoadedTiles ? addedTileEntityList : loadedTileEntityList;
        boolean flag = dest.add(tile);

        if (flag && tile instanceof IUpdatePlayerListBox)
        {
            this.tickableTileEntities.add(tile);
        }

        return flag;
    }

    public void addTileEntities(Collection tileEntityCollection)
    {
        if (this.processingLoadedTiles)
        {
            this.addedTileEntityList.addAll(tileEntityCollection);
        }
        else
        {
            Iterator iterator = tileEntityCollection.iterator();

            while (iterator.hasNext())
            {
                TileEntity tileentity = (TileEntity)iterator.next();
                this.loadedTileEntityList.add(tileentity);

                if (tileentity instanceof IUpdatePlayerListBox)
                {
                    this.tickableTileEntities.add(tileentity);
                }
            }
        }
    }
    @Override
    public void updateEntity(Entity ent){}
    @Override
    public void updateEntityWithOptionalForce(Entity p_72866_1_, boolean p_72866_2_){}
    @Override
    public boolean checkNoEntityCollision(AxisAlignedBB p_72855_1_)
    {
        return world.checkNoEntityCollision(p_72855_1_);
    }
    @Override
    public boolean checkNoEntityCollision(AxisAlignedBB p_72917_1_, Entity p_72917_2_)
    {
        return world.checkNoEntityCollision(p_72917_1_, p_72917_2_);
    }
    @Override
    public boolean checkBlockCollision(AxisAlignedBB p_72829_1_)
    {
        return world.checkBlockCollision(p_72829_1_);
    }
    @Override
    public boolean isAnyLiquid(AxisAlignedBB p_72953_1_)
    {
        return world.isAnyLiquid(p_72953_1_);
    }
    @Override
    public boolean func_147470_e(AxisAlignedBB p_147470_1_)
    {
        return world.func_147470_e(p_147470_1_);
    }
    @Override
    public boolean handleMaterialAcceleration(AxisAlignedBB p_72918_1_, Material p_72918_2_, Entity p_72918_3_){ return false;}
    @Override
    public boolean isMaterialInBB(AxisAlignedBB p_72875_1_, Material p_72875_2_)
    {
        return world.isMaterialInBB(p_72875_1_, p_72875_2_);
    }
    @Override
    public boolean isAABBInMaterial(AxisAlignedBB p_72830_1_, Material p_72830_2_)
    {
        return world.isAABBInMaterial(p_72830_1_, p_72830_2_);
    }
    @Override
    public Explosion createExplosion(Entity p_72876_1_, double p_72876_2_, double p_72876_4_, double p_72876_6_, float p_72876_8_, boolean p_72876_9_)
    {
        return this.newExplosion(p_72876_1_, p_72876_2_, p_72876_4_, p_72876_6_, p_72876_8_, false, p_72876_9_);
    }
    @Override
    public Explosion newExplosion(Entity p_72885_1_, double p_72885_2_, double p_72885_4_, double p_72885_6_, float p_72885_8_, boolean p_72885_9_, boolean p_72885_10_)
    {
        return null;
    }
    //TODO: NO FURTHER!
    public float getBlockDensity(Vec3 p_72842_1_, AxisAlignedBB p_72842_2_)
    {
        double d0 = 1.0D / ((p_72842_2_.maxX - p_72842_2_.minX) * 2.0D + 1.0D);
        double d1 = 1.0D / ((p_72842_2_.maxY - p_72842_2_.minY) * 2.0D + 1.0D);
        double d2 = 1.0D / ((p_72842_2_.maxZ - p_72842_2_.minZ) * 2.0D + 1.0D);

        if (d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D)
        {
            int i = 0;
            int j = 0;

            for (float f = 0.0F; f <= 1.0F; f = (float)((double)f + d0))
            {
                for (float f1 = 0.0F; f1 <= 1.0F; f1 = (float)((double)f1 + d1))
                {
                    for (float f2 = 0.0F; f2 <= 1.0F; f2 = (float)((double)f2 + d2))
                    {
                        double d3 = p_72842_2_.minX + (p_72842_2_.maxX - p_72842_2_.minX) * (double)f;
                        double d4 = p_72842_2_.minY + (p_72842_2_.maxY - p_72842_2_.minY) * (double)f1;
                        double d5 = p_72842_2_.minZ + (p_72842_2_.maxZ - p_72842_2_.minZ) * (double)f2;

                        if (this.rayTraceBlocks(new Vec3(d3, d4, d5), p_72842_1_) == null)
                        {
                            ++i;
                        }

                        ++j;
                    }
                }
            }

            return (float)i / (float)j;
        }
        else
        {
            return 0.0F;
        }
    }

    public boolean extinguishFire(EntityPlayer player, BlockPos pos, EnumFacing side)
    {
        pos = pos.offset(side);

        if (this.getBlockState(pos).getBlock() == Blocks.fire)
        {
            this.playAuxSFXAtEntity(player, 1004, pos, 0);
            this.setBlockToAir(pos);
            return true;
        }
        else
        {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)
    public String getDebugLoadedEntities()
    {
        return "All: " + this.loadedEntityList.size();
    }

    @SideOnly(Side.CLIENT)
    public String getProviderName()
    {
        return this.chunkProvider.makeString();
    }

    public TileEntity getTileEntity(BlockPos pos)
    {
        if (!this.isValid(pos))
        {
            return null;
        }
        else
        {
            TileEntity tileentity = null;
            int i;
            TileEntity tileentity1;

            if (this.processingLoadedTiles)
            {
                for (i = 0; i < this.addedTileEntityList.size(); ++i)
                {
                    tileentity1 = (TileEntity)this.addedTileEntityList.get(i);

                    if (!tileentity1.isInvalid() && tileentity1.getPos().equals(pos))
                    {
                        tileentity = tileentity1;
                        break;
                    }
                }
            }

            if (tileentity == null)
            {
                tileentity = this.getChunkFromBlockCoords(pos).getTileEntity(pos, Chunk.EnumCreateEntityType.IMMEDIATE);
            }

            if (tileentity == null)
            {
                for (i = 0; i < this.addedTileEntityList.size(); ++i)
                {
                    tileentity1 = (TileEntity)this.addedTileEntityList.get(i);

                    if (!tileentity1.isInvalid() && tileentity1.getPos().equals(pos))
                    {
                        tileentity = tileentity1;
                        break;
                    }
                }
            }

            return tileentity;
        }
    }

    public void setTileEntity(BlockPos pos, TileEntity tileEntityIn)
    {
        if (tileEntityIn != null && !tileEntityIn.isInvalid())
        {
            if (this.processingLoadedTiles)
            {
                tileEntityIn.setPos(pos);
                Iterator iterator = this.addedTileEntityList.iterator();

                while (iterator.hasNext())
                {
                    TileEntity tileentity1 = (TileEntity)iterator.next();

                    if (tileentity1.getPos().equals(pos))
                    {
                        tileentity1.invalidate();
                        iterator.remove();
                    }
                }

                this.addedTileEntityList.add(tileEntityIn);
            }
            else
            {
                this.addTileEntity(tileEntityIn);
                Chunk chunk = this.getChunkFromBlockCoords(pos); //Forge add NPE protection
                if (chunk != null) chunk.addTileEntity(pos, tileEntityIn);
            }
            this.updateComparatorOutputLevel(pos, getBlockState(pos).getBlock()); //Notify neighbors of changes
        }
    }

    public void removeTileEntity(BlockPos pos)
    {
        //Chunk chunk = this.getChunkFromBlockCoords(pos);
        //if (chunk != null) chunk.removeTileEntity(pos);
        //Forge ToDO: Are these patches needed anymore?

        TileEntity tileentity = this.getTileEntity(pos);

        if (tileentity != null && this.processingLoadedTiles)
        {
            tileentity.invalidate();
            this.addedTileEntityList.remove(tileentity);
            if (!(tileentity instanceof IUpdatePlayerListBox)) //Forge: If they are not tickable they wont be removed in the update loop.
                this.loadedTileEntityList.remove(tileentity);

        }
        else
        {
            if (tileentity != null)
            {
                this.addedTileEntityList.remove(tileentity);
                this.loadedTileEntityList.remove(tileentity);
                this.tickableTileEntities.remove(tileentity);
            }

            this.getChunkFromBlockCoords(pos).removeTileEntity(pos);
        }
        this.updateComparatorOutputLevel(pos, getBlockState(pos).getBlock()); //Notify neighbors of changes
    }

    public void markTileEntityForRemoval(TileEntity tileEntityIn)
    {
        this.tileEntitiesToBeRemoved.add(tileEntityIn);
    }

    public boolean func_175665_u(BlockPos p_175665_1_)
    {
        IBlockState iblockstate = this.getBlockState(p_175665_1_);
        AxisAlignedBB axisalignedbb = iblockstate.getBlock().getCollisionBoundingBox(this, p_175665_1_, iblockstate);
        return axisalignedbb != null && axisalignedbb.getAverageEdgeLength() >= 1.0D;
    }

    public static boolean doesBlockHaveSolidTopSurface(IBlockAccess p_175683_0_, BlockPos p_175683_1_)
    {
        IBlockState iblockstate = p_175683_0_.getBlockState(p_175683_1_);
        Block block = iblockstate.getBlock();
        return block.isSideSolid(p_175683_0_, p_175683_1_, EnumFacing.UP);
    }

    public boolean isBlockNormalCube(BlockPos p_175677_1_, boolean p_175677_2_)
    {
        if (!this.isValid(p_175677_1_))
        {
            return p_175677_2_;
        }
        else
        {
            Chunk chunk = this.chunkProvider.provideChunk(p_175677_1_);

            if (chunk.isEmpty())
            {
                return p_175677_2_;
            }
            else
            {
                Block block = this.getBlockState(p_175677_1_).getBlock();
                return block.isNormalCube(this, p_175677_1_);
            }
        }
    }

    public void calculateInitialSkylight()
    {
        int i = this.calculateSkylightSubtracted(1.0F);

        if (i != this.skylightSubtracted)
        {
            this.skylightSubtracted = i;
        }
    }

    public void setAllowedSpawnTypes(boolean hostile, boolean peaceful)
    {
        this.provider.setAllowedSpawnTypes(hostile, peaceful);
    }

    public void tick()
    {
        this.updateWeather();
    }

    protected void calculateInitialWeather()
    {
        this.provider.calculateInitialWeather();
    }

    public void calculateInitialWeatherBody()
    {
        if (this.worldInfo.isRaining())
        {
            this.rainingStrength = 1.0F;

            if (this.worldInfo.isThundering())
            {
                this.thunderingStrength = 1.0F;
            }
        }
    }

    protected void updateWeather()
    {
        this.provider.updateWeather();
    }

    public void updateWeatherBody()
    {
        if (!this.provider.getHasNoSky())
        {
            if (!this.isRemote)
            {
                int i = this.worldInfo.getCleanWeatherTime();

                if (i > 0)
                {
                    --i;
                    this.worldInfo.setCleanWeatherTime(i);
                    this.worldInfo.setThunderTime(this.worldInfo.isThundering() ? 1 : 2);
                    this.worldInfo.setRainTime(this.worldInfo.isRaining() ? 1 : 2);
                }

                int j = this.worldInfo.getThunderTime();

                if (j <= 0)
                {
                    if (this.worldInfo.isThundering())
                    {
                        this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
                    }
                    else
                    {
                        this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
                    }
                }
                else
                {
                    --j;
                    this.worldInfo.setThunderTime(j);

                    if (j <= 0)
                    {
                        this.worldInfo.setThundering(!this.worldInfo.isThundering());
                    }
                }

                this.prevThunderingStrength = this.thunderingStrength;

                if (this.worldInfo.isThundering())
                {
                    this.thunderingStrength = (float)((double)this.thunderingStrength + 0.01D);
                }
                else
                {
                    this.thunderingStrength = (float)((double)this.thunderingStrength - 0.01D);
                }

                this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0F, 1.0F);
                int k = this.worldInfo.getRainTime();

                if (k <= 0)
                {
                    if (this.worldInfo.isRaining())
                    {
                        this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
                    }
                    else
                    {
                        this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
                    }
                }
                else
                {
                    --k;
                    this.worldInfo.setRainTime(k);

                    if (k <= 0)
                    {
                        this.worldInfo.setRaining(!this.worldInfo.isRaining());
                    }
                }

                this.prevRainingStrength = this.rainingStrength;

                if (this.worldInfo.isRaining())
                {
                    this.rainingStrength = (float)((double)this.rainingStrength + 0.01D);
                }
                else
                {
                    this.rainingStrength = (float)((double)this.rainingStrength - 0.01D);
                }

                this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0F, 1.0F);
            }
        }
    }

    protected void setActivePlayerChunksAndCheckLight()
    {
        this.activeChunkSet.clear();
        this.theProfiler.startSection("buildList");
        this.activeChunkSet.addAll(getPersistentChunks().keySet());
        int i;
        EntityPlayer entityplayer;
        int j;
        int k;
        int l;

        for (i = 0; i < this.playerEntities.size(); ++i)
        {
            entityplayer = (EntityPlayer)this.playerEntities.get(i);
            j = MathHelper.floor_double(entityplayer.posX / 16.0D);
            k = MathHelper.floor_double(entityplayer.posZ / 16.0D);
            l = this.getRenderDistanceChunks();

            for (int i1 = -l; i1 <= l; ++i1)
            {
                for (int j1 = -l; j1 <= l; ++j1)
                {
                    this.activeChunkSet.add(new ChunkCoordIntPair(i1 + j, j1 + k));
                }
            }
        }

        this.theProfiler.endSection();

        if (this.ambientTickCountdown > 0)
        {
            --this.ambientTickCountdown;
        }

        this.theProfiler.startSection("playerCheckLight");

        if (!this.playerEntities.isEmpty())
        {
            i = this.rand.nextInt(this.playerEntities.size());
            entityplayer = (EntityPlayer)this.playerEntities.get(i);
            j = MathHelper.floor_double(entityplayer.posX) + this.rand.nextInt(11) - 5;
            k = MathHelper.floor_double(entityplayer.posY) + this.rand.nextInt(11) - 5;
            l = MathHelper.floor_double(entityplayer.posZ) + this.rand.nextInt(11) - 5;
            this.checkLight(new BlockPos(j, k, l));
        }

        this.theProfiler.endSection();
    }

    protected abstract int getRenderDistanceChunks();

    protected void playMoodSoundAndCheckLight(int p_147467_1_, int p_147467_2_, Chunk p_147467_3_)
    {
        this.theProfiler.endStartSection("moodSound");

        if (this.ambientTickCountdown == 0 && !this.isRemote)
        {
            this.updateLCG = this.updateLCG * 3 + 1013904223;
            int k = this.updateLCG >> 2;
            int l = k & 15;
            int i1 = k >> 8 & 15;
            int j1 = k >> 16 & 255;
            BlockPos blockpos = new BlockPos(l, j1, i1);
            Block block = p_147467_3_.getBlock(blockpos);
            l += p_147467_1_;
            i1 += p_147467_2_;

            if (block.isAir(this, blockpos) && this.getLight(blockpos) <= this.rand.nextInt(8) && this.getLightFor(EnumSkyBlock.SKY, blockpos) <= 0)
            {
                EntityPlayer entityplayer = this.getClosestPlayer((double)l + 0.5D, (double)j1 + 0.5D, (double)i1 + 0.5D, 8.0D);

                if (entityplayer != null && entityplayer.getDistanceSq((double)l + 0.5D, (double)j1 + 0.5D, (double)i1 + 0.5D) > 4.0D)
                {
                    this.playSoundEffect((double)l + 0.5D, (double)j1 + 0.5D, (double)i1 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.rand.nextFloat() * 0.2F);
                    this.ambientTickCountdown = this.rand.nextInt(12000) + 6000;
                }
            }
        }

        this.theProfiler.endStartSection("checkLight");
        p_147467_3_.enqueueRelightChecks();
    }

    protected void updateBlocks()
    {
        this.setActivePlayerChunksAndCheckLight();
    }

    public void forceBlockUpdateTick(Block blockType, BlockPos pos, Random random)
    {
        this.scheduledUpdatesAreImmediate = true;
        blockType.updateTick(this, pos, this.getBlockState(pos), random);
        this.scheduledUpdatesAreImmediate = false;
    }

    public boolean func_175675_v(BlockPos p_175675_1_)
    {
        return this.canBlockFreeze(p_175675_1_, false);
    }

    public boolean func_175662_w(BlockPos p_175662_1_)
    {
        return this.canBlockFreeze(p_175662_1_, true);
    }

    public boolean canBlockFreeze(BlockPos pos, boolean noWaterAdj)
    {
        return this.provider.canBlockFreeze(pos, noWaterAdj);
    }

    public boolean canBlockFreezeBody(BlockPos pos, boolean noWaterAdj)
    {
        BiomeGenBase biomegenbase = this.getBiomeGenForCoords(pos);
        float f = biomegenbase.getFloatTemperature(pos);

        if (f > 0.15F)
        {
            return false;
        }
        else
        {
            if (pos.getY() >= 0 && pos.getY() < 256 && this.getLightFor(EnumSkyBlock.BLOCK, pos) < 10)
            {
                IBlockState iblockstate = this.getBlockState(pos);
                Block block = iblockstate.getBlock();

                if ((block == Blocks.water || block == Blocks.flowing_water) && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
                {
                    if (!noWaterAdj)
                    {
                        return true;
                    }

                    boolean flag1 = this.isWater(pos.west()) && this.isWater(pos.east()) && this.isWater(pos.north()) && this.isWater(pos.south());

                    if (!flag1)
                    {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    private boolean isWater(BlockPos pos)
    {
        return this.getBlockState(pos).getBlock().getMaterial() == Material.water;
    }

    public boolean canSnowAt(BlockPos pos, boolean checkLight)
    {
        return this.provider.canSnowAt(pos, checkLight);
    }

    public boolean canSnowAtBody(BlockPos pos, boolean checkLight)
    {
        BiomeGenBase biomegenbase = this.getBiomeGenForCoords(pos);
        float f = biomegenbase.getFloatTemperature(pos);

        if (f > 0.15F)
        {
            return false;
        }
        else if (!checkLight)
        {
            return true;
        }
        else
        {
            if (pos.getY() >= 0 && pos.getY() < 256 && this.getLightFor(EnumSkyBlock.BLOCK, pos) < 10)
            {
                Block block = this.getBlockState(pos).getBlock();

                if (block.isAir(this, pos) && Blocks.snow_layer.canPlaceBlockAt(this, pos))
                {
                    return true;
                }
            }

            return false;
        }
    }

    public boolean checkLight(BlockPos pos)
    {
        boolean flag = false;

        if (!this.provider.getHasNoSky())
        {
            flag |= this.checkLightFor(EnumSkyBlock.SKY, pos);
        }

        flag |= this.checkLightFor(EnumSkyBlock.BLOCK, pos);
        return flag;
    }

    private int getRawLight(BlockPos pos, EnumSkyBlock lightType)
    {
        if (lightType == EnumSkyBlock.SKY && this.canSeeSky(pos))
        {
            return 15;
        }
        else
        {
            Block block = this.getBlockState(pos).getBlock();
            int blockLight = block.getLightValue(this, pos);
            int i = lightType == EnumSkyBlock.SKY ? 0 : blockLight;
            int j = block.getLightOpacity(this, pos);

            if (j >= 15 && blockLight > 0)
            {
                j = 1;
            }

            if (j < 1)
            {
                j = 1;
            }

            if (j >= 15)
            {
                return 0;
            }
            else if (i >= 14)
            {
                return i;
            }
            else
            {
                EnumFacing[] aenumfacing = EnumFacing.values();
                int k = aenumfacing.length;

                for (int l = 0; l < k; ++l)
                {
                    EnumFacing enumfacing = aenumfacing[l];
                    BlockPos blockpos1 = pos.offset(enumfacing);
                    int i1 = this.getLightFor(lightType, blockpos1) - j;

                    if (i1 > i)
                    {
                        i = i1;
                    }

                    if (i >= 14)
                    {
                        return i;
                    }
                }

                return i;
            }
        }
    }

    public boolean checkLightFor(EnumSkyBlock lightType, BlockPos pos)
    {
        if (!this.isAreaLoaded(pos, 17, false))
        {
            return false;
        }
        else
        {
            int i = 0;
            int j = 0;
            this.theProfiler.startSection("getBrightness");
            int k = this.getLightFor(lightType, pos);
            int l = this.getRawLight(pos, lightType);
            int i1 = pos.getX();
            int j1 = pos.getY();
            int k1 = pos.getZ();
            int l1;
            int i2;
            int j2;
            int k2;
            int i3;
            int j3;
            int k3;
            int l3;

            if (l > k)
            {
                this.lightUpdateBlockList[j++] = 133152;
            }
            else if (l < k)
            {
                this.lightUpdateBlockList[j++] = 133152 | k << 18;

                while (i < j)
                {
                    l1 = this.lightUpdateBlockList[i++];
                    i2 = (l1 & 63) - 32 + i1;
                    j2 = (l1 >> 6 & 63) - 32 + j1;
                    k2 = (l1 >> 12 & 63) - 32 + k1;
                    int l2 = l1 >> 18 & 15;
                    BlockPos blockpos1 = new BlockPos(i2, j2, k2);
                    i3 = this.getLightFor(lightType, blockpos1);

                    if (i3 == l2)
                    {
                        this.setLightFor(lightType, blockpos1, 0);

                        if (l2 > 0)
                        {
                            j3 = MathHelper.abs_int(i2 - i1);
                            k3 = MathHelper.abs_int(j2 - j1);
                            l3 = MathHelper.abs_int(k2 - k1);

                            if (j3 + k3 + l3 < 17)
                            {
                                EnumFacing[] aenumfacing = EnumFacing.values();
                                int i4 = aenumfacing.length;

                                for (int j4 = 0; j4 < i4; ++j4)
                                {
                                    EnumFacing enumfacing = aenumfacing[j4];
                                    int k4 = i2 + enumfacing.getFrontOffsetX();
                                    int l4 = j2 + enumfacing.getFrontOffsetY();
                                    int i5 = k2 + enumfacing.getFrontOffsetZ();
                                    BlockPos blockpos2 = new BlockPos(k4, l4, i5);
                                    int j5 = Math.max(1, this.getBlockState(blockpos2).getBlock().getLightOpacity());
                                    i3 = this.getLightFor(lightType, blockpos2);

                                    if (i3 == l2 - j5 && j < this.lightUpdateBlockList.length)
                                    {
                                        this.lightUpdateBlockList[j++] = k4 - i1 + 32 | l4 - j1 + 32 << 6 | i5 - k1 + 32 << 12 | l2 - j5 << 18;
                                    }
                                }
                            }
                        }
                    }
                }

                i = 0;
            }

            this.theProfiler.endSection();
            this.theProfiler.startSection("checkedPosition < toCheckCount");

            while (i < j)
            {
                l1 = this.lightUpdateBlockList[i++];
                i2 = (l1 & 63) - 32 + i1;
                j2 = (l1 >> 6 & 63) - 32 + j1;
                k2 = (l1 >> 12 & 63) - 32 + k1;
                BlockPos blockpos3 = new BlockPos(i2, j2, k2);
                int k5 = this.getLightFor(lightType, blockpos3);
                i3 = this.getRawLight(blockpos3, lightType);

                if (i3 != k5)
                {
                    this.setLightFor(lightType, blockpos3, i3);

                    if (i3 > k5)
                    {
                        j3 = Math.abs(i2 - i1);
                        k3 = Math.abs(j2 - j1);
                        l3 = Math.abs(k2 - k1);
                        boolean flag = j < this.lightUpdateBlockList.length - 6;

                        if (j3 + k3 + l3 < 17 && flag)
                        {
                            if (this.getLightFor(lightType, blockpos3.west()) < i3)
                            {
                                this.lightUpdateBlockList[j++] = i2 - 1 - i1 + 32 + (j2 - j1 + 32 << 6) + (k2 - k1 + 32 << 12);
                            }

                            if (this.getLightFor(lightType, blockpos3.east()) < i3)
                            {
                                this.lightUpdateBlockList[j++] = i2 + 1 - i1 + 32 + (j2 - j1 + 32 << 6) + (k2 - k1 + 32 << 12);
                            }

                            if (this.getLightFor(lightType, blockpos3.down()) < i3)
                            {
                                this.lightUpdateBlockList[j++] = i2 - i1 + 32 + (j2 - 1 - j1 + 32 << 6) + (k2 - k1 + 32 << 12);
                            }

                            if (this.getLightFor(lightType, blockpos3.up()) < i3)
                            {
                                this.lightUpdateBlockList[j++] = i2 - i1 + 32 + (j2 + 1 - j1 + 32 << 6) + (k2 - k1 + 32 << 12);
                            }

                            if (this.getLightFor(lightType, blockpos3.north()) < i3)
                            {
                                this.lightUpdateBlockList[j++] = i2 - i1 + 32 + (j2 - j1 + 32 << 6) + (k2 - 1 - k1 + 32 << 12);
                            }

                            if (this.getLightFor(lightType, blockpos3.south()) < i3)
                            {
                                this.lightUpdateBlockList[j++] = i2 - i1 + 32 + (j2 - j1 + 32 << 6) + (k2 + 1 - k1 + 32 << 12);
                            }
                        }
                    }
                }
            }

            this.theProfiler.endSection();
            return true;
        }
    }

    public boolean tickUpdates(boolean p_72955_1_)
    {
        return false;
    }

    public List getPendingBlockUpdates(Chunk p_72920_1_, boolean p_72920_2_)
    {
        return null;
    }

    public List func_175712_a(StructureBoundingBox p_175712_1_, boolean p_175712_2_)
    {
        return null;
    }

    public List getEntitiesWithinAABBExcludingEntity(Entity entityIn, AxisAlignedBB p_72839_2_)
    {
        return this.func_175674_a(entityIn, p_72839_2_, IEntitySelector.NOT_SPECTATING);
    }

    public List func_175674_a(Entity entityIn, AxisAlignedBB p_175674_2_, Predicate p_175674_3_)
    {
        ArrayList arraylist = Lists.newArrayList();
        int i = MathHelper.floor_double((p_175674_2_.minX - MAX_ENTITY_RADIUS) / 16.0D);
        int j = MathHelper.floor_double((p_175674_2_.maxX + MAX_ENTITY_RADIUS) / 16.0D);
        int k = MathHelper.floor_double((p_175674_2_.minZ - MAX_ENTITY_RADIUS) / 16.0D);
        int l = MathHelper.floor_double((p_175674_2_.maxZ + MAX_ENTITY_RADIUS) / 16.0D);

        for (int i1 = i; i1 <= j; ++i1)
        {
            for (int j1 = k; j1 <= l; ++j1)
            {
                if (this.isChunkLoaded(i1, j1, true))
                {
                    this.getChunkFromChunkCoords(i1, j1).getEntitiesWithinAABBForEntity(entityIn, p_175674_2_, arraylist, p_175674_3_);
                }
            }
        }

        return arraylist;
    }

    public List getEntities(Class entityType, Predicate filter)
    {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = this.loadedEntityList.iterator();

        while (iterator.hasNext())
        {
            Entity entity = (Entity)iterator.next();

            if (entityType.isAssignableFrom(entity.getClass()) && filter.apply(entity))
            {
                arraylist.add(entity);
            }
        }

        return arraylist;
    }

    public List getPlayers(Class playerType, Predicate filter)
    {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = this.playerEntities.iterator();

        while (iterator.hasNext())
        {
            Entity entity = (Entity)iterator.next();

            if (playerType.isAssignableFrom(entity.getClass()) && filter.apply(entity))
            {
                arraylist.add(entity);
            }
        }

        return arraylist;
    }

    public List getEntitiesWithinAABB(Class p_72872_1_, AxisAlignedBB p_72872_2_)
    {
        return this.getEntitiesWithinAABB(p_72872_1_, p_72872_2_, IEntitySelector.NOT_SPECTATING);
    }

    public List getEntitiesWithinAABB(Class clazz, AxisAlignedBB aabb, Predicate filter)
    {
        int i = MathHelper.floor_double((aabb.minX - MAX_ENTITY_RADIUS) / 16.0D);
        int j = MathHelper.floor_double((aabb.maxX + MAX_ENTITY_RADIUS) / 16.0D);
        int k = MathHelper.floor_double((aabb.minZ - MAX_ENTITY_RADIUS) / 16.0D);
        int l = MathHelper.floor_double((aabb.maxZ + MAX_ENTITY_RADIUS) / 16.0D);
        ArrayList arraylist = Lists.newArrayList();

        for (int i1 = i; i1 <= j; ++i1)
        {
            for (int j1 = k; j1 <= l; ++j1)
            {
                if (this.isChunkLoaded(i1, j1, true))
                {
                    this.getChunkFromChunkCoords(i1, j1).getEntitiesOfTypeWithinAAAB(clazz, aabb, arraylist, filter);
                }
            }
        }

        return arraylist;
    }

    public Entity findNearestEntityWithinAABB(Class entityType, AxisAlignedBB aabb, Entity closestTo)
    {
        List list = this.getEntitiesWithinAABB(entityType, aabb);
        Entity entity1 = null;
        double d0 = Double.MAX_VALUE;

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity2 = (Entity)list.get(i);

            if (entity2 != closestTo && IEntitySelector.NOT_SPECTATING.apply(entity2))
            {
                double d1 = closestTo.getDistanceSqToEntity(entity2);

                if (d1 <= d0)
                {
                    entity1 = entity2;
                    d0 = d1;
                }
            }
        }

        return entity1;
    }

    public Entity getEntityByID(int id)
    {
        return (Entity)this.entitiesById.lookup(id);
    }

    @SideOnly(Side.CLIENT)
    public List getLoadedEntityList()
    {
        return this.loadedEntityList;
    }

    public void markChunkDirty(BlockPos pos, TileEntity unusedTileEntity)
    {
        if (this.isBlockLoaded(pos))
        {
            this.getChunkFromBlockCoords(pos).setChunkModified();
        }
    }

    public int countEntities(Class entityType)
    {
        int i = 0;
        Iterator iterator = this.loadedEntityList.iterator();

        while (iterator.hasNext())
        {
            Entity entity = (Entity)iterator.next();

            if ((!(entity instanceof EntityLiving) || !((EntityLiving)entity).isNoDespawnRequired()) && entityType.isAssignableFrom(entity.getClass()))
            {
                ++i;
            }
        }

        return i;
    }

    public void loadEntities(Collection entityCollection)
    {
        Iterator iterator = entityCollection.iterator();

        while (iterator.hasNext())
        {
            Entity entity = (Entity)iterator.next();
            if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.EntityJoinWorldEvent(entity, this)))
            {
                loadedEntityList.add(entity);
                this.onEntityAdded(entity);
            }
        }
    }

    public void unloadEntities(Collection entityCollection)
    {
        this.unloadedEntityList.addAll(entityCollection);
    }

    public boolean canBlockBePlaced(Block p_175716_1_, BlockPos p_175716_2_, boolean p_175716_3_, EnumFacing p_175716_4_, Entity p_175716_5_, ItemStack p_175716_6_)
    {
        Block block1 = this.getBlockState(p_175716_2_).getBlock();
        AxisAlignedBB axisalignedbb = p_175716_3_ ? null : p_175716_1_.getCollisionBoundingBox(this, p_175716_2_, p_175716_1_.getDefaultState());
        if (axisalignedbb != null && !this.checkNoEntityCollision(axisalignedbb, p_175716_5_)) return false;
        if (block1.getMaterial() == Material.circuits && p_175716_1_ == Blocks.anvil) return true;
        return block1.isReplaceable(this, p_175716_2_) && p_175716_1_.canReplace(this, p_175716_2_, p_175716_4_, p_175716_6_);
    }

    public int getStrongPower(BlockPos pos, EnumFacing direction)
    {
        IBlockState iblockstate = this.getBlockState(pos);
        return iblockstate.getBlock().isProvidingStrongPower(this, pos, iblockstate, direction);
    }

    public WorldType getWorldType()
    {
        return this.worldInfo.getTerrainType();
    }

    public int getStrongPower(BlockPos pos)
    {
        byte b0 = 0;
        int i = Math.max(b0, this.getStrongPower(pos.down(), EnumFacing.DOWN));

        if (i >= 15)
        {
            return i;
        }
        else
        {
            i = Math.max(i, this.getStrongPower(pos.up(), EnumFacing.UP));

            if (i >= 15)
            {
                return i;
            }
            else
            {
                i = Math.max(i, this.getStrongPower(pos.north(), EnumFacing.NORTH));

                if (i >= 15)
                {
                    return i;
                }
                else
                {
                    i = Math.max(i, this.getStrongPower(pos.south(), EnumFacing.SOUTH));

                    if (i >= 15)
                    {
                        return i;
                    }
                    else
                    {
                        i = Math.max(i, this.getStrongPower(pos.west(), EnumFacing.WEST));

                        if (i >= 15)
                        {
                            return i;
                        }
                        else
                        {
                            i = Math.max(i, this.getStrongPower(pos.east(), EnumFacing.EAST));
                            return i >= 15 ? i : i;
                        }
                    }
                }
            }
        }
    }

    public boolean isSidePowered(BlockPos pos, EnumFacing side)
    {
        return this.getRedstonePower(pos, side) > 0;
    }

    public int getRedstonePower(BlockPos pos, EnumFacing facing)
    {
        IBlockState iblockstate = this.getBlockState(pos);
        Block block = iblockstate.getBlock();
        return block.shouldCheckWeakPower(this, pos, facing) ? this.getStrongPower(pos) : block.isProvidingWeakPower(this, pos, iblockstate, facing);
    }

    public boolean isBlockPowered(BlockPos pos)
    {
        return this.getRedstonePower(pos.down(), EnumFacing.DOWN) > 0 ? true : (this.getRedstonePower(pos.up(), EnumFacing.UP) > 0 ? true : (this.getRedstonePower(pos.north(), EnumFacing.NORTH) > 0 ? true : (this.getRedstonePower(pos.south(), EnumFacing.SOUTH) > 0 ? true : (this.getRedstonePower(pos.west(), EnumFacing.WEST) > 0 ? true : this.getRedstonePower(pos.east(), EnumFacing.EAST) > 0))));
    }

    public int isBlockIndirectlyGettingPowered(BlockPos pos)
    {
        int i = 0;
        EnumFacing[] aenumfacing = EnumFacing.values();
        int j = aenumfacing.length;

        for (int k = 0; k < j; ++k)
        {
            EnumFacing enumfacing = aenumfacing[k];
            int l = this.getRedstonePower(pos.offset(enumfacing), enumfacing);

            if (l >= 15)
            {
                return 15;
            }

            if (l > i)
            {
                i = l;
            }
        }

        return i;
    }

    public EntityPlayer getClosestPlayerToEntity(Entity entityIn, double distance)
    {
        return this.getClosestPlayer(entityIn.posX, entityIn.posY, entityIn.posZ, distance);
    }

    public EntityPlayer getClosestPlayer(double x, double y, double z, double distance)
    {
        double d4 = -1.0D;
        EntityPlayer entityplayer = null;

        for (int i = 0; i < this.playerEntities.size(); ++i)
        {
            EntityPlayer entityplayer1 = (EntityPlayer)this.playerEntities.get(i);

            if (IEntitySelector.NOT_SPECTATING.apply(entityplayer1))
            {
                double d5 = entityplayer1.getDistanceSq(x, y, z);

                if ((distance < 0.0D || d5 < distance * distance) && (d4 == -1.0D || d5 < d4))
                {
                    d4 = d5;
                    entityplayer = entityplayer1;
                }
            }
        }

        return entityplayer;
    }

    public boolean func_175636_b(double p_175636_1_, double p_175636_3_, double p_175636_5_, double p_175636_7_)
    {
        for (int i = 0; i < this.playerEntities.size(); ++i)
        {
            EntityPlayer entityplayer = (EntityPlayer)this.playerEntities.get(i);

            if (IEntitySelector.NOT_SPECTATING.apply(entityplayer))
            {
                double d4 = entityplayer.getDistanceSq(p_175636_1_, p_175636_3_, p_175636_5_);

                if (p_175636_7_ < 0.0D || d4 < p_175636_7_ * p_175636_7_)
                {
                    return true;
                }
            }
        }

        return false;
    }

    public EntityPlayer getPlayerEntityByName(String name)
    {
        for (int i = 0; i < this.playerEntities.size(); ++i)
        {
            EntityPlayer entityplayer = (EntityPlayer)this.playerEntities.get(i);

            if (name.equals(entityplayer.getName()))
            {
                return entityplayer;
            }
        }

        return null;
    }

    public EntityPlayer getPlayerEntityByUUID(UUID uuid)
    {
        for (int i = 0; i < this.playerEntities.size(); ++i)
        {
            EntityPlayer entityplayer = (EntityPlayer)this.playerEntities.get(i);

            if (uuid.equals(entityplayer.getUniqueID()))
            {
                return entityplayer;
            }
        }

        return null;
    }

    @SideOnly(Side.CLIENT)
    public void sendQuittingDisconnectingPacket() {}

    public void checkSessionLock() throws MinecraftException
    {
        this.saveHandler.checkSessionLock();
    }

    @SideOnly(Side.CLIENT)
    public void setTotalWorldTime(long p_82738_1_)
    {
        this.worldInfo.incrementTotalWorldTime(p_82738_1_);
    }

    public long getSeed()
    {
        return this.provider.getSeed();
    }

    public long getTotalWorldTime()
    {
        return this.worldInfo.getWorldTotalTime();
    }

    public long getWorldTime()
    {
        return this.provider.getWorldTime();
    }

    public void setWorldTime(long time)
    {
        this.provider.setWorldTime(time);
    }

    public BlockPos getSpawnPoint()
    {
        BlockPos blockpos = this.provider.getSpawnPoint();

        if (!this.getWorldBorder().contains(blockpos))
        {
            blockpos = this.getHorizon(new BlockPos(this.getWorldBorder().getCenterX(), 0.0D, this.getWorldBorder().getCenterZ()));
        }

        return blockpos;
    }

    public void setSpawnPoint(BlockPos pos)
    {
        this.provider.setSpawnPoint(pos);
    }

    @SideOnly(Side.CLIENT)
    public void joinEntityInSurroundings(Entity entityIn)
    {
        int i = MathHelper.floor_double(entityIn.posX / 16.0D);
        int j = MathHelper.floor_double(entityIn.posZ / 16.0D);
        byte b0 = 2;

        for (int k = i - b0; k <= i + b0; ++k)
        {
            for (int l = j - b0; l <= j + b0; ++l)
            {
                this.getChunkFromChunkCoords(k, l);
            }
        }

        if (!this.loadedEntityList.contains(entityIn))
        {
            if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.EntityJoinWorldEvent(entityIn, this)))
            this.loadedEntityList.add(entityIn);
        }
    }

    public boolean isBlockModifiable(EntityPlayer player, BlockPos pos)
    {
        return this.provider.canMineBlock(player, pos);
    }

    public boolean canMineBlockBody(EntityPlayer player, BlockPos pos)
    {
        return true;
    }

    public void setEntityState(Entity entityIn, byte state) {}

    public IChunkProvider getChunkProvider()
    {
        return this.chunkProvider;
    }

    public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam)
    {
        blockIn.onBlockEventReceived(this, pos, this.getBlockState(pos), eventID, eventParam);
    }

    public ISaveHandler getSaveHandler()
    {
        return this.saveHandler;
    }

    public WorldInfo getWorldInfo()
    {
        return this.worldInfo;
    }

    public GameRules getGameRules()
    {
        return this.worldInfo.getGameRulesInstance();
    }

    public void updateAllPlayersSleepingFlag() {}

    public float getThunderStrength(float delta)
    {
        return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * delta) * this.getRainStrength(delta);
    }

    @SideOnly(Side.CLIENT)
    public void setThunderStrength(float p_147442_1_)
    {
        this.prevThunderingStrength = p_147442_1_;
        this.thunderingStrength = p_147442_1_;
    }

    public float getRainStrength(float delta)
    {
        return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * delta;
    }

    @SideOnly(Side.CLIENT)
    public void setRainStrength(float strength)
    {
        this.prevRainingStrength = strength;
        this.rainingStrength = strength;
    }

    public boolean isThundering()
    {
        return (double)this.getThunderStrength(1.0F) > 0.9D;
    }

    public boolean isRaining()
    {
        return (double)this.getRainStrength(1.0F) > 0.2D;
    }

    public boolean canLightningStrike(BlockPos strikePosition)
    {
        if (!this.isRaining())
        {
            return false;
        }
        else if (!this.canSeeSky(strikePosition))
        {
            return false;
        }
        else if (this.getPrecipitationHeight(strikePosition).getY() > strikePosition.getY())
        {
            return false;
        }
        else
        {
            BiomeGenBase biomegenbase = this.getBiomeGenForCoords(strikePosition);
            return biomegenbase.getEnableSnow() ? false : (this.canSnowAt(strikePosition, false) ? false : biomegenbase.canSpawnLightningBolt());
        }
    }

    public boolean isBlockinHighHumidity(BlockPos pos)
    {
        return this.provider.isBlockHighHumidity(pos);
    }

    public MapStorage getMapStorage()
    {
        return this.mapStorage;
    }

    public void setItemData(String p_72823_1_, WorldSavedData p_72823_2_)
    {
        this.mapStorage.setData(p_72823_1_, p_72823_2_);
    }

    public WorldSavedData loadItemData(Class p_72943_1_, String p_72943_2_)
    {
        return this.mapStorage.loadData(p_72943_1_, p_72943_2_);
    }

    public int getUniqueDataId(String key)
    {
        return this.mapStorage.getUniqueDataId(key);
    }

    public void playBroadcastSound(int p_175669_1_, BlockPos p_175669_2_, int p_175669_3_)
    {
        for (int k = 0; k < this.worldAccesses.size(); ++k)
        {
            ((IWorldAccess)this.worldAccesses.get(k)).broadcastSound(p_175669_1_, p_175669_2_, p_175669_3_);
        }
    }

    public void playAuxSFX(int p_175718_1_, BlockPos p_175718_2_, int p_175718_3_)
    {
        this.playAuxSFXAtEntity((EntityPlayer)null, p_175718_1_, p_175718_2_, p_175718_3_);
    }

    public void playAuxSFXAtEntity(EntityPlayer p_180498_1_, int p_180498_2_, BlockPos p_180498_3_, int p_180498_4_)
    {
        try
        {
            for (int k = 0; k < this.worldAccesses.size(); ++k)
            {
                ((IWorldAccess)this.worldAccesses.get(k)).playAusSFX(p_180498_1_, p_180498_2_, p_180498_3_, p_180498_4_);
            }
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Playing level event");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Level event being played");
            crashreportcategory.addCrashSection("Block coordinates", CrashReportCategory.getCoordinateInfo(p_180498_3_));
            crashreportcategory.addCrashSection("Event source", p_180498_1_);
            crashreportcategory.addCrashSection("Event type", Integer.valueOf(p_180498_2_));
            crashreportcategory.addCrashSection("Event data", Integer.valueOf(p_180498_4_));
            throw new ReportedException(crashreport);
        }
    }

    public int getHeight()
    {
        return this.provider.getHeight();
    }

    public int getActualHeight()
    {
        return this.provider.getActualHeight();
    }

    public Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_)
    {
        long l = (long)p_72843_1_ * 341873128712L + (long)p_72843_2_ * 132897987541L + this.getWorldInfo().getSeed() + (long)p_72843_3_;
        this.rand.setSeed(l);
        return this.rand;
    }

    public BlockPos getStrongholdPos(String p_180499_1_, BlockPos p_180499_2_)
    {
        return this.getChunkProvider().getStrongholdGen(this, p_180499_1_, p_180499_2_);
    }

    public CrashReportCategory addWorldInfoToCrashReport(CrashReport report)
    {
        CrashReportCategory crashreportcategory = report.makeCategoryDepth("Affected level", 1);
        crashreportcategory.addCrashSection("Level name", this.worldInfo == null ? "????" : this.worldInfo.getWorldName());
        crashreportcategory.addCrashSectionCallable("All players", new Callable()
        {
            private static final String __OBFID = "CL_00000143";
            public String call()
            {
                return World.this.playerEntities.size() + " total; " + World.this.playerEntities.toString();
            }
        });
        crashreportcategory.addCrashSectionCallable("Chunk stats", new Callable()
        {
            private static final String __OBFID = "CL_00000144";
            public String call()
            {
                return World.this.chunkProvider.makeString();
            }
        });

        try
        {
            this.worldInfo.addToCrashReport(crashreportcategory);
        }
        catch (Throwable throwable)
        {
            crashreportcategory.addCrashSectionThrowable("Level Data Unobtainable", throwable);
        }

        return crashreportcategory;
    }

    @SideOnly(Side.CLIENT)
    public boolean extendedLevelsInChunkCache()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public double getHorizon()
    {
        return provider.getHorizon();
    }

    public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress)
    {
        for (int k = 0; k < this.worldAccesses.size(); ++k)
        {
            IWorldAccess iworldaccess = (IWorldAccess)this.worldAccesses.get(k);
            iworldaccess.sendBlockBreakProgress(breakerId, pos, progress);
        }
    }

    public Calendar getCurrentDate()
    {
        if (this.getTotalWorldTime() % 600L == 0L)
        {
            this.theCalendar.setTimeInMillis(MinecraftServer.getCurrentTimeMillis());
        }

        return this.theCalendar;
    }

    @SideOnly(Side.CLIENT)
    public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, NBTTagCompound compund) {}

    public Scoreboard getScoreboard()
    {
        return this.worldScoreboard;
    }

    public void updateComparatorOutputLevel(BlockPos pos, Block blockIn)
    {
        for (EnumFacing enumfacing : EnumFacing.values())
        {
            BlockPos blockpos1 = pos.offset(enumfacing);

            if (this.isBlockLoaded(blockpos1))
            {
                IBlockState iblockstate = this.getBlockState(blockpos1);
                iblockstate.getBlock().onNeighborChange(this, blockpos1, pos);
                if (iblockstate.getBlock().isNormalCube(this, blockpos1))
                {
                    BlockPos posOther = blockpos1.offset(enumfacing);
                    Block other = getBlockState(posOther).getBlock();
                    if (other.getWeakChanges(this, posOther))
                    {
                        other.onNeighborChange(this, posOther, pos);
                    }
                }
            }
        }
    }

    public DifficultyInstance getDifficultyForLocation(BlockPos pos)
    {
        long i = 0L;
        float f = 0.0F;

        if (this.isBlockLoaded(pos))
        {
            f = this.getCurrentMoonPhaseFactor();
            i = this.getChunkFromBlockCoords(pos).getInhabitedTime();
        }

        return new DifficultyInstance(this.getDifficulty(), this.getWorldTime(), i, f);
    }

    public EnumDifficulty getDifficulty()
    {
        return this.getWorldInfo().getDifficulty();
    }

    public int getSkylightSubtracted()
    {
        return this.skylightSubtracted;
    }

    public void setSkylightSubtracted(int newSkylightSubtracted)
    {
        this.skylightSubtracted = newSkylightSubtracted;
    }

    @SideOnly(Side.CLIENT)
    public int getLastLightningBolt()
    {
        return this.lastLightningBolt;
    }

    public void setLastLightningBolt(int lastLightningBoltIn)
    {
        this.lastLightningBolt = lastLightningBoltIn;
    }

    public boolean isFindingSpawnPoint()
    {
        return this.findingSpawnPoint;
    }

    public VillageCollection getVillageCollection()
    {
        return this.villageCollectionObj;
    }

    public WorldBorder getWorldBorder()
    {
        return this.worldBorder;
    }

    public boolean isSpawnChunk(int x, int z)
    {
        BlockPos blockpos = this.getSpawnPoint();
        int k = x * 16 + 8 - blockpos.getX();
        int l = z * 16 + 8 - blockpos.getZ();
        short short1 = 128;
        return k >= -short1 && k <= short1 && l >= -short1 && l <= short1;
    }


    /* ======================================== FORGE START =====================================*/
    /**
     * Determine if the given block is considered solid on the
     * specified side.  Used by placement logic.
     *
     * @param pos Block Position
     * @param side The Side in question
     * @return True if the side is solid
    */
    public boolean isSideSolid(BlockPos pos, EnumFacing side)
    {
       return isSideSolid(pos, side, false);
    }

    /**
     * Determine if the given block is considered solid on the
     * specified side.  Used by placement logic.
     *
     * @param pos Block Position
     * @param side The Side in question
     * @param _default The default to return if the block doesn't exist.
     * @return True if the side is solid
     */
    @Override
    public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default)
    {
        if (!this.isValid(pos)) return _default;

        Chunk chunk = getChunkFromBlockCoords(pos);
        if (chunk == null || chunk.isEmpty()) return _default;
        return getBlockState(pos).getBlock().isSideSolid(this, pos, side);
    }

    /**
     * Get the persistent chunks for this world
     *
     * @return
     */
    public ImmutableSetMultimap<ChunkCoordIntPair, Ticket> getPersistentChunks()
    {
        return ForgeChunkManager.getPersistentChunksFor(this);
    }

    /**
     * Readded as it was removed, very useful helper function
     *
     * @param pos Block position
     * @return The blocks light opacity
     */
    public int getBlockLightOpacity(BlockPos pos)
    {
        if (!this.isValid(pos)) return 0;
        return getChunkFromBlockCoords(pos).getBlockLightOpacity(pos);
    }

    /**
     * Returns a count of entities that classify themselves as the specified creature type.
     */
    public int countEntities(EnumCreatureType type, boolean forSpawnCount)
    {
        int count = 0;
        for (int x = 0; x < loadedEntityList.size(); x++)
        {
            if (((Entity)loadedEntityList.get(x)).isCreatureType(type, forSpawnCount))
            {
                count++;
            }
        }
        return count;
    }

    protected MapStorage perWorldStorage; //Moved to a getter to simulate final without being final so we can load in subclasses.
    public MapStorage getPerWorldStorage()
    {
        return perWorldStorage;
    }

	@Override
	protected IChunkProvider createChunkProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getRenderDistanceChunks() {
		// TODO Auto-generated method stub
		return 0;
	}

}
