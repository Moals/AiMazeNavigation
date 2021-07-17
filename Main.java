
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {

	
	public static void main(String[] args) {

		String pathOfImage;
		File f = null;
		int initialLocation[] = new int[2];// x and y
		int targetlLocation[] = new int[2];// x and y
		int myMaze[][] ;
		int widthOfImage;
		int heightOfImage;
		int choiceOfAlgorithm;
		int heuristic;
		
		Scanner s = new Scanner(System.in);
		// enter something like this path>>  /Users/mohammedalsaeed/Desktop/CSC361_Project_Fall2020.png
		System.out.println("Enter the path of the maze Image: ");
		pathOfImage = s.next();
		
		
		System.out.println("Enter the coordinates (column and row) of the initial location of the agent: ");
		initialLocation[0] = s.nextInt();
		initialLocation[1] = s.nextInt();
		System.out.println("Enter the coordinates (column and row) of the target location of the agent: ");
		targetlLocation[0] = s.nextInt();
		targetlLocation[1] = s.nextInt();
		
		System.out.println("To solve with Depth First Search Enter 1\nTo solve with Breadth First Search Enter 2\nTo solve with Greedy First Search Enter 3\nTo solve with AStar Enter 4");
		choiceOfAlgorithm = s.nextInt();
		if (choiceOfAlgorithm == 1) {
			try {
				long startTime = System.nanoTime();
				f = new File(pathOfImage);
				BufferedImage image = ImageIO.read(f);
				widthOfImage = image.getWidth();
				heightOfImage = image.getHeight();
				
				myMaze=makeMaze(heightOfImage,widthOfImage,image);
				if (myMaze[initialLocation[1]][initialLocation[0]] == 1 || myMaze[targetlLocation[1]][targetlLocation[0]] == 1) {
					System.out.println("the initial location or the target location is an obstacle, try again..");
				} else {
					DFS dfs = new DFS(initialLocation,targetlLocation ,myMaze , heightOfImage , widthOfImage );
					long endTime   = System.nanoTime();
					
					System.out.println("Time needed to perform the Depth First Search algorithm is : "+ ((endTime-startTime) / 1000000) + " milli seconds.");
					JFrame frame = new JFrame("DFS");
				    frame.addWindowListener(new WindowAdapter(){
				            public void windowClosing(WindowEvent e) {
				                System.exit(0);
				            }
				        });
				    
				    frame.add(new DrawPathToGoal(pathOfImage, dfs.getDFSSolutionPath()));
				    frame.pack();
				    frame.setVisible(true);
						}
					}
					catch(IOException e) {
					System.out.println("Error: " + e);
				}
		} else if(choiceOfAlgorithm == 2) {
			try {
				long startTime = System.nanoTime();
				f = new File(pathOfImage);
				BufferedImage image = ImageIO.read(f);
				widthOfImage = image.getWidth();
				heightOfImage = image.getHeight();
				
				myMaze=makeMaze(heightOfImage,widthOfImage,image);
				if (myMaze[initialLocation[1]][initialLocation[0]] == 1 || myMaze[targetlLocation[1]][targetlLocation[0]] == 1) {
					System.out.println("the initial location or the target location is an obstacle, try again..");
				} else {
					BFS bfs = new BFS(initialLocation,targetlLocation ,myMaze , heightOfImage , widthOfImage );
					long endTime   = System.nanoTime();
					
					System.out.println("Time needed to perform the Breadt First Search algorithm is : "+ ((endTime-startTime) / 1000000) + " milli seconds.");
					JFrame frame = new JFrame("BFS");
				    frame.addWindowListener(new WindowAdapter(){
				            public void windowClosing(WindowEvent e) {
				                System.exit(0);
				            }
				        });
				    
				    frame.add(new DrawPathToGoal(pathOfImage, bfs.getBFSSolutionPath()));
				    frame.pack();
				    frame.setVisible(true);
						}
					}
					catch(IOException e) {
					System.out.println("Error: " + e);
				}
		}else if(choiceOfAlgorithm == 3) {
			System.out.println("To solve with the Euclidean distance as a heuristic, Enter 1\nTo solve with the Manhattan distance as a heuristic, Enter 2");
			heuristic = s.nextInt();
			try {
				long startTime = System.nanoTime();
				f = new File(pathOfImage);
				BufferedImage image = ImageIO.read(f);
				widthOfImage = image.getWidth();
				heightOfImage = image.getHeight();
				
				myMaze=makeMaze(heightOfImage,widthOfImage,image);
				if (myMaze[initialLocation[1]][initialLocation[0]] == 1 || myMaze[targetlLocation[1]][targetlLocation[0]] == 1) {
					System.out.println("the initial location or the target location is an obstacle, try again..");
				} else {
					GreedySearch gs = new GreedySearch(initialLocation,targetlLocation ,myMaze , heightOfImage , widthOfImage , heuristic);
					long endTime   = System.nanoTime();
					if(heuristic == 1) System.out.print("With the Euclidean Distance, ");
					else System.out.print("With the Manhattan Distance, ");
					
					System.out.println("Time needed to perform the Greedy Search algorithm is : "+ ((endTime-startTime) / 1000000) + " milli seconds.");
					JFrame frame = new JFrame("Greedy First Search");
				    frame.addWindowListener(new WindowAdapter(){
				            public void windowClosing(WindowEvent e) {
				                System.exit(0);
				            }
				        });
				    
				    frame.add(new DrawPathToGoal(pathOfImage, gs.getGSsolutionPath()));
				    frame.pack();
				    frame.setVisible(true);
						}
					}
					catch(IOException e) {
					System.out.println("Error: " + e);
				}
		}else if(choiceOfAlgorithm == 4) {
			System.out.println("To solve with the Euclidean distance as a heuristic, Enter 1\nTo solve with the Manhattan distance as a heuristic, Enter 2");
			heuristic = s.nextInt();
			try {
				long startTime = System.nanoTime();
				f = new File(pathOfImage);
				BufferedImage image = ImageIO.read(f);
				widthOfImage = image.getWidth();
				heightOfImage = image.getHeight();
				
				myMaze=makeMaze(heightOfImage,widthOfImage,image);
				if (myMaze[initialLocation[1]][initialLocation[0]] == 1 || myMaze[targetlLocation[1]][targetlLocation[0]] == 1) {
					System.out.println("the initial location or the target location is an obstacle, try again..");
				} else {
					AStar as = new AStar(initialLocation,targetlLocation ,myMaze , heightOfImage , widthOfImage , heuristic);
					long endTime   = System.nanoTime();
					if(heuristic == 1) System.out.print("With the Euclidean Distance, ");
					else System.out.print("With the Manhattan Distance, ");
					
					System.out.println("Time needed to perform the AStar algorithm is : "+ ((endTime-startTime) / 1000000) + " milli seconds.");
					JFrame frame = new JFrame("AStar");
				    frame.addWindowListener(new WindowAdapter(){
				            public void windowClosing(WindowEvent e) {
				                System.exit(0);
				            }
				        });
				    
				    frame.add(new DrawPathToGoal(pathOfImage, as.getAStarSolutionPath()));
				    frame.pack();
				    frame.setVisible(true);
						}
					}
					catch(IOException e) {
					System.out.println("Error: " + e);
				}
		}else System.out.println("Wrong input, Try again.");
		
	}
	
	private static int[][] makeMaze (int height , int width , BufferedImage mazeImage) { // returns double array of 0's and 1's representing the allowed path and obsticals 
		int mazeMatrix[][] = new int[height][width];
		int pixel;
		
		for (int i = 0 ; i < height ; i++) {
			for (int j = 0 ; j < width ; j++) {
				pixel = mazeImage.getRGB(j, i);
				mazeMatrix[i][j] = isObstacle(pixel);
			}
		}
		return mazeMatrix;
	}
	
	private static int isObstacle(int pixel) { // returns 1 if it is an obstical
		int red = (pixel >> 16) & 0xff;
	    int green = (pixel >> 8) & 0xff;
	    int blue = (pixel) & 0xff;
	    if (red > 240 && green > 240 && blue > 240) {
	    	return 0;
	    }else
		return 1;
	}
	/*
	private static void displayObstacleMaze(int height , int width , BufferedImage mazeImage) { // displays a 2d array matrix of the obstacles in the image, where 1's are obstacles
		int mazeMatrix[][] = new int[height][width];
		int pixel;
		
		for (int i = 0 ; i < height ; i++) {
			for (int j = 0 ; j < width ; j++) {
				pixel = mazeImage.getRGB(j, i);
				mazeMatrix[i][j] = isObstacle(pixel);
			}
		}
		for (int i = 0; i < height; i++) { 
            for (int j = 0; j < width; j++) { 
                System.out.print(mazeMatrix[i][j] + " "); 
            } 
  
            System.out.println(); 
        }
	}
	*/

}
 