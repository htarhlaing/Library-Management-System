<%@ page import="com.example.oraclelibrary.ReturnBookModel" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 8/23/2022
  Time: 4:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">


    <title>Total Fine Report</title>
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
                    <li><a href="ReturnBook.jsp">Return Book</a></li>
                    <li ><a href="BookLoanReport.jsp">Book Loan History</a></li>
                    <li class="active"><a href="totalFine.jsp">Total Fine</a></li>

                </ul>
            </div>

        </div>
    </div>


</header>
<form action="ServletTotalFine" method="get">
    <section class="slider_section">
        <div class="sub_section historyH">
            <ul>
                <li><label >
                    Student ID:</label>
                    <input type="number" value="student_id" name="student_id" required>
                </li>
                <br>
                <li>
                    <label >
                        Choose Month:</label>

                    <select id='getMon' name='getMon'>
                        <option selected value='JAN'>Janaury</option>
                        <option value='FEB'>February</option>
                        <option value='MAR'>March</option>
                        <option value='APR'>April</option>
                        <option value='MAY'>May</option>
                        <option value='JUN'>June</option>
                        <option value='JUL'>July</option>
                        <option value='AUG'>August</option>
                        <option value='SEP'>September</option>
                        <option value='OCT'>October</option>
                        <option value='NOV'>November</option>
                        <option value='DEC'>December</option>
                    </select>
                </li><br>
                <li>
                    <input type="submit" value="Total Fine payment" name="action" class="find_btn">
                </li>

            </ul>

        </div>

    </section>
</form>
<br>
<%
    ArrayList<ReturnBookModel> totalFine_list = new ArrayList<ReturnBookModel>();
    totalFine_list = (ArrayList<ReturnBookModel>) request.getAttribute("totalFine_list");
%>

<table class="booksHead">
    <thead>
    <tr>
        <th>Student ID</th>
        <th>Student Name</th>
        <th>Student ClassYear</th>
        <th>Book ID</th>
        <th>Book Name</th>
        <th>Fine amount</th>

    </tr>
    </thead>
    <tbody>
    <%
        if(totalFine_list != null)
        {
            int sum=0;
            for(int i = 0; i <totalFine_list.size(); i++) {
                ReturnBookModel returnBookModel = totalFine_list.get(i);
                sum+=returnBookModel.getFineAmount();
    %>


    <tr>
        <td><%= returnBookModel.getStudentid()%></td>
        <td><%= returnBookModel.getStudentname()%></td>
        <td><%= returnBookModel.getStudentClassyear()%></td>
        <td><%= returnBookModel.getBookid() %></td>
        <td><%= returnBookModel.getBooktitle()%></td>
        <td><%=returnBookModel.getFineAmount()%></td>

    </tr>
        <%
         }
        %>
    <table class="booksHead">
        <tr>
            <td>Total Fine</td>
            <td><%= sum%></td>
        </tr>
    </table>
        <%
        }
    %>
</body>

</html>
