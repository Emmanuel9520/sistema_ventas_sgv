<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registro de Usuario</title>
</head>
<body>
    <h2>Crear cuenta</h2>

    <!-- Mostrar mensajes si existen -->
    <%
        String error = (String) request.getAttribute("error");
        String mensaje = (String) request.getAttribute("mensaje");
        if (error != null) {
    %>
        <p style="color:red;"><%= error %></p>
    <%
        }
        if (mensaje != null) {
    %>
        <p style="color:green;"><%= mensaje %></p>
    <%
        }
    %>

    <!-- Formulario de registro -->
    <form action="registro" method="post">
        <label for="nombreApellidos">Nombre y Apellidos:</label><br>
        <input type="text" id="nombreApellidos" name="nombreApellidos" required><br><br>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="nombreUsuario">Nombre de Usuario:</label><br>
        <input type="text" id="nombreUsuario" name="nombreUsuario" required><br><br>

        <label for="contrasena">Contraseña:</label><br>
        <input type="password" id="contrasena" name="contrasena" required><br><br>

        <label for="rol">Rol:</label><br>
        <select id="rol" name="rol" required>
            <option value="usuario">Usuario</option>
            <option value="admin">Administrador</option>
        </select><br><br>

        <button type="submit">Registrarse</button>
    </form>

    <!-- Enlace para volver al login -->
    <p>¿Ya tienes una cuenta? 
       <a href="login.jsp">Inicia sesión aquí</a>
    </p>
</body>
</html>