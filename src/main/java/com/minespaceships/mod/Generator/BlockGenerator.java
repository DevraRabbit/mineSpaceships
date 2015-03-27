package com.minespaceships.mod.Generator;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.WorldAccessContainer;

public class BlockGenerator implements IWorldGenerator{

	private Block blocktoGenerate;
	
	
	public BlockGenerator(Block blocktoGenerate) {
		this.blocktoGenerate = blocktoGenerate;
	}
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		 
			chunkX *= 16;
			chunkZ *= 16;
			
			
			
			
			
			
			
			for (int i = 0; i < 300; i++) {
				
				
				int posX = chunkX +random.nextInt(16);
				int posY =200- random.nextInt(160);
				int posZ = chunkZ + random.nextInt(16);
				
				
				WorldGenMinable wgm = new WorldGenMinable (blocktoGenerate.getDefaultState(),9);				
				wgm.generate(world, random, new BlockPos(posX, posY, posZ));
				
			}
		}
		
	}


