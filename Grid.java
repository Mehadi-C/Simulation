
package simulation;

import java.util.Random;
import java.util.Scanner;
import java.awt.*;
import java.io.*;
import javax.imageio.*; // allows image loading
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import javax.swing.event.*;  // Needed for ActionListener
import java.util.ArrayList;
import java.util.Iterator;

//----------------------------------------------------------- Grid class
/**
 * This class concerns the interaction, movement, storage, and handling of the Lifeform objects.
 *
 */
class Grid 
{
  //======================================================== data fields
  private Lifeform grid[][];
  int len = 12, rPos = -1, cPos = -1, r= 1;
  Random rn = new Random();
  Environment env;
  Season s;
  //======================================================== constructor
  /**
   * Constructs and fills a two dimensional 12 by 12 array of Lifeform objects and initializes an Environment object. It fills the array.
   *
   */
  public Grid ()
  {
    grid = new Lifeform [len][len];
    
    env = new Environment ();
    grid = env.setUp(len, grid);
    
    //fill animals
    for (int r = 0; r < len; r++){
      for (int c = 0; c < len; c++){
        if (grid[r][c].equals('_'))
        {
          if (random() <= 25)
            grid[r][c] = new Deer ();
          else if (random() <= 15)
            grid[r][c] = new Coyote ();
        }
      }
    }
  }
  
  //======================================================== methods
  /**
   * Displays the terrain by calling the showTerrain() method of the Environment class
   * and displays the Lifeform object by calling the display() method of the Lifeform class.
   * @param  g     Graphics object
   * @param  size  The integer width of the SimPanel object
   * @param  sson  Season object
   *
   */
  public void show (Graphics g, int size, Season sson)
  {
    //---on JPanel, the grid goes from x = 0 to 600 and y = 90 to 690, each box is 50 by 50
    int dim = size/len, adj = 90;
    s = sson;
    
    env.showTerrain(g); //display background
    
    //display animsl and shrubs
    for (int r = 0; r < len; r++){
      for (int c = 0; c < len; c++)
      {
        if (grid[r][c].equals('C')) 
          grid[r][c].display(g, c * dim - 1, r * dim -3  + adj, s);
        else if (grid[r][c].equals ('D')) 
          grid[r][c].display(g, c * dim - 1, r * dim-3 + adj, s);
        else if (grid[r][c].equals ('S'))
          grid[r][c].display(g, c * dim, r * dim + dim/8 + adj, s);
      }
    }
    if (s.checkSeason ("Winter")){ //winter settings
      env.snow(g);
      killPercentage();
    }
  }
  
  /**
   * Randomly chooses a Deer or Coyote object that was pre-selected by the user and kills it and the surrounding animals of its type. 
   * It kills 75% of them in a 1 block radius, 50% in a 2 block radius, and 25% in a 3 block radius.
   *
   */
  public void plague ( char an)
  { 
    ArrayList<Coord> animals = findAnimals(an); 
    if (!animals.isEmpty())
    {
      setPos (animals.get(random(animals.size())-1));
      int i = 1, x = 0, exp = 100;
      while (i <= 3 && rPos >= 0 && cPos >= 0) 
      {
        exp-=25;
        for (int r = rPos-i; r <= rPos+i; r++) {
          for (int c = cPos - i; c <= cPos+i; c++){
            if (random() <= exp && ((c < cPos-x || c > cPos+x) || (r < rPos-x || r > rPos+x)) && (r >= 0 && r < len) && (c >= 0 && c < len))
            {
              if (grid[r][c].equals(grid[rPos][cPos].get()))
                grid[r][c].kill();
            }
          }
        }
        x++;
        i++;
      }
      grid[rPos][cPos].kill();
    }
    
  }
  
  /**
   * Kills half the shrub Lifeform objects at random.
   */
  public void drought ()
  {
    for( int x= 0; x < len; x ++ ){
      for( int t= 0; t < len; t ++ )
      {
        if( grid[x][t].equals('S')){
          if(random()<= 20)
            grid[x][t]= new Lifeform();
        }
      }
    }
  }
  
  /**
   * Replaces empty spaces with shrub Lifeform objects at radnom, only if there are less than 17 shrubs exisiting.
   */
  public void heavyRain()
  {
    for( int x= 0; x < len; x ++ ){
      for( int t= 0; t < len; t ++ ){
        if( getPop('S') < 17 ){
          if( grid[x][t].equals('_'))
          {
            if(random() <= 20) //same thing as Math.random > 0.7, 30% chance
              grid[x][t]= new Lifeform('S');
          }
        }
      }
    }
  }
  
