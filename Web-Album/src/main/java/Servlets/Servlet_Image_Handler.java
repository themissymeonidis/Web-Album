package Servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.sanselan.ImageReadException;

@MultipartConfig
public class Servlet_Image_Handler extends HttpServlet {

    Statement stmt = null;
    InputStream inputStream = null;
    String fname, lname, age;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Part filePart = request.getPart("photo");
            String category = request.getParameter("category");
            String description = request.getParameter("description");

           
            if (filePart != null) {
                System.out.println(filePart.getName());
                System.out.println(filePart.getSize());
                System.out.println(filePart.getContentType());
            }
            
            
            Connection con = DatabaseConnection.initializeDatabase();
            
            if (con != null && filePart != null && filePart.getSize() != 0) {
                
                double fileSize = filePart.getSize();
                String fileName = filePart.getSubmittedFileName();
                String path = "/home/muvox/NetBeansProjects/"+"Images"+File.separator+fileName;
                File file = new File(path);
                String[] metadata = {};
                
                
                for (Part part : request.getParts()) {
                    part.write(path);
                }
                
               
                try {
                    metadata = MetadataExtraction.metadataExample(file);
                } catch (ImageReadException ex) {
                    Logger.getLogger(Servlet_Image_Handler.class.getName()).log(Level.SEVERE, null, ex);
                }
                double longitude = Double.parseDouble(metadata[5]);
                double latitude = Double.parseDouble(metadata[6]);
                
                PreparedStatement st = con.prepareStatement("Insert into Images(PATH, SIZE, FILE_NAME, CATEGORY, DESCRIPTION, DATE, MAKE, MODEL, EXIF_WIDTH, EXIF_LENGHT, LONGITUDE, LATITUDE) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                st.setString(1, path);
                st.setDouble(2, fileSize);
                st.setString(3, fileName);
                st.setString(4, category);
                st.setString(5, description);
                st.setString(6, metadata[0]); 
                st.setString(7, metadata[1]);
                st.setString(8, metadata[2]);
                st.setString(9, metadata[3]);
                st.setString(10, metadata[4]);
                st.setDouble(11, longitude);
                st.setDouble(12, latitude);

                st.executeUpdate();
                st.close();
                con.close();
                RequestDispatcher ds = request.getRequestDispatcher("after_upload.html");
                ds.include(request, response);
                
            }else {
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servlet_Image_Handler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Servlet_Image_Handler.class.getName()).log(Level.SEVERE, null, ex);
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
