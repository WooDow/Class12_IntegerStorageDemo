package com.developer.scott.class12_integerstoragedemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 1.內存可以用來儲存app的私有資料，當app移除時，存在內存的檔案也會一起移除
 * 2.寫入檔案
 *     呼叫 Context.openFileOutput (檔名,模式)，會回傳
 *     FileOutputStream 物件，再使用此物件寫入檔案
 * 3.讀取檔案
 *     呼叫 Context.openFileInput (檔名)，會回傳
 *     FileInputStream 物件，再使用此物件讀取檔案
 * 4.常用的方法
 *     (1). getFilesDir()：會回傳 File 物件，表示內存的路徑 return absolute path
 *     (2). getDir()：建立或開啟一個在內存路徑下的資料夾，回傳 File 物件
 *     (3). fileList()：列出內存路徑下的所有檔案及資料夾，回傳 String[] 物件
 *     (4). deleteFile()：刪除一個在內存路徑下的檔案，回傳 boolean
 *
 */


public class IntegerStorageDemoActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView tv_show, tv_path;
    private EditText et_input;
    private EditText et_filename;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integer_storage_demo);

        findviews();
        spinner.setOnItemSelectedListener(spListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setSpinner();
    }

    private void findviews() {
        spinner = findViewById(R.id.spinner);
        tv_show = findViewById(R.id.tv_show);
        tv_path = findViewById(R.id.tv_path);
        et_input = findViewById(R.id.et_input);
    }

    public void storage(View view) {

        View myView = getLayoutInflater().inflate(R.layout.save_data_dialog_view, null);
        et_filename = myView.findViewById(R.id.et_filename);
        new AlertDialog.Builder(IntegerStorageDemoActivity.this)
                .setTitle("Save Data?")
                .setIcon(R.mipmap.ic_launcher)
                .setView(myView)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setFile();
                        setSpinner();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(IntegerStorageDemoActivity.this,"取消儲存",Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();
    }

    private void setFile() {
        String fileName = et_filename.getText().toString();
        if(fileName.equals("")){
            Toast.makeText(IntegerStorageDemoActivity.this,"file name can't be null",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            FileOutputStream fos = openFileOutput(fileName,MODE_PRIVATE);
            fos.write((et_input.getText().toString() + "\n").getBytes());
            fos.close();
            tv_path.setText(getFilesDir().toString());
            Toast.makeText(IntegerStorageDemoActivity.this,"file saved!",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    private void setSpinner() {
        String[] filesName = fileList();
        ArrayAdapter<String> adt = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,filesName);
        adt.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adt);

    }

    AdapterView.OnItemSelectedListener spListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //下面兩個方法都可以取得 spinner 的選項值
            //String fileName = ((TextView)view).getText().toString();
            fileName = parent.getItemAtPosition(position).toString();
            readfile(fileName);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    void readfile(String fileName){
        try {
            FileInputStream fis = openFileInput(fileName);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            String text = new String(buffer);
            tv_show.setText(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addData(View view) {
        try {
            FileOutputStream fos = openFileOutput(fileName,MODE_APPEND);
            fos.write((et_input.getText().toString() + "\n").getBytes());
            Toast.makeText(IntegerStorageDemoActivity.this,"append data successful",Toast.LENGTH_SHORT).show();
            fos.close();
            readfile(fileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void delete(View view) {
        if(deleteFile(fileName)){
            Toast.makeText(IntegerStorageDemoActivity.this,"file delete successful",Toast.LENGTH_SHORT).show();
            setSpinner();
        }
    }
}
