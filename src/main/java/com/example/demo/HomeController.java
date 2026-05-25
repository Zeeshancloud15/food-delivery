package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@RestController
public class HomeController {

    String url = "jdbc:mysql://foodapp.cxeakiucmdfw.eu-north-1.rds.amazonaws.com:3306/zeedata";
    String dbUser = "admin";
    String dbPass = "foodapp123";

    // HOME WEBSITE
    @GetMapping("/")
    public String home() {
        return """
        <html>
        <body style='font-family:Arial;text-align:center;background:#f5f5f5;'>

        <h1>Cloud Tech Website</h1>
        <p>AI | DevOps | AWS | Kubernetes</p>

        <h2>Client Form</h2>

        <form method='post' action='/save'>
            <input name='name' placeholder='Name' required/><br><br>
            <input name='email' placeholder='Email' required/><br><br>
            <input name='phone' placeholder='Phone' required/><br><br>
            <button type='submit'>Submit</button>
        </form>

        <br>
        <h3>CEO: Zeeshan Uddin</h3>

        </body>
        </html>
        """;
    }

    // SAVE TO RDS
    @PostMapping("/save")
    public String save(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone) {

        try {
            Connection con = DriverManager.getConnection(url, dbUser, dbPass);

            String sql = "INSERT INTO zeetable(name,email,phone) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);

            ps.executeUpdate();
            con.close();

            return "Data Saved Successfully in RDS ✅";

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
