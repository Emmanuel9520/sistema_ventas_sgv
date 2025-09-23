/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import Dao.UsuarioDAO;
import modelo.Usuario;  // ✅ corregido a "modelo", no "Modelo"
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();  // ✅ se instancia solo una vez
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Debe ingresar correo y contraseña.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // ✅ Usar la instancia, no el nombre de la clase
        Usuario usuario = usuarioDAO.validarUsuario(email, password);

        if (usuario != null) {
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) oldSession.invalidate();

            HttpSession session = request.getSession(true);
            session.setAttribute("usuarioLogueado", usuario);
            session.setMaxInactiveInterval(30 * 60);

            response.sendRedirect("principal.jsp");
        } else {
            request.setAttribute("error", "Credenciales incorrectas. Intente de nuevo.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}


