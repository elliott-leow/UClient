package me.elliottleow.kabbalah.module.modules.defense;

import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.api.util.HypixelUtil;
import me.elliottleow.kabbalah.api.util.RenderUtils;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockBreakESP extends Module {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public BlockBreakESP() {
		super("Block Break ESP", "Outlines broken blocks", Category.DEFENSE);
		this.setKey(Keyboard.KEY_B);
		Kabbalah.settingsManager.rSetting(new Setting("Line Width", this, 5, 1, 40, true));
	}
	
	HashMap<BlockPos,Integer> positions = new HashMap<BlockPos,Integer>();
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent e) {
		positions.put(e.pos, HypixelUtil.isTunnel(e.pos));
		if (positions.containsKey(new BlockPos(e.pos.getX(), e.pos.getY()-1, e.pos.getZ())) && positions.get(e.pos) == 1) {
			positions.replace(new BlockPos(e.pos.getX(), e.pos.getY()-1, e.pos.getZ()), 1);
		}

		if (mc.theWorld.isAirBlock(new BlockPos(e.pos.getX(), e.pos.getY()+1, e.pos.getZ()))
				&& positions.containsKey(new BlockPos(e.pos.getX(), e.pos.getY()+1, e.pos.getZ()))
				&& HypixelUtil.isTunnel(new BlockPos(e.pos.getX(), e.pos.getY()+1, e.pos.getZ())) == 1
				&& !Minecraft.getMinecraft().theWorld.isAirBlock(new BlockPos(e.pos.getX(), e.pos.getY()+2, e.pos.getZ()))) {
			positions.replace(new BlockPos(e.pos.getX(), e.pos.getY()+1, e.pos.getZ()),1);
			positions.replace(e.pos, 1);
		}
	}

	@SubscribeEvent
	public void onBlockPlace(BlockEvent.PlaceEvent e) {
		BlockPos block = e.pos;
//		if (e.)
//		if (e.face.equals(EnumFacing.NORTH)) {
//			block = new BlockPos(e.pos.getX(), e.pos.getY(), e.pos.getZ()-1);
//		}
//		else if (e.face.equals(EnumFacing.SOUTH)) {
//			block = new BlockPos(e.pos.getX(), e.pos.getY(), e.pos.getZ()+1);
//		}
//		else if (e.face.equals(EnumFacing.WEST)) {
//			block = new BlockPos(e.pos.getX()-1, e.pos.getY(), e.pos.getZ());
//		}
//		else if (e.face.equals(EnumFacing.EAST)) {
//			block = new BlockPos(e.pos.getX()+1, e.pos.getY(), e.pos.getZ());
//		}
//		else if (e.face.equals(EnumFacing.UP)) {
//			block = new BlockPos(e.pos.getX(), e.pos.getY()+1, e.pos.getZ());
//		}
//		else if (e.face.equals(EnumFacing.DOWN)) {
//			block = new BlockPos(e.pos.getX(), e.pos.getY()-1, e.pos.getZ());
//		}
		if (positions.containsKey(block)) {
			positions.remove(block);
		}
	}
	
	@SubscribeEvent
	public void onRender(final RenderWorldLastEvent event) {
		for (Entry<BlockPos, Integer> entry : positions.entrySet()) {
			//int tunnelSeverity = HypixelUtil.isTunnel(entry.getKey());
			RenderUtils.blockESPBox(entry.getKey(), entry.getValue(), Kabbalah.settingsManager.getSettingByName(this, "Line Width").getValDouble()/10);
		}
	}
}
