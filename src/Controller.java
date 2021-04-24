import java.awt.event.*;

import javax.swing.JButton;

public class Controller extends WindowAdapter implements ActionListener {
	
	private GameLogic gl;
	private View view;
	
	public Controller(View vIn) {
		view = vIn;
		gl = new GameLogic(view);
	}
	
	public void actionPerformed(ActionEvent e) {
		var b = (GameButton)e.getSource();
		
		//Check if button is already clicked
		if(b.getCurrentPlayer() != "X" && b.getCurrentPlayer() != "O") {
			gl.buttonClicked(b);
			gl.checkForWin(getAllButtons());
		}
		else {
			//Button is already clicked
		}
	}
	
	public GameButton[] getAllButtons() {
		return view.getAllButtons();
	}
}
