/**
 * 
 */
package cro.j2d.games.cuc.scroller.grid;

import java.util.Vector;
/**
 * @author ymc
 * This class stores a grid location that stores a 
 * list of the objecs that are touching 
 * this region 
 */
public class Sector {
	double x; // x co-ordiantes	
	double y; // y co-ordinates
	double width; // width of the sector
	double height; // height of the sector
	Vector objList; // objects that are contained in this sector
	public Sector(double xx, double yy, double w, double h){
		this.x = xx;
		this.y = yy;
		width = w;
		height = h;
		objList = new Vector();
	}
	/**
	 * @return Returns the height.
	 */
	public double getHeight() {
		return height;
	}
	/**
	 * @param height The height to set.
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	/**
	 * @return Returns the objList.
	 */
	public Vector getObjList() {
		return objList;
	}
	/**
	 * @param objList The objList to set.
	 */
	public void setObjList(Vector objList) {
		this.objList = objList;
	}
	/**
	 * @return Returns the width.
	 */
	public double getWidth() {
		return width;
	}
	/**
	 * @param width The width to set.
	 */
	public void setWidth(double width) {
		this.width = width;
	}
	/**
	 * @return Returns the x.
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param x The x to set.
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @return Returns the y.
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y The y to set.
	 */
	public void setY(double y) {
		this.y = y;
	}

}
