package fpoly.thangldph47392.duanmau.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import fpoly.thangldph47392.duanmau.database.DbHelper;
import fpoly.thangldph47392.duanmau.models.ThanhVien;

public class DemoDB {
    private SQLiteDatabase db;
    private  ThanhVienDao thanhVienDao;
    public DemoDB(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        thanhVienDao = new ThanhVienDao(context);

    }

    public void thanhVien() {

        ThanhVien tv = new ThanhVien(1, "Nguyen Van A", "2001");
//        if (thanhVienDao.insert(tv) > 0) {
//            Log.i("//=====", "Them thanh cong");
//        } else {
//            Log.i("//=====", "Them that bai");
//        }

        Log.i("//=====", "Tong so thanhVien: " + thanhVienDao.getAll().size());
        tv = new ThanhVien(1, "Nguyen B", "2000");
        thanhVienDao.update(tv);
        Log.i("//=====", "Tong so thanhVien: " + thanhVienDao.getAll().size());
    }
}