  /**
   * Introduces new Deer or Coyote objects of an accepted type 
   * @param  parent  Lifeform object
   *
   */
  public void rejuvenate(Lifeform parent)
  {
    if( !s.equals("Winter") )
    {
      for( int x = 0; x < len; x ++ )
      {
        if( Math.random() < 0.4 )
        {
          if( grid[x][0].equals('_'))
            grid[x][0]= parent;
        }
        if( Math.random() < 0.4 )
        {
          if( grid[x][1].equals('_'))
            grid[x][1]= parent;
        }
        if( Math.random() < 0.4 )
        {
          if( grid[x][len - 1].equals('_'))
            grid[x][len - 1]= parent;
        }
        if( Math.random() < 0.4 )
        {
          if( grid[x][len - 2].equals('_'))
            grid[x][len - 2]= parent;
        }
      }
    }
  }
  
  /**
   * Updates the 2D array with new Lifeform objects at certain positions based on a set of life, death, and movement conditions and events.
   * @param  b  int birth value from jslider
   * @param  d  int death value from jslider
   *
   */
  public void advance (int b, int d)  //mehadi's advance method
  {
    //make an array of coord's of all the deers/coyotes
    ArrayList<Coord> ans = findAnimals(); //finds all desires within bounds
    Iterator<Coord> it = ans.iterator();
    
    int x = 0, rIn, cIn, rFin=-1, cFin=-1;
    while (it.hasNext())
    {
      setPos(it.next()); 
      rIn = rPos;
      cIn = cPos;
      int deers = getPop ('D'), coyotes = getPop ('C');
      int deathD = 20, birthD = 60, deathC = 10, birthC = 50, eatC = 90;
      
      if( coyotes < 8 && deers < 7 )
        rejuvenate(new Deer());
      if( coyotes < 4 && deers > 10 )
        rejuvenate(new Coyote());
      
      if (rIn >= 0 && cIn >=0)
      {
        Lifeform animal = grid[rIn][cIn]; //animal refers to the single grid[rIn][cIn] Lifeform object
        if (deers < 15  && deers > 0 && coyotes < 5) //low deer, low coyotes
        {
          birthD+=10;
          birthC+=10;
          eatC -=8;
          //rejuvenate (new Coyote());
        }
        else //not-low deer, low coys
        {
          if (deers >= 12 && coyotes < 5) //normal deer
          {
            birthC+=10;
            if (deers >= 35 && coyotes < 5) //high deer
              eatC+=8;
          }
        }
        if (deers == 0) //no more deer
        {
          deathC = 90;
          birthC = 5;
        }
        //Toggle birth and death values
        birthD += b/10;
        deathD += d/10;
        birthC += b/10 - 5;
        deathC += d/10 - 5;
        
        //Deer
        if (animal instanceof Deer)
        {
          animal.incAge();
          if( check(rIn, cIn, 1, 'S') )
            animal.decHunger();
          else
            animal.incHunger();
          
          if( check(rIn, cIn, 1, 'W') )
              animal.decThirst();
          else
              animal.incThirst();
          
          //Gets positon for an empty space that animal shares with a mate (Deer), creates new Deer at empty space
          if (random() <= birthD && check (rIn, cIn, 1, 'D')) 
          {
            birth(rIn, cIn);
            rFin = rPos;
            cFin = cPos;
            if (rFin>= 0 && cFin >= 0)
              grid[rFin][cFin] = new Deer();
          }
          //Natural death -- overpopulation, "illness", old age, thirst, starvation
          if (count (rIn, cIn, 'D') >= animal.getOverPop() || (random() <= deathD && (animal.getAge() > 10 || animal.getHunger() > 5 || animal.getThirst() > 10)))
            animal.kill();
          
          //Deer in imminent danger, moves 95% of the time 
          else if (random() <= 95 && check(rIn, cIn, 1, 'C'))
          { 
            seek (rIn, cIn, 'C', false); //moving away
            rFin = rPos;
            cFin = cPos;
            if (rFin >= 0 && cFin >= 0) { //move to a safe place, and if not possible, to any random space
              grid[rFin][cFin] = new Deer(animal);
              animal.kill();
            }
          }
          
          //Deer not in imminent danger, moves 65% of the time (to a safe place or any empty space)
          else if (random() <= 65 && !check(rIn, cIn, 1, 'C'))
          {
            if (random()<= 50) //move to the random empty space
            {
              getPos (rPos, cPos, 1, '_'); //any random empty space next to it
              rFin = rPos;
              cFin = cPos;
              if (rFin>= 0 && cFin >=0){
                grid[rFin][cFin] = new Deer(animal);
                animal.kill();
              }
            }
            else //move to an empty space that's not in imminent danger ("safe place")
            {
              seek (rIn, cIn, 'C', false); //moving away
              rFin = rPos;
              cFin = cPos;
              if (rFin >= 0 && cFin >= 0) {
                grid[rFin][cFin] = new Deer(animal);
                animal.kill();
              }
            }
          }
          
          //Generally moving: 60% of the time to any random empty space (may or may not be in imminent danger
          else if (random()<=60) 
          {
            getPos (rPos, cPos, 1, '_'); //any random empty space next to it
            rFin = rPos;
            cFin = cPos;
            if (rFin>= 0 && cFin >=0){
              grid[rFin][cFin] = new Deer(animal);
              animal.kill();
            }
          }
          
        }
        
        //Coyote
        else if (animal instanceof Coyote)
        {
          if( check(rIn, cIn, 1, 'W') )
              animal.decThirst();
          else
              animal.incThirst();
            
          grid[rIn][cIn].incAge();
          
          //Natural death -- overpopulation, "illness", old age, thirst, starvation
          if (count (rIn, cIn, 'C') >= animal.getOverPop() || (random() <= deathC && animal.getAge() > 10 && animal.getHunger() > 5 && animal.getThirst() > 10) ) 
            animal.kill();
          
          //Gets positon for an empty space that it shares with a mate, creates new Coyote at empty space
          else if (random() <= birthC && check (rIn, cIn, 1, 'C') && animal.getAge() > 5 && animal.getHunger() < 5) 
          {
            birth(rIn, cIn);
            rFin = rPos;
            cFin = cPos;
            if (rFin>= 0 && cFin >= 0){
              grid[rFin][cFin] = new Coyote();
            }
          }
          
          //Coyote "eats" deer next to it, moves to the space the deer was at
          else if (random() <= eatC && check (rIn, cIn, 1, 'D')) 
          {
            getPos (rIn, cIn, 1, 'D');
            rFin = rPos;
            cFin = cPos;
            if (rFin>= 0 && cFin>=0){
              grid[rFin][cFin] = new Coyote(animal);
              animal.kill();
            }
            grid[rFin][cFin].decHunger();
          }
          
          //Seek out and move close to a deer
          else if (random() <=60 && !check (rIn, cIn, 1, 'D'))
          {
            seek (rIn, cIn, 'D', true); //moving towards
            rFin = rPos;
            cFin = cPos;
            if (rFin >= 0 && cFin >= 0) {
              grid[rFin][cFin] = new Coyote(animal);
              animal.kill();
            }
          }
          
          //Move to a random empty space (possible even if deer next to it or close to it)
          else if (random()<=50) 
          {
            getPos (rPos, cPos, 1, '_'); //any random empty space next to it
            rFin = rPos;
            cFin = cPos;
            if (rFin>= 0 && cFin >=0){
              grid[rFin][cFin] = new Coyote(animal);
              animal.kill();
            }
          }
        }
        //else 
        //  do nothing
      }//the rIn cIn if 
    } //the while loop
    
  } //advance() method
  
