package com.thoughtworks.jieshuquan_android.service;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.thoughtworks.jieshuquan_android.model.Book;

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

    public void addBookToLibrary(final Book abook, Boolean availability) {

        AVQuery<AVObject> query = new AVQuery<AVObject>("Book");
        query.whereEqualTo(kBook_DouBanId, abook.getBookDoubanId());
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {

                    if (avObjects.size() == 0) {
                        //abook.saveInBackground();
                    }
                    /*
        // no Book, create Book and BookEntity
        if (objects.count == 0) {
            [book saveInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
                if (error) {
                    [[CustomAlert sharedAlert] showAlertWithMessage:@"添加失败！"];
                    return;
                }

                AVQuery *query = [AVQuery queryForBook];
                [query whereKey:(NSString *)kBook_DouBanId equalTo:book.bookDoubanId];
                [query getFirstObjectInBackgroundWithBlock:^(AVObject *object, NSError *error) {
                    // create BookEntity
                    [self createBookEntityWithBook:(Book *)object availability:availability succeeded:succeededBlock];
                }];
            }];
            return;
        }
                    * */
                } else {
                    //TODO: show error code
                }
            }
        });
    }
}

