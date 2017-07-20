import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

class MenuListener implements ActionListener,Serializable{
   private Board board;
    public MenuListener(Board b){
      this.board = b;
    }

    public void actionPerformed(ActionEvent actionEvent){
		//System.out.println(actionEvent.getSource());
		JMenuItem item = (JMenuItem)(actionEvent.getSource());
    if(item == board.newGame){
      beginNewGame();
    }else if(item == board.nextGame){
      beginNextGame();

    }else if(item == board.saveGame){
      saveGames();
    }else if(item == board.loadGame){
      loadGames();

    }else if(item == board.boardColor){
      changeBoardColor();
    }
    }

    public void beginNewGame(){
        int a=JOptionPane.showConfirmDialog(null,"Do want to save current game?");  
        if(a==JOptionPane.YES_OPTION){  
          if(!saveGames())return;
        }else if(a==JOptionPane.CANCEL_OPTION){
          return;
        }
        PlayerNames playerNames = new PlayerNames(board);
        board.boardReset();
        //board.initialiseBoard();
    }

    public void beginNextGame(){
        int a=JOptionPane.showConfirmDialog(null,"Do want to save current game?");  
        if(a==JOptionPane.YES_OPTION){  
          if(!saveGames())return;
        }else if(a==JOptionPane.CANCEL_OPTION){
          return;
        }
        //PlayerNames playerNames = new PlayerNames(board);
        board.boardReset();
        board.setPlayerDetailsShowBoard(board.getPlayer2Name(),board.getPlayer1Name(), board.getTimeLimit());
    }

    public boolean saveGames(){
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");   
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Saved Games","sav");
        fileChooser.setFileFilter(filter);
 
        int userSelection = fileChooser.showSaveDialog(null);
 
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try{
                FileOutputStream fos = new FileOutputStream(fileToSave.getAbsolutePath()+".sav");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(board);
                oos.close();
                fos.close();
            }
            catch(IOException e){
              e.printStackTrace();
            }
            //System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            return true;
        }
        return false;
    }

    public void loadGames(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Saved Game");   
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Saved Games","sav");
        fileChooser.setFileFilter(filter);
 
        int userSelection = fileChooser.showOpenDialog(null);
 
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try{
                FileInputStream fis = new FileInputStream(fileToSave.getAbsolutePath());
                ObjectInputStream ois = new ObjectInputStream(fis);

                Board boardLoaded=(Board)ois.readObject();
                loadBoard(boardLoaded);
                ois.close();
                fis.close();
            }
            catch(Exception e){
              e.printStackTrace();
            }
            //System.out.println("Save as file: " + fileToSave.getAbsolutePath());
           
        }
       

    }

    public void loadBoard(Board loadBoard){
      int i,j;
        for(i=1;i<9;i++){
            for(j=1;j<9;j++){
                board.getSquare(i,j).removePiece();
            }
        }

        board.setPlayerDetailsShowBoard(loadBoard.getPlayer1Name(),loadBoard.getPlayer2Name(), loadBoard.getTimeLimit());

        for(i=1;i<9;i++){
            for(j=1;j<9;j++){
                board.getSquare(i,j).addPiece(loadBoard.getSquare(i,j).getPiece());
            }
        }
        board.setChance(loadBoard.getChance());
        board.setHashMaps(loadBoard.getWhiteHashMap(),loadBoard.getBlackHashMap());
        //System.out.println("savedTime = "+board.getSaveTime());
        board.timeClockSetSavedDiff(board.getSaveTime());
    }

    public void changeBoardColor(){
      Color white=JColorChooser.showDialog(null,"Choose Color for White Cell",board.whiteColor);
      Color black=JColorChooser.showDialog(null,"Choose Color for White Cell",board.blackColor);
      System.out.println(white);
      System.out.println(black);
      board.setCellColor(white,black);
    }
    
}