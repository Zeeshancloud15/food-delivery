package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    String url = "jdbc:mysql://foodapp.cxeakiucmdfw.eu-north-1.rds.amazonaws.com:3306/techapp";

    String dbUser = "admin";

    String dbPass = "foodapp123";

    // HOME PAGE

    @GetMapping("/")
    public String homePage() {

        return "<html>" +

                "<head>" +

                "<title>Sal Tech</title>" +

                "<style>" +

                "body{margin:0;font-family:Arial;background:#f5f5f5;}" +

                ".navbar{background:#111;color:white;padding:20px;font-size:28px;font-weight:bold;text-align:center;}" +

                ".hero{background:#0d6efd;color:white;padding:80px;text-align:center;}" +

                ".hero h1{font-size:55px;margin:0;}" +

                ".hero p{font-size:22px;}" +

                ".section{padding:50px;text-align:center;}" +

                ".card{background:white;width:260px;padding:25px;margin:20px;display:inline-block;border-radius:15px;box-shadow:0px 0px 15px rgba(0,0,0,0.2);}" +

                ".form-box{background:white;width:400px;margin:auto;padding:40px;border-radius:20px;box-shadow:0px 0px 15px rgba(0,0,0,0.2);}" +

                "input{width:90%;padding:12px;margin:10px;border:1px solid #ccc;border-radius:10px;}" +

                "button{background:#0d6efd;color:white;padding:12px 25px;border:none;border-radius:10px;font-size:18px;cursor:pointer;}" +

                ".footer{background:#111;color:white;text-align:center;padding:30px;margin-top:40px;}" +

                "</style>" +

                "</head>" +

                "<body>" +

                // NAVBAR

                "<div class='navbar'>" +

                "Sal Tech" +

                "</div>" +

                // HERO

                "<div class='hero'>" +

                "<h1>Cloud & AI Solutions</h1>" +

                "<p>Smart Cloud. Fast DevOps. Future AI.</p>" +

                "</div>" +

                // SERVICES

                "<div class='section'>" +

                "<h1>Our Services</h1>" +

                "<div class='card'>" +

                "<h2>AWS Cloud</h2>" +

                "<p>Cloud Migration & Infrastructure</p>" +

                "</div>" +

                "<div class='card'>" +

                "<h2>DevOps Automation</h2>" +

                "<p>CI/CD Jenkins Docker Kubernetes</p>" +

                "</div>" +

                "<div class='card'>" +

                "<h2>AI Solutions</h2>" +

                "<p>AI Automation For Businesses</p>" +

                "</div>" +

                "</div>" +

                // FORM

                "<div class='section'>" +

                "<div class='form-box'>" +

                "<h1>Connect With Team</h1>" +

                "<form action='/save-client' method='post'>" +

                "<input type='text' name='name' placeholder='Enter Your Name' required><br>" +

                "<input type='email' name='email' placeholder='Enter Email' required><br>" +

                "<input type='text' name='phone' placeholder='Enter Phone Number' required><br>" +

                "<button type='submit'>Submit</button>" +

                "</form>" +

                "</div>" +

                "</div>" +

                // FOOTER

                "<div class='footer'>" +

                "<h2>CEO : Mohd Zeeshan Uddin</h2>" +

                "<h3>Email : zeeshancloud15@gmail.com</h3>" +

                "<h3>Phone : 7780369370</h3>" +

                "</div>" +

                "</body>" +

                "</html>";
    }

    // SAVE CLIENT DATA INTO AWS RDS

    @PostMapping("/save-client")
    public String saveClient(

            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone) {

        try {

            Connection con = DriverManager.getConnection(url, dbUser, dbPass);

            String sql = "INSERT INTO clients(name,email,phone) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);

            ps.setString(2, email);

            ps.setString(3, phone);

            ps.executeUpdate();

            con.close();

            return "<html>" +

                    "<body style='font-family:Arial;background:#f5f5f5;text-align:center;padding-top:100px;'>" +

                    "<h1 style='color:green;'>Data Stored Successfully In AWS RDS</h1>" +

                    "<br>" +

                    "<a href='/' style='background:#0d6efd;color:white;padding:15px 25px;text-decoration:none;border-radius:10px;'>Back To Home</a>" +

                    "</body>" +

                    "</html>";

        } catch (Exception e) {

            return "<h1>Database Error : " + e.getMessage() + "</h1>";
        }
    }
}
