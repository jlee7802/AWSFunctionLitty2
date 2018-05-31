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

	//private static Logger log = Logger.getLogger(LambdaConnection.class.getName());

    public String handleRequest(Object input, Context context) {
        /*String url = updateUserLocationConfig.db_url;
		String username = updateUserLocationConfig.db_username;
		String pw = updateUserLocationConfig.db_password;*/

		String url = "jdbc:postgresql://" + "litty.cgbqmmmv1tjp.us-east-1.rds.amazonaws.com" + ":" + "5432" + "/" + "Litty" + "?user=" + "jlee" + "&password=" + "***REMOVED***";
		String username = "jlee";
		String pw  = "***REMOVED***";
		LambdaLogger logger = context.getLogger();

        try {
        	Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			LocalDateTime ldt = LocalDateTime.now();
			stmt.executeQuery("UPDATE users SET latitude = 1, longitude = 1, updated_date = '" + String.valueOf(ldt) + "' WHERE user_id = " + "1");
			conn.close();
			return "Success "+ url + " " + username + " " + pw;
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