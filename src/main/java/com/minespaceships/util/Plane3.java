package com.minespaceships.util;

import net.minecraft.util.Vec3;

/**
 * Created by kevin on 17.02.15.
 */
public class Plane3 {
    private Vec3 originVec;//The origin of a plane
    private Vec3 directionVec1;//The first direction vector of a plane
    private Vec3 directioNVec2;//The second direction vector of a plane

    /**
     * Constructor of Plane3 objects
     * @param pOriginVec The origin (null) vector of a shape (fixed point in the cord system)
     * @param pDirectionVec1 The direction vector of a shape (the direction by which one edge of the plane travels in the cord system)
     * @param pDirectionVec2 The direction vector of a shape (the direction by which one edge of the plane travels in the cord system)
     */
    public Plane3(final Vec3 pOriginVec, final Vec3 pDirectionVec1, final Vec3 pDirectionVec2) {
        if(!(pOriginVec instanceof Vec3)) throw new IllegalArgumentException("The origin vector was not Vec3i");
        if(!(pDirectionVec1 instanceof Vec3)) throw new IllegalArgumentException("The direction vector 1 was not Vec3");
        if(!(pDirectionVec1 instanceof Vec3)) throw new IllegalArgumentException("The direction vector 1 was not Vec3");

        originVec = pOriginVec;
        directionVec1 = pDirectionVec1;
        directioNVec2 = pDirectionVec2;
    }

    //TODO implement function to get the CoordinateForm of a Shape

    /**
     * A public static class to implement a ShapeCoordinateForm:
     * x1Val * x1 + x2Val * x2 + x3Val * x3 = resultVal
     */
    public static class ShapeCoordinateForm {
        double x1Val;
        double x2Val;
        double x3Val;
        double resultVal;

        public ShapeCoordinateForm(final double pX1, final double pX2, final double pX3, final double pResult) {
            this.x2Val =  pX1;
            this.x2Val =  pX2;
            this.x3Val =  pX3;
            this.resultVal = pResult;
        }
    }
}
