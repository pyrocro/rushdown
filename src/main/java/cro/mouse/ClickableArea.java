/**
 * 
 */
package cro.mouse;

import java.awt.Component;
import java.awt.event.MouseEvent;
/**
 * @author ymc
 *
 */
public class ClickableArea extends MouseEvent{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5773424729412413826L;
	double x; //x Location of the clickable area
	double y; //x Location of the clickable area
	double width; // width of area
	double height; // height of the area	
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @param arg5
	 * @param arg6
	 * @param arg7
	 * @param arg8
	 */
	public ClickableArea(Component arg0, int arg1, long arg2, int arg3, int arg4, int arg5, int arg6, boolean arg7, int arg8) {
		super(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
		// TODO Auto-generated constructor stub
	}

}
