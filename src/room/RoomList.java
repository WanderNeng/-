package room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import entity.RoomPojo;
import entity.User;
import msg.ClientEnterGameRoomMsg;
import msg.ClientLogoutMsg;
import msg.ClientSendMsgtoAllMsg;
import net.MyClient;

public class RoomList extends JFrame {
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private JList<String> jl;//用户列表
	private JButton jb1;//聊天信息发送按钮
	private JScrollPane js1;// 放置玩家显示框
	private JScrollPane js2;// 放置聊天框
	private JTextArea jt1;// 信息输入框
	private JTextField jt2;// 信息显示栏
	private JLabel jl1;
	private JLabel jl2;
	private JLabel jl3;
	private JPanel jp1 = new JPanel();;

	public RoomList() {
		setTitle("游戏大厅");
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 800);
		jp1.setBounds(500, 60, 300, 740);
		add(jp1);
		// 头部细节
		jl1 = new JLabel("用户列表", JLabel.CENTER);
		jl2 = new JLabel("聊天列表", JLabel.CENTER);
		jl3 = new JLabel("房间列表", JLabel.CENTER);
		jl1.setBounds(0, 0, 200, 60);
		jl2.setBounds(200, 0, 300, 60);
		jl3.setBounds(500, 0, 300, 60);
		add(jl1);
		add(jl2);
		add(jl3);
		// 用户列表
		js1 = new JScrollPane();
		js1.setBounds(0, 60, 200, 740);
		add(js1);
		jl = new JList<String>();
		js1.setViewportView(jl);
		// 聊天列表
		js2 = new JScrollPane();
		js2.setBounds(200, 60, 300, 680);
		add(js2);
		jt1 = new JTextArea();
		jt1.setEditable(false);
		jt1.setLineWrap(true);
		jt2 = new JTextField();
		js2.setViewportView(jt1);
		jb1 = new JButton("发送");
		add(jb1);
		jb1.setBounds(430, 720, 70, 60);
		add(jt2);
		jt2.setBounds(200, 720, 230, 60);
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!jt2.getText().isEmpty()) {
					SimpleDateFormat df = new SimpleDateFormat("MM月dd日HH:mm:ss");
					String str = user.getName() + "  " + df.format(new Date()) + "\n" + jt2.getText() + "\n";
					MyClient.getMyClient().sendMsg(new ClientSendMsgtoAllMsg(str));
					jt2.setText("");
				}
			}
		});
		//监听窗口关闭
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				SimpleDateFormat df = new SimpleDateFormat("MM月dd日HH:mm:ss");
				MyClient.getMyClient().sendMsg(new ClientLogoutMsg());
				String str = "系统消息："+user.getName() + "于" + df.format(new Date()) + "退出了游戏。\n";
				MyClient.getMyClient().sendMsg(new ClientSendMsgtoAllMsg(str));
				System.exit(0);
			}

		});
	}

	/**
	 * 功能：接受聊天信息
	 */
	public void receiveMsg(String msg) {
		jt1.append(msg);
	}


	/**
	 * 功能：刷新房间列表
	 * @param al
	 * 记录每个房间的信息，int记录房间，roompojo记录房间状态
	 */
	public void showRoomList(HashMap<Integer, RoomPojo> al) {
		jp1.removeAll();
		for (Map.Entry<Integer, RoomPojo> entry : al.entrySet()) {
			if (entry.getValue().isHas()) {
				int num = entry.getKey();
				JButton jb = new JButton();
				int x = num % 2 == 0 ? 0 : 1;
				int y = num % 2 == 0 ? num / 2 : (num - 1) / 2;
				jb.setBounds(10 + x * 110, y * 110, 100, 100);
				jb.setIcon(new ImageIcon(entry.getValue().getUser().getFileName()));
				jb.setEnabled(false);
				jp1.add(jb);
			} else {
				int num = entry.getKey();
				String str = num % 2 == 0 ? String.format("房间%d黑", (num + 2) / 2)
						: String.format("房间%d白", (num + 1) / 2);
				JButton jb = new JButton(str);
				int x = num % 2 == 0 ? 0 : 1;
				int y = num % 2 == 0 ? num / 2 : (num - 1) / 2;
				jb.setBounds(10 + x * 110, y * 110, 100, 100);
				jp1.add(jb);
				jb.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MyClient.getMyClient().sendMsg(new ClientEnterGameRoomMsg(num, new RoomPojo(true, user)));
						setVisible(false);
					}
				});
			}
		}
		repaint();
	}

	/**
	 * 功能：更新显示用户列表
	 * @param al
	 * 记录用户信息
	 */
	public void showUserList(List<User> al) {
		int i = 0;
		String[] list = new String[12];
		for (User user : al) {
			list[i] = user.getName();
			i++;
		}
		jl.setListData(list);
		repaint();
	}

}
