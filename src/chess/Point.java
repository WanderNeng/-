package chess;

import java.awt.*;

/**
 * 棋子实体类
 */

public class Point {
	private int x;
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Color getColor() {
		return color;
	}
	private int y;
	private Color color;
	public static int DIAMETER = 30; 
	public Point(int x,int y,Color color) {
		this.x=x;
		this.y=y;
		this.color = color;
	}

}
