import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/AjouterAuPanier")
public class AjouterAuPanier extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null) {
            JDBC connector = new JDBC();
            connector.ajouterProduit((String)session.getAttribute("username"),request.getParameter("produit"));
            connector.close();
            response.sendRedirect("/Panier");
            
        } else {
            response.sendRedirect("/Main");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
