package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@RestController
public class HomeController {

    String url = "tech-app-db.cxeakiucmdfw.eu-north-1.rds.amazonaws.com";
    String dbUser = "admin";
    String dbPass = "techapp123";

    // HOME PAGE
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

    // SAVE CLIENT TO RDS
    @PostMapping("/save")
    public String save(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone) {

        String sql = "INSERT INTO clients(name,email,phone) VALUES(?,?,?)";

        try (Connection con = DriverManager.getConnection(url, dbUser, dbPass);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);

            ps.executeUpdate();

            return "✅ Client Saved Successfully in RDS";

        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }
}