  /**
   * Counts the number of objects  of the desired Lifeform type in the 2D array
   * @param desire  char identifier for the desired Lifeform type
   * @return the count
   */
  private int getPop (char desire)
  {
    int count = 0;
    for (int r = 0; r < len; r++){
      for (int c = 0; c < len; c++){
        if (grid[r][c].equals (desire))
          count++;
      }
    }
    return count;
  }
  
  /**
   * Sets the position of an empty space that the Deer or Coyote object at a certain position shares with a random mate.
   * @param  row     integer row number of the parent 
   * @param  col     integer column number of the parent
   */
  private void birth (int row, int col)
  {
    Lifeform parent = grid[row][col];
    
    ArrayList<Coord> emptys = findAll(row, col, 1, '_');
    ArrayList<Coord> mates = findAll (row, col, 1, parent.get());
    ArrayList<Coord> choices = new ArrayList<Coord> ();
    
    if (!emptys.isEmpty() && !mates.isEmpty()) //Go through the array of empty spaces, and see if there is a mate next to it. if so, add that Coord
    {
      for (int x = 0; x < emptys.size(); x++)
      {
        setPos (emptys.get(x));
        
        for (int r = rPos-1; r <= rPos+1; r++) {
          for (int c = cPos - 1; c <= cPos+1; c++) {
            if ( ((r >= 0 && r <len) && (c >= 0 && c < len)) && !has(choices, new Coord (rPos, cPos)) && (r!= row || c!= col) && grid[r][c].equals(parent.get()))
            {
              if (has (mates, new Coord (r, c)))
                choices.add (emptys.get(x));
            }
          }
        } 
      }
      if (!choices.isEmpty())
        setPos (choices.get(random(choices.size())-1)); //specifically desired empty space
      else
        setPos(); //no specifically desired empty spaces
    }
    else
      setPos(); //no empty spaces at all
  }
  
