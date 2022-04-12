package Servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Fetch_Image extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, java.io.IOException {
    String path = request.getParameter("path");
    BufferedInputStream inputStream = null;
    try {
        inputStream = new BufferedInputStream(new FileInputStream(new File(path)));
        OutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[1024 * 2];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, bytesRead);
        }
        outputStream.flush();
    } finally {
        if (inputStream != null) {
            inputStream.close();
        }
    }
      }

}