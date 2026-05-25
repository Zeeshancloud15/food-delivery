package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {

        return """
        <html>

        <head>

            <title>Zeeshan Food Delivery</title>

            <style>

                body{
                    margin:0;
                    padding:0;
                    font-family:Arial;
                    background:#f5f5f5;
                }

                .navbar{
                    background:#ff5722;
                    color:white;
                    padding:20px;
                    text-align:center;
                    font-size:32px;
                    font-weight:bold;
                }

                .banner{
                    text-align:center;
                    background:white;
                    padding:40px;
                }

                .banner h1{
                    color:#ff5722;
                }

                .foods{
                    display:flex;
                    flex-wrap:wrap;
                    justify-content:center;
                    padding:20px;
                }

                .card{
                    background:white;
                    width:260px;
                    margin:15px;
                    border-radius:15px;
                    overflow:hidden;
                    box-shadow:0px 0px 10px rgba(0,0,0,0.2);
                }

                .card img{
                    width:100%;
                    height:200px;
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
                    background:#ff5722;
                    color:white;
                    text-align:center;
                    padding:12px;
                    text-decoration:none;
                    margin:15px;
                    border-radius:10px;
                }

                .footer{
                    background:#222;
                    color:white;
                    text-align:center;
                    padding:30px;
                    margin-top:40px;
                }

            </style>

        </head>

        <body>

            <div class="navbar">

                Zeeshan Food Delivery App

            </div>

            <div class="banner">

                <h1>Welcome To zeeshan Food Delivery App</h1>

                <h3> “Delicious Food, One Click Away!” </h3>

                <p>Phone : 7780369370</p>

                <p>Email : zeeshancloud15@gmail.com</p>

            </div>

            <div class="foods">

                <div class="card">

                    <img src="https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=600">

                    <h2>Pizza</h2>

                    <div class="price">₹299</div>

                    <a class="btn" href="#">Add To Cart</a>

                </div>

                <div class="card">

                    <img src="https://images.unsplash.com/photo-1550547660-d9450f859349?w=600">

                    <h2>Burger</h2>

                    <div class="price">₹199</div>

                    <a class="btn" href="#">Add To Cart</a>

                </div>

                <div class="card">

                    <img src="https://images.unsplash.com/photo-1604908176997-125f25cc6f3d?w=600">

                    <h2>Biryani</h2>

                    <div class="price">₹349</div>

                    <a class="btn" href="#">Add To Cart</a>

                </div>

                <div class="card">

                    <img src="https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=600">

                    <h2>Pasta</h2>

                    <div class="price">₹249</div>

                    <a class="btn" href="#">Add To Cart</a>

                </div>

            </div>

            <div class="footer">

                <h2>Zeeshan Cloud Tech</h2>

                <p>AWS | Docker | Kubernetes | DevOps</p>

                <p>Contact : 7780369370</p>

                <p>Email : zeeshancloud15@gmail.com</p>

            </div>

        </body>

        </html>
        """;
    }
}
