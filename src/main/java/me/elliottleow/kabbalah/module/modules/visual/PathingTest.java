//package me.elliottleow.kabbalah.module.modules.visual;
//
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.PriorityQueue;
//
//import org.lwjgl.input.Keyboard;
//import org.lwjgl.opengl.GL11;
//
//import me.elliottleow.kabbalah.Kabbalah;
//import me.elliottleow.kabbalah.api.util.BlockUtils;
//import me.elliottleow.kabbalah.api.util.RenderUtils;
//import me.elliottleow.kabbalah.api.util.datastructures.Edge;
//import me.elliottleow.kabbalah.api.util.datastructures.Vertex;
//import me.elliottleow.kabbalah.module.Category;
//import me.elliottleow.kabbalah.module.Module;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.RenderGlobal;
//import net.minecraft.init.Blocks;
//import net.minecraft.util.AxisAlignedBB;
//import net.minecraft.util.BlockPos;
//import net.minecraftforge.client.event.RenderWorldLastEvent;
//import net.minecraftforge.event.world.BlockEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//public class PathingTest extends Module {
//	
//	boolean result = false;
//	
//	public PathingTest() {
//		super("Pathing Test", "Finds best path to goal", Category.Visual);
//		this.setKey(Keyboard.KEY_K);
//	}
//	
//	@Override
//	public void onEnable() {
//		
//		if (Minecraft.getMinecraft().thePlayer == null) {
//			return;
//		}
//		if (!GoalPath.doneLoading) {
//			this.setToggled(false);
//			return;
//		}
//		BlockPos currentStanding = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
//		while (BlockUtils.getBlock(currentStanding) == Blocks.air) {
//			currentStanding = currentStanding.down();
//		}
//		GoalPath.worldGraph.clearLabels();
//		if (runDijkstra(GoalPath.blocks.get(currentStanding), GoalPath.blocks.get(new BlockPos(GoalPath.targetPos[0], GoalPath.targetPos[1], GoalPath.targetPos[2])))) {
//			result = true;
//			
//		}
//		
//		super.onEnable();
//		
//		
//		
//	}
//	
//	
//	public void pathFind() {
//		BlockPos currentStanding = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
//		if (runDijkstra(GoalPath.blocks.get(currentStanding), GoalPath.blocks.get(new BlockPos(GoalPath.targetPos[0], GoalPath.targetPos[1], GoalPath.targetPos[2])))) {
//			result = true;
//			
//		} else {
//			result = false;
//		}
//	}
//	
//	
//	@SubscribeEvent
//	public void onRender(RenderWorldLastEvent event) {
//		if (result) {
//			Vertex<BlockPos> destNode = GoalPath.blocks.get(new BlockPos(GoalPath.targetPos[0], GoalPath.targetPos[1], GoalPath.targetPos[2]));
//			while (GoalPath.worldGraph.label(destNode) != null) {
//				Edge<Double> nextEdge = (Edge<Double>) GoalPath.worldGraph.label(destNode);
//				Vertex<BlockPos> nextNode = GoalPath.worldGraph.to(nextEdge).equals(destNode) ? GoalPath.worldGraph.from(nextEdge) : GoalPath.worldGraph.to(nextEdge);
//				RenderUtils.blockESPBox(destNode.get());
////				System.out.println("from: " + destNode.get().getX() + ", " + destNode.get().getY() + ", " + destNode.get().getZ() + ", " + " to: " 
////						 + nextNode.get().getX() + ", " + nextNode.get().getY() + ", " + nextNode.get().getZ());
//				
//				destNode = nextNode;
//			}
//		}
//	
//	}
//	
//	
//		
//		HashMap<Vertex<BlockPos>, Double> distance = new HashMap<>();
//
//		PriorityQueue<Vertex<BlockPos>> pq;
//		
//		
//		public boolean runDijkstra(Vertex<BlockPos> start, Vertex<BlockPos> end) {
//			System.out.println("run");
//			distance = new HashMap<>();
//			distance.put(start, 0.0);
//		    pq = new PriorityQueue<>(new VertexComparator());
//		    pq.add(start);
//		    while (!pq.isEmpty()) {
//		      
//		      Vertex<BlockPos> minDistance = pq.remove();
//		      System.out.println(minDistance);
//		      if (minDistance.equals(end)) {
//		    	System.out.println(distance.get(end));
//		    	System.out.println("lol");
//		        return true;
//		      }
//		      for (Edge<Double> outgoingEdge : GoalPath.worldGraph.outgoing(minDistance)) {
//		        Vertex<BlockPos> neighbor = GoalPath.worldGraph.to(outgoingEdge);
//		        if (!neighbor.equals(minDistance) || !distance.containsKey(neighbor)) {
//		          double dist = distance.get(minDistance) + (double) outgoingEdge.get();
//		          if (!distance.containsKey(neighbor) || dist < distance.get(neighbor)) {
//		        	  distance.put(neighbor, dist);
//		        	  GoalPath.worldGraph.label(neighbor, outgoingEdge);
//		        	  pq.add(neighbor);
//		          }
//
//		        }
//		      }
//		    }
//		    return false;
//		 }
//		
//		
//		
//		
//		
//			
//		private class VertexComparator implements Comparator<Vertex<BlockPos>> {
//
//
//		    @Override
//		    public int compare(Vertex<BlockPos> vertex1, Vertex<BlockPos> vertex2) {
//		      if (!distance.containsKey(vertex1) && !distance.containsKey(vertex2)) {
//		        return 0;
//		      } else if (!distance.containsKey(vertex1)) {
//		        return Integer.MAX_VALUE;
//		      } else if (!distance.containsKey(vertex2)) {
//		        return Integer.MIN_VALUE + 1;
//		      }
//
//		      return (int)((distance.get(vertex1) - distance.get(vertex2)));
//		    }
//		  }
//		
//
//		
//	
//}
