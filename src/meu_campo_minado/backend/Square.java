package meu_campo_minado.backend;

import java.util.ArrayList;
import java.util.List;

import meu_campo_minado.frontend.SquareButton;

public class Square {

	private String text;
	private boolean open;
	private boolean mine = false;
	private boolean flag;
	private int line;
	private int column;
	private SquareButton visualButton;

	private List<Square> nexts = new ArrayList<>();

	public void setNexts(Square next) {

		nexts.add(next);
	}

	public List<Square> getNexts() {
		return nexts;
	}

	public Square() {
		setText(" ");
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setOpen() {
		this.open = true;
	}

	public boolean getOpen() {
		return open;
	}

	public void setMine() {
		this.mine = true;
	}

	public boolean getMine() {
		return mine;
	}

	public void setFlag() {
		if (!getFlag()) {
			this.flag = true;
		} else {
			this.flag = false;
		}
	}

	public boolean getFlag() {
		return flag;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getLine() {
		return line;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getColumn() {
		return column;
	}

	public void setVisualButton(SquareButton visualButton) {
		this.visualButton = visualButton;
	}

	public SquareButton getVisualButton() {
		return visualButton;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}
}
