package lk.learnspringboot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/mime")
//@MultipartConfig
public class Main extends HttpServlet {

    // text
    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = new BufferedReader(new InputStreamReader
                (req.getInputStream())).lines().collect(Collectors.joining("\n"));
        resp.setContentType("text/plain");
        resp.getWriter().write(body);
    }*/


    // x-www-form-urlencoded
    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        resp.setContentType("text/plain");
        resp.getWriter().println("Received ass x-www-form-urlencoded:\nName: "
                +name+"\nAddress: "+address);

    }*/

    // form-data
    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Part part = req.getPart("file");
        String fileName = part.getSubmittedFileName();

        resp.getWriter().print("Received ass x-www-form-urlencoded: " + name + " " + fileName);
    }*/

    // JSON - Object
    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(req.getReader());

        String name = jsonNode.get("name").asText();
        String address = jsonNode.get("address").asText();

        resp.setContentType("text/plain");
        resp.getWriter().println(name + " " + address);
    }*/

    //JSON - Array
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<JsonNode> Users = mapper.readValue(req.getReader(),
                new TypeReference<List<JsonNode>>(){});

        for (JsonNode user : Users) {
            String name = user.get("name").asText();
            String address = user.get("address").asText();
            System.out.println(name + " " + address);
        }
        resp.getWriter().write("OK");
    }
}
