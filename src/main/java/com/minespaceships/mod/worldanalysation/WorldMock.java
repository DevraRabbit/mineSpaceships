
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
