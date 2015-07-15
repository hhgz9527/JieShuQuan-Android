package com.thoughtworks.jieshuquan_android.service;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.thoughtworks.jieshuquan_android.Constants;
import com.thoughtworks.jieshuquan_android.service.model.Book;
import com.thoughtworks.jieshuquan_android.service.model.BookEntity;
import com.thoughtworks.jieshuquan_android.service.model.BorrowRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by leihuang on 6/10/15.
 */
public class BookService {

    private static BookService instance;

    public static BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    /*
    添加二维码扫描书籍到Book类中
    * */
    public void addBookToLibrary(final Book abook, final Boolean availability, final SaveCallback addBookCallback) {

        final AVQuery<AVObject> query = new AVQuery<AVObject>("Book");
        query.whereEqualTo(Constants.KBOOK_DOUBANID, abook.getBookDoubanId());
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {

                    if (avObjects.size() == 0) {
                        abook.saveInBackground(new SaveCallback() {
                            public void done(AVException e) {
                                if (e == null) {
                                    // save success
                                    AVQuery query1 = new AVQuery<AVObject>("Book");
                                    query.whereEqualTo(Constants.KBOOK_DOUBANID, abook.getBookDoubanId());
                                    query.getFirstInBackground(new GetCallback<AVObject>() {
                                        @Override
                                        public void done(AVObject avObject, AVException e) {
                                            BookService.this.createBookEntityWithBook((Book) avObject, availability, addBookCallback);
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
                    query.whereEqualTo(Constants.KBOOK_DOUBANID, abook.getBookDoubanId());
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

    /*
    添加Book类到user的BookEntity类中
    * */
    public void createBookEntityWithBook(final Book abook, final Boolean availability, final SaveCallback createBookEntitySaveCallback) {

        final AVQuery<AVObject> query = new AVQuery<AVObject>("BookEntity");
        query.whereEqualTo(Constants.KBOOKENTITY_USER, AVUser.getCurrentUser());
        query.whereEqualTo(Constants.KBOOKENTITY_BOOK, abook);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list.size() == 0) {

                        BookEntity bookEntity = new BookEntity();
                        bookEntity.setBookAvailability(availability);
                        bookEntity.setBookName(abook.getBookName());
                        bookEntity.setBookImageHref(abook.getBookImageHref());
                        bookEntity.setUser(AVUser.getCurrentUser());
                        bookEntity.setBook(abook);
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


    /*
    获取所有人的图书，“借书”主页
    * */
    public void fetchAllBooks(int startIndex, FindCallback callback) {
        final AVQuery<AVObject> query = new AVQuery<AVObject>("Book");
        query.orderByDescending("createdAt");
        query.setLimit(10);
        query.setSkip(startIndex);
        query.findInBackground(callback);
    }


    /*
    获取 “我的书籍” 图书
    * */
    public void fetchBookEntitiesForCurrentUser(FindCallback callback) {
        final AVQuery<AVObject> query = new AVQuery<AVObject>("BookEntity");
        query.whereEqualTo(Constants.KBOOKENTITY_USER, AVUser.getCurrentUser());
        query.orderByDescending("createdAt");
        query.findInBackground(callback);

    }


    /*
    获取图书排名，借书次数必须大于0
    * */
    public void fetchRecoBooks(FindCallback callback) {
        final AVQuery<AVObject> query = new AVQuery<AVObject>("Book");
        query.whereGreaterThan("borrowCount", 0);
        query.orderByDescending("borrowCount");
        query.findInBackground(callback);
    }


    /*
    获取所有“可借”的 bookentity for a book
    * */
    public void fetchAllAvaliableBookEntities(Book book, FindCallback callback) {
        final AVQuery<AVObject> query = new AVQuery<AVObject>("BookEntity");
        query.whereEqualTo(Constants.KBOOKENTITY_BOOK, book);
        query.whereEqualTo(Constants.KBOOK_AVAILABILITY, 1);
        query.findInBackground(callback);
    }

    /*
    遍历数组bookentities，获取每一本书的owner，返回数组，用在点击“申请借阅”后的页面
    * */
    public void fetchOwners(List<BookEntity> bookEntities, FindCallback callback) {
        final List<AVUser> users = new ArrayList<>();
        final CountDownLatch countdown = new CountDownLatch(bookEntities.size());
        for (BookEntity bookEntity : bookEntities) {
            AVUser user = bookEntity.getUser();
            user.fetchIfNeededInBackground(new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if (e == null) {
                        users.add((AVUser) avObject);
                    }
                    countdown.countDown();
                }
            });
        }

        try {
            countdown.await();
            callback.done(users, null);

        } catch (InterruptedException e) {

            AVException exception = new AVException(-1, "unKnow Error");
            callback.done(null, exception);
        }
    }


    /*
    改变自己拥有图书可借状态
    * */
    public void updateBookAvailability(final Book book, final SaveCallback callback) {
        final AVQuery<AVObject> query = new AVQuery<AVObject>("BookEntity");
        query.whereEqualTo(Constants.KBOOKENTITY_USER, AVUser.getCurrentUser());
        query.whereEqualTo(Constants.KBOOKENTITY_BOOK, book);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    BookEntity bookEntity = (BookEntity) list.get(0);
                    bookEntity.setBookAvailability(true);
                    bookEntity.saveInBackground(callback);
                } else {
                    callback.done(e);
                }
            }
        });
    }


    /*
    根据收到的结束请求，来处理bookEntity的 availability 状态
    * */
    public void updateBookAvailability(BorrowRecord borrowRecord, final boolean availability, final SaveCallback callback) {
        final BookEntity bookEntity = borrowRecord.getAVObject(Constants.BOOK_ENTITY);
        bookEntity.fetchIfNeededInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    bookEntity.setBookAvailability(availability);
                    bookEntity.saveInBackground(callback);
                } else {
                    callback.done(e);
                }
            }
        });
    }

    /*
     创建借阅申请
    * */
    public void createBorrowRecord(AVUser fromUser, AVUser toUser, BookEntity bookEntity, SaveCallback callback) {
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setFromUserName(fromUser.getUsername());
        borrowRecord.setToUserName(toUser.getUsername());
        borrowRecord.setBookName(bookEntity.getBookName());
        borrowRecord.setBookImageHref(bookEntity.getBookImageHref());
        borrowRecord.setStatus(Constants.KBORROWRECORD_STATUS_PENDING);
        borrowRecord.setFromUser(fromUser);
        borrowRecord.setToUser(toUser);
        borrowRecord.setBookEntity(bookEntity);
        borrowRecord.saveInBackground(callback);
    }

    /*
    获取所有的借书通知（status = Borrow_Book_Pending）
    * */
    public void fetchAllPendingBorrowRecords(FindCallback callback) {
        final AVQuery<AVObject> query = new AVQuery<AVObject>("BorrowRecord");
        query.whereEqualTo(Constants.KBORROWRECORD_STATUS, Constants.KBORROWRECORD_STATUS_PENDING);
        query.whereEqualTo(Constants.KBORROWRECORD_TO_USER, AVUser.getCurrentUser());
        query.orderByDescending("createdAt");
        query.findInBackground(callback);
    }

    /*
    改变借书申请的状态
    * */
    public void changeBorrowRecordStatus(final String newStatus, final BorrowRecord borrowRecord, final SaveCallback callback) {
        borrowRecord.fetchIfNeededInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null && avObject != null) {
                    borrowRecord.setStatus(newStatus);
                    borrowRecord.saveInBackground(callback);
                } else {
                    callback.done(e);
                }

            }
        });
    }

    /*
    获取“我借入的书”清单
    * */
    public void fetchAllMyBorrowedInRecords(FindCallback callback) {
        final AVQuery<AVObject> query = new AVQuery<AVObject>("BorrowRecord");
        query.whereEqualTo(Constants.KBORROWRECORD_FROM_USER, AVUser.getCurrentUser());
        query.whereEqualTo(Constants.KBORROWRECORD_STATUS, Constants.KBORROWRECORD_STATUS_AGREED);
        query.orderByDescending("createdAt");
        query.findInBackground(callback);
    }

    /*
    获取“我借出的书”清单
    * */
    public void fetchAllMyBorrowedOutRecords(FindCallback callback) {
        final AVQuery<AVObject> query = new AVQuery<AVObject>("BorrowRecord");
        query.whereEqualTo(Constants.KBORROWRECORD_TO_USER, AVUser.getCurrentUser());
        query.whereEqualTo(Constants.KBORROWRECORD_STATUS, Constants.KBORROWRECORD_STATUS_AGREED);
        query.orderByDescending("createdAt");
        query.findInBackground(callback);
    }


    /*
    给借书次数加1
    * */

    public void increaseBorrowCount(BorrowRecord borrowRecord) {
        BookEntity bookEntity = borrowRecord.getBookEntity();
        bookEntity.fetchIfNeededInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    final Book book = ((BookEntity) avObject).getBook();
                    book.fetchIfNeededInBackground(new GetCallback<AVObject>() {
                        @Override
                        public void done(AVObject avObject, AVException e) {
                            book.setBorrowCount(book.getBorrowCount() + 1);
                            book.saveInBackground();
                        }
                    });
                }
            }
        });
    }

    /*
    搜索书籍
    * */
    public void searchBook(String bookName, FindCallback callback) {
        final AVQuery<AVObject> query = new AVQuery<AVObject>("Book");
        query.whereEqualTo(Constants.KBOOKNAME, bookName);
        query.findInBackground(callback);

    }
}

