import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
//import updateUserLocationConfig;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class getTopMFCountLocations implements RequestHandler<Void, List<locationObj>> {

    public List<locationObj> handleRequest(Void input, Context context) {
        String endpoint = "";
        String port = "";
        String db_name = "";
		String username = "";
		String pw = "";
		List<locationObj> locationObjList = new ArrayList<locationObj>();

		String url = "jdbc:postgresql://" + endpoint + ":" + port + "/" + db_name + "?user=" + username + "&password=" + pw;

        try{
        	Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM location ORDER BY mfCount DESC LIMIT 24";
			ResultSet rs = stmt.executeQuery(query);
   			while(rs.next())
   			{
   				locationObj obj = new locationObj(rs.getString("location_name"), rs.getInt("mcount"), rs.getInt("fcount"), rs.getInt("mfcount"), rs.getString("location_desc"));
   				locationObjList.add(obj);
   			}
   			conn.close();
   			stmt.close();
		}
		catch(SQLException e){
            context.getLogger().log(e.getMessage());
		}
		catch(Exception e){
			context.getLogger().log(e.getMessage());
		}

		return locationObjList;
    }
}