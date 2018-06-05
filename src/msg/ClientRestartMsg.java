package msg;

import net.MyServer;

/**
 * 功能：由客户端发往服务端，求和或离开了房间
 */

public class ClientRestartMsg extends BaseMsg {
	int roomId;
	int statu;
	/**
	 * @param statu 
	 * 0时表示接收到对面发出的求和请求
	 * 1 对方接受了你的求和 2 对方不接受求和 3 你打败了对方 
	 * 4 你败给对方 5 你败给对方
	 */
	public ClientRestartMsg(int roomId,int statu) {
		this.roomId = roomId;
		this.statu = statu;
	}
	
	public void doBiz() {
		MyServer.getMyServer().sendMsgToClientbyRoomId(new ServerRestartMsg(statu), -roomId);
	}

}
