package com.asn;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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
    
	private WorkDataCRUD workDataCRUD;
	
    /**
     * Register driver class and intialize the Connection object
     * 
     * @throws ClassNotFoundException 
     * @throws SQLException 
     * @see HttpServlet#HttpServlet()
     */
    public GetHoursSQL() throws ClassNotFoundException, SQLException {
        super();
        this.workDataCRUD = WorkDataCRUD.getInstance();
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
    	
    	List<WorkData> workDataList = null;
		try {
			workDataList = workDataCRUD.loadRecordByQuery(null);
		} catch (ParseException | SQLException e) {
			e.printStackTrace();
		}
    	    			
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
		
		List<WorkData> workDataList = null;
		try {
			workDataList = workDataCRUD.loadRecordByQuery(startDate, endDate);
		} catch (ParseException | SQLException e) {			
			e.printStackTrace();
		}
		
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");			
		res.getWriter().write(new Gson().toJson(workDataList));
	}
	
}
