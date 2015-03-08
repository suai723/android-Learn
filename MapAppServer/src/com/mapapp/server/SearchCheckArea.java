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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ai on 15/3/7.
 */
@WebServlet(name = "SearchCheckArea")
public class SearchCheckArea extends HttpServlet {
    public static final String KEY_ACT_ID="act_id";//活动id
    public static final String KEY_ACT_ADDR="addr";//活动地址
    public static final String KEY_ACT_LOC="loc";//活动地理位置
    public static final String KEY_ACT_DAY="start_day";//开始日期
    public static final String KEY_ACT_START_TIME="start_time";//开始时间
    public static final String KEY_ACT_END_TIME="end_time";//结束时间
    public static final String KEY_ACT_PERSON_NUM="person_num";//参与人数
    public static final String KEY_ACT_DESC="act_desc";//活动描述
    public static final String KEY_ACT_HOST_USERNAME="host_user_name";//
    public static final String KEY_ACT_HOST_USERID="host_user_id";//
    public static final String KEY_ACT_NAME="act_name";//
    public static final String KEY_ACT_STATE="act_state";//

    String driver ="com.mysql.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306/test?user=root&password=root&useUnicode=true&characterEncoding=utf-8";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId=request.getParameter("userid");

        System.out.println(userId);
        PrintWriter out = response.getWriter();


        Connection conn=null;
        ResultSet rs=null;
        Statement stt=null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url);
            stt = conn.createStatement();
            //select a.* from t_act a,t_person b where a.act_id=b.act_id and b.uer_id=
            rs=stt.executeQuery("select a.* from t_act a ,t_person b where a.act_id=b.act_id and b.user_id='"+userId+"'");
            List<Activity> acts = new ArrayList<Activity>();
            while (rs.next()){
                String a = rs.getString(KEY_ACT_ID);

                String actName = rs.getString(KEY_ACT_NAME);
                String addr = rs.getString(KEY_ACT_ADDR);
                String loc = rs.getString(KEY_ACT_LOC);
                String actId = rs.getString(KEY_ACT_ID);
                String startTime = rs.getString(KEY_ACT_START_TIME);
                String startDay = rs.getString(KEY_ACT_DAY);
                String endTime = rs.getString(KEY_ACT_END_TIME);
                String hostUserName = rs.getString(KEY_ACT_HOST_USERNAME);
                String hostUserId = rs.getString(KEY_ACT_HOST_USERID);
                String desc = rs.getString(KEY_ACT_DESC);
                String state = rs.getString(KEY_ACT_STATE);

                Activity act = new Activity();
                act.setActId(actId);
                act.setActName(actName);
                act.setAddr(addr);
                act.setLoc(loc);
                act.setStartTime(startTime);
                act.setStartDay(startDay);
                act.setEndTime(endTime);
                act.setHostUserName(hostUserName);
                act.setHostUserId(hostUserId);
                act.setDesc(desc);
                act.setState(state);
                acts.add(act);
            }
            Gson gson = new Gson();
            String listJson=gson.toJson(acts);
            out.append(listJson);

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
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }
}
