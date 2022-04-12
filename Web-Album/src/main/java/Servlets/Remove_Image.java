/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.File;
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
public class Remove_Image extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int rs1;
        try {
            response.setContentType("text/html;charset=UTF-8");
            String Str_id = request.getParameter("id");
            long id = Integer.parseInt(Str_id);
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement st1 = con.prepareStatement("SELECT * FROM Images WHERE ID="+Integer.parseInt(Str_id));
            PreparedStatement st = con.prepareStatement("DELETE from Images WHERE ID=?");
            st.setLong(1, id);
            ResultSet rs;
            rs = st1.executeQuery();
            rs.next();
            String path = rs.getString("PATH");
            File file = new File(path);
            if (!file.isDirectory()) { 
                file.delete(); 
            }
            rs1 = st.executeUpdate();
            RequestDispatcher ds = request.getRequestDispatcher("FetchData");
            ds.include(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(Remove_Image.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Remove_Image.class.getName()).log(Level.SEVERE, null, ex);
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
