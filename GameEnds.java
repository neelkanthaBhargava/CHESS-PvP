import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.imageio.ImageIO;
import java.io.*;


public class GameEnds extends JFrame implements ActionListener, Serializable
{
	private JPanel dialogPanel, buttonsPanel;
	private JButton newGame, nextGame;
	private Board board;
	private String dialogString = "What do you want to do now?";
	private JLabel resultText, dialogText;
	public GameEnds(Board board){
		
		this.board = board;
		setSize(300,200);
		dialogPanel = new JPanel();
		buttonsPanel = new JPanel();
		
		resultText = new JLabel();
		dialogText = new JLabel(dialogString);
		dialogPanel.add(resultText);
		dialogPanel.add(dialogText);
		dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		newGame = new JButton("New Game");
		nextGame = new JButton("Next Game");
		newGame.addActionListener(this);
		nextGame.addActionListener(this);
		buttonsPanel.add(newGame); buttonsPanel.add(nextGame);
		setLayout(new BorderLayout());
		add(dialogPanel,BorderLayout.CENTER); add(buttonsPanel,BorderLayout.SOUTH);
		setIcon();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setVisible(true);
	}
	public void endGame(String result){
		resultText.setText(result+"!");
		board.setVisibleFalse();
		
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == newGame){
			PlayerNames playerNames = new PlayerNames(board);
        	board.boardReset();
		}
		else{
			board.boardReset();
        	board.setPlayerDetailsShowBoard(board.getPlayer2Name(),board.getPlayer1Name(), board.getTimeLimit());
		}
		setVisible(false);
		board.setVisibleTrue();
	}
		void setIcon() {
		setResizable(false);
		try{
			setIconImage(ImageIO.read(new File("Chess_icon.png")));//throws IOException sets icon for frame
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}