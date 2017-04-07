package mobilecomputing.twitterclient.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import mobilecomputing.twitterclient.model.Group;

public class DBHelper extends SQLiteOpenHelper {

    public SQLiteDatabase _db;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GroupManager.db";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if(_db == null){
            _db = getWritableDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String command = "CREATE TABLE " + DBTables.GroupTable.TABLE_NAME + " (";
        command += DBTables.GroupTable._ID + " INTEGER PRIMARY KEY,";
        command += DBTables.GroupTable.COLUMN_NAME_GROUP_NAME + " TEXT,";
        command += DBTables.GroupTable.COLUMN_NAME_NUM_MEMBERS + " TEXT";
        command += ")";

        db.execSQL(command);

        command = "CREATE TABLE " + DBTables.ScreenNameTable.TABLE_NAME + " (";
        command += DBTables.ScreenNameTable._ID + " INTEGER PRIMARY KEY,";
        command += DBTables.ScreenNameTable.COLUMN_NAME_GROUP_ID + " TEXT,";
        command += DBTables.ScreenNameTable.COLUMN_NAME_SCREENAME + " TEXT";
        command += ")";

        db.execSQL(command);

        /* Create Sample Data */
        Group group = new Group();
        group.groupName = "Celebrities";
        group.numberOfMembers = 2;

        int id = (int) AddGroup(group);

        ArrayList<String> users = new ArrayList<>();

        String one = "realdonaldtrump";
        String two = "barackobama";

        users.add(one);
        users.add(two);

        AddUsers(users,id);
    }

    public long AddGroup(Group newGroup){
        ContentValues values = new ContentValues();
        values.put(DBTables.GroupTable.COLUMN_NAME_GROUP_NAME, newGroup.groupName);
        values.put(DBTables.GroupTable.COLUMN_NAME_NUM_MEMBERS, Integer.toString(newGroup.numberOfMembers));
        return _db.insert(DBTables.GroupTable.TABLE_NAME, null, values);

    }
    public void DeleteGroup(int id){
        _db.delete(DBTables.ScreenNameTable.TABLE_NAME, DBTables.ScreenNameTable.COLUMN_NAME_GROUP_ID
                + "=" + Integer.toString(id), null);
        _db.delete(DBTables.GroupTable.TABLE_NAME, DBTables.GroupTable._ID + "=" + Integer.toString(id), null);

    }

    public void AddUsers(ArrayList<String> screenNames, int groupId){
        ContentValues values;
        for(String name : screenNames){
            values = new ContentValues();
            values.put(DBTables.ScreenNameTable.COLUMN_NAME_GROUP_ID, Integer.toString(groupId));
            values.put(DBTables.ScreenNameTable.COLUMN_NAME_SCREENAME, name);
            _db.insert(DBTables.ScreenNameTable.TABLE_NAME, null, values);
        }
    }

    public ArrayList<Group> GetGroups(){

        ArrayList<Group> groups = new ArrayList<>();

        String[] columns = {
                DBTables.GroupTable._ID,
                DBTables.GroupTable.COLUMN_NAME_GROUP_NAME,
                DBTables.GroupTable.COLUMN_NAME_NUM_MEMBERS
        };

        Cursor cursor = _db.query(DBTables.GroupTable.TABLE_NAME, columns, null, null, null, null,
                DBTables.GroupTable._ID);

        while (cursor.moveToNext()) {
            Group model = new Group();

            model.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBTables.GroupTable._ID)));
            model.groupName = cursor.getString(cursor.getColumnIndex(DBTables.GroupTable.COLUMN_NAME_GROUP_NAME));
            model.numberOfMembers = Integer.parseInt(
                    cursor.getString(cursor.getColumnIndex(DBTables.GroupTable.COLUMN_NAME_NUM_MEMBERS)));

            groups.add(model);
        }
        cursor.close();

        return groups;
    }

    public ArrayList<String> GetUsers(int groupId){

        ArrayList<String> screenNames = new ArrayList<>();

        String[] columns = {
                DBTables.ScreenNameTable._ID,
                DBTables.ScreenNameTable.COLUMN_NAME_GROUP_ID,
                DBTables.ScreenNameTable.COLUMN_NAME_SCREENAME
        };
        Cursor cursor = _db.query(DBTables.ScreenNameTable.TABLE_NAME,columns,
                DBTables.ScreenNameTable.COLUMN_NAME_GROUP_ID + "=" + Integer.toString(groupId),
                null, null, null, null);

        while (cursor.moveToNext()) {
            String screenname = cursor.getString(cursor.getColumnIndex(DBTables.ScreenNameTable.COLUMN_NAME_SCREENAME));

            screenNames.add(screenname);
        }
        cursor.close();

        return screenNames;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete the database and the recreate it
        String command = "DROP TABLE IF EXISTS " + DBTables.GroupTable.TABLE_NAME;
        db.execSQL(command);
        command = "DROP TABLE IF EXISTS " + DBTables.ScreenNameTable.TABLE_NAME;
        db.execSQL(command);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
