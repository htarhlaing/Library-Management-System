package com.example.oraclelibrary;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * This class will illustrate how to handle return book data with Oracle database, Find relevant borrowing records
 * through the Oracle's borrow_return table and book table, Update the return date and book status in the Oracle's borrow_return
 * table and book table, Save the overdue days and fine amount in the Oracle's fine table
 *
 * @author Gusto Htar Htar Hlaing
 */
@Stateless(name = "ReturnBookEJB")
public class ReturnBookBean {
    @EJB
    OracleClientProviderBean oracleClientProviderBean;

    public ReturnBookBean() {
    }

    /**
     *
     * This method will use the user's input book id and student id to check whether there is a relevant record of borrowing books,
     * whether the return status of the book  and then return the relevant borrow book record
     *
     * @param BookId the book id entered by the user to return the book
     * @param StudentId the book id entered by the user to return the book
     * @return return borrow book information from the Oracle's borrow_return table and book table to ServletBorrowBook.java
     * @throws SQLException
     */
    public ReturnBookModel FindBorrowBook(int BookId, int StudentId) throws SQLException {
        Connection connection = oracleClientProviderBean.getOracleClient();

        // find the corresponding borrowing record from the Oracle's borrow_return table with user's input student id and book id
        PreparedStatement pstmtCheck = connection.prepareStatement("select * from borrow_return where book_id =? and student_id = ?");
        pstmtCheck.setInt(1, BookId);
        pstmtCheck.setInt(2, StudentId);
        ResultSet rsCheck = pstmtCheck.executeQuery();

        // find the corresponding book information from the Oracle's book table with user's input book id
        PreparedStatement pstmtBook = connection.prepareStatement("select * from book where book_id=?");
        pstmtBook.setInt(1, BookId);
        ResultSet rsBook = pstmtBook.executeQuery();

        ReturnBookModel returnBookModel = null;
        int found = 0; // check for book borrowing and book information exist or not,0 indicates no data found
        while (rsCheck.next() && rsBook.next()) {
            found = 1; // 1 indicates that data found
            returnBookModel = new ReturnBookModel();
            Integer book_status = rsBook.getInt("book_status");

            /*
             *   check the book status from book table in Oracle, 1 indicates that the book has been returned ,
             *    0 indicates that the book has not been returned
             * */
            if (book_status == 0) {
                returnBookModel.setBookid(rsCheck.getInt("book_id"));
                returnBookModel.setStudentid(rsCheck.getInt("student_id"));
                returnBookModel.setBooktitle(rsBook.getString("book_title"));

                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

                java.sql.Date dbSqlDate = rsCheck.getDate("borrow_date");
                java.util.Date udate = new java.util.Date(dbSqlDate.getTime());
                returnBookModel.setBorrowDate(udate);

                dbSqlDate = rsCheck.getDate("due_date");
                udate = new java.util.Date(dbSqlDate.getTime());
                returnBookModel.setDueDate(udate);

            }
            else if (book_status == 1) {
                returnBookModel = new ReturnBookModel();
                returnBookModel.setBookid(-1);
            }
        }
        if (found == 0) {
            returnBookModel = new ReturnBookModel();
            returnBookModel.setBookid(-2);
        }

        return returnBookModel;

    }

    /**
     *
     * This method will update the borrow book's return date in the Oracle's borrow_return table and
     * book status in the Oracle's book table
     * @param BookId the associated book id entered by the user to return book
     * @param StudentId the associated student id entered by the user to return book
     * @param returnDate date of the day the user returns the book
     * @throws SQLException
     */
    public void ReturnDateBook(int BookId, int StudentId, Date returnDate) throws SQLException {
        Connection connection = oracleClientProviderBean.getOracleClient();

        //update return data in the Oraclels borrow_return table
        PreparedStatement pstmt = connection.prepareStatement("UPDATE borrow_return SET return_date=? WHERE student_id=? and book_id=?");
        pstmt.setDate(1, new java.sql.Date(returnDate.getTime()));
        pstmt.setInt(2, StudentId);
        pstmt.setInt(3, BookId);
        pstmt.execute();

        //// update book status in the Oracle's book table after returning book
        PreparedStatement pstmtBookStatus = connection.prepareStatement("UPDATE book SET book_status=? WHERE book_id=?");
        pstmtBookStatus.setInt(1, 1);
        pstmtBookStatus.setInt(2, BookId);
        pstmtBookStatus.execute();

    }


    /**
     *
     * This method will save borrow_id from the Oracle's borrow_return table and  data from
     * ServletReturnBook.java to the Oracle's fine table
     *
     * @param StudentId the student id entered by the user to return the book
     * @param BookId the book id entered by the user to return the book
     * @param OverDueDays the number of days the borrow book is overdue calculated from ServletReturnBook.java
     * @param FineAmount  the amount of fine should be paid calculated from ServletReturnBook.java
     *
     */
    public void GetFine(int StudentId, int BookId, int OverDueDays, int FineAmount) {
        Connection connection = oracleClientProviderBean.getOracleClient();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;

        try {
            // get borrow_id from the Oracle's borrow_return table with student id and book id
            int borrow_id = 0;
            pstmt1 = connection.prepareStatement("select borrow_id from borrow_return where book_id=? and student_id=?");
            pstmt1.setInt(1, BookId);
            pstmt1.setInt(2, StudentId);
            ResultSet rs = pstmt1.executeQuery();
            while (rs.next()) {
                borrow_id = rs.getInt("borrow_id");
            }

            // insert fine relevant data into the Oracle's fine table
            pstmt = connection.prepareStatement("insert into fine(student_id,book_id,borrow_id,overdue_days,fine_amount) values(?,?,?,?,?)");
            pstmt.setInt(1, StudentId);
            pstmt.setInt(2, BookId);
            pstmt.setInt(3, borrow_id);
            pstmt.setInt(4, OverDueDays);
            pstmt.setInt(5, FineAmount);
            pstmt.execute();


        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
