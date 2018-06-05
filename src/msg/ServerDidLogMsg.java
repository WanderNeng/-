package msg;

import room.LoginPanel;

/**
 * 功能: 该用户名已经登录,返回登录界面，重新登录
 */
public class ServerDidLogMsg extends BaseMsg {
	
  public void doBiz(){
	  new LoginPanel().reInput2();
  }
}
