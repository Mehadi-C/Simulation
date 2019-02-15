
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
import javax.swing.event.*;  // Needed for ActionListener
import javax.swing.text.JTextComponent;

/**
 * Creates new window to display information about the simulation
 *
 * @author Fatima Ali, Mehadi Chowdury, Frank Hong
 * @version 2018-01-24
 */

class Simulation2 extends JFrame//class Simulation2 extends JFrame
{  
    public String t= "";//datafield
  //======================================================== constructor
  public Simulation2 () throws IOException
  {
      
    // 1... Create/initialize components
    
      //set components of text area
    JTextArea n= new JTextArea(text());    
    n.setFont(new Font("Times New Roman", Font.BOLD, 12));
    add(n);
    
    // 2... Create content pane, set layout
    JPanel content = new JPanel ();        
    content.setLayout (new BorderLayout ());
    content.setBackground (Color.white);
    setLayout (new FlowLayout ());
    setContentPane (content);
    
    add(n);//add n to window
    
    // 3... Add the components to the input area.
//    readin("QuickInfo.txt", n);
    
    
//    gr= new Grid(birth.getValue(), death.getValue());
    
    // 4... Set this window's attributes.
    pack();
    setTitle ("Life Simulation QuickInfo");
    setSize (700, 700);
    setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo (null);
    setResizable (false);
  }
  
  //method text()
  //returns information for the user about the simulation
  public String text()//text()
  {
      return "                                                                           Welcome to the Ecosystem Simulation!\n" +
        "                                                               Creators: Mehadi Chowdhury, Fatima Ali, Frank Hong\n" +
        "Overview of the Simulation Display \n" +
        "\n" +
        "  There are three main sections of the simulation display. \n" +
        "  The top left corner contains the buttons, drop down menu,and sliders that the user can click and use \n" +
        "  to change the simulation. The bottom left section displays the year number and the month.\n" +
        "  The picture complementing the seasons will change at: Month 12 for Winter, \n" +
        "  Month 3 for Spring, Month 6 for Summer, Month 9 for Fall.  \n" +
        "  The entire right sector of the board displays the actual simulation, showcasing deer and coyote interaction.\n" +
        "\n" +
        "Starting the Simulation: \n" +
        "\n" +
        "  > After the program is open, you will see the full program screen. \n" +
        "  > As mentioned before, the left side board will contain many different buttons. \n" +
        "  > The right side board will display the simulation.  \n" +
        "  > Click “Simulate” in the top right board to start the simulation. \n" +
        "  > You will see the deer and coyotes move around in the right side board.\n" +
        "\n" +
        "Summary of Interactions \n" +
        " \n" +
        " Deer \n" +
        "   > feed from shrubs, drink from lake, run away from coyotes, usually to places safer from their predators. \n" +
        "      Have shorter lives and mate more. \n" +
        " Coyotes \n" +
        "   > look for deer (food source), hunt and eat them. Drink from lake, have longer lives, usually mate less.  \n" +
        " Both \n" +
        "   > procreate and die, are affected by plague, old age, starvation, thirst and the weather \n" +
        "\n" +
        "1. Define Birth Rate Slider:\n" +
        "   - This slider allows the user to increase or decrease the animals’ spawning rate. \n" +
        "       The slider will start in the middle at a pre-set normal rate.  \n" +
        "2. Define Death Rate Slider:\n" +
        "   - This slider allows the user to increase or decrease the animals’ dying rate. \n" +
        "       The slider will start in the middle at a pre-set normal rate.  \n" +
        "3. Define Sim Speed Slider:\n" +
        "   - This slider allows the user to increase or decrease the speed of the simulation. \n" +
        "       The slider will start in the middle at a pre-set normal rate.  \n" +
        "4. Plague Button:\n" +
        "   - Clicking “Plague” will cause the program to have the user select an animal on the grid, \n" +
        "       and kill other members of its species around it. The animal itself will die, along with \n" +
        "       75% death rate for animals in a one block radius, 50% death rate for animals in a two block \n" +
        "       radius and 25% for animals in a three block radius.\n" +
        "5. Rainfall Button:\n" +
        "   - Clicking “Rainfall” will cause new shrubs to grow in the simulation, if it is not Winter\n" +
        "6. Drought Button:\n" +
        "   - Clicking “Drought” will cause 50% of the shrubs in the simulation to die off, if it is not Winter. ";
  }
}