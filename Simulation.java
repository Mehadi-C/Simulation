
package simulation;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;
import java.io.*;
import javax.imageio.*; // allows image loading
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.*;  // Needed for ActionListener
import sun.audio.*;

/**
 * Write a description of the class here.
 *
 * @author Fatima Ali, Mehadi Chowdury, Frank Hong
 * @version 2018-01-24
 */

class Simulation extends JFrame implements ActionListener, MouseListener, ChangeListener
{
  //======================================================== data fields
  static Grid gr= new Grid();
  static Timer t;
  TimePanel time;
  SimPanel sim;
  boolean plagueClicked = false;
  static int x= 0, y= 0, year = 0, month = 0, season = 1; //months: nov, dec, jan, feb = winter; march, apr = spring; may, june, july = summer; aug, sept, oct = spring
  static Timekeeper box = new Timekeeper();
  public JButton rain, plague, drought, add;
  static JSlider birth, death, speed;
  static JComboBox choice;
  static Season sson = new Season(month);
  
  //======================================================== constructor
  public Simulation ()
  {
      
    // 1... Create/initialize components
    JButton simulate = new JButton ("Simulate");
    simulate.addActionListener (this);
    JButton info= new JButton("QuickInfo");
    info.addActionListener(this);
    plague = new JButton ("Plague");
    plague.addActionListener (this);
    drought= new JButton("Drought");
    drought.addActionListener(this);
    rain= new JButton("Rainfall");
    rain.addActionListener(this);
    JButton check = new JButton ("Check");
    check.addActionListener (this);
    
    birth = new JSlider ();
    death = new JSlider ();
    speed = new JSlider();
    birth.addChangeListener (this);
    death.addChangeListener (this);
    speed.addChangeListener (this);
    
    String[] an= {"Deer", "Coyotes"};
    
    choice= new JComboBox(an);
    choice.addActionListener(this);
    choice.setSelectedIndex(0);
    
    JLabel br = new JLabel("Define birth rates", JLabel.CENTER);
    br.setFont(new Font("Veranda", Font.BOLD, 15));
    br.setForeground(Color.white);
    JLabel dth = new JLabel("Define death rates", JLabel.CENTER);
    dth.setFont(new Font("Veranda", Font.BOLD, 15));
    dth.setForeground(Color.white);
    JLabel s = new JLabel("Define sim speed", JLabel.CENTER);
    s.setFont(new Font("Veranda", Font.BOLD, 15));
    s.setForeground(Color.white);
    
    // 2... Create content pane, set layout
    JPanel content = new JPanel ();        
    content.setLayout (new BorderLayout ()); 
    content.setBackground (Color.black);
    Side side = new Side ();
    JPanel up = new JPanel ();
    up.setLayout (new FlowLayout ());
    up.setPreferredSize (new Dimension (200, 520));
    time = new TimePanel (200, 170);
    sim = new SimPanel (600, 720);
    sim.addMouseListener(this); 
    time.addMouseListener (this); 
    up.setOpaque (false);
    
    // 3... Add the components to the input area.
    up.add (simulate);
    up.add (plague);
    up.add (choice);
    up.add (rain);
    up.add (drought);
    up.add (check);
    up.add (br);
    up.add (birth);
    up.add (dth);
    up.add (death);
    up.add (s);
    up.add (speed);
    time.add(info);
    
    side.add(up, "North");
    side.add(time, "South");
    content.add (side, "West");
    content.add (sim, "East"); 
    
//    gr= new Grid(birth.getValue(), death.getValue());
    
    // 4... Set this window's attributes.
    setContentPane (content);
    pack();
    setTitle ("Life Simulation");
    setSize (810, 720);
    setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo (null);           
    setResizable (false);
  }
  
  //======================================================== methods
  /**
   * Invoked if the user performs specific actions to implement certain methods of the Grid class.  
   *
   * @param  e  ActionEvent  
   */
  
