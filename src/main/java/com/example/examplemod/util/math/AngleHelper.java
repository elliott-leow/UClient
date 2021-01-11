package com.example.examplemod.util.math;


import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class AngleHelper {
	  public static final long DEFAULT_N = 1000000000L; // 9 decimal places
	  public static final double DEFAULT_EPSILON = (1.D / (double) DEFAULT_N);

	  public static final double TWO_PI = Math.PI * 2.D; // 180 degrees
	  public static final double HALF_PI = Math.PI / 2.D; // 90 degrees
	  public static final double QUARTER_PI = Math.PI / 4.D; // 45 degrees

	  public static double roundAngle(double a, long n) {
	    return Math.round(a * n) / (double) n;
	  }

	  public static double roundAngle(double a) {
	    return roundAngle(a, DEFAULT_N);
	  }

	  public static boolean isAngleEqual(double a1, double a2, double epsilon) {
	    return Double.compare(a1, a2) == 0 || Math.abs(a1 - a2) < epsilon;
	  }

	  public static boolean isAngleEqual(double a1, double a2) {
	    return isAngleEqual(a1, a2, 1E-4);
	  }

	  public static boolean isEqual(Angle ang1, Angle ang2) {
	    Angle a1 = ang1.normalize();
	    Angle a2 = ang2.same(a1).normalize();
	    return isAngleEqual(a1.getPitch(), a2.getPitch())
	        && isAngleEqual(a1.getYaw(), a2.getYaw())
	        && isAngleEqual(a1.getRoll(), a2.getRoll());
	  }

	  public static double normalizeInRadians(double ang) {
	    while (ang > Math.PI) ang -= 2 * Math.PI;
	    while (ang < -Math.PI) ang += 2 * Math.PI;
	    return ang;
	  }

	  public static float normalizeInRadians(float ang) {
	    while (ang > Math.PI) ang -= 2 * Math.PI;
	    while (ang < -Math.PI) ang += 2 * Math.PI;
	    return ang;
	  }

	  public static double normalizeInDegrees(double ang) {
	    return MathHelper.wrapAngleTo180_double(ang);
	  }

	  public static float normalizeInDegrees(float ang) {
	    return MathHelper.wrapAngleTo180_float(ang);
	  }

	  public static Angle getAngleFacingInRadians(Vec3 vector) {
	    double pitch, yaw;
	    if (vector.xCoord == 0 && vector.zCoord == 0) {
	      yaw = 0.D;
	      pitch = HALF_PI;
	    } else {
	      yaw = Math.atan2(vector.zCoord, vector.xCoord) - HALF_PI;
	      double mag = Math.sqrt(vector.xCoord * vector.xCoord + vector.zCoord * vector.zCoord);
	      pitch = -Math.atan2(vector.yCoord, mag);
	    }
	    return Angle.radians((float) pitch, (float) yaw);
	  }

	  public static Angle getAngleFacingInDegrees(Vec3 vector) {
	    return getAngleFacingInRadians(vector).inDegrees();
	  }
	}