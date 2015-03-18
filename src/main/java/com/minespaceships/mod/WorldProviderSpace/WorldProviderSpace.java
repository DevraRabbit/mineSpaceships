package com.minespaceships.mod.WorldProviderSpace;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderSpace extends WorldProvider{

	
	
	@Override
	public String getDimensionName() {
		return "Spaaaaaace";
	
	}

	@Override
	public String getInternalNameSuffix() {
		return "";
	}
	
	
	 public void registerWorldChunkManager()
	    {
	        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.plains, 0.0F);
	        this.dimensionId = 2;
	        this.hasNoSky = false;
	    }
	 
	 public IChunkProvider createChunkGenerator()
	    {
	        return new ChunkProviderSpace(this.worldObj,this.worldObj.getSeed(),true);
//	        return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
	    }
	 
	 
	 public float calculateCelestialAngle(long longn, float longN)
	    {
	        return 0.0F;
	    }
	 
	 
	 
	 @SideOnly(Side.CLIENT)
	    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
	    {
	            return null;
	        	            	   	          
	    }
	 
	 
	 
	 @SideOnly(Side.CLIENT)
	    public Vec3 getFogColor(float p_76562_1_, float p_76562_2_)
	    {
	        int i = 10518688;
	        float f2 = MathHelper.cos(p_76562_1_ * (float)Math.PI * 2.0F) * 2.0F + 0.5F;
	        f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);
	        float f3 = (float)(i >> 16 & 255) / 255.0F;
	        float f4 = (float)(i >> 8 & 255) / 255.0F;
	        float f5 = (float)(i & 255) / 255.0F;
	        f3 *= f2 * 0.0F + 0.15F;
	        f4 *= f2 * 0.0F + 0.15F;
	        f5 *= f2 * 0.0F + 0.15F;
	        return new Vec3((double)f3, (double)f4, (double)f5);
	    }
	 
	 @SideOnly(Side.CLIENT)
	    public boolean isSkyColored()
	    {
	        return false;
	    }

	    public boolean canRespawnHere()
	    {
	        return true;
	    }

	    public boolean isSurfaceWorld()
	    {
	        return false;
	    }

	    @SideOnly(Side.CLIENT)
	    public float getCloudHeight()
	    {
	        return 8.0F;
	    }
	    
	    
	    public boolean canCoordinateBeSpawn(int x, int z)
	    {
	        return this.worldObj.getGroundAboveSeaLevel(new BlockPos(x, 0, z)).getMaterial().blocksMovement();
	    }

	    public BlockPos getSpawnCoordinate()
	    {
	        return new BlockPos(100, 50, 0);
	    }

	    public int getAverageGroundLevel()
	    {
	        return 80;
	    }

	    @SideOnly(Side.CLIENT)
	    public boolean doesXZShowFog(int x, int z)
	    {
	        return true;
	    }
	}
