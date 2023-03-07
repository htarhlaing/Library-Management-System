package com.example.oraclelibrary;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class will illustrate how to compare, judge and save the borrow book data
 * that the user has entered with the Book and Student tables in the Oracle database
 *
 * @author Gusto Htar Htar Hlaing
 */
@Stateless(name = "BorrowBookEJB")
public class BorrowBookBean {
    @EJB
    OracleClientProviderBean oracleClientProviderBean;

    public BorrowBookBean() {
    }

    /**
     * This method will perform a series of operation on borrowing books, including comparing the user input
     * book id and student id with the data in the Oracle's book and student tables, determining the
     * book should be borrowed, whether the states of the book should be updated, and whether the borrowing
     * record should be saved in the borrow_return table
     *
     * @param StudentId  the student id entered by the user from BorrowBook.jsp
     * @param BookId     the book id entered by the user from BorrowBook.jsp
     * @param BorrowDate the borrow date selected by the user from BorrowBook.jsp
     * @param DueDate    the due date selected by the user from BorrowBook.jsp
     * @return return borrowBookmodel to ServletBorrowBook.java to judge the success of book lending
     * @throws SQLException
     */
    public BorrowBookModel AddBorrowBook(int StudentId, int BookId, Date BorrowDate, Date DueDate) throws SQLException {

        BorrowBookModel borrowBookModel = null;
        Connection connection = oracleClientProviderBean.getOracleClient();

        // for matching book id and student id entered by the user with the data in the Oracle's book and student table
        PreparedStatement pstmtBookId = connection.prepareStatement("SELECT  * from book where book_id=?");
        pstmtBookId.setInt(1, BookId);
        ResultSet rsBookId = pstmtBookId.executeQuery();

        PreparedStatement pstmtStudentId = connection.prepareStatement("SELECT * from student where student_id=?");
        pstmtStudentId.setInt(1, StudentId);
        ResultSet rsStudentId = pstmtStudentId.executeQuery();

        int found = 0; // check for student and book information exist or not,0 indicates no data found
        while (rsBookId.next() && rsStudentId.next()) {
            found = 1;//  1 indicates data found
            Integer book_status = rsBookId.getInt("book_status");
            borrowBookModel = new BorrowBookModel();
             /*
               check the book status from book table in Oracle database, 1 indicates that the book has not been borrowed,
               0 indicates that the book has been borrowed
              */
            if (book_status == 1) {
                PreparedStatement pstmt = connection.prepareStatement("" +
                        "insert into borrow_return(student_id,book_id,borrow_date,due_date) values (?,?,?,?)");
                pstmt.setInt(1, StudentId);
                pstmt.setInt(2, BookId);
                pstmt.setDate(3, new java.sql.Date(BorrowDate.getTime()));
                pstmt.setDate(4, new java.sql.Date(DueDate.getTime()));
                pstmt.execute();

                pstmtBookId = connection.prepareStatement("UPDATE book SET book_status=? WHERE book_id=?");// update the book status in Oracle's book table
                pstmtBookId.setInt(1, 0);
                pstmtBookId.setInt(2, BookId);
                pstmtBookId.execute();


            }
            else if (book_status == 0) {
                borrowBookModel.setBookid(-1); // the book has been borrowed
                return borrowBookModel;
            }

        }
        if (found == 0) {
            borrowBookModel = new BorrowBookModel();
            borrowBookModel.setBookid(-2);
            return borrowBookModel;

        }

        return borrowBookModel;
    }

    /**
     *
     *This method will look up the book data information in the Oracle's book table for display in BorrowBook.jsp page
     * @return return the existing book data information in the Oracle's book table to ServletBorrowBook.java
     */
    public ArrayList<BorrowBookModel> getBookList() {

        ArrayList book_list = new ArrayList();
        BorrowBookModel borrowBookModel;

        try {

            Connection con = oracleClientProviderBean.getOracleClient();
            borrowBookModel = null;
            PreparedStatement ptmt = con.prepareStatement("Select * from book");
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                borrowBookModel = new BorrowBookModel();
                borrowBookModel.setBookid(rs.getInt("book_id"));
                borrowBookModel.setBooktitle(rs.getString("book_title"));
                borrowBookModel.setBookauthor(rs.getString("book_author"));
                borrowBookModel.setBookcategory(rs.getString("book_category"));
                borrowBookModel.setPubyear(rs.getInt("publication_year"));


                book_list.add(borrowBookModel);
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return book_list;
    }


}
