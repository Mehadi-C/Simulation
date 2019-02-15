
package simulation;

 //============================================Coord class
  /**
   * This class sets the row and column positions to be used in the Grid class
   *
   */
  class Coord
  {
    private int row, col;
    
    /**
     * Initialzes the row and column number for the postions
     * @param  row  The integer row number
     * @param  col  The integer col number
     */
    public Coord (int r, int c)
    {
      row = r;
      col = c;
    }
    
    /**
     * Default constructor that initialzes the row and column number to -1.
     * @param  row  The integer row number
     * @param  col  The integer col number
     */
    public Coord ()
    {
      row = -1;
      col = -1;
    }
    
    /**
     * Checks if a certain Coord object has the same column and row number as the current one
     * @param  row  The integer row number
     * @param  col  The integer column number
     * @return true if same
     */
    public boolean equals(Coord in)
    {
      if (row == in.getRow() && col == in.getCol())
        return true;
      return false;
    }
    
    /**
     * Getter method for the row number
     * @return row
     */
    public int getRow()
    {
      return row;
    }
    
    /**
     *Getter method for the column number
     * @return col
     */
    public int getCol()
    {
      return col;
    }
}
