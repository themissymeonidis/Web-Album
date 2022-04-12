
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

public class FetchData extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html");  
         PrintWriter writer=response.getWriter();
         int counter = 0;
         
        try {
            
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement create = con.prepareStatement("select * from Images");
            PreparedStatement create2 = con.prepareStatement("select * from Images");
            ResultSet rs;
            ResultSet rs2;
            rs = create.executeQuery();
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
                    + "<form class=form-container action='SearchDatabase' method='POST' >\n"
                    + "<label for=Categories>Category</label>\n"
                     +   "<select name=Categories>\n"
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
            writer.println( "</select>\n"
                    + "<label for=Tags>Tags</label>\n"
            +"<select name=Tags>\n"
            + "<option value=empty>Select</option>");
            rs2 = create2.executeQuery();
            while(rs2.next()) {
                maker[maker_count] = rs2.getString("TAGS");
                Boolean maker_flag = true;
                
            for (int k=0; k < maker_count; k++){
                    if (maker[k].equals(maker[maker_count])){
                    maker_flag = false;
                }
            }
            if (maker_flag == true) {
                writer.println("<option value="+maker[maker_count]+">"+maker[maker_count]+"</option>");
            }
            maker_count++;
            }
            
            writer.println("</select>"
            + "<label for=Make>Description</label>\n"
            +   "<input type=\"text\" name=\"desc\"></input>\n"
            + "<label for=participants>Participants</label>\n"
            +   "<input type=\"text\" name=\"participants\"></input>\n"
            +   "<input type=\"submit\" value=\"search\" class=\"search-btn\" />");
            
            writer.println("</form>"
                    + "</div>"
                                + "<div class='img-container'>");
            while(rs.next()){
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
            }
            
            writer.println("</div>");
            writer.println(
                    
                        "</div>"
                    + "</body>"
                + "</html>");
            
        } catch (SQLException ex) {
            Logger.getLogger(FetchData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FetchData.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
