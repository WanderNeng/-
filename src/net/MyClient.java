package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import msg.BaseMsg;
import room.GameRoom;
import room.RoomList;

/**
 * 功能：客户端
 */

public class MyClient {

	private Socket client = null;
	private boolean connected = false;
	private static MyClient myClient;

	//将gameroom放在Myclient类中，以后再用时不用新建
	private GameRoom gameRoom;
	public GameRoom getRoom() {
		return gameRoom;
	}
	public void setRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}
	//将roomlist放在Myclient类中，以后再用时不用新建
	public RoomList roomlist;
    public RoomList getRoomlist() {
		return roomlist;
	}
	public void setRoomlist(RoomList roomlist) {
		this.roomlist = roomlist;
	}

// 构造函数私有化
	private MyClient(){}
	public static MyClient getMyClient(){
		if(myClient==null){
			myClient = new MyClient();
		}
		return myClient;
	}

	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	/**
	 * 连接3788端口上的服务器
	 * @return是否成功连接
	 */
	public boolean connect(){
		try {
			client=new Socket("localhost",3788);		
		} catch (Exception e) {
			e.printStackTrace();
			this.setConnected(false);
		}
		this.setConnected(true);
		new ReceiveServerThread(client).start();
		return true;
	}

	/**
	 *关闭客户端连接 
	 * @return 是否成功
	 */
	public boolean disConnect(){
		if(client==null){
			return true;
		}
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("关闭了");
		}
		connected=false;
		return true;
	}

	/**
	 * 不断接受报文类并执行其Biz方法的线程
	 *
	 */
	class ReceiveServerThread extends Thread{
		private  Socket client;

		public ReceiveServerThread(Socket client) {
			super();
			this.client = client;
		}

		public void run() {
			
				try {
					while(true){
					 ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
					 BaseMsg msg = (BaseMsg)ois.readObject();
					 System.out.println("收到数据"+msg);
					 msg.doBiz();
			//		 ois.close();
				    }
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
			
		}
		
	}

	/**
	 * 供用户界面的监听器调用，向服务器发送报文类的方法
	 * @param msg
	 */
	
	public void sendMsg(BaseMsg msg){
		if(!this.isConnected()){
			return;
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			oos.writeObject(msg);
			System.out.println("发送报文"+msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}