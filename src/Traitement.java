import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by user on 2/26/2016.
 */
@WebServlet(urlPatterns = "/Traitement")
public class Traitement extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " +
                        "transitional//en\">\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>Traitement des donnees</title></head>\n"+
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">Traitement des donnees</h1>\n" +
                "<table width=\"100%\" border=\"1\" align=\"center\">\n" +
                "<tr bgcolor=\"#949494\">\n" +
                "<th>Header Name</th><th>Header Value(s)</th>\n"+
                "</tr>\n");

        String  nom=request.getParameter("nom"),
                prenom=request.getParameter("prenom"),
                preferences=request.getParameter("prefs");

        Enumeration parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()) {
            String paramName = (String)parameterNames.nextElement();
            out.print("<tr><td>" + paramName + "</td>\n");
            String paramValue = request.getParameter(paramName);
            out.println("<td> " + paramValue + "</td></tr>\n");
        }
        out.println("</table>\n</body></html>");

        //Ajout a la bdd
        JDBC bdd = new JDBC();
        bdd.add(nom,prenom,preferences);
        bdd.close();

    }
}
