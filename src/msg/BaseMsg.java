package msg;

import java.io.Serializable;
import java.net.Socket;

/**
 * 所有Msg的父类
 */

public abstract class BaseMsg implements Serializable{

	protected Socket client;

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}
	public abstract void doBiz();

}
