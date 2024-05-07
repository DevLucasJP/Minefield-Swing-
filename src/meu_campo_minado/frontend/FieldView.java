package meu_campo_minado.frontend;

import java.awt.GridLayout;

import javax.swing.JPanel;

import meu_campo_minado.backend.Field;
import meu_campo_minado.backend.Square;

@SuppressWarnings("serial")
public class FieldView extends JPanel {

	public FieldView(Field field) {

		setLayout(new GridLayout(9, 9));
//		field.linkButton(square -> add(new SquareButton(square)));
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				
				Square square = field.getSquare(i, j);

				SquareButton visualButton = new SquareButton(square);
				visualButton.registerObserver(field);
				
				field.linkButton(s -> add(visualButton), i, j);
			}
		}
	}
}
