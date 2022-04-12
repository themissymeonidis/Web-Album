package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author muvox
 */
public class Change_Metadata extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String id = request.getParameter("id");
            String date = request.getParameter("date");
            String category = request.getParameter("category");
            String description = request.getParameter("description");
            String tags = request.getParameter("tags");
            String party = request.getParameter("party");
            String size = request.getParameter("size");
            String make1 = request.getParameter("make");
            String make = "'"+make1+"'";
            String model1 = request.getParameter("model");
            String model= "'"+model1+"'";
            String longtitude = request.getParameter("longtitude");
            String latitude = request.getParameter("latitude");

            Connection con;
            try {
                con = DatabaseConnection.initializeDatabase();
                PreparedStatement create = con.prepareStatement("UPDATE Images SET SIZE='"+size+"',DATE='"+date+"', CATEGORY='"+category+"', DESCRIPTION='"+description+"', TAGS='"+tags+"', PARTICIPANTS='"+party+"', MAKE="+make+", MODEL="+model+", LONGITUDE="+longtitude+", LATITUDE="+latitude+" WHERE ID="+id);
                System.out.println(create);
                create.executeUpdate();
                con.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(Change_Metadata.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Change_Metadata.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher ds = request.getRequestDispatcher("FetchData");
            ds.include(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
