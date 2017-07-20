import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;

public class Cell extends JButton implements Serializable{
	public int x, y;
	private Piece piece;
	public Cell(int x, int y){//to get coordinates when clicked
		this.x = x;
		this.y = y;
	}

	public void addPiece(Piece piece){
		this.piece = piece;
		if(piece!=null)
		this.setIcon(piece.getPieceIcon());
	}

	public void removePiece(){
		if(this.piece != null){
		this.piece = null;
		this.setIcon(null);
		}
	}

	public void pseudoAddPiece(Piece piece){
		//System.out.println("Cell pseudoAddPiece : " + piece + " cell : "+x+" " + y);
		this.piece = piece;
	}

	public void pseudoRemovePiece(){
		//System.out.println("Cell pseudoRemovePiece" + " cell : "+x+" " + y);
		if(this.piece != null){
			this.piece = null;
		}
	}

	public Piece getPiece(){
	
		return this.piece;
	}

	public int getColour(){
		//System.out.println("cell="+piece.getColour());
		if(piece!=null)
		return this.piece.getColour();
		return -1;
	}

	public String getPieceId(){
		return this.piece.getPieceId();
	}
	public boolean isEmpty(){
		return (piece==null?true:false);
	}
}