  /** 
   * Replaces 30-40% of Deer and Coyote objects with blank spaces.
   */
  private void killPercentage()
  {
    for(int x = 0; x < len; x++){
      for(int y = 0; y < len; y++){
        if(grid[x][y].equals('D'))
        {
          if(random() <= 40)
            grid[x][y]= new Lifeform();
        }
        if(grid[x][y].equals('C'))
        {
          if( random() < 20 )
              grid[x][y]= new Lifeform();
        }
      }
    }
  }
  
  /**
   * Checks if the Lifeform object at a certain position has a Lifeform object of the desired type within a certain radius, starting from a one block radius, 
   * to a two block one and so on (radiating outwards).
   * @param  row     integer row number
   * @param  col     integer column number
   * @param  bound   integer value of the maximum radius.
   * @param  desire  char identifier of the desired Lifeform type
   * 
   * @return true if found
   */
  private boolean check (int row, int col, int bound, char desire)
  {
    int i = 1, x = 0;
    while (i <= bound) 
    {
      for (int r = row-i; r <= row+i; r++) {
        for (int c = col - i; c <= col+i; c++) {
          if (((c < col-x || c > col+x) || (r < row-x || r > row+x)) && (r >= 0 && r <len) && (c >= 0 && c < len)){
            if (grid[r][c].equals(desire))
              return true;
          }
        }
      }
      x++;
      i++;
    }
    return false;
  }
  
  /**
   * Checks if an ArrayList of Coord objects contains a Coord object with the same content as another Coord object.
   * @param  in   ArrayList of Coord objects
   * @param  crd  The Coord object
   * @return true if contains 
   */
  private boolean has (ArrayList<Coord> in, Coord crd)
  {
    for (int x = 0; x < in.size(); x++)
    {
      if (in.get(x).equals (crd))
        return true;
    }
    return false;
  }
  
  /**
   * Sets the integer rPos and cPos to that of an empty space either one step close to or one step away from the Lifeform object of the desired type.
   * If this is not possible then it sets them to a random empty space within a one block radius.If this is not possible then they are set to default.
   * 
   * @param  row     integer row number
   * @param  col     integer column number
   * @param  desire  char identifier of the desired Lifeform type
   * @param  towards boolean value, true if empty space must be one step closer, false if one step further.
   */
  private void seek (int row, int col, char desire, boolean towards) 
  {
    ArrayList<Coord> emptys = findAll(row, col, 1, '_');
    ArrayList<Coord> choices = new ArrayList<Coord> ();
    if (!emptys.isEmpty())
    {
      for (int x = 0; x < emptys.size(); x++)
      {
        setPos (emptys.get(x));
        for (int r = rPos-1; r <= rPos+1; r++) {
          for (int c = cPos - 1; c <= cPos+1; c++) {
            if ( ((r >= 0 && r <len) && (c >= 0 && c < len)) && (r!= row || c!= col) && !has(choices, new Coord (rPos, cPos)) )
            {
              if (towards && grid[r][c].equals(desire)) //if lifeform wants to move towards desire
                choices.add(emptys.get(x));
              else if (!towards && !grid[r][c].equals(desire)) //if lifeform wants to move away from desire
                choices.add (emptys.get(x));
            }
          }
        } 
      }
      
      if (!choices.isEmpty())
        setPos (choices.get(random(choices.size())-1)); //specifically desired empty space
      else
        setPos(emptys.get(random(emptys.size())-1)); //no specifically desired empty spaces, send a random one
    }
    else
      setPos(); //no empty spaces at all
  }
  
