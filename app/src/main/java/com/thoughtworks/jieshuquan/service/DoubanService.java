package com.thoughtworks.jieshuquan.service;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thoughtworks.jieshuquan.BuildConfig;
import com.thoughtworks.jieshuquan.Constants;

public class DoubanService {

    private static DoubanService instance;

    public static DoubanService getInstance() {
        if (instance == null) {
            instance = new DoubanService();
        }
        return instance;
    }

    /*
    根据IBNS码获取书籍信息
    * */
    public void getBookInfoFromDouBan(String ibns, JsonHttpResponseHandler handler) {

        String urlString = BuildConfig.DOUBAN_SERVER_URL + "isbn/" + ibns;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("apikey", BuildConfig.DOUBAN_SERVER_API_KEY);

        client.get(urlString, params, handler);
    }

    /*
    根据bookId获取书籍的所有评论
    * */
    public void getBookComments(String bookId, int startIndex, JsonHttpResponseHandler handler) {
        String urlString = BuildConfig.DOUBAN_SERVER_URL + bookId + "/reviews?start=" + Constants.DEFAULT_COMMENTS_COUNTS * startIndex;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("apikey", BuildConfig.DOUBAN_SERVER_API_KEY);

        client.get(urlString, params, handler);

    }
}
