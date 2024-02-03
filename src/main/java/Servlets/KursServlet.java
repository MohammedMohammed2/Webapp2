package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(urlPatterns = "/kurser")
public class KursServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static Connection con;
    public static Statement st;
    public static ResultSet rs;
    public static String tablestyler = "style='border: 1px solid black; background-color: #96D4D4; margin-left: auto; margin-right: auto; width:40%; height:40%;'";
    public static String backgroundstyler = "style=\"background-image: url('https://i.pinimg.com/originals/5e/9f/e2/5e9fe2b0bde19a68a87a095f92bc38aa.jpg');\"";

    public static String Navigationbar = " style=\"\n" +
            "  float: left;\n" +
            "  display: block;\n" +
            "  color: black;\n" +
            "  text-align: center;\n" +
            "  padding: 14px 16px;\n" +
            "  text-decoration: none;\n" +
            "  font-size: 17px;color:black;background-color:cyan;\"";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String top = "<html>" + "<body "+ backgroundstyler + ">"
                + "<h1 style=\"color:black;background-color:cyan;text-align: center;margin: 0;\">Kurser table</h1>"
                + "<a href=\"http://localhost:9090\"" +Navigationbar+ "> Home </a>"
                + "<a href=/students"+ Navigationbar +"> Studenter </a>"
                + "<a href=/narvaro" + Navigationbar + "> Närvaro </a>"
                + "<br>";
        try {
            out.println(top);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy","user", "user");
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM kurser");
                out.println("<table " + tablestyler+ ">");
                out.println("<tr>");
                out.println("<th> id </th>");
                out.println("<th> Name </th>");
                out.println("<th> YHP </th>");
                out.println("<th> Beskrivning </th>");
                out.println("</tr>");
                while (rs.next()){
                    out.println("<tr style = 'text-align: center;'>");
                    out.println("<td " + tablestyler + ">" + rs.getInt(1)+"</td>"
                            + "<td " + tablestyler + ">" + rs.getString(2) + "</td>"
                            + "<td " + tablestyler + ">" +  rs.getString(3) + "</td>"
                            + "<td " + tablestyler+ ">" + rs.getString(4)+"</td>");
                    out.println("</tr>");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}