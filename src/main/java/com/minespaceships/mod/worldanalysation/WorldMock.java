package com.minespaceships.mod.worldanalysation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
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
	private HashMap<BlockPos, IBlockState> setBlocks;
	private ArrayList<BlockPos> removedBlocks;
	private ArrayList<BlockPos> lightChangedBlocks;
	private ArrayList<BlockPos> tileEntityAdded;
	private ArrayList<BlockPos> tileEntityRemoved;
	private ArrayList<Entity> entityAdded;
	
	public WorldMock(World infoContainer) {
		super(null, null,infoContainer.provider, null, true);
		world = infoContainer;
		setBlocks = new HashMap<BlockPos, IBlockState>();
		removedBlocks = new ArrayList<BlockPos>();
		lightChangedBlocks = new ArrayList<BlockPos>();
		tileEntityAdded = new ArrayList<BlockPos>();
		tileEntityRemoved = new ArrayList<BlockPos>();
		entityAdded = new ArrayList<Entity>();
	}	
	
	public Set<BlockPos> nextSetBlocks(){
		Set<BlockPos> out = setBlocks.keySet();
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
    	if(newState.getBlock().getMaterial() != Material.air){
    		setBlocks.put(pos, newState);
    	} else {
    		removedBlocks.add(pos);
    	}
        newState.getBlock().onBlockAdded(this, pos, newState);
        notifyNeighborsOfStateChange(pos, newState.getBlock());
        return true;
    }

    @Override
    public void markAndNotifyBlock(BlockPos pos, Chunk chunk, IBlockState old, IBlockState new_, int flags){}
    @Override
    public boolean setBlockToAir(BlockPos pos)
    {
        removedBlocks.add(pos);
        notifyNeighborsOfStateChange(pos, Block.getStateById(0).getBlock());
        return true;
    }
    @Override
    public boolean destroyBlock(BlockPos pos, boolean dropBlock)
    {
    	removedBlocks.add(pos);
    	return true;
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
    	if(setBlocks.containsKey(pos)){
    		return setBlocks.get(pos);
    	}
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
    public boolean addWeatherEffect(Entity p_72942_1_){return true;}
    @Override
    public boolean spawnEntityInWorld(Entity p_72838_1_){return true;/*TODO: make entity list!*/}
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
    public Vec3 drawCloudsBody(float p_72824_1_){return null;}
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

    public boolean addTileEntity(TileEntity tile){return true;}

    public void addTileEntities(Collection tileEntityCollection){}
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
        return world.getBlockDensity(p_72842_1_, p_72842_2_);
    }

    public boolean extinguishFire(EntityPlayer player, BlockPos pos, EnumFacing side){return false;}

    @SideOnly(Side.CLIENT)
    public String getDebugLoadedEntities()
    {
        return world.getDebugLoadedEntities();
    }

    @SideOnly(Side.CLIENT)
    public String getProviderName()
    {
        return world.getProviderName();
    }
    @Override
    public TileEntity getTileEntity(BlockPos pos)
    {
        return world.getTileEntity(pos);
    }
    @Override
    public void setTileEntity(BlockPos pos, TileEntity tileEntityIn)
    {
        tileEntityAdded.add(pos);
    }
    @Override
    public void removeTileEntity(BlockPos pos)
    {
        tileEntityRemoved.add(pos);
    }
    @Override
    public void markTileEntityForRemoval(TileEntity tileEntityIn){}
    @Override
    public boolean func_175665_u(BlockPos p_175665_1_)
    {
        return world.func_175665_u(p_175665_1_);
    }
    @Override
    public boolean isBlockNormalCube(BlockPos p_175677_1_, boolean p_175677_2_)
    {
        return world.isBlockNormalCube(p_175677_1_, p_175677_2_);
    }
    @Override
    public void calculateInitialSkylight(){}
    @Override
    public void setAllowedSpawnTypes(boolean hostile, boolean peaceful){}
    @Override
    public void tick(){}
    @Override
    protected void calculateInitialWeather(){}
    @Override
    public void calculateInitialWeatherBody(){}
    @Override
    protected void updateWeather(){}
    @Override
    public void updateWeatherBody(){}
    @Override
    protected void setActivePlayerChunksAndCheckLight(){}
    @Override
    protected void playMoodSoundAndCheckLight(int p_147467_1_, int p_147467_2_, Chunk p_147467_3_){}
    @Override
    protected void updateBlocks(){}
    @Override
    public void forceBlockUpdateTick(Block blockType, BlockPos pos, Random random){}
    @Override
    public boolean func_175675_v(BlockPos p_175675_1_)
    {
        return world.func_175675_v(p_175675_1_);
    }
    @Override
    public boolean func_175662_w(BlockPos p_175662_1_)
    {
        return world.func_175662_w(p_175662_1_);
    }
    @Override
    public boolean canBlockFreeze(BlockPos pos, boolean noWaterAdj)
    {
        return world.canBlockFreeze(pos, noWaterAdj);
    }
    @Override
    public boolean canBlockFreezeBody(BlockPos pos, boolean noWaterAdj)
    {
        return world.canBlockFreezeBody(pos, noWaterAdj);
    }
    @Override
    public boolean canSnowAt(BlockPos pos, boolean checkLight)
    {
        return world.canSnowAt(pos, checkLight);
    }
    @Override
    public boolean canSnowAtBody(BlockPos pos, boolean checkLight)
    {
        return world.canSnowAtBody(pos, checkLight);
    }
    @Override
    public boolean checkLight(BlockPos pos)
    {
        return world.checkLight(pos);
    }
    @Override
    public boolean checkLightFor(EnumSkyBlock lightType, BlockPos pos)
    {
    	return world.checkLightFor(lightType, pos);
    }
    @Override
    public boolean tickUpdates(boolean p_72955_1_)
    {
        return false;
    }
    @Override
    public List getPendingBlockUpdates(Chunk p_72920_1_, boolean p_72920_2_)
    {
        return null;
    }
    @Override
    public List func_175712_a(StructureBoundingBox p_175712_1_, boolean p_175712_2_)
    {
        return null;
    }
    @Override
    public List getEntitiesWithinAABBExcludingEntity(Entity entityIn, AxisAlignedBB p_72839_2_)
    {
        return world.getEntitiesWithinAABBExcludingEntity(entityIn, p_72839_2_);
    }
    @Override
    public List func_175674_a(Entity entityIn, AxisAlignedBB p_175674_2_, Predicate p_175674_3_)
    {
        return world.func_175674_a(entityIn, p_175674_2_, p_175674_3_);
    }
    @Override
    public List getEntities(Class entityType, Predicate filter)
    {
        return world.getEntities(entityType, filter);
    }
    @Override
    public List getPlayers(Class playerType, Predicate filter)
    {
        return world.getPlayers(playerType, filter);
    }
    @Override
    public List getEntitiesWithinAABB(Class p_72872_1_, AxisAlignedBB p_72872_2_)
    {
        return world.getEntitiesWithinAABB(p_72872_1_, p_72872_2_);
    }
    @Override
    public List getEntitiesWithinAABB(Class clazz, AxisAlignedBB aabb, Predicate filter)
    {
        return world.getEntitiesWithinAABB(clazz, aabb, filter);
    }
    @Override
    public Entity findNearestEntityWithinAABB(Class entityType, AxisAlignedBB aabb, Entity closestTo)
    {
        return world.findNearestEntityWithinAABB(entityType, aabb, closestTo);
    }
    @Override
    public Entity getEntityByID(int id)
    {
        return world.getEntityByID(id);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public List getLoadedEntityList()
    {
        return world.loadedEntityList;
    }
    @Override
    public void markChunkDirty(BlockPos pos, TileEntity unusedTileEntity){}
    @Override
    public int countEntities(Class entityType)
    {
        return world.countEntities(entityType);
    }
    @Override
    public void loadEntities(Collection entityCollection){}
    @Override
    public void unloadEntities(Collection entityCollection){}
    @Override
    public boolean canBlockBePlaced(Block p_175716_1_, BlockPos p_175716_2_, boolean p_175716_3_, EnumFacing p_175716_4_, Entity p_175716_5_, ItemStack p_175716_6_)
    {
        return world.canBlockBePlaced(p_175716_1_, p_175716_2_, p_175716_3_, p_175716_4_, p_175716_5_, p_175716_6_);
    }
    @Override
    public int getStrongPower(BlockPos pos, EnumFacing direction)
    {
        return world.getStrongPower(pos, direction);
    }
    @Override
    public WorldType getWorldType()
    {
        return world.getWorldType();
    }
    @Override
    public int getStrongPower(BlockPos pos)
    {
    	return world.getStrongPower(pos);
    }
    @Override
    public boolean isSidePowered(BlockPos pos, EnumFacing side)
    {
        return world.isSidePowered(pos, side);
    }
    @Override
    public int getRedstonePower(BlockPos pos, EnumFacing facing)
    {
        return world.getRedstonePower(pos, facing);
    }
    @Override
    public boolean isBlockPowered(BlockPos pos)
    {
        return world.isBlockPowered(pos);
    }
    @Override
    public int isBlockIndirectlyGettingPowered(BlockPos pos)
    {
        return world.isBlockIndirectlyGettingPowered(pos);
    }
    @Override
    public EntityPlayer getClosestPlayerToEntity(Entity entityIn, double distance)
    {
        return world.getClosestPlayerToEntity(entityIn, distance);
    }
    @Override
    public EntityPlayer getClosestPlayer(double x, double y, double z, double distance)
    {
        return world.getClosestPlayer(x, y, z, distance);
    }
    @Override
    public boolean func_175636_b(double p_175636_1_, double p_175636_3_, double p_175636_5_, double p_175636_7_)
    {
        return world.func_175636_b(p_175636_1_, p_175636_3_, p_175636_5_, p_175636_7_);
    }
    @Override
    public EntityPlayer getPlayerEntityByName(String name)
    {
        return world.getPlayerEntityByName(name);
    }
    @Override
    public EntityPlayer getPlayerEntityByUUID(UUID uuid)
    {
        return world.getPlayerEntityByUUID(uuid);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void sendQuittingDisconnectingPacket() {}
    @Override
    public void checkSessionLock() throws MinecraftException {}
    @Override
    @SideOnly(Side.CLIENT)
    public void setTotalWorldTime(long p_82738_1_){}
    @Override
    public long getSeed()
    {
        return world.getSeed();
    }
    @Override
    public long getTotalWorldTime()
    {
        return world.getTotalWorldTime();
    }
    @Override
    public long getWorldTime()
    {
        return world.getWorldTime();
    }
    @Override
    public void setWorldTime(long time){}
    @Override
    public BlockPos getSpawnPoint()
    {
        return world.getSpawnPoint();
    }
    @Override
    public void setSpawnPoint(BlockPos pos){}
    @Override
    @SideOnly(Side.CLIENT)
    public void joinEntityInSurroundings(Entity entityIn){}
    @Override
    public boolean isBlockModifiable(EntityPlayer player, BlockPos pos)
    {
        return world.isBlockModifiable(player, pos);
    }
    @Override
    public boolean canMineBlockBody(EntityPlayer player, BlockPos pos)
    {
        return world.canMineBlockBody(player, pos);
    }
    @Override
    public void setEntityState(Entity entityIn, byte state) {}
    @Override
    public IChunkProvider getChunkProvider()
    {
        return world.getChunkProvider();
    }
    @Override
    public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam){}
    @Override
    public ISaveHandler getSaveHandler()
    {
        return world.getSaveHandler();
    }
    @Override
    public WorldInfo getWorldInfo()
    {
        return world.getWorldInfo();
    }
    @Override
    public GameRules getGameRules()
    {
        return world.getGameRules();
    }
    @Override
    public void updateAllPlayersSleepingFlag() {}
    @Override
    public float getThunderStrength(float delta)
    {
        return world.getThunderStrength(delta);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void setThunderStrength(float p_147442_1_){}
    @Override
    public float getRainStrength(float delta)
    {
        return world.getRainStrength(delta);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void setRainStrength(float strength){}
    @Override
    public boolean isThundering()
    {
        return world.isThundering();
    }
    @Override
    public boolean isRaining()
    {
        return world.isRaining();
    }
    @Override
    public boolean canLightningStrike(BlockPos strikePosition)
    {
        return world.canLightningStrike(strikePosition);
    }
    @Override
    public boolean isBlockinHighHumidity(BlockPos pos)
    {
        return world.isBlockinHighHumidity(pos);
    }
    @Override
    public MapStorage getMapStorage()
    {
        return world.getMapStorage();
    }
    @Override
    public void setItemData(String p_72823_1_, WorldSavedData p_72823_2_){}
    @Override
    public WorldSavedData loadItemData(Class p_72943_1_, String p_72943_2_)
    {
        return world.loadItemData(p_72943_1_, p_72943_2_);
    }
    @Override
    public int getUniqueDataId(String key)
    {
        return world.getUniqueDataId(key);
    }
    @Override
    public void playBroadcastSound(int p_175669_1_, BlockPos p_175669_2_, int p_175669_3_){}
    @Override
    public void playAuxSFX(int p_175718_1_, BlockPos p_175718_2_, int p_175718_3_){}
    @Override
    public void playAuxSFXAtEntity(EntityPlayer p_180498_1_, int p_180498_2_, BlockPos p_180498_3_, int p_180498_4_){}
    @Override
    public int getHeight()
    {
        return world.getHeight();
    }
    @Override
    public int getActualHeight()
    {
        return world.getActualHeight();
    }
    @Override
    public Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_){return null;}
    @Override
    public BlockPos getStrongholdPos(String p_180499_1_, BlockPos p_180499_2_)
    {
        return world.getStrongholdPos(p_180499_1_, p_180499_2_);
    }
    @Override
    public CrashReportCategory addWorldInfoToCrashReport(CrashReport report){
    	return world.addWorldInfoToCrashReport(report);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public boolean extendedLevelsInChunkCache()
    {
        return false;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public double getHorizon()
    {
        return world.getHorizon();
    }
    @Override
    public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress){}
    @Override
    public Calendar getCurrentDate()
    {
        return world.getCurrentDate();
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, NBTTagCompound compund) {}
    @Override
    public Scoreboard getScoreboard()
    {
        return world.getScoreboard();
    }
    @Override
    public void updateComparatorOutputLevel(BlockPos pos, Block blockIn){}
    @Override
    public DifficultyInstance getDifficultyForLocation(BlockPos pos)
    {
        return world.getDifficultyForLocation(pos);
    }
    @Override
    public EnumDifficulty getDifficulty()
    {
        return world.getDifficulty();
    }
    @Override
    public int getSkylightSubtracted()
    {
        return world.getSkylightSubtracted();
    }
    @Override
    public void setSkylightSubtracted(int newSkylightSubtracted){}
    @Override
    @SideOnly(Side.CLIENT)
    public int getLastLightningBolt()
    {
        return world.getLastLightningBolt();
    }
    @Override
    public void setLastLightningBolt(int lastLightningBoltIn){}
    @Override
    public boolean isFindingSpawnPoint()
    {
        return world.isFindingSpawnPoint();
    }
    @Override
    public VillageCollection getVillageCollection()
    {
        return world.getVillageCollection();
    }
    @Override
    public WorldBorder getWorldBorder()
    {
        return world.getWorldBorder();
    }
    @Override
    public boolean isSpawnChunk(int x, int z)
    {
        return world.isSpawnChunk(x, z);
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
    @Override
    public boolean isSideSolid(BlockPos pos, EnumFacing side)
    {
       return world.isSideSolid(pos, side);
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
        return world.isSideSolid(pos, side, _default);
    }

    /**
     * Get the persistent chunks for this world
     *
     * @return
     */
    @Override
    public ImmutableSetMultimap<ChunkCoordIntPair, Ticket> getPersistentChunks()
    {
        return world.getPersistentChunks();
    }

    /**
     * Readded as it was removed, very useful helper function
     *
     * @param pos Block position
     * @return The blocks light opacity
     */
    @Override
    public int getBlockLightOpacity(BlockPos pos)
    {
        return world.getBlockLightOpacity(pos);
    }

    /**
     * Returns a count of entities that classify themselves as the specified creature type.
     */
    @Override
    public int countEntities(EnumCreatureType type, boolean forSpawnCount)
    {
        return world.countEntities(type, forSpawnCount);
    }

    public MapStorage getPerWorldStorage()
    {
        return world.getPerWorldStorage();
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
