package msg;

import net.MyClient;


/**
 * 功能：由服务器发往所有客户端，roomlist界面中的消息接受
 */

public class ServerSendMsgtoAllMsg extends BaseMsg {
	String str;
	public ServerSendMsgtoAllMsg(String str) {
		this.str = str;
	}
	
	public void doBiz() {
		MyClient.getMyClient().getRoomlist().receiveMsg(str);
	}

}
