import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"), password = request.getParameter("password");
        JDBC connector = new JDBC();
        if (connector.checkLogin(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username",username);
            session.setMaxInactiveInterval(1*60);
            response.sendRedirect("Dashboard");
        }
        else{
            response.sendRedirect("Main");
        }
        connector.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
