import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.Context; 
import java.time.LocalDateTime;
//import updateUserLocationConfig;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class updateLocation implements RequestHandler<Object, String> {

    public String handleRequest(Object input, Context context) {
        String endpoint = updateUserLocationConfig.db_url;
        String port = updateUserLocationConfig.db_port;
        String db_name = updateUserLocationConfig.db_name;
		String username = updateUserLocationConfig.db_username;
		String pw = updateUserLocationConfig.db_password;

		String url = "jdbc:postgresql://" + endpoint + ":" + port + "/" + db_name + "?user=" + username + "&password=" + pw;
		LambdaLogger logger = context.getLogger();

        try {
        	Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			LocalDateTime ldt = LocalDateTime.now();
			stmt.executeQuery("UPDATE users SET latitude = 55, longitude = 66, updated_date = '" + String.valueOf(ldt) + "' WHERE user_id = " + "1");
			conn.close();
			return "Success "+ url;
		}
		catch(ClassNotFoundException e){ 
			return "classerror";
		}
		catch(SQLException e){
			/*e.toString();
            log.warn(e.toString());
            System.out.println(e + "\nSQLException");*/
                            // Need to figure out what to do if there is an error
            return "Failed Exception: " + e.getMessage() + "  getCause: " + e.getCause() + url + " " + username + " " + pw;
		}
    }

}