import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

public class Knight extends Piece implements Serializable{
	public Knight(int x, int y, int colour){
		super(x, y, colour);
		Icon ico;
		if(colour==0){
			ico=new ImageIcon("Images\\White_Knight.png");
		}else{
			ico=new ImageIcon("Images\\Black_Knight.png");
		}
		this.pieceIcon=ico;
		this.pieceId="KNIGHT";
	}
	
	
	public ArrayList<Cell> getValidMoves(Cell boardCells[][], int it, int jt, boolean retFlag){
		//System.out.println("----------------------***---------------------");
		//System.out.println("Chosen cell "+ it + " " + jt+" Piece being " + boardCells[it][jt].getPiece());
		validMoves.clear();// Need to clear, to clear previously stored cells
		pseudoValidMoves.clear();
		int xs[]={+2,+2,-2,-2,-1,+1,+1,-1};
		int ys[]={-1,+1,+1,-1,+2,+2,-2,-2};
		int i = it, j = jt;
		for(int iit = 0; iit < 8; iit++){
			i += xs[iit]; j += ys[iit];
			if(isValid(i,j)){
				if(boardCells[i][j].isEmpty() || boardCells[i][j].getColour() != colour){
					pseudoValidMoves.add(boardCells[i][j]);
				}
			}
			i = it; j = jt;
		}
		//for(i = 0; i < pseudoValidMoves.size(); i++){
			//System.out.println("Pseudovalid "+pseudoValidMoves.get(i).x+" "+pseudoValidMoves.get(i).y);
		//}
		if(retFlag) return pseudoValidMoves;
		validMoves = checkFreeMoves(boardCells,it,jt);
		//for(i = 0; i < validMoves.size(); i++){
		//	//System.out.println("Pseudovalid "+validMoves.get(i).x+" "+validMoves.get(i).y);
		//}
		return validMoves;
	}

	// to be modified later as per convenience
	public void move(int dstx, int dsty){
		this.x = x;
		this.y = y;
	}
}