package servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "sample-servlet", urlPatterns = "/sample")
public class SampleServlet extends HttpServlet {

    ConcurrentHashMap<String, String> values = new ConcurrentHashMap<>();

    private static final Logger log = LoggerFactory.getLogger(SampleServlet.class);

    @Override
    public void init() {
        log.info("Sample Servlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter responseBody = resp.getWriter();
        String ip = req.getRemoteAddr();
        values.put(ip, req.getHeader("User-agent"));
        resp.setContentType("text/html");
        responseBody.println("<h3 align=\"center\">Request from: " + req.getRemoteHost() + "</h3>");
        responseBody.println(build(ip));

    }

    @Override
    public void destroy() {
        log.info("Sample Servlet destroyed");
    }

    String build(String ip){
        StringBuilder builder = new StringBuilder();
        builder.append("<ul>");

        for (Map.Entry<String, String> stringStringEntry : values.entrySet()){
            if (stringStringEntry.getKey().equals(ip)){
                builder.append("<li>").append(stringStringEntry.getValue()).append(" ").append(stringStringEntry.getKey()).append("</li>");
            }
        }
        builder.append("<ul>");
        return builder.toString();
    }
}