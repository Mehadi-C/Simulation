//
package simulation;

import java.util.Random;
import java.util.Scanner;
import java.awt.*;
import java.io.*;
import javax.imageio.*; // allows image loading
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import javax.swing.event.*;  // Needed for ActionListener
//
class Environment
{
  private Lifeform shrub, lake, space;
  
  public Environment ()
  { 
    shrub = new Lifeform ('S');
    lake = new Lifeform ('W');
    space = new Lifeform ('_');
  }
  
  public Lifeform[][] setUp (int len, Lifeform[][] gr)
  {
    for (int r = 0; r < len; r++){
      for (int c = 0; c < len; c++){
        if (Math.random () < 0.1)
          gr[r][c] = shrub;
        else
          gr[r][c] = space;
      }
    }
    
    for (int r = 10; r < len; r++){ //temporary, will fix up
      for (int c = 2; c < 11; c++)
        gr[r][c] = lake;
    }
    return gr;
  }
  
  public void showTerrain (Graphics g)
  {
    try {
      Image pic = ImageIO.read (new File ("b1.2.png")); 
      g.drawImage (pic, 0, 0, null);
      Image lake = ImageIO.read (new File ("lake2.png")); 
      g.drawImage (lake, 100, 595, null);
    } 
    catch (Exception e) {}
  }
  
  
  public void snow (Graphics g)
  {
    try {
      Image s = ImageIO.read (new File ("ss.png")); 
      g.drawImage (s, 0, 0, null);
      Image s2 = ImageIO.read (new File ("ss2.png")); 
      g.drawImage (s2, 0, 0, null);
    } 
    catch (Exception e) {}
  }
  
}