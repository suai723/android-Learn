package com.mapapp.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Ai on 15/3/7.
 */
@WebServlet(name = "InitApp")
public class InitApp extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out =null;
        try {
            out= response.getWriter();
            out.append("ok!!!!");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (out!=null)
                out.close();
        }

    }
}
