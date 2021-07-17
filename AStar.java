import java.util.*;


public class AStar {
	private ArrayList<Node> AStarSolutionPath = new ArrayList<>();
	public AStar (int[] initialLocation, int[] targetlLocation , int[][] maze , int height , int width , int heuristic) {
		Node root = new Node();
		root.setX(initialLocation[0]);
		root.setY(initialLocation[1]);
		if (heuristic == 1) {
			root.setH(EuclideanDist(initialLocation[0],initialLocation[1],targetlLocation[0],targetlLocation[1]));
		} else {
			root.setH(manhattanDist(initialLocation[0],initialLocation[1],targetlLocation[0],targetlLocation[1]));
		}
	    
	    root.setG(0);
	    root.setF(root.getH()+root.getG());
	    
		Node goalNode = new Node();
		goalNode.setX(targetlLocation[0]);
		goalNode.setY(targetlLocation[1]);
	    goalNode.setH(0);
	    
	    CompareNodeF cn = new CompareNodeF();
	    PriorityQueue<Node> fringe = new PriorityQueue<Node>(2500, cn);

	    fringe.add(root);
	    HashMap <String, Integer> closedList = new HashMap<String,Integer>();
	    Node n = new Node();
	    n = fringe.poll();

	    closedList.put("x:"+n.getX()+"y:"+n.getY() , 1);
	    calculateAStarSearch ( n,  goalNode, root, maze,  height ,  width , heuristic , closedList , fringe);
		}
	
	public void calculateAStarSearch (Node n, Node goalNode,Node root, int[][] maze, int heightOfImage , int widthOfImage , int heuristic , HashMap <String, Integer> closedList ,PriorityQueue<Node> fringe) {
		int maxFrontier = 0;
		int depth = 0;
		int expandedNodes =0;
		while ( (n.getX() != goalNode.getX()) || (n.getY() != goalNode.getY()) )  // check if the goal state reached   
        {
			expandedNodes++;                                        
            ArrayList<Node> sucessorNodes = new ArrayList<Node>();
            sucessorNodes = getChildren(maze, n, goalNode, heightOfImage, widthOfImage , heuristic , closedList); 
            
            for(int i =0; i < sucessorNodes.size() ; i++) {
        		closedList.put("x:"+sucessorNodes.get(i).getX()+"y:"+sucessorNodes.get(i).getY() , maze[sucessorNodes.get(i).getY()][sucessorNodes.get(i).getX()]);
        		fringe.add(sucessorNodes.get(i));
        	}
            
            if (fringe.size() > maxFrontier) 
            	maxFrontier = fringe.size();  
            
            n = fringe.poll(); 
            if((n.getX() == goalNode.getX()) && (n.getY() == goalNode.getY())) {
            	depth = n.getG();
            	displaySolutionPath (closedList, expandedNodes , depth, maxFrontier, n, root,  goalNode);
            }
                	
        }
	}
	
	public void displaySolutionPath (HashMap <String, Integer> closedList, int expandedNodes , int depth, int maxFrontier, Node n, Node root, Node goalNode) {
		System.out.println("Nodes Generated: " + ( closedList.size() ) );
        System.out.println("Nodes Expanded: "+ expandedNodes );
        System.out.println("Goal Node is at depth: " + depth );
        System.out.println("Max frontier: " + maxFrontier);
        System.out.println();
        //Setting values for Goal Node to be used while printing
        goalNode.setG(depth);
        goalNode.setF( goalNode.getG() + goalNode.getH() );
        
        // array list to show the solution path
        ArrayList<Node> solutionPath = new ArrayList<>();
        solutionPath.add(goalNode);
        while( (n.getX() != root.getX()) || (n.getY() != root.getY()) )
        {
        	solutionPath.add(n);
            n = n.getAncestor();
        }
        solutionPath.add(root);
        setAStarSolutionPath(solutionPath);
	}
	
	public static int manhattanDist(int x1, int y1, int x2, int y2) {
		return (Math.abs(x2-x1) + Math.abs(y2-y1));
	}
	
	public static int EuclideanDist(int x1, int y1, int x2, int y2) {
		int x = (int) Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
		return x;
	}
	
	// comparator class for priority queue
	static class CompareNodeF implements Comparator<Node>
	{
	    public int compare(Node a, Node b)
	    {
	        if (a.getF() > b.getF())
	        {
	            return 1;
	        }
	        if (a.getF() < b.getF())
	        {
	            return -1;
	        }
	        return 0;
	    }
	}
	
