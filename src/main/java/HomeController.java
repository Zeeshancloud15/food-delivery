package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    String url =
    "jdbc:mysql://foodapp.cxeakiucmdfw.eu-north-1.rds.amazonaws.com:3306/foodapp";

    String dbUser = "admin";

    String dbPass = "foodapp123";

    @GetMapping("/")
    public String loginPage() {

        return "<h1>User Login</h1>" +

                "<form action='/login' method='post'>" +

                "<input type='email' name='email' placeholder='Email'/> <br><br>" +

                "<input type='password' name='password' placeholder='Password'/> <br><br>" +

                "<button type='submit'>Login</button>" +

                "</form>" +

                "<br><br>" +

                "<a href='/signup'>Create Account</a>";
    }

    @GetMapping("/signup")
    public String signupPage() {

        return "<h1>User Signup</h1>" +

                "<form action='/register' method='post'>" +

                "<input type='text' name='username' placeholder='Username'/> <br><br>" +

                "<input type='email' name='email' placeholder='Email'/> <br><br>" +

                "<input type='password' name='password' placeholder='Password'/> <br><br>" +

                "<button type='submit'>Signup</button>" +

                "</form>";
    }

    @PostMapping("/register")
    public String register(

            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {

        try {

            Connection con =
                    DriverManager.getConnection(
                            url,
                            dbUser,
                            dbPass);

            String sql =
                    "INSERT INTO users(username,email,password) VALUES(?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();

            con.close();

            return "<h1>User Registered Successfully</h1>" +

                    "<a href='/'>Go To Login</a>";

        } catch (Exception e) {

            return "<h1>Database Error</h1>";
        }
    }

    @PostMapping("/login")
    public String login(

            @RequestParam String email,
            @RequestParam String password) {

        try {

            Connection con =
                    DriverManager.getConnection(
                            url,
                            dbUser,
                            dbPass);

            String sql =
                    "SELECT * FROM users WHERE email=? AND password=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return "<h1>Login Successful</h1>" +

                        "<h2>Welcome "
                        + rs.getString("username")
                        + "</h2>";
            }

            con.close();

            return "<h1>Invalid Email or Password</h1>";

        } catch (Exception e) {

            return "<h1>Database Error</h1>";
        }
    }
}
