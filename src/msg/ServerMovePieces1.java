package msg;

import java.awt.Color;

import net.MyClient;
import room.GameRoom;

/**
 * 功能：由服务端发往客户端，传递落子信息
 */

public class ServerMovePieces1 extends BaseMsg {
	private int x;
	private int y;
	
	public ServerMovePieces1(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void doBiz() {
		MyClient.getMyClient().getRoom().getChessPanel().netChess(x,y);
	}

}
