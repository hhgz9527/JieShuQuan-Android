package com.thoughtworks.jieshuquan_android.service;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.thoughtworks.jieshuquan_android.model.Book;
import com.thoughtworks.jieshuquan_android.model.BookEntity;

import java.util.List;

/**
 * Created by leihuang on 6/10/15.
 */
public class BookService {
    private static String kBook_DouBanId = "bookDoubanId";
    private static String kBookEntity_Book = "doubanBook";
    private static String kBookEntity_User = "bookOwner";

    private static BookService instance;

    public static BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    public void addBookToLibrary(final Book abook, final Boolean availability, final SaveCallback addBookCallback) {

        final AVQuery<AVObject> query = new AVQuery<AVObject>("Book");
        query.whereEqualTo(kBook_DouBanId, abook.getBookDoubanId());
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {

                    if (avObjects.size() == 0) {
                        abook.saveInBackground(new SaveCallback() {
                            public void done(AVException e) {
                                if (e == null) {
                                    // save success
                                    AVQuery query1 = new AVQuery<AVObject>("Book");
                                    query.whereEqualTo(kBook_DouBanId, abook.getBookDoubanId());
                                    query.getFirstInBackground(new GetCallback<AVObject>() {
                                        @Override
                                        public void done(AVObject avObject, AVException e) {
                                            BookService.this.createBookEntityWithBook((Book) avObject, availability,addBookCallback);
                                        }
                                    });
                                    return;

                                } else {
                                    // save failure
                                    AVException exception = new AVException(-1, "SaveFailure");
                                    addBookCallback.done(exception);
                                    return;
                                }
                            }
                        });
                    }
                    AVQuery query1 = new AVQuery<AVObject>("Book");
                    query.whereEqualTo(kBook_DouBanId, abook.getBookDoubanId());
                    query.getFirstInBackground(new GetCallback<AVObject>() {
                        @Override
                        public void done(AVObject avObject, AVException e) {
                            BookService.this.createBookEntityWithBook((Book) avObject, availability, addBookCallback);
                        }
                    });
                    return;

                } else {
                    // find faulure
                    AVException exception = new AVException(-1, "searchFailure");
                    addBookCallback.done(exception);
                }
            }
        });
    }

    public void createBookEntityWithBook(final Book abook, final Boolean availability, final SaveCallback createBookEntitySaveCallback) {

        final AVQuery<AVObject> query = new AVQuery<AVObject>("BookEntity");
        query.whereEqualTo(kBookEntity_User, AVUser.getCurrentUser());
        query.whereEqualTo(kBookEntity_Book, abook);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list.size() == 0) {

                        BookEntity bookEntity = new BookEntity();
                        bookEntity.setBookAvailability(availability);
                        bookEntity.setBookName(abook.getBookName());
                        bookEntity.setBookImageHref(abook.getBookImageHref());
                        bookEntity.put(kBookEntity_User, AVUser.getCurrentUser());
                        bookEntity.put(kBookEntity_Book, abook);
                        bookEntity.saveInBackground(createBookEntitySaveCallback);
                    } else {
                        // had, not need to add
                        AVException exception = new AVException(-1, "AlreadyHave");
                        createBookEntitySaveCallback.done(exception);
                    }
                } else {
                    createBookEntitySaveCallback.done(e);
                }
            }
        });
    }
}

