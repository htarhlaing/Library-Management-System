package com.example.oraclelibrary;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class method will illustrate how to query the book fine record, book information and student information
 * of the relevant month in the Oracle database when searching for the loan month with different ID
 *
 * @author Gusto Htar Htar Hlaing
 */
@Stateless(name = "TotalFineEJB")
public class TotalFineBean {
    @EJB
    OracleClientProviderBean oracleClientProviderBean;

    public TotalFineBean() {
    }

    /**
     *
     * This method will query related book borrowing records in the Oracle's borrow_return table on different dates
     * with different student ID, to get fine information through borrow_id from result book borrowing record, to
     * get book information through book id from result book borrowing record and student information
     * from user's input student id and then add all the data into the bookLoan_list through ReturnBookModel
     *
     * @param student_id user's input student_id for finding the book borrowing record
     * @param borrow_startStr the start date of the month after converting the user's input month
     * @param borrow_endStr the end date of the month after converting the user's input month
     * @return return the book fine record, book and student information to ServletTotalFine.java
     *
     */
    public List<ReturnBookModel> FindTotalFine(int student_id, String borrow_startStr, String borrow_endStr) {

        List<ReturnBookModel> fine_list = new ArrayList<ReturnBookModel>();
        try {

            Connection connection = oracleClientProviderBean.getOracleClient();

            // query the book borrowing record from user's input student id and return date
            PreparedStatement pstmt = connection.prepareStatement("select * from borrow_return where student_id =? and " +
                    " return_date between TO_DATE(?, 'DD-MON-YY') " +
                    " AND TO_DATE(?, 'DD-MON-YY')");
            pstmt.setInt(1, student_id);
            pstmt.setString(2, borrow_startStr);
            pstmt.setString(3, borrow_endStr);
            ResultSet borrowRs = pstmt.executeQuery();

            while (borrowRs.next()) {
                String book_title = null;
                String student_name = null;
                Integer fine_amount = 0;
                Integer student_classYear = 0;
                int borrow_id = borrowRs.getInt("borrow_id");

                //query fine information through borrow_id result from book borrowing record
                PreparedStatement finepstmt = connection.prepareStatement("select fine_amount from fine where borrow_id=?");
                finepstmt.setInt(1, borrow_id);
                ResultSet fineRs = finepstmt.executeQuery();
                while (fineRs.next()) {
                    fine_amount = fineRs.getInt("fine_amount");
                }

                // query book information through book id result from book borrowing record
                int book_id = borrowRs.getInt("book_id");
                PreparedStatement bookpstmt = connection.prepareStatement("select * from book where book_id=?");
                bookpstmt.setInt(1, book_id);
                ResultSet bookRs = bookpstmt.executeQuery();
                while (bookRs.next()) {
                    book_title = bookRs.getString("book_title");
                }

                // query student information through student id result from user input
                PreparedStatement studentpstmt = connection.prepareStatement("select * from student where student_id=?");
                studentpstmt.setInt(1, student_id);
                ResultSet stuRs = studentpstmt.executeQuery();
                while (stuRs.next()) {
                    student_name = stuRs.getString("stu_name");
                    student_classYear = stuRs.getInt("stu_classYear");

                }

                fine_list.add(new ReturnBookModel(book_id, student_id, book_title, student_name, student_classYear, fine_amount));

            }

        }
        catch (Exception ee) {

            ee.printStackTrace();
        }
        return fine_list;


    }
}
