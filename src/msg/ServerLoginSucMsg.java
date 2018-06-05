package msg;

import entity.User;
import net.MyClient;
import room.RoomList;
/**
 * 登录成功报文类，由服务器发往客户端
 */
public class ServerLoginSucMsg extends BaseMsg{


	private User user;
	public ServerLoginSucMsg(User user) {
		super();
		this.user = user;
	}
	public void doBiz() {//新建并打开roomlist窗口
		RoomList rl = new RoomList();
		rl.setUser(user);
		MyClient.getMyClient().setRoomlist(rl);
		rl.setVisible(true);
	}

}
