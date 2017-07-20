//package com.neelcg.chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.util.*;


class Board implements ActionListener,Serializable{ 
    private JFrame boardFrame;
    private JPanel boardPanel, player1Panel, player2Panel, menuPanel, timePanel;
    private Cell squares[][];
    private JLabel player1, player2, timeLabel, turnLabel, checkLabel;
    private ArrayList<Cell> highlightedCells = new ArrayList<>();
    private int chance;
    private Cell selectedCell;
    private HashMap<String, Integer> blackPiece,whitePiece;
    JMenuBar jmb;
    JMenu file,edit;
    JMenuItem newGame,nextGame,saveGame,loadGame,boardColor;
    Color blackColor,whiteColor;
    //private TimeClock timeClockThread;
	private String timeString;
    public King w0,b0;
    private boolean checkFlag;
    private ArrayList<Piece> whitePieceRef;
    private ArrayList<Piece> blackPieceRef;
    private boolean checkMateFlag;
    public GameEnds gameEnder;
    private Long saveTime;
    Board() {

        boardFrame = new JFrame("CHESS");
        BorderLayout boardLayout = new BorderLayout();
        boardFrame.setLayout(boardLayout);
        boardFrame.setSize(800,720);

        //board panel
        boardPanel = new JPanel();
        GridLayout boardPanelLayout= new GridLayout(0,9);
        boardPanel.setLayout(boardPanelLayout);
        //boardPanel.setBounds(10,10,640,640);
        
        //player1Panel panel
        player1Panel = new JPanel();
        FlowLayout player1PanelLayout=new FlowLayout(FlowLayout.CENTER,20,5);
        player1Panel.setLayout(player1PanelLayout);

        //player2Panel panel
        player2Panel = new JPanel();
        FlowLayout player2PanelLayout=new FlowLayout(FlowLayout.CENTER,20,5);
        player2Panel.setLayout(player2PanelLayout);

        //Time panel
        timePanel = new JPanel();
        GridLayout timePanelLayout = new GridLayout(3,0,10,0);
        timePanel.setLayout(timePanelLayout);
		
		//menubar
        initialiseMenuBar();
		boardFrame.setJMenuBar(jmb);

        //detail initialisation
        initialisePlayer();

        //board initialisation
        squares = new  Cell[9][9];
        initialiseBoard();

        //initialise timePanel
        initialiseTimePanel();
        timeString="";

        
        
        chance=0;checkFlag=false;checkMateFlag=false;
        boardFrame.add(player1Panel,BorderLayout.NORTH);
        boardFrame.add(player2Panel,BorderLayout.SOUTH);
        boardFrame.add(boardPanel,BorderLayout.CENTER);
        boardFrame.add(timePanel,BorderLayout.EAST);
		setIcon();
		closeWindow();
    }

    public void initialiseBoard(){
        JLabel letteredLabel[] = new  JLabel[9];
        JLabel numberedLabel[] = new  JLabel[8];
        
        int i = 0,j = 0;int white=1;
		whiteColor = new Color(204,153,255);
		blackColor = new Color(102,0,102);
        for(i = 0; i<9 ; i++){
            if(i == 0){
                int k = 0;
                letteredLabel[0] = new JLabel("  ",SwingConstants.CENTER);
                boardPanel.add(letteredLabel[0]);
                for(k = 1; k<9 ; k++){
                    letteredLabel[k] = new JLabel(""+(char)('a'+k-1),SwingConstants.CENTER);
                    boardPanel.add(letteredLabel[k]);
                }
            }
            else{
                int k = 0;
                for( j = 0; j<9 ; j++){
                    if(j==0){
                        
                            numberedLabel[k] = new JLabel(""+(9-i),SwingConstants.CENTER);
                            boardPanel.add(numberedLabel[k++]);
                
                    }
                    else{
                        squares[i][j] = new Cell(i,j);
                        if((i%2!=0 && (i*j)%2==1) || (i%2==0 && j%2==0)){
                            squares[i][j].setBackground(whiteColor);
                            
                        }else if((i%2!=0 && (i*j)%2==0) || (i%2==0 && j%2==1)){
                            squares[i][j].setBackground(blackColor);
                        }
                        squares[i][j].addActionListener(this);
						//squares[i-1][j-1].setCell(i, j);
                        boardPanel.add(squares[i][j]);
                    }
                }
            }   
        }

        //initialising pieces
        initialiseWhitePieces();
        initialiseBlackPieces();
        initialiseHashMAP();
        Check.setBoardObject(this);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		boardFrame.setLocation(dim.width/2-boardFrame.getSize().width/2, dim.height/2-boardFrame.getSize().height/2);
		boardFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
    }

