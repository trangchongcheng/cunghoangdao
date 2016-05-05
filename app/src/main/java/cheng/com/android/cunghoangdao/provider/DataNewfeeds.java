package cheng.com.android.cunghoangdao.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.model.Article;

/**
 * Created by Welcome on 4/14/2016.
 */
public class DataNewfeeds extends SQLiteOpenHelper {

    private Context context;
    private final String TAG = getClass().getSimpleName();
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "articleNotifycation";

    // Contacts table name
    private static final String TABLE_ARTICLE = "article";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_IMAGE = "image";
    private static final String HAS_READ = "hasread";
    private final ArrayList<Article> content_list = new ArrayList<>();


    public DataNewfeeds(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ARTICLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_CONTENT + " TEXT,"
                + HAS_READ + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
        // Create tables again
        onCreate(db);
    }

    public void addArticle(Article article) {
        delete(0);
        if (article != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, article.getmTitle());
            values.put(KEY_DESCRIPTION, article.getmDescription());
            values.put(KEY_CONTENT, article.getmContent());
            values.put(HAS_READ, 0);
            db.insert(TABLE_ARTICLE, null, values);
            db.close(); // Closing database connection
            //Toast.makeText(context, "Đã lưu thành công", Toast.LENGTH_SHORT).show();
        }
    }

    public int setHasRead() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HAS_READ, 1);
        // updating row
        return db.update(TABLE_ARTICLE, values, HAS_READ + " = ?",
                new String[]{String.valueOf(0)});
    }

    public Article getArticle(int hasRead) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ARTICLE, new String[]{KEY_ID,
                        KEY_TITLE, KEY_DESCRIPTION, KEY_CONTENT, HAS_READ}, HAS_READ + "=?",
                new String[]{String.valueOf(hasRead)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Log.d(TAG, "getArticleById: " + cursor.getString(1));
        Article article = new Article(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), Integer.parseInt(cursor.getString(4)));
        cursor.close();
        db.close();
        //Toast.makeText(context, "Lấy thành công", Toast.LENGTH_SHORT).show();
        return article;
    }

    public void delete(int hasread) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ARTICLE, HAS_READ + " = ?",
                new String[]{String.valueOf(hasread)});
        Log.d(TAG, "delete successfully");
        db.close();
    }
}
