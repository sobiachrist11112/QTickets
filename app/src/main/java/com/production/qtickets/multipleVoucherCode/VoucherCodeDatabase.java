package com.production.qtickets.multipleVoucherCode;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {VoucherCode.class},version = 1, exportSchema = false)
public abstract class VoucherCodeDatabase extends RoomDatabase{

    public abstract MyDao myDao();

}
