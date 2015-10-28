package shagold.wifwaf.dataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserOperations {


    // Database fields
    private DataBaseWrapper dbHelper;
    private String[] USER_TABLE_COLUMNS = { DataBaseWrapper.USER_ID,
                                               DataBaseWrapper.USER_EMAIL,
                                               DataBaseWrapper.USER_NICKNAME,
                                               DataBaseWrapper.USER_PASSWORD,
                                               DataBaseWrapper.USER_BIRTHDAY,
                                               DataBaseWrapper.USER_PHONENUMBER,
                                               DataBaseWrapper.USER_DESCRIPTION,
                                               DataBaseWrapper.USER_PHOTO};
    private SQLiteDatabase database;

    public UserOperations(Context context) {
        dbHelper = new DataBaseWrapper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public User addUser(String email, String nickname, String password, String birthday,
                        int phoneNumber, String description, String photo) {

        ContentValues values = new ContentValues();

        values.put(DataBaseWrapper.USER_EMAIL, email);
        values.put(DataBaseWrapper.USER_NICKNAME, nickname);
        values.put(DataBaseWrapper.USER_PASSWORD, password);
        values.put(DataBaseWrapper.USER_BIRTHDAY, birthday);
        values.put(DataBaseWrapper.USER_PHONENUMBER, phoneNumber);
        values.put(DataBaseWrapper.USER_DESCRIPTION, description);
        values.put(DataBaseWrapper.USER_PHOTO, photo);

        long userId = database.insert(DataBaseWrapper.USERS, null, values);

        // now that the student is created return it ...
        Cursor cursor = database.query(DataBaseWrapper.USERS,
                USER_TABLE_COLUMNS, DataBaseWrapper.USER_ID + " = "
                        + userId, null, null, null, null);

        cursor.moveToFirst();

        User newComment = parseUser(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteUser(User comment) {
        long id = comment.getIdUser();
        System.out.println("Comment deleted with id: " + id);
        database.delete(DataBaseWrapper.USERS, DataBaseWrapper.USER_ID
                + " = " + id, null);
    }

    public List getAllUsers() {
        List users = new ArrayList();

        Cursor cursor = database.query(DataBaseWrapper.USERS,
                USER_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = parseUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }

        cursor.close();
        return users;
    }

    private User parseUser(Cursor cursor) {
        User user = new User();
        user.setIdUser(cursor.getInt(0));
        user.setEmail(cursor.getString(1));
        user.setNickname(cursor.getString(2));
        user.setPassword(cursor.getString(3));
        user.setBirthday(cursor.getString(4));
        user.setPhoneNumber(cursor.getString(5));
        user.setDescription(cursor.getString(6));
        user.setPhoto(cursor.getString(7));
        return user;
    }

}
