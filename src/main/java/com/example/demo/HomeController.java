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

    String username = "admin";

    String password = "foodapp123";

    @GetMapping("/")
    public String home() {

        return "<html>" +

                "<head>" +

                "<title>ZeeshanCloudTech</title>" +

                "<style>" +

                "body{font-family:Arial;margin:0;background:#f5f5f5;}" +

                ".navbar{background:#111;color:white;padding:20px;font-size:25px;font-weight:bold;text-align:center;}" +

                ".hero{background:#0d6efd;color:white;padding:80px;text-align:center;}" +

                ".hero h1{font-size:50px;}" +

                ".section{padding:40px;text-align:center;}" +

                ".card{background:white;width:250px;padding:25px;margin:20px;display:inline-block;border-radius:15px;box-shadow:0px 0px 10px rgba(0,0,0,0.2);}" +

                ".form-box{background:white;width:400px;margin:auto;padding:40px;border-radius:20px;box-shadow:0px 0px 15px rgba(0,0,0,0.2);}" +

                "input{width:90%;padding:12px;margin:10px;border-radius:10px;border:1px solid #ccc;}" +

                "button{background:#0d6efd;color:white;padding:12px 25px;border:none;border-radius:10px;font-size:18px;cursor:pointer;}" +

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

                "<div class='form-box'>" +

                "<h1>Client Login</h1>" +

                "<form action='/save-client' method='post'>" +

                "<input type='text' name='name' placeholder='Enter Name' required><br>" +

                "<input type='email' name='email' placeholder='Enter Email' required><br>" +

                "<input type='text' name='phone' placeholder='Enter Phone Number' required><br>" +

                "<button type='submit'>Connect Our Team</button>" +

                "</form>" +

                "</div>" +

                "</div>" +

                "<div class='section'>" +

                "<h2>CEO : Mohd Zeeshan Uddin</h2>" +

                "<h3>Founder : Ibrahim</h3>" +

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

                    "<h1 style='color:green;'>Data Stored Successfully In AWS RDS Database</h1>" +

                    "<br>" +

                    "<a href='/' style='background:#0d6efd;color:white;padding:15px 25px;text-decoration:none;border-radius:10px;'>Back To Home</a>" +

                    "</body>" +

                    "</html>";

        } catch (Exception e) {

            return "<h1>Database Error : " + e.getMessage() + "</h1>";
        }
    }
}
