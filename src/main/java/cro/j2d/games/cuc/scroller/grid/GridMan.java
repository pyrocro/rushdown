/**
 * 
 */
package cro.j2d.games.cuc.scroller.grid;


import java.util.Vector;

import cro.j2d.games.cuc.scroller.*;
/**
 * @author Yohance McDonald
 *
 */
public class GridMan {	
	static public final int COLUMN_MAX = 20;
	static public final int ROW_MAX = 10;
	double sectorWidth;
	double sectorHeight;
	Sector grid[][];
        /* Constructor for screen Grid
         * @param w World object that contains information about the Screen co-ordinates and playable size
         */
	public GridMan(World w){
		double x = w.getPlayableX(), y = w.getPlayableY();
		sectorWidth = w.getPlayableXSize()/COLUMN_MAX;
		sectorHeight = w.getPlayableYSize()/ROW_MAX;
		Sector sec = null;
		grid = new Sector[COLUMN_MAX][ROW_MAX];
		//for every column
		for (int row = 0; row<ROW_MAX; row++){
			//create a vector to store sectors
			//System.out.println("count i ="+row);
			for (int col=0; col<COLUMN_MAX;col++){
				// and add sectors to the vector
				sec = new Sector(x,y,sectorWidth,sectorHeight);
				grid[col][row] = sec;
				x += sectorWidth; // increment the column position
			}
			x = 0; //reset x position (column position)
			y += sectorHeight; // increment y position (row position)
		}
		return;
	}        
	public void clearSectors(){
		for (int row = 0; row<ROW_MAX; row++)
			for (int col=0; col<COLUMN_MAX;col++){
				grid[col][row].getObjList().clear();                                
			}
	}
	static public void removeFromSectors(Obj o){
        Sector sec = null;
        for (int i=0; i<o.getSectorList().size(); i++){
    		sec = (Sector)o.getSectorList().get(i);
    		sec.getObjList().remove(o);
    		   		
    	}
	}
	//TODO: do the organisation using a parttern algorithm
	//Places the object in the appropriate sectors
	public void organiseObj(Obj o){
            o.getSectorList().clear();
            for (int row = 0; row<ROW_MAX; row++)
                for (int col=0; col<COLUMN_MAX;col++){
                if ( grid[col][row].getX()+ grid[col][row].getWidth() >= o.getX())
                    if ( grid[col][row].getY() <= o.getY()+o.getHeight() )
                        if ( grid[col][row].getX() <= o.getX()+o.getWidth() )
                            if ( grid[col][row].getY()+ grid[col][row].getHeight() >= o.getY()){
                    grid[col][row].getObjList().add(o);
                    //o.setSectorVecIndex(grid[col][row].getObjList().size()-1); // stores the index that the object in the Sector Object List(for quick reference in the engine)
                    o.getSectorList().add(grid[col][row]);
                            }
                }
	}
	private boolean testLocation(int col, int row, Obj o){
		if ( grid[col][row].getX()+ grid[col][row].getWidth() >= o.getX())
			if ( grid[col][row].getY() <= o.getY()+o.getHeight() )
				if ( grid[col][row].getX() <= o.getX()+o.getWidth() )
					if ( grid[col][row].getY()+ grid[col][row].getHeight() >= o.getY())
						return true;
		return false;
	}
	/**
	 * @return Returns the grid.
	 */
	public Sector[][] getGrid() {
		return grid;
	}
	
}
