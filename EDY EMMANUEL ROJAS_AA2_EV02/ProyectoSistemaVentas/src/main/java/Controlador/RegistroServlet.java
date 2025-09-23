/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import Dao.UsuarioDAO;
import modelo.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre_apellidos");
        String email = request.getParameter("email");
        String usuarioNombre = request.getParameter("nombre_usuario");
        String contrasena = request.getParameter("password");
        String rol = "usuario"; // valor por defecto

        // Validar campos vacíos
        if (nombre == null || email == null || usuarioNombre == null || contrasena == null ||
                nombre.isEmpty() || email.isEmpty() || usuarioNombre.isEmpty() || contrasena.isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Validar duplicado por email
        if (usuarioDAO.existeUsuarioPorEmail(email)) {
            request.setAttribute("error", "El correo ya está registrado.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario(nombre, email, usuarioNombre, contrasena, rol);
        boolean creado = usuarioDAO.crearUsuario(nuevoUsuario);

        if (creado) {
            request.setAttribute("mensaje", "Registro exitoso. Ahora puede iniciar sesión.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Ocurrió un error al registrarse. Intente nuevamente.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }
}