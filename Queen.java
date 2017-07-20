import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

public class Queen extends Piece{
	public Queen(int x, int y, int colour){
		super(x, y, colour);
		Icon ico;
		if(colour==0){
			ico=new ImageIcon("Images\\White_Queen.png");
		}else{
			ico=new ImageIcon("Images\\Black_Queen.png");
		}
		this.pieceIcon=ico;
		this.pieceId="QUEEN";
	}
	
	
	public ArrayList<Cell> getValidMoves(Cell boardCells[][], int it, int jt, boolean retFlag){
		//System.out.println("----------------------***---------------------");
		//System.out.println("Chosen cell "+ it + " " + jt+" Piece being " + boardCells[it][jt].getPiece());
		validMoves.clear();// Need to clear, to clear previously stored cells
		pseudoValidMoves.clear();
		int xs[]={+1,-1, 0, 0,+1,-1,+1,-1};
		int ys[]={ 0, 0,+1,-1,+1,+1,-1,-1};
		int i = it, j = jt;
		for(int iit = 0; iit < 8; iit++){
			i += xs[iit]; j += ys[iit];
			while(isValid(i,j) && boardCells[i][j].isEmpty()){
				pseudoValidMoves.add(boardCells[i][j]);
				i += xs[iit]; j += ys[iit];
			}
			if(isValid(i,j) && boardCells[i][j].getColour()!=colour){
				pseudoValidMoves.add(boardCells[i][j]);
			}
			i = it; j = jt;
		}
		//for(i = 0; i < pseudoValidMoves.size(); i++){
		//	System.out.println("Pseudovalid "+pseudoValidMoves.get(i).x+" "+pseudoValidMoves.get(i).y);
		//}
		if(retFlag) return pseudoValidMoves;
		validMoves = checkFreeMoves(boardCells,it,jt);

		return validMoves;
	}

	// to be modified later as per convenience
	public void move(int dstx, int dsty){
		this.x = x;
		this.y = y;
	}
}