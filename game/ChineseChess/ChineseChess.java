package org.westos.java.ChineseChess;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;

/**
 * 象棋主类
 * 
 * @author cnlht
 */
public class ChineseChess extends JFrame implements ActionListener {
	ChessBoard board = null;
	Demon demon = null;
	MakeChessManual record = null;
	Container con = null;
	JMenuBar bar;
	JMenu fileMenu;
	JMenuItem 制作棋谱, 保存棋谱, 演示棋谱;
	JFileChooser fileChooser = null;
	LinkedList 棋谱 = null;

	public ChineseChess() {
		bar = new JMenuBar();
		fileMenu = new JMenu("中国象棋");
		制作棋谱 = new JMenuItem("制作棋谱");
		保存棋谱 = new JMenuItem("保存棋谱");
		保存棋谱.setEnabled(false);
		演示棋谱 = new JMenuItem("演示棋谱");
		fileMenu.add(制作棋谱);
		fileMenu.add(保存棋谱);
		fileMenu.add(演示棋谱);
		bar.add(fileMenu);
		setJMenuBar(bar);
		setTitle(制作棋谱.getText());
		制作棋谱.addActionListener(this);
		保存棋谱.addActionListener(this);
		演示棋谱.addActionListener(this);
		board = new ChessBoard(45, 45, 9, 10);
		record = board.record;
		con = getContentPane();
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, board, record);
		split.setDividerSize(5);
		split.setDividerLocation(460);
		con.add(split, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setVisible(true);
		setBounds(60, 20, 690, 540);
		fileChooser = new JFileChooser();
		con.validate();
		validate();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == 制作棋谱) {
			con.removeAll();
			保存棋谱.setEnabled(true);
			this.setTitle(制作棋谱.getText());
			board = new ChessBoard(60, 60, 20, 15);
			record = board.record;
			JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, board, record);
			split.setDividerSize(5);
			split.setDividerLocation(460);
			con.add(split, BorderLayout.CENTER);
			validate();
		}
		if (e.getSource() == 保存棋谱) {
			int state = fileChooser.showSaveDialog(null);
			File saveFile = fileChooser.getSelectedFile();
			if (saveFile != null && state == JFileChooser.APPROVE_OPTION) {
				try {
					FileOutputStream outOne = new FileOutputStream(saveFile);
					ObjectOutputStream outTwo = new ObjectOutputStream(outOne);
					outTwo.writeObject(record.获取棋谱());
					outOne.close();
					outTwo.close();
				} catch (IOException event) {
				}
			}
		}
		if (e.getSource() == 演示棋谱) {
			con.removeAll();
			con.repaint();
			con.validate();
			validate();
			保存棋谱.setEnabled(false);

			int state = fileChooser.showOpenDialog(null);
			File openFile = fileChooser.getSelectedFile();
			if (openFile != null && state == JFileChooser.APPROVE_OPTION) {
				try {
					FileInputStream inOne = new FileInputStream(openFile);
					ObjectInputStream inTwo = new ObjectInputStream(inOne);
					棋谱 = (LinkedList) inTwo.readObject();
					inOne.close();
					inTwo.close();
					ChessBoard board = new ChessBoard(45, 45, 9, 10);
					demon = new Demon(board);
					demon.set棋谱(棋谱);
					con.add(demon, BorderLayout.CENTER);
					con.validate();
					validate();
					this.setTitle(演示棋谱.getText() + ":" + openFile);
				} catch (Exception event) {
					JLabel label = new JLabel("不是棋谱文件");
					label.setFont(new Font("隶书", Font.BOLD, 60));
					label.setForeground(Color.red);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					con.add(label, BorderLayout.CENTER);
					con.validate();
					this.setTitle("没有打开棋谱");
					validate();
				}
			} else {
				JLabel label = new JLabel("没有打开棋谱文件呢");
				label.setFont(new Font("隶书", Font.BOLD, 50));
				label.setForeground(Color.pink);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				con.add(label, BorderLayout.CENTER);
				con.validate();
				this.setTitle("没有打开棋谱文件呢");
				validate();
			}
		}
	}

	public static void main(String args[]) {
		new ChineseChess();
	}
}