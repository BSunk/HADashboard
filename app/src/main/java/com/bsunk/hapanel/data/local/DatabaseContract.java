package com.bsunk.hapanel.data.local;

import android.provider.BaseColumns;

/**
 * Created by Bryan on 3/8/2017.
 */

 public class DatabaseContract {

    public static final class HAPanel implements BaseColumns {

        public static final String TABLE_NAME = "ha_panel";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_ENTITY_ID = "entity_id";
        public static final String COLUMN_ATTRIBUTES = "attributes";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_LAST_CHANGED = "last_changed";
        public static final String COLUMN_POSITION = "position";
        public static final String COLUMN_HIDE_FLAG = "hide_flag";
        public static final String COLUMN_NOTIFY_FLAG = "notify_flag";
        public static final String COLUMN_ALERT_FLAG = "alert_flag";

    }
}

