import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {
	
	private Controller controller;
	
	private JPanel header,gameArea, footer, turnPanel, xScorePanel, oScorePanel;
	private JLabel xScore, oScore, turnCounter, currentPlayerLabel, playerLabel, playerLabel2, xLabel, oLabel;
	GameButton b1, b2, b3, b4, b5, b6, b7, b8, b9;
	GameButton[] gameButtons = {b1, b2, b3, b4, b5, b6, b7, b8, b9};
	
	LogoPanel lp;
	
	private final int PADDING = 20;
	
	public View(){
		
		controller = new Controller(this);
		
		/* Making components */
		
		//Panels
		header = new JPanel();
		gameArea = new JPanel();
		footer = new JPanel();
		turnPanel = new JPanel();
		xScorePanel = new JPanel();
		oScorePanel = new JPanel();
		lp = new LogoPanel();
		
		//Labels
		playerLabel = new JLabel("Player ");
		playerLabel2 = new JLabel("Player ");
		xScore = new JLabel(" Score: 0");
		oScore = new JLabel(" Score: 0");
		xLabel = new JLabel("X");
		oLabel = new JLabel("O");
		xLabel.setForeground(Color.BLUE);
		oLabel.setForeground(Color.RED);
		
		turnCounter = new JLabel("Next player: ");
		currentPlayerLabel = new JLabel("X");
		
		//Set up for buttons
		for(int i = 0; i<gameButtons.length ; i++) {
			gameButtons[i] = new GameButton();
			gameButtons[i].addActionListener(controller);
			gameArea.add(gameButtons[i]);
		}
		
		//Layouts
		header.setLayout(new BorderLayout());
		gameArea.setLayout(new GridLayout(3,3));
		this.setLayout(new BorderLayout());
		
		//Adding components to HEADER
		header.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
		header.setPreferredSize(new Dimension(500, 80));
		
		xScorePanel.setLayout(new FlowLayout());
		oScorePanel.setLayout(new FlowLayout());
		
		xScorePanel.add(playerLabel);
		xScorePanel.add(xLabel);
		xScorePanel.add(xScore);
		
		oScorePanel.add(playerLabel2);
		oScorePanel.add(oLabel);
		oScorePanel.add(oScore);
		
		//header.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.NORTH);
		header.add(xScorePanel, BorderLayout.WEST);
		header.add(lp, BorderLayout.CENTER); //REPLACE WITH LOGO
		header.add(oScorePanel, BorderLayout.EAST);
		
		//Adding components to GAME AREA
		gameArea.setBorder(new EmptyBorder(0, PADDING, PADDING, PADDING));
		
		//Adding components to turnPanel
		turnPanel.setLayout(new FlowLayout());
		turnPanel.add(turnCounter);
		turnPanel.add(currentPlayerLabel);
		setCurrentPlayerLabelColor(Color.blue);
		
		//Adding components to FOOTER
		footer.setBorder(new EmptyBorder(0, PADDING, PADDING, PADDING));
		footer.add(turnPanel);
		
		this.add(header, BorderLayout.NORTH);
		this.add(gameArea, BorderLayout.CENTER);
		this.add(footer, BorderLayout.SOUTH);
		this.setSize(500, 500);
		this.setMinimumSize(new Dimension(350, 350));
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new View();
	}
	
	//Sets color for current player
	private void setCurrentPlayerLabelColor(Color c) {
		currentPlayerLabel.setForeground(c);
	}

	@Override
	public void update(Observable o, Object arg) {
		
		GameLogic gl = (GameLogic) o;
		currentPlayerLabel.setText(""+gl.getNextPlayer());
		
		if(arg == "end") {
			System.out.println("Game ended");
			resetGame(gl);
		}else {
			((GameButton) arg).currentPlayer = gl.getPlayer();
			((GameButton) arg).setForeground(gl.getColor());
			setCurrentPlayerLabelColor(gl.getNextColor());
		}
		
	}
	
	//Reset game function
	private void resetGame(GameLogic gl) {
		
		JOptionPane.showMessageDialog(gameArea, gl.getWinner() + " won the game");
		
		//Reset game field
		for(int i = 0; i<gameButtons.length ; i++) {
			gameButtons[i].currentPlayer = "";
			gameButtons[i].repaintButton();
		}
		
		turnCounter.setText("Next player: ");
		currentPlayerLabel.setText("X");
		setCurrentPlayerLabelColor(Color.blue);
		
		//Updating player score
		xScore.setText("Score: " +gl.getPlayerXWins());
		oScore.setText("Score: " +gl.getPlayerOWins());
	}

	public GameButton[] getAllButtons() {
		return gameButtons;
	}
}

//GameButton custom component
class GameButton extends JButton {
	
	String currentPlayer;
	
	public GameButton(){
		currentPlayer = "";
	}
	
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(String s) {
		currentPlayer = s;
	}
	
	public void repaintButton() {
		this.repaint();
	}
	
	protected void paintComponent(Graphics gr) {
		
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D)gr;
		
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g.setRenderingHints(rh);
		
	    //Text growing with window
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D bounds = fm.getStringBounds(currentPlayer, g);
		
		int width = g.getFontMetrics().stringWidth(currentPlayer);
		int height = g.getFontMetrics().getHeight() / 2;
		int addedWidth = 0;
		
		if(this.getWidth() > this.getHeight()) { //WIDE WINDOW
			g.setFont(new Font("Sans", Font.PLAIN, this.getHeight()/3*2));
			g.drawString(currentPlayer, this.getWidth()/2 - (this.getHeight()/3*2)/2+(width) + addedWidth, this.getHeight()/2 + (this.getHeight()/3*2)/2 - (height / 2));

		}else { //HIGH WINDOW
			g.setFont(new Font("Sans", Font.PLAIN, this.getWidth()/3*2));
			g.drawString(currentPlayer, this.getWidth()/2 - (this.getWidth()/3*2)/2+ (width/2) + addedWidth, this.getHeight()/2 + (this.getWidth()/3*2)/2 - width / 2);
		}
		
		
	}
	
}

//Custom logo panel
class LogoPanel extends JPanel {
	
	protected void paintComponent(Graphics gr) {
		
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D)gr;
		
		//Kod från: https://jvm-gaming.org/t/loading-images-from-a-projects-directory/38442
		Image img = null;
		File file = new File("images/tttlogo.png"); //needs to be an "image" folder in the project folder
		try {
		  img = ImageIO.read(new File(file.toURI()));
		} catch (Exception e) {
		    
		}
		
		g.drawImage(img, this.getWidth()/2 - 15, 0, this.getHeight(), this.getHeight(), null);
		
	}
	
}
