<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <title>Pay Fine</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
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
                </li>

                <br>
                <li>
                    <label class="payLabel">
                        Book ID:</label>
                    <h3 class="showData" id="showData" ><%= request.getAttribute("book_id")==null?"":request.getAttribute("book_id") %></h3>
                    <input name="book_id" value='<%= request.getAttribute("book_id")==null?"":request.getAttribute("book_id") %>' hidden>

                </li><br>
                <li>
                    <label class="payLabel">
                        Book Name: &nbsp;</label>
                            <h3 class="showData"><%= request.getAttribute("book_title")==null?"":request.getAttribute("book_title") %></h3>

                </li><br>
                <li>
                    <label class="payLabel">
                        Overdue_days: &nbsp;</label>
                    <h3 class="showData"><%= request.getAttribute("overdue_days")==null?"":request.getAttribute("overdue_days") %></h3>
                    <input name="overdue_days" value='<%= request.getAttribute("overdue_days")==null?"":request.getAttribute("overdue_days") %>' hidden>
                    <input name="return_date" value='<%= request.getAttribute("return_date")==null?"":request.getAttribute("return_date") %>' hidden>

                </li><br>
                <li>
                    <label class="payLabel">
                        Fine_amount:</label>
                    <h3 class="showData"><%= request.getAttribute("fine_amount")==null?"":request.getAttribute("fine_amount") %></h3>
                    <input name="fine_amount" value='<%= request.getAttribute("fine_amount")==null?"":request.getAttribute("fine_amount") %>' hidden>

                </li><br>

                <li>
                    <input type="submit" value="Pay" name="action" class="find_btn">
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