    public void initialiseWhitePieces(){
        int i=1;
        whitePieceRef = new ArrayList<Piece>();
        for(i=1;i<9;i++){
            Pawn p = new Pawn(7,i,0);
            squares[7][i].addPiece(p);
            whitePieceRef.add(p);
        }
		Queen p1 = new Queen(8,4,0);
		squares[8][4].addPiece(p1);
        whitePieceRef.add(p1);
        w0 = new King(8,5,0);
        whitePieceRef.add(w0);
		squares[8][5].addPiece(w0);
		Bishop p2 = new Bishop(8,3,0);
		squares[8][3].addPiece(p2);
        whitePieceRef.add(p2);
		Bishop p3 = new Bishop(8,6,0);
		squares[8][6].addPiece(p3);
        whitePieceRef.add(p3);
		Rook p4 = new Rook(8,1,0);
		squares[8][1].addPiece(p4);
        whitePieceRef.add(p4);
		Rook p5 = new Rook(8,8,0);
		squares[8][8].addPiece(p5);
        whitePieceRef.add(p5);
		Knight p6 = new Knight(8,2,0);
		squares[8][2].addPiece(p6);
        whitePieceRef.add(p6);
		Knight p7 = new Knight(8,7,0);
		squares[8][7].addPiece(p7);
        whitePieceRef.add(p7);
    }

    public void initialiseBlackPieces(){
        int i=1;
        blackPieceRef = new ArrayList<Piece>();
        for(i=1;i<9;i++){
            Pawn p=new Pawn(2,i,1);
            squares[2][i].addPiece(p);
            blackPieceRef.add(p);
        }
		Queen p1 = new Queen(1,4,1);
		squares[1][4].addPiece(p1);
        blackPieceRef.add(p1);
        b0 = new King(1,5,1);
		squares[1][5].addPiece(b0);
        blackPieceRef.add(b0);
		Bishop p2 = new Bishop(1,3,1);
		squares[1][3].addPiece(p2);
        blackPieceRef.add(p2);
		Bishop p3 = new Bishop(1,6,1);
		squares[1][6].addPiece(p3);
        blackPieceRef.add(p3);
		Rook p4 = new Rook(1,1,1);
		squares[1][1].addPiece(p4);
        blackPieceRef.add(p4);
		Rook p5 = new Rook(1,8,1);
		squares[1][8].addPiece(p5);
        blackPieceRef.add(p5);
		Knight p6 = new Knight(1,2,1);
		squares[1][2].addPiece(p6);
        blackPieceRef.add(p6);
		Knight p7 = new Knight(1,7,1);
		squares[1][7].addPiece(p7);
        blackPieceRef.add(p7);
    }

    public void initialiseHashMAP(){
        whitePiece = new HashMap<String,Integer>();
        blackPiece = new HashMap<String,Integer>();
        whitePiece.put("PAWN",8);
        blackPiece.put("PAWN",8);
        whitePiece.put("KING",1);
        blackPiece.put("KING",1);
        whitePiece.put("QUEEN",1);
        blackPiece.put("QUEEN",1);
        whitePiece.put("ROOK",2);
        blackPiece.put("ROOK",2);
        whitePiece.put("KNIGHT",2);
        blackPiece.put("KNIGHT",2);
        whitePiece.put("BISHOP",2);
        blackPiece.put("BISHOP",2);
    }

