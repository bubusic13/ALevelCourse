package com.alevel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(urlPatterns = "/converter")
public class ConverterServlet extends HttpServlet {

    private String responseBody;

    @Override
    public void init() throws ServletException {

        try {
            URI templateURI = getClass().getResource("/template/converter.html").toURI();
            byte[] bytes = Files.readAllBytes(Paths.get(templateURI));
            responseBody = new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException | URISyntaxException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(responseBody);

    }


}