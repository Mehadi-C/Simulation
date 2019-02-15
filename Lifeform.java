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

//-------------------------------------------------------- Lifeform class
/**
 * This class sets the characteristics of Lifeform objects and their subclasses, and creates the various Lifeform objects. 
 *
 */
class Lifeform
{
  //======================================================== data fields
  protected char life, food = ' ';
  protected Season s = new Season(0);
  protected int hunger = -1, overPop = -1, age = -1, thirst= -1;
  //======================================================== constructor
  /**
   * Constructs the new Lifeform object with the accepted char type
   * 
   * @param  type  char variable is either C for coyote, D for deer, S for shrub, _ for blank space
   */
  public Lifeform (char type)
  {
    life = type; //C for coyote, D for deer, S for shrub, W for water
  }
  
  /**
   * Default constructor for an empty Lifeform object (blank space)
   */
  public Lifeform () //_ for blank space
  {
    life = '_';
  }
  
  /**
   * Duplicate constructor that creates a new Lifeform object with the same content as the accepted one
   * 
   * @param  d  Lifeform object to be copied from
   */
  public Lifeform (Lifeform d)
  {
    life = d.life;
  }
  //======================================================== methods
  /**
   * This method reads and returns the various images needed for different Lifeform objects.
   * 
   * @return the corresponding image
   */
  private Image loadImage ()
  {
    try
    {
      if (life == 'S' && s.checkSeason ("Winter"))
        return ImageIO.read (new File ("s3.png")); 
      else if (life == 'S')
        return ImageIO.read (new File ("s2.png")); //normal shrub
      else if (life == 'D')
        return ImageIO.read (new File ("deer4.1.png")); //dd2, deer1.3, deer1.4
      else if (life == 'C')
        return ImageIO.read (new File ("c1.png")); 
      return null;
    }
    catch (Exception e) 
    {
      return null;
    }
  }
  
  /**
   * This method displays the Lifeform object on the SimPanel.
   * 
   * @param  g     Graphics object
   * @param  x     The x value for location of the new image display
   * @param  y     The y value for location of the new image display
   * @param  sson  Season object
   */
  public void display (Graphics g, int x, int y, Season sson)
  {
    s = sson;
    g.drawImage (loadImage(), x, y, null);
  }
  
  /**
   * Getter Method for the life variable
   * @return  life  The char type of the Lifeform object.
   */
  public char get () //access method for lifeform
  {
    return life;
  }
  
  /**
   * Getter Method for the food variable
   * @return  food  The char food type of the Lifeform object.
   */
  public char getFood () //access method for lifeform's food
  {
    return food;
  }
  
  /**
   * Getter Method for the age variable
   * @return  birth  The int current age of the Lifeform object
   */
  public int getAge ()
  {
    return age; 
  }
  
  /**
   * Getter Method for the hunger variable
   * @return  hunger  The int hunger count of the Lifeform object
   */
  public int getHunger()
  {
    return hunger;
  }
  
   /**
   * Getter Method for the thirst variable
   * @return  thirst  The int thirst count of the Lifeform object
   */
  
  public int getThirst()
  {
      return thirst;
  }
  
  /**
   * Getter Method for the overpop variable
   * @return  overpop  The int normal overpopularion amount of the Lifeform type
   */
  public int getOverPop ()
  {
    return overPop;
  }
  
  /**
   * Checks if the obejct has the same type as the accepted char type
   * @param  ch  char Lifeform type
   * @return  true if same
   */
  public boolean equals (char ch) 
  {
    if (life == ch)
      return true;
    return false;
  }
  
  /**
   * Checks if the object has the same type as another Lifeform object
   * @param  ch  Lifeform object
   * @return  true if the same
   */
  public boolean equals (Lifeform ch) //check is a lifeform is so-and-so
  {
    if (life == ch.life)
      return true;
    return false;
  }
  
  /**
   * Checks if the object is food for the accepted Lifeform object
   * @param  a  Lifeform object
   * @return  true if it is food
   */
  public boolean isFood (Lifeform a) 
  {
    if (life == a.getFood())
      return true;
    return false;
  }
  
  /**
   * Kills the Lifeform object, setting all varaibles back to default
   */
  public void kill () //kill lifeform
  {
    life = '_';
    food = ' ';
    hunger = -1;
    overPop = -1;
    age = -1;
    
  }
  
  /**
   * Increments the age variabe by 1
   */
  public void incAge ()
  {
    age++;
  }
  
  /**
   * Increments the hunger variable by 1
   */
  public void incHunger ()
  {
    hunger++;
  }
  
  /**
   * Increments the thirst variable by 1
   */
  public void incThirst()
  {
      thirst ++;
  }
  
  /**
   * Decrements the hunger variable by 1
   */
  public void decHunger ()
  {
    hunger--;
  }
  
  /**
   * Decrements the thirst variable by 1
   */
  public void decThirst()
  {
      thirst --;
  }
  /**
   * Checks if the Lifeform object is old enough to procreate
   * @return  false if not called by a Deer or Coyote
   */
  public boolean birthRange () //not true if not Deer or Coyote
  {
    return false;
  }
  
}

//----------------------------------------------------------Deer class
/**
 * This class is a subclass of Lifeform, and sets the various characteristics of the Deer object.
 *
 */
class Deer extends Lifeform
{
  /**
   * Default constructor that sets the attributes of the Deer object.
   *
   */
  public Deer ()
  {
    super('D');
    food = 'S';
    overPop = 3;
    age = 0;
    hunger = 20;
    thirst= 15;
  }
  
  /**
   * Duplicate constructor that creates a new Deer object with the same content as the accepted one.
   * @param d Lifeform object
   */
  public Deer (Lifeform d)
  {
    super (d);
    if (d.equals ('D')){
      food = 'S';
      overPop = d.overPop;
      age = d.age;
      hunger = d.hunger;
      thirst = d.thirst;
    }
  }
}

//---------------------------------------------------------------Coyote class
/**
 * This class is a subclass of Lifeform, and sets the various characteristics of the Coyote object.
 *
 */
class Coyote extends Lifeform
{
  /**
   * Default constructor that sets the attributes of the Coyote object.
   *
   */
  public Coyote ()
  {
    super('C');
    food = 'D';
    overPop = 4;
    age = 0;
    hunger = 20;
    thirst= 15;
  }
  
  /**
   * Duplicate constructor that creates a new Coyote object with the same content as the accepted object.
   * @param c Lifeform object
   */
  public Coyote (Lifeform c)
  {
    super (c);
    if (c.equals('C')){
      food = 'D';
      overPop = c.overPop;
      age = c.age;
      hunger = c.hunger;
      thirst = c.thirst;
    }
  }
}