package lk.learnspringboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/connectionPoolExample")
public class ConnectionPoolExampleServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        ServletContext basicDataSource = getServletContext();
//        BasicDataSource ds = (BasicDataSource) basicDataSource.getAttribute("dataSource");

        try {
            ServletContext context = getServletContext();
            BasicDataSource basicDataSource = (BasicDataSource) context.getAttribute("dataSource");
            Connection connection = basicDataSource.getConnection();

            String query = "SELECT * FROM event";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<Map<String, String>> events = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> event = new HashMap<>();
                event.put("eid", resultSet.getString("eid"));
                event.put("ename", resultSet.getString("ename"));
                event.put("ediscription", resultSet.getString("ediscription"));
                event.put("edate", resultSet.getString("edate"));
                event.put("eplace", resultSet.getString("eplace"));
                events.add(event);
            }

            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(events);
            resp.getWriter().write(jsonResponse);
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Error fetching events: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> event = mapper.readValue(req.getInputStream(), Map.class);
        try {
            ServletContext basicDataSource = getServletContext();
            BasicDataSource ds = (BasicDataSource) basicDataSource.getAttribute("dataSource");
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into events values(?,?,?,?,?)");
            statement.setString(1, req.getParameter("eid"));
            statement.setString(2, req.getParameter("ename"));
            statement.setString(3, req.getParameter("edescription"));
            statement.setString(4, req.getParameter("edate"));
            statement.setString(5, req.getParameter("eplace"));
            int result = statement.executeUpdate();
            resp.getWriter().write(result + " record(s) inserted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}