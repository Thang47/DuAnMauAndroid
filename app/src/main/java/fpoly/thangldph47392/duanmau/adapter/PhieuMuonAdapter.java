package fpoly.thangldph47392.duanmau.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;

import fpoly.thangldph47392.duanmau.R;
import fpoly.thangldph47392.duanmau.dao.SachDao;
import fpoly.thangldph47392.duanmau.dao.ThanhVienDao;
import fpoly.thangldph47392.duanmau.fragment.PhieuMuonFragment;
import fpoly.thangldph47392.duanmau.models.PhieuMuon;
import fpoly.thangldph47392.duanmau.models.Sach;
import fpoly.thangldph47392.duanmau.models.ThanhVien;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon>  {
    private Context context;
    private PhieuMuonFragment fragment;
    private List<PhieuMuon> list;

    private TextView tvMaPM, tvTenTV, tvTenSach, tvTienThue, tvNgay, tvTraSach;
    private ImageView imgDel;

    private SachDao sachDao;
    private ThanhVienDao thanhVienDao;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public PhieuMuonAdapter(@NonNull Context context, PhieuMuonFragment fragment, List<PhieuMuon> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.phieu_muon_item, null);
        }

        final PhieuMuon item = list.get(position);
        if (item != null) {
            tvMaPM = v.findViewById(R.id.tvMaPM);
            tvMaPM.setText("Mã Phiếu Mượn: " + item.getMaPM());

            thanhVienDao = new ThanhVienDao(context);
            ThanhVien thanhVien = thanhVienDao.getID(String.valueOf(item.getMaTV()));
            tvTenTV = v.findViewById(R.id.tvTenTV);
            tvTenTV.setText("Tên thành viên: " + thanhVien.getHoTen());

            sachDao = new SachDao(context);
            Sach sach = sachDao.getID(String.valueOf(item.getMaSach()));
            tvTenSach = v.findViewById(R.id.tvTenSach);
            tvTenSach.setText("Tên sách: " + sach.getTenSach());

            tvTienThue = v.findViewById(R.id.tvTienThue);
            tvTienThue.setText("Tiền thuê: " + item.getTienThue());

            tvNgay = v.findViewById(R.id.tvNgay);
            tvNgay.setText("Ngày thuê: " + sdf.format(item.getNgay()));

            tvTraSach = v.findViewById(R.id.tvTraSach);
            if (item.getTraSach() == 1) {
                tvTraSach.setText("Đã trả sách");
                tvTraSach.setTextColor(Color.BLUE);
            } else {
                tvTraSach.setText("Chưa trả sách");
                tvTraSach.setTextColor(Color.RED);
            }

            imgDel = v.findViewById(R.id.imgDeleteLS);
        }

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.getMaPM()));
            }
        });

        return v;
    }

}
