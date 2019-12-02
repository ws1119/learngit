package com.example.contentprovidertext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ContentResolver contentResolver;
    Button insert = null;
    Button search = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取系统的ContentResolver对象
        contentResolver=getContentResolver();
        insert = findViewById(R.id.insert);
        search = findViewById(R.id.search);
        //为insert按钮添加监听事件
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户输入
                String word = ((EditText)findViewById(R.id.word)).getText().toString();
                String detail = ((EditText)findViewById(R.id.detail)).getText().toString();
                //插入生词记录
                ContentValues values = new ContentValues();
                values.put(Words.Word.WORD,word);
                values.put(Words.Word.DETAIL,detail);
                contentResolver.insert(Words.Word.DICT_CONTENT_URI,values);
                //显示提示信息
                Toast.makeText( MainActivity.this, "添加生词成功", Toast.LENGTH_SHORT ).show();
            }
        });
        //为search按钮添加事件绑定监听器
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户输入
                String key =((EditText)findViewById(R.id.key)).getText().toString();
                //执行查询
                Cursor cursor = contentResolver.query(Words.Word.DICT_CONTENT_URI,null,"word like ? or detail like ?",new  String[]{"%"+key+"%","%"+key+"%"},null);
                //创建一个bundle对象
                //ArrayList<Map<String,String>> list  =new ArrayList<>();

                while(cursor.moveToNext()){
                    /*Map<String,String> map  =new HashMap<String, String>();
                    map.put(Words.Word.WORD,cursor.getString(1));
                    map.put(Words.Word.DETAIL,cursor.getString(2));*/
                    ((TextView)findViewById(R.id.show)).setText(cursor.getString(2));
                    //list.add(map);
                }


            }
        });
    }
}
