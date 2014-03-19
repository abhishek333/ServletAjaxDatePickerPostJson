package com.asn;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class AddWorkData
 */
@WebServlet("/addWork")
public class AddWorkData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WorkDataCRUD workDataCRUD;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddWorkData() {
        super();     
        this.workDataCRUD = WorkDataCRUD.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println(request.getParameter("jsonNewWorkData"));
		Gson gson = new GsonBuilder().setDateFormat("yy-mm-dd").create();
		WorkData workData = gson.fromJson(request.getParameter("jsonNewWorkData"), WorkData.class);
		try {
			workData = workDataCRUD.saveWork(workData);
			response.setContentType("application/json");
	    	response.setCharacterEncoding("UTF-8");			
	    	response.getWriter().write(new Gson().toJson(workData));
		} catch (SQLException e) {
			response.setCharacterEncoding("UTF-8");
	    	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    	String errMsg = "Oops, there is problem in database transaction.";
	    	response.getWriter().write(errMsg);
	    	response.flushBuffer();
			
			e.printStackTrace();			
		}
	}

}
