package me.elliottleow.kabbalah.module.modules.visual;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.api.util.BlockUtils;
import me.elliottleow.kabbalah.api.util.RenderUtils;
import me.elliottleow.kabbalah.api.util.datastructures.Edge;
import me.elliottleow.kabbalah.api.util.datastructures.Graph;
import me.elliottleow.kabbalah.api.util.datastructures.SparseGraph;
import me.elliottleow.kabbalah.api.util.datastructures.Vertex;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.module.ModuleManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GoalPath extends Module {
	
	int blocksLoaded = 0;
	
	int airLoaded = 0;
	
	static boolean doneLoading = false;
	
	BlockPos currentPlayerPos = null;
	
	public static SparseGraph<BlockPos, Double> worldGraph = new SparseGraph<>();
	
	static HashMap<BlockPos, Vertex<BlockPos>> blocks = new HashMap<>();
	
	final HashSet<Block> breakable = new HashSet<>();
	
	String currentWorld = "";
	
	public static int[] targetPos = {28, 93, 0};
	
	Block targetBlock = Blocks.mob_spawner;
	
	public GoalPath() {
		super("Goal Path", "Finds best path to goal", Category.Visual);
		this.setKey(Keyboard.KEY_K);
		breakable.add(Blocks.clay);
	}
	
	@Override
	public void onEnable() {
		worldGraph = new SparseGraph<>();
		if (Minecraft.getMinecraft().thePlayer != null) createGraph(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY ,Minecraft.getMinecraft().thePlayer.posZ);
		super.onEnable();
	}
	
	@SubscribeEvent
	public void onRender(RenderWorldLastEvent event) {
		
		if (!doneLoading) {
			return;
		}
		BlockPos currentStanding = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
		while (BlockUtils.getBlock(currentStanding) == Blocks.air) {
			currentStanding = currentStanding.down();
			if (currentStanding.getY() < 0) {
				return;
			}
		}
		if (currentPlayerPos != null && ((int) currentPlayerPos.getX() != (int) Minecraft.getMinecraft().thePlayer.posX 
				|| (int) currentPlayerPos.getY() != (int) Minecraft.getMinecraft().thePlayer.posY 
				|| (int) currentPlayerPos.getZ() != (int) Minecraft.getMinecraft().thePlayer.posZ)) {
			
			
			GoalPath.worldGraph.clearLabels();
			System.out.println("running");
			runDijkstra(GoalPath.blocks.get(currentStanding), GoalPath.blocks.get(new BlockPos(GoalPath.targetPos[0], GoalPath.targetPos[1], GoalPath.targetPos[2])));
		}
		currentPlayerPos = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY ,Minecraft.getMinecraft().thePlayer.posZ);
		
		

				
		Vertex<BlockPos> destNode = GoalPath.blocks.get(new BlockPos(GoalPath.targetPos[0], GoalPath.targetPos[1], GoalPath.targetPos[2]));
		while (destNode != null && GoalPath.worldGraph.label(destNode) != null) {
			Edge<Double> nextEdge = (Edge<Double>) GoalPath.worldGraph.label(destNode);
			Vertex<BlockPos> nextNode = GoalPath.worldGraph.to(nextEdge).equals(destNode) ? GoalPath.worldGraph.from(nextEdge) : GoalPath.worldGraph.to(nextEdge);
			RenderUtils.blockESPBox(destNode.get());
	//				System.out.println("from: " + destNode.get().getX() + ", " + destNode.get().getY() + ", " + destNode.get().getZ() + ", " + " to: " 
	//						 + nextNode.get().getX() + ", " + nextNode.get().getY() + ", " + nextNode.get().getZ());
			
			destNode = nextNode;
		}

	
		
		
		
	
	}
	
	
	
	
	private void addSurroundingEdges(BlockPos block) {
		try {
			int posX = block.getX();
			int posY = block.getY();
			int posZ = block.getZ();
			int range = 1;
	        int minX = posX - range;
	        int maxX = posX + range;
	        int minZ = posZ - range;
	        int maxZ = posZ + range;
	        int minY = posY - range;
	        int maxY = posY + range;
	        
	        for (int y = minY; y <= maxY; y++) {
	            for (int z = minZ; z <= maxZ; z++) {
	                for (int x = minX; x <= maxX; x++) {
	                	if (BlockUtils.getBlock(block.add(posX - x, posY - y, posZ - z)) != Blocks.air) {
	        				worldGraph.insert(blocks.get(block.add(posX - x, posY - y, posZ - z)), blocks.get(block), getWeight(block.add(posX - x, posY - y, posZ - z), block));
	        				worldGraph.insert(blocks.get(block), blocks.get(block.add(posX - x, posY - y, posZ - z)), getWeight(block, block.add(posX - x, posY - y, posZ - z)));
	        				
	        			}
	                }
	            }
	        }
			

		} catch (Exception e) {
			
		}
	}
	
	//precondition: to insert one block, to must be null
	private void addBlockToGraph(BlockPos from, BlockPos to) {
		BlockPos insertedBlock = null;
		if (to == null) {
			Vertex<BlockPos> blockFrom = worldGraph.insert(from);
			insertedBlock = from;
			blocks.put(from, blockFrom);
		} else {
			if ((BlockUtils.getBlock(from) == Blocks.air && BlockUtils.getBlock(to) == Blocks.air)) {
				return;
			}
			Vertex<BlockPos> blockTo = worldGraph.insert(to);
			blocks.put(to, blockTo);
			insertedBlock = to;
		}
		
		if (BlockUtils.getBlock(insertedBlock) != Blocks.air) {
			blocksLoaded++;
		} else {
			airLoaded++;
		}
		if (!blocks.containsKey(insertedBlock.north())) {
			addBlockToGraph(insertedBlock, insertedBlock.north());
		}
		if (!blocks.containsKey(insertedBlock.south())) {
			addBlockToGraph(insertedBlock, insertedBlock.south());
		}
		if (!blocks.containsKey(insertedBlock.east())) {
			addBlockToGraph(insertedBlock, insertedBlock.east());
		}
		if (!blocks.containsKey(insertedBlock.west())) {
			addBlockToGraph(insertedBlock, insertedBlock.west());
		}
		if (!blocks.containsKey(insertedBlock.up())) {
			addBlockToGraph(insertedBlock, insertedBlock.up());
		}
		if (!blocks.containsKey(insertedBlock.down())) {
			addBlockToGraph(insertedBlock, insertedBlock.down());
		}
		System.out.println("[KABBALAH CLIENT]: Path finder loaded " + blocksLoaded + " blocks");
		
	}
	
	
	
	
	
	public void createGraph(double posX, double posY, double posZ) {
		class graphCreator extends Thread {
			public void run() {
				blocksLoaded = 0;
				airLoaded = 0;
				worldGraph = new SparseGraph<>();
				BlockPos currentPos = new BlockPos(posX, posY, posZ);
				while (BlockUtils.getBlock(currentPos) == Blocks.air) {
					currentPos = currentPos.down();
				}
				long currentTime = System.currentTimeMillis();
				addBlockToGraph(currentPos, null);
				System.out.println(System.currentTimeMillis() - currentTime);
				for (Vertex<BlockPos> vertex : worldGraph.vertices()) {
					addSurroundingEdges(vertex.get());
				}
				System.out.println("Done");
				doneLoading = true;
			}
		}
		graphCreator t = new graphCreator();
        t.start();
		

    }
	
	@SubscribeEvent
	public void onBlockPlace(BlockEvent.PlaceEvent event) {
		System.out.println("placed");
		
		try {
			Vertex<BlockPos> vertex = worldGraph.insert(event.pos);
			addSurroundingEdges(vertex.get()); 
			blocks.put(event.pos, vertex);
			System.out.println(event.pos.getX());
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
		
	}
	
	@SubscribeEvent
	public void onBlockRemove(BlockEvent.HarvestDropsEvent event) {
		//block remove block from tree after handing its edges;
		Vertex<BlockPos> block = blocks.get(event.pos);
		
		//how to handle edges???
		for (Edge<Double> outgoing : worldGraph.outgoing(block)) {
			worldGraph.remove(outgoing);
		}
		for (Edge<Double> incoming : worldGraph.outgoing(block)) {
			worldGraph.remove(incoming);
		}
		worldGraph.remove(blocks.get(event.pos.add(0, 1, 0)));
		blocks.remove(event.pos.add(0, 1, 0));
		
		BlockPos currentStanding = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
		runDijkstra(GoalPath.blocks.get(currentStanding), GoalPath.blocks.get(new BlockPos(GoalPath.targetPos[0], GoalPath.targetPos[1], GoalPath.targetPos[2])));
		
	}
	
	//returns the time for the player to stand on the block v2 from v1.
	public double getWeight(BlockPos v1, BlockPos v2) {
		BlockPos destinationBlock = v2;
		
		//if there are not mineable blocks above v2, then its impossible to stand on it
		if (!isMineable(destinationBlock.add(0, 1, 0)) || !isMineable(destinationBlock.add(0, 2, 0))) return Double.MAX_VALUE-1;
		
		
		return v1.distanceSq(v2);
	}
	
	
	
	private boolean isJumpable(BlockPos p1, BlockPos p2) {
		double x2 = p2.getX();
		double y2 = p2.getY();
		double z2 = p2.getZ();
		double x1 = p1.getX();
		double y1 = p1.getY();
		double z1 = p1.getZ();
		
		double x = x2-x1;
		double y = y2-y1;
		double z = z2-z1;
		
		
		double angle = x>0 ? -Math.atan(z/x) : x == 0 ? -Math.atan(z/x) - Math.PI : 0;
		
		double xRot = x*Math.cos(angle)-y*Math.sin(angle);
		//double yRot = x*Math.sin(angle)+y*Math.cos(angle);
		
		return y < (-1/2.25)*xRot*(xRot-3) 
				&& Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x2, y2+1, z2)).getBlock() == Blocks.air
				&& Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x2, y2+2, z2)).getBlock() == Blocks.air;
	}
	
	private boolean isMineable(BlockPos p) {
		return Minecraft.getMinecraft().theWorld.getBlockState(p).getBlock() == Blocks.clay || Minecraft.getMinecraft().theWorld.getBlockState(p).getBlock() == Blocks.air;
	}
	
	public boolean runFloodFill(Vertex<BlockPos> start, Vertex<BlockPos> end) {
		Vertex<BlockPos> currentVertex = start;
		while (!currentVertex.equals(end)) {
			
		}
		return false;
	}
	
		
	HashMap<Vertex<BlockPos>, Double> distance = new HashMap<>();

	PriorityQueue<Vertex<BlockPos>> pq;
	
	
	public boolean runDijkstra(Vertex<BlockPos> start, Vertex<BlockPos> end) {
		System.out.println("run");
		distance = new HashMap<>();
		distance.put(start, 0.0);
	    pq = new PriorityQueue<>(new VertexComparator());
	    pq.add(start);
	    while (!pq.isEmpty()) {
	      Vertex<BlockPos> minDistance = pq.remove();
	      if (minDistance.equals(end)) {
	        return true;
	      }
	      for (Edge<Double> outgoingEdge : worldGraph.outgoing(minDistance)) {
	        Vertex<BlockPos> neighbor = worldGraph.to(outgoingEdge);
	        if (!neighbor.equals(minDistance) || !distance.containsKey(neighbor)) {
	          double dist = distance.get(minDistance) + (double) outgoingEdge.get();
	          if (!distance.containsKey(neighbor) || dist < distance.get(neighbor)) {
	        	  distance.put(neighbor, dist);
	        	  worldGraph.label(neighbor, outgoingEdge);
	        	  pq.add(neighbor);
	          }

	        }
	      }
	    }
	    return false;
	 }
	
	
	
	
	
		
	private class VertexComparator implements Comparator<Vertex<BlockPos>> {


	    @Override
	    public int compare(Vertex<BlockPos> vertex1, Vertex<BlockPos> vertex2) {
	      if (!distance.containsKey(vertex1) && !distance.containsKey(vertex2)) {
	        return 0;
	      } else if (!distance.containsKey(vertex1)) {
	        return Integer.MAX_VALUE;
	      } else if (!distance.containsKey(vertex2)) {
	        return Integer.MIN_VALUE + 1;
	      }

	      return (int)((distance.get(vertex1) - distance.get(vertex2)));
	    }
	  }
		

		
	

	  
	
	
	
	
	

}
