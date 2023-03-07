package com.example.oraclelibrary;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * This class method will illustrate how to query the book loan record, book information and student information
 * of the relevant month in the oracle database when searching for the loan month with different ID
 *
 *  @author Gusto Htar Htar Hlaing
 */
@Stateless(name = "BookLoanHistoryEJB")
public class BookLoanHistoryBean {
    @EJB
    OracleClientProviderBean oracleClientProviderBean;

    public BookLoanHistoryBean() {
    }

    /**
     *
     * This method will query related book borrowing records in the Oracle's borrow_return table on different dates
     * with different student ID ,to get date from result book borrowing record, to get book information  through book id
     * from result book borrowing record and student information from user's input student id ,
     * and then add all the data into the bookLoan_list through ReturnBookModel
     *
     * @param student_id      user's input student_id for finding the book borrowing record
     * @param borrow_startStr the start date of the month after converting the user's input month
     * @param borrow_endStr   the end date of the month after converting the user's input month
     * @return return the book borrowing record, book and student information to ServletBookLoanHistory.java
     */
    public List<ReturnBookModel> FindLoanHistory(int student_id, String borrow_startStr, String borrow_endStr) {

        List<ReturnBookModel> bookLoan_list = new ArrayList<ReturnBookModel>();
        try {
            Connection connection = oracleClientProviderBean.getOracleClient();

            // query the book borrowing record from user's input student id and borrow date
            PreparedStatement pstmt = connection.prepareStatement("select * from borrow_return where student_id =? and " + " borrow_date between TO_DATE(?, 'DD-MON-YY') " + " AND TO_DATE(?, 'DD-MON-YY')");

            pstmt.setInt(1, student_id);
            pstmt.setString(2, borrow_startStr);
            pstmt.setString(3, borrow_endStr);
            ResultSet borrowRs = pstmt.executeQuery();
            while (borrowRs.next()) {
                String book_title = null;
                String student_name = null;
                Integer book_status = null;
                int book_id = borrowRs.getInt("book_id");

                // query book information through book id result from borrow_return table
                PreparedStatement bookpstmt = connection.prepareStatement("select * from book where book_id=?");
                bookpstmt.setInt(1, book_id);
                ResultSet bookRs = bookpstmt.executeQuery();
                while (bookRs.next()) {
                    book_title = bookRs.getString("book_title");
                    book_status = bookRs.getInt("book_status");
                    System.out.print("book status in book loan histroy bean "+book_status);
                }

                // query student information through student id result from user input
                PreparedStatement stupstmt = connection.prepareStatement("select * from student where student_id=?");
                stupstmt.setInt(1, student_id);
                ResultSet stuRs = stupstmt.executeQuery();
                while (stuRs.next()) {
                    student_name = stuRs.getString("stu_name");
                }

                java.sql.Date dbSqlDate = borrowRs.getDate("borrow_date");
                java.util.Date borrow_date = new java.util.Date(dbSqlDate.getTime());

                dbSqlDate = borrowRs.getDate("due_date");
                java.util.Date due_date = new java.util.Date(dbSqlDate.getTime());

                // check the return status of book to avoid displaying the wrong return date
                java.util.Date return_date = null;
                if (borrowRs.getDate("return_date") != null) {
                    dbSqlDate = borrowRs.getDate("return_date");
                    return_date = new java.util.Date(dbSqlDate.getTime());
                }
                else {
                    return_date = null;
                }


                bookLoan_list.add(new ReturnBookModel(book_id, student_id, book_title, student_name, borrow_date, due_date, return_date, book_status));
            }

        }
        catch (Exception ee) {

            ee.printStackTrace();
        }
        return bookLoan_list;


    }
}
