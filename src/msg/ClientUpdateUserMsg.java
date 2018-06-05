package msg;

import entity.User;
import net.MyServer;

/**
 * 功能：由客户端发往服务端，在服务端更新用户在数据库中的信息
 */

public class ClientUpdateUserMsg extends BaseMsg {
	int roomId;
	int statu;
	User user;


	/**
	 * @param statu 0 更新用户头像 1 更新用户胜负场
	 */
	public ClientUpdateUserMsg(int roomId, int statu, User user) {
		this.roomId = roomId;
		this.statu = statu;
		this.user = user;
	}

	public void doBiz() {
		int win = roomId > 0 ? 2 * roomId - 2 : -2 * roomId - 1;//对应roomId在hashmap中的key值
		int lose = roomId > 0 ? 2 * roomId - 1 : -2 * roomId - 2;//另一玩家对应roomId在hashmap中的key值
		User user2 = MyServer.getMyServer().getRooms().get(lose).getUser();//对面选手的user值
		//更新数据库中的值
		if (statu == 0) {//更新用户头像
			MyServer.getMyServer().getRooms().get(win).setUser(user);;
			MyServer.getMyServer().updateUserImag(user.getFileName(), user.getName());
		} else {//更新用户胜负场
			user2.setLoseNum(user2.getLoseNum()+1);
			MyServer.getMyServer().getRooms().get(win).setUser(user);
			MyServer.getMyServer().getRooms().get(lose).setUser(user2);
			MyServer.getMyServer().updateWinNum(user);
			MyServer.getMyServer().updateLoseNum(user2);
		}
		//更新客户端界面中用户信息板块
		MyServer.getMyServer().sendMsgToClientbyRoomId(new ServerUpdatefinishMsg(user,roomId), roomId);
		if (MyServer.getMyServer().getRooms().get(lose).isHas()) {
			MyServer.getMyServer().sendMsgToClientbyRoomId(new ServerUpdatefinishMsg(user,roomId), -roomId);
		}
	}

}
