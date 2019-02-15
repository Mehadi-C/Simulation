
package simulation;


public class MazeAlgorithm {
    
    public MazeAlgorithm () {}

   //===========================================================
   //  Creates a new maze, prints its original form, attempts
   //  to solve it, and prints out its final form.
   //===========================================================
   public static void main (String[] args) {

      Maze labyrinth = new Maze();
      
      labyrinth.print_maze();

      if (labyrinth.solve(0, 0))
         System.out.println ("Maze solved!");
      else
         System.out.println ("No solution.");

      labyrinth.print_maze();

   }  // method main

}  // class Maze_Search

class Maze {

   int[][] grid = {{1,1,1,0,1,1,0,0,0,1,1,1,1},
                   {1,0,1,1,1,0,1,1,1,1,0,0,1},
                   {0,0,0,0,1,0,1,0,1,0,1,0,0},
                   {1,1,1,0,1,1,1,0,1,0,1,1,1},
                   {1,0,1,0,0,0,0,1,1,1,0,0,1},
                   {1,0,1,1,1,1,1,1,0,1,1,1,1},
                   {1,0,0,0,0,0,0,0,0,0,0,0,0},
                   {1,1,1,1,1,1,1,1,1,1,1,1,1}};

   //===========================================================
   //  Prints the maze grid.
   //===========================================================
   public void print_maze () {
   
      System.out.println();

      for (int row=0; row < grid.length; row++) {
         for (int column=0; column < grid[row].length; column++)
            System.out.print (grid[row][column]);
         System.out.println();
      }

      System.out.println();
      
   }
   
   
   public boolean solve (int row, int column) {

      boolean done = false;
      
      if (valid (row, column)) {

         grid[row][column] = 3;  // cell has been tried

         if (row == grid.length-1 && column == grid[0].length-1)
            done = true;  // maze is solved
         else {
            done = solve (row+1, column);  // down
            if (!done)
               done = solve (row, column+1);  // right
            if (!done)
               done = solve (row-1, column);  // up
            if (!done)
               done = solve (row, column-1);  // left
         }
         if (done)  // part of the final path
            grid[row][column] = 7;
      }
      
      return done;

   }  // method solve
   
   //===========================================================
   //  Determines if a specific location is valid.
   //===========================================================
   private boolean valid (int row, int column) {

      boolean result = false;
 
      // check if cell is in the bounds of the matrix
      if (row >= 0 && row < grid.length &&
          column >= 0 && column < grid[0].length)

         //  check if cell is not blocked and not previously tried
         if (grid[row][column] == 1)
            result = true;

      return result;

   }  // method valid
   
}  // class Maze
