package org.westos.java.ChineseChess;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * 走棋规则类
 * 
 * @author cnlht
 */
public class Rule {
	ChessBoard board = null;
	ChessPiece piece = null;
	ChessPoint point[][];
	int startI, startJ, endI, endJ;

	public Rule(ChessBoard board, ChessPoint point[][]) {
		this.board = board;
		this.point = point;
	}

	public void isWine(ChessPiece piece) {
		this.piece = piece;
		if (piece.getName() == "将" || piece.getName() == "帅") {
			if (piece.颜色类别 == "红方") {
				JOptionPane.showMessageDialog(null, "黑方  胜利！");
			} else {
				JOptionPane.showMessageDialog(null, "红方  胜利！");
			}
		}
	}

	public boolean movePieceRule(ChessPiece piece, int startI, int startJ, int endI, int endJ) {
		this.piece = piece;
		this.startI = startI;
		this.startJ = startJ;
		this.endI = endI;
		this.endJ = endJ;
		int minI = Math.min(startI, endI);
		int maxI = Math.max(startI, endI);
		int minJ = Math.min(startJ, endJ);
		int maxJ = Math.max(startJ, endJ);
		boolean 可否走棋 = false;
		if (piece.getName().equals("车")) {
			if (startI == endI) {
				int j = 0;
				for (j = minJ + 1; j <= maxJ - 1; j++) {
					if (point[startI][j].isPiece()) {
						可否走棋 = false;
						break;
					}
				}
				if (j == maxJ) {
					可否走棋 = true;
				}
			} else if (startJ == endJ) {
				int i = 0;
				for (i = minI + 1; i <= maxI - 1; i++) {
					if (point[i][startJ].isPiece()) {
						可否走棋 = false;
						break;
					}
				}
				if (i == maxI) {
					可否走棋 = true;
				}
			} else {
				可否走棋 = false;
			}

		} else if (piece.getName().equals("車")) {
			if (startI == endI) {
				int j = 0;
				for (j = minJ + 1; j <= maxJ - 1; j++) {
					if (point[startI][j].isPiece()) {
						可否走棋 = false;
						break;
					}
				}
				if (j == maxJ) {
					可否走棋 = true;
				}
			} else if (startJ == endJ) {
				int i = 0;
				for (i = minI + 1; i <= maxI - 1; i++) {
					if (point[i][startJ].isPiece()) {
						可否走棋 = false;
						break;
					}
				}
				if (i == maxI) {
					可否走棋 = true;
				}
			} else {
				可否走棋 = false;
			}

		} else if (piece.getName().equals("马")) {
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);

			if (xAxle == 2 && yAxle == 1) {
				if (endI > startI) {
					if (point[startI + 1][startJ].isPiece()) {
						可否走棋 = false;
					} else {
						可否走棋 = true;
					}
				}
				if (endI < startI) {
					if (point[startI - 1][startJ].isPiece()) {
						可否走棋 = false;
					} else {
						可否走棋 = true;
					}
				}

			} else if (xAxle == 1 && yAxle == 2) {
				if (endJ > startJ) {
					if (point[startI][startJ + 1].isPiece()) {
						可否走棋 = false;
					} else {
						可否走棋 = true;
					}
				}
				if (endJ < startJ) {
					if (point[startI][startJ - 1].isPiece()) {
						可否走棋 = false;
					} else {
						可否走棋 = true;
					}
				}

			} else {
				可否走棋 = false;
			}
		} else if (piece.getName().equals("馬")) {
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);

