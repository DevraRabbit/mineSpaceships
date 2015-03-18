package com.minespaceships.mod.WorldProviderSpace;

import com.google.common.collect.Lists;
import com.minespaceships.mod.Generator.WorldGeneratorAsteroid;
import com.minespaceships.mod.Generator.WorldGenFloatingIslandsSmall;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureMineshaftPieces;
import net.minecraft.world.gen.structure.StructureOceanMonument;

public class ChunkProviderSpace implements IChunkProvider
{
    private World worldObj;
    private Random random;
    private final IBlockState[] cachedBlockIDs = new IBlockState[256];
    private final List structureGenerators = Lists.newArrayList();
    private final boolean hasDecoration = false;
    private final boolean hasDungeons = true;
    private WorldGenLakes waterLakeGenerator;
    private WorldGenLakes lavaLakeGenerator;
    private static final String __OBFID = "CL_00000391";
    
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    public NoiseGeneratorOctaves noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    private double[] densities;
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;
    private final boolean spaceFeaturesEnabled = true;
    private MapGenBase caveGenerator = new MapGenCaves();
  
 
    

    public ChunkProviderSpace(World worldIn, long seed, boolean spaceFeaturesEnabled)
    {
        this.worldObj = worldIn;
        this.random = new Random(seed);

       
               

                this.structureGenerators.add(new MapGenVillage());
                this.structureGenerators.add(new MapGenStronghold());            
                this.structureGenerators.add(new StructureOceanMonument());            
                this.waterLakeGenerator = new WorldGenLakes(Blocks.end_stone);
                this.lavaLakeGenerator = new WorldGenLakes(Blocks.coal_block);
                
                
                boolean flag1 = true;
     
    }

    public Chunk provideChunk(int x, int z)
    {
        ChunkPrimer chunkprimer = new ChunkPrimer();
        int i1;

        for (int k = 0; k < this.cachedBlockIDs.length; ++k)
        {
            IBlockState iblockstate = this.cachedBlockIDs[k];

            if (iblockstate != null)
            {
                for (int l = 0; l < 16; ++l)
                {
                    for (i1 = 0; i1 < 16; ++i1)
                    {
                        chunkprimer.setBlockState(l, k, i1, iblockstate);
                    }
                }
            }
        }

        Iterator iterator = this.structureGenerators.iterator();

        while (iterator.hasNext())
        {
            MapGenBase mapgenbase = (MapGenBase)iterator.next();
            mapgenbase.func_175792_a(this, this.worldObj, x, z, chunkprimer);
        }

        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
        BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();

        for (i1 = 0; i1 < abyte.length; ++i1)
        {
            abyte[i1] = (byte)abiomegenbase[i1].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    public boolean chunkExists(int x, int z)
    {
        return true;
    }

    public void populate(IChunkProvider chunkProvider, int i, int j)
    {
        int k = i * 16;
        int l = j * 16;
        BlockPos blockpos = new BlockPos(k, 0, l);
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(new BlockPos(k + 16, 0, l + 16));
        boolean flag = false;
        this.random.setSeed(this.worldObj.getSeed());
        long i1 = this.random.nextLong() / 2L * 2L + 1L;
        long j1 = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed((long)i * i1 + (long)j * j1 ^ this.worldObj.getSeed());
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
        Iterator iterator = this.structureGenerators.iterator();

        while (iterator.hasNext())
        {
            MapGenStructure mapgenstructure = (MapGenStructure)iterator.next();
            boolean flag1 = mapgenstructure.func_175794_a(this.worldObj, this.random, chunkcoordintpair);

            if (mapgenstructure instanceof MapGenVillage)
            {
                flag |= flag1;
            }
        }

      
       

        if (this.hasDungeons)
        {
            for (int k1 = 0; k1 < 8; ++k1)
            {
                (new WorldGenDungeons()).generate(this.worldObj, this.random, blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
            }
            
            
           
        }
        
        if (random.nextInt(2)==0 && worldObj.provider.getDimensionId()==2) {
        	
			}
        if (this.hasDecoration)
        {
            biomegenbase.decorate(this.worldObj, this.random, new BlockPos(k, 0, l));
        }
    }

    public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_)
    {
        return false;
    }

    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
    {
        return true;
    }

    public void saveExtraData() {}

    public boolean unloadQueuedChunks()
    {
        return false;
    }

    public boolean canSave()
    {
        return true;
    }

    public String makeString()
    {
        return "FlatLevelSource";
    }

    public List func_177458_a(EnumCreatureType p_177458_1_, BlockPos p_177458_2_)
    {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(p_177458_2_);
        return biomegenbase.getSpawnableList(p_177458_1_);
    }

    public BlockPos getStrongholdGen(World worldIn, String p_180513_2_, BlockPos p_180513_3_)
    {
       
            Iterator iterator = this.structureGenerators.iterator();

            while (iterator.hasNext())
            {
                MapGenStructure mapgenstructure = (MapGenStructure)iterator.next();

                if (mapgenstructure instanceof MapGenStronghold)
                {
                    return mapgenstructure.getClosestStrongholdPos(worldIn, p_180513_3_);
                }
        }

        return null;
    }

    public int getLoadedChunkCount()
    {
        return 0;
    }

    public void recreateStructures(Chunk chunk, int x, int y)
    {    	
    	if (this.spaceFeaturesEnabled) {    		    		    		   		
		}
        Iterator iterator = this.structureGenerators.iterator();

        while (iterator.hasNext())
        {
            MapGenStructure mapgenstructure = (MapGenStructure)iterator.next();
            mapgenstructure.func_175792_a(this, this.worldObj, x, y, (ChunkPrimer)null);
        }
    }

    public Chunk provideChunk(BlockPos blockPosIn)
    {
        return this.provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
    }
}