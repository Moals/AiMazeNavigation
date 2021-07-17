import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class DrawPathToGoal extends Component{

	BufferedImage img;
    
    ArrayList<Node> solutionPath = new ArrayList<>();
    

    public DrawPathToGoal(String path, ArrayList<Node> solutionPath) {
    	
    	this.solutionPath = solutionPath;
    	
       try {
           img = ImageIO.read(new File(path));
          
       } catch (IOException e) {
                                }
       

    }

    public Dimension getPreferredSize() {
        if (img == null) {
             return new Dimension(100,100);
        } else {
           return new Dimension(img.getWidth(null), img.getHeight(null));
       }
    }
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
        for (int i=0; i < (solutionPath.size()-1) ; i++) {
        	g.drawLine(solutionPath.get(i).getX(), solutionPath.get(i).getY(), solutionPath.get(i+1).getX(), solutionPath.get(i+1).getY());
        }
       
    }
}
