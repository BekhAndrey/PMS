package com.example.lab7.data;

import android.provider.BaseColumns;

public final class DBContract {
    public DBContract(){ }
    public static abstract class DBEntry implements BaseColumns{
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_PRIORITY = "priority";
        public static final String COLUMN_NAME_DIFFICULTY = "difficulty";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_IMAGE_PATH = "imagePath";
        public static final String COLUMN_NAME_STATUS = "status";
    }
}



