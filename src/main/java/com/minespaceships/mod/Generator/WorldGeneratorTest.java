package com.minespaceships.mod.Generator;



import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGeneratorTest implements IWorldGenerator
{
	
	private BlockPos blockpos;
	private Block blocktogenerate;
	private BlockPos newblockpos;
	private BlockPos blockPosOrigin;
	private int lastChunkX;
	private int lastChunkZ;
	private int lastRandomY;
	
	private final static int[][] adjustendBlocks = {{1,0,0},{0,0,1},{0,1,0},{-1,0,0},{0,0,-1},{0,-1,0}};
	
	public WorldGeneratorTest(Block block) {
		blocktogenerate = block;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		int randomY;
		if (testIfNearChunkWasGenerated(chunkX, chunkZ)) {
			randomY = lastRandomY;	
		} else {
			randomY = 250 - random.nextInt(190);
		}
	
		blockPosOrigin = new BlockPos(chunkX*16,randomY,chunkZ*16);
		blockpos = new BlockPos(chunkX*16 + 8 +random.nextInt(8) -random.nextInt(8),randomY,chunkZ*16 + 8 + random.nextInt(8) - random.nextInt(8));
		if ((random.nextInt(28)==1 || (testIfNearChunkWasGenerated(chunkX, chunkZ) && random.nextBoolean())) && world.provider.getDimensionId()!=-1) {
			
		Block oretobegenerated = getRandomBlock("Ore", random);
		world.setBlockState(blockpos,oretobegenerated.getDefaultState());
		world.setBlockState(blockpos.add(3, 3, 3),oretobegenerated.getDefaultState());
		for (int j = 0; j < 5; j++) {
		for (int i = 0; i < 3 + random.nextInt(2); i++) {
			if (random.nextBoolean()) {
				BlockPos blockposnewRND = blockpos.add(8+random.nextInt(3+j) - random.nextInt(8),8+random.nextInt(3+j) - random.nextInt(8),8+random.nextInt(3+j) - random.nextInt(8));
				world.setBlockState(blockposnewRND,oretobegenerated.getDefaultState());
				int r = random.nextInt(5);
				switch (r) {
				case 0:
					if (world.getBlockState(blockposnewRND.add(1, 0, 0)).getBlock().getMaterial()!= oretobegenerated.getMaterial()) {
						
						world.setBlockState(blockposnewRND.add(1, 0, 0),oretobegenerated.getDefaultState());
					}
					break;
				case 1:
					if (world.getBlockState(blockposnewRND.add(0, 0, 1)).getBlock().getMaterial()!= oretobegenerated.getMaterial()) {
						world.setBlockState(blockposnewRND.add(0, 0, 1),Blocks.cobblestone.getDefaultState());
					}
					break;
				case 2:
				if(world.getBlockState(blockposnewRND.add(0, 1, 0)).getBlock().getMaterial()!= oretobegenerated.getMaterial()) {
					world.setBlockState(blockposnewRND.add(0, 1, 0),Blocks.cobblestone.getDefaultState());
				}
					break;
				case 3:
					if (world.getBlockState(blockposnewRND.add(-1, 0, 0)).getBlock().getMaterial()!= oretobegenerated.getMaterial()) {
						world.setBlockState(blockposnewRND.add(-1, 0, 0),Blocks.cobblestone.getDefaultState());
					}
					break;
				case 4:
					if (world.getBlockState(blockposnewRND.add(0, 0,-1)).getBlock().getMaterial()!= oretobegenerated.getMaterial()) {
						world.setBlockState(blockposnewRND.add(0, 0, -1),Blocks.cobblestone.getDefaultState());
					}
					break;
				case 5:
					if (world.getBlockState(blockposnewRND.add(0,-1, 0)).getBlock().getMaterial()!= oretobegenerated.getMaterial()) {
						world.setBlockState(blockposnewRND.add(0,-1,0),Blocks.cobblestone.getDefaultState());
					}
					break;

				default:
					break;
				}
				world.setBlockState(blockposnewRND.add(1, 0, 0),oretobegenerated.getDefaultState());
					
			}else {
				world.setBlockState(blockpos.add(random.nextInt(3+j),random.nextInt(3+j),random.nextInt(3+j)),Blocks.cobblestone.getDefaultState());			
			}
		}
		}
		
		for (int r = 0; r < 7; r++) {					
		for (int i = 0; i < 280+random.nextInt(1000);i++) {
			newblockpos = blockPosOrigin.add(random.nextInt(r*2+1),random.nextInt(r*2+1),random.nextInt(r*2+1));
			if (TestForAdjustendBlock(newblockpos, world, chunkX, chunkZ) && world.getBlockState(newblockpos).getBlock().getMaterial()!= oretobegenerated.getMaterial() ) {
			world.setBlockState(newblockpos,Blocks.cobblestone.getDefaultState());
				}
			}
		  }
		
		lastChunkX = chunkX;
		lastChunkZ = chunkZ;
		lastRandomY =randomY;
		
   	}
		BiomeGenBase b = world.getBiomeGenForCoords(blockpos);
		
		
	}
	
	
	
	public static boolean TestForAdjustendBlock (BlockPos blockpos,World world,int chunkX,int chunkZ) {
		
		
		
		
			for (int i = 0; i < 5; i++) {
				if(blockpos.add(adjustendBlocks[i][0],adjustendBlocks[i][1],adjustendBlocks[i][2]).getX()<chunkX*16+16 && 
					blockpos.add(adjustendBlocks[i][0],adjustendBlocks[i][1],adjustendBlocks[i][2]).getX()>= chunkX*16 &&
					blockpos.add(adjustendBlocks[i][0],adjustendBlocks[i][1],adjustendBlocks[i][2]).getZ()<chunkZ*16+16 && 
					blockpos.add(adjustendBlocks[i][0],adjustendBlocks[i][1],adjustendBlocks[i][2]).getZ()>= chunkZ*16) 
						{
					
					if (world.getBlockState(blockpos.add(adjustendBlocks[i][0],adjustendBlocks[i][1],adjustendBlocks[i][2]).add(-1, 0, 0)).getBlock().getMaterial()!= Material.air ) {
						return true;
							}					
						}
					}
			
			
			
			
					return false;
				
				} 	
	
	public static boolean TestForNearbyAsteroid (int chunkX,int y, int chunkZ, World world) {
		BlockPos blockPos = new BlockPos(chunkX*16, y, chunkZ*16);
		for (int l = 0; l < 4; l++) {
			
		
		for (int k = 0; k < 3; k++) {
			y = -2+k;
		
		for (int i = 0; i < 17+l; i++) {
			if (world.getBlockState(blockPos.add(i-l, y, -l)).getBlock().getMaterial()!= Material.air) {
				return true;
			}
		}
		
		for (int j = 0; j < 17+l; j++) {
				if (world.getBlockState(blockPos.add(-l, y,j-l)).getBlock().getMaterial()!= Material.air) {
					return true;
				}
			}
		for (int i = 0; i < 17+l; i++) {
			if (world.getBlockState(blockPos.add(i+l, y, 16+l)).getBlock().getMaterial()!= Material.air) {
				return true;
			}
		}
		
		for (int j = 0; j < 17+l; j++) {
				if (world.getBlockState(blockPos.add(16+l,y,j+l)).getBlock().getMaterial()!= Material.air) {
					return true;
				}
			}
		}
	 }
		return false;
	}
	
	
	
	public boolean testIfNearChunkWasGenerated(int chunkX,int chunkZ) {
		if (chunkX == lastChunkX || chunkX == lastChunkX+1 || chunkX == lastChunkX-1) {
			if (chunkZ == lastChunkZ || chunkZ == lastChunkZ+1 || chunkZ == lastChunkZ-1) {
				return true;
			}
			}
		return false;
		
		
		
		
	}
	
	 public static Block getRandomBlock (String blocktype,Random random) {
		 int randomId;
		 if (blocktype.equals("Ore")) {
		randomId = random.nextInt(9);	
		switch (randomId) {
		case 0:
			return Blocks.stone;
		case 1:
			return Blocks.iron_ore;
		case 2:
			return Blocks.coal_ore;
		case 3:
			return Blocks.gold_ore;
		case 4:
			return Blocks.diamond_ore;
		case 5:
			return Blocks.emerald_ore;
		case 6:
			return Blocks.lapis_ore;
		case 7:
			return Blocks.redstone_ore;
		case 8:
			return Blocks.quartz_ore;
		case 9:
			return Blocks.water;

		default:
			return Blocks.stone;
		}
		 }
		 else {
			if (blocktype.equals("Core")) {
				randomId = random.nextInt(10);
				switch (randomId){
			case 0:
				return Blocks.stone;
			case 1:
				return Blocks.iron_block;
			case 2:
				return Blocks.coal_block;
			case 3:
				return Blocks.gold_block;
			case 4:
				return Blocks.diamond_block;
			case 5:
				return Blocks.emerald_block;
			case 6:
				return Blocks.lapis_block;
			case 7:
				return Blocks.redstone_block;
			case 8:
				return Blocks.glowstone;
			case 9:
				return Blocks.water;
			case 10:
				return Blocks.lava;

			default:
				return Blocks.stone;
			}
			}
		}
		return Blocks.cobblestone;
		
	}
	
}