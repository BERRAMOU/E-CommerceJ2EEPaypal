import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Panier")
public class Panier extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null) {
            String html = "<html>\n" +
                    "<head>\n" +
                    "    <title>Dashboard</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h1>Votre Identifiant : "+ session.getAttribute("username") +"\n" +
                    "    <a href='/Logout'><input type='button' value='Se Deconnecter'></a>\n" +
                    "    <a href='/Dashboard'><input type='button' value='Dashboard Produit'></a>\n" +
                    "</h1>\n" +
                    "    <br/><hr/>";
            JDBC connector = new JDBC();
            String[] list = connector.getPanier((String)session.getAttribute("username"));
            if(list==null || list.length==0){
                html += "<h2>Votre Panier est vide !</h2>";
            }else{
                html += "<h2>Votre Panier contient :</h2><br>" +
                        "<form action=\"https://www.sandbox.paypal.com/cgi-bin/webscr\" method=\"post\">\n"+
                        "    <input type=\"hidden\" name=\"cmd\" value=\"_cart\">\n" +
                        "    <input type=\"hidden\" name=\"upload\" value=\"1\">\n" +
                        "    <input type=\"hidden\" name=\"business\" value=\"mpsi2014-facilitator@gmail.com\">\n";
                String[] prod;
                for (short i=0;i<list.length;i++) {
                    String str = list[i];
                    html += "<h2>\n" + str +"</h2><hr><br>";
                    prod = str.split(" ");
                    html += "    <input type=\"hidden\" name=\"item_name_"+(i+1)+"\" value=\""+ prod[0] +"\">\n" +
                            "    <input type=\"hidden\" name=\"currency_code\" value=\"USD\">\n" +
                            "    <input type=\"hidden\" name=\"amount_"+(i+1)+"\" value=\""+ prod[1].substring(1,prod[1].length()-1) +"\">\n";
                    System.out.println(prod[0] +"  "+ prod[1].substring(1,prod[1].length()-1));
                }
            }
            html += "<input type=\"hidden\" name=\"return\" value=\"http://localhost:8080/Panier\" >\n" +
                    "<input type=\"hidden\" name=\"notify_url\" value=\"http://localhost:8080/ViderPanier\" >\n" +
                    "<input type=\"submit\" name=\"submit\" value=\"Payer !\"> </form>";
            connector.close();
            html += "</body></html>";
            PrintWriter out = response.getWriter();
            out.print(html);

        } else {
            response.sendRedirect("/Main");
        }
    }

}
