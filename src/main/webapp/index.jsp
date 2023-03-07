<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gusto Library</title>
    <link rel="stylesheet" href="css/style.css">
    <style>


        .library {
            position: absolute;
            width: 100%;
            height: 400px;
            background-color: rgba(87, 177, 60, 0.5);
            overflow: hidden;
            text-align: center;
            top: 50px;
        }

        h1 {

            position: relative;
            top: 50px;
            font-weight:700;
            font-style: italic;
            font-size: 50px;
            color: rgb(248, 252, 248);

        }

        img {
            position: absolute;
            width: 150px;
            height: 150px;
            top: 10px;
            left: 150px;
            border-radius: 50%;


        }

        a {
            position: relative;
            top: 200px;
            background-color: rgb(163, 226, 158);
            color: white;
            padding: 1em 1.5em;
            text-decoration: none;
            font-size: 35px;
            text-transform: uppercase;
        }
    </style>
</head>

<body>


<div class="library">
    <img src="images/logo.png" alt="">
    <h1> Welcome to GUSTO Library </h1>

    <a href="ServletBorrowBook"> Home </a>


</div>
</body>


</html>