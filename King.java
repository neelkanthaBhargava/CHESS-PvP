import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

public class King extends Piece implements Serializable{
	public King(int x, int y, int colour){
		super(x, y, colour);
		Icon ico;
		if(colour==0){
			ico=new ImageIcon("Images\\White_King.png");
		}else{
			ico=new ImageIcon("Images\\Black_King.png");
		}
		this.pieceIcon=ico;
		this.pieceId="KING";
	}
	
	
	public ArrayList<Cell> getValidMoves(Cell boardCells[][], int it, int jt, boolean retFlag){
		validMoves.clear();
		pseudoValidMoves.clear();
		int xs[]={+1,-1, 0, 0,+1,-1,+1,-1};
		int ys[]={ 0, 0,+1,-1,+1,+1,-1,-1};
		int i = it, j = jt;        //System.out.println("ln26");

		for(int iit = 0; iit < 8; iit++){
            //System.out.println("ln29 "+iit);
			i += xs[iit]; j += ys[iit];
			if(isValid(i,j) && boardCells[i][j].isEmpty()){
				pseudoValidMoves.add(boardCells[i][j]);
				//System.out.println("ln33 "+iit);
			}else{
			    if(isValid(i,j) && boardCells[i][j].getColour()!=colour){
				    pseudoValidMoves.add(boardCells[i][j]);//System.out.println("ln36");
			    }
            }
			i = it; j = jt;
		}
		if(retFlag) return pseudoValidMoves;
        validMoves = checkFreeMoves(boardCells,it,jt);
		return validMoves;
	}

	// to be modified later as per convenience
	public void move(int dstx, int dsty){
		this.x = x;
		this.y = y;
		flag = 1;
	}

}