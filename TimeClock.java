//import java.lang.System;

public class TimeClock extends Thread{
	private long startTime;
	private Board board;
	private int timeLimit = 5;
	private long savedDiff;	
	public TimeClock(Board board){
		this.board = board;
		//board.setTimeClock(this);
		startTime = System.currentTimeMillis();
		savedDiff=0;
	}
	public void run(){
		while(true){
			updateTime();
			try{
				Thread.sleep(500);
			}
			catch(InterruptedException e){
				System.out.println(e);
			}
		}
	}
	public void setTimeLimit(int timeLimit){
		this.timeLimit = timeLimit;
		//System.out.println("New timelimit is: "+timeLimit);
	}
	public void resetTime(){
		startTime = System.currentTimeMillis();
		savedDiff=0;
	}
	
	public int getTimeLimit(){
		return timeLimit;
	}
	
	private void updateTime(){
		long liveTime = System.currentTimeMillis();
		long diff = savedDiff + liveTime - startTime;
		board.setSaveTime(diff);
		//System.out.println("diff = "+diff);
		diff /= 1000;
		long timeMinutes = diff / 60;
		if(timeMinutes >= timeLimit){
			board.changeChance();
			startTime = System.currentTimeMillis();
			return;
		}
		long timeSeconds = diff % 60;
		String timeString = String.format("%02d",timeMinutes) + ":" + String.format("%02d",timeSeconds);
		board.setTimeClock(timeString);
		
	}

	public void setSavedDiff(long save){
		this.savedDiff = save;
		//System.out.println("SavedTime in TimeClock = "+savedDiff);
	}

	public long getSavedDiff(){
		return this.savedDiff;
		//System.out.println("SavedTime in TimeClock = "+savedDiff);
	}
}