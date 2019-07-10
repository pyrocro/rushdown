/**
 * 
 */
package cro.j2d.games.cuc.scroller;

import cro.j2d.games.cuc.scroller.pixelmap.Sprite;
import java.util.Vector;

/**
 * @author ymc
 *
 */
public class Enemy extends Obj {
	
	public Enemy(Vector vec, Sprite s, int x_tmp, int y_tmp, World w)
    {
        super(vec, s, x_tmp, y_tmp, w);
        tangible = true;
    }
	/* (non-Javadoc)
	 * @see cro.j2d.games.cuc.scroller.Obj#doLogic()
	 */
	public void doLogic() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see cro.j2d.games.cuc.scroller.Obj#collideWith(cro.j2d.games.cuc.scroller.Obj)
	 */
	public void collideWith(Obj obj) {
		// TODO Auto-generated method stub

	}
        public void removeFromScreen(){
            super.removeFromScreen();            
        }
	
}
