package me.elliottleow.kabbalah.module.modules.hud;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;

public class Cape extends Module {
	public Cape() {
		super("Cape", "Custom cape for Kabbalah masters", Category.HUD);
		this.setKey(Keyboard.KEY_M);
	}
	
	//private final RenderPlayer playerRenderer = EntityWorldJoinListener.renderp;

	public static String capeurl = "https://i.postimg.cc/g2NwsW-B2/kabbalahcape.png";
	
	//public static String theurl = "https://i.pinimg.com/originals/75/1b/c6/751bc6c85d4dfd1763c6233e453647f4.png";
    //String theurl = "https://i.pinimg.com/originals/75/1b/c6/751bc6c85d4dfd1763c6233e453647f4.png";
    //String theurl = "https://i.ibb.co/dMyc5pT/cape-img.png";
	    
    public static BufferedImage getImageFromURL(String url) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(url));
        } catch (IOException e) {
        	System.err.println("[Kabbalah Client]: Error getting cape");
        }
        return image;
    }

}