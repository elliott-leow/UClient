package me.elliottleow.kabbalah.module.modules.qol;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;

public class AntiGhost extends Module {
	public AntiGhost() {
		super("Anti Ghost", "Discovers ghost blocks", Category.QoL);
		Kabbalah.settingsManager.rSetting(new Setting("Output", this, false));
		this.setKey(Keyboard.KEY_G);
		
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		Minecraft mc=Minecraft.getMinecraft();
        NetHandlerPlayClient conn = mc.getNetHandler();
        if (conn==null)
            return;
        BlockPos pos=mc.thePlayer.getPosition();
        for (int dx=-4; dx<=4; dx++)
            for (int dy=-4; dy<=4; dy++)
                for (int dz=-4; dz<=4; dz++) {
                    C07PacketPlayerDigging packet=new C07PacketPlayerDigging(
                            C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, 
                            new BlockPos(pos.getX()+dx, pos.getY()+dy, pos.getZ()+dz),
                            EnumFacing.UP       // with ABORT_DESTROY_BLOCK, this value is unused
                    );
                    conn.addToSendQueue(packet);
                }
        if (Kabbalah.settingsManager.getSettingByName(this, "Output").getValBoolean()) {
        	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2474requesting resent of blocks in 4x4 radius."));
        }
        this.setToggled(false);
	}
}
