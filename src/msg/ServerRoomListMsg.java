package msg;

import java.util.HashMap;

import entity.RoomPojo;
import net.MyClient;

/**
 * 功能：客户端发往服务端，更新roomlist
 */
public class ServerRoomListMsg extends BaseMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Integer,RoomPojo> roomList;

	public ServerRoomListMsg(HashMap<Integer,RoomPojo> roomList) {
		super();
		this.roomList = roomList;
	}

	public HashMap<Integer,RoomPojo> getRoomList() {
		return roomList;
	}

	public void setRoomList(HashMap<Integer,RoomPojo> roomList) {
		this.roomList = roomList;
	}

	@Override
	public void doBiz() {
		MyClient.getMyClient().getRoomlist().showRoomList(roomList);
	}
}
