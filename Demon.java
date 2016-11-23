package org.westos.java.ChineseChess;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * 演示棋谱类
 * 
 * @author cnlht
 */
public class Demon extends JPanel implements ActionListener, Runnable {
	public JButton replay = null, next = null, auto = null, stop = null;
	LinkedList 棋谱 = null;
	Thread 自动演示 = null;
	int index = -1;
	ChessBoard board = null;
	JTextArea text;
	JTextField 时间间隔 = null;
	int time = 1000;
	String 演示过程 = "";
	JSplitPane splitH = null, splitV = null;

	public Demon(ChessBoard board) {
		this.board = board;
		replay = new JButton("重新演示");
		next = new JButton("下一步");
		auto = new JButton("自动演示");
		stop = new JButton("暂停演示");
		自动演示 = new Thread(this);
		replay.addActionListener(this);
		next.addActionListener(this);
		auto.addActionListener(this);
		stop.addActionListener(this);
		text = new JTextArea();
		时间间隔 = new JTextField("1");
		setLayout(new BorderLayout());
		JScrollPane pane = new JScrollPane(text);
		JPanel p = new JPanel(new GridLayout(3, 2));
		p.add(next);
		p.add(replay);
		p.add(auto);
		p.add(stop);
		p.add(new JLabel("时间间隔(秒)", SwingConstants.CENTER));
		p.add(时间间隔);
		splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pane, p);
		splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board, splitV);
		splitV.setDividerSize(5);
		splitV.setDividerLocation(400);
		splitH.setDividerSize(5);
		splitH.setDividerLocation(460);
		add(splitH, BorderLayout.CENTER);
		validate();
	}

	public void set棋谱(LinkedList 棋谱) {
		this.棋谱 = 棋谱;
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

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == next) {
			index++;
			if (index < 棋谱.size()) {
				演示一步(index);
			} else {
				演示结束("棋谱演示完毕");
			}
		}
		if (e.getSource() == replay) {
			board = new ChessBoard(45, 45, 9, 10);
			splitH.remove(board);
			splitH.setDividerSize(5);
			splitH.setDividerLocation(460);
			splitH.setLeftComponent(board);
			splitH.validate();
			index = -1;
			text.setText(null);
		}
		if (e.getSource() == auto) {
			next.setEnabled(false);
			replay.setEnabled(false);
			try {
				time = 1000 * Integer.parseInt(时间间隔.getText().trim());
			} catch (NumberFormatException ee) {
				time = 1000;
			}

			if (!(自动演示.isAlive())) {
				自动演示 = new Thread(this);
				board = new ChessBoard(45, 45, 9, 10);
				splitH.remove(board);
				splitH.setDividerSize(5);
				splitH.setDividerLocation(460);
				splitH.setLeftComponent(board);
				splitH.validate();
				text.setText(null);
				自动演示.start();
			}

		}
		if (e.getSource() == stop) {
			if (e.getActionCommand().equals("暂停演示")) {
				演示过程 = "暂停演示";
				stop.setText("继续演示");
				stop.repaint();
			}
			if (e.getActionCommand().equals("继续演示")) {
				演示过程 = "继续演示";
				自动演示.interrupt();
				stop.setText("暂停演示");
				stop.repaint();
			}
		}
	}

	public synchronized void run() {
		for (index = 0; index < 棋谱.size(); index++) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
			}
			while (演示过程.equals("暂停演示")) {
				try {
					wait();
				} catch (InterruptedException e) {
					notifyAll();
				}
			}
			演示一步(index);
		}
		if (index >= 棋谱.size()) {
			演示结束("棋谱演示完毕");
			next.setEnabled(true);
			replay.setEnabled(true);
		}
	}

	public void 演示一步(int index) {
		MoveStep step = (MoveStep) 棋谱.get(index);
		Point pStart = step.pStart;
		Point pEnd = step.pEnd;
		int startI = pStart.x;
		int startJ = pStart.y;
		int endI = pEnd.x;
		int endJ = pEnd.y;
		ChessPiece piece = (board.point)[startI][startJ].getPiece();
		if ((board.point)[endI][endJ].isPiece() == true) {
			ChessPiece pieceRemoved = (board.point)[endI][endJ].getPiece();
			(board.point)[endI][endJ].reMovePiece(pieceRemoved, board);
			board.repaint();
			(board.point)[endI][endJ].setPiece(piece, board);
			(board.point)[startI][startJ].set有棋子(false);
			board.repaint();
		} else {
			(board.point)[endI][endJ].setPiece(piece, board);
			(board.point)[startI][startJ].set有棋子(false);

		}
		String 棋子类别 = piece.棋子类别();
		String name = piece.getName();
		String m = "#" + 棋子类别 + name + ": " + startI + numberToLetter(startJ) + " 到 " + endI + numberToLetter(endJ);
		text.append(m);
		if (piece.棋子类别().equals(board.黑方颜色))
			text.append("\n");
	}

	public void 演示结束(String message) {
		splitH.remove(board);
		splitH.setDividerSize(5);
		splitH.setDividerLocation(460);
		JLabel label = new JLabel(message);
		label.setFont(new Font("隶书", Font.BOLD, 40));
		label.setForeground(Color.blue);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		splitH.setLeftComponent(label);
		splitH.validate();
	}
}