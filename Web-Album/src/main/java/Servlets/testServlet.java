package Servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.sanselan.ImageReadException;

@MultipartConfig
public class testServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    
        if (ServletFileUpload.isMultipartContent(request)) {
            System.out.println("Got into multiple");
                try {
                    String[] params = {"a" ,"b", "g", "d"};
                    System.out.println(params[0]);
                    int i = 0;
                    List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                    Connection con = DatabaseConnection.initializeDatabase();
                    for (FileItem item : multiparts) {
                        System.out.println("Got into Loop");
                        if (!item.isFormField()) {
                            System.out.println("Got into formating");
                            double fileSize = item.getSize();
                            String fileName = new File(item.getName()).getName();
                            String path = "/home/muvox/NetBeansProjects/"+"Images"+File.separator+fileName;   
                            File file = new File(path);
                            item.write(new File(path));
                            
                            String[] metadata = {};
                            
                            try {
                                metadata = MetadataExtraction.metadataExample(file);
                            } catch (ImageReadException ex) {
                                Logger.getLogger(Servlet_Image_Handler.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            double longitude = Double.parseDouble(metadata[5]);
                            double latitude = Double.parseDouble(metadata[6]);

                            System.out.println("Before Statement");
                            PreparedStatement st = con.prepareStatement("Insert into Images(PATH, SIZE, FILE_NAME, CATEGORY, DESCRIPTION, DATE, MAKE, MODEL, EXIF_WIDTH, EXIF_LENGHT, LONGITUDE, LATITUDE, TAGS, PARTICIPANTS) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            st.setString(1, path);
                            st.setDouble(2, fileSize);
                            st.setString(3, fileName);
                            st.setString(4, params[0]);
                            st.setString(5, params[1]);
                            st.setString(6, metadata[0]); 
                            st.setString(7, metadata[1]);
                            st.setString(8, metadata[2]);
                            st.setString(9, metadata[3]);
                            st.setString(10, metadata[4]);
                            st.setDouble(11, longitude);
                            st.setDouble(12, latitude);
                            st.setString(13, params[2]);
                            st.setString(14, params[3]);

                            st.executeUpdate();
                            st.close();
                            System.out.println(path);
                            
                        }else {
                            params[i] = item.getString();
                            System.out.println("params " + i + " = " + params[i]);
                            i++;
                        }
                    }
                    con.close();
                    System.out.println("FINISH");
                    RequestDispatcher ds = request.getRequestDispatcher("after_upload.html");
                    ds.include(request, response);
                
                } catch (Exception ex) {
                    Logger.getLogger(testServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
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
