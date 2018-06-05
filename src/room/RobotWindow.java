package room;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import chess.ChessPanel;

/**
 * 人机棋盘主界面
 */
public class RobotWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private ChessPanel chessPanel;
	private JPanel toolbar;
	private JButton exit;
	private JButton back;
	private JButton restart;

	
	
	public RobotWindow() {
		chessPanel = new ChessPanel(1);
		setTitle("五子棋");
		setLayout(null);
		setBounds(0,0,710,780);
		exit = new JButton("退出");
		back = new JButton("悔棋");
		restart = new JButton("重新开始");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		restart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.print("重新开始");
				chessPanel.restartGame();
			}
		});
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.print("悔棋了");
				chessPanel.goBack();
			}
		});
		toolbar = new JPanel();
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		// toolbar.add(start);
		toolbar.add(restart);
		toolbar.add(back);
		toolbar.add(exit);
		chessPanel.setBounds(0,0,700,700);
		toolbar.setBounds(0,700,700,200);
		add(toolbar);
		add(chessPanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


}
