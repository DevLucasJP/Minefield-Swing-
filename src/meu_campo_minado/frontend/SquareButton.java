package meu_campo_minado.frontend;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import meu_campo_minado.backend.FieldEvents;
import meu_campo_minado.backend.Square;
import meu_campo_minado.backend.SquareButtonObserver;

@SuppressWarnings("serial")
public class SquareButton extends JButton implements MouseListener {

	private Square square;

	private Color bgDefault = Color.LIGHT_GRAY;
	private Color bgMark = Color.CYAN;
	private Color bgExplosion = Color.RED;

	private List<SquareButtonObserver> observers = new ArrayList<>();

	public SquareButton(Square square) {
		
		this.square = square;
		addMouseListener(this);
		square.setVisualButton(this);

		applyDefaultStyle(square);
	}
	
	public Square getSquare() {
		return square;
	}

	public void registerObserver(SquareButtonObserver observer) {
		observers.add(observer);
	}

	public void notifyObsevers(FieldEvents event) {

		for (SquareButtonObserver observer : observers) {

			if (event == FieldEvents.OPEN) {
				observer.update(this, FieldEvents.OPEN);
			} else if (event == FieldEvents.MARK) {
				observer.update(this, FieldEvents.MARK);
			} else if (event == FieldEvents.UNMARK) {
				observer.update(this, FieldEvents.UNMARK);
			} else if (event == FieldEvents.EXPLOSION) {
				observer.update(this, FieldEvents.EXPLOSION);
			}
		}
	}

	public void applyDefaultStyle(Square square) {

		setBackground(bgDefault);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}

	public void applyOpenStyle(Square square) {

		setBackground(bgDefault);
		setBorder(BorderFactory.createBevelBorder(2));
		setText(square.getText());
	}

	public void applyMarkStyle(Square square) {

		setBackground(bgMark);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("F");
	}

	public void applyExplosionStyle(Square square) {

		setBackground(bgExplosion);
		setBorder(BorderFactory.createBevelBorder(0));
		setText(square.getText());
	}
	
	public void applyNextsStyle (Square next) {
		
		this.square = next;
		applyOpenStyle(square);
	}

	public void mousePressed(MouseEvent e) {
		
		if (e.getButton() == 3 && !square.getOpen() && !square.getFlag()) {
			square.setFlag();
			applyMarkStyle(square);
			notifyObsevers(FieldEvents.MARK);
		} else if (e.getButton() == 3 && square.getFlag()) {
			square.setFlag();
			applyDefaultStyle(square);
			notifyObsevers(FieldEvents.UNMARK);
		} else if (e.getButton() == 1 && !square.getFlag()) {
			if (!square.getMine()) {
				if (square.getText().equals(" ")) {
					square.setOpen();
					applyOpenStyle(square);
					notifyObsevers(FieldEvents.OPEN);
				} else {
					square.setOpen();
					applyOpenStyle(square);
				}
			} else {
				square.setOpen();
				applyExplosionStyle(square);
				notifyObsevers(FieldEvents.EXPLOSION);
			}
		}
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
