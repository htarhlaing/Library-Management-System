<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 8/19/2022
  Time: 10:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Book Return</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="css/style.css">
    <script>
        $(function () {
            $("#datepicker").datepicker();
        });
    </script>
</head>
<body>
<header>
    <div class="header">
        <div class="header_1">
            <div class="logo"><a href="index.jsp">
          <img src="images/logo.png" alt="" class="logo">
            </a></div>
        </div>
        <div class="header_2">
            <div class="menu_area">
                <ul class="menu-area-main">
                    <li><a href="ServletBorrowBook">Burrow Book</a></li>
                    <li class="active"><a href="ReturnBook.jsp">Return Book</a></li>
                    <li><a href="BookLoanReport.jsp">Book Loan History</a></li>
                    <li><a href="TotalFineReport.jsp">Total Fine</a></li>
                </ul>
            </div>

        </div>
    </div>


</header>
<br>
<h3 class="btext">${message}</h3>
<form action = "ServletReturnBook" method = "GET">
    <section class="slider_section">
        <div class="sub_section returnH">
            <ul>
                <li><label >
                    Student ID:</label>
                    <input type="text" value='<%= request.getAttribute("student_id")==null?"":request.getAttribute("student_id")%>' name="student_id" required>
                </li>
                <br>
                <li>
                    <label>
                        Book ID:</label>
                    <input type="text" value='<%= request.getAttribute("book_id")==null?"":request.getAttribute("book_id")%>' name="book_id" required>

                </li><br>

                <li>
                    <label >
                        Return Date:</label>
                    <input type="text" value='<%= new java.text.SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date()) %>' name="return_date" required>
                </li>
                <br>
                <li>
                    <input type="submit" value="Find" name="action" class="find_btn"  required>
                </li>
                <br>
            </ul>
        </div>
    </section>
</form>
</body>
</html>
