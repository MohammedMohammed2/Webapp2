package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = "/AddStudent")
public class UpdateStudentsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static Connection con;
    public static Statement st;
    public static ResultSet rs;
    public static String tablestyler = "style='border: 1px solid black; background-color: #96D4D4; margin-left: auto; margin-right: auto; width:40%; height:40%; margin-top: 50px;'";
    public static String backgroundstyler = "style=\"background-image: url('https://i.pinimg.com/originals/5e/9f/e2/5e9fe2b0bde19a68a87a095f92bc38aa.jpg');\"";

    public static String Navigationbar = " style=\"\n" +
            "  float: left;\n" +
            "  display: block;\n" +
            "  color: black;\n" +
            "  text-align: center;\n" +
            "  padding: 14px 16px;\n" +
            "  text-decoration: none;\n" +
            "  font-size: 17px;color:black;background-color:cyan;\"";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateStudent(request, response);
        showForm(request, response);
        response.sendRedirect("/students");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        tableData(request, response);
        showForm(request, response);
    }

    private void tableData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String top = "<html>" + "<body " + backgroundstyler + ">"
                + "<h1 style=\"color:black;background-color:cyan;text-align: center;margin: 0;\">Studenter som går till skolan</h1>"
                + "<a href=\"http://localhost:9090\"" + Navigationbar + "> Home </a>"
                + "<a href=/narvaro" + Navigationbar + "> Närvaro </a>"
                + "<a href=/students" + Navigationbar + "> Studenter </a>"
                + "<a href=/kurser" + Navigationbar + "> Kurser </a>"
                + "<a href=/AddCourse" + Navigationbar + "> Add Courses </a>"
                +" <a href=/Combine" + Navigationbar +"> combine </a>"
                + "<br>";

        try {
            out.println(top);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "user", "user");

                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM studenter");
                out.println("<table " + tablestyler + ">");
                out.println("<tr>");
                out.println("<th> id </th>");
                out.println("<th> Name </th>");
                out.println("<th> Lastname </th>");
                out.println("<th> Town </th>");
                out.println("<th> Hobby </th>");
                out.println("</tr>");

                while (rs.next()) {
                    out.println("<tr style = 'text-align: center;'>");
                    out.println("<td " + tablestyler + ">" + rs.getInt(1) + "</td>"
                            + "<td " + tablestyler + ">" + rs.getString(2) + "</td>"
                            + "<td " + tablestyler + ">" + rs.getString(3) + "</td>"
                            + "<td " + tablestyler + ">" + rs.getString(4) + "</td>"
                            + "<td " + tablestyler + ">" + rs.getString(5) + "</td>");
                    out.println("</tr>");
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/HTML");
        PrintWriter out = resp.getWriter();

        out.println("<br>"
                + "<div style='border:black solid; width:200px; padding:5px display:block; margin-left:auto; margin-right:auto; margin-top:5px; margin-bottom:5px;'>"
                + "<form style='margin:5px;' action=/AddStudent method=POST>"
                + "            <label for=fname>First Name:</label>"
                + "            <input pattern=\"[a-zA-Z]*\" type=text id=fname name=fname required><br><br>"
                + "             <label for=fname>Last name:</label>"
                + "            <input pattern=\"[a-zA-Z]*\" type=text id=lname name=lname required><br><br>"
                + "             <label for=ort>town:</label>"
                + "            <input pattern=\"[a-zA-Z-äöå]*\" type=text id=town name=town><br><br>"
                + "             <label for=intressen>hobby:</label>"
                + "            <input pattern=\"[a-zA-Z-äåö]*\" type=text id=hobby name=hobby><br><br>"
                + "            <input type=submit value=Submit>"
                + "        </form>"
                + "</div>"
                + "<br>"
        );
    }

    private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String top = "<html>" + "<body " + backgroundstyler + ">"
                + "<h1 style=\"color:black;background-color:cyan;text-align: center;margin: 0;\">Studenter som går till skolan</h1>"
                + "<a href=\"http://localhost:9090\"" + Navigationbar + "> Home </a>"
                + "<a href=/narvaro" + Navigationbar + "> Närvaro </a>"
                + "<a href=/kurser" + Navigationbar + "> Kurser </a>"
                + "<a href=/AddCourse" + Navigationbar + "> Add Courses </a>"
                +" <a href=/Combine" + Navigationbar +"> combine </a>"
                + "<br>";

        try {
            out.println(top);
            try {
                resp.setContentType("text/HTML");

                String fName = req.getParameter("fname");
                String lName = req.getParameter("lname");
                String town = req.getParameter("town");
                String hobby = req.getParameter("hobby");

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "inserter", "inserter");
                    st = con.createStatement();

                    PreparedStatement ps = con.prepareStatement("INSERT INTO studenter (fname, lname, town, hobby) VALUES (?, ?, ?, ?)");

                    ps.setString(1, fName);
                    ps.setString(2, lName);
                    ps.setString(3, town);
                    ps.setString(4, hobby);
                    ps.executeUpdate();

                    con.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}


