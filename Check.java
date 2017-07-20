import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.util.*;

class Check{
    private static Board board;
    public static boolean check(Cell boardCells[][],int color){
        King k;
        if(color == 0){
            k = board.w0;
        }else k=board.b0;
        int xs[]={+1,-1, 0, 0,+1,-1,+1,-1};
		int ys[]={ 0, 0,+1,-1,+1,+1,-1,-1};
        int i=k.x,j=k.y;
		//System.out.println("King location "+i + " " + j );
        boolean flag=false;
        for(int it=0;it<8;it++){
            int x=i+xs[it],y=j+ys[it];
            while(k.isValid(x,y) && flag==false){
                if(!boardCells[x][y].isEmpty() && boardCells[x][y].getColour()!=k.getColour()){
                    Piece p = boardCells[x][y].getPiece();
                    switch(p.getPieceId()){
                        case "PAWN":if(it>=4 && it<8){
                                        if(x==i+xs[it] && y==j+ys[it])
                                        flag=true;
										//System.out.println("Found the reason for check: "+"PAWN" );
                                    }
                                    break;
                        case "QUEEN":flag=true;
						//System.out.println("Found the reason for check: "+"QUEEN" );
                                    break;
                        case "BISHOP":if(it>=4 && it<8){flag=true;}
						//System.out.println("Found the reason for check: "+"BISHOP" );
                                    break;
                        case "ROOK":if(it>=0 && it<4){flag=true;}
						//System.out.println("Found the reason for check: "+"ROOK" );
                                    break;
                    }
                    break;
                }else if(!boardCells[x][y].isEmpty() && boardCells[x][y].getColour()==k.getColour()){
                            break;
                }
            
                x=x+xs[it];
                y=y+ys[it];
            }
        }
        if(flag==false){
            int kxs[]={+2,+2,-2,-2,-1,+1,+1,-1};
		    int kys[]={-1,+1,+1,-1,+2,+2,-2,-2};
            int x,y;
		    for(int iit = 0; iit < 8; iit++){
			     x= i+kxs[iit]; y = j+kys[iit];
			    if(k.isValid(x,y)){
				    if(!boardCells[x][y].isEmpty() && boardCells[x][y].getColour() != k.getColour() && boardCells[x][y].getPiece().getPieceId().equals("KNIGHT")){
					    flag = true;
						//System.out.println("Found the reason for check: "+"KNIGHT" );
                        break;
				    }
			    }
			    
		    }
        }
        return flag;
    }
    public static void setBoardObject(Board boardRef){
        board = boardRef;
    }
}
