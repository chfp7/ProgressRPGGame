package ai;

import java.util.ArrayList;

import entity.Entity;
import main.GamePanel;

public class PathFinder {

	GamePanel gp;
	Node[][] node;
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	int step = 0; 
	
	public PathFinder(GamePanel gp) {
		this.gp = gp;
		instantiateNode();
	}

	public void instantiateNode() {    //CORRECT
		node = new Node[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		//we create a node for every tile on the map
		while(col<gp.maxWorldCol && row<gp.maxWorldRow) {
			node[col][row] = new Node(col,row);
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	
	public void resetNodes() {//WORKS FINE
		
		int col = 0;
		int row = 0;
		
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			//RESET open, checked and solid area
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
			
		}
		
		//RESET OTHER SETTINGS 
		
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
	
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
		
		resetNodes();
		//set start and goal nodes
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		
		while(col<gp.maxWorldCol && row<gp.maxWorldRow) {//FIXED
			
			//SET SOLID NODE
			//CHECK TILES
			int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
			if(gp.tileM.tile[tileNum].collision == true) {
				
				node[col][row].solid = true; //setting the solid tiles solid
				
			}
			//SET COST
			getCost(node[col][row]);
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		
		//CHECKING INTERACTIVE TILES
		for(int i = 0 ; i<gp.iTile[1].length;i++) {
			if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible == true) {
				int itCol = gp.iTile[gp.currentMap][i].worldX/gp.tileSize;
				int itRow = gp.iTile[gp.currentMap][i].worldY/gp.tileSize;
				node[itCol][itRow].solid = true;
			}
		}
		
	}
	
	public void getCost(Node node) {
		
		//get G Cost
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		
		//get H Cost
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		
		//get FCost
		
		node.fCost = node.gCost + node.hCost;

	}
	
	public boolean search() {
		
		while(goalReached == false && step<500) {
			
			int col = currentNode.col;
			int row = currentNode.row;
			
			//check the current node
			
			currentNode.checked = true;
			openList.remove(currentNode);
			
			//OPEN THE UP NODE
			if(row-1 >= 0) {
			openNode(node[col][row-1]);//-1 because its the upNode
			}
			//OPEN THE LEFT NODE
			if(col-1 >= 0) {
			openNode(node[col-1][row]);
			}
			//OPEN THE DOWN NODE
			if(row+1 < gp.maxWorldRow) {
			openNode(node[col][row+1]);
			}
			//OPEN THE RIGHT NODE
			if(col+1 < gp.maxWorldCol) {
			openNode(node[col+1][row]);
			}
			
			//FIND THE BEST NODE
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for(int i = 0; i < openList.size() ; i++) {
				//Check if node's F cost is better
				if(openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
				}
				else if(openList.get(i).fCost == bestNodefCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			if(openList.size() == 0) {
				break;
			}
				
			//after the loop we get the best node which is our next step
			currentNode = openList.get(bestNodeIndex);
			
			if(currentNode == goalNode) {
				goalReached = true;
				trackThePath();	
			}
			step++;
		}
		
		return goalReached;
		
	}
			
		
		
	

	public void openNode(Node node) {
		
		if(node.open == false && node.checked == false && node.solid == false) {
			
			node.open = true;
			
			node.parent = currentNode;
			openList.add(node);
		}
		
	}
	
	public void trackThePath() {
		
		Node current = goalNode;
		
		while(current != startNode) {
			
			pathList.add(0,current);
			current = current.parent;
			
		}
		
	}
	
	
}
