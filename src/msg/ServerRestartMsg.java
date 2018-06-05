package msg;

import net.MyClient;

/**
 * 功能：由服务端发往客户端，关于求和和输赢的通报
 */

public class ServerRestartMsg extends BaseMsg{
	int statu;
	/**
	 * @param statu 
	 * 0时表示接收到对面发出的求和请求
	 * 1 对方接受了你的求和 2 对方不接受求和 3 你打败了对方 
	 * 4 你败给对方 5 你败给对方
	 */
	public ServerRestartMsg(int statu) {
		this.statu = statu;
	}
	
	public void doBiz() {
		MyClient.getMyClient().getRoom().askStatu(statu);
		
	}

}
