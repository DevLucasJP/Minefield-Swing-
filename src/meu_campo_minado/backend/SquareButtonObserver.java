package meu_campo_minado.backend;

import meu_campo_minado.frontend.SquareButton;

public interface SquareButtonObserver {
	
	public void update(SquareButton squareButton, FieldEvents event);
}
