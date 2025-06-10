<%-- 
    Document   : contact-us
    Created on : Jun 10, 2025, 11:04:32 AM
    Author     : namp0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Home | E-Shopper</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->       
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-57-precomposed.png">
</head><!--/head-->
<body>
        <div id="contact-page" class="container">
            <div class="bg">
                <div class="row">    		
                    <div class="col-sm-12">    			   			
                        <h2 class="title text-center">Contact <strong>Us</strong></h2>    			    				    				
                        <div id="gmap" class="contact-map">
                        </div>
                    </div>			 		
                </div>    	
                <div class="row">  	
                    <div class="col-sm-8">
                        <div class="contact-form">
                            <h2 class="title text-center">Get In Touch</h2>
                            <div class="status alert alert-success" style="display: none"></div>
                            <form action="contact" method="post" id="main-contact-form" class="contact-form row" name="contact-form" >
                                <div class="form-group col-md-6">
                                    <input type="text" name="name" class="form-control" required="required" placeholder="Name">
                                </div>
                                <div class="form-group col-md-6">
                                    <input type="email" name="email" class="form-control" required="required" placeholder="Email">
                                </div>
                                <div class="form-group col-md-12">
                                    <input type="text" name="subject" class="form-control" required="required" placeholder="Subject">
                                </div>
                                <div class="form-group col-md-12">
                                    <textarea name="message" id="message" required="required" class="form-control" rows="8" placeholder="Your Message Here"></textarea>
                                </div>                        
                                <div class="form-group col-md-12">
                                    <input type="submit" value="Submit" name="submit" class="btn btn-primary pull-right">
                                </div>
                            </form>
                            <div id="statusMessage" style="margin-top:10px;"></div>

                            <script>
                                document.getElementById("main-contact-form").addEventListener("submit", function (e) {
                                    e.preventDefault();

                                    const form = this;
                                    const data = new URLSearchParams(new FormData(form));
                                    const statusDiv = document.getElementById("statusMessage");
                                            statusDiv.innerHTML = ""; 

                                    fetch("/SWP391_G1_1906/contact", {
                                        method: "POST",
                                        headers: {
                                            "Content-Type": "application/x-www-form-urlencoded"
                                        },
                                        body: data
                                    })
                                            .then(res => res.text())
                                            .then(result => {
                                                const statusDiv = document.getElementById("statusMessage");
                                                if (result.trim() === "success") {
                                                    statusDiv.innerHTML = "<span style='color:green;'>✔ Gửi email thành công!</span>";
                                                } else {
                                                    statusDiv.innerHTML = "<span style='color:red;'>❌ Gửi email thất bại. Vui lòng thử lại.</span>";
                                                }
                                            })
                                            .catch(err => {
                                                document.getElementById("statusMessage").innerHTML = "<span style='color:red;'>❌ Lỗi hệ thống.</span>";
                                                console.error(err);
                                            });
                                });
                            </script>
                        </div>
                    </div>

                    <div class="col-sm-4">
                        <div class="contact-info">
                            <h2 class="title text-center">Contact Info</h2>
                            <address>
                                <p>E-Shopper Inc.</p>
                                <p>240, Thon 1,Thach That, Hoa Lac,TP Ha Noi</p>
                                <p>Viet Nam</p>
                                <p>Mobile: +0392 96 8548</p>
                                <p>Fax: 1-714-252-0026</p>
                                <p>Email: namp04464@gmail.com</p>
                            </address>
                            <div class="social-networks">
                                <h2 class="title text-center">Social Networking</h2>
                                <ul>
                                    <li>
                                        <a href="https://www.facebook.com/profile.php?id=100092576516491"><i class="fa fa-facebook"></i></a>
                                    </li>
                                    <li>
                                        <a href="https://www.facebook.com/profile.php?id=100092576516491"><i class="fa fa-twitter"></i></a>
                                    </li>
                                    <li>
                                        <a href="#"><i class="fa fa-google-plus"></i></a>
                                    </li>
                                    <li>
                                        <a href="https://www.youtube.com/c/Tr%E1%BB%B1cTi%E1%BA%BFpGameVN"><i class="fa fa-youtube"></i></a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>    			
                </div>  
            </div>	
        </div>
	  
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/price-range.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
