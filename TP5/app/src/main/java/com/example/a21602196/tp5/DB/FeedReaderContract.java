package com.example.a21602196.tp5.DB;

import android.provider.BaseColumns;

public class FeedReaderContract {
    private FeedReaderContract(){}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Annonces";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PRIX = "price";
        public static final String COLUMN_NAME_DESC = "description";
        public static final String COLUMN_NAME_PSEUDO = "pseudo";
        public static final String COLUMN_NAME_MAIL = "mail";
        public static final String COLUMN_NAME_TEL = "tel";
        public static final String COLUMN_NAME_VILLE = "ville";
        public static final String COLUMN_NAME_CP = "cp";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
