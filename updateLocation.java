import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
//import updateUserLocationConfig;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class updateLocation implements RequestHandler<userLocation, String> {

    public String handleRequest(userLocation input, Context context) {
        String endpoint = "";
        String port = "";
        String db_name = "";
		String username = "";
		String pw = "";

		String url = "jdbc:postgresql://" + endpoint + ":" + port + "/" + db_name + "?user=" + username + "&password=" + pw;

        try {
        	Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			LocalDateTime ldt = LocalDateTime.now();
			stmt.executeQuery("UPDATE users SET latitude = " + input.getLatitude() + ", longitude = " + input.getLongitude() + ", updated_date = '" + String.valueOf(ldt) + "' WHERE user_id = " + input.getUserId());
			stmt.close();
			conn.close();
			return "Success "+ url;
		}
		catch(SQLException e){
			/*e.toString();
            log.warn(e.toString());
            System.out.println(e + "\nSQLException");*/
                            // Need to figure out what to do if there is an error
            context.getLogger().log(e.getMessage());
            return "Failed Exception: " + e.getMessage() + "  getCause: " + e.getCause() + url + " " + username + " " + pw;
		}
		catch(Exception e){
			context.getLogger().log("classerror1000" +e.getMessage());
			return "classerror";
		}
    }

}