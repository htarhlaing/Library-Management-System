package com.example.oraclelibrary;

/**
 * This model will use to determine how book data is stored and retrieved
 *
 * @author Gusto Htar Htar Hlaing
 */
public class BorrowBookModel {
    int bookId;
    String bookTitle;
    String bookAuthor;
    String bookCategory;
    int pubYear;
    int bookStatus;


    public int getBookid() {
        return bookId;
    }

    public void setBookid(int bookId) {
        this.bookId = bookId;
    }

    public String getBooktitle() {
        return bookTitle;
    }

    public void setBooktitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookauthor() {
        return bookAuthor;
    }

    public void setBookauthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookcategory() {
        return bookCategory;
    }

    public void setBookcategory(String bookCategory) {this.bookCategory = bookCategory;}

    public int getPubyear() {return pubYear;}

    public void setPubyear(int pubYear) {this.pubYear = pubYear;}

    public int getBookstatus() {return bookStatus;}

    public void setBookstatus(int bookStatus) {this.bookStatus = bookStatus;}
    
    public BorrowBookModel() {

    }

    public BorrowBookModel(int bookId, String bookTitle, String bookAuthor, String bookCategory, int pubYear, int bookStatus) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookCategory = bookCategory;
        this.pubYear = pubYear;
        this.bookStatus = bookStatus;

    }
}
