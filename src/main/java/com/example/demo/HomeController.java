package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    String url =
        "jdbc:mysql://foodapp.cxeakiucmdfw.eu-north-1.rds.amazonaws.com:3306/foodapp";

    String username = "admin";

    String password = "foodapp123";

    @GetMapping("/")
    public String home() {

        return "<html>" +

                "<head>" +

                "<title>ZeeshanCloudTech</title>" +

                "<style>" +

                "body{margin:0;font-family:Arial;background:#0f172a;color:white;}" +

                ".navbar{background:#111827;padding:20px;display:flex;justify-content:space-between;}" +

                ".logo{font-size:28px;font-weight:bold;color:#38bdf8;}" +

                ".menu a{color:white;text-decoration:none;margin:15px;font-size:18px;}" +

                ".hero{padding:80px;text-align:center;background:linear-gradient(to right,#0f172a,#1e3a8a);}" +

                ".hero h1{font-size:55px;color:#38bdf8;}" +

                ".hero p{font-size:22px;}" +

                ".btn{background:#38bdf8;color:black;padding:15px 30px;border-radius:10px;text-decoration:none;font-size:20px;}" +

                ".services{padding:50px;text-align:center;}" +

                ".card{background:#1e293b;width:300px;padding:20px;margin:20px;border-radius:20px;display:inline-block;}" +

                ".card img{width:100%;height:200px;border-radius:15px;}" +

                ".login{background:white;color:black;width:400px;margin:auto;padding:30px;border-radius:20px;}" +

                "input{width:90%;padding:12px;margin:10px;border-radius:10px;border:1px solid gray;}" +

                "button{padding:12px 25px;background:#38bdf8;border:none;border-radius:10px;font-size:18px;}" +

                "</style>" +

                "</head>" +

                "<body>" +

                "<div class='navbar'>" +

                "<div class='logo'>ZeeshanCloudTech</div>" +

                "<div class='menu'>" +

                "<a href='#'>Home</a>" +

                "<a href='#'>Services</a>" +

                "<a href='#'>Clients</a>" +

                "<a href='#'>Contact</a>" +

                "</div>" +

                "</div>" +

                "<div class='hero'>" +

                "<h1>Cloud & AI Solutions</h1>" +

                "<p>Helping Businesses Scale With AWS DevOps & AI</p>" +

                "<br>" +

                "<a class='btn' href='#login'>Connect With Team</a>" +

                "</div>" +

                "<div class='services'>" +

                "<h1>Our Services</h1>" +

                "<div class='card'>" +

                "<img src='https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=600'>" +

                "<h2>AWS Cloud</h2>" +

                "<p>Cloud Migration & Infrastructure</p>" +

                "</div>" +

                "<div class='card'>" +

                "<img src='https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=600'>" +

                "<h2>DevOps Automation</h2>" +

                "<p>CI/CD Jenkins Kubernetes Docker</p>" +

                "</div>" +

                "<div class='card'>" +

                "<img src='https://images.unsplash.com/photo-1487058792275-0ad4aaf24ca7?w=600'>" +

                "<h2>AI Solutions</h2>" +

                "<p>AI Automation For Businesses</p>" +

                "</div>" +

                "</div>" +

                "<div id='login' style='padding:50px;text-align:center;'>" +

                "<div class='login'>" +

                "<h1>Client Login</h1>" +

                "<form action='/save-client'>" +

                "<input type='text' name='name' placeholder='Company Name' required>" +

                "<input type='email' name='email' placeholder='Business Email' required>" +

                "<input type='text' name='phone' placeholder='Phone Number' required>" +

                "<button type='submit'>Connect Our Team</button>" +

                "</form>" +

                "</div>" +

                "</div>" +

                "<div style='padding:40px;text-align:center;background:#111827;'>" +

                "<h2>CEO : Mohd Zeeshan Uddin</h2>" +

                "<h3>Founder : Ibrahim</h3>" +

                "<p>Email : zeeshancloud15@gmail.com</p>" +

                "<p>Phone : 7780369370</p>" +

                "</div>" +

                "</body>" +

                "</html>";
    }

    @GetMapping("/save-client")
    public String saveClient(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone) {

        try {

            Connection con = DriverManager.getConnection(url, username, password);

            String sql =
                "INSERT INTO clients(name,email,phone) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);

            ps.executeUpdate();

            con.close();

            return "<h1 style='text-align:center;margin-top:100px;color:green;'>Client Data Saved Successfully</h1>";

        } catch (Exception e) {

            return "<h1>Database Error : "
                    + e.getMessage() +
                    "</h1>";
        }
    }
}
