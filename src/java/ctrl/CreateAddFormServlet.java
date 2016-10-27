/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import dto.Album;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author diegoMarquez & silvioBelledone
 */
public class CreateAddFormServlet extends HttpServlet
{

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
            throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //Getting parameters
        String album = request.getParameter("album");
        String artista = request.getParameter("artista");
        request.getSession().setAttribute("album",album);
        request.getSession().setAttribute("artista",artista);
        int canciones = 0;
        try
        {
            canciones = Integer.parseInt(request.getParameter("canciones"));
        } catch (Exception e)
        {
            request.setAttribute("errorMsg", "Must enter an int");
            request.getRequestDispatcher("addSongs.jsp").forward(request, response);
        }
        if (canciones <= 0)
        {
            request.setAttribute("errorMsg", "Songs quantity must be bigger than 0");
            request.getRequestDispatcher("addSongs.jsp").forward(request, response);
        } else
        {
            //Create object, put it into the session and send the form with the updated number
            //of rows.
            Album toAdd = new Album();
            toAdd.setArtista(artista);
            toAdd.setNombre(album);
            request.getSession().setAttribute("toAdd", toAdd);
            request.getSession().setAttribute("canciones", canciones);
            request.getRequestDispatcher("addSongs.jsp").forward(request, response);
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
            throws ServletException, IOException
    {
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
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
