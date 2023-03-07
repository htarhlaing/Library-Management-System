package com.example.oraclelibrary;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The class will illustrate how to handle the different instructions that are passed in, according to
 * the ReturnBook.jsp for returning the books and then re-render the ReturnBookBean computed data to appropriate jsp page
 * @author Gusto Htar Htar Hlaing
 */
@WebServlet(name = "ServletReturnBook", value = "/ServletReturnBook")
public class ServletReturnBook extends HttpServlet {
    @EJB
    ReturnBookBean returnBookBean;


    /**
     * This method will request the return book input data from ReturnBook.jsp and then operate the data
     * according to the action values requested from different jsp (ReturnBook.jsp, NoFineReturn.jsp, PayFineReturn.jsp )
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // when the action value requested from ReturnBook.jsp is "Find"
        if (action.equals("Find")) {

            try {
                // request the user's input book id, student id and return date, then convert return date to SimpleDateFormate
                int book_id = Integer.parseInt(request.getParameter("book_id"));
                int student_id = Integer.parseInt(request.getParameter("student_id"));

                String returnDateStr = request.getParameter("return_date");
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date return_date = null;
                return_date = sdf.parse(returnDateStr);

                // the book loan record is found by inserting the user's input data into ReturnBookBean's method FindBorrowBook
                ReturnBookModel returnBookModel = returnBookBean.FindBorrowBook(book_id, student_id);
                System.out.print("return book modell result "+ returnBookModel.getBookid());
                if (returnBookModel.getBookid() == -1) {

                    request.setAttribute("message", "Book has been returned!");
                    request.getRequestDispatcher("ReturnBook.jsp").forward(request, response);
                }
                else if (returnBookModel.getBookid() == -2) {

                    request.setAttribute("message", "No data found");
                    request.getRequestDispatcher("ReturnBook.jsp").forward(request, response);

                }
                else {

                    request.setAttribute("book_id", returnBookModel.getBookid());
                    request.setAttribute("student_id", returnBookModel.getStudentid());
                    request.setAttribute("book_title", returnBookModel.getBooktitle());
                    request.setAttribute("borrow_date", new SimpleDateFormat("MM/dd/yyyy").format(returnBookModel.getBorrowDate()));
                    request.setAttribute("due_date", new SimpleDateFormat("MM/dd/yyyy").format(returnBookModel.getDueDate()));
                    request.setAttribute("return_date", returnDateStr);

                    /*
                     * after finding the corresponding borrowing book record, the user's input return date and due_date from database are compared
                     * to determine whether the user's return date exceeds the deadline
                     * */
                    Date due_date = returnBookModel.getDueDate();
                    int comparison = return_date.compareTo(due_date);
                    if (comparison <= 0) {
                        // when return_date is less than due_date, the page will forward to NoFineReturn.jsp with user's input data
                        request.getRequestDispatcher("NoFineReturn.jsp").forward(request, response);
                    }
                    else {
                        /*
                         * when return_date is greater than due_date, the number of overdue days and fines will be calculated, and
                         * the resulting data and user's input data will be shipped to PayFineReturn.jsp and redirected to its page
                         *
                         * */
                        long diffInMillies = Math.abs(return_date.getTime() - due_date.getTime());
                        long diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                        int overdue_days = (int) diffDays;

                        request.setAttribute("book_title", returnBookModel.getBooktitle());
                        request.setAttribute("overdue_days", overdue_days);

                        int fine_amount = (overdue_days * 1000);
                        request.setAttribute("fine_amount", fine_amount);
                        request.setAttribute("pay", 1);
                        request.getRequestDispatcher("PayFineReturn.jsp").forward(request, response);
                    }

                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        // when the action value requested from NoFineReturn.jsp is "Return"
        if (action.equals("Return")) {
            try {
                // request the book id, student id and return date from NoFineReturn.jsp page, then convert return date to SimpleDateFormate
                int book_id = Integer.parseInt(request.getParameter("book_id"));
                int student_id = Integer.parseInt(request.getParameter("student_id"));

                String returnDateStr = request.getParameter("return_date");
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date return_date = null;
                return_date = sdf.parse(returnDateStr);

               /*
               * insert user input data into BorrowBookBean's ReturnDateBook method to record
               * the return_date into the Oracle's borrow_return table and update book status in the Oracle's book table
               * */
                returnBookBean.ReturnDateBook(book_id, student_id, return_date);

                request.setAttribute("message", "Book Return is finished!");
                request.getRequestDispatcher("ReturnBook.jsp").forward(request, response);


            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        // when the action value requested from PayFineReturn.jsp is "Pay"
        if (action.equals("Pay")) {
            try {
                // request the book id, student id and return date from PayFineReturn.jsp page, then convert return date to SimpleDateFormate
                int book_id = Integer.parseInt(request.getParameter("book_id"));
                int student_id = Integer.parseInt(request.getParameter("student_id"));
                int overdue_days = Integer.parseInt(request.getParameter("overdue_days"));
                int fine_amount = Integer.parseInt(request.getParameter("fine_amount"));

                String returnDateStr = request.getParameter("return_date");
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date return_date = null;
                return_date = sdf.parse(returnDateStr);

                //insert relevant fine data into ReturnBookBean's GetFine method to save data into the Oracle's fine table
                returnBookBean.GetFine( student_id, book_id,overdue_days, fine_amount);

                /*
                 * insert user input data into BorrowBookBean's ReturnDateBook method to record
                 * the return_date into the Oracle's borrow_return table and update book status in the Oracle's book table
                 * */
                returnBookBean.ReturnDateBook(book_id, student_id, return_date);

                request.setAttribute("message", "return successfully!");
                request.getRequestDispatcher("ReturnBook.jsp").forward(request, response);

            }
            catch (Exception e) {
                e.printStackTrace();

            }

        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}








