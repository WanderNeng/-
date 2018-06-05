package room;

import javax.swing.*;

import entity.UpdatePicture;
import entity.User;

import java.awt.event.*;
import net.MyClient;
import msg.*;

/**
 * 功能：完成账号的验证和登录
 */

public class LoginPanel extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton login;
	private JTextField jt1;
	private JLabel name;
	public static String userName;
	private User user = new User("");

	public LoginPanel() {
		setTitle("五子棋登录界面");
		setLayout(null);
		setBounds(300, 300, 300, 150);
		login = new JButton("登陆");
		jt1 = new JTextField();
		name = new JLabel("用户名", JLabel.CENTER);

		name.setBounds(20, 30, 60, 30);
		add(name);
		jt1.setBounds(90, 30, 150, 30);
		add(jt1);
		login.setBounds(140, 70, 60, 30);
		add(login);
		login.addActionListener(new ActionListener() {//登录按钮
			public void actionPerformed(ActionEvent e) {
				if (!jt1.getText().isEmpty()) {
					if (!MyClient.getMyClient().isConnected()) {
						MyClient.getMyClient().connect();
					}
					MyClient.getMyClient().sendMsg(new ClientLoginMsg(jt1.getText(), null, 0));
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "用户名不能为空", "提示", JOptionPane.WARNING_MESSAGE);
				}

			}
		});

	}

	/**
	 * 功能：登录时发现用户名不存在，调用此函数新建用户
	 */
	public void reInput1() {
		this.setVisible(true);
		int n = JOptionPane.showConfirmDialog(this, String.format("用户名%s不存在，是否创建新用户？", jt1.getText()), "标题",
				JOptionPane.YES_NO_OPTION);
		if (n == 0) {
			dispose();
			String str = JOptionPane.showInputDialog("请输入用户名：");
			user = new User(str);
			new UpdatePicture(user, 1).setVisible(true);
		}

	}

	/**
	 * 功能：发现用户名已登录，重现登录
	 */
	public void reInput2() {
		this.setVisible(true);
		JOptionPane.showMessageDialog(this, "该用户已登录，请检查！", "提示", JOptionPane.WARNING_MESSAGE);
	}

}
