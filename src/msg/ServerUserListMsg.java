package msg;

import java.util.List;

import entity.User;
import net.MyClient;
/**
 * 记录全体在线用户的报文类，由服务器发往所有客户端，在客户端执行dobiz更新用户列表
 *
 */
public class ServerUserListMsg extends BaseMsg {
	private List<User> userList;
	
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public ServerUserListMsg(List<User> userList) {
		super();
		this.userList = userList;
	}

	@Override
	public void doBiz() {
		MyClient.getMyClient().getRoomlist().showUserList(userList);
	}
}
