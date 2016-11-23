package org.westos.java.ChineseChess;

/**
 * 棋点类
 * 
 * @author cnlht
 */
public class ChessPoint {
	/** 棋子坐标 */
	int x, y;

	/** 该坐标 是否有子 */
	boolean 有棋子;

	/** 改坐标的棋子 */
	ChessPiece piece = null;

	/** 坐标所属棋盘 */
	ChessBoard board = null;

	public ChessPoint(int x, int y, boolean boo) {
		this.x = x;
		this.y = y;
		有棋子 = boo;
	}

	public boolean isPiece() {
		return 有棋子;
	}

	public void set有棋子(boolean boo) {
		有棋子 = boo;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// 设置改点棋子
	public void setPiece(ChessPiece piece, ChessBoard board) {
		this.board = board;
		this.piece = piece;
		board.add(piece);
		int w = (board.unitWidth);
		int h = (board.unitHeight);
		piece.setBounds(x - w / 2, y - h / 2, w, h);// 棋子位置，宽度，高度
		有棋子 = true;
		board.validate();
	}

	public ChessPiece getPiece() {
		return piece;
	}

	public void reMovePiece(ChessPiece piece, ChessBoard board) {
		this.board = board;
		this.piece = piece;
		board.remove(piece);
		board.validate();
		有棋子 = false;
	}
}
