package com.example.firstprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ContentResolver contentResolver;
    Uri uri = Uri.parse("content://com.example.firstprovider.FirstProvider");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取系统的contenResolver对象
        contentResolver=getContentResolver();

    }

    public void query(View source)
    {
        //调用ContentResolver的query方法
        //实际返回的是Uri对应的ContentProvider的query方法的返回值
        Cursor c=contentResolver.query(uri,null,"query_where",
                null,null);
        Toast.makeText( MainActivity.this, "远程ContentProvider返回的Cursor为"+c, Toast.LENGTH_SHORT ).show();
    }
    public void insert(View source)
    {
        ContentValues values = new ContentValues();
        values.put("name","fkjava");
        //调用ContentResolver的insert方法
        //实际返回的是Uri对应的ContentProvider的insert方法的返回值
        Uri newUri = contentResolver.insert(uri,values);
        Toast.makeText( MainActivity.this, "远程ContentProvider新插入记录的Uri为"+newUri, Toast.LENGTH_SHORT ).show();
    }

    public void update(View source)
    {
        ContentValues values = new ContentValues();
        values.put("name","fkjava");
        //调用ContentResolver的update方法
        //实际返回的是Uri对应的ContentProvider的update方法的返回值
        int count = contentResolver.update(uri,values,"updata_where",null);
        Toast.makeText( MainActivity.this, "远程ContentProvider更新记录数为"+count, Toast.LENGTH_SHORT ).show();
    }

    public void delete(View source)
    {
        //调用ContentResolver的delete方法
        //实际返回的是Uri对应的ContentProvider的delete方法的返回值
        int count = contentResolver.delete(uri,"delete_where",null);
        Log.v("==========",uri.toString());
        Toast.makeText( MainActivity.this, "远程ContentProvider删除记录的Uri为"+count, Toast.LENGTH_SHORT ).show();
    }

}
