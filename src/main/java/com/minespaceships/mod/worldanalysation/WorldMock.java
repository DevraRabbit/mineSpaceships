package com.minespaceships.mod.worldanalysation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
	private ArrayList<BlockPos> setTileEntities, removedTileEntities;
	private ArrayList<BlockPos> settedBlocks;
	private ArrayList<BlockPos> removedBlocks;
	private ArrayList<BlockPos> forcedBlockUpdateTicks;
	private ArrayList<TileEntity> tileEntitiesToBeRemoved;
	private ArrayList<Chunk> modifiedChunks;

	protected WorldMock(World infoContainer) {
		super(null, null, null, null, true);
		world = infoContainer;
		setTileEntities = new ArrayList<BlockPos>();
		removedTileEntities = new ArrayList<BlockPos>();
		settedBlocks = new ArrayList<BlockPos>();
		removedBlocks = new ArrayList<BlockPos>();
		forcedBlockUpdateTicks = new ArrayList<BlockPos>();
		tileEntitiesToBeRemoved = new ArrayList<TileEntity>();
		modifiedChunks = new ArrayList<Chunk>();
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
		settedBlocks.add(pos);
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

	public int getLight(BlockPos pos)
	{
		if (pos.getY() < 0)
		{
			return 0;
		}
		else
		{
			if (pos.getY() >= 256)
			{
				pos = new BlockPos(pos.getX(), 255, pos.getZ());
			}

			return this.getChunkFromBlockCoords(pos).setLight(pos, 0);
		}
	}

	public int getLightFromNeighbors(BlockPos pos)
	{
		return this.getLight(pos, true);
	}

	public int getLight(BlockPos pos, boolean checkNeighbors)
	{
		if (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000)
		{
			if (checkNeighbors && this.getBlockState(pos).getBlock().getUseNeighborBrightness())
			{
				int i1 = this.getLight(pos.up(), false);
				int i = this.getLight(pos.east(), false);
				int j = this.getLight(pos.west(), false);
				int k = this.getLight(pos.south(), false);
				int l = this.getLight(pos.north(), false);

				if (i > i1)
				{
					i1 = i;
				}

				if (j > i1)
				{
					i1 = j;
				}

				if (k > i1)
				{
					i1 = k;
				}

				if (l > i1)
				{
					i1 = l;
				}

				return i1;
			}
			else if (pos.getY() < 0)
			{
				return 0;
			}
			else
			{
				if (pos.getY() >= 256)
				{
					pos = new BlockPos(pos.getX(), 255, pos.getZ());
				}

				Chunk chunk = this.getChunkFromBlockCoords(pos);
				return chunk.setLight(pos, this.skylightSubtracted);
			}
		}
		else
		{
			return 15;
		}
	}

	public BlockPos getHorizon(BlockPos pos)
	{
		int i;

		if (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000)
		{
			if (this.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4, true))
			{
				i = this.getChunkFromChunkCoords(pos.getX() >> 4, pos.getZ() >> 4).getHeight(pos.getX() & 15, pos.getZ() & 15);
			}
			else
			{
				i = 0;
			}
		}
		else
		{
			i = 64;
		}

		return new BlockPos(pos.getX(), i, pos.getZ());
	}

	public int getChunksLowestHorizon(int x, int z)
	{
		if (x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000)
		{
			if (!this.isChunkLoaded(x >> 4, z >> 4, true))
			{
				return 0;
			}
			else
			{
				Chunk chunk = this.getChunkFromChunkCoords(x >> 4, z >> 4);
				return chunk.getLowestHeight();
			}
		}
		else
		{
			return 64;
		}
	}

	public int getLightFor(EnumSkyBlock type, BlockPos pos)
	{
		if (pos.getY() < 0)
		{
			pos = new BlockPos(pos.getX(), 0, pos.getZ());
		}

		if (!this.isValid(pos))
		{
			return type.defaultLightValue;
		}
		else if (!this.isBlockLoaded(pos))
		{
			return type.defaultLightValue;
		}
		else
		{
			Chunk chunk = this.getChunkFromBlockCoords(pos);
			return chunk.getLightFor(type, pos);
		}
	}

	@SideOnly(Side.CLIENT)
	public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos p_175705_2_)
	{
		if (this.provider.getHasNoSky() && type == EnumSkyBlock.SKY)
		{
			return 0;
		}
		else
		{
			if (p_175705_2_.getY() < 0)
			{
				p_175705_2_ = new BlockPos(p_175705_2_.getX(), 0, p_175705_2_.getZ());
			}

			if (!this.isValid(p_175705_2_))
			{
				return type.defaultLightValue;
			}
			else if (!this.isBlockLoaded(p_175705_2_))
			{
				return type.defaultLightValue;
			}
			else if (this.getBlockState(p_175705_2_).getBlock().getUseNeighborBrightness())
			{
				int i1 = this.getLightFor(type, p_175705_2_.up());
				int i = this.getLightFor(type, p_175705_2_.east());
				int j = this.getLightFor(type, p_175705_2_.west());
				int k = this.getLightFor(type, p_175705_2_.south());
				int l = this.getLightFor(type, p_175705_2_.north());

				if (i > i1)
				{
					i1 = i;
				}

				if (j > i1)
				{
					i1 = j;
				}

				if (k > i1)
				{
					i1 = k;
				}

				if (l > i1)
				{
					i1 = l;
				}

				return i1;
			}
			else
			{
				Chunk chunk = this.getChunkFromBlockCoords(p_175705_2_);
				return chunk.getLightFor(type, p_175705_2_);
			}
		}
	}

	public void setLightFor(EnumSkyBlock type, BlockPos pos, int lightValue)
	{
		if (this.isValid(pos))
		{
			if (this.isBlockLoaded(pos))
			{
				Chunk chunk = this.getChunkFromBlockCoords(pos);
				chunk.setLightFor(type, pos, lightValue);
				this.notifyLightSet(pos);
			}
		}
	}

	public void notifyLightSet(BlockPos pos)
	{
		for (int i = 0; i < this.worldAccesses.size(); ++i)
		{
			((IWorldAccess)this.worldAccesses.get(i)).notifyLightSet(pos);
		}
	}

	@SideOnly(Side.CLIENT)
	public int getCombinedLight(BlockPos pos, int p_175626_2_)
	{
		int j = this.getLightFromNeighborsFor(EnumSkyBlock.SKY, pos);
		int k = this.getLightFromNeighborsFor(EnumSkyBlock.BLOCK, pos);

		if (k < p_175626_2_)
		{
			k = p_175626_2_;
		}

		return j << 20 | k << 4;
	}

	public float getLightBrightness(BlockPos pos)
	{
		return this.provider.getLightBrightnessTable()[this.getLightFromNeighbors(pos)];
	}

	public IBlockState getBlockState(BlockPos pos)
	{
		if (!this.isValid(pos))
		{
			return Blocks.air.getDefaultState();
		}
		else
		{
			Chunk chunk = this.getChunkFromBlockCoords(pos);
			return chunk.getBlockState(pos);
		}
	}

	public boolean isDaytime()
	{
		return this.provider.isDaytime();
	}

	public MovingObjectPosition rayTraceBlocks(Vec3 p_72933_1_, Vec3 p_72933_2_)
	{
		return this.rayTraceBlocks(p_72933_1_, p_72933_2_, false, false, false);
	}

	public MovingObjectPosition rayTraceBlocks(Vec3 p_72901_1_, Vec3 p_72901_2_, boolean p_72901_3_)
	{
		return this.rayTraceBlocks(p_72901_1_, p_72901_2_, p_72901_3_, false, false);
	}

	public MovingObjectPosition rayTraceBlocks(Vec3 p_147447_1_, Vec3 p_147447_2_, boolean p_147447_3_, boolean p_147447_4_, boolean p_147447_5_)
	{
		if (!Double.isNaN(p_147447_1_.xCoord) && !Double.isNaN(p_147447_1_.yCoord) && !Double.isNaN(p_147447_1_.zCoord))
		{
			if (!Double.isNaN(p_147447_2_.xCoord) && !Double.isNaN(p_147447_2_.yCoord) && !Double.isNaN(p_147447_2_.zCoord))
			{
				int i = MathHelper.floor_double(p_147447_2_.xCoord);
				int j = MathHelper.floor_double(p_147447_2_.yCoord);
				int k = MathHelper.floor_double(p_147447_2_.zCoord);
				int l = MathHelper.floor_double(p_147447_1_.xCoord);
				int i1 = MathHelper.floor_double(p_147447_1_.yCoord);
				int j1 = MathHelper.floor_double(p_147447_1_.zCoord);
				BlockPos blockpos = new BlockPos(l, i1, j1);
				new BlockPos(i, j, k);
				IBlockState iblockstate = this.getBlockState(blockpos);
				Block block = iblockstate.getBlock();

				if ((!p_147447_4_ || block.getCollisionBoundingBox(this, blockpos, iblockstate) != null) && block.canCollideCheck(iblockstate, p_147447_3_))
				{
					MovingObjectPosition movingobjectposition = block.collisionRayTrace(this, blockpos, p_147447_1_, p_147447_2_);

					if (movingobjectposition != null)
					{
						return movingobjectposition;
					}
				}

				MovingObjectPosition movingobjectposition2 = null;
				int k1 = 200;

				while (k1-- >= 0)
				{
					if (Double.isNaN(p_147447_1_.xCoord) || Double.isNaN(p_147447_1_.yCoord) || Double.isNaN(p_147447_1_.zCoord))
					{
						return null;
					}

					if (l == i && i1 == j && j1 == k)
					{
						return p_147447_5_ ? movingobjectposition2 : null;
					}

					boolean flag5 = true;
					boolean flag3 = true;
					boolean flag4 = true;
					double d0 = 999.0D;
					double d1 = 999.0D;
					double d2 = 999.0D;

					if (i > l)
					{
						d0 = (double)l + 1.0D;
					}
					else if (i < l)
					{
						d0 = (double)l + 0.0D;
					}
					else
					{
						flag5 = false;
					}

					if (j > i1)
					{
						d1 = (double)i1 + 1.0D;
					}
					else if (j < i1)
					{
						d1 = (double)i1 + 0.0D;
					}
					else
					{
						flag3 = false;
					}

					if (k > j1)
					{
						d2 = (double)j1 + 1.0D;
					}
					else if (k < j1)
					{
						d2 = (double)j1 + 0.0D;
					}
					else
					{
						flag4 = false;
					}

					double d3 = 999.0D;
					double d4 = 999.0D;
					double d5 = 999.0D;
					double d6 = p_147447_2_.xCoord - p_147447_1_.xCoord;
					double d7 = p_147447_2_.yCoord - p_147447_1_.yCoord;
					double d8 = p_147447_2_.zCoord - p_147447_1_.zCoord;

					if (flag5)
					{
						d3 = (d0 - p_147447_1_.xCoord) / d6;
					}

					if (flag3)
					{
						d4 = (d1 - p_147447_1_.yCoord) / d7;
					}

					if (flag4)
					{
						d5 = (d2 - p_147447_1_.zCoord) / d8;
					}

					if (d3 == -0.0D)
					{
						d3 = -1.0E-4D;
					}

					if (d4 == -0.0D)
					{
						d4 = -1.0E-4D;
					}

					if (d5 == -0.0D)
					{
						d5 = -1.0E-4D;
					}

					EnumFacing enumfacing;

					if (d3 < d4 && d3 < d5)
					{
						enumfacing = i > l ? EnumFacing.WEST : EnumFacing.EAST;
						p_147447_1_ = new Vec3(d0, p_147447_1_.yCoord + d7 * d3, p_147447_1_.zCoord + d8 * d3);
					}
					else if (d4 < d5)
					{
						enumfacing = j > i1 ? EnumFacing.DOWN : EnumFacing.UP;
						p_147447_1_ = new Vec3(p_147447_1_.xCoord + d6 * d4, d1, p_147447_1_.zCoord + d8 * d4);
					}
					else
					{
						enumfacing = k > j1 ? EnumFacing.NORTH : EnumFacing.SOUTH;
						p_147447_1_ = new Vec3(p_147447_1_.xCoord + d6 * d5, p_147447_1_.yCoord + d7 * d5, d2);
					}

					l = MathHelper.floor_double(p_147447_1_.xCoord) - (enumfacing == EnumFacing.EAST ? 1 : 0);
					i1 = MathHelper.floor_double(p_147447_1_.yCoord) - (enumfacing == EnumFacing.UP ? 1 : 0);
					j1 = MathHelper.floor_double(p_147447_1_.zCoord) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
					blockpos = new BlockPos(l, i1, j1);
					IBlockState iblockstate1 = this.getBlockState(blockpos);
					Block block1 = iblockstate1.getBlock();

					if (!p_147447_4_ || block1.getCollisionBoundingBox(this, blockpos, iblockstate1) != null)
					{
						if (block1.canCollideCheck(iblockstate1, p_147447_3_))
						{
							MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(this, blockpos, p_147447_1_, p_147447_2_);

							if (movingobjectposition1 != null)
							{
								return movingobjectposition1;
							}
						}
						else
						{
							movingobjectposition2 = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, p_147447_1_, enumfacing, blockpos);
						}
					}
				}

				return p_147447_5_ ? movingobjectposition2 : null;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}

	public void playSoundAtEntity(Entity p_72956_1_, String p_72956_2_, float p_72956_3_, float p_72956_4_)
	{
		p_72956_2_ = net.minecraftforge.event.ForgeEventFactory.onPlaySoundAt(p_72956_1_, p_72956_2_, p_72956_3_, p_72956_4_);
		if (p_72956_2_ == null) return;
		for (int i = 0; i < this.worldAccesses.size(); ++i)
		{
			((IWorldAccess)this.worldAccesses.get(i)).playSound(p_72956_2_, p_72956_1_.posX, p_72956_1_.posY, p_72956_1_.posZ, p_72956_3_, p_72956_4_);
		}
	}

	public void playSoundToNearExcept(EntityPlayer p_85173_1_, String p_85173_2_, float p_85173_3_, float p_85173_4_)
	{
		p_85173_2_ = net.minecraftforge.event.ForgeEventFactory.onPlaySoundAt(p_85173_1_, p_85173_2_, p_85173_3_, p_85173_4_);
		if (p_85173_2_ == null) return;
		for (int i = 0; i < this.worldAccesses.size(); ++i)
		{
			((IWorldAccess)this.worldAccesses.get(i)).playSoundToNearExcept(p_85173_1_, p_85173_2_, p_85173_1_.posX, p_85173_1_.posY, p_85173_1_.posZ, p_85173_3_, p_85173_4_);
		}
	}

	public void playSoundEffect(double x, double y, double z, String soundName, float volume, float pitch)
	{
		for (int i = 0; i < this.worldAccesses.size(); ++i)
		{
			((IWorldAccess)this.worldAccesses.get(i)).playSound(soundName, x, y, z, volume, pitch);
		}
	}

	public void playSound(double x, double y, double z, String soundName, float volume, float pitch, boolean distanceDelay) {}

	public void playRecord(BlockPos p_175717_1_, String p_175717_2_)
	{
		for (int i = 0; i < this.worldAccesses.size(); ++i)
		{
			((IWorldAccess)this.worldAccesses.get(i)).playRecord(p_175717_2_, p_175717_1_);
		}
	}

	public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int ... p_175688_14_)
	{
		this.spawnParticle(particleType.getParticleID(), particleType.func_179344_e(), xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_175688_14_);
	}

	@SideOnly(Side.CLIENT)
	public void spawnParticle(EnumParticleTypes particleType, boolean p_175682_2_, double p_175682_3_, double p_175682_5_, double p_175682_7_, double p_175682_9_, double p_175682_11_, double p_175682_13_, int ... p_175682_15_)
	{
		this.spawnParticle(particleType.getParticleID(), particleType.func_179344_e() | p_175682_2_, p_175682_3_, p_175682_5_, p_175682_7_, p_175682_9_, p_175682_11_, p_175682_13_, p_175682_15_);
	}

	private void spawnParticle(int p_175720_1_, boolean p_175720_2_, double xCood, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int ... p_175720_15_)
	{
		for (int k = 0; k < this.worldAccesses.size(); ++k)
		{
			((IWorldAccess)this.worldAccesses.get(k)).spawnParticle(p_175720_1_, p_175720_2_, xCood, yCoord, zCoord, xOffset, yOffset, zOffset, p_175720_15_);
		}
	}

	public boolean addWeatherEffect(Entity p_72942_1_)
	{
		this.weatherEffects.add(p_72942_1_);
		return true;
	}

	public boolean spawnEntityInWorld(Entity p_72838_1_)
	{
		// do not drop any items while restoring blocksnapshots. Prevents dupes
		if (!this.isRemote && (p_72838_1_ == null || (p_72838_1_ instanceof net.minecraft.entity.item.EntityItem && this.restoringBlockSnapshots))) return false;

		int i = MathHelper.floor_double(p_72838_1_.posX / 16.0D);
		int j = MathHelper.floor_double(p_72838_1_.posZ / 16.0D);
		boolean flag = p_72838_1_.forceSpawn;

		if (p_72838_1_ instanceof EntityPlayer)
		{
			flag = true;
		}

		if (!flag && !this.isChunkLoaded(i, j, true))
		{
			return false;
		}
		else
		{
			if (p_72838_1_ instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)p_72838_1_;
				this.playerEntities.add(entityplayer);
				this.updateAllPlayersSleepingFlag();
			}

			if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.EntityJoinWorldEvent(p_72838_1_, this)) && !flag) return false;

			this.getChunkFromChunkCoords(i, j).addEntity(p_72838_1_);
			this.loadedEntityList.add(p_72838_1_);
			this.onEntityAdded(p_72838_1_);
			return true;
		}
	}

	public void onEntityAdded(Entity p_72923_1_)
	{
		for (int i = 0; i < this.worldAccesses.size(); ++i)
		{
			((IWorldAccess)this.worldAccesses.get(i)).onEntityAdded(p_72923_1_);
		}
	}

	public void onEntityRemoved(Entity p_72847_1_)
	{
		for (int i = 0; i < this.worldAccesses.size(); ++i)
		{
			((IWorldAccess)this.worldAccesses.get(i)).onEntityRemoved(p_72847_1_);
		}
	}

	public void removeEntity(Entity p_72900_1_)
	{
		if (p_72900_1_.riddenByEntity != null)
		{
			p_72900_1_.riddenByEntity.mountEntity((Entity)null);
		}

		if (p_72900_1_.ridingEntity != null)
		{
			p_72900_1_.mountEntity((Entity)null);
		}

		p_72900_1_.setDead();

		if (p_72900_1_ instanceof EntityPlayer)
		{
			this.playerEntities.remove(p_72900_1_);
			this.updateAllPlayersSleepingFlag();
			this.onEntityRemoved(p_72900_1_);
		}
	}

	public void removePlayerEntityDangerously(Entity p_72973_1_)
	{
		p_72973_1_.setDead();

		if (p_72973_1_ instanceof EntityPlayer)
		{
			this.playerEntities.remove(p_72973_1_);
			this.updateAllPlayersSleepingFlag();
		}

		int i = p_72973_1_.chunkCoordX;
		int j = p_72973_1_.chunkCoordZ;

		if (p_72973_1_.addedToChunk && this.isChunkLoaded(i, j, true))
		{
			this.getChunkFromChunkCoords(i, j).removeEntity(p_72973_1_);
		}

		this.loadedEntityList.remove(p_72973_1_);
		this.onEntityRemoved(p_72973_1_);
	}

	public void addWorldAccess(IWorldAccess p_72954_1_)
	{
		this.worldAccesses.add(p_72954_1_);
	}

	public List getCollidingBoundingBoxes(Entity p_72945_1_, AxisAlignedBB p_72945_2_)
	{
		ArrayList arraylist = Lists.newArrayList();
		int i = MathHelper.floor_double(p_72945_2_.minX);
		int j = MathHelper.floor_double(p_72945_2_.maxX + 1.0D);
		int k = MathHelper.floor_double(p_72945_2_.minY);
		int l = MathHelper.floor_double(p_72945_2_.maxY + 1.0D);
		int i1 = MathHelper.floor_double(p_72945_2_.minZ);
		int j1 = MathHelper.floor_double(p_72945_2_.maxZ + 1.0D);

		for (int k1 = i; k1 < j; ++k1)
		{
			for (int l1 = i1; l1 < j1; ++l1)
			{
				if (this.isBlockLoaded(new BlockPos(k1, 64, l1)))
				{
					for (int i2 = k - 1; i2 < l; ++i2)
					{
						BlockPos blockpos = new BlockPos(k1, i2, l1);
						boolean flag = p_72945_1_.isOutsideBorder();
						boolean flag1 = this.isInsideBorder(this.getWorldBorder(), p_72945_1_);

						if (flag && flag1)
						{
							p_72945_1_.setOutsideBorder(false);
						}
						else if (!flag && !flag1)
						{
							p_72945_1_.setOutsideBorder(true);
						}

						IBlockState iblockstate;

						if (!this.getWorldBorder().contains(blockpos) && flag1)
						{
							iblockstate = Blocks.stone.getDefaultState();
						}
						else
						{
							iblockstate = this.getBlockState(blockpos);
						}

						iblockstate.getBlock().addCollisionBoxesToList(this, blockpos, iblockstate, p_72945_2_, arraylist, p_72945_1_);
					}
				}
			}
		}

		double d0 = 0.25D;
		List list = this.getEntitiesWithinAABBExcludingEntity(p_72945_1_, p_72945_2_.expand(d0, d0, d0));

		for (int j2 = 0; j2 < list.size(); ++j2)
		{
			if (p_72945_1_.riddenByEntity != list && p_72945_1_.ridingEntity != list)
			{
				AxisAlignedBB axisalignedbb1 = ((Entity)list.get(j2)).getBoundingBox();

				if (axisalignedbb1 != null && axisalignedbb1.intersectsWith(p_72945_2_))
				{
					arraylist.add(axisalignedbb1);
				}

				axisalignedbb1 = p_72945_1_.getCollisionBox((Entity)list.get(j2));

				if (axisalignedbb1 != null && axisalignedbb1.intersectsWith(p_72945_2_))
				{
					arraylist.add(axisalignedbb1);
				}
			}
		}

		return arraylist;
	}

	public boolean isInsideBorder(WorldBorder p_175673_1_, Entity p_175673_2_)
	{
		double d0 = p_175673_1_.minX();
		double d1 = p_175673_1_.minZ();
		double d2 = p_175673_1_.maxX();
		double d3 = p_175673_1_.maxZ();

		if (p_175673_2_.isOutsideBorder())
		{
			++d0;
			++d1;
			--d2;
			--d3;
		}
		else
		{
			--d0;
			--d1;
			++d2;
			++d3;
		}

		return p_175673_2_.posX > d0 && p_175673_2_.posX < d2 && p_175673_2_.posZ > d1 && p_175673_2_.posZ < d3;
	}

	public List func_147461_a(AxisAlignedBB p_147461_1_)
	{
		ArrayList arraylist = Lists.newArrayList();
		int i = MathHelper.floor_double(p_147461_1_.minX);
		int j = MathHelper.floor_double(p_147461_1_.maxX + 1.0D);
		int k = MathHelper.floor_double(p_147461_1_.minY);
		int l = MathHelper.floor_double(p_147461_1_.maxY + 1.0D);
		int i1 = MathHelper.floor_double(p_147461_1_.minZ);
		int j1 = MathHelper.floor_double(p_147461_1_.maxZ + 1.0D);

		for (int k1 = i; k1 < j; ++k1)
		{
			for (int l1 = i1; l1 < j1; ++l1)
			{
				if (this.isBlockLoaded(new BlockPos(k1, 64, l1)))
				{
					for (int i2 = k - 1; i2 < l; ++i2)
					{
						BlockPos blockpos = new BlockPos(k1, i2, l1);
						IBlockState iblockstate;

						if (k1 >= -30000000 && k1 < 30000000 && l1 >= -30000000 && l1 < 30000000)
						{
							iblockstate = this.getBlockState(blockpos);
						}
						else
						{
							iblockstate = Blocks.bedrock.getDefaultState();
						}

						iblockstate.getBlock().addCollisionBoxesToList(this, blockpos, iblockstate, p_147461_1_, arraylist, (Entity)null);
					}
				}
			}
		}

		return arraylist;
	}

	public int calculateSkylightSubtracted(float p_72967_1_)
	{
		float f2 = provider.getSunBrightnessFactor(p_72967_1_);
		f2 = 1.0F - f2;
		return (int)(f2 * 11.0F);
	}

	/**
	 * The current sun brightness factor for this dimension.
	 * 0.0f means no light at all, and 1.0f means maximum sunlight.
	 * Highly recommended for sunlight detection like solar panel.
	 *
	 * @return The current brightness factor
	 * */
	public float getSunBrightnessFactor(float p_72967_1_)
	{
		float f1 = this.getCelestialAngle(p_72967_1_);
		float f2 = 1.0F - (MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F);
		f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);
		f2 = 1.0F - f2;
		f2 = (float)((double)f2 * (1.0D - (double)(this.getRainStrength(p_72967_1_) * 5.0F) / 16.0D));
		f2 = (float)((double)f2 * (1.0D - (double)(this.getThunderStrength(p_72967_1_) * 5.0F) / 16.0D));
		return f2;
	}

	public void removeWorldAccess(IWorldAccess p_72848_1_)
	{
		this.worldAccesses.remove(p_72848_1_);
	}

	@SideOnly(Side.CLIENT)
	public float getSunBrightness(float p_72971_1_)
	{
		return this.provider.getSunBrightness(p_72971_1_);
	}

	@SideOnly(Side.CLIENT)
	public float getSunBrightnessBody(float p_72971_1_)
	{
		float f1 = this.getCelestialAngle(p_72971_1_);
		float f2 = 1.0F - (MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.2F);
		f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);
		f2 = 1.0F - f2;
		f2 = (float)((double)f2 * (1.0D - (double)(this.getRainStrength(p_72971_1_) * 5.0F) / 16.0D));
		f2 = (float)((double)f2 * (1.0D - (double)(this.getThunderStrength(p_72971_1_) * 5.0F) / 16.0D));
		return f2 * 0.8F + 0.2F;
	}

	@SideOnly(Side.CLIENT)
	public Vec3 getSkyColor(Entity p_72833_1_, float p_72833_2_)
	{
		return this.provider.getSkyColor(p_72833_1_, p_72833_2_);
	}

	@SideOnly(Side.CLIENT)
	public Vec3 getSkyColorBody(Entity p_72833_1_, float p_72833_2_)
	{
		float f1 = this.getCelestialAngle(p_72833_2_);
		float f2 = MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;
		f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);
		int i = MathHelper.floor_double(p_72833_1_.posX);
		int j = MathHelper.floor_double(p_72833_1_.posY);
		int k = MathHelper.floor_double(p_72833_1_.posZ);
		BlockPos blockpos = new BlockPos(i, j, k);
		int l = net.minecraftforge.client.ForgeHooksClient.getSkyBlendColour(this, blockpos);
		float f4 = (float)(l >> 16 & 255) / 255.0F;
		float f5 = (float)(l >> 8 & 255) / 255.0F;
		float f6 = (float)(l & 255) / 255.0F;
		f4 *= f2;
		f5 *= f2;
		f6 *= f2;
		float f7 = this.getRainStrength(p_72833_2_);
		float f8;
		float f9;

		if (f7 > 0.0F)
		{
			f8 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.6F;
			f9 = 1.0F - f7 * 0.75F;
			f4 = f4 * f9 + f8 * (1.0F - f9);
			f5 = f5 * f9 + f8 * (1.0F - f9);
			f6 = f6 * f9 + f8 * (1.0F - f9);
		}

		f8 = this.getThunderStrength(p_72833_2_);

		if (f8 > 0.0F)
		{
			f9 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.2F;
			float f10 = 1.0F - f8 * 0.75F;
			f4 = f4 * f10 + f9 * (1.0F - f10);
			f5 = f5 * f10 + f9 * (1.0F - f10);
			f6 = f6 * f10 + f9 * (1.0F - f10);
		}

		if (this.lastLightningBolt > 0)
		{
			f9 = (float)this.lastLightningBolt - p_72833_2_;

			if (f9 > 1.0F)
			{
				f9 = 1.0F;
			}

			f9 *= 0.45F;
			f4 = f4 * (1.0F - f9) + 0.8F * f9;
			f5 = f5 * (1.0F - f9) + 0.8F * f9;
			f6 = f6 * (1.0F - f9) + 1.0F * f9;
		}

		return new Vec3((double)f4, (double)f5, (double)f6);
	}

	public float getCelestialAngle(float p_72826_1_)
	{
		return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), p_72826_1_);
	}

	@SideOnly(Side.CLIENT)
	public int getMoonPhase()
	{
		return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
	}

	public float getCurrentMoonPhaseFactor()
	{
		return provider.getCurrentMoonPhaseFactor();
	}

	public float getCurrentMoonPhaseFactorBody()
	{
		return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
	}

	public float getCelestialAngleRadians(float p_72929_1_)
	{
		float f1 = this.getCelestialAngle(p_72929_1_);
		return f1 * (float)Math.PI * 2.0F;
	}

	@SideOnly(Side.CLIENT)
	public Vec3 getCloudColour(float p_72824_1_)
	{
		return this.provider.drawClouds(p_72824_1_);
	}

	@SideOnly(Side.CLIENT)
	public Vec3 drawCloudsBody(float p_72824_1_)
	{
		float f1 = this.getCelestialAngle(p_72824_1_);
		float f2 = MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;
		f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);
		float f3 = (float)(this.cloudColour >> 16 & 255L) / 255.0F;
		float f4 = (float)(this.cloudColour >> 8 & 255L) / 255.0F;
		float f5 = (float)(this.cloudColour & 255L) / 255.0F;
		float f6 = this.getRainStrength(p_72824_1_);
		float f7;
		float f8;

		if (f6 > 0.0F)
		{
			f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
			f8 = 1.0F - f6 * 0.95F;
			f3 = f3 * f8 + f7 * (1.0F - f8);
			f4 = f4 * f8 + f7 * (1.0F - f8);
			f5 = f5 * f8 + f7 * (1.0F - f8);
		}

		f3 *= f2 * 0.9F + 0.1F;
		f4 *= f2 * 0.9F + 0.1F;
		f5 *= f2 * 0.85F + 0.15F;
		f7 = this.getThunderStrength(p_72824_1_);

		if (f7 > 0.0F)
		{
			f8 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
			float f9 = 1.0F - f7 * 0.95F;
			f3 = f3 * f9 + f8 * (1.0F - f9);
			f4 = f4 * f9 + f8 * (1.0F - f9);
			f5 = f5 * f9 + f8 * (1.0F - f9);
		}

		return new Vec3((double)f3, (double)f4, (double)f5);
	}

	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float p_72948_1_)
	{
		float f1 = this.getCelestialAngle(p_72948_1_);
		return this.provider.getFogColor(f1, p_72948_1_);
	}

	public BlockPos getPrecipitationHeight(BlockPos p_175725_1_)
	{
		return this.getChunkFromBlockCoords(p_175725_1_).getPrecipitationHeight(p_175725_1_);
	}

	public BlockPos getTopSolidOrLiquidBlock(BlockPos pos)
	{
		Chunk chunk = this.getChunkFromBlockCoords(pos);
		BlockPos blockpos1;
		BlockPos blockpos2;

		for (blockpos1 = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos1.getY() >= 0; blockpos1 = blockpos2)
		{
			blockpos2 = blockpos1.down();
			Block block = chunk.getBlock(blockpos2);

			if (block.getMaterial().blocksMovement() && !block.isLeaves(this, blockpos2) && !block.isFoliage(this, blockpos2))
			{
				break;
			}
		}

		return blockpos1;
	}

	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float p_72880_1_)
	{
		return this.provider.getStarBrightness(p_72880_1_);
	}

	@SideOnly(Side.CLIENT)
	public float getStarBrightnessBody(float p_72880_1_)
	{
		float f1 = this.getCelestialAngle(p_72880_1_);
		float f2 = 1.0F - (MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.25F);
		f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);
		return f2 * f2 * 0.5F;
	}

	public void scheduleUpdate(BlockPos pos, Block blockIn, int delay) {}

	public void updateBlockTick(BlockPos p_175654_1_, Block p_175654_2_, int p_175654_3_, int p_175654_4_) {}

	public void func_180497_b(BlockPos pos, Block p_180497_2_, int p_180497_3_, int p_180497_4_) {}

	public void updateEntities()
	{
		this.theProfiler.startSection("entities");
		this.theProfiler.startSection("global");
		int i;
		Entity entity;
		CrashReport crashreport;
		CrashReportCategory crashreportcategory;

		for (i = 0; i < this.weatherEffects.size(); ++i)
		{
			entity = (Entity)this.weatherEffects.get(i);

			try
			{
				++entity.ticksExisted;
				entity.onUpdate();
			}
			catch (Throwable throwable2)
			{
				crashreport = CrashReport.makeCrashReport(throwable2, "Ticking entity");
				crashreportcategory = crashreport.makeCategory("Entity being ticked");

				if (entity == null)
				{
					crashreportcategory.addCrashSection("Entity", "~~NULL~~");
				}
				else
				{
					entity.addEntityCrashInfo(crashreportcategory);
				}

				if (ForgeModContainer.removeErroringEntities)
				{
					FMLLog.severe(crashreport.getCompleteReport());
					removeEntity(entity);
				}
				else
				{
					throw new ReportedException(crashreport);
				}
			}

			if (entity.isDead)
			{
				this.weatherEffects.remove(i--);
			}
		}

		this.theProfiler.endStartSection("remove");
		this.loadedEntityList.removeAll(this.unloadedEntityList);
		int j;
		int l;

		for (i = 0; i < this.unloadedEntityList.size(); ++i)
		{
			entity = (Entity)this.unloadedEntityList.get(i);
			j = entity.chunkCoordX;
			l = entity.chunkCoordZ;

			if (entity.addedToChunk && this.isChunkLoaded(j, l, true))
			{
				this.getChunkFromChunkCoords(j, l).removeEntity(entity);
			}
		}

		for (i = 0; i < this.unloadedEntityList.size(); ++i)
		{
			this.onEntityRemoved((Entity)this.unloadedEntityList.get(i));
		}

		this.unloadedEntityList.clear();
		this.theProfiler.endStartSection("regular");

		for (i = 0; i < this.loadedEntityList.size(); ++i)
		{
			entity = (Entity)this.loadedEntityList.get(i);

			if (entity.ridingEntity != null)
			{
				if (!entity.ridingEntity.isDead && entity.ridingEntity.riddenByEntity == entity)
				{
					continue;
				}

				entity.ridingEntity.riddenByEntity = null;
				entity.ridingEntity = null;
			}

			this.theProfiler.startSection("tick");

			if (!entity.isDead)
			{
				try
				{
					this.updateEntity(entity);
				}
				catch (Throwable throwable1)
				{
					crashreport = CrashReport.makeCrashReport(throwable1, "Ticking entity");
					crashreportcategory = crashreport.makeCategory("Entity being ticked");
					entity.addEntityCrashInfo(crashreportcategory);
					if (ForgeModContainer.removeErroringEntities)
					{
						FMLLog.severe(crashreport.getCompleteReport());
						removeEntity(entity);
					}
					else
					{
						throw new ReportedException(crashreport);
					}

				}
			}

			this.theProfiler.endSection();
			this.theProfiler.startSection("remove");

			if (entity.isDead)
			{
				j = entity.chunkCoordX;
				l = entity.chunkCoordZ;

				if (entity.addedToChunk && this.isChunkLoaded(j, l, true))
				{
					this.getChunkFromChunkCoords(j, l).removeEntity(entity);
				}

				this.loadedEntityList.remove(i--);
				this.onEntityRemoved(entity);
			}

			this.theProfiler.endSection();
		}

		this.theProfiler.endStartSection("blockEntities");
		this.processingLoadedTiles = true;
		Iterator iterator = this.tickableTileEntities.iterator();

		while (iterator.hasNext())
		{
			TileEntity tileentity = (TileEntity)iterator.next();

			if (!tileentity.isInvalid() && tileentity.hasWorldObj())
			{
				BlockPos blockpos = tileentity.getPos();

				if (this.isBlockLoaded(blockpos) && this.worldBorder.contains(blockpos))
				{
					try
					{
						((IUpdatePlayerListBox)tileentity).update();
					}
					catch (Throwable throwable)
					{
						CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Ticking block entity");
						CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Block entity being ticked");
						tileentity.addInfoToCrashReport(crashreportcategory1);
						if (ForgeModContainer.removeErroringTileEntities)
						{
							FMLLog.severe(crashreport1.getCompleteReport());
							tileentity.invalidate();
							this.removeTileEntity(tileentity.getPos());
						}
						else
						{
							throw new ReportedException(crashreport1);
						}
					}
				}
			}

			if (tileentity.isInvalid())
			{
				iterator.remove();
				this.loadedTileEntityList.remove(tileentity);

				if (this.isBlockLoaded(tileentity.getPos()))
				{
					this.getChunkFromBlockCoords(tileentity.getPos()).removeTileEntity(tileentity.getPos());
				}
			}
		}

		if (!this.tileEntitiesToBeRemoved.isEmpty())
		{
			for (Object tile : tileEntitiesToBeRemoved)
			{
				((TileEntity)tile).onChunkUnload();
			}
			this.tickableTileEntities.removeAll(this.tileEntitiesToBeRemoved);
			this.loadedTileEntityList.removeAll(this.tileEntitiesToBeRemoved);
			this.tileEntitiesToBeRemoved.clear();
		}

		this.processingLoadedTiles = false;  //FML Move below remove to prevent CMEs

		this.theProfiler.endStartSection("pendingBlockEntities");

		if (!this.addedTileEntityList.isEmpty())
		{
			for (int k = 0; k < this.addedTileEntityList.size(); ++k)
			{
				TileEntity tileentity1 = (TileEntity)this.addedTileEntityList.get(k);

				if (!tileentity1.isInvalid())
				{
					if (!this.loadedTileEntityList.contains(tileentity1))
					{
						this.addTileEntity(tileentity1);
					}

					if (this.isBlockLoaded(tileentity1.getPos()))
					{
						this.getChunkFromBlockCoords(tileentity1.getPos()).addTileEntity(tileentity1.getPos(), tileentity1);
					}

					this.markBlockForUpdate(tileentity1.getPos());
				}
			}

			this.addedTileEntityList.clear();
		}

		this.theProfiler.endSection();
		this.theProfiler.endSection();
	}

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

	public void updateEntity(Entity ent)
	{
		this.updateEntityWithOptionalForce(ent, true);
	}

	public void updateEntityWithOptionalForce(Entity p_72866_1_, boolean p_72866_2_)
	{
		int i = MathHelper.floor_double(p_72866_1_.posX);
		int j = MathHelper.floor_double(p_72866_1_.posZ);
		boolean isForced = getPersistentChunks().containsKey(new ChunkCoordIntPair(i >> 4, j >> 4));
		byte b0 = isForced ? (byte)0 : 32;
		boolean canUpdate = !p_72866_2_ || this.isAreaLoaded(i - b0, 0, j - b0, i + b0, 0, j + b0, true);
		if (!canUpdate) canUpdate = net.minecraftforge.event.ForgeEventFactory.canEntityUpdate(p_72866_1_);

		if (canUpdate)
		{
			p_72866_1_.lastTickPosX = p_72866_1_.posX;
			p_72866_1_.lastTickPosY = p_72866_1_.posY;
			p_72866_1_.lastTickPosZ = p_72866_1_.posZ;
			p_72866_1_.prevRotationYaw = p_72866_1_.rotationYaw;
			p_72866_1_.prevRotationPitch = p_72866_1_.rotationPitch;

			if (p_72866_2_ && p_72866_1_.addedToChunk)
			{
				++p_72866_1_.ticksExisted;

				if (p_72866_1_.ridingEntity != null)
				{
					p_72866_1_.updateRidden();
				}
				else
				{
					p_72866_1_.onUpdate();
				}
			}

			this.theProfiler.startSection("chunkCheck");

			if (Double.isNaN(p_72866_1_.posX) || Double.isInfinite(p_72866_1_.posX))
			{
				p_72866_1_.posX = p_72866_1_.lastTickPosX;
			}

			if (Double.isNaN(p_72866_1_.posY) || Double.isInfinite(p_72866_1_.posY))
			{
				p_72866_1_.posY = p_72866_1_.lastTickPosY;
			}

			if (Double.isNaN(p_72866_1_.posZ) || Double.isInfinite(p_72866_1_.posZ))
			{
				p_72866_1_.posZ = p_72866_1_.lastTickPosZ;
			}

			if (Double.isNaN((double)p_72866_1_.rotationPitch) || Double.isInfinite((double)p_72866_1_.rotationPitch))
			{
				p_72866_1_.rotationPitch = p_72866_1_.prevRotationPitch;
			}

			if (Double.isNaN((double)p_72866_1_.rotationYaw) || Double.isInfinite((double)p_72866_1_.rotationYaw))
			{
				p_72866_1_.rotationYaw = p_72866_1_.prevRotationYaw;
			}

			int k = MathHelper.floor_double(p_72866_1_.posX / 16.0D);
			int l = MathHelper.floor_double(p_72866_1_.posY / 16.0D);
			int i1 = MathHelper.floor_double(p_72866_1_.posZ / 16.0D);

			if (!p_72866_1_.addedToChunk || p_72866_1_.chunkCoordX != k || p_72866_1_.chunkCoordY != l || p_72866_1_.chunkCoordZ != i1)
			{
				if (p_72866_1_.addedToChunk && this.isChunkLoaded(p_72866_1_.chunkCoordX, p_72866_1_.chunkCoordZ, true))
				{
					this.getChunkFromChunkCoords(p_72866_1_.chunkCoordX, p_72866_1_.chunkCoordZ).removeEntityAtIndex(p_72866_1_, p_72866_1_.chunkCoordY);
				}

				if (this.isChunkLoaded(k, i1, true))
				{
					p_72866_1_.addedToChunk = true;
					this.getChunkFromChunkCoords(k, i1).addEntity(p_72866_1_);
				}
				else
				{
					p_72866_1_.addedToChunk = false;
				}
			}

			this.theProfiler.endSection();

			if (p_72866_2_ && p_72866_1_.addedToChunk && p_72866_1_.riddenByEntity != null)
			{
				if (!p_72866_1_.riddenByEntity.isDead && p_72866_1_.riddenByEntity.ridingEntity == p_72866_1_)
				{
					this.updateEntity(p_72866_1_.riddenByEntity);
				}
				else
				{
					p_72866_1_.riddenByEntity.ridingEntity = null;
					p_72866_1_.riddenByEntity = null;
				}
			}
		}
	}

	public boolean checkNoEntityCollision(AxisAlignedBB p_72855_1_)
	{
		return this.checkNoEntityCollision(p_72855_1_, (Entity)null);
	}

	public boolean checkNoEntityCollision(AxisAlignedBB p_72917_1_, Entity p_72917_2_)
	{
		List list = this.getEntitiesWithinAABBExcludingEntity((Entity)null, p_72917_1_);

		for (int i = 0; i < list.size(); ++i)
		{
			Entity entity1 = (Entity)list.get(i);

			if (!entity1.isDead && entity1.preventEntitySpawning && entity1 != p_72917_2_ && (p_72917_2_ == null || p_72917_2_.ridingEntity != entity1 && p_72917_2_.riddenByEntity != entity1))
			{
				return false;
			}
		}

		return true;
	}

	public boolean checkBlockCollision(AxisAlignedBB p_72829_1_)
	{
		int i = MathHelper.floor_double(p_72829_1_.minX);
		int j = MathHelper.floor_double(p_72829_1_.maxX);
		int k = MathHelper.floor_double(p_72829_1_.minY);
		int l = MathHelper.floor_double(p_72829_1_.maxY);
		int i1 = MathHelper.floor_double(p_72829_1_.minZ);
		int j1 = MathHelper.floor_double(p_72829_1_.maxZ);

		for (int k1 = i; k1 <= j; ++k1)
		{
			for (int l1 = k; l1 <= l; ++l1)
			{
				for (int i2 = i1; i2 <= j1; ++i2)
				{
					Block block = this.getBlockState(new BlockPos(k1, l1, i2)).getBlock();

					if (!block.isAir(this, new BlockPos(k1, l1, i2)))
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isAnyLiquid(AxisAlignedBB p_72953_1_)
	{
		int i = MathHelper.floor_double(p_72953_1_.minX);
		int j = MathHelper.floor_double(p_72953_1_.maxX);
		int k = MathHelper.floor_double(p_72953_1_.minY);
		int l = MathHelper.floor_double(p_72953_1_.maxY);
		int i1 = MathHelper.floor_double(p_72953_1_.minZ);
		int j1 = MathHelper.floor_double(p_72953_1_.maxZ);

		for (int k1 = i; k1 <= j; ++k1)
		{
			for (int l1 = k; l1 <= l; ++l1)
			{
				for (int i2 = i1; i2 <= j1; ++i2)
				{
					Block block = this.getBlockState(new BlockPos(k1, l1, i2)).getBlock();

					if (block.getMaterial().isLiquid())
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean func_147470_e(AxisAlignedBB p_147470_1_)
	{
		int i = MathHelper.floor_double(p_147470_1_.minX);
		int j = MathHelper.floor_double(p_147470_1_.maxX + 1.0D);
		int k = MathHelper.floor_double(p_147470_1_.minY);
		int l = MathHelper.floor_double(p_147470_1_.maxY + 1.0D);
		int i1 = MathHelper.floor_double(p_147470_1_.minZ);
		int j1 = MathHelper.floor_double(p_147470_1_.maxZ + 1.0D);

		if (this.isAreaLoaded(i, k, i1, j, l, j1, true))
		{
			for (int k1 = i; k1 < j; ++k1)
			{
				for (int l1 = k; l1 < l; ++l1)
				{
					for (int i2 = i1; i2 < j1; ++i2)
					{
						Block block = this.getBlockState(new BlockPos(k1, l1, i2)).getBlock();

						if (block == Blocks.fire || block == Blocks.flowing_lava || block == Blocks.lava)
						{
							return true;
						}
						else if (block.isBurning(this, new BlockPos(k1, l1, i2)))
						{
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public boolean handleMaterialAcceleration(AxisAlignedBB p_72918_1_, Material p_72918_2_, Entity p_72918_3_)
	{
		int i = MathHelper.floor_double(p_72918_1_.minX);
		int j = MathHelper.floor_double(p_72918_1_.maxX + 1.0D);
		int k = MathHelper.floor_double(p_72918_1_.minY);
		int l = MathHelper.floor_double(p_72918_1_.maxY + 1.0D);
		int i1 = MathHelper.floor_double(p_72918_1_.minZ);
		int j1 = MathHelper.floor_double(p_72918_1_.maxZ + 1.0D);

		if (!this.isAreaLoaded(i, k, i1, j, l, j1, true))
		{
			return false;
		}
		else
		{
			boolean flag = false;
			Vec3 vec3 = new Vec3(0.0D, 0.0D, 0.0D);

			for (int k1 = i; k1 < j; ++k1)
			{
				for (int l1 = k; l1 < l; ++l1)
				{
					for (int i2 = i1; i2 < j1; ++i2)
					{
						BlockPos blockpos = new BlockPos(k1, l1, i2);
						IBlockState iblockstate = this.getBlockState(blockpos);
						Block block = iblockstate.getBlock();

						if (block.getMaterial() == p_72918_2_)
						{
							double d0 = (double)((float)(l1 + 1) - BlockLiquid.getLiquidHeightPercent(((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue()));

							if ((double)l >= d0)
							{
								flag = true;
								vec3 = block.modifyAcceleration(this, blockpos, p_72918_3_, vec3);
							}
						}
					}
				}
			}

			if (vec3.lengthVector() > 0.0D && p_72918_3_.isPushedByWater())
			{
				vec3 = vec3.normalize();
				double d1 = 0.014D;
				p_72918_3_.motionX += vec3.xCoord * d1;
				p_72918_3_.motionY += vec3.yCoord * d1;
				p_72918_3_.motionZ += vec3.zCoord * d1;
			}

			return flag;
		}
	}

	public boolean isMaterialInBB(AxisAlignedBB p_72875_1_, Material p_72875_2_)
	{
		int i = MathHelper.floor_double(p_72875_1_.minX);
		int j = MathHelper.floor_double(p_72875_1_.maxX + 1.0D);
		int k = MathHelper.floor_double(p_72875_1_.minY);
		int l = MathHelper.floor_double(p_72875_1_.maxY + 1.0D);
		int i1 = MathHelper.floor_double(p_72875_1_.minZ);
		int j1 = MathHelper.floor_double(p_72875_1_.maxZ + 1.0D);

		for (int k1 = i; k1 < j; ++k1)
		{
			for (int l1 = k; l1 < l; ++l1)
			{
				for (int i2 = i1; i2 < j1; ++i2)
				{
					if (this.getBlockState(new BlockPos(k1, l1, i2)).getBlock().getMaterial() == p_72875_2_)
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isAABBInMaterial(AxisAlignedBB p_72830_1_, Material p_72830_2_)
	{
		int i = MathHelper.floor_double(p_72830_1_.minX);
		int j = MathHelper.floor_double(p_72830_1_.maxX + 1.0D);
		int k = MathHelper.floor_double(p_72830_1_.minY);
		int l = MathHelper.floor_double(p_72830_1_.maxY + 1.0D);
		int i1 = MathHelper.floor_double(p_72830_1_.minZ);
		int j1 = MathHelper.floor_double(p_72830_1_.maxZ + 1.0D);

		for (int k1 = i; k1 < j; ++k1)
		{
			for (int l1 = k; l1 < l; ++l1)
			{
				for (int i2 = i1; i2 < j1; ++i2)
				{
					BlockPos blockpos = new BlockPos(k1, l1, i2);
					IBlockState iblockstate = this.getBlockState(blockpos);
					Block block = iblockstate.getBlock();

					if (block.getMaterial() == p_72830_2_)
					{
						int j2 = ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue();
						double d0 = (double)(l1 + 1);

						if (j2 < 8)
						{
							d0 = (double)(l1 + 1) - (double)j2 / 8.0D;
						}

						if (d0 >= p_72830_1_.minY)
						{
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public Explosion createExplosion(Entity p_72876_1_, double p_72876_2_, double p_72876_4_, double p_72876_6_, float p_72876_8_, boolean p_72876_9_)
	{
		return this.newExplosion(p_72876_1_, p_72876_2_, p_72876_4_, p_72876_6_, p_72876_8_, false, p_72876_9_);
	}

	public Explosion newExplosion(Entity p_72885_1_, double p_72885_2_, double p_72885_4_, double p_72885_6_, float p_72885_8_, boolean p_72885_9_, boolean p_72885_10_)
	{
		Explosion explosion = new Explosion(this, p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, p_72885_9_, p_72885_10_);
		if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(this, explosion)) return explosion;
		explosion.doExplosionA();
		explosion.doExplosionB(true);
		return explosion;
	}

	@Override
	public float getBlockDensity(Vec3 vector, AxisAlignedBB aabb)
	{
		return world.getBlockDensity(vector, aabb); 
	}

	@Override
	public boolean extinguishFire(EntityPlayer player, BlockPos pos, EnumFacing side)
	{
		return world.extinguishFire(player, pos, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getDebugLoadedEntities()
	{
		return "All: " + world.loadedEntityList.size();
	}

	@Override
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
		setTileEntities.add(pos);
	}

	@Override
	public void removeTileEntity(BlockPos pos)
	{
		removedTileEntities.add(pos);
	}

	@Override
	public void markTileEntityForRemoval(TileEntity tileEntityIn)
	{
		this.tileEntitiesToBeRemoved.add(tileEntityIn);
	}

	@Override
	public boolean func_175665_u(BlockPos p_175665_1_)
	{
		IBlockState iblockstate = world.getBlockState(p_175665_1_);
		AxisAlignedBB axisalignedbb = iblockstate.getBlock().getCollisionBoundingBox(world, p_175665_1_, iblockstate);
		return axisalignedbb != null && axisalignedbb.getAverageEdgeLength() >= 1.0D;
	}

	@Override
	public boolean isBlockNormalCube(BlockPos p_175677_1_, boolean p_175677_2_)
	{
		return world.isBlockNormalCube(p_175677_1_, p_175677_2_);
	}

	@Override
	public void calculateInitialSkylight()
	{
		world.calculateInitialSkylight();
	}

	@Override
	public void setAllowedSpawnTypes(boolean hostile, boolean peaceful)
	{ // TODO: log spawn types
	}

	@Override
	public void tick()
	{
	}

	@Override
	protected void calculateInitialWeather()
	{
		// this.provider.calculateInitialWeather();
		// leave this one here as comment in case game crashes without it.
	}

	@Override
	public void calculateInitialWeatherBody()
	{
		/* Same here! 
		   if (this.worldInfo.isRaining())
		{
			this.rainingStrength = 1.0F;

			if (this.worldInfo.isThundering())
			{
				this.thunderingStrength = 1.0F;
			}
		} */
	}

	@Override
	protected void updateWeather()
	{
		// this.provider.updateWeather();
	}

	@Override
	public void updateWeatherBody()
	{
		/*
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
		*/
	}

	@Override
	protected void setActivePlayerChunksAndCheckLight()
	{
		/* this.activeChunkSet.clear();
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
		*/
	}

	@Override
	protected void playMoodSoundAndCheckLight(int p_147467_1_, int p_147467_2_, Chunk p_147467_3_)
	{
		/* this.theProfiler.endStartSection("moodSound");

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
		*/
	}

	@Override
	protected void updateBlocks()
	{
		// this.setActivePlayerChunksAndCheckLight();
	}

	@Override
	public void forceBlockUpdateTick(Block blockType, BlockPos pos, Random random)
	{
		forcedBlockUpdateTicks.add(pos);
	}

	@Override
	public boolean func_175675_v(BlockPos p_175675_1_)
	{
		return world.canBlockFreeze(p_175675_1_, false);
	}

	@Override
	public boolean func_175662_w(BlockPos p_175662_1_)
	{
		return world.canBlockFreeze(p_175662_1_, true);
	}

	@Override
	public boolean canBlockFreeze(BlockPos pos, boolean noWaterAdj)
	{
		return world.provider.canBlockFreeze(pos, noWaterAdj);
	}

	@Override
	public boolean canBlockFreezeBody(BlockPos pos, boolean noWaterAdj)
	{
		return world.canBlockFreezeBody(pos, noWaterAdj);
	}

	@Override
	public boolean canSnowAt(BlockPos pos, boolean checkLight)
	{
		return world.provider.canSnowAt(pos, checkLight);
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
	public List getEntitiesWithinAABBExcludingEntity(Entity entityIn, AxisAlignedBB p_72839_2_)
	{
		return world.func_175674_a(entityIn, p_72839_2_, IEntitySelector.NOT_SPECTATING);
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
		return world.getEntitiesWithinAABB(p_72872_1_, p_72872_2_, IEntitySelector.NOT_SPECTATING);
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
		return (Entity)world.getEntityByID(id);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List getLoadedEntityList()
	{
		return world.getLoadedEntityList();
	}

	@Override
	public void markChunkDirty(BlockPos pos, TileEntity unusedTileEntity)
	{
		Chunk chunk;
		if (world.isBlockLoaded(pos) && !modifiedChunks.contains(chunk = world.getChunkFromBlockCoords(pos))) {
			modifiedChunks.add(chunk);
		}
	}

	@Override
	public int countEntities(Class entityType)
	{
		return world.countEntities(entityType);
	}

	@Override
	public void loadEntities(Collection entityCollection)
	{
	}

	@Override
	public void unloadEntities(Collection entityCollection)
	{
	}

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
	public void checkSessionLock() throws MinecraftException
	{
		world.checkSessionLock();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setTotalWorldTime(long p_82738_1_)
	{
	}

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
	public void setWorldTime(long time)
	{
	}

	@Override
	public BlockPos getSpawnPoint()
	{
		return world.getSpawnPoint();
	}

	@Override
	public void setSpawnPoint(BlockPos pos)
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void joinEntityInSurroundings(Entity entityIn)
	{
	}

	@Override
	public boolean isBlockModifiable(EntityPlayer player, BlockPos pos)
	{
		return world.isBlockModifiable(player, pos);
	}

	@Override
	public IChunkProvider getChunkProvider()
	{
		return world.getChunkProvider();
	}

	@Override
	public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam)
	{
	}

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
	public float getThunderStrength(float delta)
	{
		return world.getThunderStrength(delta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setThunderStrength(float p_147442_1_)
	{
	}

	@Override
	public float getRainStrength(float delta)
	{
		return world.getRainStrength(delta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setRainStrength(float strength)
	{
	}

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
	public void setItemData(String p_72823_1_, WorldSavedData p_72823_2_)
	{
	}

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
	public void playBroadcastSound(int p_175669_1_, BlockPos p_175669_2_, int p_175669_3_)
	{
	}

	@Override
	public void playAuxSFX(int p_175718_1_, BlockPos p_175718_2_, int p_175718_3_)
	{
	}

	@Override
	public void playAuxSFXAtEntity(EntityPlayer p_180498_1_, int p_180498_2_, BlockPos p_180498_3_, int p_180498_4_)
	{
	}

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
	public Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_)
	{
		return world.setRandomSeed(p_72843_1_, p_72843_2_, p_72843_3_);
	}

	@Override
	public BlockPos getStrongholdPos(String p_180499_1_, BlockPos p_180499_2_)
	{
		return world.getStrongholdPos(p_180499_1_, p_180499_2_);
	}

	@Override
	public CrashReportCategory addWorldInfoToCrashReport(CrashReport report)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getHorizon()
	{
		return world.getHorizon();
	}

	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress)
	{
	}

	@Override
	public Calendar getCurrentDate()
	{
		return world.getCurrentDate();
	}

	@Override
	public Scoreboard getScoreboard()
	{
		return world.getScoreboard();
	}

	@Override
	public void updateComparatorOutputLevel(BlockPos pos, Block blockIn)
	{
	}

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
	public void setSkylightSubtracted(int newSkylightSubtracted)
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getLastLightningBolt()
	{
		return world.getLastLightningBolt();
	}

	@Override
	public void setLastLightningBolt(int lastLightningBoltIn)
	{
	}

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
		return world.isSideSolid(pos, side, false);
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
		return ForgeChunkManager.getPersistentChunksFor(world);
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

	// protected MapStorage perWorldStorage; //Moved to a getter to simulate final without being final so we can load in subclasses.
	@Override
	public MapStorage getPerWorldStorage()
	{
		return world.getPerWorldStorage();
	}
}
