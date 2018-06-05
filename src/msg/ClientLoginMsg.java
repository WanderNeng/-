package msg;


import entity.User;
import net.MyServer;
/**
 * 用户登录报文类，由客户端发送至服务器端
 *
 */

public class ClientLoginMsg extends BaseMsg{


	private String username;
	private String filename;
	private int statu;
	public ClientLoginMsg(String username,String filename,int statu) {
		super();
		this.username = username;
		this.filename = filename;
		this.statu = statu;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public void doBiz() {
		User user;
		if(statu == 0) {//通过statu来控制新建user
		    user= MyServer.getMyServer().findUser(username);
		}else {
			user = new User(username,filename,0,0,0);
			MyServer.getMyServer().insertUser(user);
		}
		if(user!=null) {
			if(!MyServer.getMyServer().loged(user)) {//如果该用户已登录
				ServerDidLogMsg msg3=new ServerDidLogMsg();
				MyServer.getMyServer().sendMsgToClient(msg3, this.client);
				return;
			}
			ServerLoginSucMsg msg=new ServerLoginSucMsg(user);
			//服务器发送报文给指定客户
			MyServer.getMyServer().sendMsgToClient(msg, this.client);
			//将客户端的用户名与所分配到的线程进行绑定
			//有username查询dao得到User对象user
			MyServer.getMyServer().bindUsername(user, client);
			//向所有客户端发送在线用户列表
			ServerUserListMsg msg2=new ServerUserListMsg(MyServer.getMyServer().getUserList());
			MyServer.getMyServer().sendMsgToAll(msg2);
			//服务器向登录的客户端发送房间列表报文
			ServerRoomListMsg msg3=new ServerRoomListMsg(MyServer.getMyServer().getRooms());
			MyServer.getMyServer().sendMsgToClient(msg3, this.client);
			

	}else {
		ServerLoginFailMsg msg = new ServerLoginFailMsg();
		MyServer.getMyServer().sendMsgToClient(msg, this.client);
		}
	}


}
