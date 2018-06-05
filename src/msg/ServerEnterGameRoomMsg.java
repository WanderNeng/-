package msg;

import entity.User;
import net.MyClient;
import room.GameRoom;

/**
 * 功能：由服务端发往客户端，进入游戏房间
 * 
 */

public class ServerEnterGameRoomMsg extends BaseMsg {

	private int statu;
	private int roomId;
	private User user;
	private User user1;
	/**
	 * 功能：
	 * @param statu 1 房间内没人 2 房间内已有一个人 3 本来在房间内的人检测到有人进入房间，更新用户信息
	 * 4 a和b都在房间，当a退出房间时，发送更新用户消息到b
	 */
	public ServerEnterGameRoomMsg(int statu,int roomId,User user,User user1) {
		this.statu = statu;
		this.roomId = roomId;
		this.user = user;
		this.user1 = user1;
	}
	public void doBiz() {
		if(statu==3) {
			MyClient.getMyClient().getRoom().setJp(user1,-roomId);
			MyClient.getMyClient().getRoom().control(true);
		}else if(statu ==1) {
			GameRoom gr = new GameRoom();
			gr.getChessPanel().setRoomId(roomId);
			gr.setRoomId(roomId);
			gr.setUser(user);
			MyClient.getMyClient().setRoom(gr);
			gr.setJp(user,roomId);
			gr.setVisible(true);
			gr.control(false);
		}else if(statu == 2) {
			GameRoom gr = new GameRoom();
			gr.getChessPanel().setRoomId(roomId);
			gr.setRoomId(roomId);
			gr.setUser(user);
			MyClient.getMyClient().setRoom(gr);
			gr.setJp(user,roomId);
			gr.setJp(user1,-roomId);
			gr.setVisible(true);
			gr.control(true);
		}else if(statu == 4) {
			MyClient.getMyClient().getRoom().setJp(-roomId);
			MyClient.getMyClient().getRoom().control(false);
		}
	}
}
