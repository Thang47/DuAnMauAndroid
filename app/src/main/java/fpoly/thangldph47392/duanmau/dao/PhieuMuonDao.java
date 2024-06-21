package fpoly.thangldph47392.duanmau.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fpoly.thangldph47392.duanmau.database.DbHelper;
import fpoly.thangldph47392.duanmau.models.PhieuMuon;

public class PhieuMuonDao {

    private SQLiteDatabase db;
    private Context context;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat stf = new SimpleDateFormat("HH;mm");
    public PhieuMuonDao() {
    }

    public PhieuMuonDao(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper (context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("maTT", phieuMuon.getMaTT());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("ngay", sdf.format(phieuMuon.getNgay()));
        values.put("gioMuonSach", stf.format(phieuMuon.getGioMuonSach()));
        values.put("giaThue", phieuMuon.getTienThue());
        values.put("traSach", phieuMuon.getTraSach());

        return db.insert("PHIEUMUON", null, values);
    }

    public int update(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("maTT", phieuMuon.getMaTT());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("ngay", sdf.format(phieuMuon.getNgay()));
        values.put("gioMuonSach", stf.format(phieuMuon.getGioMuonSach()));
        values.put("giaThue", phieuMuon.getTienThue());
        values.put("traSach", phieuMuon.getTraSach());

        return db.update("PHIEUMUON", values, "maPM=?", new String[]{String.valueOf(phieuMuon.getMaPM())});
    }

    public int delete(String ID) {
        return db.delete("PHIEUMUON", "maPM = ?", new String[]{String.valueOf(ID)});
    }

    @SuppressLint("Range")
    public List<PhieuMuon> getData(String sql, String...selectionArgs) {
        List<PhieuMuon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);

        while(c.moveToNext()) {
            PhieuMuon phieuMuon = new PhieuMuon();
            phieuMuon.setMaPM(Integer.parseInt(c.getString(c.getColumnIndex("maPM"))));
            phieuMuon.setMaTT(c.getString(c.getColumnIndex("maTT")));
            phieuMuon.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex("maTV"))));
            phieuMuon.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("maSach"))));
            phieuMuon.setTraSach(Integer.parseInt(c.getString(c.getColumnIndex("traSach"))));
            phieuMuon.setTienThue(Integer.parseInt(c.getString(c.getColumnIndex("giaThue"))));
            try {
                phieuMuon.setNgay(sdf.parse(c.getString(c.getColumnIndex("ngay"))));
                phieuMuon.setGioMuonSach(stf.parse(c.getString(c.getColumnIndex("gioMuonSach"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            list.add(phieuMuon);
        }
        return list;
    }

    public List<PhieuMuon> getAll() {
        String sql = "select * from PHIEUMUON";
        return getData(sql);
    }

    public PhieuMuon getID(String ID) {
        String sql = "select * from PHIEUMUON where maPM = ?";
        List<PhieuMuon> list = getData(sql, ID);
        return list.get(0);
    }

}
