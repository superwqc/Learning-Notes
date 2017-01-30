package org.westos.java.ChineseChess;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * 棋子类
 * 
 * @author cnlht
 */
public class ChessPiece extends JLabel {
	String name; // 棋子名字
	Color backColor = Color.RED, foreColor;// 背景色和前景色
	String 颜色类别 = null;
	ChessBoard board = null;
	int width, height;// 大小

	public ChessPiece(String name, Color fc, Color bc, int width, int height, ChessBoard board) {// 构造棋子
		this.name = name;
		this.board = board;
		this.width = width;
		this.height = height;
		foreColor = fc;
		backColor = bc;
		setSize(width, height);
		setBackground(bc);
		addMouseMotionListener(board);
		addMouseListener(board);
	}

	// 绘制棋子
	public void paint(Graphics g) {
		g.drawImage(board.pieceImg, 2, 2, width - 2, height - 2, null);
		g.setColor(foreColor);
		g.setFont(new Font("楷体", Font.BOLD, 26));
		g.drawString(name, 7, height - 8);// 在棋子上绘制 “棋子名”
		g.setColor(Color.black);
		// g.drawOval(1, 1, width - 1, height - 1);
		float lineWidth = 2.3f;
		((Graphics2D) g).setStroke(new BasicStroke(lineWidth));
		((Graphics2D) g).drawOval(2, 2, width - 2, height - 2);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}

	public Color 获取棋子颜色() {
		return foreColor;
	}

	public void set棋子类别(String 类别) {
		颜色类别 = 类别;
	}

	public String 棋子类别() {
		return 颜色类别;
	}
}
