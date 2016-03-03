import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null) {
            String html = "<html>\n" +
                    "<head>\n"+
                    "    <title>Dashboard</title>\n"+
                    "</head>\n" +
                    "<body>\n" +
                    "<h1>Votre Identifiant : " + session.getAttribute("username") + "\n" +
                    "    <a href='/Logout'><input type='button' value='Se Deconnecter'></a>\n" +
                    "    <a href='/Panier'><input type='button' value='Voir Panier'></a>\n" +
                    "<br/><hr/>\n";
            ArrayList<Produit> produits;
            JDBC connector = new JDBC();
            produits = connector.getProduits();
            if(produits.isEmpty()){
                html += "<h2>Ya pas de produits !???</h2>";
            }
            for(Produit prod : produits){
                html += "<h2>"+ prod.nom +" | prix :"+ prod.prix +"$ </h2>";
                html += "<form action='/AjouterAuPanier' method='post'><input type='hidden' name='produit' value='"+ prod.nom+" :"+prod.prix+"$"+ "'><input type='submit' value='Ajouter Au Panier !'></form><br><hr><br>";
            }

            html += "</body></html>";
            PrintWriter out = response.getWriter();
            out.print(html);
            connector.close();
        } else {
            response.sendRedirect("Main");
        }

    }
}
