package com.production.qtickets.multipleVoucherCode;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void addVoucherCode(VoucherCode voucherCode);

    @Query("select * from voucher_codes")
    public List<VoucherCode> getVoucherCodes();

    @Delete
    public void deleteVoucherCode(VoucherCode voucherCode);
}
