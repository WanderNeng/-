package msg;

import net.MyServer;

/**
 * 功能：roomlist聊天信息，由客户端发往服务端，服务端将聊天信息转发到所有客户端
 */

public class ClientSendMsgtoAllMsg extends BaseMsg {
    String str;
	public ClientSendMsgtoAllMsg(String str) {
    	this.str = str;
    }     
	public void doBiz() {
        	 MyServer.getMyServer().sendMsgToAll(new ServerSendMsgtoAllMsg(str));
         } 
}
