package com.minespaceships.util;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

/**
 * Created by kevin on 17.02.15.
 * This class models a simple straight line in a 3 dimensional room with (x,y,z) values
 */
public class Line {
    private Vec3i originVec; // The origin vector of the line
    private Vec3i directionVec; // The direction vector of the line

    /**
     * Checks if a vector is in this line
     * @param pVec The vector which should be checked
     * @return True if the vector is in this line, false otherwise
     */
    public boolean hitsVec3(final Vec3 pVec) {
        if(!(pVec instanceof Vec3)) throw new IllegalArgumentException("The parameter vector was not in Vec");
        final double lambdaX = (pVec.xCoord - originVec.getX()) / directionVec.getX();
        final double lamdadY = (pVec.yCoord - originVec.getY()) / directionVec.getY();
        final double lambdaZ = (pVec.zCoord - originVec.getZ()) / directionVec.getZ();
        return ((lambdaX == lamdadY) && (lamdadY == lambdaZ));
    }

    /**
     * Checks if a vector is in this line
     * @param pVec The vector which should be checked
     * @return True if the vector is in this line, false otherwise
     */
    public boolean hitsVec3i(final Vec3i pVec) {
        if(!(pVec instanceof Vec3i)) throw new IllegalArgumentException("The parameter vector was not in Vec3i");
        final double lambdaX = (pVec.getX() - originVec.getX()) / directionVec.getX();
        final double lambdaY = (pVec.getY() - originVec.getY()) / directionVec.getY();
        final double lambdaZ = (pVec.getZ() - originVec.getZ()) / directionVec.getZ();
        return ((lambdaX == lambdaY) && (lambdaY == lambdaZ));
    }


    /**
     * Checks if a block position is in this line
     * @param pBlockPos The position of the block to be checked
     * @return True if the block position is in this line, false otherwise
     */
    public boolean hitsBlockPos(final BlockPos pBlockPos) {
        if(!(pBlockPos instanceof BlockPos)) throw new IllegalArgumentException("The parameter block was not a BlockPos");
        return hitsVec3i(BlockPos.NULL_VECTOR);
    }

    //Getters and Setters
    public Vec3i getOriginVector() {
        return originVec;
    }

    public void setOriginVector(final Vec3i pOriginVec) {
        this.originVec = pOriginVec;
    }

    public void setDirectionVector(final Vec3i pDirectionVec) {
        this.directionVec = pDirectionVec;
    }

    public Vec3i getDirectionVector() {
        return directionVec;
    }

    /**
     * Returns the x,y,z cords of the origin and direction vector in a String
     * @return
     */
    @Override
    public String toString() {
        return "Origin vector: x: " + originVec.getX() + ", y:  "  + originVec.getY() + ", z: " + originVec.getZ()
                + "; Direction vector: x: " + directionVec.getX() + ", y:  "  + directionVec.getY() + ", z: " + directionVec.getZ();
    }
}