//package com.neelcg.chess;

public class Chess{
    public static TimeClock timeClockThread;
    public static void main(String args[]){
        Board board = new Board();
		PlayerNames playerNames = new PlayerNames(board);
        board.setTurnLabel(board.getPlayer2Name());
		timeClockThread = new TimeClock(board);
        timeClockThread.start();

    }

    
}