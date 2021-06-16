package tw.cody.test08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView view1;
    private File sdroot,approot;
    private Sql sql;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},9487);
        } else {
            init();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9487) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            } else  {
                finish();
            }
        }
    }

    private void init() {
        sp = getSharedPreferences("cody",MODE_PRIVATE);
        editor = sp.edit();
        view1 = findViewById(R.id.textView);

        sdroot = Environment.getExternalStorageDirectory();
        Log.v("cody",sdroot.getAbsolutePath());

        approot = new File(sdroot,"Android/data/" + getPackageName());
        if (!approot.exists()) {
            approot.mkdirs();
        }
        Log.v("cody",approot.getAbsolutePath());
        sql = new Sql(this,"db",null,1);
        db = sql.getWritableDatabase();
    }

    public void test1(View view) {
        editor.putFloat("aa",123);
        editor.putBoolean("bb",true);
        editor.putInt("cc",456);
        editor.commit();
        Toast.makeText(this,"save ok",Toast.LENGTH_SHORT).show();
    }
    public void test2(View view) {
        float aa = sp.getFloat("aa",44);
        Boolean bb = sp.getBoolean("bb",false);
        Integer cc = sp.getInt("cc",789);
        Log.v("cody",aa +":"+ bb +":"+ cc);

    }
    public void test3(View view) {
        try {
            FileOutputStream stream = openFileOutput("coco.txt",MODE_APPEND);
            stream.write("5484\n".getBytes());
            stream.flush();
            stream.close();
            Toast.makeText(this,"save ok",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.v("cody",e.toString());
        }

    }
    public void test4(View view) {
        try {
            FileInputStream stream = openFileInput("coco.txt");
            StringBuffer sb = new StringBuffer();
            int i;
            while ((i = stream.read()) !=-1 ) {
                sb.append((char)i);
            }
            view1.setText(sb.toString());
        Toast.makeText(this,"save ok",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.v("cody",e.toString());
        }
    }
    public void test5(View view) {
        File file = new File(sdroot,"cc1.cc");
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write("hello".getBytes());
            stream.flush();
            stream.close();
        Toast.makeText(this,"save ok",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.v("cody",e.toString());
        }
    }
    public void test6(View view) {
        File file = new File(approot,"cc2.cc");
        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write("hello22".getBytes());
            stream.flush();
            stream.close();
        Toast.makeText(this,"save ok",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.v("cody",e.toString());
        }
    }

    public void test7(View view) {
        Cursor c =  db.query("user",null,null,null,null,null,null);
        while (c.moveToNext()) {
            String id = c.getString(0);
            String user = c.getString(1);
            String tel = c.getString(2);
            String birthday = c.getString(3);
            Log.v("cody",id +":"+ user +":"+ tel +":"+ birthday);
        }
    }
    public void test8(View view) {
        ContentValues cv = new ContentValues();
        cv.put("username","cody");
        cv.put("tel","0212");
        cv.put("birthday","1999-01-01");
        db.insert("user",null,cv);
        test7(null);
    }
    public void test9(View view) {
        db.delete("user","id = ?",new String[] {"1"});
        test7(null);
    }
    public void test10(View view) {
        ContentValues cv = new ContentValues();
        cv.put("username","co");
        cv.put("tel","02");
        cv.put("birthday","1979-01-11");
        db.update("user",cv,"id = ?",new String[] {"2"});
        test7(null);
    }
}