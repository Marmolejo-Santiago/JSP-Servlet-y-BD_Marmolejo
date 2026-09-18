package org.gerdoc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/MiServlet")
public class MiServlet extends HttpServlet
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String url = "jdbc:mysql://127.0.0.1:3306/Alumno?user=root&password=toor&useSSL=false&serverTimezone=UTC";
        String sql = "INSERT INTO Alumnos(Nombre, Edad) VALUES(?, ?)";
        int row = 0;

        String nombre = req.getParameter("nombre");
        String edadStr = req.getParameter("edad");

        if (nombre != null && edadStr != null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection connection = DriverManager.getConnection(url);
                     PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                    preparedStatement.setString(1, nombre);
                    preparedStatement.setInt(2, Integer.parseInt(edadStr));

                    row = preparedStatement.executeUpdate();
                }
            } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='es'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("  <title>Respuesta Servlet</title>");
        out.println("  <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body class='bg-light d-flex justify-content-center align-items-center vh-100'>");

        out.println("  <div class='card shadow-sm' style='width: 22rem;'>");
        out.println("    <div class='card-body'>");
        out.println("      <h5 class='card-title text-primary mb-3'>Registro de Alumno</h5>");
        out.println("      <p class='card-text'><strong>Nombre:</strong> " + (nombre != null ? nombre : "N/A") + "</p>");
        out.println("      <p class='card-text'><strong>Edad:</strong> " + (edadStr != null ? edadStr : "N/A") + "</p>");
        out.println("      <hr>");

        if (row > 0) {
            out.println("      <div class='alert alert-success py-2 mb-3' role='alert'>¡Alumno guardado con éxito!</div>");
        } else {
            out.println("      <div class='alert alert-warning py-2 mb-3' role='alert'>No se insertaron registros.</div>");
        }

        out.println("      <a href='index.jsp' class='btn btn-primary w-100'>Regresar</a>");

        out.println("    </div>");
        out.println("  </div>");

        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doGet(req, resp);
    }
}