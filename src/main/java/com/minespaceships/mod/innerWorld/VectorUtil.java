package com.minespaceships.mod.innerWorld;

import net.minecraft.util.BlockPos;
import java.util.Vector;

/**
 * 
 * @author ovae.
 * @version 20150302
 */
public class VectorUtil {

	public static int calculateScalar(BlockPos blockOne, BlockPos blockTwo) throws NoScalarException{
		int alpha = 0;
		int xScal,yScal,zScal;
		xScal = blockTwo.getX() / blockOne.getX();
		yScal = blockTwo.getY() / blockOne.getY();
		zScal = blockTwo.getZ() / blockOne.getZ();
		if(xScal == yScal &&  yScal == zScal){
			return alpha;
		}else{
			throw new NoScalarException();
		}
	}

	/**
	 * Calculates the vector product.
	 * @param blockOne
	 * @param blockTwo
	 */
	public static BlockPos vectorproduct(BlockPos blockOne, BlockPos blockTwo){
		double x,y,z;
		x = (blockOne.getY()*blockTwo.getZ())-(blockOne.getZ()*blockTwo.getY());
		y = (blockOne.getZ()*blockTwo.getX())-(blockOne.getX()*blockTwo.getZ());
		z = (blockOne.getX()*blockTwo.getY())-(blockOne.getY()*blockTwo.getX());
		return new BlockPos(x,y,z);
	}

	/**
	 * 
	 * @param blockOne
	 * @param blockTwo
	 * @param blockThree
	 */
	public static int spatProduct(BlockPos blockOne, BlockPos blockTwo, BlockPos blockThree){
		int product;
		product = blockOne.getY()*blockTwo.getZ()*blockThree.getX()+
				  blockOne.getZ()*blockTwo.getX()*blockThree.getY()+
				  blockOne.getX()*blockTwo.getY()*blockThree.getZ()-
				  blockOne.getZ()*blockTwo.getY()*blockThree.getX()-
				  blockOne.getX()*blockTwo.getZ()*blockThree.getY()-
				  blockOne.getY()*blockTwo.getX()*blockThree.getZ();
		return product;
	}

	/**
	 * Calculates the scalar product.
	 * @param blockOne
	 * @param blockTwo
	 */
	public static int scalarProduct(BlockPos blockOne, BlockPos blockTwo){
		int product = blockOne.getX()*blockTwo.getX()+blockOne.getY()*blockTwo.getY()+blockOne.getZ()*blockTwo.getZ();
		return product;
	}
}
