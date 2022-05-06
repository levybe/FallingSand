import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int METALBGONE = 3;
  public static final int SANDBGONE = 4;
  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[5];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[METALBGONE] = "Metal-B-Gone";
    names[SANDBGONE] = "Sand-B-Gone";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int[numRows][numCols];
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool) {
    grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay() {
    Color metalBGoneColor = new Color(150, 0, 150);
    Color sandBGoneColor = new Color(255, 0, 255);
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        switch (grid[row][col]) {
          case 0: display.setColor(row, col, Color.black);
          case 1: display.setColor(row, col, Color.lightGray);
          case 2: display.setColor(row, col, Color.yellow);
          case 3: display.setColor(row, col, metalBGoneColor);
          case 4: display.setColor(row, col, sandBGoneColor);
        }
      }
    }
  }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step() {
    
  }
  
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
