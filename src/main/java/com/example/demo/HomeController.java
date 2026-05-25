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

    // DB NAME = zeedata
    String url = "jdbc:mysql://foodapp.cxeakiucmdfw.eu-north-1.rds.amazonaws.com:3306/zeedata";

    String dbUser = "admin";
    String dbPass = "foodapp123";

    @GetMapping("/")
    public String home() {
        return "Zee App Running Successfully";
    }

    @PostMapping("/save-client")
    public String saveClient(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone) {

        try {
            Connection con = DriverManager.getConnection(url, dbUser, dbPass);

            // TABLE NAME = zeetable
            String sql = "INSERT INTO zeetable(name,email,phone) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);

            ps.executeUpdate();

            con.close();

            return "Data Saved Successfully in zeetable";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
