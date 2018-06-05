package msg;

import net.MyClient;
import room.GameRoom;

/**
 * 功能：游戏房间内，信息的传递
 */

public class ServergameroomSendMsg extends BaseMsg {

	String str;
	
	public ServergameroomSendMsg(String str) {
		this.str = str;
	}
	
	public void doBiz() {
		MyClient.getMyClient().getRoom().receiveMsg(str);
	}
}
