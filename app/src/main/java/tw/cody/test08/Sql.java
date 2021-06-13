package tw.cody.test08;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Sql extends SQLiteOpenHelper {
    private final static String createTable =
            "CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", username TEXT, tel TEXT, birthday DATE)";
    public Sql(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
