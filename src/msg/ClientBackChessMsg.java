package msg;

import net.MyServer;

/**
 * 客户端发往服务端，悔棋的实现
 */

public class ClientBackChessMsg extends BaseMsg{
	int roomId;
	int statu;
	public ClientBackChessMsg(int roomId,int statu) {
		this.roomId = roomId;
		this.statu = statu;
	}
	
	public void doBiz() {
		MyServer.getMyServer().sendMsgToClientbyRoomId(new ServerBackChess(statu), -roomId);
	}

}
