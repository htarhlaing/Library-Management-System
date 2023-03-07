package com.example.oraclelibrary;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 *
 * This class will illustrate how to query the relevant total fine information based on different date month and student id
 * entered by the user and then forward the corresponding result of student,book and fine information to the TotalFineReport.jsp
 *
 * @author Gusto Htar Htar Hlaing
 */
@WebServlet(name = "ServletTotalFine", value = "/ServletTotalFine")
public class ServletTotalFine extends HttpServlet {
    @EJB
    TotalFineBean totalFineBean;

    /**
     *
     * This method will request the user's input student id and return date month and then convert the input's month to match the date format
     * in the Oracle's database for finding the relevant fine record, book and student information to TotalFineReport.jsp
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int student_id = Integer.parseInt(request.getParameter("student_id"));
        String borrow_month = request.getParameter("getMon");

        // assign different dates according to different months
        String  borrow_StartStr = "";
        String  borrow_EndStr = " ";
        if(borrow_month.equals("FEB")) {
            borrow_StartStr = "01"+borrow_month + "-22";
            borrow_EndStr = "28"+borrow_month+ "-22";
        }
        else if(borrow_month.equals("JAN") ||
                borrow_month.equals("MAR") ||
                borrow_month.equals("MAY") ||
                borrow_month.equals("JUL") ||
                borrow_month.equals("AUG") ||
                borrow_month.equals("OCT") ||
                borrow_month.equals("DEC")
        ) {
            borrow_StartStr = "01"+borrow_month + "-22";
            borrow_EndStr = "31"+borrow_month + "-22";
        }
        else{
            borrow_StartStr = "01"+borrow_month + "-22";
            borrow_EndStr = "30"+borrow_month + "-22";
        }

        // query the fine record, book and student information through  user's input
        List<ReturnBookModel> totalFine_list =  totalFineBean.FindTotalFine(student_id,borrow_StartStr,borrow_EndStr);
        request.setAttribute("totalFine_list",totalFine_list);
        request.getRequestDispatcher("TotalFineReport.jsp").forward(request, response);



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
