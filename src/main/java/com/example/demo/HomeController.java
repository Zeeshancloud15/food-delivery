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

    String url = "jdbc:mysql://foodapp.cxeakiucmdfw.eu-north-1.rds.amazonaws.com:3306/zeedata";
    String dbUser = "admin";
    String dbPass = "foodapp123";

    // 🌐 HOME PAGE (Cloud Tech Website)
    @GetMapping("/")
    public String home() {

        return "<html>" +
                "<head>" +
                "<title>Cloud Tech AI</title>" +
                "<style>" +

                "body{margin:0;font-family:Arial;background:#0f172a;color:white;}" +

                ".navbar{background:#111827;padding:20px;text-align:center;font-size:28px;font-weight:bold;}" +

                ".hero{padding:70px;text-align:center;background:linear-gradient(90deg,#2563eb,#7c3aed);}" +
                ".hero h1{font-size:50px;}" +

                ".section{padding:40px;text-align:center;}" +

                ".card{background:#1f2937;padding:20px;margin:15px;display:inline-block;width:250px;border-radius:15px;}" +

                ".form-box{background:#111827;width:400px;margin:auto;padding:30px;border-radius:15px;}" +

                "input{width:90%;padding:12px;margin:10px;border-radius:10px;border:none;}" +

                "button{background:#2563eb;color:white;padding:12px 25px;border:none;border-radius:10px;cursor:pointer;}" +

                ".footer{background:#111;text-align:center;padding:20px;margin-top:30px;}" +

                "</style>" +
                "</head>" +

                "<body>" +

                "<div class='navbar'>Cloud Tech AI Solutions</div>" +

                "<div class='hero'>" +
                "<h1>Cloud • DevOps • AI Automation</h1>" +
                "<p>Build Future with Smart Cloud Systems</p>" +
                "</div>" +

                // 👇 AI IMAGES SECTION (simple placeholders)
                "<div class='section'>" +
                "<h2>AI Images Gallery</h2>" +
                "<div class='card'>AI Cloud System</div>" +
                "<div class='card'>DevOps Pipeline</div>" +
                "<div class='card'>AI Automation</div>" +
                "</div>" +

                // 👇 ABOUT YOU
                "<div class='section'>" +
                "<h2>About Me</h2>" +
                "<p>Name: Mohd Zeeshan Uddin</p>" +
                "<p>Email: zeeshancloud15@gmail.com</p>" +
                "<p>Phone: 7780369370</p>" +
                "</div>" +

                // 👇 CLIENT FORM
                "<div class='section'>" +
                "<div class='form-box'>" +
                "<h2>Client Contact Form</h2>" +
                "<form action='/save-client' method='post'>" +

                "<input type='text' name='name' placeholder='Enter Name' required>" +
                "<input type='email' name='email' placeholder='Enter Email' required>" +
                "<input type='text' name='phone' placeholder='Enter Phone' required>" +

                "<button type='submit'>Submit</button>" +

                "</form>" +
                "</div>" +
                "</div>" +

                "<div class='footer'>" +
                "© 2026 Cloud Tech AI | Zeeshan Uddin" +
                "</div>" +

                "</body>" +
                "</html>";
    }

    // 💾 SAVE CLIENT DATA INTO DB
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

            return "<h1 style='color:green;text-align:center;margin-top:100px;'>Client Saved Successfully 🚀</h1>" +
                   "<div style='text-align:center;'><a href='/'>Back</a></div>";

        } catch (Exception e) {
            e.printStackTrace();
            return "<h1>Error: " + e.getMessage() + "</h1>";
        }
    }
}
