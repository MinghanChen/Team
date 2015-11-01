package q3servlet;

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

/**
 * Servlet implementation class Q3servlet
 */
public class Q3servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Q3servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//q3	
			Connection conn3 = null;
			final String DBDRIVER3 = "org.gjt.mm.mysql.Driver";
			final String DBURL3 = "jdbc:mysql://localhost:3306/tweets";
			final String DBUSER3 = "Gundam";
			final String DBPASS3 = "15619";

			try {
				Class.forName(DBDRIVER3);
				conn3 = DriverManager.getConnection(DBURL3, DBUSER3, DBPASS3);

				Statement stmt3 = conn3.createStatement();
				String sql3 = "USE tweets";
				stmt3.executeUpdate(sql3);}
			catch (Exception e){}
			res.setContentType("text/plain;charset=UTF-8");
			String info3 = req.getQueryString();
			String userid = info3.substring(7);
			//System.out.println(userid);
			PrintWriter sos3 = res.getWriter();
			sos3.write("GUNDAM,6838-9567-0525,6024-8213-6870,1743-9149-4421\n");
			try{
				// search MySQL
				Statement st3 = conn3.createStatement();
				String query3 = "SELECT retweets FROM q3 WHERE user_id = \"" + userid + "\" ";
				ResultSet rs3 = st3.executeQuery(query3);
				 //PreparedStatement search = conn.prepareStatement( "select retweets from q3 where user_id=?");
		          //search.setString(1, userid);
		         // ResultSet rs = search.executeQuery();

				while (rs3.next()) {
					StringBuffer sb3 = new StringBuffer();
					String user_id_primitive = rs3.getString("retweets");
					String user_id = user_id_primitive.trim();
					String[] values = user_id.split(" ");
					for (int i = 0; i < values.length; i++){
						sb3.append(values[i] + "\n");
					}
					sos3.write(sb3.toString());
				}}
			catch(Exception e){}
			finally{	
				if(conn3!=null){
					try{conn3.close();}catch(SQLException e ){e.printStackTrace();}
				}
				sos3.flush();
				sos3.close();	
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
