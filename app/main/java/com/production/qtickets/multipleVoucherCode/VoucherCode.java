package com.production.qtickets.multipleVoucherCode;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "voucher_codes")
public class VoucherCode {


    @PrimaryKey
    private int id;

    @ColumnInfo(name = "voucher_code")
    private String code;

    @ColumnInfo(name = "voucher_value")
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
