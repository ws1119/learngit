package com.example.contentprovidertext;

import android.net.Uri;
import android.provider.BaseColumns;

public class Words {
    //定义ContentProvider的Authorities
    public static final String AUTHORITY="com.example.contentprovidertext.DictProvider";
    //定义静态内部类，定义该ContentProvider所包含的数据列的列名
    public static final class  Word implements BaseColumns{
        //定义Content所允许操作的三个数据
        public final static String _ID= "_id";
        public final static String WORD= "word";
        public final static String DETAIL= "detail";
        //定义该content提供服务的两个URI
        public final static Uri DICT_CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/words");
        public final static Uri WORD_CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/word");
    }
}
