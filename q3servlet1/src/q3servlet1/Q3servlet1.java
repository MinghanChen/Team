package q3servlet1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebServlet({ "/q3" })
public class Q3servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String DBDRIVER3 = "org.gjt.mm.mysql.Driver";
	final String DBURL3 = "jdbc:mysql://localhost:3306/tweets";
	final String DBUSER3 = "Gundam";
	final String DBPASS3 = "15619";

	public void init() throws ServletException {
		super.init();

	}

	public String query(String userid) {
		StringBuffer sb = new StringBuffer();
		sb.append("GUNDAM,6838-9567-0525,6024-8213-6870,1743-9149-4421\n");
		Connection conn = null;
		try {
			Class.forName(DBDRIVER3);
			conn = DriverManager.getConnection(DBURL3, DBUSER3, DBPASS3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("select * from q3 where user_id=?;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.setString(1, userid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (rs.next()) {
				String retweetsId = null;
				try {
					retweetsId = rs.getString("content");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] str = retweetsId.split("\\s+");
				// sb.append(str.length);
				for (int i = 0; i < str.length; i++) {
					sb.append(str[i]);
					sb.append("\n");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return sb.toString();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String userid = req.getParameter("userid");
		// us
		String result = null;

		result = query(userid);

		// TODO Auto-generated catch block

		res.setContentType("text/plain");

		res.getWriter().write(result);

	}

}
