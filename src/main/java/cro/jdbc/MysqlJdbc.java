// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 6/8/2006 2:08:57 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MysqlJdbc.java

package cro.jdbc;

import java.sql.*;

public class MysqlJdbc
{
    private String dbHost;
    private String dbPort;
    private String dbName;
    private String dbUser;
    private String dbPass;
    private databaseSettings ds;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    /*
     *Constructor that takes the data base connection setting 
     *@param host
     *@param port
     *@param db
     *@param user
     *@param pass
     */
    public MysqlJdbc(String host, String port, String db, String user, String pass)
    {
        ds = new databaseSettings();
        dbHost = host;
        dbPort = port;
        dbName = db;
        dbUser = user;
        dbPass = pass;
        ds.setHost(host);
        ds.setPort(port);
        ds.setDatabase(db);
        ds.setUser(user);
        ds.setPassword(pass);
        start();
    }
    
    public void start()
    {
        ds.setVisible(true);
        while (ds.isShowing()){
            Thread.yield();
        }
        openConnection();
        closeConnection();
        con = null;
        st = null;
        rs = null;
        //ds.dispose();
    }    
    public void openConnection(){
        dbHost = ds.getHost();
        dbPort = ds.getPort();
        dbName = ds.getDatabase();
        dbUser = ds.getUser();
        dbPass = ds.getUserPassword();        
        ds.connectionMessage("**********Testing Database Connection**********");
        if (!openConnection(dbHost, dbPort, dbName, dbUser, dbPass)) return;
    }  
    public boolean openConnection(String host, String port, String db, String user, String pass)
    {
        if(con != null) closeConnection();
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+db, user, pass);
            if(!con.isClosed()) {
                ds.connectionMessage("Succesfully connected to Mysql server using TCP/IP...[*_*]");
                st = con.createStatement();
            }
            
        }
        catch(Exception e)
        {
            ds.connectionMessage("Jackie Says: Bad Day.....Unnnnncle");
            ds.connectionMessage("Exception : "+e.getMessage());
            return false;
        }
        return true;
    }

    public boolean isConnected()
    {
        try
        {
            if(con.isClosed()) return false;
        }
        catch(Exception exception) { return false;}
        return true;
    }

    public void closeConnection()
    {
        try
        {
            if(st != null)
                st.close();
            if(rs != null)
                rs.close();
            if(con != null)
                con.close();
        }
        catch(SQLException e)
        {
            System.err.print((new StringBuilder("Could not close database connection:\nEXCEPTION:")).append(e.getMessage()).toString());
        }
    }

    public void select(String str)
    {
        if (!isConnected()) return;
        try
        {
            rs = st.executeQuery(str);
            rs.first();
        }
        catch(Exception e)
        {
            System.err.print((new StringBuilder("Could not execute sql statement\nException:")).append(e.getMessage()).toString());
        }
    }

    public String getValue(String field)
    {
        String value = "";
        try
        {
            value = rs.getString(field);
        }
        catch(Exception e)
        {
            System.err.print((new StringBuilder("could not access field:\nEception:")).append(e.getMessage()).toString());
        }
        return value;
    }

    public int update(String str)
    {
        int recordUpdate = 0;
        try
        {
            recordUpdate = st.executeUpdate(str);
        }
        catch(Exception e)
        {
            System.err.print((new StringBuilder("Could not execute sql statement\nException:")).append(e.getMessage()).toString());
        }
        return recordUpdate;
    }
    
}