			if (xAxle == 2 && yAxle == 1) {
				if (endI > startI) {
					if (point[startI + 1][startJ].isPiece()) {
						可否走棋 = false;
					} else {
						可否走棋 = true;
					}
				}
				if (endI < startI) {
					if (point[startI - 1][startJ].isPiece()) {
						可否走棋 = false;
					} else {
						可否走棋 = true;
					}
				}

			} else if (xAxle == 1 && yAxle == 2) {
				if (endJ > startJ) {
					if (point[startI][startJ + 1].isPiece()) {
						可否走棋 = false;
					} else {
						可否走棋 = true;
					}
				}
				if (endJ < startJ) {
					if (point[startI][startJ - 1].isPiece()) {
						可否走棋 = false;
					} else {
						可否走棋 = true;
					}
				}

			} else {
				可否走棋 = false;
			}
		} else if (piece.getName().equals("象")) {
			int centerI = (startI + endI) / 2;
			int centerJ = (startJ + endJ) / 2;
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (xAxle == 2 && yAxle == 2 && endJ <= 5) {
				if (point[centerI][centerJ].isPiece()) {
					可否走棋 = false;
				} else {
					可否走棋 = true;
				}
			} else {
				可否走棋 = false;
			}
		} else if (piece.getName().equals("相")) {
			int centerI = (startI + endI) / 2;
			int centerJ = (startJ + endJ) / 2;
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (xAxle == 2 && yAxle == 2 && endJ >= 6) {
				if (point[centerI][centerJ].isPiece()) {
					可否走棋 = false;
				} else {
					可否走棋 = true;
				}
			} else {
				可否走棋 = false;
			}
		} else if (piece.getName().equals("炮")) {
			int number = 0;
			if (startI == endI) {
				int j = 0;
				for (j = minJ + 1; j <= maxJ - 1; j++) {
					if (point[startI][j].isPiece()) {
						number++;
					}
				}
				if (number > 1) {
					可否走棋 = false;
				} else if (number == 1) {
					if (point[endI][endJ].isPiece()) {
						可否走棋 = true;
					}
				} else if (number == 0 && !point[endI][endJ].isPiece()) {
					可否走棋 = true;
				}
			} else if (startJ == endJ) {
				int i = 0;
				for (i = minI + 1; i <= maxI - 1; i++) {
					if (point[i][startJ].isPiece()) {
						number++;
					}
				}
				if (number > 1) {
					可否走棋 = false;
				} else if (number == 1) {
					if (point[endI][endJ].isPiece()) {
						可否走棋 = true;
					}
				} else if (number == 0 && !point[endI][endJ].isPiece()) {
					可否走棋 = true;
				}
			} else {
				可否走棋 = false;
			}
		} else if (piece.getName().equals("兵")) {
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);

			if (endJ >= 6) {
				if (startJ - endJ == 1 && xAxle == 0) {
					可否走棋 = true;
				}

				else {
					可否走棋 = false;
				}
			} else if (endJ <= 5) {
				if ((startJ - endJ == 1) && (xAxle == 0)) {
					可否走棋 = true;
				} else if ((endJ - startJ == 0) && (xAxle == 1)) {
					可否走棋 = true;
				} else {
					可否走棋 = false;
				}
			}
		} else if (piece.getName().equals("卒")) {
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);

			if (endJ <= 5) {
				if (endJ - startJ == 1 && xAxle == 0) {
					可否走棋 = true;
				} else {
					可否走棋 = false;
				}
			} else if (endJ >= 6) {
				if ((endJ - startJ == 1) && (xAxle == 0)) {
					可否走棋 = true;
				} else if ((endJ - startJ == 0) && (xAxle == 1)) {
					可否走棋 = true;
				} else {
					可否走棋 = false;
				}
			}
		}

		else if (piece.getName().equals("士")) {
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (endI <= 6 && endI >= 4 && xAxle == 1 && yAxle == 1) {
				可否走棋 = true;
			} else {
				可否走棋 = false;
			}
		} else if (piece.getName().equals("仕")) {
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (endI <= 6 && endI >= 4 && xAxle == 1 && yAxle == 1) {
				可否走棋 = true;
			} else {
				可否走棋 = false;
			}
		} else if ((piece.getName().equals("帅")) || (piece.getName().equals("将"))) {
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (endI <= 6 && endI >= 4) {
				if ((xAxle == 1 && yAxle == 0) || (xAxle == 0 && yAxle == 1)) {
					可否走棋 = true;
				} else {
					可否走棋 = false;
				}
			} else {
				可否走棋 = false;
			}
		}

		return 可否走棋;

	}
}