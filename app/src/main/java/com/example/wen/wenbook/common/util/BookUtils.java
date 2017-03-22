package com.example.wen.wenbook.common.util;

import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.bean.DoubanBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/3/21.
 */

public class BookUtils {
    public static Book DoubanBook2Book(DoubanBook doubanBook){
        Book book_db = new Book();

        // author
        StringBuilder authorString = new StringBuilder();
        if (doubanBook.getAuthor() != null) {
            for (int i = 0; i < doubanBook.getAuthor().size(); i++) {
                if (i < doubanBook.getAuthor().size() - 1) {
                    authorString.append(doubanBook.getAuthor().get(i).toString());
                    authorString.append("、");
                } else {
                    authorString.append(doubanBook.getAuthor().get(i).toString());
                }
            }
        }
        book_db.setAuthor(authorString.toString());

        // translators
        StringBuilder translatorString = new StringBuilder();
        if (doubanBook.getTranslator() != null) {
            for (int i = 0; i < doubanBook.getTranslator().size(); i++) {
                if (i < doubanBook.getTranslator().size() - 1) {
                    translatorString.append(doubanBook.getTranslator().get(i).toString());
                    translatorString.append("、");
                } else {
                    translatorString.append(doubanBook.getTranslator().get(i).toString());
                }
            }
        }
        book_db.setTranslator(translatorString.toString());

        book_db.setAuthor_intro(doubanBook.getAuthor_intro());
        book_db.setImage(doubanBook.getImages().getLarge());
        book_db.setPages(doubanBook.getPages());
        book_db.setOrigin_title(doubanBook.getOrigin_title());
        book_db.setBinding(doubanBook.getBinding());
        book_db.setCatalog(doubanBook.getCatalog());
        book_db.setPrice(doubanBook.getPrice());
        book_db.setPubdate(doubanBook.getPubdate());
        book_db.setSubtitle(doubanBook.getSubtitle());
        book_db.setSummary(doubanBook.getSummary());
        book_db.setTitle(doubanBook.getTitle());
        book_db.setUrl(doubanBook.getUrl());
        book_db.setAlt(doubanBook.getAlt());
        book_db.setPublisher(doubanBook.getPublisher());
        book_db.setIsbn13(doubanBook.getIsbn13().isEmpty() ? doubanBook.getIsbn10() : doubanBook.getIsbn13());
        book_db.setAverage(doubanBook.getRating().getAverage());
        book_db.setFavourite(false);
        book_db.setNote("");
        book_db.setNote_date("");

        // tags
        if (doubanBook.getTags() != null) {
            if (doubanBook.getTags().size() >= 3) {
                book_db.setTag1(doubanBook.getTags().get(0).getName());
                book_db.setTag2(doubanBook.getTags().get(1).getName());
                book_db.setTag3(doubanBook.getTags().get(2).getName());
            } else {
                switch (doubanBook.getTags().size()) {
                    case 0:
                        break;
                    case 1:
                        book_db.setTag1(doubanBook.getTags().get(0).getName());
                        break;
                    case 2:
                        book_db.setTag1(doubanBook.getTags().get(0).getName());
                        book_db.setTag2(doubanBook.getTags().get(1).getName());
                        break;
                }
            }
        }
        return book_db;
    }
    
    public static List<Book> parseAll(List<DoubanBook> doubanBookList){
        List<Book> bookList= new ArrayList<>(doubanBookList.size());

        for (DoubanBook doubanBook:doubanBookList) {
            bookList.add(DoubanBook2Book(doubanBook));
        }
        return bookList;
    }
}
