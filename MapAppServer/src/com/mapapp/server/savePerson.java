package com.mapapp.server;

import com.google.gson.Gson;
import com.mapapp.server.Entity.Activity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.UUID;

/**
 * Created by Ai on 15/3/8.
 */
@WebServlet(name = "savePerson")
public class savePerson extends HttpServlet {
    String driver ="com.mysql.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306/test?user=root&password=root&useUnicode=true&characterEncoding=utf-8";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost");
        String userid=request.getParameter("userid");
        String username = request.getParameter("username");
        String actid=request.getParameter("actid");
        String curloc=request.getParameter("curloc");
        String personid= UUID.randomUUID().toString();

        PrintWriter out =response.getWriter();

        String insertSql="insert INTO t_person " +
                "(person_id,act_id,user_name,user_id) " +
                "values ('" +
                personid+"','" +
                actid+"','"+
                username+"','" +
                userid+"')";

        Connection conn=null;
        ResultSet rs=null;
        Statement stt=null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url);
            stt = conn.createStatement();
            if (!stt.execute(insertSql)){
                out.append("ok");
            }else{
                out.append("error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn!=null){
                    conn.close();
                }
                if (stt!=null){
                    stt.close();
                }
                if (rs!=null){
                    rs.close();
                }
                if (out!=null){
                    out.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
