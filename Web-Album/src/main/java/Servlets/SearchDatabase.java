/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchDatabase extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String category = request.getParameter("Categories");
            String company = request.getParameter("Tags");
            String description1 = request.getParameter("desc");
            String description = "'%"+description1+"%'";
            String party1 = request.getParameter("participants");
            String party = "'%"+party1+"%'";
            response.setContentType("text/html");  
            PrintWriter writer=response.getWriter();
            int counter = 0;
            Boolean myflag = false;
            
            StringBuilder myStatement = new StringBuilder();
            
            myStatement.append("SELECT * FROM Images ");
            System.out.println(description);
            System.out.println(myStatement);
            
            if (!category.equals("empty")) { 
                myStatement.append("WHERE CATEGORY='").append(category+"'");
                myflag = true;
                System.out.println(myStatement);
            }
            
            if (!company.equals("empty")) {
                if (myflag == true) {
                    myStatement.append("AND TAGS='"+company+"'");
                } else {
                    myStatement.append("WHERE TAGS='"+company+"'");
                    myflag = true;
                }
            }
                
            if (!description1.equals("")) {
                if (myflag == true) {
                    myStatement.append("AND DESCRIPTION LIKE"+description);
                    System.out.println(myStatement);
                }else {
                    myStatement.append("WHERE DESCRIPTION LIKE"+description);
                    myflag = true;
                }
            }
            
            if (!party1.equals("")) {
                if (myflag == true) {
                    myStatement.append("AND PARTICIPANTS LIKE"+party);
                    System.out.println(myStatement);
                }else {
                    myStatement.append("WHERE PARTICIPANTS LIKE"+party);
                    myflag = true;
                }
            }
                System.out.println(myStatement);
            
            
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement create = con.prepareStatement(myStatement.toString());
            myStatement.setLength(0);
            ResultSet rs;
            rs = create.executeQuery();
            writer.println( "<html>"
                          + "<head>"
                                + "<title>Start Page</title>"
                                + "<link rel=\"stylesheet\" href=\"Styles/Images.css\">"
                                + "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">"
                                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                          + "</head>"
                          + "<body>");
            PreparedStatement create2 = con.prepareStatement("select * from Images");
            ResultSet rs2;
            rs2 = create2.executeQuery();
            int count = 0;
            int maker_count = 0;
            String[] categories = new String[150];
            String[] maker = new String[150];
            writer.println( "<html>"
                            + "<head>"
                                + "<title>Start Page</title>"
                                + "<link rel=\"stylesheet\" href=\"Styles/Images.css\">"
                                + "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">"
                                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                            + "</head>"
                            + "<body>"
                                + "<div id=container-1>"
                                    + "<form class=form-container action='SearchDatabase' method='POST' >"
                                        + "<label for=Categories>Category</label>"
                                        + "<select name=Categories>"
                                            + "<option value=empty>Select</option>");
            while(rs2.next()) {
            categories[count] = rs2.getString("CATEGORY");
            Boolean category_flag = true;
            
            for (int k=0; k < count; k++){
                if (categories[k].equals(categories[count])) {
                    category_flag = false;
                }
            }
            if (category_flag == true) {
                writer.println("<option value="+categories[count]+">"+categories[count]+"</option>");
            }
            count++;
            }
            writer.println( "</select>"
                        + "<label for=Tags>Tags</label>"
                        + "<select name=Tags>"
                            + "<option value=empty>Select</option>");
            rs2 = create2.executeQuery();
            while(rs2.next()) {
                maker[maker_count] = rs2.getString("TAGS");
                Boolean maker_flag = true;

            for (int k=0; k < maker_count; k++){
                if (maker[k].equals(maker[maker_count])) {
                    maker_flag = false;
                }
            }
            if (maker_flag == true) {
                writer.println("<option value="+maker[maker_count]+">"+maker[maker_count]+"</option>");
            }
            maker_count++;
            }
            writer.println("</select>"
            +   "<label for=Make>Description</label>"
            +   "<input type=\"text\" name=\"desc\"></input>"
            + "<label for=participants>Participants</label>\n"
            +   "<input type=\"text\" name=\"participants\"></input>\n"
            +   "<input type=\"submit\" value=\"search\" class=\"search-btn\" />");
            
            
            writer.println("</form>"
                    + "</div>"
                    + "<div class='img-container'>");
            if (rs.next() == false) {
                writer.println("<h1>Not Such Image Found</h1>"
                +"<h1>Consider Changing The Search Combination</h1>");
            }else {
                
                do {
                int id =rs.getInt("ID");
                    String path = rs.getString("PATH");
                    String url = ("http://localhost:8080/mavenproject2/Fetch_Image?path="+path);
                    String Rm_url = ("http://localhost:8080/mavenproject2/Remove_Image?id="+id);
                    String View_url = ("http://localhost:8080/mavenproject2/Individual_Image_View?id="+id);
                    if (counter == 0) {
                        writer.println(""
                            + "<div class='row'>");
                    }
                    if (counter < 3) {
                        writer.println(""

                                + "<div class='col-sm-4'>"
                                    + "<div class='container'>"
                                        + "<img src="+url+" class='image'/>"
                                        + "<div class='middle'>"
                                            + " <div class='text'><a href='"+View_url+"'>View Details</a></div>"
                                        + "</div>"
                                        + "<div class='middle2'>"
                                            + " <div class='text2'><a class='a2' href='"+Rm_url+"'>Remove Image</a></div>"
                                        + "</div>"
                                    + "</div>"     
                                + "</div>");
                        counter++;
                    }
                    if (counter == 3) {
                      writer.println("</div>");
                      counter = 0;
                    }
                }while(rs.next());  
            }
                
                writer.println("</div>");
            writer.println(
                    
                        "</div>"
                    + "</body>"
                + "</html>");
            
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SearchDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchDatabase.class.getName()).log(Level.SEVERE, null, ex);
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
