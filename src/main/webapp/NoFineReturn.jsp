<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 9/3/2022
  Time: 7:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">


    <title>Pay Fine</title>
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

<form action = "ServletReturnBook" method = "GET">
    <section class="slider_section">
        <div class="sub_section fineH">
            <ul>
                <li ><label class="payLabel" for="student_id">
                    Student ID:</label>
                    <h3 onsubmit="" id="student_id" class="showData"><%= request.getAttribute("student_id")==null?"":request.getAttribute("student_id") %></h3>
                    <input name="student_id" value='<%= request.getAttribute("student_id")==null?"":request.getAttribute("student_id") %>' hidden>
<%-- for send data to ServletReturnBook.java--%>
                </li><br>
                <li>
                    <label class="payLabel">
                        Book ID:</label>
                    <h3 class="showData" id="showData" ><%= request.getAttribute("book_id")==null?"":request.getAttribute("book_id") %></h3>
                    <input name="book_id" value='<%= request.getAttribute("book_id")==null?"":request.getAttribute("book_id") %>' hidden>
 <%--  for send data to ServletReturnBook.java--%>
                </li><br>
                <li>
                    <label class="payLabel">
                        Book Title: &nbsp;</label>
                            <h3 class="showData"><%= request.getAttribute("book_title")==null?"":request.getAttribute("book_title") %></h3>

                </li><br>
                <li>
                    <label>
                        Borrow Date:
                    </label>
                    <h3 class="showData"><%= request.getAttribute("borrow_date")==null?"":request.getAttribute("borrow_date") %></h3>
                </li><br>
                <li>
                    <label >
                        Due Date:</label>
                    <h3 class="showData"><%= request.getAttribute("due_date")==null?"":request.getAttribute("due_date") %></h3>
                </li><br>
                <li>
                    <label >
                        Return Date:</label>
                    <h3 class="showData"><%= request.getAttribute("return_date")==null?"":request.getAttribute("return_date") %></h3>
                    <input name="return_date" value='<%= request.getAttribute("return_date")==null?"":request.getAttribute("return_date") %>' hidden>
                    <%-- for send data to ServletReturnBook.java--%>
                </li><br>
                <li>
                    <input type="submit" name="action" class="return_btn" value="Return">
                </li>

            </ul>

        </div>

    </section>
</form>

<script>
    function showAlert()
    {
        var pay=${pay};

        if(pay=1)
        {
            alert("Your due date is expire, you need to pay fine first! ")

        }

    }

</script>
<script type="text/javascript"> window.onload = showAlert; </script>
</body>
</html>
