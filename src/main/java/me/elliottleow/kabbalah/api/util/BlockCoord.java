package me.elliottleow.kabbalah.api.util;

import com.google.common.base.Strings;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class BlockCoord
{
    public final int x;
    public final int y;
    public final int z;
    
    public BlockCoord() {
        this(0, 0, 0);
    }
    
    public BlockCoord(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public BlockCoord(final double x, final double y, final double z) {
        this(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
    }
    
    /*public BlockCoord(final TileEntity tile) {
        this(tile.xCoord, tile.yCoord, tile.zCoord);
    }*/
    
    public BlockCoord(final Entity e) {
        this(e.posX, e.posY, e.posZ);
    }
    
    public BlockCoord(final BlockCoord bc) {
        this(bc.x, bc.y, bc.z);
    }
    
    public BlockCoord getLocation(final ForgeDirection dir) {
        return new BlockCoord(this.x + dir.offsetX, this.y + dir.offsetY, this.z + dir.offsetZ);
    }
    
    public BlockCoord(final String x, final String y, final String z) {
        this(Strings.isNullOrEmpty(x) ? 0 : Integer.parseInt(x), Strings.isNullOrEmpty(y) ? 0 : Integer.parseInt(y), Strings.isNullOrEmpty(z) ? 0 : Integer.parseInt(z));
    }
    
    public BlockCoord(final MovingObjectPosition mop) {
        this(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
    }
    
    /*public Block getBlock(final IBlockAccess world) {
        return world.getBlock(this.x, this.y, this.z);
    }
    
    public int getMetadata(final IBlockAccess world) {
        return world.getBlockMetadata(this.x, this.y, this.z);
    }
    
    public TileEntity getTileEntity(final IBlockAccess world) {
        return world.getTileEntity(this.x, this.y, this.z);
    }*/
    
    public int getDistSq(final BlockCoord other) {
        final int xDiff = this.x - other.x;
        final int yDiff = this.y - other.y;
        final int zDiff = this.z - other.z;
        return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
    }
    
    /*public int getDistSq(final TileEntity other) {
        return this.getDistSq(new BlockCoord(other));
    }*/
    
    public int getDist(final BlockCoord other) {
        final double dsq = this.getDistSq(other);
        return (int)Math.ceil(Math.sqrt(dsq));
    }
    
    /*public int getDist(final TileEntity other) {
        return this.getDist(new BlockCoord(other));
    }*/
    
    public void writeToBuf(final ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }
    
    public static BlockCoord readFromBuf(final ByteBuf buf) {
        return new BlockCoord(buf.readInt(), buf.readInt(), buf.readInt());
    }
    
    public void writeToNBT(final NBTTagCompound tag) {
        tag.setInteger("bc:x", this.x);
        tag.setInteger("bc:y", this.y);
        tag.setInteger("bc:z", this.z);
    }
    
    public static BlockCoord readFromNBT(final NBTTagCompound tag) {
        return new BlockCoord(tag.getInteger("bc:x"), tag.getInteger("bc:y"), tag.getInteger("bc:z"));
    }
    
    public String chatString() {
        return this.chatString(EnumChatFormatting.WHITE);
    }
    
    public String chatString(final EnumChatFormatting defaultColor) {
        return String.format("x%s%d%s y%s%d%s z%s%d", EnumChatFormatting.GREEN, this.x, defaultColor, EnumChatFormatting.GREEN, this.y, defaultColor, EnumChatFormatting.GREEN, this.z);
    }
    
    public boolean equals(final int x, final int y, final int z) {
        return this.equals(new BlockCoord(x, y, z));
    }
    
    @Override
    public String toString() {
        return "X: " + this.x + "  Y: " + this.y + "  Z: " + this.z;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BlockCoord)) {
            return false;
        }
        final BlockCoord other = (BlockCoord)o;
        return other.canEqual(this) && this.x == other.x && this.y == other.y && this.z == other.z;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof BlockCoord;
    }
    
    @Override
    public int hashCode() {
        //final int PRIME = 59;
        int result = 1;
        result = result * 59 + this.x;
        result = result * 59 + this.y;
        result = result * 59 + this.z;
        return result;
    }
    
    public BlockCoord withX(final int x) {
        return (this.x == x) ? this : new BlockCoord(x, this.y, this.z);
    }
    
    public BlockCoord withY(final int y) {
        return (this.y == y) ? this : new BlockCoord(this.x, y, this.z);
    }
    
    public BlockCoord withZ(final int z) {
        return (this.z == z) ? this : new BlockCoord(this.x, this.y, z);
    }
}
