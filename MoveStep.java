package org.westos.java.ChineseChess;

import java.awt.Point;

/**
 * ×ß²½Àà
 * 
 * @author cnlht
 * 
 */
public class MoveStep implements java.io.Serializable {
	public Point pStart, pEnd;

	public MoveStep(Point p1, Point p2) {
		pStart = p1;
		pEnd = p2;
	}
}