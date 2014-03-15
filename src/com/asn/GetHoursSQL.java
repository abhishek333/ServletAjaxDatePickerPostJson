package com.asn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetHoursSQL
 */
@WebServlet("/getHoursSQL")
public class GetHoursSQL extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Connection connection;
	
    /**
     * Register driver class and intialize the Connection object
     * 
     * @throws ClassNotFoundException 
     * @throws SQLException 
     * @see HttpServlet#HttpServlet()
     */
    public GetHoursSQL() throws ClassNotFoundException, SQLException {
        super();
        Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "yourpassword");
    }

    /**
     * loads all WorkData records whatever exists in WorkData table.
     * 
     * @see GetHoursSQL#loadRecordByQuery(String... dates)
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) 
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	
    	List<WorkData> workDataList = loadRecordByQuery(null);
    	    			
    	resp.setContentType("application/json");
    	resp.setCharacterEncoding("UTF-8");			
    	resp.getWriter().write(new Gson().toJson(workDataList));
    }
    
	/**
	 * loads WorkData records which are in between the given dates from WorkData table.
	 * 
	 * @see GetHoursSQL#loadRecordByQuery(String... dates)
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");		
		
		List<WorkData> workDataList = loadRecordByQuery(startDate, endDate);
		
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");			
		res.getWriter().write(new Gson().toJson(workDataList));
	}

	/**
	 * Generates one PreparedStatement from:
	 * 	<ul>
	 * 		<li>If dates are given then, it will generate using the given dates.</li>
	 * 		<li>If dates are not given, then it will generate default.(All records are listed)</li>
	 * </ul>
	 * and if the two date strings are available, then creates the PreparedStatement based on the dates.
	 * @param dates pass two date string, OR pass null if you want to list all records. 
	 * @return a <code>List</code> with retrived WorkData records, else a empty List.
	 * 
	 * @see com.asn.WorkData WorkData
	 */
	private List<WorkData> loadRecordByQuery(String... dates){		
		List<WorkData> workDataList = new ArrayList<WorkData>();
		
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
		
		try {			
			PreparedStatement pstmt = null;
			if(dates!=null){
				String query = "select P_id, Date, Name, Address, Day_hours, Day_minutes, km_to_address, Time_to_address, "+ 
						 " ROUND((Day_hours + (Day_minutes)/100),2) as Allday_hours "+
						 "from Workdata"
						 + " where Date between ? and ?";

				pstmt = connection.prepareStatement(query);
				Date stDate = format.parse(dates[0]);
				Date edDate = format.parse(dates[1]);
				pstmt.setDate(1, new java.sql.Date(stDate.getTime()));
				pstmt.setDate(2, new java.sql.Date(edDate.getTime()));			
			}else{
				String query = "select P_id, Date, Name, Address, Day_hours, Day_minutes, km_to_address, Time_to_address, "+ 
		    			 " ROUND((Day_hours + (Day_minutes)/100),2) as Allday_hours "+
		    			 "from Workdata";
				pstmt = connection.prepareStatement(query);
			}
			
			ResultSet rSet = pstmt.executeQuery();
			
			while(rSet.next()){
				WorkData workData = new WorkData(rSet.getLong("P_id"),
						rSet.getDate("Date"), 
						rSet.getString("name"),
						rSet.getString("Address"),
						rSet.getInt("Day_hours"),
						rSet.getInt("Day_minutes"),
						rSet.getFloat("Allday_hours"),
						rSet.getInt("km_to_address"),
						rSet.getFloat("Time_to_address"));
				workDataList.add(workData);
			}					
			
		} catch (SQLException | ParseException e) {			
			e.printStackTrace();
		}
		return workDataList;
	}
	
}