  public void actionPerformed (ActionEvent e)
  {
      char animal= 'D';
    if(choice.getSelectedIndex() == 0)
        animal= 'D';
    if(choice.getSelectedIndex() == 1)
        animal= 'C';
    
    if( sson.equals("Winter") )
    {
        rain.setEnabled(false);
        drought.setEnabled(false);
        rain.setBorderPainted( false );
        drought.setFocusPainted( false );
    }
    else if( !sson.equals("Winter") )
    {
        rain.setEnabled(true);
        drought.setEnabled(true);
        rain.setBorderPainted( true );
        drought.setFocusPainted( true );
    }
      
    if (e.getActionCommand ().equals ("Simulate"))
    {
      MyTimer keep = new MyTimer (box); // ActionListener for timekeeper
      t = new Timer (200, keep);
      if (t!= null)
        t.setDelay (5000);
      t.start();
      Movement moveColony = new Movement (gr); // ActionListener for movement
      t = new Timer (200, moveColony); 
      if (t!= null)
        t.setDelay (500);
      t.start();
    }
    else if (e.getActionCommand ().equals ("QuickInfo")) //to check if advance is working
    {
        try {
            Simulation2 s2= new Simulation2();
            s2.setVisible(true);
        } catch (IOException ex) {}
    }
    else if (e.getActionCommand ().equals ("Check")) //to check if advance is working
    {
      gr.advance(birth.getValue(), death.getValue());
    }
    else if (e.getActionCommand ().equals ("Plague")) //to check if advance is working
    {
      gr.plague(animal);
    }
    else if (e.getActionCommand ().equals ("Rainfall") && !sson.equals("Winter") ) //to check if advance is working
    {
      gr.heavyRain();
    }
    else if (e.getActionCommand ().equals ("Drought") && !sson.equals("Winter")) //to check if advance is working
    {
      gr.drought();
    }
    repaint (); //Refresh display of grid
  }
  
  /**
   * Invoked when the target of the listener has changed its state. 
   *
   * @param  e  ChangeEvent 
   */
  
  public void stateChanged (ChangeEvent e) 
  {
     if (t != null)
            t.setDelay (500 - 4 * speed.getValue ()); // 0 to 400 ms
  }
  
  public void mouseClicked(MouseEvent e) // Respond to mouse click
  {
    //Get x and y values 
    x = e.getX() - 205;
    y = e.getY()- 195;
    
    System.out.println(x);
    System.out.println(y);
    
    repaint(); 
  }  
  
  public void mousePressed(MouseEvent e) { // All interface methods must be defined
  }
  
  public void mouseReleased(MouseEvent e) { // All interface methods must be defined
  }
  
  public void mouseEntered(MouseEvent e) { // All interface methods must be defined
  }
  
  public void mouseExited(MouseEvent e) { // All interface methods must be defined
  }
  
  //=====================================================Side class
  /**
   * Nested class in Simulation that creates a JPanel for the sidebar.
   *
   */
  
  private class Side extends JPanel
  {
    private Image sideBg;
    
    /**
     * Constructs a JPanel for the sidebar with a border layout and the dimensions 200 x 720.
     *
     */
    
    public Side ()
    {
      setLayout (new BorderLayout ());
      setPreferredSize (new Dimension (200, 720));
    }
    
    /**
     * Draws a background image to display the Season object and other features in the sidebar.
     *
     * @param  g  Graphics object
     */
    
    public void paintComponent (Graphics g)
    {
      try {
        sideBg = sson.displaySeason(); //fall2.jpg, win5.jpg, summersun.jpg
        g.drawImage (sideBg, 0, 140, null);
      } 
      catch (Exception e) {System.out.print(e);}
      
      g.setColor (new Color (191, 141, 97));
      g.fillRect (0, 0, 250, 220);
      
      g.setColor (new Color (191, 141, 97));
      g.fillRect (0, 0, 250, 160);
      
    }
  }
  
  //======================================================== SimPanel class
  /**
   * Nested class in Simulation that creates a JPanel for the Grid class to be displayed.
   *
   */
  
  class SimPanel extends JPanel
  {
    /**
     * Constructs a JPanel with a defult flow layout with accepted dimensions.
     * @param  w  The integer width of the JPanel
     * @param  h  The integer height of the JPanel
     */
      
    public SimPanel (int w, int h)
    {
      this.setPreferredSize (new Dimension (w+1, h)); // size
    }
    
    /**
     * Calls the show() method of the Grid class
     * @param  g  Graphics object
     */
    
