@RestController
public class HomeController {

    String url = "jdbc:mysql://foodapp.cxeakiucmdfw.eu-north-1.rds.amazonaws.com:3306/zeedata";
    String dbUser = "admin";
    String dbPass = "foodapp123";

    @PostMapping("/save-client")
    public String saveClient(String name, String email, String phone) {

        try {
            Connection con = DriverManager.getConnection(url, dbUser, dbPass);

            String sql = "INSERT INTO zeetable(name,email,phone) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);

            ps.executeUpdate();

            con.close();

            return "Saved Successfully";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
