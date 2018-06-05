package entity;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import msg.ClientLoginMsg;
import msg.ClientUpdateUserMsg;
import net.MyClient;

/**
 * 更新玩家的头像信息窗口类
 */

public class UpdatePicture extends JFrame {
  public UpdatePicture u = this;
  private User user;
  public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}
private JButton btnNewButton;
  private JButton btnNewButton_1;
  private JButton btnNewButton_2;
  private JButton btnNewButton_3;
  private JButton btnNewButton_4;
  private JButton btnNewButton_5;
  private JButton btnNewButton_6;
  private JButton btnNewButton_7;
  private JButton btnNewButton_8;
  private JButton btnNewButton_9;
  JButton btnNewButton_10;
  JButton btnNewButton_11;
  JButton btnNewButton_12;
  JButton btnNewButton_13;
  JButton btnNewButton_14;
  JButton btnNewButton_15;
  JButton btnNewButton_16;
  JButton btnNewButton_17;
  JButton btnNewButton_18;

  /**
   * @param flag 1 注册新用户 0 更新头像信息
   */

  public UpdatePicture(User user,int flag) {
    this.user = user;
    this.setBounds(100, 100, 400, 400);
    this.setResizable(false);
    getContentPane().setLayout(new GridLayout(5, 10));
    btnNewButton = new JButton(new ImageIcon("resource/photo/1.jpg"));
    getContentPane().add(btnNewButton);
    btnNewButton_1 = new JButton(new ImageIcon("resource/photo/2.jpg"));
    getContentPane().add(btnNewButton_1);
    btnNewButton_2 = new JButton(new ImageIcon("resource/photo/3.jpg"));
    getContentPane().add(btnNewButton_2);
    btnNewButton_3 = new JButton(new ImageIcon("resource/photo/4.jpg"));
    getContentPane().add(btnNewButton_3);
    btnNewButton_4 = new JButton(new ImageIcon("resource/photo/5.jpg"));
    getContentPane().add(btnNewButton_4);
    btnNewButton_5 = new JButton(new ImageIcon("resource/photo/6.jpg"));
    getContentPane().add(btnNewButton_5);
    btnNewButton_6 = new JButton(new ImageIcon("resource/photo/7.jpg"));
    getContentPane().add(btnNewButton_6);
    btnNewButton_7 = new JButton(new ImageIcon("resource/photo/8.jpg"));
    getContentPane().add(btnNewButton_7);
    btnNewButton_8 = new JButton(new ImageIcon("resource/photo/9.jpg"));
    getContentPane().add(btnNewButton_8);
    btnNewButton_9 = new JButton(new ImageIcon("resource/photo/10.jpg"));
    getContentPane().add(btnNewButton_9);
    btnNewButton_10 = new JButton(new ImageIcon("resource/photo/11.jpg"));
    getContentPane().add(btnNewButton_10);
    btnNewButton_11 = new JButton(new ImageIcon("resource/photo/12.jpg"));
    getContentPane().add(btnNewButton_11);
    btnNewButton_12 = new JButton(new ImageIcon("resource/photo/13.jpg"));
    getContentPane().add(btnNewButton_12);
    btnNewButton_13 = new JButton(new ImageIcon("resource/photo/14.jpg"));
    getContentPane().add(btnNewButton_13);
    btnNewButton_14 = new JButton(new ImageIcon("resource/photo/15.jpg"));
    getContentPane().add(btnNewButton_14);
    btnNewButton_15 = new JButton(new ImageIcon("resource/photo/16.jpg"));
    getContentPane().add(btnNewButton_15);
    btnNewButton_16 = new JButton(new ImageIcon("resource/photo/17.jpg"));
    getContentPane().add(btnNewButton_16);
    btnNewButton_17 = new JButton(new ImageIcon("resource/photo/18.jpg"));
    getContentPane().add(btnNewButton_17);
    btnNewButton_18 = new JButton(new ImageIcon("resource/photo/19.jpg"));
    getContentPane().add(btnNewButton_18);
    frameInit(flag);
  }

  public void frameInit(int flag) {

    btnNewButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/1.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
      

    });

    btnNewButton_1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/2.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/3.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/4.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });
    btnNewButton_4.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/5.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_5.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/6.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_6.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/7.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_7.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/8.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_8.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/9.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_9.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/10.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_10.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/11.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_11.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/12.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_12.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/13.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_13.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/14.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_14.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/15.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_15.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/16.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_16.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/17.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_17.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/18.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

    btnNewButton_18.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String root1 = "resource/photo/19.jpg";
        user.setFileName(root1);
        if(flag==1) login();
        else updatePhoto();
      }
    });

  }
  /**
   * 隐藏照片窗口，发出登录的信息
   */
  public void login(){
    dispose();
    ClientLoginMsg msg = new ClientLoginMsg(user.getName(),user.getFileName(),1);
    MyClient.getMyClient().sendMsg(msg);
  }
  /**
   * 更新完照片，把照片窗口隐藏
   */
  public void updatePhoto(){
	MyClient.getMyClient().sendMsg(new ClientUpdateUserMsg(MyClient.getMyClient().getRoom().getRoomId(),0,user));
    dispose();
  }

//  public static void main(String[] args) {
//    new UpdatePicture(new User("czf"),0);
//		
//	}

}

