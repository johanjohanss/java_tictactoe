import java.awt.Color;
import java.util.Observable;
import javax.swing.JButton;

public class GameLogic extends Observable {
	
	private String turn;
	private String color;
	private View view;
	private int playerXWins, playerOWins;
	private String winner;
	
	public GameLogic(View vIn) {
		view = vIn;
		this.addObserver(view);
		turn = "X";
		color = "blue";
		playerXWins = 0;
		playerOWins = 0;
	}
	
	public void buttonClicked(JButton b) {
		setChanged();
		notifyObservers(b);
		switchTurn();
	}
	
	private void switchTurn() {
		//Switching turns
		if(turn == "X") {
			turn = "O";
			color = "red";
		}else if(turn == "O") {
			turn = "X";
			color = "blue";
		}
	}
	
	public void checkForWin(GameButton[] gameButtons) {
		boolean rowMatch = false, colMatch = false, diagonalMatch = false, draw = false;
		
		rowMatch = checkRows(gameButtons, getNextPlayer());
		colMatch = checkCols(gameButtons, getNextPlayer());
		diagonalMatch = checkDiagonals(gameButtons, getNextPlayer());
		
		
		if(rowMatch || colMatch || diagonalMatch) {
			endGame(getNextPlayer());
		}else {
			draw = checkDraw(gameButtons);
		}
		if(draw) {
			endGame("It's a draw! No one");
		}
		
	}
	
	private void endGame(String gameWinner) {
		
		if(gameWinner == "X" || gameWinner == "O") {
			winner = "Player " + gameWinner;
		}else {
			winner = gameWinner;
		}
		
		//Reset turn
		turn = "X";
		color = "blue";
		
		if(gameWinner == "X") {
			playerXWins++;
		}else if(gameWinner == "O") {
			playerOWins++;
		}
		
		setChanged();
		notifyObservers("end");
	}
	
	public String getWinner() {
		return winner;
	}
	
	public String getPlayer() {
		return turn;
	}
	
	public Color getColor() {
		if(color == "blue") {
			return Color.BLUE;
		}
		else{
			return Color.RED;
		}
	}
	
	public Color getNextColor() {
		if(color == "blue") {
			return Color.RED;
		}
		else{
			return Color.BLUE;
		}
	}
	
	public String getNextPlayer() {
		if(turn == "X") {
			return "O";
		}else if(turn == "O") {
			return "X";
		}
		return turn;
	}
	
	public int getPlayerXWins() {
		return playerXWins;
	}
	
	public int getPlayerOWins() {
		return playerOWins;
	}
	
	//Checking winner functions
	private boolean checkRows(GameButton[] gameButtons, String s) {
		if(gameButtons[0].getCurrentPlayer() == s && gameButtons[1].getCurrentPlayer() == s && gameButtons[2].getCurrentPlayer() == s) {
			System.out.println(s + " Won");
			return true;
		}
		if(gameButtons[3].getCurrentPlayer() == s && gameButtons[4].getCurrentPlayer() == s && gameButtons[5].getCurrentPlayer() == s) {
			System.out.println(s + " Won");
			return true;
		}
		if(gameButtons[6].getCurrentPlayer() == s && gameButtons[7].getCurrentPlayer() == s && gameButtons[8].getCurrentPlayer() == s) {
			System.out.println(s + " Won");
			return true;
		}
		return false;
	}
	
	private boolean checkCols(GameButton[] gameButtons, String s) {
		if(gameButtons[0].getCurrentPlayer() == s && gameButtons[3].getCurrentPlayer() == s && gameButtons[6].getCurrentPlayer() == s) {
			System.out.println(s + " Won");
			return true;
		}
		if(gameButtons[1].getCurrentPlayer() == s && gameButtons[4].getCurrentPlayer() == s && gameButtons[7].getCurrentPlayer() == s) {
			System.out.println(s + " Won");
			return true;
		}
		if(gameButtons[2].getCurrentPlayer() == s && gameButtons[5].getCurrentPlayer() == s && gameButtons[8].getCurrentPlayer() == s) {
			System.out.println(s + " Won");
			return true;
		}
		return false;
	}
	
	private boolean checkDiagonals(GameButton[] gameButtons, String s) {
		if(gameButtons[0].getCurrentPlayer() == s && gameButtons[4].getCurrentPlayer() == s && gameButtons[8].getCurrentPlayer() == s) {
			System.out.println(s + " Won");
			return true;
		}
		if(gameButtons[6].getCurrentPlayer() == s && gameButtons[4].getCurrentPlayer() == s && gameButtons[2].getCurrentPlayer() == s) {
			System.out.println(s + " Won");
			return true;
		}
		return false;
	}
	
	//Check draw function
	private boolean checkDraw(GameButton[] gameButtons) {
		//Check if any square is empty
		if(gameButtons[0].getCurrentPlayer() == "" || gameButtons[1].getCurrentPlayer() == "" ||
				gameButtons[2].getCurrentPlayer() == "" || gameButtons[3].getCurrentPlayer() == "" ||
				gameButtons[4].getCurrentPlayer() == "" || gameButtons[5].getCurrentPlayer() == "" ||
				gameButtons[6].getCurrentPlayer() == "" || gameButtons[7].getCurrentPlayer() == "" ||
				gameButtons[8].getCurrentPlayer() == "")
		{
			//It's not a draw since all squares are not empty
		}else {
			//It's a draw
			return true;
		}
		return false;
	}

}
