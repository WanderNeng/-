package entity;

import java.io.Serializable;
/**
 * 房间实体类
 */

public class RoomPojo implements Serializable{

	private boolean has;//房间有没有人
	private User user;//房间内玩家的姓名


	public boolean isHas() {
		return has;
	}


	public void setHas(boolean has) {
		this.has = has;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public RoomPojo( boolean has, User user) {
		super();
		this.user = user;
		this.has = has;
    }


}