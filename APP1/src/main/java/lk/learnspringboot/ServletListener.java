package lk.learnspringboot;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;

@WebListener
public class ServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(jakarta.servlet.ServletContextEvent sce) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/eventdb");
        basicDataSource.setPassword("root");
        basicDataSource.setPassword("hasindu12345");
        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxTotal(50);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dataSource", basicDataSource);
    }

    @Override
    public void contextDestroyed(jakarta.servlet.ServletContextEvent sce) {
        try {
        ServletContext servletContext = sce.getServletContext();
        BasicDataSource dataSource = (BasicDataSource)servletContext.getAttribute("dataSource");
        dataSource.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
