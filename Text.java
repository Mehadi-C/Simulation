
package simulation;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Text {
    
    public String text;
    
    public Text()
    {
        text= "";
    }
    
    public String getText()
    {
        return text;
    }
    
    public void loadText() throws IOException
    {
        try//try
        {
            File file= new File("QuickInfo.txt");//create new file
            Scanner s= new Scanner(file);//create it as scanner
        
            while( s.hasNext() )//while there are still boolean files to read
            {
               text+= s.next();//grid[row][col] is equal to s.nextBoolean
            }
        }
        catch(IOException e){}//catch
    }
}
