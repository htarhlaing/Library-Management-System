<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 8/19/2022
  Time: 7:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Book Rental</title>
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
                    <li class="active"><a href="ServletBorrowBook">Burrow Book</a></li>
                    <li><a href="ReturnBook.jsp">Return Book</a></li>
                    <li><a href="BookLoanReport.jsp">Book Loan History</a></li>
                    <li><a href="TotalFineReport.jsp">Total Fine</a></li>
                </ul>
            </div>
        </div>
    </div>
</header>
<br>
<h3 class="btext">${message}</h3>
<form action="ServletBorrowBook" method="POST">
    <section class="slider_section">
        <div class="sub_section">
            <ul>
                <li><label>
                    Student ID:</label>
                    <input type="text" name="student_id" required>
                </li>
                <br>
                <li>
                    <label>
                        Book ID:</label>
                    <input type="text" name="book_id" required>

                </li>
                <br>
                <li>
                    <label>
                        Borrow Date:</label>
                    <input type="text" value="<%= new java.text.SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date()) %>"  name="borrow_date" required>

                </li>
                <br>
                <li>
                    <label>
                        Due Date:</label>
                    <input type="text" name="due_date" id="datepicker" required>
                </li>
                <li>
                    <input type="submit" value="Borrow" class="borrow_btn" required>
                </li>
                <br>
            </ul>
        </div>
        <br>
        <table class="booksHead">
            <tr>
                <th>Book_Id</th>
                <th>Book_Title</th>
                <th>Book_Author</th>
                <th>Book_Category</th>
                <th>Publication_Year</th>
            </tr>

            <c:forEach var="bookList" items="${requestScope.book_list}">
                <tr>
                    <td> ${bookList.getBookid()}</td>
                    <td>${bookList.getBooktitle()}</td>
                    <td>${bookList.getBookauthor()}</td>
                    <td>${bookList.getBookcategory()}</td>
                    <td>${bookList.getPubyear()}</td>
                </tr>
            </c:forEach>
        </table>
    </section>
</form>
</body>
</html>
