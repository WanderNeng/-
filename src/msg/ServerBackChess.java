package msg;

import net.MyClient;

/**
 * 功能：由服务端发往客户端，实现悔棋功能
 */

public class ServerBackChess extends BaseMsg {
	int statu;
	/**
	 * @param statu
	 * 0时接收到悔棋请求 1时对方同意悔棋 2时对方不同意悔棋
	 */
	public ServerBackChess(int statu) {
		this.statu = statu;
	}

	public void doBiz() {
		MyClient.getMyClient().getRoom().BackChess(statu);
	}
}
