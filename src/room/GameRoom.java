package room;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chess.ChessPanel;
import entity.UpdatePicture;
import entity.User;
import msg.ClientBackChessMsg;
import msg.ClientBackRoom;
import msg.ClientLogoutMsg;
import msg.ClientRestartMsg;
import msg.ClientgameroomSendMsg;
import net.MyClient;

/**
 * 功能：网络对战房间显示
 */

public class GameRoom extends JFrame {
	

	private ChessPanel chessPanel;//棋盘类
	public ChessPanel getChessPanel() {
		return chessPanel;
	}
	private JButton send = new JButton("发送");//聊天信息发送
	private JTextField jt;//聊天信息输入框
	private JTextArea jt2;//聊天信息显示面板
	private JScrollPane js;//显示面板放置的容器
	private JPanel toolbar;//防置按键
	private JButton askPeace = new JButton("求和");
	public JButton isReady = new JButton("准备");
	private JButton back = new JButton("悔棋");
	private JButton exit = new JButton("返回");
	private JPanel jp;//放置玩家信息
	private JButton jl1 = new JButton();//显示黑方玩家头像
	private JTextArea jl2 = new JTextArea();//显示黑方玩家信息
	private JButton jl3 = new JButton();//显示白方玩家头像
	private JTextArea jl4 = new JTextArea();//显示白方玩家信息
	private User user;//记录房主姓名
	private int roomId;//记录房间ID，大于0为黑方，小于0为白方

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public GameRoom() {
		setTitle("联机五子棋");
		setBounds(0, 0, 1040, 780);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//导入棋盘类
		chessPanel = new ChessPanel(2);
		chessPanel.setBounds(0, 0, 700, 700);
		add(chessPanel);
		//按键区设置
		toolbar = new JPanel();
		add(toolbar);
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolbar.add(askPeace);
		toolbar.add(back);
		toolbar.add(isReady);
		toolbar.add(exit);
		toolbar.setBounds(0, 700, 700, 200);
		askPeace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyClient.getMyClient().sendMsg(new ClientRestartMsg(roomId, 0));
				askPeace.setEnabled(false);
			}
		});
		isReady.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isReady.setEnabled(false);
				if (roomId > 0) {
					chessPanel.isCanplay = true;
				}
			}
		});
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyClient.getMyClient().sendMsg(new ClientBackChessMsg(roomId, 0));
				back.setEnabled(false);
			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MyClient.getMyClient().sendMsg(new ClientBackRoom(roomId));
				MyClient.getMyClient().getRoomlist().setVisible(true);

			}
		});
		//聊天区功能设置
		jt = new JTextField("");
		jt2 = new JTextArea("");
		js = new JScrollPane(jt2);
		add(js);
		add(send);
		add(jt);
		jt2.setLineWrap(true);
		jt2.setEditable(false);
		jt.setBounds(700, 700, 260, 30);
		send.setBounds(940, 700, 75, 30);
		js.setBounds(700, 200, 340, 500);
		send.addActionListener(new ActionListener() {// 发送键的响应事件，将输入文本框清零，输出到area上去
			public void actionPerformed(ActionEvent e) {
				if (jt.getText() != null) {
					SimpleDateFormat df = new SimpleDateFormat("MM月dd日HH:mm:ss");
					MyClient.getMyClient().sendMsg(new ClientgameroomSendMsg(
							user.getName() + " " + df.format(new Date()) + "\n" + jt.getText()+"\n", roomId));
					jt2.append(user.getName() + " " + df.format(new Date()) + "\n" + jt.getText());
					jt.setText("");
				}
			}
		});
		
		//玩家信息显示区设置
		jp = new JPanel();
		jl1 = new JButton();
		jl2 = new JTextArea();
		jl3 = new JButton();
		jl4 = new JTextArea();
		add(jp);
		jp.setBounds(700, 0, 340, 200);
		jp.setLayout(null);
		jl1.setBounds(700, 0, 100, 100);
		jl2.setBounds(800, 0, 240, 100);
		jl2.setText("");
		jl2.setEditable(false);
		jl3.setBounds(700, 100, 100, 100);
		jl4.setText("");
		jl4.setEditable(false);
		jl4.setBounds(800, 100, 240, 100);
		jp.add(jl1);
		jp.add(jl2);
		jp.add(jl3);
		jp.add(jl4);
		jl1.addActionListener(new ActionListener() {//更换头像
			@Override
			public void actionPerformed(ActionEvent e) {
				if(roomId>0) {//通过roomId保证只有房主能控制
				UpdatePicture up=new UpdatePicture(user,0);
				up.setVisible(true);
				}
			}
		});
		jl3.addActionListener(new ActionListener() {//更换头像
			@Override
			public void actionPerformed(ActionEvent e) {
				if (roomId<0) {//通过roomId保证只有房主能控制
					UpdatePicture up=new UpdatePicture(user,0);
					up.setVisible(true);
				}
				
			}
		});
		//窗口关闭的监听，窗口关闭后，通知所有人，并且刷新roomlist界面里的房间列表和用户列表。
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				SimpleDateFormat df = new SimpleDateFormat("MM月dd日HH:mm:ss");
				MyClient.getMyClient().sendMsg(new ClientgameroomSendMsg(
						"系统消息：" + user.getName() + "于" + df.format(new Date()) + "离开了房间", roomId));
				MyClient.getMyClient().sendMsg(new ClientBackRoom(roomId));
				MyClient.getMyClient().sendMsg(new ClientLogoutMsg());
			}
		});

	}
	

	/**
	 * 功能：在房间里只有一个人时，设置按键不能被选。
	 */
	public void control(boolean b) {
		askPeace.setEnabled(b);
		back.setEnabled(b);
		send.setEnabled(b);
		isReady.setEnabled(b);
		chessPanel.isCanplay = false;
		this.repaint();
	}

	/**
	 * 功能：聊天信息的接收
	 */
	public void receiveMsg(String str) {
		jt2.append(str);
		this.repaint();
	}

	/**
	 * 功能：当房间内玩家信息发生变化时，更新玩家信息显示。
	 */
	public void setJp(User user, int roomId) {
		if (roomId > 0) {
			jl1.setIcon(new ImageIcon(user.getFileName()));
			jl2.setText("昵称： " + user.getName() + "\n" + "胜场：  " + user.getWinNum() + "\n" + "负场：  " + user.getLoseNum()
					+ "\n" + "    执黑");
		} else {
			jl3.setIcon(new ImageIcon(user.getFileName()));
			jl4.setText("昵称： " + user.getName() + "\n" + "胜场：  " + user.getWinNum() + "\n" + "负场：  " + user.getLoseNum()
					+ "\n" + "    执白");
		}
		this.repaint();
	}
	/**
	 * 功能：当房间内玩家离开时，更新玩家信息显示。
	 */
	public void setJp(int roomId) {
		if (roomId > 0) {
			jl1.setEnabled(false);
			jl2.setText("");
		} else {
			jl3.setEnabled(false);
			jl4.setText("");
		}
		this.repaint();
	}

	/**
	 * 功能：悔棋控制程序 statu取值不同来控制
	 * @param statu
	 * 0时接收到悔棋请求 1时对方同意悔棋 2时对方不同意悔棋
	 */
	public void BackChess(int statu) {
		if (statu == 0) {
			int op = JOptionPane.showConfirmDialog(this, "对面要悔棋，你答应嘛", "提示", JOptionPane.YES_NO_OPTION);
			if (op == JOptionPane.YES_OPTION) {
				chessPanel.goBack();
				chessPanel.isCanplay = !chessPanel.isCanplay;
				MyClient.getMyClient().sendMsg(new ClientBackChessMsg(roomId, 1));
			} else {
				MyClient.getMyClient().sendMsg(new ClientBackChessMsg(roomId, 2));
			}
		} else if (statu == 1) {
			JOptionPane.showMessageDialog(this, "对方同意悔棋了");
			chessPanel.goBack();
			chessPanel.isCanplay = !chessPanel.isCanplay;
			back.setEnabled(true);
		} else {
			JOptionPane.showMessageDialog(this, "对方不同意悔棋");
			back.setEnabled(true);
		}
	}
	/**
	 * 功能：求和，输赢的通报 
	 * @param statu 
	 * 0时表示接收到对面发出的求和请求
	 * 1 对方接受了你的求和 2 对方不接受求和 3 你打败了对方 
	 * 4 你败给对方 5 你败给对方
 	*/
	public void askStatu(int statu) {
		if (statu == 0) {//statu为0时表示接受到对面发出的求和请求
			int op = JOptionPane.showConfirmDialog(this, "对面要求和，你答应嘛", "提示", JOptionPane.YES_NO_OPTION);
			if (op == JOptionPane.YES_OPTION) {
				chessPanel.restartGame();
				MyClient.getMyClient().sendMsg(new ClientRestartMsg(roomId, 1));
			} else {
				MyClient.getMyClient().sendMsg(new ClientRestartMsg(roomId, 2));
			}
		} else if (statu == 1) {//对方接受了你的求和
			JOptionPane.showMessageDialog(this, "对方接受了你的求和");
			chessPanel.restartGame();
			askPeace.setEnabled(true);
		} else if (statu == 2) {//对方不接受求和
			JOptionPane.showMessageDialog(this, "对方不接受求和");
			askPeace.setEnabled(true);
		} else if (statu == 3) {//你打败了对方
			JOptionPane.showMessageDialog(this, "你赢了");
			chessPanel.restartGame();
		} else if (statu == 4) {//你败给对方
			JOptionPane.showMessageDialog(this, "你输了");
			chessPanel.restartGame();
		} else if(statu == 5) {//对手离开房间的通报
			JOptionPane.showMessageDialog(this, "对手离开了房间");
			chessPanel.restartGame();
		}
	}

}
