/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import dto.Login;
import java.io.IOException;
import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.LoginDAO;
import dao.LoginDAOImpl;
import java.io.PrintWriter;

/**
 *
 * @author hmoraga
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        
        Login login = new Login();
        login.setUsername(user);
        login.setPassword(pass);
        
        LoginDAO logindao = new LoginDAOImpl();
        
        //validate login
        if (logindao.validate(login))
        {
            
            session.setAttribute("Login", login);
            
            // agregar a la sesion los atributos perfil y nombre de usuario
            
            if (logindao.getProfile(login.getUsername()).equals("admin") ) 
            {
                session.setAttribute("user", login);
                session.setAttribute("username", login.getUsername());
                request.getRequestDispatcher("/menuAdmin.jsp").include(request, response);
            }
            else if(logindao.getProfile(login.getUsername()).equals("commonUser"))
            {
                session.setAttribute("user", login);
                session.setAttribute("username", login.getUsername());
                request.getRequestDispatcher("/listOnly.jsp").include(request, response);
            } 
            
            else  throw new AuthenticationException("Usuario no posee profile!");        
            
        }
                
        
         
        else {
            request.setAttribute("errorMsg", "Usuario Invalido");
            request.getRequestDispatcher("./").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
