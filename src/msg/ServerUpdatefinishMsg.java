package msg;

import entity.User;
import net.MyClient;

/**
 * 在游戏结束时或者用户更换头像时调用
 * 功能：由服务器发往客户端，在客户端中更新游戏大厅里列表。
 */

public class ServerUpdatefinishMsg extends BaseMsg{
	User user;
	int roomId;
	
	public ServerUpdatefinishMsg(User user, int roomId) {
		this.user = user;
		this.roomId = roomId;
	}

	public void doBiz() {
		MyClient.getMyClient().getRoom().setJp(user,roomId);
	}
}
