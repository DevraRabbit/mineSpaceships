package com.minespaceships.mod.Generator;


import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
	
	
public class WorldGeneratorAsteroid implements IWorldGenerator{
	

	
	    private Block blockToBeGenerated;
	    private static final String text= "CL_00000433";

	    public WorldGeneratorAsteroid(Block block){
			 blockToBeGenerated = block;
	    }      

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World worldIn,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		BlockPos pos = new BlockPos(chunkX*16, random.nextInt(150),chunkZ*16);
		
}
}
       
    
		