  /**
   * Creates and returns an ArrayList of Coord objects of all the desired Lifeform types around a certain positon within a certain radius
   * @param  row    integer row number
   * @param  col    integer column number
   * @param  bound  integer value of the bound 
   * @param  desire char identifier of the desired Lifeform type
   * @return ArrayList of found Coord objects
   */
  private ArrayList<Coord> findAll (int row, int col, int bound, char desire) //finds all desires within bounds
  {
    ArrayList<Coord> found = new ArrayList<Coord> (8);
    for (int r = row - bound; r <= row+bound; r++) {
      for (int c = col - bound; c <= col+bound; c++) {
        if ((r >= 0 && r < grid.length) && (c >= 0 && c < grid[0].length)) {
          if (grid[r][c].equals (desire)) {
            found.add (new Coord (r, c));
          }
        }
      }
    }
    found.trimToSize();
    return found;
  }
  
  /**
   * Creates and returns an ArrayList of Coord objects of all the desired Lifeform types around a certain positon within a certain radius
   * @param  desire char identifier of the desired Lifeform type
   * @return ArrayList of found Coord objects
   */
  private ArrayList<Coord> findAnimals (  char desire) //finds all desires within bounds
  {
    ArrayList<Coord> found = new ArrayList<Coord> (8);
    for (int r = 0; r < len; r++) {
      for (int c = 0; c < len; c++) {
        if ((r >= 0 && r < grid.length) && (c >= 0 && c < grid[0].length)) {
          if (grid[r][c].equals (desire)) {
            found.add (new Coord (r, c));
          }
        }
      }
    }
    found.trimToSize();
    return found;
  }
  
  /**
   * Sets the inetger rPos and cPos to that of a desired Lifeform type within certain radius around a postion randomly, or defaults them.
   * @param  row    integer row number
   * @param  col    integer column number
   * @param  b      integer bound 
   * @param  desire char identifier of the desired Lifeform type
   */
  private void getPos (int row, int col, int b, char desire)
  {
    ArrayList<Coord> pos = findAll(row, col, b, desire);
    if (!pos.isEmpty()) {
      setPos (pos.get(random(pos.size()) - 1));
    }
    else{
      setPos();
    }
  }
  
  /**
   * Creates and returns an ArrayList of Coords of all the Coyote and Deer objects in the 2D array.
   * 
   * @return ArrayList of Coord objects of animal posiitions
   */
  private ArrayList<Coord> findAnimals () //finds all animals 
  {
    ArrayList<Coord> animals = new ArrayList<Coord> ();
    for (int r = 0; r < len; r++) {
      for (int c = 0; c < len; c++) {
        if ((r >= 0 && r < len) && (c >= 0 && c < len)) {
          if (grid[r][c] instanceof Coyote|| grid[r][c] instanceof Deer) {
            animals.add (new Coord (r, c));
          }
        }
      }
    }
    animals.trimToSize();
    return animals;
  }
  
  /**
   * Counts the number of the accepted Lieform object types around a certain postion.
   * @param  row   The integer row number
   * @param  col   The integer column number
   * @param  reqd  char identifier of the required Lifeform type
   * @return count
   */
  private int count (int row, int col, char reqd)
  {
    int count = 0;
    for (int r = row - 1; r <= row+1; r++) {
      for (int c = col - 1; c <= col+1; c++) {
        if ( c!= col || r != row) { 
          if ((r >= 0 && r < grid.length-1) && (c >= 0 && c < grid[0].length)) {
            if (grid[r][c].equals(reqd)) 
              count++;
          }
        }
      }
    }
    return count;
  }
  
  /**
   * Generates a random number between 1 and 100 (inclusive)
   * @return random number
   */
  private int random ()
  {
    return (rn.nextInt(100)+1);
  }
  
  /**
   * Generates a random integer from 1 to one less than the accepted inetger 
   * @param  up  integer value of the range.
   * @return random number
   */
  private int random (int up)
  {
    return (rn.nextInt(up)+1);
  }
  
  /**
   * Sets the integer rPos and cPos to the row and column numbers of the Coord object using the getRow() snd getCol() methods.
   * @param  a  Coord object
   *
   */
  private void setPos (Coord a)
  {
    rPos = a.getRow();
    cPos = a.getCol();
  }
  
  /**
   * Sets the integer rPos and cPos to accepted values.
   * @param  r  The integer row number
   * @param  c  The integer column number
   *
   */
  private void setPos (int r, int c)
  {
    rPos = r;
    cPos = c;
  }
  
  /**
   * Defaults the integer rPos and cPos to -1
   *
   */
  private void setPos ()
  {
    rPos = -1;
    cPos = -1;
  }
}