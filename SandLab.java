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
  public static final int WATER = 3;
  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[4];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int[numRows][numCols];
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool) {
    grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay() {
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        switch (grid[row][col]) {
          case 0: display.setColor(row, col, Color.black);
                  break;
          case 1: display.setColor(row, col, Color.lightGray);
                  break;
          case 2: display.setColor(row, col, Color.yellow);
                  break;
          case 3: display.setColor(row, col, Color.blue);
                  break;
        }
      }
    }
  }

  public boolean isValid (int row, int col) {
    if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
      return true;
    }
    return false;
  }
  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step() {
    int row = (int) (Math.random() * grid.length);
    int col = (int) (Math.random() * grid[0].length);

    switch (grid[row][col]) {
      case SAND:
        if (isValid(row + 1, col)) {
          if (grid[row + 1][col] == EMPTY) {
            grid[row][col] = EMPTY;
            grid[row + 1][col] = SAND;
          }
          else if (grid[row + 1][col] == WATER) {
            grid[row][col] = WATER;
            grid[row + 1][col] = SAND;
          }
        }
        break;
      case WATER:
        int newCol = col + ((int) (Math.random() * 3) - 1);
        if (newCol == col - 1 || newCol == col + 1) {
          if (isValid(row, newCol) && grid[row][newCol] == EMPTY) {
            grid[row][col] = EMPTY;
            grid[row][newCol] = WATER;
          }
        } else {
          if (isValid(row + 1, col) && grid[row + 1][col] == EMPTY) {
            grid[row][col] = EMPTY;
            grid[row + 1][col] = WATER;
          }
        }
        break;
    }
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
