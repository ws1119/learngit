package com.example.firstprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class FirstProvider extends ContentProvider {
    //第一次创建该ContentProvider时调用该方法
    @Override
    public boolean onCreate() {
        System.out.println("=====onCreate方法被调用====");
        return true;
    }



    //查询方法，该方法该返回查询得到的Cursor对象
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        System.out.println(uri+"=====query方法被调用====");
        System.out.println("where的参数为"+selection);
        return null;
    }

    //该方法的返回值代表了该Provider所提供的MIME数据类型
    @Override
    public String getType(Uri uri) {
        return null;
    }

    //实现插入方法。该方法返回新插入的记录的Uri
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        System.out.println(uri+"=====insert方法被调用====");
        System.out.println("values的参数为："+values);
        return null;
    }
    //实现删除，该方法返回被删除的记录条数
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        System.out.println(uri+"=====delete方法被调用====");
        System.out.println("where的参数为"+selection);
        return 0;
    }

    //实现更新方法，该方法返回被更新的记录条数
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        System.out.println(uri+"=====update方法被调用====");
        System.out.println("where的参数为"+selection+",values的参数为"+values);
        return 0;
    }
}
