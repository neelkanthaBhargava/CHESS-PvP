import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.imageio.ImageIO;
import java.io.*;

public class PlayerNames extends JFrame implements ActionListener,Serializable,ChangeListener{
	private JPanel textFieldInputPanel;
	private JLabel playerOneLabel, playerTwoLabel;
	private JTextField playerOneNameTextField, playerTwoNameTextField;
	private JButton submitButton;
	private JSlider slider;
	private int timeLimit = 5;
	private Board board;
	
	public PlayerNames(Board board){
		textFieldInputPanel = new JPanel();
		playerOneLabel = new JLabel("Enter Player One Name: ");
		playerTwoLabel = new JLabel("Enter Player Two Name: ");
		playerOneNameTextField = new JTextField("PlayerOne");
		playerTwoNameTextField = new JTextField("PlayerTwo");
		submitButton = new JButton("Submit");
		slider = new JSlider(JSlider.HORIZONTAL, 1, 60, 5);
		this.board = board;
		submitButton.addActionListener(this);
		slider.setMinorTickSpacing(1);  
		slider.setMajorTickSpacing(10);  
		slider.setPaintTicks(true);  
		slider.setPaintLabels(true);
		slider.addChangeListener(this);
		textFieldInputPanel.add(playerOneLabel);
		textFieldInputPanel.add(playerOneNameTextField);
		textFieldInputPanel.add(playerTwoLabel);
		textFieldInputPanel.add(playerTwoNameTextField);
		textFieldInputPanel.add(slider);
		textFieldInputPanel.setLayout(new BoxLayout(textFieldInputPanel, BoxLayout.Y_AXIS));
		BorderLayout dialogBoxLayout = new BorderLayout();
		setLayout(dialogBoxLayout);
		setSize(300,200);
		//removeMinMaxClose(this);
		add(textFieldInputPanel, BorderLayout.NORTH);
		add(submitButton, BorderLayout.SOUTH);
		setIcon();
		//setUndecorated(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setVisible(true);
		
	}
	//@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (!source.getValueIsAdjusting()) {
			timeLimit = (int)source.getValue();
		}
	}
	
	public void actionPerformed(ActionEvent actionEvent){
		if(actionEvent.getSource() == submitButton){
			String playerOneName = playerOneNameTextField.getText();
			String playerTwoName = playerTwoNameTextField.getText();
			setVisible(false);
			board.setPlayerDetailsShowBoard(playerOneName, playerTwoName, timeLimit);
		}
		else{
			//System.out.println("--************************//////////////////////////////");
			JSlider source = (JSlider)actionEvent.getSource();
			if (!source.getValueIsAdjusting()) {
			timeLimit = (int)source.getValue();
			//System.out.println("--************************//////////////////////////////");
		}
		}
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