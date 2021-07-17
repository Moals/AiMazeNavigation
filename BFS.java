import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;



public class BFS {
	private ArrayList<Node> BFSSolutionPath = new ArrayList<>();
	public BFS (int[] initialLocation, int[] targetlLocation , int[][] maze , int height , int width) {
		Node root = new Node();
		root.setX(initialLocation[0]);
		root.setY(initialLocation[1]);
		
	    
		Node goalNode = new Node();
		goalNode.setX(targetlLocation[0]);
		goalNode.setY(targetlLocation[1]);
	    
	   
	    HashMap <String, Integer> closedList = new HashMap<String,Integer>();
	    Queue<Node> queue = new LinkedList<>();
	    queue.add(root);
	    Node n = new Node();
	    n = queue.poll();

	    closedList.put("x:"+n.getX()+"y:"+n.getY() , 1);
	    
	    calculateBFSearch (n, goalNode, root ,maze,  height ,  width  , closedList, queue);
		}
	
	public void calculateBFSearch (Node n, Node goalNode,Node root, int[][] maze, int heightOfImage , int widthOfImage  , HashMap <String, Integer> closedList, Queue<Node> queue ) {
		int maxFrontier = 0;
		int expandedNodes =0;
		boolean goalFound = false;
		while ( !goalFound )  // check if the goal node reached   
        {
			expandedNodes++;     
			
            if((n.getX() == goalNode.getX()) && (n.getY() == goalNode.getY())) {
            	displaySolutionPath (closedList, expandedNodes , maxFrontier, n, root,  goalNode);
            	goalFound = true;
            	break;
            } else {
            	ArrayList<Node> sucessorNodes = new ArrayList<Node>();
                sucessorNodes = getChildren(maze, n, goalNode, heightOfImage, widthOfImage ,closedList); 
                
                for(int i =0; i < sucessorNodes.size() ; i++) {
                	closedList.put("x:"+sucessorNodes.get(i).getX()+"y:"+sucessorNodes.get(i).getY() , maze[sucessorNodes.get(i).getY()][sucessorNodes.get(i).getX()]);
            		queue.add(sucessorNodes.get(i));
            	}
                if (queue.size() > maxFrontier) 
                	maxFrontier = queue.size(); 
                
                n = queue.poll(); 
            }
			 
        }
	}
	
	public void displaySolutionPath (HashMap <String, Integer> closedList, int expandedNodes , int maxFrontier, Node n, Node root, Node goalNode) {
		
        
        // array list to show the solution path
        ArrayList<Node> solutionPath = new ArrayList<>();
        solutionPath.add(goalNode);
        while( (n.getX() != root.getX()) || (n.getY() != root.getY()) )
        {
        	solutionPath.add(n);
            n = n.getAncestor();
        }
        solutionPath.add(root);
        int depth = solutionPath.size();
        System.out.println("Nodes Generated: " + ( closedList.size() ) );
        System.out.println("Nodes Expanded: "+ expandedNodes );
        System.out.println("Goal Node is at depth: " + depth );
        System.out.println("Max frontier: " + maxFrontier);
        System.out.println();
        setBFSSolutionPath(solutionPath);
	}
	
	
	
