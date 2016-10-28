/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.AlbumDAOImpl;
import dao.CancionDAOImpl;
import dao.LoginDAO;
import dao.LoginDAOImpl;
import dto.CancionExtendida;
import dto.Album;
import dto.Cancion;
import dto.Login;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hmoraga
 */
public class CargarCancionesServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException{
       
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        LoginDAO logindao = new LoginDAOImpl();
        PrintWriter out = response.getWriter();
        AlbumDAOImpl albumDB = new AlbumDAOImpl();
        CancionDAOImpl cancionDB = new CancionDAOImpl();
        List<CancionExtendida> listaCanciones = new ArrayList<>();
        List<Album> listaAlbums = new ArrayList<>();
        List<Cancion> listaC = new ArrayList<>();
        Album album = new Album();
        Login login = (Login)session.getAttribute("user");
        
      /** 
     * En esta zona se genera la lista de canciones extendidas de acuerdo
     * al artista ingresado por el usuario, se consulta a la base de datos para 
     * saber si este existe o no.
     */
        if (request.getParameter("searchBy").equals("Artista")) 
        {
            listaAlbums = albumDB.findByArtist(request.getParameter("data"));
            if (listaAlbums.isEmpty()) 
            {
                out.println("No existe un artista con ese nombre");
                request.getRequestDispatcher("/listOnly.jsp").include(request, response);    
            }
            else
            {
                for (Album a : listaAlbums) 
                {
                    listaC = cancionDB.findByAlbumId(a.getId());
                    for (Cancion c : listaC) 
                    {
                        CancionExtendida ca = new CancionExtendida(c,a);
                        listaCanciones.add(ca);
                    }
                }
                session.setAttribute("listaCanciones", listaCanciones);
                /** 
                * Se verifica el tipo de perfil de usuario para redirigir a 
                * la pagina correspondiente.
                */
                if (logindao.getProfile(login.getUsername()).equals("admin")) 
                {
                    request.getRequestDispatcher("/listSongs.jsp").forward(request, response);
                }
                else
                {
                    request.getRequestDispatcher("/listOnly.jsp").forward(request, response);
                }
                
            }
        }
        
      /** 
     * En esta zona se genera la lista de canciones extendidas de acuerdo
     * al nombre de albim ingresado por el usuario, se consulta a la base de datos para 
     * saber si este existe o no.
     */
        else
        {
            album = albumDB.findByName(request.getParameter("data"));
            if (album == null) 
            {
                out.println("No existe un album con ese nombre");
                request.getRequestDispatcher("/listOnly.jsp").include(request, response);     
            }
            else
            {

                listaC = cancionDB.findByAlbumId(album.getId());
                for (Cancion c : listaC) 
                {
                    CancionExtendida ca = new CancionExtendida(c,album);
                    listaCanciones.add(ca);
                }
                session.setAttribute("listaCanciones", listaCanciones);
                if (logindao.getProfile(login.getUsername()).equals("admin")) 
                {
                    request.getRequestDispatcher("/listSongs.jsp").forward(request, response);
                }
                else
                {
                    request.getRequestDispatcher("/listOnly.jsp").forward(request, response);
                }
            }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CargarCancionesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CargarCancionesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet que me crea la lista de canciones seleccionadas desde el formulario de listar canciones";
    }// </editor-fold>

}
