package msg;

import room.LoginPanel;

/**
 * 功能：由服务器发往客户端，用户名不存在，登录失败，跳转到新建用户名
 */

public class ServerLoginFailMsg extends BaseMsg{

	public void doBiz() {
		new LoginPanel().reInput1();
	}

}
