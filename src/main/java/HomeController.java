package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    String url = "jdbc:mysql://foodapp.cxeakiucmdfw.eu-north-1.rds.amazonaws.com:3306/foodapp";

    String dbUser = "admin";

    String dbPass = "foodapp123";

    @GetMapping("/")
    public String loginPage() {

        return "<html>" +

                "<head>" +

                "<title>Food Delivery</title>" +

                "<style>" +

                "body{font-family:Arial;background:linear-gradient(to right,#ff512f,#dd2476);display:flex;justify-content:center;align-items:center;height:100vh;margin:0;}" +

                ".box{background:white;padding:40px;border-radius:20px;width:350px;text-align:center;box-shadow:0px 0px 20px rgba(0,0,0,0.3);}" +

                "input{width:90%;padding:12px;margin:10px;border-radius:10px;border:1px solid #ccc;}" +

                "button{background:#ff512f;color:white;padding:12px;border:none;border-radius:10px;width:95%;font-size:18px;}" +

                "a{text-decoration:none;color:#dd2476;font-weight:bold;}" +

                "</style>" +

                "</head>" +

                "<body>" +

                "<div class='box'>" +

                "<h1>Food Delivery Login</h1>" +

                "<form action='/login' method='post'>" +

                "<input type='email' name='email' placeholder='Enter Email' required/>" +

                "<input type='password' name='password' placeholder='Enter Password' required/>" +

                "<button type='submit'>Login</button>" +

                "</form>" +

                "<br>" +

                "<a href='/signup'>Create Account</a>" +

                "</div>" +

                "</body>" +

                "</html>";
    }

    @GetMapping("/signup")
    public String signupPage() {

        return "<html>" +

                "<head>" +

                "<title>Signup</title>" +

                "<style>" +

                "body{font-family:Arial;background:linear-gradient(to right,#fc466b,#3f5efb);display:flex;justify-content:center;align-items:center;height:100vh;margin:0;}" +

                ".box{background:white;padding:40px;border-radius:20px;width:350px;text-align:center;box-shadow:0px 0px 20px rgba(0,0,0,0.3);}" +

                "input{width:90%;padding:12px;margin:10px;border-radius:10px;border:1px solid #ccc;}" +

                "button{background:#3f5efb;color:white;padding:12px;border:none;border-radius:10px;width:95%;font-size:18px;}" +

                "</style>" +

                "</head>" +

                "<body>" +

                "<div class='box'>" +

                "<h1>Create Account</h1>" +

                "<form action='/register' method='post'>" +

                "<input type='text' name='username' placeholder='Username' required/>" +

                "<input type='email' name='email' placeholder='Email' required/>" +

                "<input type='password' name='password' placeholder='Password' required/>" +

                "<button type='submit'>Signup</button>" +

                "</form>" +

                "</div>" +

                "</body>" +

                "</html>";
    }

    @PostMapping("/register")
    public String register(

            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {

        try {

            Connection con = DriverManager.getConnection(url, dbUser, dbPass);

            String sql = "INSERT INTO users(username,email,password) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();

            con.close();

            return "<h1 style='color:green;text-align:center;margin-top:100px;'>User Registered Successfully</h1>" +

                    "<center><a href='/'>Go To Login</a></center>";

        } catch (Exception e) {

            return "<h1>Database Error</h1>";
        }
    }

    @PostMapping("/login")
    public String login(

            @RequestParam String email,
            @RequestParam String password) {

        try {

            Connection con = DriverManager.getConnection(url, dbUser, dbPass);

            String sql = "SELECT * FROM users WHERE email=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return "<html>" +

                        "<head>" +

                        "<title>Food App</title>" +

                        "<style>" +

                        "body{font-family:Arial;margin:0;background:#f5f5f5;}" +

                        ".navbar{background:#ff3d00;color:white;padding:20px;font-size:28px;font-weight:bold;}" +

                        ".container{padding:30px;}" +

                        ".card{background:white;width:250px;border-radius:15px;display:inline-block;margin:15px;overflow:hidden;box-shadow:0px 0px 10px rgba(0,0,0,0.2);}" +

                        ".card img{width:100%;height:180px;}" +

                        ".card h2{text-align:center;}" +

                        ".btn{display:block;background:#ff3d00;color:white;text-align:center;padding:10px;text-decoration:none;}" +

                        "</style>" +

                        "</head>" +

                        "<body>" +

                        "<div class='navbar'>Welcome " + rs.getString("username") + "</div>" +

                        "<div class='container'>" +

                        "<div class='card'>" +

                        "<img src='https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=600'>" +

                        "<h2>Pizza</h2>" +

                        "<a class='btn'>Order Now</a>" +

                        "</div>" +

                        "<div class='card'>" +

                        "<img src='https://images.unsplash.com/photo-1550547660-d9450f859349?w=600'>" +

                        "<h2>Burger</h2>" +

                        "<a class='btn'>Order Now</a>" +

                        "</div>" +

                        "<div class='card'>" +

                        "<img src='https://images.unsplash.com/photo-1604908176997-125f25cc6f3d?w=600'>" +

                        "<h2>Biryani</h2>" +

                        "<a class='btn'>Order Now</a>" +

                        "</div>" +

                        "</div>" +

                        "</body>" +

                        "</html>";
            }

            con.close();

            return "<h1>Invalid Email Or Password</h1>";

        } catch (Exception e) {

            return "<h1>Database Error</h1>";
        }
    }
}
