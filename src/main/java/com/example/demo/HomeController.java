package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    String url = "jdbc:mysql://foodapp.cxeakiucmdfw.eu-north-1.rds.amazonaws.com:3306/techapp";
    String username = "admin";
    String password = "foodapp123";

    @GetMapping("/")
    public String home() {

        return "<html>" +
                "<head>" +
                "<title>ZeeshanCloudTech</title>" +

                "<style>" +

                "body{font-family:Arial;margin:0;background:#f5f5f5;}" +

                ".navbar{background:#111;color:white;padding:20px;font-size:22px;font-weight:bold;}" +

                ".hero{padding:80px;text-align:center;background:#0d6efd;color:white;}" +

                ".hero h1{font-size:50px;}" +

                ".section{padding:40px;text-align:center;}" +

                ".card{background:white;padding:30px;margin:20px;display:inline-block;width:250px;border-radius:15px;box-shadow:0px 0px 10px rgba(0,0,0,0.2);}" +

                "input{width:80%;padding:12px;margin:10px;border:1px solid #ccc;border-radius:10px;}" +

                "button{background:#0d6efd;color:white;padding:12px 25px;border:none;border-radius:10px;font-size:18px;}" +

                "</style>" +

                "</head>" +

                "<body>" +

                "<div class='navbar'>" +

                "ZeeshanCloudTech" +

                "</div>" +

                "<div class='hero'>" +

                "<h1>Cloud & AI Solutions</h1>" +

                "<h2>Helping Businesses Scale With AWS DevOps & AI</h2>" +

                "</div>" +

                "<div class='section'>" +

                "<h1>Our Services</h1>" +

                "<div class='card'>" +

                "<h2>AWS Cloud</h2>" +

                "<p>Cloud Migration & Infrastructure</p>" +

                "</div>" +

                "<div class='card'>" +

                "<h2>DevOps Automation</h2>" +

                "<p>CI/CD Jenkins Kubernetes Docker</p>" +

                "</div>" +

                "<div class='card'>" +

                "<h2>AI Solutions</h2>" +

                "<p>AI Automation For Businesses</p>" +

                "</div>" +

                "</div>" +

                "<div class='section'>" +

                "<h1>Client Login</h1>" +

                "<form action='/save-client' method='post'>" +

                "<input type='text' name='name' placeholder='Enter Name' required><br>" +

                "<input type='email' name='email' placeholder='Enter Email' required><br>" +

                "<input type='text' name='phone' placeholder='Enter Phone Number' required><br>" +

                "<button type='submit'>Connect Our Team</button>" +

                "</form>" +

                "</div>" +

                "<div class='section'>" +

                "<h1>CEO : Mohd Zeeshan Uddin</h1>" +

                "<h2>Founder : Ibrahim</h2>" +

                "<h3>Email : zeeshancloud15@gmail.com</h3>" +

                "<h3>Phone : 7780369370</h3>" +

                "</div>" +

                "</body>" +

                "</html>";
    }

    @PostMapping("/save-client")
    public String saveClient(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone) {

        try {

            Connection con = DriverManager.getConnection(url, username, password);

            String sql = "INSERT INTO clients(name,email,phone) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);

            ps.executeUpdate();

            con.close();

            return "<html>" +
                    "<body style='font-family:Arial;text-align:center;padding-top:100px;background:#f5f5f5;'>" +
                    "<h1 style='color:green;'>Client Data Saved Successfully</h1>" +
                    "<a href='/clients'>View Clients</a>" +
                    "</body>" +
                    "</html>";

        } catch (Exception e) {

            return "Database Error : " + e.getMessage();
        }
    }

    @GetMapping("/clients")
    public String clients() {

        String data = "";

        try {

            Connection con = DriverManager.getConnection(url, username, password);

            String sql = "SELECT * FROM clients";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                data += "<div style='background:white;padding:20px;margin:20px;border-radius:10px;'>" +

                        "<h2>Name : " + rs.getString("name") + "</h2>" +

                        "<h3>Email : " + rs.getString("email") + "</h3>" +

                        "<h3>Phone : " + rs.getString("phone") + "</h3>" +

                        "</div>";
            }

            con.close();

        } catch (Exception e) {

            return "Database Error : " + e.getMessage();
        }

        return "<html>" +
                "<body style='font-family:Arial;background:#f5f5f5;padding:30px;'>" +
                "<h1>All Clients</h1>" +
                data +
                "</body>" +
                "</html>";
    }
}
