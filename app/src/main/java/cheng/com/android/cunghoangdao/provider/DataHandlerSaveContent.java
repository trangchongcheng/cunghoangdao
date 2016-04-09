package cheng.com.android.cunghoangdao.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.model.Article;

/**
 * Created by Welcome on 4/8/2016.
 */
public class DataHandlerSaveContent extends SQLiteOpenHelper {
    private Context context;
    private final String TAG = getClass().getSimpleName();
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "articleManager";

    // Contacts table name
    private static final String TABLE_ARTICLE = "article";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_CONTENT = "content";
    private final ArrayList<Article> content_list = new ArrayList<Article>();

    public DataHandlerSaveContent(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    //    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ARTICLE + "("
//                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT,"
//                + KEY_LINK + " TEXT," + KEY_PUBDATE + " TEXT" + KEY_CATEGORY + " TEXT,"+ KEY_CONTENT + " TEXT,"+ ")";
//        db.execSQL(CREATE_CONTACTS_TABLE);
//    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ARTICLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_IMAGE + " BLOB,"
                + KEY_CONTENT + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addArticle(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, article.getmTitle());
        values.put(KEY_CATEGORY, article.getmCategory());
        values.put(KEY_IMAGE, article.getmImage());
        values.put(KEY_CONTENT, article.getmContent()); // Contact Name
        // Inserting Row
        db.insert(TABLE_ARTICLE, null, values);
        db.close(); // Closing database connection
        Toast.makeText(context, "Đã lưu thành công", Toast.LENGTH_SHORT).show();
    }

    public Article getArticleById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ARTICLE, new String[]{KEY_ID,
                        KEY_CONTENT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Article article = new Article(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
               cursor.getBlob(3), cursor.getString(4));
        cursor.close();
        db.close();

        return article;
    }

    public ArrayList<Article> getAllArticle() {
        try {
            content_list.clear();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ARTICLE;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Article article = new Article();
                    article.setId(Integer.parseInt(cursor.getString(0)));
                    article.setmTitle(cursor.getString(1));
                    article.setmLink(cursor.getString(2));
                    article.setmImage(cursor.getBlob(3));
                    article.setmContent(cursor.getString(4));
                    content_list.add(article);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return content_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_contact", "" + e);
        }

        return content_list;
    }
    public void deleteArticle(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ARTICLE, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

}
