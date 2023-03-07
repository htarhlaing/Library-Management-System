package com.example.oraclelibrary;

import java.util.Date;

/**
 * This model will use to determine how book, student and fine data is stored and retrieved
 *
 * @author Gusto Htar Htar Hlaing
 */
public class ReturnBookModel {
    int bookid;
    String booktitle;
    int studentid;
    int bookstatus;
    String studentname;

    int studentClassyear;
    Date borrowDate = null;
    Date dueDate = null;
    Date returnDate = null;
    int overDueDays;
    int fineAmount;

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public int getBookstatus() {
        return bookstatus;
    }

    public void setBookstatus(int bookstatus) {
        this.bookstatus = bookstatus;
    }

    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }


    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public int getStudentClassyear() {
        return studentClassyear;
    }

    public void setStudentClassyear(int studentClassyear) {
        this.studentClassyear = studentClassyear;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getOverDueDays() {
        return overDueDays;
    }

    public void setOverDueDays(int overDueDays) {
        this.overDueDays = overDueDays;
    }

    public int getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(int fineAmount) {
        this.fineAmount = fineAmount;
    }

    public ReturnBookModel() {

    }

    public ReturnBookModel(int bookid, String booktitle, int studentid, Date borrowDate, Date dueDate, Date returnDate, int overDueDays, int fineAmount) {
        this.bookid = bookid;
        this.booktitle = booktitle;
        this.studentid = studentid;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.overDueDays = overDueDays;
        this.fineAmount = fineAmount;
    }

    // for BookLoanHistoryBean.java
    public ReturnBookModel(int book_id, int student_id, String book_title, String student_name, Date borrow_date, Date due_date, Date return_date, int book_status) {
        this.bookid = book_id;
        this.studentid = student_id;
        this.booktitle = book_title;
        this.studentname = student_name;
        this.borrowDate = borrow_date;
        this.dueDate = due_date;
        this.returnDate = return_date;
        this.bookstatus = book_status;
    }

    // for TotalFineBean.java
    public ReturnBookModel(int book_id, int student_id, String book_title, String student_name, int student_classYear, int fine_amount) {
        this.bookid = book_id;
        this.booktitle = book_title;
        this.studentid = student_id;
        this.studentname = student_name;
        this.studentClassyear = student_classYear;
        this.fineAmount = fine_amount;

    }


}
