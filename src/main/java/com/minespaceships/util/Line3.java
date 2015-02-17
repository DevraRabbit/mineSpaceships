package com.minespaceships.util;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

/**
 * Created by kevin on 17.02.15.
 * This class models a simple straight line in a 3 dimensional room with (x,y,z) values
 */
public class Line3 {
    private Vec3 originVec; // The origin vector of the line
    private Vec3 directionVec; // The direction vector of the line

    /**
     * Constructor of Line3 objects
     * @param pOriginVec The origin (null) vector of a line (fixed point in the cord system)
     * @param pDirectionVec The direction vector of a line (the direction by which the line travels in the cord system)
     */
    public Line3(final Vec3 pOriginVec, final Vec3 pDirectionVec) {
        if(!(pOriginVec instanceof Vec3)) throw new IllegalArgumentException("The origin vector was not Vec3");
        if(!(pDirectionVec instanceof Vec3)) throw new IllegalArgumentException("The direction vector was not Vec3");
        this.originVec = pOriginVec;
        this.directionVec = pDirectionVec;
    }

    /**
     * Checks if a vector is in this line
     * @param pVec The vector which should be checked
     * @return True if the vector is in this line, false otherwise
     */
    public boolean hitsVec3(final Vec3 pVec) {
        if(!(pVec instanceof Vec3)) throw new IllegalArgumentException("The parameter vector was not in Vec");
        final double lambdaX = (pVec.xCoord - originVec.xCoord) / directionVec.xCoord;
        final double lambdaY = (pVec.yCoord - originVec.yCoord) / directionVec.yCoord;
        final double lambdaZ = (pVec.zCoord - originVec.zCoord) / directionVec.zCoord;
        return ((lambdaX == lambdaY) && (lambdaY == lambdaZ));
    }

    /**
     * Checks if a vector is in this line
     * @param pVec The vector which should be checked
     * @return True if the vector is in this line, false otherwise
     */
    public boolean hitsVec3i(final Vec3i pVec) {
        if(!(pVec instanceof Vec3i)) throw new IllegalArgumentException("The parameter vector was not in Vec3i");
        final double lambdaX = (pVec.getX() - originVec.xCoord) / directionVec.xCoord;
        final double lambdaY = (pVec.getY() - originVec.yCoord) / directionVec.yCoord;
        final double lambdaZ = (pVec.getZ() - originVec.zCoord) / directionVec.zCoord;
        return ((lambdaX == lambdaY) && (lambdaY == lambdaZ));
    }

    //TODO Implement function to see if a line hits a plane

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
    public Vec3 getOriginVector() {
        return originVec;
    }

    public void setOriginVector(final Vec3 pOriginVec) {
        this.originVec = pOriginVec;
    }

    public void setDirectionVector(final Vec3 pDirectionVec) {
        this.directionVec = pDirectionVec;
    }

    public Vec3 getDirectionVector() {
        return directionVec;
    }

    /**
     * Returns the x,y,z cords of the origin and direction vector in a String
     * @return
     */
    @Override
    public String toString() {
        return "Origin vector: x: " + originVec.xCoord + ", y:  " + originVec.yCoord + ", z: " + originVec.zCoord
                + "; Direction vector: x: " + directionVec.xCoord + ", y:  " + directionVec.yCoord + ", z: " + directionVec.zCoord;
    }
}