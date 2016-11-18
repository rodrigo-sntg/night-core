
package night.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Conexao {
	
	public static Connection getConnection() 
			throws ClassNotFoundException, 
		SQLException{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/night";
		String user = "root";
		String password = "rodrigo";
		Class.forName( driver );
		Connection conn = 
				DriverManager.getConnection( url, user, password);

		return conn;
		
		

	}

}
