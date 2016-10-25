/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import dao.AlbumDAOImpl;
import dao.CancionDAOImpl;
import dto.Album;
import dto.Cancion;
import dto.CancionExtendida;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hmoraga
 */
public class AddSongsServlet extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws java.sql.SQLException
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        Album album = (Album) request.getSession().getAttribute("toAdd");
        int albumID = 0;

        //Trying to add album
        try
        {
            if (AlbumDAOImpl.create(album) < 0)
            {
                String errorMsg = "Error cuando se creó el album (solo el album). Ya existe\n";
                errorMsg += request.getSession.getParameter("errorMsg");
                request.getSession.setParameter("errorMsg", errorMsg);
            } else
            {
                //Obtain ID
                albumID = AlbumDAOImpl.findID(album.getNombre(), album.getArtista());
            }
        } catch (Exception e)
        {
            String errorMsg = "Error cuando se creó el album (solo el album). " + e.getMessage() + "\n";
            errorMsg += request.getSession.getParameter("errorMsg");
            request.getSession.setParameter("errorMsg", errorMsg);
        }

        for (int i = 1; i <= request.getSession.getAttribute("canciones"); i++)
        {
            String name = request.getParameter("cancion" + i);
            String gender = request.getParameter("genero" + i);
            String duration = request.getParameter("duracion" + i);

            Cancion song = new Cancion();
            song.setNombre(name);
            song.setDuracion(duration);
            song.setGenero(gender);
            song.setIdAlbum(albumID);

            try
            {
                if (CancionDAOImpl.create(song) < 0)
                {
                    String errorMsg = "Error cuando se creó la cancion " + song.getNombre()
                            + "(solo la cancion). Ya existe\n";
                    errorMsg += request.getSession.getParameter("errorMsg");
                    request.getSession.setParameter("errorMsg", errorMsg);
                    request.getRequestDispatcher().forward("addSongs.jsp", request, response);
                }
            } catch (Exception e)
            {
                String errorMsg = "Error cuando se creó la cancion (solo la cancion). " + e.getMessage() + "\n";
                errorMsg += request.getSession.getParameter("errorMsg");
                request.getSession.setParameter("errorMsg", errorMsg);
                request.getRequestDispatcher().forward("addSongs.jsp", request, response);
            }
        }

        request.getSession().setParameter("addSongResult", "Album agregado exitosamente");
        request.getRequestDispatcher().forward("addSongs.jsp", request, response);

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
        try
        {
            processRequest(request, response);
        } catch (SQLException ex)
        {
            Logger.getLogger(AddSongsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            throws ServletException, IOException
    {
        try
        {
            processRequest(request, response);
        } catch (SQLException ex)
        {
            Logger.getLogger(AddSongsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
