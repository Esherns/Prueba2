/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Login;

/**
 *
 * @author hmoraga
 */
public interface LoginDAO {
    boolean validate(Login login);
    String getProfile(String username);
}
