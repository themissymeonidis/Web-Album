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

/**
 *
 * @author muvox
 */
public class Individual_Image_View extends HttpServlet {

 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {  
            response.setContentType("text/html");
            PrintWriter writer=response.getWriter();
            String id = request.getParameter("id");
            System.out.println(id);
            
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement create = con.prepareStatement("select * from Images WHERE ID="+id);
            ResultSet rs;
            rs = create.executeQuery();
            rs.next();
            double size = rs.getDouble("SIZE");
            System.out.println(size);
            String path = rs.getString("PATH");
            String date = rs.getString("DATE");
            String width = rs.getString("EXIF_WIDTH");
            String lenght = rs.getString("EXIF_LENGHT");
            String category = rs.getString("CATEGORY");
            String description = rs.getString("DESCRIPTION");
            double longtitude = rs.getFloat("LONGITUDE");
            double latitude = rs.getFloat("LATITUDE");
            String name = rs.getString("FILE_NAME");
            String make = rs.getString("MAKE");
            String model = rs.getString("MODEL");
            String tags = rs.getString("TAGS");
            String party = rs.getString("PARTICIPANTS");
            String url = ("http://localhost:8080/mavenproject2/Fetch_Image?path="+path);

            writer.println("<html>\n" +
"    <head>\n" +
"        <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\">\n" +
"        <link href=\"https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,300;0,900;1,300&display=swap\" rel=\"stylesheet\"> \n" +
"        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6\" crossorigin=\"anonymous\">        \n" +
"        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <script>\n" +
"            // Initialize and add the map\n" +
"            function initMap() {\n" +
"              // The location of Uluru\n" +
"              const uluru = { lat: "+latitude+", lng: "+longtitude+" };\n" +
"              // The map, centered at Uluru\n" +
"              const map = new google.maps.Map(document.getElementById(\"map\"), {\n" +
"                zoom: 4,\n" +
"                center: uluru,\n" +
"              });\n" +
"              // The marker, positioned at Uluru\n" +
"              const marker = new google.maps.Marker({\n" +
"                position: uluru,\n" +
"                map: map,\n" +
"              });\n" +
"            }\n" +
"          </script>\n" +
"      \n" +
"        <link rel=\"stylesheet\" type=\"text/css\" href=\"Styles/StyleForImg.css\">\n" +
"    </head>\n" +
"    <body>\n" +
"        <div id=\"container-1\">\n" +
"            <div class=\"img-container\">\n" +
"                <img src=\""+url+"\">\n" +
"            </div>\n" +
"                <a data-scroll href=\"#container-2\">\n" +
"                    <div class=\"arrow\"></div>\n" +
"                </a>\n" +
"        </div>\n" +
"        <div id=\"container-2\">\n" +
"            <div class=\"container\">\n" +
"                <div class=\"row\">\n" +
"                    <h3>METADATA</h3>\n" +
"                    <div class=\"col-md-3\">\n" +
"                        <div class=\"mytable\">\n" +
"                            <h2>Category</h2>\n" +
"                            <h2>Description</h2>\n" +
"                            <h2>Tags</h2>\n" +
"                            <h2>Participants</h2>\n" +
"                            <h2>Date</h2>\n" +
"                            <h2>Size</h2>\n" +
"                            <h2>Make</h2>\n" +
"                            <h2>Model</h2>\n" +
"                            <h2>Longtide</h2>\n" +
"                            <h2>Latitude</h2>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                    <div class=\"col-md-9\">\n" +
"                        <div class=\"mytable2\">\n" +
"                           <form mothod='POST' action='Change_Metadata'>" +
"                           <input type='hidden' name='id' value='"+id+"' />"+
"                               <input type='text' id='"+category+"' value='"+category+"' name='category' /><br/>" +
"                               <input type='text' id='"+description+"' value='"+description+"' name='description' /><br/>" +
"                               <input type='text' id='"+tags+"' value='"+tags+"' name='tags' /><br/>" +
"                               <input type='text' id='"+party+"' value='"+party+"' name='party' /><br/>" +
"                               <input type='text' id='"+date+"' value='"+date+"' name='date' /><br/>" +
"                               <input type='text' id='"+size+"' value='"+size+"' name='size' /><br/>" +
"                               <input type='text' id='"+make+"' value="+make+" name='make' /><br/>" +
"                               <input type='text' id='"+model+"' value="+model+" name='model' /><br/>" +
"                               <input type='text' id='"+longtitude+"' value='"+longtitude+"' name='longtitude' /><br/>" +
"                               <input type='text' id='"+latitude+"' value='"+latitude+"' name='latitude' /><br/>" +
"                               <input type='submit' id='submit_btn' class='submit-btn' value='Save Changes' />" +
"                           </form>"+
"                        </div>\n" +
"                    </div>\n" +
"                </div>\n" +
"            </div>\n" +
"            <div id=\"map\"></div>   \n" +
"        </div>\n" +
"        <script\n" +
"      src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDKL7-f08AbiDgZ22z-KdT_WnRgprUBQ0k&callback=initMap&libraries=&v=weekly\"\n" +
"      async\n" +
"    ></script>\n" +
"\n" +
"    </body>\n" +
"</html>");
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Individual_Image_View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Individual_Image_View.class.getName()).log(Level.SEVERE, null, ex);
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
