package chess;

/**
 * 人类线程
 */
public class HumanThread implements Runnable {
    private ChessPanel chessTable;
  
    public HumanThread(ChessPanel chessTable) {
      this.chessTable = chessTable;
    }
  
    /**
     * 人类线程开启鼠标监听
     */
    public void run() {
      chessTable.addMouseListener(chessTable.new MouseHandler());
    }
  
  }