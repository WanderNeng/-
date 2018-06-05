package room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



/**
 * 功能：登录主界面
 * 
 */
public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JButton jb1;
	private JButton jb2;
	private JPanel jp;
	public Main() {
		setTitle("欢迎使用五子棋");
		setBounds(300,300,500,300);
		jb1 = new JButton("单机游戏");
		jb2 = new JButton("联机游戏");
		jp = new JPanel();
		jp.setBounds(100,50,300,200);
		add(jp);
		jb1.setBounds(200,100,100,30);
		jb2.setBounds(200,200,100,30);
		jp.add(jb1);
		jp.add(jb2);
		jb1.addActionListener(new ActionListener() {//单机游戏响应
			public void actionPerformed(ActionEvent e) {
				dispose();
				new RobotWindow().setVisible(true);
				
			}
		});
		jb2.addActionListener(new ActionListener() {//联机游戏响应
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginPanel().setVisible(true);
			}
		});
	}
	public static void main(String[] args) {
		new Main().setVisible(true);
	}

}
