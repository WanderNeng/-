package msg;

import entity.RoomPojo;
import entity.User;
import net.MyServer;

/**
 * 功能：客户端发往服务端，返回游戏大厅
 */

public class ClientBackRoom extends BaseMsg {

	int roomId;
	public ClientBackRoom(int roomId) {
		this.roomId=roomId;
	}
	
	public void doBiz() {
		int m = roomId>0?2*roomId-2:-1-2*roomId;//对应roomId的key值
		MyServer.getMyServer().getRooms().put(m,new RoomPojo(false,null));
		MyServer.getMyServer().sendMsgToAll(new ServerRoomListMsg(MyServer.getMyServer().getRooms()));
		int key = roomId>0?2*roomId-1:-2*roomId-2;//另一个玩家对应roomId的key值
		if(MyServer.getMyServer().getRooms().get(key).isHas()) {//如果房间对面有人
			User user = MyServer.getMyServer().getRooms().get(key).getUser();
			MyServer.getMyServer().sendMsgToClient(new ServerEnterGameRoomMsg(4,-roomId,user,null), user);
			MyServer.getMyServer().sendMsgToClientbyRoomId(new ServerRestartMsg(5), -roomId);
		}
	}
}
