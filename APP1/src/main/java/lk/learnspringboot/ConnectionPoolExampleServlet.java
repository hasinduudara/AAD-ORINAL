package lk.learnspringboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/connectionPool")
public class ConnectionPoolExampleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/eventdb");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("hasindu12345");
        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxTotal(50);

        try {
            Connection connection = basicDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM event");
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Map<String,String>> elist = new ArrayList<Map<String,String>>();

            while (resultSet.next()){
                Map<String,String> event = new HashMap<String,String>();

                event.put("eid",resultSet.getString("eid"));
                event.put("ename",resultSet.getString("ename"));
                event.put("edescription",resultSet.getString("edescription"));
                event.put("edate",resultSet.getString("edate"));
                event.put("eplace",resultSet.getString("eplace"));

                elist.add(event);
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(),elist);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

