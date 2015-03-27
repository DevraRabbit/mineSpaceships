package com.minespaceships.mod.Generator;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import com.minespaceships.mod.Generator.WorldGeneratorTest;

public class WorldGenFloatingIslandsSmall implements IWorldGenerator
{
    private final Block block;
    private final boolean pbool;
    private static final String __OBFID = "CL_00000414";
    private BlockPos blockpos;
	private Block blocktogenerate;
	private BlockPos newblockpos;
	private BlockPos blockPosOrigin;
	private int lastChunkX;
	private int lastChunkZ;
	private int lastRandomY;
	private int islandsToGenerate;
	private Block randomHullBlock;
	private final int[][] gateMatrix = {{0,1,-1},{2,-2},{3,-3},{4,-4},{4,-4},{4,-4},{3,-3},{2,-2},{0,1,-1}};
	

	
	
	
    public WorldGenFloatingIslandsSmall(Block blockToBeGenerated, boolean pbool){
    
        this.block = blockToBeGenerated;
        this.pbool = pbool;
    }

    
   
	
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		int randomY;
		
	
		
		blockpos = new BlockPos(chunkX*16 + 8,40+random.nextInt(140),chunkZ*16 + 8);
		if ((random.nextInt(35)==1 || islandsToGenerate >1) && world.provider.getDimensionId()!=-1) {
			if (islandsToGenerate <1) {
				islandsToGenerate = 5;
				
				int randomBlock = random.nextInt(5);
				
				switch (randomBlock) {
				case 0:
					randomHullBlock = Blocks.grass;
					break;
				case 1:
					if (random.nextInt(4) == 1) {
						randomHullBlock = Blocks.grass;
					}
					else {
						randomHullBlock = Blocks.stone;	
					}
					
					break;
					
				case 2:
					randomHullBlock = Blocks.mycelium;
					break;
					
				case 3:
					randomHullBlock = Blocks.ice;
					break;
					
				case 4:
					randomHullBlock = Blocks.sandstone;
					break;

				case 5:
					if (random.nextInt(4) == 1) {
						randomHullBlock = Blocks.coal_block;
					}
					else {
						randomHullBlock = Blocks.quartz_block;	
					}
					
					break;
					
				default:
						randomHullBlock = Blocks.stone;
					break;
				}
				
			}
		Block coreToBeGenerated = WorldGeneratorTest.getRandomBlock("Core", random);
		
		
		BlockPos blockPosForHull = blockpos.add(0, -1, 0);
		for (int z = 0; z <= 5; z++) {
			for (int x = -1-z; x <= 1+z ; x++) {
				for (int y = -1-z; y <= 1+z ; y++) {
					if (world.getBlockState(blockPosForHull.add(x,z,y)).getBlock().getMaterial()!= coreToBeGenerated.getMaterial()) {
						world.setBlockState(blockPosForHull.add(x,z,y),randomHullBlock.getDefaultState());
					}
					
				}
			}
		}
		
		
		blockpos = blockpos.add(0,+2, 0);
		world.setBlockState(blockpos,coreToBeGenerated.getDefaultState());
		world.setBlockState(blockpos.add(1,0,0),coreToBeGenerated.getDefaultState());
		world.setBlockState(blockpos.add(0,0,1),coreToBeGenerated.getDefaultState());
		world.setBlockState(blockpos.add(1,0,1),coreToBeGenerated.getDefaultState());
		world.setBlockState(blockpos.add(1,1,0),coreToBeGenerated.getDefaultState());
		world.setBlockState(blockpos.add(0,1,1),coreToBeGenerated.getDefaultState());
		world.setBlockState(blockpos.add(1,1,1),coreToBeGenerated.getDefaultState());
		world.setBlockState(blockpos.add(0,1,0),coreToBeGenerated.getDefaultState());
		
		
		islandsToGenerate--;
		
		
		if (random.nextInt(9)==1) {
		
			BlockPos gatepos = blockpos.add(0, 3, 0);
			int i = 1;
			
			
			for (int z = 0; z <= 8; z++) {
				for (int x = 0; x < gateMatrix[z].length; x++) {
					try {
						world.setBlockState(gatepos.add(gateMatrix[z][x],z, 0),Blocks.stone.getDefaultState());
					} catch (Exception e) {
						world.setBlockState(gatepos.add(0,z, i), Blocks.redstone_block.getDefaultState());
						i++;
					}
					
				} 									
			}			
		}
	}
	}
}