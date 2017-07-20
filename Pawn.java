import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

public class Pawn extends Piece implements Serializable, ActionListener/*, WindowListener */{
	
	private Dialog promoterFrame;
	private Label headerLabel;
	private Label statusLabel;
	private Panel controlPanel;
	private String newPieceId = "QUEEN";
	private Choice newPiece;
	private Cell squares[][];
	
	public Pawn(int x, int y, int colour){
		super(x, y, colour);
		Icon ico;
		if(colour==0){
			ico=new ImageIcon("Images\\White_Pawn.png");
		}else{
			ico=new ImageIcon("Images\\Black_Pawn.png");
		}
		this.pieceIcon=ico;
		this.pieceId="PAWN";
	}
	
	
	public ArrayList<Cell> getValidMoves(Cell boardCells[][], int it, int jt, boolean retFlag){
		//System.out.println("----------------------***---------------------");
		//System.out.println("Chosen cell "+ it + " " + jt+" Piece being " + boardCells[it][jt].getPiece());
		validMoves.clear();// Need to clear, to clear previously stored cells
		pseudoValidMoves.clear();
		if(flag == 0){
			//System.out.println("Colour of the piece is : "+this.colour+" "+it+" "+jt);
			if(colour == 0 && boardCells[it-2][jt].isEmpty() && boardCells[it-1][jt].isEmpty()){
				pseudoValidMoves.add(boardCells[it-2][jt]);
			}
			if(colour == 1 && boardCells[it+2][jt].isEmpty() && boardCells[it+1][jt].isEmpty()){
				pseudoValidMoves.add(boardCells[it+2][jt]);
			}
		}
		if(colour == 0){
			//System.out.println("Checking moves for whitepiece");
			if(isValid(it-1,jt) && boardCells[it-1][jt].isEmpty()){//check for top coding white = 0
				pseudoValidMoves.add(boardCells[it-1][jt]);
			}
			if(isValid(it-1,jt+1) && !boardCells[it-1][jt+1].isEmpty() && boardCells[it-1][jt+1].getColour()!=colour){
				pseudoValidMoves.add(boardCells[it-1][jt+1]);
			}
			if(isValid(it-1,jt-1) && !boardCells[it-1][jt-1].isEmpty() && boardCells[it-1][jt-1].getColour()!=colour){
				pseudoValidMoves.add(boardCells[it-1][jt-1]);
			}
		}
		else{
			if(isValid(it+1,jt) && boardCells[it+1][jt].isEmpty()){//check for top coding white = 0
				//System.out.println("ln 49");
				pseudoValidMoves.add(boardCells[it+1][jt]);
			}
			if(isValid(it+1,jt-1) && !boardCells[it+1][jt-1].isEmpty() && boardCells[it+1][jt-1].getColour()!=colour ){
				pseudoValidMoves.add(boardCells[it+1][jt-1]);
			}
			if(isValid(it+1,jt+1) && !boardCells[it+1][jt+1].isEmpty() && boardCells[it+1][jt+1].getColour()!=colour ){
				pseudoValidMoves.add(boardCells[it+1][jt+1]);
			}
		}
		for(int i = 0; i < pseudoValidMoves.size(); i++){
			//System.out.println("Pseudovalid "+pseudoValidMoves.get(i).x+" "+pseudoValidMoves.get(i).y);
		}
		if(retFlag) return pseudoValidMoves;
		validMoves = checkFreeMoves(boardCells,it,jt);

		return validMoves;
	}

	// to be modified later as per convenience
	public void move(int dstx, int dsty){
		/*this.x = x;
		this.y = y;
		if(x == 1 || x == 8){
			promotePawn();
		}
		flag = 1;*/
	}
	public void promotePawn(JFrame boardFrame, Cell squares[][]){
		promoterFrame = new Dialog(boardFrame);
		promoterFrame.setSize(400,250);
		GridLayout gridLayout = new GridLayout(3,1);
		promoterFrame.setLayout(gridLayout);
		//promoterFrame.addWindowListener(this); 
		headerLabel = new Label();
		headerLabel.setAlignment(Label.CENTER);
		statusLabel = new Label();        
		statusLabel.setAlignment(Label.CENTER);
		statusLabel.setSize(350,100);
		controlPanel = new Panel();
		controlPanel.setLayout(new FlowLayout());
		promoterFrame.add(headerLabel);
		promoterFrame.add(controlPanel);
		promoterFrame.add(statusLabel);
		headerLabel.setText("Choose a piece you want your pawn to be promoted to : "); 
		newPiece = new Choice();
		newPiece.add("KNIGHT");
		newPiece.add("BISHOP");
		newPiece.add("ROOK");
		newPiece.add("QUEEN");
		Button showButton = new Button("Select");
		showButton.addActionListener(this);
		promoterFrame.add(newPiece);
		promoterFrame.add(showButton);
		promoterFrame.setVisible(true);
		this.squares = squares;
		
	}
	public void actionPerformed(ActionEvent e) {     
        newPieceId = newPiece.getItem(newPiece.getSelectedIndex());
		statusLabel.setText("Piece Selected: " + newPieceId);
		if(colour == 0){
			switch(newPieceId){
                        case "KNIGHT":
							Knight p7 = new Knight(x,y,0);
							squares[x][y].addPiece(p7);
                            break;
                        case "QUEEN":
						Queen p1 = new Queen(x,y,0);
							squares[x][y].addPiece(p1);
							break;
                        case "BISHOP":
						Bishop p3 = new Bishop(x,y,0);
							squares[x][y].addPiece(p3);
							break;
                        case "ROOK":
							Rook p5 = new Rook(x,y,0);
							squares[x][y].addPiece(p5);
						break;
			}
		}
		else{
			switch(newPieceId){
                        case "KNIGHT":
							Knight p7 = new Knight(x,y,1);
							squares[x][y].addPiece(p7);
                            break;
                        case "QUEEN":
						Queen p1 = new Queen(x,y,1);
							squares[x][y].addPiece(p1);
							break;
                        case "BISHOP":
						Bishop p3 = new Bishop(x,y,1);
							squares[x][y].addPiece(p3);
							break;
                        case "ROOK":
							Rook p5 = new Rook(x,y,1);
							squares[x][y].addPiece(p5);
						break;
			}
		}
		promoterFrame.setVisible(false);
		promoterFrame.dispose();
    }/*
	publ/*ic void windowClosing(WindowEvent windowEvent){
		System.exit(0);
	}*/
}