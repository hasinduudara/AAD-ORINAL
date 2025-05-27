package lk.learnspringboot;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/path/*")
public class PathVariableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && !pathInfo.isEmpty()) {
            String[] parts = pathInfo.split("/");

            for (String part : parts) {
                if (!part.isEmpty()) {
                    System.out.println(part);
                }
            }
        } else {
            System.out.println("No path variables found.");
        }
    }
}
