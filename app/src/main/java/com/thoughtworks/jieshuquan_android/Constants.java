package com.thoughtworks.jieshuquan_android;

public interface Constants {

    // Douban Key
    public final String K_DOUBANBOOK_ID = "id";
    public final String K_DOUBANBOOK_TITLE = "title";
    public final String K_DOUBANBOOK_AUTHOR = "author";
    public final String K_DOUBANBOOK_IMAGE = "image";
    public final String K_DOUBANBOOK_IMAGES = "images";
    public final String K_DOUBANBOOK_LARGE = "large";
    public final int DEFAULT_COMMENTS_COUNTS = 20;


    // All key for AVOS
    public final String KAVATAR = "avatar";
    public final String KOBJECT_ID = "objectId";

    public final String KBOOK_NAME = "bookName";
    public final String KBOOK_AUTHOR = "bookAuthor";
    public final String KBOOK_IMAGE_HREF = "bookImageHref";

    public final String BOOK_ENTITY = "BookEntity";
    public final String KBOOK_DOUBANID = "bookDoubanId";
    public final String KBOOKENTITY_BOOK = "doubanBook";
    public final String KBOOKENTITY_USER = "bookOwner";
    public final String KBOOK_AVAILABILITY = "bookAvailability";

    // Activity result tag
    public final int LOGIN_ACTIVITY_RESULT_TAG = 200;
    public final int SCANER_ACTIVITY_RESULT_TAG = 100;

    public final String KSUCCESS = "success";

    // Discover
    public final String KUSER = "user";
    public final String KTWITTER = "twitter";
    public final String KBOOK = "book";
    public final String KTYPE = "type";
    public final String KBOOKNAME = "bookName";

    public final int DISCOVERTYPE_ADDBOOK = 0;
    public final int DISCOVERTYPE_TWITTER = 1;

    public static final String EXTRA_BOOK_ID = "book_id";
}
