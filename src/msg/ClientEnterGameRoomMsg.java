package msg;

import entity.RoomPojo;
import entity.User;
import net.MyServer;

/**
 * 客户端发往服务端，点击进入房间
 */

public class ClientEnterGameRoomMsg extends BaseMsg {
	private RoomPojo room;
	private int key;
	public ClientEnterGameRoomMsg(int key,RoomPojo room) {
		this.room = room;
		this.key = key;
	}
	public void doBiz() {
		MyServer.getMyServer().getRooms().put(key,room);//更新房间状态map的信息
		int m = key%2==0?key+1:key-1;//房间对面玩家的key值
		int roomId = key%2==0?key/2+1:-(key+1)/2;//通过key生成roomId，同一房间内roomId互为相反数
		MyServer.getMyServer().bindRoomId(roomId,this.client);//将roomId和client绑定
		MyServer.getMyServer().sendMsgToAll(new ServerRoomListMsg(MyServer.getMyServer().getRooms()));//更新所有玩家roomlist的房间列表
		if(MyServer.getMyServer().getRooms().get(m).isHas()) {//如果进入的房间对面有人
			User keyuser = MyServer.getMyServer().getRooms().get(key).getUser();
			User muser = MyServer.getMyServer().getRooms().get(m).getUser();
			MyServer.getMyServer().sendMsgToClient(new ServerEnterGameRoomMsg(2,roomId,keyuser,muser), keyuser);
			MyServer.getMyServer().sendMsgToClient(new ServerEnterGameRoomMsg(3,-roomId,muser,keyuser), muser);
		}else {
			User userkey = MyServer.getMyServer().getRooms().get(key).getUser();
		    MyServer.getMyServer().sendMsgToClient(new ServerEnterGameRoomMsg(1,roomId,userkey,null), userkey);
		}
	}

}
