package chess;

/**
 * 机器人线程
 */
public class RobotThread implements Runnable {
    private ChessPanel chessTable;
    public RobotThread(ChessPanel chessTable){
      this.chessTable=chessTable;
    }
    public void run(){
      chessTable.robotChess();
    }
  
  }