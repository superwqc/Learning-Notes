package org.westos.java.ChineseChess;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

/**
 * 制作棋谱类
 * 
 * @author cnlht
 */
public class MakeChessManual extends JPanel implements ActionListener {
	JTextArea text = null;
	JScrollPane scroll = null;
	ChessBoard board = null;
	ChessPoint[][] point;
	LinkedList 棋谱 = null;
	LinkedList 吃掉的棋子 = null;
	JButton buttonUndo;
	int i = 0;

	public MakeChessManual(ChessBoard board, ChessPoint[][] point) {
		this.board = board;
		this.point = point;
		text = new JTextArea();
		scroll = new JScrollPane(text);
		棋谱 = new LinkedList();
		吃掉的棋子 = new LinkedList();
		buttonUndo = new JButton("悔棋");
		buttonUndo.setFont(new Font("隶书", Font.PLAIN, 18));
		setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);
		add(buttonUndo, BorderLayout.SOUTH);
		buttonUndo.addActionListener(this);
	}

	public char numberToLetter(int n) {
		char c = '\0';
		switch (n) {
		case 1:
			c = 'A';
			break;
		case 2:
			c = 'B';
			break;
		case 3:
			c = 'C';
			break;
		case 4:
			c = 'D';
			break;
		case 5:
			c = 'E';
			break;
		case 6:
			c = 'F';
			break;
		case 7:
			c = 'G';
			break;
		case 8:
			c = 'H';
			break;
		case 9:
			c = 'I';
			break;
		case 10:
			c = 'J';
			break;
		}
		return c;
	}

	public void 记录棋谱(ChessPiece piece, int startI, int startJ, int endI, int endJ) {
		Point pStart = new Point(startI, startJ);
		Point pEnd = new Point(endI, endJ);
		MoveStep step = new MoveStep(pStart, pEnd);
		棋谱.add(step);

		String 棋子类别 = piece.棋子类别();
		String name = piece.getName();
		String m = "#" + 棋子类别 + name + ": " + startI + numberToLetter(startJ) + " 到 " + endI + numberToLetter(endJ);
		text.append(m);
		if (piece.棋子类别().equals(board.黑方颜色))
			text.append("\n");
	}

	public void 记录吃掉的棋子(Object object) {
		吃掉的棋子.add(object);
	}

	public LinkedList 获取棋谱() {
		return 棋谱;
	}

	public void actionPerformed(ActionEvent e) {
		int position = text.getText().lastIndexOf("#");
		if (position != -1)
			text.replaceRange("", position, text.getText().length());
		if (棋谱.size() > 0) {
			MoveStep lastStep = (MoveStep) 棋谱.getLast();
			棋谱.removeLast();
			Object qizi = 吃掉的棋子.getLast();
			吃掉的棋子.removeLast();
			String temp = qizi.toString();
			if (temp.equals("没吃棋子")) {
				int startI = lastStep.pStart.x;
				int startJ = lastStep.pStart.y;
				int endI = lastStep.pEnd.x;
				int endJ = lastStep.pEnd.y;
				ChessPiece piece = point[endI][endJ].getPiece();

				point[startI][startJ].setPiece(piece, board);
				(point[endI][endJ]).set有棋子(false);

				if (piece.棋子类别().equals(board.红方颜色)) {
					board.红方走棋 = true;
					board.黑方走棋 = false;
				}
				if (piece.棋子类别().equals(board.黑方颜色)) {
					board.黑方走棋 = true;
					board.红方走棋 = false;
				}
			} else {
				ChessPiece removedPiece = (ChessPiece) qizi;
				int startI = lastStep.pStart.x;
				int startJ = lastStep.pStart.y;
				int endI = lastStep.pEnd.x;
				int endJ = lastStep.pEnd.y;
				ChessPiece piece = point[endI][endJ].getPiece();
				point[startI][startJ].setPiece(piece, board);
				point[endI][endJ].setPiece(removedPiece, board);
				(point[endI][endJ]).set有棋子(true);

				if (piece.棋子类别().equals(board.红方颜色)) {
					board.红方走棋 = true;
					board.黑方走棋 = false;
				}
				if (piece.棋子类别().equals(board.黑方颜色)) {
					board.黑方走棋 = true;
					board.红方走棋 = false;
				}
			}
		}
	}
}