
package simulation;

import java.util.Random;
import java.util.Scanner;
import java.awt.*;
import java.io.*;
import javax.imageio.*; // allows image loading
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import javax.swing.event.*;  // Needed for ActionListener
import java.util.TimerTask;
import java.util.Timer;

//------------------------------------------------------------------Season class
/**
 * This class sets and helps run the seasons of the simulation.
 */
class Season 
{
  //================================================ data fields
  private String season;
  
  //================================================ constructor
  /**
   * Constructs the Season object depending on the accepted int month number
   * @param  num  The integer month number 
   */
  public Season (int num)
  {
    if( num== 3 || num == 4)
      season = "Spring";
    if( num== 5 || num ==6 || num == 7)
      season = "Summer";
    if( num == 8 || num == 9 || num == 10 )
      season = "Fall";
    else
      season = "Winter";
  }
  
  //================================================= methods
  /**
   * Getter methods for the season
   * @return season
   *
   */
  public String getSeason()
  {
    return season;
  }
  
  /**
   * Changes the season depending on the accepted int month number
   * @param  num  The integer month number 
   */
  public void change(int num)
  {
    if( num== 3 || num == 4 || num == 5)
      season = "Spring";
    else if( num== 6 || num ==7 || num == 8)
      season = "Summer";
    else if( num == 9 || num == 10 || num == 11 )
      season = "Fall";
    else
      season = "Winter";
  }
  
  /**
   * Displays the background image related to the current season
   *
   */
  public Image displaySeason() throws IOException
  {
    if( season.equals("Winter"))
      return ImageIO.read (new File ("win5.jpg"));
    else if( season.equals("Spring"))
      return ImageIO.read (new File ("spring1.jpg"));
    else if( season.equals("Summer"))
      return ImageIO.read (new File ("summersun.jpg"));
    else if( season.equals("Fall"))
      return ImageIO.read (new File ("fall2.jpg"));
    
    return null;
  }
  
  /**
   * Checks if the current season is the same as the accepted String
   * @return true if the same
   */
  public boolean checkSeason (String s)
  {
    if (season.equals(s))
      return true;
    return false;
  }  
}
