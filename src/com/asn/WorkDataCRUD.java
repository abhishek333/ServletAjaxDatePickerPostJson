/**
 * 
 */
package com.asn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Abhishek
 *
 */
public class WorkDataCRUD {
	
	private static volatile WorkDataCRUD SINGLETONWORKDATACRUD = null;
	
	/**
	 * Register driver class and intialize the Connection object
     * 
	 */
	private WorkDataCRUD(){
		System.out.println("WorkDataCRUD instance created..");        
	}
	
	public static synchronized WorkDataCRUD getInstance() {
		/**
		 * This is called "Double-Checked Locking idiom". It's easy to forget the volatile statement and difficult to understand why it is necessary.
		 *For details : http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
		 */
	    if (SINGLETONWORKDATACRUD == null) { // Check 1
	    	synchronized (WorkDataCRUD.class) {
                if (SINGLETONWORKDATACRUD == null) { // Check 2
                	SINGLETONWORKDATACRUD = new WorkDataCRUD();
                }
	    	}	    	
	    }
		return SINGLETONWORKDATACRUD;
	}
		
	public Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {		
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");		
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
	 * @throws SQLException 
	 * @throws ParseException 
	 * 
	 * @see com.asn.WorkData WorkData
	 */
	public List<WorkData> loadRecordByQuery(String... dates) throws ParseException, SQLException{		
		List<WorkData> workDataList = new ArrayList<WorkData>();
		
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		
		try{
			connection = getConnection();		
			if(dates!=null){
				String query = "select P_id, Date, Name, Address, Day_hours, Day_minutes, km_to_address, Time_to_address, "+ 
							 " ROUND((Day_hours + (Day_minutes)/100),2) as Allday_hours "+
							 "from Workdata"+
							 " where Date between ? and ?";
	
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
				
			rSet = pstmt.executeQuery();
				
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
		}catch(SQLException e){
			throw e;
		}finally{
			try { if (rSet != null) rSet.close(); } catch (Exception e) {};
		    try { if (pstmt != null) pstmt.close(); } catch (Exception e) {};
		    try { if (connection != null) connection.close(); } catch (Exception e) {};
		}
		return workDataList;
	}

	/**
	 * saves a new WorkData object into database.
	 * @param workData
	 * @return the same WorkData object with the id in database.
	 * @throws SQLException
	 */
	public WorkData saveWork(final WorkData workData) throws SQLException{		
		final String saveQuery = "insert into "
				+ "Workdata(Date, Name, Address, Day_hours, Day_minutes, km_to_address, Time_to_address)"
				+ " values(?,?,?,?,?,?,?)";
		
		Connection connection = null;
		PreparedStatement psmt = null;
		ResultSet rSet = null;
		
		try{
			connection = getConnection();		
			
			psmt = connection.prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS);
			psmt.setDate(1, new java.sql.Date(workData.getDate().getTime()));
			psmt.setString(2, workData.getName());
			psmt.setString(3, workData.getAddress());
			psmt.setInt(4, workData.getDayHours());
			psmt.setInt(5, workData.getDayMinutes());
			psmt.setInt(6, workData.getKmDistanceAddr());
			psmt.setFloat(7, workData.getTimeToTravel());
			
			psmt.executeUpdate();
			rSet = psmt.getGeneratedKeys();
			
			if(rSet.next())
				workData.setpId(rSet.getLong(1));
			
		}catch(SQLException e){
			throw e;
		}finally{
			try { if (rSet != null) rSet.close(); } catch (Exception e) {};
		    try { if (psmt != null) psmt.close(); } catch (Exception e) {};
		    try { if (connection != null) connection.close(); } catch (Exception e) {};
		}
		return workData;
	}

}
