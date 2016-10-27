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
import javax.servlet.http.HttpSession;

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
        int canciones = (int) request.getSession().getAttribute("canciones");
        AlbumDAOImpl p = new AlbumDAOImpl();

        //Trying to add album
        try
        {
            p.create(album);
            albumID = p.findId(album.getNombre(), album.getArtista());
            album.setId(albumID);
        } catch (Exception e)
        {
            String errorMsg = "Error cuando se creó el album (solo el album). " + e.getMessage() + "\n";
            errorMsg += request.getAttribute("errorMsg");
            
            request.setAttribute("errorMsg", errorMsg);
        }
        int errors = 0;
        CancionDAOImpl dao = new CancionDAOImpl();
        for (int i = 1; i <= canciones; i++)
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
                if ( dao.create(song) < 0)
                {
                    errors++;
                }
            } catch (Exception e)
            {
                errors++;
            }
        }
        if (errors > 0)
        {
            String errorMsg = errors + " de " + canciones + " canciones agregadas erróneamente";
            errorMsg += request.getAttribute("errorMsg");
            request.setAttribute("errorMsg", errorMsg);
            request.getRequestDispatcher("addSongs.jsp").forward(request, response);
        } else
        {
            String addSongResult = "Canciones agregadas exitosamente";
            request.getSession().setAttribute("addSongResult", addSongResult);
            request.getRequestDispatcher("menuAdmin.jsp").forward(request, response);
        }

        //request.setAttribute("addSongResult", "Album agregado exitosamente");
        //request.getRequestDispatcher("addSongs.jsp").forward(request, response);
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
            Logger.getLogger(AddSongsServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddSongsServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
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
