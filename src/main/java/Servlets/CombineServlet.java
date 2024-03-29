package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = "/Combine")
public class CombineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Connection con;
    private static Statement st;
    private static ResultSet rs;
    private static String tablestyler = "style='border: 1px solid black; background-color: #96D4D4; margin-left: auto; margin-right: auto; width:40%; height:15%; margin-top: 50px;'";
    private static String tablestyler2 = "style='border: 1px solid black; background-color: #96D4D4; float:right;  width:40%; height:50%; margin-top: 85px;'";
    private static String tablestyler3 = "style='border: 1px solid black; background-color: #96D4D4; float:left; width:40%; height:5%;  margin-top: 50px;'";

    private static String backgroundstyler = "style=\"background-image: url('https://i.pinimg.com/originals/5e/9f/e2/5e9fe2b0bde19a68a87a095f92bc38aa.jpg');\"";

    private static String Navigationbar = " style=\"\n"
            + "  float: left;\n"
            + "  display: block;\n"
            + "  color: black;\n"
            + "  text-align: center;\n"
            + "  padding: 14px 16px;\n"
            + "  text-decoration: none;\n"
            + "  font-size: 17px;color:black;background-color:cyan;\"";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        tableData(request,response);
        showForm(request, response);
        updater(request,response);
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
                + "<a href=narvaro" + Navigationbar + "> Närvaro </a>"
                + "<a href=/students" + Navigationbar + "> Studenter </a>"
                + "<a href=/kurser" + Navigationbar + "> Kurser </a>"
                + "<a href=/AddStudent" + Navigationbar + "> Add Studdents </a>"
                + "<a href=/AddCourse" + Navigationbar + "> Add Courses </a>"
                + "<br>";

        try {
            out.println(top);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "user", "user");

                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM studenter");
                out.println("<table " + tablestyler2 + ">");
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
            } catch (Exception e) {
                System.out.println(e);
            }
            out.println("</table>");
            rs = st.executeQuery("SELECT * FROM kurser");
            out.println("<table " + tablestyler3 + ">");
            out.println("<tr>");
            out.println("<th> id </th>");
            out.println("<th> Name </th>");
            out.println("<th> YHP </th>");
            out.println("<th> Beskrivning </th>");
            out.println("</tr>");
            while (rs.next()) {
                out.println("<tr style = 'text-align: center;'>");
                out.println("<td " + tablestyler + ">" + rs.getInt(1) + "</td>"
                        + "<td " + tablestyler + ">" + rs.getString(2) + "</td>"
                        + "<td " + tablestyler + ">" + rs.getString(3) + "</td>"
                        + "<td " + tablestyler + ">" + rs.getString(4) + "</td>");
                out.println("</tr>");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    private void showForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/HTML");
        PrintWriter out = resp.getWriter();

        out.println("<br>"
                + "<div style='border:black solid; width:200px; padding:5px display:block; margin-left:auto; margin-right:auto; margin-top:5px; margin-top:450px;'>"
                + "<form style='margin:5px;' action=/Combine method=POST>"
                + "            <label for=student_id>StudentID:</label>"
                + "            <input type=number id=student_id name=student_id required><br><br>"
                + "             <label for=kurs_id>CourseID:</label>"
                + "            <input type=number id=kurs_id name=kurs_id required><br><br>"
                + "            <input type=submit value=Submit>"
                + "            <button type=button id=reset onclick=location.href='/Combine'> reset </button>"
                + "        </form>"
                + "</div>"
                + "<br>"
        );
    }
    private void updater(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

            try {
                resp.setContentType("text/HTML");

                String student_id = req.getParameter("student_id");
                String kurs_id = req.getParameter("kurs_id");
                boolean studentFinder = false;

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gritacademy", "inserter", "inserter");
                    st = con.createStatement();

                    PreparedStatement ps = con.prepareStatement("INSERT INTO närvaro (student_id, kurs_id) VALUES (?, ?)");

                    ps.setString(1, student_id);
                    ps.setString(2, kurs_id);
                    ps.executeUpdate();

                    con.close();
                }catch (Exception e) {
                        studentFinder = true;
                    }
                if (studentFinder) {
                    out.println("<p style ='color:red;margin-top:50px;text-align: center; font-size: 15px; background-color:black; width:20%; margin-right:auto; margin-left:auto;'> Cannot add the student to this course beacause the student is already apart of this course </p>");
                }else{
                    resp.sendRedirect("/narvaro");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
}

