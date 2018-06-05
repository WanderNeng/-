package msg;

import net.MyServer;

/**
 * 客户端发往服务端，游戏房间内的聊天信息
 */

public class ClientgameroomSendMsg extends BaseMsg {

	String str;
	int roomId;
	
	public ClientgameroomSendMsg(String str,int roomId) {
		this.str = str;
		this.roomId = roomId;
	}
	public void doBiz() {
		MyServer.getMyServer().sendMsgToClientbyRoomId(new ServergameroomSendMsg(str), -roomId);
	}
}
