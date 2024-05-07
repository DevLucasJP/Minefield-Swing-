package meu_campo_minado.frontend;

import javax.swing.JFrame;

import meu_campo_minado.backend.Field;

@SuppressWarnings("serial")
public class Window extends JFrame {

	public Window() {

		Field field = new Field();
		FieldView fieldView = new FieldView(field);
		add(fieldView);

		setTitle("Campo Minado");
		setSize(250, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {

		new Window();
	}
}
