# AiMazeNavigation
Path-planning is important for autonomous mobile robots. It allows them to find the optimal path between two locations.
In this project we will use 4 different artificial intelligence algorithms to find the optimal path between two locations in any rectangular shape maze picture.

Input:-
•	an image that represents the map of the environment.
•	the coordinates of the initial location of the agent. e.g. (56 37)
•	the coordinates of the target location. e.g. (798 433)
•	the algorithm of choice.


The following algorithms will be implemented:
1.	Breadth first search (BFS)
2.	Depth first search (DFS)
3.	Greedy Best-first search (using Manhattan distance and Euclidean distance as heuristics)
4.	A* (using Manhattan distance and Euclidean distance as heuristics)


Output:-
a displayed image with the path from the initial position to the target position, and different other numbers.
e.g.
![Output](https://user-images.githubusercontent.com/67923027/126050430-d2e276fd-d396-48e1-bb05-289a0ca9be9b.png)
<img width="526" alt="Output numbers" src="https://user-images.githubusercontent.com/67923027/126050435-d0e406a6-48a3-43df-8d21-a855d58c0f12.png">


Design:-

The project was designed in the following way:

For the Breadth first search algorithm i stored the nodes of the maze in a First In First Out (queue) LinkedList, starting from the root node, in each iteration we check if the node is the goal node, otherwise we get each all of it’s children and store them in the queue, then we pull the next node from the queue until we find the goal node, the we go through iteration on the goal node to get each of its ancestors until the root node in order to find the solution path.

For the Depth first search algorithm i stored the nodes of the maze in a Last In First Out (stack), and used a closed list using HashMap and assigned each node a key containing the values of X and Y to check if the node was in the closed list in O(1) time, in the closed list we stored each of the visited nodes and the nodes in the fringe in order to prevent going through a loop, in each iteration for this algorithm we check first if the node in the goal node otherwise we get each children of the node if it wasn’t in the closed list and we store them in the stack, then we pop the first node in the stack until we find the solution.

For the Greedy first search algorithm we used a priority queue that would compare and store the nodes on the fringe queue based on the heuristic value, and we used the HashMap to make a closed list to prevent going to the same node twice, in each iteration of the algorithm we check if the polled out of the fringe node is the goal node otherwise, we get each of their children node and store them on the fringe queue based their heuristic value.

For the AStar search algorithm was very close to the greedy first algorithm with only difference is that the fringe will store the node based on their heuristic value and number of steps from the first node to the current node.

In order to read the image of the maze from the user we stored the image in a File class and used the BufferedImage subclass to read the image 
In order to draw the path to the goal on the image we used the Graphics class and its methods.


Implementation:-


To get the child of each node without going outside the boundaries of the image we tried first to check if the node was in the corners of the image in that case it will have three possible children, otherwise we checked if the node was on the corner of the image in this case it will have five possible children, otherwise it must be in the middle with eight possible children.
In the Informed Search Problems, we calculated the Euclidean and Manhattan admissible heuristic in the following way:

For the Euclidean distance we used the Pythagorean theorem to get the distance between each node and the goal node, which states that the area of the square whose side is the hypotenuse is equal to the sum of the areas of the squares on the other two sides.
