<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Página Principal</title>
</head>
<body>
    <h2>Bienvenido, <%= usuario.getNombreApellidos() %> 👋</h2>
    <p>Tu rol es: <%= usuario.getRol() %></p>

    <form action="logout" method="post">
        <button type="submit">Cerrar sesión</button>
    </form>
    <form action="logout" method="post">
    <button type="submit">Cerrar sesión</button>
</form>
</body>
</html>