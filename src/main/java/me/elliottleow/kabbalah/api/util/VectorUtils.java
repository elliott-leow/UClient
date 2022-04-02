package me.elliottleow.kabbalah.api.util;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class VectorUtils {
	
	 private static void VecTransformCoordinate(Vector4f vec, Matrix4f matrix) {
		    float x = vec.x;
		    float y = vec.y;
		    float z = vec.z;
		    vec.x = (x * matrix.m00) + (y * matrix.m10) + (z * matrix.m20) + matrix.m30;
		    vec.y = (x * matrix.m01) + (y * matrix.m11) + (z * matrix.m21) + matrix.m31;
		    vec.z = (x * matrix.m02) + (y * matrix.m12) + (z * matrix.m22) + matrix.m32;
		    vec.w = (x * matrix.m03) + (y * matrix.m13) + (z * matrix.m23) + matrix.m33;
		  }
	
	
	
	 public static ScreenPos toScreen(double x, double y, double z, float partialTicks) {
		    Entity view = Minecraft.getMinecraft().getRenderViewEntity();

		    if (view == null) return new ScreenPos(0.D, 0.D, false);

		    Vec3 camPos = ActiveRenderInfo.getPosition();
		    		
		    Vec3 eyePos = ActiveRenderInfo.projectViewFromEntity(view, partialTicks);

		    float vecX = (float) ((camPos.xCoord + eyePos.xCoord) - (float) x);
		    float vecY = (float) ((camPos.yCoord + eyePos.yCoord) - (float) y);
		    float vecZ = (float) ((camPos.zCoord + eyePos.zCoord) - (float) z);

		    Vector4f pos = new Vector4f(vecX, vecY, vecZ, 1.f);

		    final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
		    final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
		    GlStateManager.getFloat(GL11.GL_PROJECTION_MATRIX, projection);
		    GlStateManager.getFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
		    Matrix4f projectionMatrix = (Matrix4f) new Matrix4f().load(projection.asReadOnlyBuffer());
		    Matrix4f modelViewMatrix = (Matrix4f) new Matrix4f().load(modelview.asReadOnlyBuffer());
		    

		    VecTransformCoordinate(pos, modelViewMatrix);
		    VecTransformCoordinate(pos, projectionMatrix);

		    if (pos.w > 0.f) {
		      pos.x *= -1;
		      pos.y *= -1;
		    } else {
		      float invert = 1.f / pos.w;
		      pos.x *= invert;
		      pos.y *= invert;
		    }
		    
		    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

		    float halfWidth = (float) sr.getScaledWidth() / 2.f;
		    float halfHeight = (float) sr.getScaledHeight() / 2.f;

		    pos.x = halfWidth + (0.1f * pos.x * sr.getScaledWidth() + 0.5f);
		    pos.y = halfHeight - (0.1f * pos.y * sr.getScaledHeight() + 0.5f);

		    boolean bVisible = true;
		    
		    if (pos.x < 0 || pos.y < 0 || pos.x > sr.getScaledWidth() || pos.y > sr.getScaledHeight())
		      bVisible = false;

		    return new ScreenPos(pos.x, pos.y, bVisible);
		  }
	
	
	  public static class ScreenPos {
	    public final int x;
	    public final int y;
	    public final boolean isVisible;

	    public final double xD;
	    public final double yD;

	    public ScreenPos(double x, double y, boolean isVisible) {
	      this.x = (int) x;
	      this.y = (int) y;
	      this.xD = x;
	      this.yD = y;
	      this.isVisible = isVisible;
	    }
	  }
	
	public static ScreenPos toScreen(Vec3 vec3d, float partialTicks) {
        return VectorUtils.toScreen((double)vec3d.xCoord, (double)vec3d.yCoord, (double)vec3d.zCoord,partialTicks);
    }
}
