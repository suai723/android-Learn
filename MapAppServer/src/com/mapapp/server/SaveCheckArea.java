package com.mapapp.server;

import com.google.gson.Gson;
import com.mapapp.server.Entity.Activity;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by Ai on 15/3/7.
 */
public class SaveCheckArea extends HttpServlet {
    String driver ="com.mysql.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306/test?user=root&password=root&useUnicode=true&characterEncoding=utf-8";

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("doPost");
        String actJson=request.getParameter("act");
        Gson gson = new Gson();
        Activity act=gson.fromJson(actJson, Activity.class);
        PrintWriter out =response.getWriter();

        String insertSql="insert into t_act values ('" +
                act.getActId()+"','" +
                act.getAddr()+"','" +
                act.getLoc()+"','" +
                act.getStartDay()+"','" +
                act.getStartTime()+"','" +
                act.getEndTime()+"','" +
                act.getPersonNum()+"','"+
                act.getDesc()+"','" +
                act.getHostUserName()+"','" +
                act.getHostUserId()+"','" +
                act.getActName()+"','" +
                act.getState()+"')";
        System.out.println(insertSql);

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

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("doGet");
    }
}
