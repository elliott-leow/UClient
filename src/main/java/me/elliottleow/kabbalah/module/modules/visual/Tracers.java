package me.elliottleow.kabbalah.module.modules.visual;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.google.gson.JsonSyntaxException;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.api.util.RenderUtils;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Tracers extends Module {
	public Tracers() {
		super("Tracers", "Draws a line to entities", Category.Visual);
		this.setKey(Keyboard.KEY_O);
		Kabbalah.settingsManager.rSetting(new Setting("Mobs", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("Players", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Items", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("Only Invis", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("Red", this, 100, 0, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Green", this, 0, 0, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Blue", this, 0, 0, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Line Width", this, 5, 1, 40, true));
		Kabbalah.settingsManager.rSetting(new Setting("Transparency", this, 50, 0, 100, true));
		//Kabbalah.settingsManager.rSetting(new Setting("NPC Check", this, true));
	}
	
	protected static final Minecraft mc = Minecraft.getMinecraft();
	
	/*public String getJsonUUID(String uuid) throws IOException {
    	URL url1 = new URL("https://api.hypixel.net/player?key=5668d932-38bf-4611-a98c-d3690338bcc6&uuid="+uuid);
		Scanner sc = new Scanner(url1.openStream());
        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()) {
           sb.append(sc.next());
        }
        String result = sb.toString();
        result = result.replaceAll("<[^>]*>", "");
        sc.close();
        return result;
    }*/
	
	@SubscribeEvent
	public void onRender(final RenderWorldLastEvent event) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, JsonSyntaxException, IOException {
		for(Object entity: Minecraft.getMinecraft().theWorld.loadedEntityList) {
			 Entity e = (Entity)entity;
			 if ((e instanceof EntityOtherPlayerMP || e instanceof EntityPlayerMP || e instanceof EntityPlayerSP) && e.getUniqueID()!=mc.thePlayer.getUniqueID() && Kabbalah.settingsManager.getSettingByName(this, "Players").getValBoolean()) {
				 if (e instanceof EntityPlayer) {
					 if ((Kabbalah.settingsManager.getSettingByName(this, "Only Invis").getValBoolean() && e.isInvisible()) || !Kabbalah.settingsManager.getSettingByName(this, "Only Invis").getValBoolean()) {
					//EntityPlayer player = (EntityPlayer)e;
					//JsonObject json;
					//json = new JsonParser().parse(getJsonUUID(player.getUniqueID().toString())).getAsJsonObject();
					//if (!Kabbalah.settingsManager.getSettingByName(this, "NPC Check").getValBoolean() || (Kabbalah.settingsManager.getSettingByName(this, "NPC Check").getValBoolean() && json.get("player").isJsonNull())) {
						RenderUtils.drawTracerLine((Entity) e,
						(Kabbalah.settingsManager.getSettingByName(this, "Red").getValDouble()/100),
						(Kabbalah.settingsManager.getSettingByName(this, "Green").getValDouble()/100),
						(Kabbalah.settingsManager.getSettingByName(this, "Blue").getValDouble()/100),
						(Kabbalah.settingsManager.getSettingByName(this, "Transparency").getValDouble()/100),
						(Kabbalah.settingsManager.getSettingByName(this, "Line Width").getValDouble()/10));
						GL11.glColor4f(1.0F, 1.0F, 1.0F, (float)(Kabbalah.settingsManager.getSettingByName(this, "Transparency").getValDouble())/100f);

					//}
					 }
				 }
			 }
			  
			 if ((e instanceof EntityMob || e instanceof EntityAnimal || e instanceof EntityTameable)&& Kabbalah.settingsManager.getSettingByName(this, "Mobs").getValBoolean()) {
				 if ((Kabbalah.settingsManager.getSettingByName(this, "Only Invis").getValBoolean() && e.isInvisible()) || !Kabbalah.settingsManager.getSettingByName(this, "Only Invis").getValBoolean()) {
					 RenderUtils.drawTracerLine((Entity) e,
					 (Kabbalah.settingsManager.getSettingByName(this, "Red").getValDouble()/100),
					 (Kabbalah.settingsManager.getSettingByName(this, "Green").getValDouble()/100),
					 (Kabbalah.settingsManager.getSettingByName(this, "Blue").getValDouble()/100),
					 (Kabbalah.settingsManager.getSettingByName(this, "Transparency").getValDouble()/100),
					 (Kabbalah.settingsManager.getSettingByName(this, "Line Width").getValDouble()/10));
				 }
			 }
			 
			 if (e instanceof EntityItem && Kabbalah.settingsManager.getSettingByName(this, "Items").getValBoolean()) {
				 RenderUtils.drawTracerLine((Entity) e, 
				 (Kabbalah.settingsManager.getSettingByName(this, "Red").getValDouble()/100), 
				 (Kabbalah.settingsManager.getSettingByName(this, "Green").getValDouble()/100), 
				 (Kabbalah.settingsManager.getSettingByName(this, "Blue").getValDouble()/100),
				 (Kabbalah.settingsManager.getSettingByName(this, "Transparency").getValDouble()/100),
				 (Kabbalah.settingsManager.getSettingByName(this, "Line Width").getValDouble()/10));
			 }
		 }
	 }
}
