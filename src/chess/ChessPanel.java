package chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import msg.ClientMovePieces1;
import msg.ClientRestartMsg;
import msg.ClientUpdateUserMsg;
import net.MyClient;

public class ChessPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int MARGIN = 30;// 边距
	public static int GRID_SPAN = 35;// 网格间距
	public static int ROWS = 18;// 棋盘行数
	public static int COLS = 18;// 棋盘列数
	private Point[] chessList = new Point[(ROWS + 1) * (COLS + 1)];// 放置棋子的数组
	private int xIndex;// 人下棋的位置
	private int yIndex;// 人下棋的位置
	private int chessCount;// 棋盘上总共棋子数
	public boolean isCanplay;// 控制游戏的进行
	private boolean gameOver = false;// 游戏结束
	public boolean lock = false;// 机器人线程锁
	private ChessPanel chessPanel = this;
	private Executor pool = Executors.newFixedThreadPool(2); // 2个线程容量的线程池
	private int roomId;// 用于网络对战时的roomId

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int flag;// 控制人机的切换

	/**
	 * @param flag
	 *            1 人机 0 人人
	 */

	public ChessPanel(int flag) {
		this.flag = flag;
		setBackground(Color.LIGHT_GRAY);
		if (flag == 1) {
			HumanThread humanThread = new HumanThread(this); // 人类线程
			RobotThread robotThread = new RobotThread(this); // 机器人线程
			pool.execute(humanThread);
			pool.execute(robotThread);
			isCanplay = true;
		} else {
			this.addMouseListener(new MouseHandler());
		}

	}

	// 网络下棋
	public void netChess(int x, int y) {

		System.out.println("在chessPanel中被调用");
		Color c = Color.black;
		if (roomId < 0) {
			c = Color.black;
		} else {
			c = Color.WHITE;
		}
		Point ch = new Point(x, y, c);
		chessPanel.chessList[chessCount++] = ch;
		repaint();
		MyClient.getMyClient().getRoom().repaint();
		if (isWin(x, y, c)) {
			JOptionPane.showMessageDialog(chessPanel, "你败了");
			chessPanel.gameOver = true;
			MyClient.getMyClient().sendMsg(new ClientRestartMsg(roomId, 3));
		} else {
			chessPanel.isCanplay = true;
		}
	}

	// 内部类，鼠标监听下棋
	public class MouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			synchronized (chessPanel) {

				if (gameOver) {
					return;
				}
				if (isCanplay) {
					if (flag == 1) {
						xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
						yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
						if (xIndex < 0 || yIndex < 0 || xIndex > ROWS || yIndex > COLS || gameOver
								|| findChess(xIndex, yIndex)) {
							return;
						}
						Point ch = new Point(xIndex, yIndex, Color.black);
						chessList[chessCount++] = ch;
						isCanplay = false;
						lock = true;
						repaint();
						if (isWin(xIndex, yIndex, Color.black)) {
							String msg = "菜鸡，你打赢电脑了！！！";
							JOptionPane.showMessageDialog(chessPanel, msg);
							chessPanel.gameOver = true;
							restartGame();
							chessPanel.lock = false;
						}
						chessPanel.notifyAll();

					} else {
						Color c = Color.black;
						if (roomId > 0) {
							c = Color.black;
						} else {
							c = Color.WHITE;
						}
						if (isCanplay) {
							chessPanel.xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
							chessPanel.yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
							if (xIndex < 0 || yIndex < 0 || xIndex > ROWS || yIndex > COLS || gameOver
									|| findChess(xIndex, yIndex)) {
								return;
							}
							Point ch = new Point(xIndex, yIndex, c);
							chessPanel.chessList[chessCount++] = ch;
							chessPanel.isCanplay = false;
							MyClient.getMyClient().sendMsg(new ClientMovePieces1(xIndex, yIndex, roomId));
							repaint();
							MyClient.getMyClient().getRoom().repaint();
							if (isWin(xIndex, yIndex, c)) {
								JOptionPane.showMessageDialog(chessPanel, "你赢了");
								chessPanel.gameOver = true;
								MyClient.getMyClient().sendMsg(new ClientRestartMsg(roomId, 4));
								restartGame();
								MyClient.getMyClient().sendMsg(
										new ClientUpdateUserMsg(roomId, 1, MyClient.getMyClient().getRoom().getUser()));
							}
						}
					}
				}
			}
		}
	}

	// 发现某个位置有没有棋子
	public boolean findChess(int x, int y) {
		for (int i = 0; i < chessList.length; i++) {
			if (chessList[i] != null && chessList[i].getX() == x && chessList[i].getY() == y) {
				return true;
			}

		}
		return false;
	}

	/**
	 * 得到某个位置的棋子
	 */
	private Point getChess(int x, int y, Color c) {
		for (Point point : chessList) {
			if (point != null && point.getX() == x && point.getY() == y && point.getColor() == c) {
				return point;
			}
		}
		return null;
	}

	/**
	 * 判断输赢
	 */
	private boolean isWin(int xIndex, int yIndex, Color c) {
		int count = 1;
		for (int x = xIndex - 1; x >= 0; x--) {
			if (getChess(x, yIndex, c) != null) {
				count++;
			} else {
				break;
			}
		}
		for (int x = xIndex + 1; x <= ROWS; x++) {
			if (getChess(x, yIndex, c) != null) {
				count++;
			} else {
				break;
			}
		}
		if (count >= 5) {
			return true;
		} else {
			count = 1;
		}
		for (int y = yIndex - 1; y >= 0; y--) {
			if (getChess(xIndex, y, c) != null) {
				count++;
			} else {
				break;
			}
		}
		for (int y = yIndex + 1; y <= COLS; y++) {
			if (getChess(xIndex, y, c) != null) {
				count++;
			} else {
				break;
			}
		}
		if (count >= 5) {
			return true;
		} else {
			count = 1;
		}
		for (int y = yIndex - 1, x = xIndex - 1; y >= 0 && x >= 0; x--, y--) {
			if (getChess(x, y, c) != null) {
				count++;
			} else {
				break;
			}
		}
		for (int y = yIndex + 1, x = xIndex + 1; x <= ROWS && y <= COLS; x++, y++) {
			if (getChess(x, y, c) != null) {
				count++;
			} else {
				break;
			}
		}
		if (count >= 5) {
			return true;
		} else {
			count = 1;
		}
		for (int y = yIndex - 1, x = xIndex + 1; y >= 0 && x <= ROWS; x++, y--) {
			if (getChess(x, y, c) != null) {
				count++;
			} else {
				break;
			}
		}
		for (int y = yIndex + 1, x = xIndex - 1; x >= 0 && y <= COLS; x--, y++) {
			if (getChess(x, y, c) != null) {
				count++;
			} else {
				break;
			}
		}
		if (count >= 5) {
			return true;
		} else {
			count = 1;
		}

		return false;
	}

	/**
	 * 绘制棋盘
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i <= ROWS; i++) {
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS * GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i <= COLS; i++) {
			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + ROWS * GRID_SPAN);
		}
		for (int i = 0; i < chessCount; i++) {
			int xPos = chessList[i].getX() * GRID_SPAN + MARGIN;
			int yPos = chessList[i].getY() * GRID_SPAN + MARGIN;
			g.setColor(chessList[i].getColor());
			g.fillOval(xPos - Point.DIAMETER / 2, yPos - Point.DIAMETER / 2, Point.DIAMETER, Point.DIAMETER);
			if (i == chessCount - 1) {
				g.setColor(Color.red);// 标记最后一个棋子为红色
				g.drawRect(xPos - Point.DIAMETER / 2, yPos - Point.DIAMETER / 2, Point.DIAMETER, Point.DIAMETER);
			}
		}

	}

	/**
	 * 功能: 机器人下棋线程
	 */
	public synchronized void robotChess() {
		System.out.println("机器线程开启");
		synchronized (chessPanel) {
			while (true) {
				if (!lock) {
					try {
						wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					int[] XY = ComTurn(xIndex, yIndex);
					Point ch = new Point(XY[0], XY[1], Color.white);
					chessList[chessCount++] = ch;
					repaint();
					lock = false;
					isCanplay = true;
					if (this.isWin(XY[0], XY[1], Color.white)) {
						String msg = "菜鸡，输给电脑了！！！";
						JOptionPane.showMessageDialog(this, msg);
						gameOver = true;
					}
					chessPanel.notifyAll();
				}
			}
		}
	}

	// 重新开始游戏
	public void restartGame() {
		for (int i = 0; i < chessList.length; i++) {
			chessList[i] = null;
		}
		gameOver = false;
		chessCount = 0;
		repaint();
		if (flag == 2) {
			isCanplay = false;
			MyClient.getMyClient().getRoom().isReady.setEnabled(true);
			MyClient.getMyClient().getRoom().repaint();
		} else {
			isCanplay = true;
		}
	}

	// 悔棋
	public void goBack() {
		if (chessCount == 0) {
			return;
		}
		chessList[chessCount - 1] = null;
		chessCount--;
		if (chessCount > 0) {
			xIndex = chessList[chessCount - 1].getX();
			yIndex = chessList[chessCount - 1].getY();
		}

		if (flag == 1) {
			chessList[chessCount - 1] = null;
			chessCount--;
			if (chessCount > 0) {
				xIndex = chessList[chessCount - 1].getX();
				yIndex = chessList[chessCount - 1].getY();
			}
		}
		repaint();
		if (flag == 2) {
			MyClient.getMyClient().getRoom().repaint();
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(MARGIN * 2 + GRID_SPAN * COLS, MARGIN * 2 + GRID_SPAN * ROWS);
	}

	public int[] ComTurn(int x, int y) { // 找出电脑（白子）最佳落子点
		int[] index = new int[2];
		int point = 0;
		for (int m = 0; m < 18; m++) {
			for (int n = 0; n < 18; n++) {
				if (!findChess(m, n)) {
					int temp = countPoint(m, n);
					if (temp > point) {
						System.out.println("temp = " + temp);
						System.out.println("point = " + point);
						index[0] = m;
						index[1] = n;
						point = temp;
					}
				}
			}
		}

		return index;
	}

	public int countPoint(int xIndex, int yIndex) {// 计算棋盘上每个点的分值
		int count = 1;
		int point = 0;
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		Color c = Color.white;
		for (int x = xIndex - 1; x >= 0; x--) {
			x1 = x;
			y1 = yIndex;
			if (getChess(x, yIndex, c) != null) {
				count++;
			} else {
				break;
			}
		}
		for (int x = xIndex + 1; x <= ROWS; x++) {
			x2 = x;
			y2 = yIndex;
			if (getChess(x, yIndex, c) != null) {
				count++;
			} else {
				break;
			}
		}

		point += changePoint(x1, y1, x2, y2, count);
		count = 1;
		for (int y = yIndex - 1; y >= 0; y--) {
			x1 = xIndex;
			y1 = y;
			if (getChess(xIndex, y, c) != null) {
				count++;
			} else {
				break;
			}
		}
		for (int y = yIndex + 1; y <= COLS; y++) {
			x2 = xIndex;
			y2 = y;
			if (getChess(xIndex, y, c) != null) {
				count++;
			} else {
				break;
			}
		}
		point += changePoint(x1, y1, x2, y2, count);
		count = 1;
		for (int y = yIndex - 1, x = xIndex - 1; y >= 0 && x >= 0; x--, y--) {
			x1 = x;
			y1 = y;
			if (getChess(x, y, c) != null) {
				count++;
			} else {
				break;
			}
		}
		for (int y = yIndex + 1, x = xIndex + 1; x <= ROWS && y <= COLS; x++, y++) {
			x2 = x;
			y2 = y;
			if (getChess(x, y, c) != null) {
				count++;
			} else {
				break;
			}
		}
		point += changePoint(x1, y1, x2, y2, count);
		count = 1;
		for (int y = yIndex - 1, x = xIndex + 1; y >= 0 && x <= ROWS; x++, y--) {
			x1 = x;
			y1 = y;
			if (getChess(x, y, c) != null) {
				count++;
			} else {
				break;
			}
		}
		for (int y = yIndex + 1, x = xIndex - 1; x >= 0 && y <= COLS; x--, y++) {
			x2 = x;
			y2 = y;
			if (getChess(x, y, c) != null) {
				count++;
			} else {
				break;
			}
		}
		point += changePoint(x1, y1, x2, y2, count);
		count = 0;
		c = Color.black;
		for (int x = xIndex - 1; x >= 0; x--) {
			x1 = x;
			y1 = yIndex;
			if (getChess(x, yIndex, c) != null) {
				count--;
			} else {
				break;
			}
		}
		for (int x = xIndex + 1; x <= ROWS; x++) {
			x2 = x;
			y2 = yIndex;
			if (getChess(x, yIndex, c) != null) {
				count--;
			} else {
				break;
			}
		}
		point += changePoint(x1, y1, x2, y2, count);
		count = 0;
		for (int y = yIndex - 1; y >= 0; y--) {
			x1 = xIndex;
			y1 = y;
			if (getChess(xIndex, y, c) != null) {
				count--;
			} else {
				break;
			}
		}
		for (int y = yIndex + 1; y <= COLS; y++) {
			x2 = xIndex;
			y2 = y;
			if (getChess(xIndex, y, c) != null) {
				count--;
			} else {
				break;
			}
		}
		point += changePoint(x1, y1, x2, y2, count);
		count = 0;
		for (int y = yIndex - 1, x = xIndex - 1; y >= 0 && x >= 0; x--, y--) {
			x1 = x;
			y1 = y;
			if (getChess(x, y, c) != null) {
				count--;
			} else {
				break;
			}
		}
		for (int y = yIndex + 1, x = xIndex + 1; x <= ROWS && y <= COLS; x++, y++) {
			x2 = x;
			y2 = y;
			if (getChess(x, y, c) != null) {
				count--;
			} else {
				break;
			}
		}
		point += changePoint(x1, y1, x2, y2, count);
		count = 0;
		for (int y = yIndex - 1, x = xIndex + 1; y >= 0 && x <= ROWS; x++, y--) {
			x1 = x;
			y1 = y;
			if (getChess(x, y, c) != null) {
				count--;
			} else {
				break;
			}
		}
		for (int y = yIndex + 1, x = xIndex - 1; x >= 0 && y <= COLS; x--, y++) {
			x2 = x;
			y2 = y;
			if (getChess(x, y, c) != null) {
				count--;
			} else {
				break;
			}
		}
		point += changePoint(x1, y1, x2, y2, count);

		return point;
	}

	public int changePoint(int x1, int y1, int x2, int y2, int count) {// 每种情形的分值
		int point = 0;
		// count>0表示白子，也就是电脑
		if (count > 0) {// 如果落子在这个位置会产生的结果，不是现存
			// 五子及以上
			if (count >= 5) {
				point = 100000;
			}
			// 活两子
			if (count == 2 && !findChess(x1, y1) && !findChess(x2, y2)) {
				point = 4;
			}
			// 单边两子
			if (count == 2 && (!findChess(x1, y1) || !findChess(x2, y2))) {
				point = 2;
			}
			// 死两子
			if (count == 2 && (findChess(x1, y1) && findChess(x2, y2))) {
				point = -3;
			}
			// 死三子
			if (count == 3 && (findChess(x1, y1) && findChess(x2, y2))) {
				point = 0;
			}
			// 活三子
			if (count == 3 && !findChess(x1, y1) && !findChess(x2, y2)) {
				point = 60;
			}
			// 半三子
			if (count == 3 && (!findChess(x1, y1) || !findChess(x2, y2))) {
				point = 20;
			}
			// 活四
			if (count == 4 && !findChess(x1, y1) && !findChess(x2, y2)) {
				point = 800;
			}
			// 半四
			if (count == 4 && (!findChess(x1, y1) || !findChess(x2, y2))) {
				point = 500;
			}
			// 死四
			if (count == 4 && (findChess(x1, y1) && findChess(x2, y2))) {
				point = 0;
			}
		} else {// 现有形式
			count = -count;
			if (count == 1) {
				point = 1;
			}
			if (count == 2 && !findChess(x1, y1) && !findChess(x2, y2)) {
				point = 15;
			}
			if (count == 2 && (!findChess(x1, y1) || !findChess(x2, y2))) {
				point = 3;
			}
			if (count == 2 && (findChess(x1, y1) && findChess(x2, y2))) {
				point = -3;
			}
			if (count == 3 && (findChess(x1, y1) && findChess(x2, y2))) {
				point = 0;
			}
			if (count == 3 && !findChess(x1, y1) && !findChess(x2, y2)) {
				point = 300;
			}
			if (count == 3 && (!findChess(x1, y1) || !findChess(x2, y2))) {
				point = 40;
			}
			if (count == 4 && !findChess(x1, y1) && !findChess(x2, y2)) {
				point = 10000;
			}
			if (count == 4 && (!findChess(x1, y1) || !findChess(x2, y2))) {
				point = 10000;
			}
			if (count == 4 && (findChess(x1, y1) && findChess(x2, y2))) {
				point = 0;
			}
		}
		return point;
	}
}
