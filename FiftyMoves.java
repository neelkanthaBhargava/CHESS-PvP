public class FiftyMoves
{
	private static int counter = 0;
	private static Board board;
	public static void resetCounter(){
		counter = 0;
	}
	public static void setBoard(Board boardboard){
		board = boardboard;
	}
	public static void incrementCounter(){
		counter++;
		if(counter == 50){
			board.endTheGame("DRAW");
		}
	}
}