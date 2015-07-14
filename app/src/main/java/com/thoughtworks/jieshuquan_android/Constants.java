package com.thoughtworks.jieshuquan_android;

public interface Constants {

    // Douban Key
    public final String K_DOUBANBOOK_ID = "id";
    public final String K_DOUBANBOOK_TITLE = "title";
    public final String K_DOUBANBOOK_AUTHOR = "author";
    public final String K_DOUBANBOOK_IMAGE = "image";
    public final String K_DOUBANBOOK_IMAGES = "images";
    public final String K_DOUBANBOOK_LARGE = "large";
    public final String K_DOUBANBOOK_DESCRIPTION = "summary";
    public final String K_DOUBANBOOK_PUBLISHER = "publisher";
    public final int DEFAULT_COMMENTS_COUNTS = 20;


    // All key for AVOS
    public final String KUSER_OFFICE = "office";
    public final String KAVATAR = "avatar";
    public final String KOBJECT_ID = "objectId";

    public final String KBOOK_NAME = "bookName";
    public final String KBOOK_AUTHOR = "bookAuthor";
    public final String KBOOK_IMAGE_HREF = "bookImageHref";
    public final String KBOOK_BORROW_COUNT = "borrowCount";
    public final String KBOOK_DESCRIPTION = "bookDescription";
    public final String KBOOK_PRESS = "bookPress";



    public final String BOOK_ENTITY = "BookEntity";
    public final String KBOOK_DOUBANID = "bookDoubanId";
    public final String KBOOKENTITY_BOOK = "doubanBook";
    public final String KBOOKENTITY_USER = "bookOwner";
    public final String KBOOK_AVAILABILITY = "bookAvailability";

    // Activity result tag
    public final int LOGIN_ACTIVITY_RESULT_TAG = 200;
    public final int SCANER_ACTIVITY_RESULT_TAG = 100;
    public final int POPULAR_ACTIVITY_RESULT_TAG = 300;

    public final String KSUCCESS = "success";

    // Discover
    public final String KUSER = "user";
    public final String KTWITTER = "twitter";
    public final String KBOOK = "book";
    public final String KTYPE = "type";
    public final String KBOOKNAME = "bookName";

    public final int DISCOVERTYPE_ADDBOOK = 0;
    public final int DISCOVERTYPE_TWITTER = 1;

    //BorrowRecord
    // pending, agreed, rejected, returned
    public final String KBORROWRECORD_STATUS_PENDING = "pending";
    public final String KBORROWRECORD_STATUS_AGREED = "agreed";
    public final String KBORROWRECORD_STATUS_REJECTED = "rejected";
    public final String KBORROWRECORD_STATUS_RETURNED = "returned";


    public final String KBORROWRECORD_FROM_USER_NAME = "fromUserName";
    public final String KBORROWRECORD_STATUS = "status";
    public final String KBORROWRECORD_TO_USER_NAME = "toUserName";
    public final String KBORROWRECORD_BOOK_IMAGE_HREF = "bookImageHref";
    public final String KBORROWRECORD_TO_USER = "toUser";
    public final String KBORROWRECORD_BOOK_ENTITY = "bookEntity";
    public final String KBORROWRECORD_FROM_USER = "fromUser";
    
    
    public static final String EXTRA_BOOK_ID = "book_id";
}