	public static ArrayList<Node> getChildren (int[][] maze, Node n, Node GN, int height, int width  , HashMap <String, Integer> closedList){
		ArrayList<Node> children = new ArrayList<>();
		
		//checking first if the node is on the corners
		if((n.getX() == 0 && n.getY() == 0) || (n.getX() == width-1 && n.getY() == 0) || (n.getX() == 0 && n.getY() == height-1) || (n.getX() == width-1 && n.getY() == height-1) ){
		if (n.getX() == 0 && n.getY() == 0) {
		if (maze[n.getY()][n.getX()+1] == 0 ) {
			Node child1 = new Node();
			child1.setX(n.getX()+1);
			child1.setY(n.getY());
			if ( !closedList.containsKey("x:"+child1.getX()+"y:"+child1.getY())  ) {
				child1.setAncestor(n);
		     	children.add(child1);
			}
		}
		if (maze[n.getY()+1][n.getX()] == 0 ) {
			Node child2 = new Node();
			child2.setX(n.getX());
			child2.setY(n.getY()+1);
			if ( !closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
				child2.setAncestor(n);
		     	children.add(child2);
			}
		}
		if (maze[n.getY()][n.getX()+1] == 0 && maze[n.getY()+1][n.getX()] == 0 && maze[n.getY()+1][n.getX()+1] == 0) {
			Node child3 = new Node();
			child3.setX(n.getX()+1);
			child3.setY(n.getY()+1);
			if ( !closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
				child3.setAncestor(n);
		     	children.add(child3);
			}
		}
			}
		if (n.getX() == 0 && n.getY() == height-1) {
			if (maze[n.getY()][n.getX()+1] == 0 ) {
				Node child1 = new Node();
				child1.setX(n.getX()+1);
				child1.setY(n.getY());
				if (!closedList.containsKey("x:"+child1.getX()+"y:"+child1.getY())) {
					child1.setAncestor(n);
			     	children.add(child1);
				}
			}
			if (maze[n.getY()-1][n.getX()] == 0 ) {
				Node child2 = new Node();
				child2.setX(n.getX());
				child2.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
					child2.setAncestor(n);
			     	children.add(child2);
				}
			}
			if (maze[n.getY()][n.getX()+1] == 0 && maze[n.getY()-1][n.getX()] == 0 && maze[n.getY()-1][n.getX()+1] == 0) {
				Node child3 = new Node();
				child3.setX(n.getX()+1);
				child3.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
					child3.setAncestor(n);
			     	children.add(child3);
				}
			}
				}
		if (n.getX() == width-1 && n.getY() == 0) {
			if (maze[n.getY()][n.getX()-1] == 0 ) {
				Node child1 = new Node();
				child1.setX(n.getX()-1);
				child1.setY(n.getY());
				if (!closedList.containsKey("x:"+child1.getX()+"y:"+child1.getY())) {
					child1.setAncestor(n);
			     	children.add(child1);
				}
			}
			if (maze[n.getY()+1][n.getX()] == 0 ) {
				Node child2 = new Node();
				child2.setX(n.getX());
				child2.setY(n.getY()+1);
				if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
					child2.setAncestor(n);
			     	children.add(child2);
				}
			}
			if (maze[n.getY()][n.getX()-1] == 0 && maze[n.getY()+1][n.getX()] == 0 && maze[n.getY()+1][n.getX()-1] == 0) {
				Node child3 = new Node();
				child3.setX(n.getX()-1);
				child3.setY(n.getY()+1);
				if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
					child3.setAncestor(n);
			     	children.add(child3);
				}
			}
				}
		if (n.getX() == width-1 && n.getY() == height-1) {
			if (maze[n.getY()-1][n.getX()] == 0 ) {
				Node child1 = new Node();
				child1.setX(n.getX());
				child1.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child1.getX()+"y:"+child1.getY())) {
					child1.setAncestor(n);
			     	children.add(child1);
				}
			}
			if (maze[n.getY()][n.getX()-1] == 0 ) {
				Node child2 = new Node();
				child2.setX(n.getX()-1);
				child2.setY(n.getY());
				if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
					child2.setAncestor(n);
			     	children.add(child2);
				}
			}
			if (maze[n.getY()-1][n.getX()] == 0 && maze[n.getY()][n.getX()-1] == 0 && maze[n.getY()-1][n.getX()-1] == 0) {
				Node child3 = new Node();
				child3.setX(n.getX()-1);
				child3.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
					child3.setAncestor(n);
			     	children.add(child3);
				}
			}
				}
		} // if the node is not in the corners 
		// check if the node is in the borders of the image
		else if (  (n.getX()==0)  ||  (n.getX()==(width-1))  ||  (n.getY()==0) ||  (n.getY()==(height-1))   ){
			if (n.getX() == 0) {
				if(maze[n.getY()-1][n.getX()] == 0) {
					Node child1 = new Node();
					child1.setX(n.getX());
					child1.setY(n.getY()-1);
					if (!closedList.containsKey("x:"+child1.getX()+"y:"+child1.getY())) {
						child1.setAncestor(n);
				     	children.add(child1);
					}
				}
				if(maze[n.getY()][n.getX()+1] == 0) {
					Node child2 = new Node();
					child2.setX(n.getX()+1);
					child2.setY(n.getY());
					if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
						child2.setAncestor(n);
				     	children.add(child2);
					}
				}
				if((maze[n.getY()-1][n.getX()] == 0) && (maze[n.getY()][n.getX()+1] == 0) && (maze[n.getY()-1][n.getX()+1] == 0)) {
					Node child3 = new Node();
					child3.setX(n.getX()+1);
					child3.setY(n.getY()-1);
					if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
						child3.setAncestor(n);
				     	children.add(child3);
					}
				}
				if(maze[n.getY()+1][n.getX()] == 0) {
					Node child4 = new Node();
					child4.setX(n.getX());
					child4.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child4.getX()+"y:"+child4.getY())) {
						child4.setAncestor(n);
				     	children.add(child4);
					}
				}
				if((maze[n.getY()+1][n.getX()] == 0) && (maze[n.getY()][n.getX()+1] == 0) && (maze[n.getY()+1][n.getX()+1] == 0)) {
					Node child5 = new Node();
					child5.setX(n.getX()+1);
					child5.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child5.getX()+"y:"+child5.getY())) {
						child5.setAncestor(n);
				     	children.add(child5);
					}
				}
			}
			if (n.getX() == (width-1)) {
				if(maze[n.getY()-1][n.getX()] == 0) {
					Node child1 = new Node();
					child1.setX(n.getX());
					child1.setY(n.getY()-1);
					if (!closedList.containsKey("x:"+child1.getX()+"y:"+child1.getY())) {
						child1.setAncestor(n);
				     	children.add(child1);
					}
				}
				if(maze[n.getY()][n.getX()-1] == 0) {
					Node child2 = new Node();
					child2.setX(n.getX()-1);
					child2.setY(n.getY());
					if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
						child2.setAncestor(n);
				     	children.add(child2);
					}
				}
				if((maze[n.getY()-1][n.getX()] == 0) && (maze[n.getY()][n.getX()-1] == 0) && (maze[n.getY()-1][n.getX()-1] == 0)) {
					Node child3 = new Node();
					child3.setX(n.getX()-1);
					child3.setY(n.getY()-1);
					if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
						child3.setAncestor(n);
				     	children.add(child3);
					}
				}
				if(maze[n.getY()+1][n.getX()] == 0) {
					Node child4 = new Node();
					child4.setX(n.getX());
					child4.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child4.getX()+"y:"+child4.getY())) {
						child4.setAncestor(n);
				     	children.add(child4);
					}
				}
				if((maze[n.getY()+1][n.getX()] == 0) && (maze[n.getY()][n.getX()-1] == 0) && (maze[n.getY()+1][n.getX()-1] == 0)) {
					Node child5 = new Node();
					child5.setX(n.getX()-1);
					child5.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child5.getX()+"y:"+child5.getY())) {
						child5.setAncestor(n);
				     	children.add(child5);
					}
				}
			}
			if (n.getY() == 0) {
				if(maze[n.getY()+1][n.getX()] == 0) { 
					Node child1 = new Node();
					child1.setX(n.getX());
					child1.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child1.getX()+"y:"+child1.getY())) {
						child1.setAncestor(n);
				     	children.add(child1);
					}
				}
				if(maze[n.getY()][n.getX()+1] == 0) { 
					Node child2 = new Node();
					child2.setX(n.getX()+1);
					child2.setY(n.getY());
					if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
						child2.setAncestor(n);
				     	children.add(child2);
					}
				}
				if((maze[n.getY()+1][n.getX()] == 0) && (maze[n.getY()][n.getX()+1] == 0) && (maze[n.getY()+1][n.getX()+1] == 0)) {
					Node child3 = new Node();
					child3.setX(n.getX()+1);
					child3.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
						child3.setAncestor(n);
				     	children.add(child3);
					}
				}
				if( maze[n.getY()][n.getX()-1] == 0) {
					Node child4 = new Node();
					child4.setX(n.getX()-1);
					child4.setY(n.getY());
					if (!closedList.containsKey("x:"+child4.getX()+"y:"+child4.getY())) {
						child4.setAncestor(n);
				     	children.add(child4);
					}
				} 
				if((maze[n.getY()+1][n.getX()] == 0) && (maze[n.getY()][n.getX()-1] == 0) && (maze[n.getY()+1][n.getX()-1] == 0)) {
					Node child5 = new Node();
					child5.setX(n.getX()-1);
					child5.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child5.getX()+"y:"+child5.getY())) {
						child5.setAncestor(n);
				     	children.add(child5);
					}
				}
			}
			if (n.getY() == (height-1)) {
				if(maze[n.getY()-1][n.getX()] == 0) {
					Node child1 = new Node();
					child1.setX(n.getX());
					child1.setY(n.getY()-1);
					if (!closedList.containsKey("x:"+child1.getX()+"y:"+child1.getY())) {
						child1.setAncestor(n);
				     	children.add(child1);
					}
				}
				if(maze[n.getY()][n.getX()-1] == 0) {
					Node child2 = new Node();
					child2.setX(n.getX()-1);
					child2.setY(n.getY());
					if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
						child2.setAncestor(n);
				     	children.add(child2);
					}
				}
				if((maze[n.getY()-1][n.getX()] == 0) && (maze[n.getY()][n.getX()-1] == 0) && (maze[n.getY()-1][n.getX()-1] == 0)) {
					Node child3 = new Node();
					child3.setX(n.getX()-1);
					child3.setY(n.getY()-1);
					if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
						child3.setAncestor(n);
				     	children.add(child3);
					}
				}
				if(maze[n.getY()][n.getX()+1] == 0) {
					Node child4 = new Node();
					child4.setX(n.getX()+1);
					child4.setY(n.getY());
					if (!closedList.containsKey("x:"+child4.getX()+"y:"+child4.getY())) {
						child4.setAncestor(n);
				     	children.add(child4);
					}
				}
				if((maze[n.getY()-1][n.getX()] == 0) && (maze[n.getY()][n.getX()+1] == 0) && (maze[n.getY()-1][n.getX()+1] == 0)) {
					Node child5 = new Node();
					child5.setX(n.getX()+1);
					child5.setY(n.getY()-1);
					if (!closedList.containsKey("x:"+child5.getX()+"y:"+child5.getY())) {
						child5.setAncestor(n);
				     	children.add(child5);
					}
				}
			}
		} // if the node is not in the corners or in the borders of the image 
		else {
			if(maze[n.getY()][n.getX()-1] == 0) {
				Node child6 = new Node();
				child6.setX(n.getX()-1);
				child6.setY(n.getY());
				if (!closedList.containsKey("x:"+child6.getX()+"y:"+child6.getY())) {
					child6.setAncestor(n);
			     	children.add(child6);
				}
			}
			if(maze[n.getY()+1][n.getX()] == 0) {
				Node child4 = new Node();
				child4.setX(n.getX());
				child4.setY(n.getY()+1);
				if (!closedList.containsKey("x:"+child4.getX()+"y:"+child4.getY())) {
					child4.setAncestor(n);
			     	children.add(child4);
				}
			}
			if(maze[n.getY()][n.getX()+1] == 0) {
				Node child2 = new Node();
				child2.setX(n.getX()+1);
				child2.setY(n.getY());
				if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
					child2.setAncestor(n);
			     	children.add(child2);
				}
			}
			if(maze[n.getY()-1][n.getX()] == 0) {
				Node child1 = new Node();
				child1.setX(n.getX());
				child1.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child1.getX()+"y:"+child1.getY())) {
					child1.setAncestor(n);
			     	children.add(child1);
				}
			}
			if ((maze[n.getY()-1][n.getX()] == 0) && (maze[n.getY()][n.getX()+1] == 0) && (maze [n.getY()-1][n.getX()+1] == 0)) {
				Node child3 = new Node();
				child3.setX(n.getX()+1);
				child3.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
					child3.setAncestor(n);
			     	children.add(child3);
				}
			}
			
			if ((maze[n.getY()+1][n.getX()] == 0) && (maze[n.getY()][n.getX()+1] == 0)  && (maze[n.getY()+1][n.getX()+1] == 0)) {
				Node child5 = new Node();
				child5.setX(n.getX()+1);
				child5.setY(n.getY()+1);
				if (!closedList.containsKey("x:"+child5.getX()+"y:"+child5.getY())) {
					child5.setAncestor(n);
			     	children.add(child5);
				}
			}
			
			if ((maze[n.getY()+1][n.getX()] == 0) && (maze[n.getY()][n.getX()-1] == 0) && (maze[n.getY()+1][n.getX()-1] == 0)) {
				Node child7 = new Node();
				child7.setX(n.getX()-1);
				child7.setY(n.getY()+1);
				if (!closedList.containsKey("x:"+child7.getX()+"y:"+child7.getY())) {
					child7.setAncestor(n);
			     	children.add(child7);
				}
			}
			if ((maze[n.getY()-1][n.getX()] == 0) && (maze[n.getY()][n.getX()-1] == 0) && (maze[n.getY()-1][n.getX()-1] == 0)) {
				Node child8 = new Node();
				child8.setX(n.getX()-1);
				child8.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child8.getX()+"y:"+child8.getY())) {
					child8.setAncestor(n);
			     	children.add(child8);
				}
			}
		}
		
		
		return children;
	}
	
	
    
  
    
    public ArrayList<Node> getBFSSolutionPath() {
		return BFSSolutionPath;
	}



	public void setBFSSolutionPath(ArrayList<Node> bfsSolutionPath) {
		BFSSolutionPath = bfsSolutionPath;
	}
	
    
 }