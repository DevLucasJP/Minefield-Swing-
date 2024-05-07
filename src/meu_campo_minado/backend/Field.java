package meu_campo_minado.backend;

import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.swing.JOptionPane;

import meu_campo_minado.frontend.SquareButton;

public class Field implements SquareButtonObserver {

	private Square[][] hiddenField = new Square[9][9];

	private Predicate<Square> isMined = im -> im.getMine();

	public Field() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				hiddenField[i][j] = new Square();
				hiddenField[i][j].setLine(i);
				hiddenField[i][j].setColumn(j);
			}
		}

		setNexts();

		generateMines();
	}

	private void generateMines() {

		Random random = new Random();

		for (int i = 0; i < 10; i++) {

			int line = random.nextInt(9);
			int column = random.nextInt(9);

			while (isMined.test(hiddenField[line][column])) {
				line = random.nextInt(9);
				column = random.nextInt(9);
			}

			hiddenField[line][column].setText("*");
			hiddenField[line][column].setMine();
			generateWarnings(line, column);
		}
	}

	private boolean findNext(int line, int column, int i, int j) {

		BinaryOperator<Integer> absolute = (a, b) -> Math.abs(a - b);
		int absLine = absolute.apply(line, i);
		int absColumn = absolute.apply(column, j);

		boolean diagonalNext = absLine == 1 && absColumn == 1;
		boolean crossNext = (absLine + absColumn) == 1;
		boolean next = diagonalNext || crossNext;

		return next;
	}

	private void setNexts() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				for (int x = 0; x < 9; x++) {
					for (int y = 0; y < 9; y++) {

						if (findNext(hiddenField[i][j].getLine(), hiddenField[i][j].getColumn(),
								hiddenField[x][y].getLine(), hiddenField[x][y].getColumn())) {
							hiddenField[i][j].setNexts(hiddenField[x][y]);
						}
					}
				}
			}
		}
	}

	private void generateWarnings(int line, int column) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				if (findNext(line, column, i, j) && !isMined.test(hiddenField[i][j])) {
					if (hiddenField[i][j].getText().equals("1")) {
						hiddenField[i][j].setText("2");
					} else if (hiddenField[i][j].getText().equals("2")) {
						hiddenField[i][j].setText("3");
					} else if (hiddenField[i][j].getText().equals("3")) {
						hiddenField[i][j].setText("4");
					} else {
						hiddenField[i][j].setText("1");
					}
				}
			}
		}
	}

	private void openField(SquareButton squareButton, FieldEvents event) {

		if (event == FieldEvents.MARK) {
			squareButton.getSquare().getVisualButton().applyMarkStyle(squareButton.getSquare());
			
			winCondition();
		} else if (event == FieldEvents.UNMARK) {
			squareButton.getSquare().getVisualButton().applyDefaultStyle(squareButton.getSquare());
		} else {
			if (event == FieldEvents.EXPLOSION) {
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {

						if (hiddenField[i][j].getMine()) {
							hiddenField[i][j].setOpen();
							hiddenField[i][j].getVisualButton().applyExplosionStyle(hiddenField[i][j]);
						}
					}
				}

				JOptionPane.showMessageDialog(null, "Você perdeu!");
				System.exit(0);
			} else {
				for (Square nexts : squareButton.getSquare().getNexts()) {

					if (squareButton.getSquare().getMine()) {
					} else {
						if (nexts.getText().equals(" ") && !nexts.getOpen()) {
							nexts.setOpen();
							nexts.getVisualButton().applyOpenStyle(nexts);

							triggerChain(nexts);
						} else {
							nexts.setOpen();
							nexts.getVisualButton().applyOpenStyle(nexts);
						}
					}
				}

				winCondition();
			}
		}
	}

	private void triggerChain(Square square) {

		for (Square nexts : square.getNexts()) {

			if (square.getMine()) {
			} else {
				if (nexts.getText().equals(" ") && !nexts.getOpen()) {
					nexts.setOpen();
					nexts.getVisualButton().applyOpenStyle(nexts);

					triggerChain(nexts);
				} else {
					nexts.setOpen();
					nexts.getVisualButton().applyOpenStyle(nexts);
				}
			}
		}
	}

	private void winCondition() {

		int openedFields = 0;
		int flagedFields = 0;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				if (hiddenField[i][j].getOpen()) {
					openedFields++;
				} else if (hiddenField[i][j].getFlag()) {
					flagedFields++;
				}
			}
		}

		if (openedFields == 71 && flagedFields == 10) {
			JOptionPane.showMessageDialog(null, "Você venceu!");
			System.exit(0);
		}
	}

	public Square getSquare(int i, int j) {
		return hiddenField[i][j];
	}

	public void linkButton(Consumer<Square> function, int i, int j) {

		function.accept(hiddenField[i][j]);
	}

	public void update(SquareButton squareButton, FieldEvents event) {

		openField(squareButton, event);
	}
}
