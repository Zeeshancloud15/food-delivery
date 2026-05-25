package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@RestController
public class HomeController {

    // AWS RDS DATABASE
    String url = "jdbc:mysql://foodapp.cxeakiucmdfw.eu-north-1.rds.amazonaws.com:3306/zeedata";
    String dbUser = "admin";
    String dbPass = "YOUR_PASSWORD";

    // HOME PAGE
    @GetMapping("/")
    public String home() {

        return """
        <html>
        <head>
        <title>Cloud Tech AI</title>
        <style>
            body {font-family:Arial;margin:0;background:#f4f6f9;}
            .nav {background:#111;color:white;padding:20px;text-align:center;font-size:28px;}
            .hero {background:#2563eb;color:white;padding:60px;text-align:center;}
            .section {padding:40px;text-align:center;}
            .card {display:inline-block;background:white;padding:20px;margin:10px;width:250px;border-radius:10px;box-shadow:0 0 10px rgba(0,0,0,0.1);}
            input,button {width:90%;padding:10px;margin:8px;}
            button {background:#2563eb;color:white;border:none;cursor:pointer;}
        </style>
        </head>

        <body>

        <div class="nav">Cloud Tech AI Solutions</div>

        <div class="hero">
            <h1>AI + Cloud + DevOps</h1>
            <p>We build scalable modern solutions</p>
        </div>

        <div class="section">
            <h2>Founder</h2>
            <div class="card">
                <h3>Mohd Zeeshan Uddin</h3>
                <p>Cloud & DevOps Engineer</p>
                <p>AWS | Docker | Kubernetes | Jenkins</p>
            </div>
        </div>

        <div class="section">
            <h2>Services</h2>

            <div class="card">AWS Cloud</div>
            <div class="card">DevOps CI/CD</div>
            <div class="card">AI Automation</div>
        </div>

        <div class="section">
            <h2>Products</h2>

            <div class="card">AI Chatbot</div>
            <div class="card">Cloud Dashboard</div>
            <div class="card">DevOps Toolkit</div>
        </div>

        <div class="section">
            <h2>Contact Client</h2>

            <form action="/save-client" method="post">
                <input type="text" name="name" placeholder="Name" required>
                <input type="email" name="email" placeholder="Email" required>
                <input type="text" name="phone" placeholder="Phone" required>
                <button type="submit">Submit</button>
            </form>
        </div>

        </body>
        </html>
        """;
    }

    // SAVE CLIENT INTO DATABASE
    @PostMapping("/save-client")
    public String saveClient(
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

            return "<h2 style='color:green;text-align:center;'>Client Saved Successfully</h2>"
                    + "<a href='/'>Go Back</a>";

        } catch (Exception e) {
            return "<h2 style='color:red;text-align:center;'>Error: " + e.getMessage() + "</h2>";
        }
    }
}