    public void initialiseMenuBar(){
        

        file = new JMenu("File");
        edit = new JMenu("Edit");

        newGame = new JMenuItem("New Game");
        nextGame = new JMenuItem("Next Game");
        saveGame = new JMenuItem("Save Game");
        loadGame = new JMenuItem("Load Game");
        boardColor = new JMenuItem("Board Colour");

        //listeners
        MenuListener ml = new MenuListener(this);
        newGame.addActionListener(ml);
        nextGame.addActionListener(ml);
        saveGame.addActionListener(ml);
        loadGame.addActionListener(ml);
        boardColor.addActionListener(ml);

        file.add(newGame);
        file.add(nextGame);
        file.addSeparator();
        file.add(saveGame);
        file.addSeparator();
        file.add(loadGame);
        //file.addSeparator();
        edit.add(boardColor);
        jmb=new JMenuBar();
        jmb.add(file);
        jmb.add(edit);
    }

    public void initialisePlayer(){
        player1 = new JLabel();
        player1.setFont(new Font("Serif", Font.PLAIN, 15));
        player2 = new JLabel();
        player2.setFont(new Font("Serif", Font.PLAIN, 15));

        player1Panel.add(player1);
        player2Panel.add(player2);

    }

    public void initialiseTimePanel(){
        //JLabel blankLabel = new JLabel();
        timeLabel = new JLabel(" 00:00:00 ");
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        turnLabel = new JLabel(" Player 2"+"'s Turn ");
        turnLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        checkLabel = new JLabel("         ");
        checkLabel.setFont(new Font("Serif", Font.PLAIN, 15));

        //timePanel.add(blankLabel);
        timePanel.add(turnLabel);
        timePanel.add(timeLabel);
        timePanel.add(checkLabel);
    }

