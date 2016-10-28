/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.CancionDAOImpl;
import dto.CancionExtendida;
import dto.Cancion;
import dto.Album;
import java.util.List;
import javax.servlet.http.HttpSession;
import ctrl.Helpers;
import java.io.PrintWriter;

/**
 *
 * @author hmoraga
 */
public class UpdateSongServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException {        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        List<CancionExtendida> listaCanciones = (List<CancionExtendida>)session.getAttribute("listaCanciones");
        CancionDAOImpl ca = new CancionDAOImpl();
        String Validar = Validar(request);
        
        /** 
         * En caso de que la validacion sea exitosa se pasa a buscar
         * la cancion correspondiente a modificar, al obtenerla se modifican
         * los datos de esta y luego se modifican en la base de datos.
        */
        if (Validar.equals("true")) 
        {
            for (CancionExtendida c : listaCanciones) {
                if (c.getCancion().getId() == Integer.parseInt(request.getParameter("idCancion"))) {

                    c.getCancion().setDuracion(request.getParameter("duracion"));
                    c.getCancion().setGenero(request.getParameter("genero"));
                    c.getCancion().setNombre(request.getParameter("nombre"));
                    Cancion cancion = c.getCancion();
                    ca.update(cancion);
                    session.setAttribute("listaCanciones", listaCanciones);
                    request.setAttribute("cancionExtendida", c);
                    request.getRequestDispatcher("/listSongs.jsp").forward(request, response);
                }

            }
        }
        else
        {
            out.println(Validar);
            request.getRequestDispatcher("/EditarCancion.do").include(request, response); 
        }
        
        
    }
    
     /** 
      * Metodo para validar que los datos ingresados por el usuario son correctos.
      * *@return el metodo retorna un string con el mensaje de error correspondiente.
     */
    
    public String Validar(HttpServletRequest request)
    {
        
        if (request.getParameter("nombre").trim().equals("") || request.getParameter("nombre") == null) 
        {
            return "El campo nombre no puede estar vacio";
        }
        else if (request.getParameter("genero").trim().equals("") || request.getParameter("genero") == null) 
        {
            return "El campo genero no puede estar vacio";
        }
        try 
        { 
            Helpers.getHourFormatToSeconds(request.getParameter("duracion"));
        } 
        catch(NumberFormatException e )
        { 
           return "El campo duracion debe estar en el formato hh:mm:ss";
        }
        
        return "true";
  
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateSongServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateSongServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
