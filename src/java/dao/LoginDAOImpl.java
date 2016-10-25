/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import ctrl.DBAccess;
import dto.Login;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hmoraga
 */
public class LoginDAOImpl implements LoginDAO{
    private static final String VALIDATE_LOGIN = "SELECT COUNT(*) FROM usuario WHERE username=? AND password=?";
    private static final String GET_PROFILE_BY_USERNAME = "SELECT B.profileName FROM usuario A,profile B WHERE A.profile_id=B.id AND A.username=?";
    
    @Override
    public boolean validate(Login login){
        if (login == null) {
            return false;
        } else {
            try {
                DBAccess acceso = DBAccess.getInstance();

                PreparedStatement op = acceso.getConnection().prepareStatement(VALIDATE_LOGIN);
                try {
                    op.setString(1, login.getUsername());
                    op.setString(2, login.getPassword());

                    ResultSet rs = op.executeQuery();

                    if (rs.next()) {
                        return (rs.getInt(1) == 1);
                    }
                    return false;
                } catch (SQLException ex) {
                    Logger.getLogger(LoginDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
    }
            
    @Override
    public String getProfile(String username) {
        try {
            DBAccess access = DBAccess.getInstance();
            
            PreparedStatement ps = access.ejecutarConParametros(GET_PROFILE_BY_USERNAME);
            ps.setString(1, username);
            String profile;
                
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                profile = rs.getString(1);
            }
            
            ps.close();

            return profile;
        } catch (SQLException ex) {
            Logger.getLogger(LoginDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