    public void paintComponent (Graphics g)
    {
      gr.show (g, getWidth()-1, sson); //display grid
    }
  }
  
  
  //=================================================================================
  /**
   * Nested class in Simulation that creates a JPanel for the Timekeeper object to be displayed.
   *
   */
  
  class TimePanel extends JPanel
  {
    /**
     * Constructs a JPanel with a default flow layout with accepted dimensions.
     * @param  w  The integer width of the JPanel
     * @param  h  The integer height of the JPanel
     */
      
    public TimePanel (int w, int h)
    {
      this.setPreferredSize (new Dimension (w, h));
    }
    
    /**
     * Calls the display() method of the Timekeeper class and draws a background box for it.
     * @param  g  Graphics object
     */
    
    public void paintComponent (Graphics g)
    {
      g.setColor(Color.white);
      g.fillRect (10, 10, getWidth()-15, getHeight()-50);
      g.setColor(Color.black);
      g.drawRect (10, 10, getWidth()-15, getHeight()-50);
      box.display (g, month, year, sson.getSeason());
      
    }
  }
  
  //======================================================== ActionListener class for Timer
  /**
   * A nested event handler class that implements an ActionListener interface for Grid class in Simulation. 
   * This is invoked when the user clicks 'Simulate' by the actionPerformed() method in Simulation.
   *
   */
  
  class Movement implements ActionListener
  {
    private Grid grid;
    
    /**
     * Default constructor that accepts a Grid object to refer to.
     * @param  n  Grid object
     *
     */
    
    public Movement (Grid n)
    {
      grid = n;
    }
    
    /**
     * Invoked immediately when a Movement object is created. 
     *
     * @param  e  ActionEvent  
     */
    
    public void actionPerformed (ActionEvent e)
    {
      grid.advance(birth.getValue(), death.getValue()); 
      repaint (); //refresh display
    }
  }
  
  
  //=======================================================================================
  /**
   * A nested event handler class that implements an ActionListener interface for Timekeeper class in Simulation. 
   * This is invoked when the user clicks 'Simulate' by the actionPerformed() method in Simulation.
   *
   */
  
  class MyTimer extends JPanel implements ActionListener
  {
    private Timekeeper kper;
    
    /**
     * Default constructor that accepts a Timekeeper object to refer to.
     * @param  kpr  Timekeeper object
     *
     */
    
    public MyTimer (Timekeeper temp)
    {
      kper = temp;
    }
    
    /**
     * Invoked immediately when a MyTimer object is created. 
     * Increments the integer month variable of the Simulation class, setting it to 0 if 12 months have passed and incrementing the year. 
     * Calls the change() method of the Season class to change the season if required.
     *
     * @param  e  ActionEvent  
     */
    
    public void actionPerformed (ActionEvent e)
    {
      if (month ==12)
        year++;
      if (month == 12)
        month = 0;
      month++;
      sson.change (month);
      repaint(); //refresh display
      
    }
  }
  
  
  //======================================================== method main
  /**
   * The main method that creates a Simulation object and makes the GUI visible.
   *
   */
  
  public static void main (String[] args)
  {
    Simulation window = new Simulation ();
    window.setVisible (true);
  }
}


//----------------------------------------------------------- Timekeeper class
/**
 * This class displays the month, season and year component of the sidebar in the bottom left corner.
 *
 */

class Timekeeper
{
  //======================================================== data fields
  private String yr, mth, sson;
  private Color col;
  private Font f;
  
  //======================================================== constructor
  
  /**
   * Default constructor that sets the colour and the font for the time display.
   *
   */
  
  public Timekeeper ()
  {
    col = new Color (51, 25, 0);
    f = new Font ("Perpetua",Font.BOLD,25);
  }
  
  //======================================================== methods
  /**
   * This method draws the display of the month and year count as well as the season. 
   *
   * @param  g      Graphics object.
   * @param  month  Integer month variable of the Simulation class.
   * @param  year   Integer month variable of the Simulation class.
   * @param  season String of the name of the current season.
   */
  
  public void display (Graphics g, int month, int year, String season)
  {    
    mth = "Month " + month + "";
    yr = "Year " + year + "";
    sson = "" + season + "";
    g.setColor (col);
    g.setFont (f);
    g.drawString (mth, 30, 50);
    g.drawString (yr, 30, 80);
    g.drawString (sson, 30, 110);
    
  }
}