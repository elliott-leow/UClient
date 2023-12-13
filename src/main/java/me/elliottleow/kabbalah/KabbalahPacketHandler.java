package me.elliottleow.kabbalah;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MessageDeserializer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@ChannelHandler.Sharable
public class KabbalahPacketHandler extends SimpleChannelInboundHandler<Packet> {

	public KabbalahPacketHandler() {
		super(false);
	}
	
	int entityId;
	List<S38PacketPlayerListItem.AddPlayerData> l;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
		try {
			/*if (msg instanceof S14PacketEntity) {
				//PacketBuffer packetbuffer = new PacketBuffer(ctx.alloc().buffer());
				//System.out.println("S14PacketEntity STARTS HERE");
				S14PacketEntity packet = (S14PacketEntity) msg;
				Object o = ReflectionHelper.getPrivateValue(S14PacketEntity.class, packet, 0);
				entityId = (int)o;
				//System.out.println("ENTITY DETECTED: " + entityId);
				//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Player Detected: \2473" + Minecraft.getMinecraft().theWorld.getEntityByID(entityId)));
			}*/
			if (msg instanceof S38PacketPlayerListItem) {
				S38PacketPlayerListItem packet = (S38PacketPlayerListItem) msg;
				System.out.println(msg);
				//Object o1 = ReflectionHelper.getPrivateValue(S38PacketPlayerListItem.class, packet, 0);
				//if ((S38PacketPlayerListItem.Action)o1 == S38PacketPlayerListItem.Action.REMOVE_PLAYER) {
				l = (List<S38PacketPlayerListItem.AddPlayerData>)ReflectionHelper.getPrivateValue(S38PacketPlayerListItem.class, packet, 1);
				if (l.toString().contains("name=<null>")){
					String UUID = l.toString();
					UUID = UUID.split("id=")[1];
					UUID = UUID.split(",")[0];
					URL url = new URL("https://api.mojang.com/user/profile/" + UUID);
					Scanner sc = new Scanner(url.openStream());
			        StringBuffer sb = new StringBuffer();
			        while(sc.hasNext()) {
			           sb.append(sc.next());
			        }
			        String result = sb.toString();
			        if (result.contains("name")) {
				        result = result.split("name")[1];
				        result = result.split("}")[0];
				        System.out.println("result: " + result);
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Player Detected: \2473" + result));
			        }
				}
			}
		}
		catch (Exception e) {
			System.err.println("[Kabbalah Client]: PACKET HANDLER FAILED FOR " + msg);
			System.err.println(e);
		}
		//System.out.println(msg);
		ctx.fireChannelRead(msg);
	}
	
	@SubscribeEvent
	public void connect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		ChannelPipeline pipeline = event.manager.channel().pipeline();
		pipeline.addBefore("packet_handler", this.getClass().getName(), this);
	}
	
	@SubscribeEvent
	public void onClientChatReceived(ClientChatReceivedEvent event) {
		String msg = event.message.getUnformattedText();
		if (msg.contains("has joined (2/2)!")) {
			//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Player Detected: \2473" + Minecraft.getMinecraft().theWorld.getEntityByID(entityId)));
		}
	}

}
