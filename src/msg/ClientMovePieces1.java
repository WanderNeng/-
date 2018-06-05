package msg;

import java.awt.Color;

import entity.RoomPojo;
import net.MyServer;
import net.MyClient;

/**
 * 实现下棋的传递
 */
public class ClientMovePieces1 extends BaseMsg {
	private int x;
	private int y;
	private int roomId;

	public ClientMovePieces1(int x, int y, int roomId) {
		this.x = x;
		this.y = y;
		this.roomId = roomId;
	}
	@Override
	public void doBiz() {
		ServerMovePieces1 msg = new ServerMovePieces1(x, y);
		MyServer.getMyServer().sendMsgToClientbyRoomId(msg, -roomId);

	}

}
