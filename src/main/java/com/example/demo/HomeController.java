package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    String url = "jdbc:mysql://foodapp.cxeakiucmdfw.eu-north-1.rds.amazonaws.com:3306/foodapp";
    String dbUser = "admin";
    String dbPass = "foodapp123";

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    Map<String, String> otpStore = new HashMap<>();

    // ================= LOGIN PAGE =================

    @GetMapping("/")
    public String loginPage() {

        return """
        <html>
        <head>
        <title>ZeeshanCloudTech Food Delivery</title>

        <style>

        body{
            font-family:Arial;
            margin:0;
            background:#f5f5f5;
        }

        .navbar{
            background:#ff3d00;
            color:white;
            padding:20px;
            text-align:center;
            font-size:30px;
            font-weight:bold;
        }

        .container{
            display:flex;
            justify-content:center;
            align-items:center;
            height:85vh;
        }

        .box{
            background:white;
            padding:40px;
            width:350px;
            border-radius:20px;
            text-align:center;
            box-shadow:0px 0px 15px rgba(0,0,0,0.2);
        }

        input{
            width:90%;
            padding:12px;
            margin:10px;
            border-radius:10px;
            border:1px solid #ccc;
        }

        button{
            width:95%;
            padding:12px;
            background:#ff3d00;
            color:white;
            border:none;
            border-radius:10px;
            font-size:18px;
            cursor:pointer;
        }

        a{
            text-decoration:none;
            color:#ff3d00;
            font-weight:bold;
        }

        </style>
        </head>

        <body>

        <div class='navbar'>
        ZeeshanCloudTech Food Delivery
        </div>

        <div class='container'>

        <div class='box'>

        <h2>Login With OTP</h2>

        <form action='/send-otp' method='post'>

        <input type='email' name='email' placeholder='Enter Email' required>

        <button type='submit'>Send OTP</button>

        </form>

        <br>

        <a href='/signup'>Create Account</a>

        <br><br>

        <h4>📞 7780369370</h4>
        <h4>📧 zeeshancloud15@gmail.com</h4>

        </div>

        </div>

        </body>
        </html>
        """;
    }

    // ================= SIGNUP PAGE =================

    @GetMapping("/signup")
    public String signupPage() {

        return """
        <html>
        <head>

        <title>Signup</title>

        <style>

        body{
            font-family:Arial;
            background:linear-gradient(to right,#ff512f,#dd2476);
            display:flex;
            justify-content:center;
            align-items:center;
            height:100vh;
            margin:0;
        }

        .box{
            background:white;
            padding:40px;
            border-radius:20px;
            width:350px;
            text-align:center;
        }

        input{
            width:90%;
            padding:12px;
            margin:10px;
            border-radius:10px;
            border:1px solid #ccc;
        }

        button{
            width:95%;
            padding:12px;
            background:#ff3d00;
            color:white;
            border:none;
            border-radius:10px;
            font-size:18px;
        }

        </style>

        </head>

        <body>

        <div class='box'>

        <h1>Create Account</h1>

        <form action='/register' method='post'>

        <input type='text' name='username' placeholder='Username' required>

        <input type='email' name='email' placeholder='Email' required>

        <input type='password' name='password' placeholder='Password' required>

        <input type='text' name='mobile' placeholder='Mobile Number' required>

        <input type='text' name='address' placeholder='Address' required>

        <button type='submit'>Signup</button>

        </form>

        </div>

        </body>
        </html>
        """;
    }

    // ================= REGISTER =================

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String mobile,
            @RequestParam String address) {

        try {

            Connection con = DriverManager.getConnection(url, dbUser, dbPass);

            String hashedPassword = encoder.encode(password);

            String sql = "INSERT INTO users(username,email,password,mobile,address) VALUES(?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, hashedPassword);
            ps.setString(4, mobile);
            ps.setString(5, address);

            ps.executeUpdate();

            con.close();

            return """
            <h1 style='color:green;text-align:center;margin-top:100px;'>
            User Registered Successfully
            </h1>

            <center>
            <a href='/'>Go To Login</a>
            </center>
            """;

        } catch (Exception e) {

            return "<h1>Database Error : " + e.getMessage() + "</h1>";
        }
    }

    // ================= SEND OTP =================

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {

        Random random = new Random();

        String otp = String.valueOf(1000 + random.nextInt(9000));

        otpStore.put(email, otp);

        return """
        <html>

        <body style='font-family:Arial;text-align:center;padding-top:100px;'>

        <h1>OTP Sent Successfully</h1>

        <h2>Your OTP : """ + otp + """

        </h2>

        <form action='/verify-otp' method='post'>

        <input type='hidden' name='email' value='""" + email + """'>

        <input type='text' name='otp' placeholder='Enter OTP'
        style='padding:10px;width:250px;' required>

        <br><br>

        <button style='padding:10px 20px;
        background:#ff3d00;
        color:white;
        border:none;
        border-radius:10px;'>

        Verify OTP

        </button>

        </form>

        </body>

        </html>
        """;
    }

    // ================= VERIFY OTP =================

    @PostMapping("/verify-otp")
    public String verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {

        try {

            if (otp.equals(otpStore.get(email))) {

                Connection con = DriverManager.getConnection(url, dbUser, dbPass);

                String sql = "SELECT * FROM users WHERE email=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, email);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    int userId = rs.getInt("id");

                    return homePage(rs.getString("username"), userId);
                }
            }

            return "<h1>Invalid OTP</h1>";

        } catch (Exception e) {

            return "<h1>Login Error : " + e.getMessage() + "</h1>";
        }
    }

    // ================= HOME PAGE =================

    public String homePage(String username, int userId) {

        return """
        <html>

        <head>

        <title>Zeeshan Food App</title>

        <style>

        body{
            font-family:Arial;
            margin:0;
            background:#f5f5f5;
        }

        .navbar{
            background:#ff3d00;
            color:white;
            padding:20px;
            display:flex;
            justify-content:space-between;
            font-size:24px;
        }

        .container{
            padding:20px;
        }

        .card{
            background:white;
            width:260px;
            display:inline-block;
            margin:15px;
            border-radius:15px;
            overflow:hidden;
            box-shadow:0px 0px 10px rgba(0,0,0,0.2);
        }

        .card img{
            width:100%;
            height:180px;
        }

        .card h2{
            text-align:center;
        }

        .price{
            text-align:center;
            color:green;
            font-size:22px;
            font-weight:bold;
        }

        .btn{
            display:block;
            background:#ff3d00;
            color:white;
            text-align:center;
            padding:12px;
            text-decoration:none;
        }

        </style>

        </head>

        <body>

        <div class='navbar'>

        <span>Welcome """ + username + """</span>

        <span>

        <a href='/cart/""" + userId + """'
        style='color:white;text-decoration:none;'>Cart</a>

        |

        <a href='/profile/""" + userId + """'
        style='color:white;text-decoration:none;'>Profile</a>

        </span>

        </div>

        <div class='container'>

        """ +

        foodCard(userId,
                "Pizza",
                299,
                "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=600")

        +

        foodCard(userId,
                "Burger",
                199,
                "https://images.unsplash.com/photo-1550547660-d9450f859349?w=600")

        +

        foodCard(userId,
                "Biryani",
                349,
                "https://images.unsplash.com/photo-1604908176997-125f25cc6f3d?w=600")

        +

        foodCard(userId,
                "Pasta",
                249,
                "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=600")

        +

        """

        </div>

        </body>

        </html>
        """;
    }

    // ================= FOOD CARD =================

    public String foodCard(int userId, String food, int price, String image) {

        return """
        <div class='card'>

        <img src='""" + image + """'>

        <h2>""" + food + """</h2>

        <div class='price'>₹""" + price + """</div>

        <a class='btn'
        href='/add-cart?userId=""" + userId + "&food=" + food + "&price=" + price + """'>
        Add To Cart
        </a>

        </div>
        """;
    }

    // ================= ADD TO CART =================

    @GetMapping("/add-cart")
    public String addCart(
            @RequestParam int userId,
            @RequestParam String food,
            @RequestParam int price) {

        try {

            Connection con = DriverManager.getConnection(url, dbUser, dbPass);

            String sql = "INSERT INTO cart(user_id,food_name,price,quantity) VALUES(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setString(2, food);
            ps.setInt(3, price);
            ps.setInt(4, 1);

            ps.executeUpdate();

            con.close();

            return """
            <h1 style='text-align:center;color:green;margin-top:100px;'>
            Item Added To Cart
            </h1>

            <center>

            <a href='/cart/""" + userId + """'>
            Go To Cart
            </a>

            </center>
            """;

        } catch (Exception e) {

            return "<h1>Cart Error : " + e.getMessage() + "</h1>";
        }
    }

    // ================= VIEW CART =================

    @GetMapping("/cart/{userId}")
    public String viewCart(@PathVariable int userId) {

        try {

            Connection con = DriverManager.getConnection(url, dbUser, dbPass);

            String sql = "SELECT * FROM cart WHERE user_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            String data = "";

            int total = 0;

            while (rs.next()) {

                total += rs.getInt("price");

                data += """
                <div style='background:white;
                padding:20px;
                margin:20px;
                border-radius:10px;'>

                <h2>""" + rs.getString("food_name") + """</h2>

                <h3>₹""" + rs.getInt("price") + """</h3>

                </div>
                """;
            }

            return """
            <html>

            <body style='font-family:Arial;
            background:#f5f5f5;
            padding:30px;'>

            <h1>Your Cart</h1>

            """ + data + """

            <h2>Total : ₹""" + total + """</h2>

            <a href='/payment/""" + userId + "/" + total + """'
            style='background:green;
            color:white;
            padding:15px;
            text-decoration:none;
            border-radius:10px;'>

            Proceed Payment

            </a>

            </body>

            </html>
            """;

        } catch (Exception e) {

            return "Cart Error : " + e.getMessage();
        }
    }

    // ================= PAYMENT =================

    @GetMapping("/payment/{userId}/{total}")
    public String payment(
            @PathVariable int userId,
            @PathVariable int total) {

        return """
        <html>

        <body style='font-family:Arial;
        text-align:center;
        padding-top:100px;
        background:#f5f5f5;'>

        <h1>Payment Page</h1>

        <h2>Total Amount : ₹""" + total + """</h2>

        <br>

        <button style='padding:15px 30px;
        background:green;
        color:white;
        border:none;
        border-radius:10px;
        font-size:20px;'>

        Pay Now

        </button>

        <br><br>

        <h3>UPI / Card / Net Banking</h3>

        </body>

        </html>
        """;
    }

    // ================= PROFILE =================

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable int id) {

        try {

            Connection con = DriverManager.getConnection(url, dbUser, dbPass);

            String sql = "SELECT * FROM users WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return """
                <html>

                <body style='font-family:Arial;
                background:#f5f5f5;
                padding:50px;'>

                <div style='background:white;
                padding:40px;
                border-radius:20px;
                width:400px;
                margin:auto;'>

                <h1>User Profile</h1>

                <h3>Name : """ + rs.getString("username") + """</h3>

                <h3>Email : """ + rs.getString("email") + """</h3>

                <h3>Mobile : """ + rs.getString("mobile") + """</h3>

                <h3>Address : """ + rs.getString("address") + """</h3>

                <br>

                <a href='/'
                style='background:red;
                color:white;
                padding:10px 20px;
                text-decoration:none;
                border-radius:10px;'>

                Logout

                </a>

                </div>

                </body>

                </html>
                """;
            }

            return "User Not Found";

        } catch (Exception e) {

            return "Profile Error : " + e.getMessage();
        }
    }
}
