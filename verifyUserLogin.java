import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.sql.ResultSet;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class verifyUserLogin implements RequestHandler<userCredential, Integer> {

	public Integer handleRequest(userCredential input, Context context){
		String endpoint = "";
        String port = "";
        String db_name = "";
		String username = "";
		String pw = "";

		String url = "jdbc:postgresql://" + endpoint + ":" + port + "/" + db_name + "?user=" + username + "&password=" + pw;
		
		try{
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection(url);
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1");
			pstmt.setString(1, input.getUsername());
   			pstmt.setString(2, input.getPassword());
   			ResultSet rs = pstmt.executeQuery();
   			if(rs.next())
   				return rs.getInt("user_id");
   			else
   				return 0;
		}
		catch(Exception e){
			context.getLogger().log(e.getMessage());
			return 1337;
		}
	}
}