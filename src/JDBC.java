
import java.sql.*;
import java.util.ArrayList;
import java.util.StringJoiner;

public class JDBC {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
    Connection conn = null;
    Statement stmt = null;
    public JDBC(){
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
    public void add(String nom,String prenom,String preferences){
        try{
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "insert into inscription (nom,prenom,preferences) values (\""+nom+"\",\""+prenom+"\",\""+preferences+"\")";
            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table...");
            //STEP 6: Clean-up environment
            stmt.close();
        }catch(Exception e){
        }
    }
    public boolean checkLogin(String user,String pass) {
        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "select * from users where username=\""+user+"\"";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("password").equals(pass))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void ajouterProduit(String user,String prod){
        try{
            String[] eltspanier = getPanier(user);
            String panier = prod;
            for (String str : eltspanier) {
                panier += ";"+str;
            }
            System.out.println("panier contient :"+panier);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();

            String sql;
            sql = "update users set panier=\""+ panier +"\" WHERE username=\""+ user +"\"";
            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table...");
            //STEP 6: Clean-up environment
            stmt.close();
        }catch(Exception e){
        }
    }
    public String[] getPanier(String user){
        ArrayList<String> list = new ArrayList<>();
        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "select * from users where username=\""+user+"\"";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("username").equals(user)) {
                    return rs.getString("panier").split(";");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public ArrayList<Produit> getProduits(){
        ArrayList<Produit> list = new ArrayList<>();

        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "select * from produits";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Produit(rs.getString("nom"),rs.getInt("prix")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public void close(){
        try {
            stmt.close();
            conn.close();
        }catch(Exception e){

        }
    }
}//end FirstExample