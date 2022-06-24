/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import java.sql.*;

/**
 *
 * @author cardi
 */
public class UsersDAO extends Data.UsersDO {

    private static DBConnector dbc = new DBConnector();

    public UsersDAO(String name, String password, boolean admin, boolean editor, boolean locked, boolean registered, Date birthdate) {
        super(name, password, admin, editor, locked, registered, birthdate);
    }

    // CRUD
    public static UsersDAO create(String name, String password, boolean admin, boolean editor, boolean locked, boolean registered, Date birthdate) throws Exception {
        UsersDAO newUser = null;

        PreparedStatement ps = dbc.getPreparedStatement(
                "INSERT INTO Users (name, `password`, admin, editor, locked, registered, birthdate)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, name);
        ps.setString(2, password);
        ps.setBoolean(3, admin);
        ps.setBoolean(4, editor);
        ps.setBoolean(5, locked);
        ps.setBoolean(6, registered);
        ps.setDate(7, birthdate);
        dbc.write(ps);
        
        return UsersDAO.read(name);
   }
    
    public static UsersDAO read(int id) throws Exception {
        UsersDAO newUser = null;

        ResultSet rs = dbc.read("select DISTINCT * FROM Users WHERE `pk_ID`=" + id);
        while (rs.next()) {
            newUser = new UsersDAO(
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getBoolean("admin"),
                    rs.getBoolean("editor"),
                    rs.getBoolean("locked"),
                    rs.getBoolean("registered"),
                    rs.getDate("birthdate")
            );
            newUser.setPk_ID(rs.getShort("pk_ID"));
        }
        
        return newUser;
    }
    
    public static UsersDAO read(String name) throws Exception {
        UsersDAO newUser = null;
        
        ResultSet rs = dbc.read("SELECT DISTINCT * FROM Users WHERE `name`='" + name + "'");
        while (rs.next()) {
            newUser = new UsersDAO(
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getBoolean("admin"),
                    rs.getBoolean("editor"),
                    rs.getBoolean("locked"),
                    rs.getBoolean("registered"),
                    rs.getDate("birthdate")
            );
            newUser.setPk_ID(rs.getShort("pk_ID"));
        }
        
        return newUser;
    }
    
    public UsersDAO update() throws Exception {
        UsersDAO updatedUser = null;
        
        PreparedStatement ps = dbc.getPreparedStatement(
                "UPDATE Users SET "
                        + "`name` = ? , "
                        + "`password` = ? , "
                        + "`admin` = ? , "
                        + "`editor` = ? , "
                        + "`locked` = ? , "
                        + "`registered` = ? , "
                        + "`birthdate` = ? "
                        + "WHERE `pk_ID` = ? ;");
        ps.setString(1, this.getName());
        ps.setString(2, this.getPassword());
        ps.setBoolean(3, this.isAdmin());
        ps.setBoolean(4, this.isEditor());
        ps.setBoolean(5, this.isLocked());
        ps.setBoolean(6, this.isRegistered());
        ps.setDate(7, this.getBirthdate());
        ps.setShort(8, this.getPk_ID());
        
        dbc.write(ps);
        
        updatedUser = UsersDAO.read(this.getPk_ID());
        
        return updatedUser;
    }
    
    public static void delete(short id) throws Exception {
         PreparedStatement ps = dbc.getPreparedStatement(
                "DELETE FROM Users WHERE pk_ID = " + id);
        
        dbc.write(ps);      
    }
    
    public static void delete(String name) throws Exception {
         PreparedStatement ps = dbc.getPreparedStatement(
                "DELETE FROM Users WHERE `name` = '" + name + "'");
        
        dbc.write(ps);      
    }

    
    // konkrete Instanz löschen aus der Datenbank
    public void delete() throws Exception {
        UsersDAO.delete(this.getPk_ID());
        this.setPk_ID((short)0);
    }
    
    
    
}
