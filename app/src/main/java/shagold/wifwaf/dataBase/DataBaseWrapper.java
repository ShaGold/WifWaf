package shagold.wifwaf.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseWrapper extends SQLiteOpenHelper {

    public static final String USERS = "User";
    public static final String USER_ID = "idUser";
    public static final String USER_EMAIL = "email";
    public static final String USER_NICKNAME = "nickname";
    public static final String USER_PASSWORD = "password";
    public static final String USER_BIRTHDAY = "birthday";
    public static final String USER_PHONENUMBER="phoneNumber";
    public static final String USER_DESCRIPTION="description";
    public static final String USER_PHOTO="photo";


    private static final String DATABASE_NAME = "WifWaf.db";
    private static final int DATABASE_VERSION = 1;

    // creation SQLite statement
    private static final String DATABASE_CREATE_USER = "create table " + USERS
            + "(" + USER_ID + " integer primary key autoincrement, "
            + USER_EMAIL + " text not null, "
            + USER_NICKNAME + " text not null, "
            + USER_PASSWORD + " text not null, "
            + USER_BIRTHDAY  + " text not null, "
            + USER_PHONENUMBER + " integer, "
            + USER_DESCRIPTION + " text not null, "
            + USER_PHOTO + " text not null);";

    public DataBaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you should do some logging in here
        // ..

        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        onCreate(db);
    }

}
