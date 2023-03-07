<%@ page import="com.example.oraclelibrary.ReturnBookModel" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 8/23/2022
  Time: 4:47 PM
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
    <title>Book History Report</title>
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
                    <li class="active"><a href="BookLoanReport.jsp">Book Loan History</a></li>
                    <li><a href="TotalFineReport.jsp">Total Fine</a></li>

                </ul>
            </div>

        </div>
    </div>


</header>
<form action="ServletBookLoanHistory" method="get">
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
                    <input type="submit" value="SearchLoanHistory" name="action" class="find_btn">
                </li>

            </ul>

        </div>

    </section>



</form>
<br>
<%
    ArrayList<ReturnBookModel> bookLoan_list = new ArrayList<ReturnBookModel>();
    bookLoan_list = (ArrayList<ReturnBookModel>) request.getAttribute("bookLoan_list");
%>

<table class="booksHead">
    <thead>
    <tr>
        <th>Student ID</th>
        <th>Student Name</th>
        <th>Book ID</th>
        <th>Book Name</th>
        <th>Borrow Date</th>
        <th>Due Date</th>
        <th>Return Date</th>
        <th>Book Status</th>
    </tr>
    </thead>
    <tbody>
    <%
        if(bookLoan_list != null)
        {
            for(int i = 0; i < bookLoan_list.size(); i++) {
                ReturnBookModel returnBookModel = bookLoan_list.get(i);
                String return_Date=returnBookModel.getReturnDate()==null?"no return yet":new SimpleDateFormat("MM/dd/yyyy").format(returnBookModel.getReturnDate());

    %>

    <tr>
        <td><%= returnBookModel.getStudentid()%></td>
        <td><%= returnBookModel.getStudentname()%></td>
        <td><%= returnBookModel.getBookid() %></td>
        <td><%= returnBookModel.getBooktitle()%></td>
        <td><%= new SimpleDateFormat("MM/dd/yyyy").format(returnBookModel.getBorrowDate()) %></td>
        <td><%= new SimpleDateFormat("MM/dd/yyyy").format(returnBookModel.getDueDate())%></td>
        <td><%= return_Date%></td>
        <td><%= returnBookModel.getBookstatus()==1?"AVAILABLE":"NOT AVAILABLE"%></td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>

</html>

