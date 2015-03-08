package com.mapapp.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by Ai on 15/3/7.
 */
@WebServlet(name = "DelCheckArea")
public class DelCheckArea extends HttpServlet {


    String driver ="com.mysql.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306/test?user=root&password=root&useUnicode=true&characterEncoding=utf-8";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn=null;
        ResultSet rs=null;
        Statement stt=null;

        String userId=request.getParameter("userid");
        String actId=request.getParameter("actid");
        PrintWriter out = response.getWriter();

        String delSql = "delete from t_act where host_user_id='"+userId+"' and act_id='"+actId+"'";

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url);
            stt = conn.createStatement();
            if (!stt.execute(delSql)){
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
                if (conn != null) {
                    conn.close();
                }
                if (stt != null) {
                    stt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (out != null) {
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
