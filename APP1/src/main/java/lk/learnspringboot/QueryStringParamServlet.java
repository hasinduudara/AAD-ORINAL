package lk.learnspringboot;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/querystring")
public class QueryStringParamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eid =  req.getParameter("eid");
        String ename = req.getParameter("ename");
        System.out.println("eid" + eid);
        System.out.println("ename" + ename);
    }
}