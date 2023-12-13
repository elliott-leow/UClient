package me.elliottleow.kabbalah.module.modules.qol;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

import me.elliottleow.kabbalah.api.util.BlockCoord;
import me.elliottleow.kabbalah.api.util.ForgeDirection;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class FastPlace extends Module {

	public FastPlace() {
		super("Fast Place", "Place blocks without tick limit", Category.QoL);
	}
	
	private static BlockCoord lastTargetPos;
    private static ForgeDirection lastTargetSide;
	private static final Field _rightClickDelayTimer;
    private static final MethodHandle getDelayTimer;
    private static final MethodHandle setDelayTimer;
	
	static {
        _rightClickDelayTimer = ReflectionHelper.findField((Class<?>)Minecraft.class, new String[] { "rightClickDelayTimer", "rightClickDelayTimer" });
        try {
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            getDelayTimer = lookup.unreflectGetter(FastPlace._rightClickDelayTimer);
            setDelayTimer = lookup.unreflectSetter(FastPlace._rightClickDelayTimer);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
	
	@SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) throws Throwable {
        if (event.phase == TickEvent.Phase.START) {
            final int timer = (int) FastPlace.getDelayTimer.invoke(Minecraft.getMinecraft());
            final MovingObjectPosition hover = Minecraft.getMinecraft().objectMouseOver;
            if (hover != null && hover.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final BlockCoord pos = new BlockCoord(hover);
                if (timer > 0 && !pos.equals(FastPlace.lastTargetPos) && (FastPlace.lastTargetPos == null || !pos.equals(FastPlace.lastTargetPos.getLocation(FastPlace.lastTargetSide)))) FastPlace.setDelayTimer.invoke(Minecraft.getMinecraft(), 0);
                FastPlace.lastTargetPos = pos;
                FastPlace.lastTargetSide = ForgeDirection.getOrientation(hover.sideHit.getIndex());
            }
        }
    }

}
