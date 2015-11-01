package q6server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Q6_Servlet extends HttpServlet {
	private HikariDataSource ds;

	public void init() throws ServletException {
		super.init();
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(100);
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.addDataSourceProperty("serverName", "localhost");
		config.addDataSourceProperty("port", "3306");
		config.addDataSourceProperty("databaseName", "tweet");
		config.addDataSourceProperty("user", "Gundam");
		config.addDataSourceProperty("password", "15619");
		this.ds = new HikariDataSource(config);
	}
	
	public int getCountm(long m){
		Connection conn = null;
		int countm=0;
		if(m<=12){
			return countm;
		}
		else{
			try {
				conn = this.ds.getConnection();
	//			Statement st1 = conn.createStatement();
	//			String query = "SELECT score FROM q5 WHERE userid= \"" + m
	//					+ "\"LIMIT 1";
				PreparedStatement stmt = conn.prepareStatement("select count from q6 where user_id=(select max(user_id) from q6 where user_id < ?)");
				stmt.setLong(1, m);
				ResultSet rs = stmt.executeQuery();
				
				 if (rs.next()){
					 countm = rs.getInt("count");
					 //sb.append(output + "\n");
				 }
			} catch (SQLException e) {
			}
			finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
			return countm; 
		}	
	}
	
	public int getCountn(long n){
		Connection conn = null;
		int countn=48856657;
		if(n>=2594921025.0){
			return countn;
		}
		try {
			conn = this.ds.getConnection();
//			Statement st1 = conn.createStatement();
//			String query = "SELECT score FROM q5 WHERE userid= \"" + m
//					+ "\"LIMIT 1";
			PreparedStatement stmt = conn.prepareStatement("select count from q6 where user_id=(select max(user_id) from q6 where user_id <=?)");
			stmt.setLong(1, n);
			ResultSet rs = stmt.executeQuery();
			
			 if (rs.next()){
				 countn = rs.getInt("count");
				 //sb.append(output + "\n");
			 }
		} catch (SQLException e) {
		}
		finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return countn; 
	}
	
	

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		Connection conn = null;
		//res.setContentType("text/plain;charset=UTF-8");
		long m = Long.parseLong(req.getParameter("m"));
		long n = Long.parseLong(req.getParameter("n"));
		int countm, countn,countresult;
		
		if(m<n){
			countm = getCountm(m);
			countn = getCountn(n);
			countresult = countn - countm;
		}
		else{
			countresult = 0;
		}
		PrintWriter sos1 = res.getWriter();
		StringBuffer sb = new StringBuffer();
		sb.append("GUNDAM,6838-9567-0525,6024-8213-6870,1743-9149-4421\n")
			.append(countresult)
			.append("\n");
		sos1.print(sb.toString());
		
//		String date = req.getParameter("date");
//		String location = req.getParameter("location");
//		String minRank = req.getParameter("m");
//		String maxRank = req.getParameter("n");
//		String time_place  = new StringBuilder().append(date).append(location).toString();
//		try {
//			conn = this.ds.getConnection();
////			Statement st1 = conn.createStatement();
////			String query = "SELECT score FROM q5 WHERE userid= \"" + m
////					+ "\"LIMIT 1";
//			PreparedStatement stmt = conn.prepareStatement("select count from q6 where user_id=(select max(user_id) from q6 where user_id < ?)");
//			stmt.setLong(1, m);
//			ResultSet rs = stmt.executeQuery();
//			
//			 if (rs.next()){
//				 int countm = rs.getInt("count");
//				 //sb.append(output + "\n");
//			 }
//			StringBuffer sb = new StringBuffer();
//			sos1.print("GUNDAM,6838-9567-0525,6024-8213-6870,1743-9149-4421\n"
//					+ sb.toString());
//
//		} catch (SQLException e) {
//			sos1.println("error_3");
//		}
//		finally {
//			sos1.close();
//			if (conn != null)
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//		}
		

	}
}