	public static ArrayList<Node> getChildren (int[][] maze, Node n, Node GN, int height, int width , int heuristic , HashMap <String, Integer> closedList){
		ArrayList<Node> children = new ArrayList<>();
		
		//checking first if the node is on the corners
		if((n.getX() == 0 && n.getY() == 0) || (n.getX() == width-1 && n.getY() == 0) || (n.getX() == 0 && n.getY() == height-1) || (n.getX() == width-1 && n.getY() == height-1) ){
		if (n.getX() == 0 && n.getY() == 0) {
		if (maze[n.getY()][n.getX()+1] == 0 ) {
			Node child1 = new Node();
			child1.setX(n.getX()+1);
			child1.setY(n.getY());
			if ( !closedList.containsKey("x:"+child1.getX()+"y:"+child1.getY())  ) {
				setNewNode(child1, n , GN , heuristic);
		     	children.add(child1);
			}
		}
		if (maze[n.getY()+1][n.getX()] == 0 ) {
			Node child2 = new Node();
			child2.setX(n.getX());
			child2.setY(n.getY()+1);
			if ( !closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
				setNewNode(child2, n , GN , heuristic);
		     	children.add(child2);
			}
		}
		if (maze[n.getY()][n.getX()+1] == 0 && maze[n.getY()+1][n.getX()] == 0 && maze[n.getY()+1][n.getX()+1] == 0) {
			Node child3 = new Node();
			child3.setX(n.getX()+1);
			child3.setY(n.getY()+1);
			if ( !closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
				setNewNode(child3, n , GN , heuristic);
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
					setNewNode(child1, n , GN , heuristic);
			     	children.add(child1);
				}
			}
			if (maze[n.getY()-1][n.getX()] == 0 ) {
				Node child2 = new Node();
				child2.setX(n.getX());
				child2.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
					setNewNode(child2, n , GN , heuristic);
			     	children.add(child2);
				}
			}
			if (maze[n.getY()][n.getX()+1] == 0 && maze[n.getY()-1][n.getX()] == 0 && maze[n.getY()-1][n.getX()+1] == 0) {
				Node child3 = new Node();
				child3.setX(n.getX()+1);
				child3.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
					setNewNode(child3, n , GN , heuristic);
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
					setNewNode(child1, n , GN , heuristic);
			     	children.add(child1);
				}
			}
			if (maze[n.getY()+1][n.getX()] == 0 ) {
				Node child2 = new Node();
				child2.setX(n.getX());
				child2.setY(n.getY()+1);
				if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
					setNewNode(child2, n , GN , heuristic);
			     	children.add(child2);
				}
			}
			if (maze[n.getY()][n.getX()-1] == 0 && maze[n.getY()+1][n.getX()] == 0 && maze[n.getY()+1][n.getX()-1] == 0) {
				Node child3 = new Node();
				child3.setX(n.getX()-1);
				child3.setY(n.getY()+1);
				if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
					setNewNode(child3, n , GN , heuristic);
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
					setNewNode(child1, n , GN , heuristic);
			     	children.add(child1);
				}
			}
			if (maze[n.getY()][n.getX()-1] == 0 ) {
				Node child2 = new Node();
				child2.setX(n.getX()-1);
				child2.setY(n.getY());
				if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
					setNewNode(child2, n , GN , heuristic);
			     	children.add(child2);
				}
			}
			if (maze[n.getY()-1][n.getX()] == 0 && maze[n.getY()][n.getX()-1] == 0 && maze[n.getY()-1][n.getX()-1] == 0) {
				Node child3 = new Node();
				child3.setX(n.getX()-1);
				child3.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
					setNewNode(child3, n , GN , heuristic);
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
						setNewNode(child1, n , GN , heuristic);
				     	children.add(child1);
					}
				}
				if(maze[n.getY()][n.getX()+1] == 0) {
					Node child2 = new Node();
					child2.setX(n.getX()+1);
					child2.setY(n.getY());
					if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
						setNewNode(child2, n , GN , heuristic);
				     	children.add(child2);
					}
				}
				if((maze[n.getY()-1][n.getX()] == 0) && (maze[n.getY()][n.getX()+1] == 0) && (maze[n.getY()-1][n.getX()+1] == 0)) {
					Node child3 = new Node();
					child3.setX(n.getX()+1);
					child3.setY(n.getY()-1);
					if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
						setNewNode(child3, n , GN , heuristic);
				     	children.add(child3);
					}
				}
				if(maze[n.getY()+1][n.getX()] == 0) {
					Node child4 = new Node();
					child4.setX(n.getX());
					child4.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child4.getX()+"y:"+child4.getY())) {
						setNewNode(child4, n , GN , heuristic);
				     	children.add(child4);
					}
				}
				if((maze[n.getY()+1][n.getX()] == 0) && (maze[n.getY()][n.getX()+1] == 0) && (maze[n.getY()+1][n.getX()+1] == 0)) {
					Node child5 = new Node();
					child5.setX(n.getX()+1);
					child5.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child5.getX()+"y:"+child5.getY())) {
						setNewNode(child5, n , GN , heuristic);
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
						setNewNode(child1, n , GN , heuristic);
				     	children.add(child1);
					}
				}
				if(maze[n.getY()][n.getX()-1] == 0) {
					Node child2 = new Node();
					child2.setX(n.getX()-1);
					child2.setY(n.getY());
					if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
						setNewNode(child2, n , GN , heuristic);
				     	children.add(child2);
					}
				}
				if((maze[n.getY()-1][n.getX()] == 0) && (maze[n.getY()][n.getX()-1] == 0) && (maze[n.getY()-1][n.getX()-1] == 0)) {
					Node child3 = new Node();
					child3.setX(n.getX()-1);
					child3.setY(n.getY()-1);
					if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
						setNewNode(child3, n , GN , heuristic);
				     	children.add(child3);
					}
				}
				if(maze[n.getY()+1][n.getX()] == 0) {
					Node child4 = new Node();
					child4.setX(n.getX());
					child4.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child4.getX()+"y:"+child4.getY())) {
						setNewNode(child4, n , GN , heuristic);
				     	children.add(child4);
					}
				}
				if((maze[n.getY()+1][n.getX()] == 0) && (maze[n.getY()][n.getX()-1] == 0) && (maze[n.getY()+1][n.getX()-1] == 0)) {
					Node child5 = new Node();
					child5.setX(n.getX()-1);
					child5.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child5.getX()+"y:"+child5.getY())) {
						setNewNode(child5, n , GN , heuristic);
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
						setNewNode(child1, n , GN , heuristic);
				     	children.add(child1);
					}
				}
				if(maze[n.getY()][n.getX()+1] == 0) { 
					Node child2 = new Node();
					child2.setX(n.getX()+1);
					child2.setY(n.getY());
					if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
						setNewNode(child2, n , GN , heuristic);
				     	children.add(child2);
					}
				}
				if((maze[n.getY()+1][n.getX()] == 0) && (maze[n.getY()][n.getX()+1] == 0) && (maze[n.getY()+1][n.getX()+1] == 0)) {
					Node child3 = new Node();
					child3.setX(n.getX()+1);
					child3.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
						setNewNode(child3, n , GN , heuristic);
				     	children.add(child3);
					}
				}
				if( maze[n.getY()][n.getX()-1] == 0) {
					Node child4 = new Node();
					child4.setX(n.getX()-1);
					child4.setY(n.getY());
					if (!closedList.containsKey("x:"+child4.getX()+"y:"+child4.getY())) {
						setNewNode(child4, n , GN , heuristic);
				     	children.add(child4);
					}
				} 
				if((maze[n.getY()+1][n.getX()] == 0) && (maze[n.getY()][n.getX()-1] == 0) && (maze[n.getY()+1][n.getX()-1] == 0)) {
					Node child5 = new Node();
					child5.setX(n.getX()-1);
					child5.setY(n.getY()+1);
					if (!closedList.containsKey("x:"+child5.getX()+"y:"+child5.getY())) {
						setNewNode(child5, n , GN , heuristic);
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
						setNewNode(child1, n , GN , heuristic);
				     	children.add(child1);
					}
				}
				if(maze[n.getY()][n.getX()-1] == 0) {
					Node child2 = new Node();
					child2.setX(n.getX()-1);
					child2.setY(n.getY());
					if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
						setNewNode(child2, n , GN , heuristic);
				     	children.add(child2);
					}
				}
				if((maze[n.getY()-1][n.getX()] == 0) && (maze[n.getY()][n.getX()-1] == 0) && (maze[n.getY()-1][n.getX()-1] == 0)) {
					Node child3 = new Node();
					child3.setX(n.getX()-1);
					child3.setY(n.getY()-1);
					if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
						setNewNode(child3, n , GN , heuristic);
				     	children.add(child3);
					}
				}
				if(maze[n.getY()][n.getX()+1] == 0) {
					Node child4 = new Node();
					child4.setX(n.getX()+1);
					child4.setY(n.getY());
					if (!closedList.containsKey("x:"+child4.getX()+"y:"+child4.getY())) {
						setNewNode(child4, n , GN , heuristic);
				     	children.add(child4);
					}
				}
				if((maze[n.getY()-1][n.getX()] == 0) && (maze[n.getY()][n.getX()+1] == 0) && (maze[n.getY()-1][n.getX()+1] == 0)) {
					Node child5 = new Node();
					child5.setX(n.getX()+1);
					child5.setY(n.getY()-1);
					if (!closedList.containsKey("x:"+child5.getX()+"y:"+child5.getY())) {
						setNewNode(child5, n , GN , heuristic);
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
					setNewNode(child6, n , GN , heuristic);
			     	children.add(child6);
				}
			}
			if(maze[n.getY()+1][n.getX()] == 0) {
				Node child4 = new Node();
				child4.setX(n.getX());
				child4.setY(n.getY()+1);
				if (!closedList.containsKey("x:"+child4.getX()+"y:"+child4.getY())) {
					setNewNode(child4, n , GN , heuristic);
			     	children.add(child4);
				}
			}
			if(maze[n.getY()][n.getX()+1] == 0) {
				Node child2 = new Node();
				child2.setX(n.getX()+1);
				child2.setY(n.getY());
				if (!closedList.containsKey("x:"+child2.getX()+"y:"+child2.getY())) {
					setNewNode(child2, n , GN , heuristic);
			     	children.add(child2);
				}
			}
			if(maze[n.getY()-1][n.getX()] == 0) {
				Node child1 = new Node();
				child1.setX(n.getX());
				child1.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child1.getX()+"y:"+child1.getY())) {
					setNewNode(child1, n , GN , heuristic);
			     	children.add(child1);
				}
			}
			if ((maze[n.getY()-1][n.getX()] == 0) && (maze[n.getY()][n.getX()+1] == 0) && (maze [n.getY()-1][n.getX()+1] == 0)) {
				Node child3 = new Node();
				child3.setX(n.getX()+1);
				child3.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child3.getX()+"y:"+child3.getY())) {
					setNewNode(child3, n , GN , heuristic);
			     	children.add(child3);
				}
			}
			
			if ((maze[n.getY()+1][n.getX()] == 0) && (maze[n.getY()][n.getX()+1] == 0)  && (maze[n.getY()+1][n.getX()+1] == 0)) {
				Node child5 = new Node();
				child5.setX(n.getX()+1);
				child5.setY(n.getY()+1);
				if (!closedList.containsKey("x:"+child5.getX()+"y:"+child5.getY())) {
					setNewNode(child5, n , GN , heuristic);
			     	children.add(child5);
				}
			}
			
			if ((maze[n.getY()+1][n.getX()] == 0) && (maze[n.getY()][n.getX()-1] == 0) && (maze[n.getY()+1][n.getX()-1] == 0)) {
				Node child7 = new Node();
				child7.setX(n.getX()-1);
				child7.setY(n.getY()+1);
				if (!closedList.containsKey("x:"+child7.getX()+"y:"+child7.getY())) {
					setNewNode(child7, n , GN , heuristic);
			     	children.add(child7);
				}
			}
			if ((maze[n.getY()-1][n.getX()] == 0) && (maze[n.getY()][n.getX()-1] == 0) && (maze[n.getY()-1][n.getX()-1] == 0)) {
				Node child8 = new Node();
				child8.setX(n.getX()-1);
				child8.setY(n.getY()-1);
				if (!closedList.containsKey("x:"+child8.getX()+"y:"+child8.getY())) {
					setNewNode(child8, n , GN , heuristic);
			     	children.add(child8);
				}
			}
		}
		
		
		return children;
	}
	
	// assigning values to the node
    public static void setNewNode(Node s, Node n, Node GN, int h) {
    	s.setAncestor(n);
    	if(h == 1) {
    		s.setH(EuclideanDist(s.getX(),s.getY(), GN.getX(), GN.getY()));
    	} else {
    		s.setH(manhattanDist(s.getX(),s.getY(), GN.getX(), GN.getY()));
    	}
        s.setG(s.getAncestor().getG() + 1);
        s.setF(s.getH()+s.getG());
    }
    
  
    
    public ArrayList<Node> getAStarSolutionPath() {
		return AStarSolutionPath;
	}



	public void setAStarSolutionPath(ArrayList<Node> aStarSolutionPath) {
		AStarSolutionPath = aStarSolutionPath;
	}
	
    
 }