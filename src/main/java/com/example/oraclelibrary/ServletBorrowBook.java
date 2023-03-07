package com.example.oraclelibrary;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * This class will illustrate how to request borrow book data from BorrowBook.jsp
 * and re-render the BorrowBookBean computed data to BorrowBook.jsp page
 *
 * @author Gusto Htar Htar Hlaing
 */
@WebServlet(name = "ServletBorrowBook", value = "/ServletBorrowBook")
public class ServletBorrowBook extends HttpServlet {
    @EJB
    BorrowBookBean borrowBookBean;


    /**
     *
     * This method will request book information from bookList method for automatically displayed list of books in the BorrowBook.jsp
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        bookList(request, response); // automatically display list of booka when the BorrowBook.jsp is opened
    }

    /**
     * This method will request the borrow book input data from BorrowBook.jsp then
     * checks the data situation  to determine whether it can be borrowed or not
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // request data form BorrowBook.jsp
            int book_id = Integer.parseInt(request.getParameter("book_id"));
            int student_id = Integer.parseInt(request.getParameter("student_id"));
            String bDate = request.getParameter("borrow_date");
            String dDate = request.getParameter("due_date");

            // turn the borrow date and due date to simple data format
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date borrow_date = null;
            Date due_date = null;
            borrow_date = sdf.parse(bDate);
            due_date = sdf.parse(dDate);

            // insert user's input data into BorrowBookBean's AddBorrowBook for check and save the loan book
            BorrowBookModel checkId = borrowBookBean.AddBorrowBook(student_id, book_id, borrow_date, due_date);
            if (checkId.getBookid() == -1) {
                request.setAttribute("message", " Book has been borrowed !");
                bookList(request, response);

            }
            else if (checkId.getBookid() == -2) {
                request.setAttribute("message", " Wrong Book id or Student id !");
                bookList(request, response);

            }
            else {
                request.setAttribute("message", "Borrow Success! ");
                bookList(request, response);

            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method will request a list of books from the Oracle database and
     * then forward the book data to BorrowBook.jsp to display existing books on the page
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void bookList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BorrowBookModel book = new BorrowBookModel();
        ArrayList<BorrowBookModel> books_list = borrowBookBean.getBookList();//get a list of book data from BorrowBookBean's method

        try {

            request.setAttribute("book_list", books_list);
            request.getRequestDispatcher("BorrowBook.jsp").forward(request, response);
        } finally {

        }


    }
}
