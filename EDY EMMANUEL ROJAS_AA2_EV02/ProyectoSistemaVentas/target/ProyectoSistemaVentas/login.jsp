<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>login</title>
</head>
<body>
    <h2>Iniciar sesión</h2>

    <!-- Mostrar mensaje de error si existe -->
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <p style="color:red;"><%= error %></p>
    <%
        }
    %>

    <form action="login" method="post">
        <label>Email:</label><br>
        <input type="text" name="email" required><br><br>

        <label>Contraseña:</label><br>
        <input type="password" name="password" required><br><br>

        <button type="submit">Ingresar</button>
    </form>

   <p>¿No tienes cuenta?
    <a href="${pageContext.request.contextPath}/registro.jsp">Regístrate aquí</a>
</p>
</body>
</html>
