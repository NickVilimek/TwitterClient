package mobilecomputing.twitterclient.database;

import android.provider.BaseColumns;

public final class DBTables {

    public static class GroupTable implements BaseColumns {
        public static final String TABLE_NAME = "groups";
        public static final String COLUMN_NAME_GROUP_NAME = "groupName";
        public static final String COLUMN_NAME_NUM_MEMBERS = "numberOfMembers";
    }

    public static class ScreenNameTable implements BaseColumns {
        public static final String TABLE_NAME = "screeNames";
        public static final String COLUMN_NAME_GROUP_ID = "groupId";
        public static final String COLUMN_NAME_SCREENAME = "username";
    }

}
