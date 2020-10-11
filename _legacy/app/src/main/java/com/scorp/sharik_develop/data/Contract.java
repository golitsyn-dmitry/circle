package com.scorp.circle2.data;

import android.provider.BaseColumns;

public class Contract {

    private Contract(){}

    public static abstract class Entry implements BaseColumns {

        public static String DATABASE_PATH;
        public static final String DATABASE_NAME = "circle.db";
        public static final int DATABASE_VERSION = 1;

        public static final String TABLE_NAME = "player_state";

        public static final String CULUMN_ID = BaseColumns._ID;
        public static final String CULUMN_PLAYER_NAME = "player_name";
        public static final String CULUMN_CURRENT_COUNT = "current_count";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        CULUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CULUMN_PLAYER_NAME + " TEXT," +
                        CULUMN_CURRENT_COUNT + " INTEGER)";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
