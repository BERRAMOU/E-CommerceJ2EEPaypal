import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Main")
public class Main extends HttpServlet {
    private String htmlCode = "<html>\n" +
            "<head><title>Exemple</title>\n" +
            "    <link rel='stylesheet' href='/style.css' type='text/css'>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div class='container'>\n" +
            "    <form action=\"/Login\" method='post' class='login-form'>\n" +
            "        <input type=\"text\" name=\"username\" placeholder=\"Username\">\n" +
            "        <input type=\"password\" name=\"password\" placeholder=\"Password\">\n" +
            "        <input type=\"submit\" value=\"Se Connecter\">\n" +
            "    </form>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println(htmlCode);
    }
}