    public void closeWindow(){
		boardFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//Taking control over closing operation
		boardFrame.addWindowListener(new WindowAdapter() {//adding windowlistener to monitor window operations
			@Override
			public void windowClosing(WindowEvent windowEventObject)//invoking when close button is pressed
			{
				int promptResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?");
				if(promptResult == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			}
		});
	}

	public void boardReset(){
        int i,j;
        for(i=1;i<9;i++){
            for(j=1;j<9;j++){
                
                squares[i][j].removePiece();
            }
        }
        initialiseWhitePieces();
        initialiseBlackPieces();
        initialiseHashMAP();
        chance = 0;
    }

    void setIcon() {
		boardFrame.setResizable(false);
		try{
			boardFrame.setIconImage(ImageIO.read(new File("Chess_icon.png")));//throws IOException sets icon for frame
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

    public void setVisibleFalse(){
		boardFrame.setVisible(false);
	}
	public void setVisibleTrue(){
		boardFrame.setVisible(true);
	}

    public String getPlayer1Name(){
        return player1.getText();
    }

    public String getPlayer2Name(){
        return player2.getText();
    }
    
    public void setTurnLabel(String player){
        turnLabel.setText("    "+player+"'s turn   ");
    }

    public int getTimeLimit(){
		return Chess.timeClockThread.getTimeLimit();
	}

    public void timeClockSetSavedDiff(long diff){
       // System.out.println("before setting = "+Chess.timeClockThread.getSavedDiff());
        Chess.timeClockThread.setSavedDiff(diff);
        //System.out.println("after setting = "+Chess.timeClockThread.getSavedDiff());
    }

	public void setPlayerDetailsShowBoard(String playerOneName, String playerTwoName, int timeLimit){
		player1.setText(playerOneName);
		player2.setText(playerTwoName);
        if(chance == 0){
            setTurnLabel(player2.getText());
        }else setTurnLabel(player1.getText());
		boardFrame.setVisible(true);
        Chess.timeClockThread.setTimeLimit(timeLimit);
		Chess.timeClockThread.resetTime();
		
	}

	public int getChance(){
        return chance;

    }

    public void setChance(int chance){
        this.chance = chance;
        if(chance == 0){
            setTurnLabel(player2.getText());
        }else setTurnLabel(player1.getText());
    }

    public void setSaveTime(long saveTime){
        this.saveTime = saveTime;
    }

    public long getSaveTime(){
        return this.saveTime;
    }

	public HashMap<String, Integer> getWhiteHashMap(){
        return whitePiece;
    }

    public HashMap<String, Integer> getBlackHashMap(){
        return blackPiece;
    }

    public void setHashMaps(HashMap<String, Integer> white, HashMap<String, Integer> black){
        whitePiece = white;
        blackPiece = black;
    }

    void setCellColor(Color white,Color black){
        whiteColor = white;
        blackColor = black;
        for(int i=1;i<9;i++){
            for(int j =1;j<9;j++){
                if((i%2!=0 && (i*j)%2==1) || (i%2==0 && j%2==0)){
                    squares[i][j].setBackground(whiteColor);
                            
                }else if((i%2!=0 && (i*j)%2==0) || (i%2==0 && j%2==1)){
                    squares[i][j].setBackground(blackColor);
                }
            }
        }
    }

    public Cell getSquare(int i,int j){
        return squares[i][j];
    }

    /*public void setTimeClock(TimeClock timeClockThread){
		this.timeClockThread = timeClockThread;
	}*/
	
	public void setTimeClock(String timeString){
		this.timeString = timeString;
		timeLabel.setText("    Time: "+timeString+"  ");
	}
    
	public void actionPerformed(ActionEvent actionEvent){
		Cell tempcell = (Cell)actionEvent.getSource();
		Piece p=tempcell.getPiece();
            //System.out.println(p);
        if(p==null && !highlightedCells.contains(tempcell))return;
		
        if(highlightedCells.contains(tempcell)){
            movePiece(tempcell);
			//System.out.println("178");
        }
		
        else if(p!=null && chance==tempcell.getColour()/* && p.getPieceId().equals("PAWN")*/){
            //Pawn pawnPiece = (Pawn) p;
			//System.out.println("In Board.actionPerformed for : "+p);
            ArrayList<Cell> moveList = /*pawnPiece*/p.getValidMoves(squares, tempcell.x, tempcell.y, false);
            highlightCells(moveList);
            selectedCell=tempcell;
        }
	}

    public void movePiece(Cell destination){
        Piece p = selectedCell.getPiece();
        p.setXY(destination.x,destination.y);
        if(!destination.isEmpty()){
            FiftyMoves.resetCounter();//New
            if(destination.getColour()==0){
                int temp = whitePiece.get(destination.getPieceId());
                whitePiece.put(destination.getPieceId(), temp-1);
                for(int i=0;i<whitePieceRef.size();i++){
                    if(destination.x == whitePieceRef.get(i).getX() && destination.y == whitePieceRef.get(i).getY()){
                        whitePieceRef.remove(i);
                        break;
                    }
                }
            }else{
                int temp = blackPiece.get(destination.getPieceId());
                blackPiece.put(destination.getPieceId(), temp-1);
                for(int i=0;i<blackPieceRef.size();i++){
                    if(destination.x == blackPieceRef.get(i).getX() && destination.y == blackPieceRef.get(i).getY()){
                        blackPieceRef.remove(i);
                        break;
                    }
                }
            }
        }
        destination.addPiece(p);
        if(selectedCell.getPiece().getPieceId().equals("PAWN")) FiftyMoves.resetCounter();
        chance=(chance+1)%2;
        if(chance == 0){
            setTurnLabel(player2.getText());
        }else setTurnLabel(player1.getText());
        selectedCell.removePiece();
        FiftyMoves.incrementCounter();//new
		if(!highlightedCells.isEmpty()){
			Border thinBorder = new LineBorder(Color.BLACK, 0);
            for(int i=0;i<highlightedCells.size();i++){
				Cell c=highlightedCells.get(i);
				c.setBorder(thinBorder);
			}
			highlightedCells.clear();
        }
		selectedCell = null;
        if(!destination.isEmpty() && destination.getPieceId().equals("PAWN") && (destination.x == 1 || destination.x == 8)){
            Pawn p1 = (Pawn)destination.getPiece();
            p1.promotePawn(boardFrame,squares);
            if(p1.getColour()==0){
                
                
                for(int i=0;i<whitePieceRef.size();i++){
                    if(p1.x == whitePieceRef.get(i).getX() && p1.y == whitePieceRef.get(i).getY()){
                        whitePieceRef.remove(i);
                        break;
                    }
                }
            }else{
                
                for(int i=0;i<blackPieceRef.size();i++){
                    if(p1.x == blackPieceRef.get(i).getX() && p1.y == blackPieceRef.get(i).getY()){
                        blackPieceRef.remove(i);
                        break;
                    }
                }
            }
            if(squares[p1.getX()][p1.getY()].getColour()==0){
                whitePieceRef.add(squares[p1.getX()][p1.getY()].getPiece());
            }else {
                blackPieceRef.add(squares[p1.getX()][p1.getY()].getPiece());
            }
        }
        Chess.timeClockThread.resetTime();
        checkFlag = Check.check(squares,chance);
        if(checkFlag == true){
            checkLabel.setText("     Check!!    ");
        }
        else checkLabel.setText("               ");
        checkMateFlag = checkMate();
        if(checkFlag && checkMateFlag){
            String result;
            if(chance == 0)result="Black Wins";
            else result= "White Wins";
            gameEnder = new GameEnds(this);
            gameEnder.endGame(result);
        }else if(checkMateFlag){
            gameEnder = new GameEnds(this);
            gameEnder.endGame("DRAW");
        }
        //System.out.println("checkMateFlag = "+checkMateFlag);

        //checkCreated(destination);
    }

    private void highlightCells(ArrayList<Cell> moves){
        Border thickBorder = new LineBorder(Color.BLACK, 5);
        Border thinBorder = new LineBorder(Color.BLACK, 0);
        if(!highlightedCells.isEmpty()){
            for(int i=0;i<highlightedCells.size();i++){
				Cell c=highlightedCells.get(i);
				c.setBorder(thinBorder);
			}
			highlightedCells.clear();
        }
        
        for(int i=0; i < moves.size();i++){
            Cell c=moves.get(i);
			//System.out.println("Setting Borders for : "+c.x+" "+c.y);
            c.setBorder(thickBorder);
            highlightedCells.add(c);
        }
    }

    public void changeChance(){
		chance = (chance + 1) % 2;
		if(selectedCell != null){
			Border thinBorder = new LineBorder(Color.BLACK, 0);
            for(int i=0;i<highlightedCells.size();i++){
				Cell c=highlightedCells.get(i);
				c.setBorder(thinBorder);
			}
			highlightedCells.clear();
        }
		selectedCell = null;
        if(chance == 0){
            setTurnLabel(player2.getText());
        }else setTurnLabel(player1.getText());
		//include changes in labels
	}

    public boolean checkCreated(Cell destination){
        Piece p=destination.getPiece();
        ArrayList<Cell> moveList = p.getValidMoves(squares, destination.x, destination.y, true);
        King temp;
        if(p.getColour() == 0){
            temp = b0;
        }else temp = w0;
        checkFlag = moveList.contains(squares[temp.x][temp.y]);
        /*for(int i=0;i<moveList.size();i++)
        System.out.println(moveList.get(i).x+" "+moveList.get(i).y);
        System.out.println(temp.x+" "+temp.y);
        System.out.println(checkFlag);*/
        return checkFlag;    
    }

    public boolean checkMate(){
        boolean flag=true;
        if(chance==0){
            for(int i=0;i<whitePieceRef.size();i++){
                ArrayList<Cell> validMoves;
                Piece p=whitePieceRef.get(i);
                validMoves=p.getValidMoves(squares,p.getX(),p.getY(),false);
                if(!validMoves.isEmpty()){
                    flag=false;
                    break;
                }
            }
        }else {
            for(int i=0;i<blackPieceRef.size();i++){
                ArrayList<Cell> validMoves;
                Piece p=blackPieceRef.get(i);
                validMoves=p.getValidMoves(squares,p.getX(),p.getY(),false);
                if(!validMoves.isEmpty()){
                    flag=false;
                    break;
                }
            }

        }
        checkMateFlag=flag;
        return flag;
    }

    public void endTheGame(String gameEnderString){
		gameEnder.endGame(gameEnderString);
	}

}