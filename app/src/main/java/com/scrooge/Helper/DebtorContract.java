package com.scrooge.Helper;

import android.provider.BaseColumns;

public final class DebtorContract {
    private DebtorContract(){}

    public static class DebtorColumns implements BaseColumns {
        public static final String TABLE_NAME = "debtor";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DEBT = "debt";
    }
}
