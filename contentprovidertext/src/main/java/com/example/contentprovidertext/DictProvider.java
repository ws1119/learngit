package com.example.contentprovidertext;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DictProvider extends ContentProvider {

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int WORDS=1;
    private static final int WORD=2;
    private  MyDatabaseHelper dbOpenHelper;
    static {
        //为Uri注册两个Uri
        matcher.addURI(Words.AUTHORITY,"words",WORDS);
        matcher.addURI(Words.AUTHORITY,"word",WORD);
    }
    //第一次调用DictProvider时，系统先创建DictProvider对象，并回调该方法
    @Override
    public boolean onCreate() {
        dbOpenHelper = new MyDatabaseHelper(this.getContext(),"MyDict1.db3",1);
        return true;
    }
    //返回指定Uri参数对应的数据的MIME类型
    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri))
        {
            //如果操作的是多项记录
            case WORDS:
                return "vnd.android.cursor.dir/com.example.dict";
            //如果操作的是单项记录
            case WORD:
                return "vnd.android.cursor.item/com.example.dict";
                default:
                    throw new IllegalArgumentException("未知URI："+uri);

        }
    }


    //查询数据的方法
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (matcher.match(uri)){
            //如果URI参数代表操作全部数据
            case WORDS:
                //执行查询
                return db.query("dict1",projection,selection,selectionArgs,null,null,sortOrder);
            //如果URI代表操作制定项数据
            case WORD:
                //解析出想要查询的ID
                long id  = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID+"="+id;
                //如果原来的where字句存在，拼接where字句
                if (selection != null && "".equals(selection)) {
                    whereClause = whereClause+"and"+selection;
                }
                return  db.query("dict1",projection,whereClause,selectionArgs,null,null,sortOrder);
                default:
                    throw new IllegalArgumentException("未知URI："+uri);
        }
    }

    //插入数据的方法
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v("============","Provider的insert方法被调用");
        //获取数据库实例
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (matcher.match(uri))
        {
            //如果URI参数代表操作全部数据
            case WORDS:
                //插入数据返回插入记录的ID
                long rowID = db.insert("dict1",Words.Word._ID,values);
                //如果插入成功，返回URI
                if (rowID>0)
                {
                    //在已有的URI后面追加ID
                    Uri wordUri = ContentUris.withAppendedId(uri,rowID);
                    //通知数据已经该改变
                    getContext().getContentResolver().notifyChange(wordUri,null);
                    return wordUri;
                }
                break;
                default:
                    throw new IllegalArgumentException("未知URI："+uri);
        }
        return null;
    }

    //删除的方法

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        //记录删除的记录数
        int num =0;
        switch (matcher.match(uri)){
            //如果URI参数代表操作全部数据
            case WORDS:
                num = db.delete("dict1",selection,selectionArgs);
                break;
            //如果URI代表操作制定项数据
            case WORD:
                //解析想要删除的数据的ID
                long id= ContentUris.parseId(uri);
                String whereCalues = Words.Word._ID+"="+id;
                //where语句进行拼接
                if (selection != null && "".equals(selection))
                {
                    whereCalues = selection+"and"+whereCalues;
                }
                num=db.delete("dict1",whereCalues,selectionArgs);
                break;
                default:
                    throw new IllegalArgumentException("未知URI："+uri);
        }
        //通知数据已经改变
        getContext().getContentResolver().notifyChange(uri,null);
        return num;
    }


    //修改数据的方法
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        //记录所修修改的记录数
        int num=0;
        switch (matcher.match(uri)){
            //如果URI参数代表操作全部数据
            case WORDS:
                num = db.update("dict1",values,selection,selectionArgs);
                break;
            //如果URI代表操作制定项数据
            case WORD:
                //解析处想要修改的记录的ID
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID+"="+id;
                //如果原来的where字句存在，进行拼接
                if (selection != null && "".equals(selection))
                {
                    whereClause=selection+"and"+whereClause;
                }
                num=db.update("dict1",values,selection,selectionArgs);
                break;
                default:
                    throw new IllegalArgumentException("未知URI："+uri);
        }
        //通知数据已经改变
        getContext().getContentResolver().notifyChange(uri,null);
        return num;
    }
}
