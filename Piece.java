import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

public abstract class Piece implements Serializable
{
	protected Icon pieceIcon;//check for protection
	protected int  x, y, colour;
	protected String pieceId;
	protected int flag = 0; //to denote first turn
	public Piece pieceObj, tempPiece;
	private Cell tempCell;
	
	protected ArrayList<Cell> validMoves = new ArrayList<>();
	protected ArrayList<Cell> pseudoValidMoves = new ArrayList<>();
	
	public Icon getPieceIcon(){
		return pieceIcon;
	}
	
	public abstract ArrayList<Cell> getValidMoves(Cell boardCells[][], int it, int jt, boolean retFlag);
	
	public Piece(int x, int y, int colour){
		
		this.x = x;
		this.y = y;
		this.colour = colour;
		pieceObj = this;
	}
	
	public void setXY(int x,int y){
		this.flag = 1;// sets flag to 1 after first turn, applicable in case of King, Rook and Pawn
		//if(pieceId == "PAWN") move(x, y);
		this.x = x;
		this.y = y;
	}
	public int getColour(){
		return this.colour;
	}
	
	public String getPieceId(){
		return this.pieceId;
	}
	
	protected boolean isValid(int x, int y){
		return (x >= 1 && x <= 8) && (y >= 1 && y<= 8);
	}

	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}

	public abstract void move(int dstx, int dsty);//to define internals
	protected ArrayList<Cell> checkFreeMoves(Cell boardCells[][], int it,int jt){
		//System.out.println("Piece.ln49"+" "+pseudoValidMoves.size());
		int pvmit, sz = pseudoValidMoves.size();
		for(pvmit = 0; pvmit < sz; pvmit++){
			tempCell = pseudoValidMoves.get(pvmit);//a temporary cell stores the cell provided in pseudovalid moves
			boardCells[it][jt].pseudoRemovePiece();//currenltly selected cell, the piece present is removed
			tempPiece = tempCell.getPiece();//if there is a piece in the pseudovalid move
			tempCell.pseudoAddPiece(this);//this piece is added to the temp cell
			this.x = tempCell.x;//new coordinates for the selected piece useful in case of king
			this.y = tempCell.y;
			/*System.out.println("Piece.ln52"+" "+pvmit);
			Console getChar = System.console();
			getChar.readLine();
			//Check()?*/
			//System.out.println(Check.check(boardCells, colour)+" not my problem");
			if(!Check.check(boardCells, colour)){//returns true if check
				//System.out.println("Adding to valid Moves : Declaring true "+" "+tempCell.x+" "+tempCell.y);
				validMoves.add(tempCell);
			}
			tempCell.pseudoRemovePiece();//from the tempcell the piece is removed
			tempCell.pseudoAddPiece(tempPiece);//next the stored piece is added
			boardCells[it][jt].pseudoAddPiece(this);//to the current cell this piece is added back
			this.x = it;//its coordinates are reset
			this.y = jt;
		}
		return validMoves;
	}
}