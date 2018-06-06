import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
//import updateUserLocationConfig;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class updateLocation implements RequestHandler<userLocation, Void> {

    public Void handleRequest(userLocation input, Context context) {
        String endpoint = "";
        String port = "";
        String db_name = "";
		String username = "";
		String pw = "";

		String url = "jdbc:postgresql://" + endpoint + ":" + port + "/" + db_name + "?user=" + username + "&password=" + pw;

        try{
        	Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection(url);
			PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET latitude = ?, longitude = ?, updated_date = ? WHERE user_id = ?");
			Timestamp ldt = new Timestamp(new java.util.Date().getTime());
			pstmt.setDouble(1, input.getLatitude());
   			pstmt.setDouble(2, input.getLongitude());
   			pstmt.setTimestamp(3, ldt);
   			pstmt.setInt(4, input.getUserId());
   			pstmt.execute();
			pstmt.close();
			conn.close();
		}
		catch(SQLException e){
            context.getLogger().log(e.getMessage());
		}
		catch(Exception e){
			context.getLogger().log(e.getMessage());
		}

		return null;
    }
}