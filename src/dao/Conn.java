package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn { //数据库的连接
	public static Connection getConnection() {
		Connection con = null;
		final String url = "jdbc:mysql://localhost:3306/game?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		final String name = "root";
		final String password = "414899525"; 
		try {//加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {//通过访问数据库的url获取数据库连接对象
			con = DriverManager.getConnection(url,name,password);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 发出查询命令
	 */

	public static ResultSet doQuery(String sql, String[] paras) {
		// 创建psm --> sql
		Connection connection = getConnection();
		if (connection == null)
			return null;
		// 执行操作
		ResultSet rs = null;
		PreparedStatement psm = null;
		try {
			psm = connection.prepareStatement(sql);
			if (paras != null) {
				// 设置动态参数 --> ?
				int index = 1;
				for (String str : paras) {
					psm.setString(index++, str);
				}
			}
			rs = psm.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	//查找用户信息，返回整个用户信息
	// 以后看一看加上finanlly do close会怎么样
	public static ResultSet doQuery(String sql) {
		// 创建psm --> sql
		Connection connection = getConnection();
		if (connection == null)
			return null;
		// 执行操作
		ResultSet rs = null;
		PreparedStatement psm = null;
		try {
			psm = connection.prepareStatement(sql);
			rs = psm.executeQuery();
			return rs;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	//更新  不需要ResultSet
	public static int doUpdate(String sql, String[] paras) {
		// 创建psm --> sql
		Connection connection = getConnection();
		if (connection == null)
			return 0;
		// 执行操作
		PreparedStatement psm = null;
		try {
			psm = connection.prepareStatement(sql);
			if (paras != null) {
				// 设置动态参数 --> ?
				int index = 1;
				for (String str : paras) {
					psm.setString(index++, str);
				}
			}
			int result = psm.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}finally{
			doClose(connection,psm,null);
		}
	}

		public static void doClose(Connection conn, Statement psm,
			ResultSet rs) {
		// 关闭各种资源
		try {
			if (rs != null)
				rs.close();
			if (psm != null)
				psm.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean doInsert(String sql,String[] paras){
		Connection connection = getConnection();
		if (connection == null)
			return false;
		// 执行操作
		PreparedStatement psm = null;
		try {
			psm = connection.prepareStatement(sql);
			if (paras != null) {
				// 设置动态参数 --> ?
				int index = 1;
				for (String str : paras) {
					psm.setString(index++, str);
				}
			}
			boolean result = psm.execute();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			doClose(connection,psm,null);
		}
	}
}

	

	
	



