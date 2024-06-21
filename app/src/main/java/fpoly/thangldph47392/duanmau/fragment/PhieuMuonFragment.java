package fpoly.thangldph47392.duanmau.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fpoly.thangldph47392.duanmau.R;
import fpoly.thangldph47392.duanmau.adapter.PhieuMuonAdapter;
import fpoly.thangldph47392.duanmau.adapter.SachSpinnerAdapter;
import fpoly.thangldph47392.duanmau.adapter.ThanhVienSpinnerAdapter;
import fpoly.thangldph47392.duanmau.dao.PhieuMuonDao;
import fpoly.thangldph47392.duanmau.dao.SachDao;
import fpoly.thangldph47392.duanmau.dao.ThanhVienDao;
import fpoly.thangldph47392.duanmau.models.PhieuMuon;
import fpoly.thangldph47392.duanmau.models.Sach;
import fpoly.thangldph47392.duanmau.models.ThanhVien;

public class PhieuMuonFragment  extends Fragment {

    private ListView lvPM;
    private List<PhieuMuon> list;
    private Dialog dialog;
    private FloatingActionButton fab;
    private EditText edMaPM;
    private Spinner spTV, spSach;
    private TextView tvNgay, tvGio,  tvTienThue;
    private CheckBox chkTraSach;
    private Button btnSave, btnCancel;

    private PhieuMuonDao phieuMuonDao;
    private PhieuMuonAdapter adapter;
    private PhieuMuon item;

    private ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    private List<ThanhVien> thanhVienList;
    private ThanhVienDao thanhVienDao;
    private ThanhVien thanhVien;
    private int maThanhVien, positionTV;

    private SachSpinnerAdapter sachSpinnerAdapter;
    private List<Sach> sachList;
    private SachDao sachDao;
    private Sach sach;
    private int maSach, tienThue, positionSach;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat stf = new SimpleDateFormat("HH:mm");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        lvPM = v.findViewById(R.id.lvPM);
        fab = v.findViewById(R.id.fab);
        phieuMuonDao = new PhieuMuonDao(getActivity());
        capNhatLv();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });

        lvPM.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(), 1);
                return false;
            }
        });

        return v;
    }

    void capNhatLv() {
        list = (List<PhieuMuon>) phieuMuonDao.getAll();
        adapter = new PhieuMuonAdapter(getActivity(), this, list);
        lvPM.setAdapter(adapter);
    }

    protected void openDialog(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.phieu_muon_dialog);
        edMaPM = dialog.findViewById(R.id.edMaPM);
        spTV = dialog.findViewById(R.id.spTV);
        spSach = dialog.findViewById(R.id.spSach);
        tvNgay = dialog.findViewById(R.id.tvNgay);
        tvGio = dialog.findViewById(R.id.tvGio);
        tvTienThue = dialog.findViewById(R.id.tvTienThue);
        chkTraSach = dialog.findViewById(R.id.chkTraSach);
        btnCancel = dialog.findViewById(R.id.btnCancelPM);
        btnSave = dialog.findViewById(R.id.btnSavePM);
        tvNgay.setText("Ngày: " + sdf.format(new Date()));
        tvGio.setText("Giờ: " + stf.format(new Date()));
        thanhVienDao = new ThanhVienDao(context);
        thanhVienList = new ArrayList<>();
        thanhVienList = thanhVienDao.getAll();
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context, thanhVienList);
        spTV.setAdapter(thanhVienSpinnerAdapter);

        spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = thanhVienList.get(position).getMaTV();
                Toast.makeText(context, "Chọn " + thanhVienList.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sachDao = new SachDao(context);
        sachList = new ArrayList<>();
        sachList = sachDao.getAll();
        sachSpinnerAdapter = new SachSpinnerAdapter(context, sachList);
        spSach.setAdapter(sachSpinnerAdapter);

        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = sachList.get(position).getMaSach();
                tienThue = sachList.get(position).getGiaThue();
                tvTienThue.setText("Tiền thuê: " + tienThue);
                Toast.makeText(context, "Chọn " + sachList.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edMaPM.setEnabled(false);
        if (type == 1) {
            edMaPM.setText(String.valueOf(item.getMaPM()));

            for (int i = 0; i < thanhVienList.size(); i++) {
                if (item.getMaTV() == (thanhVienList.get(i).getMaTV())) {
                    positionTV = i;
                }
            }
            spTV.setSelection(positionTV);
            for (int i = 0; i < sachList.size(); i++) {
                if (item.getMaSach() == (sachList.get(i).getMaSach())) {
                    positionSach = i;
                }
            }
            spSach.setSelection(positionSach);

            tvNgay.setText("Ngày thuê: " + sdf.format(item.getNgay()));
            tvTienThue.setText("Tiền thuê: " + item.getTienThue());

            if (item.getTraSach() == 1) {
                chkTraSach.setChecked(true);
            } else {
                chkTraSach.setChecked(false);
            }
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new PhieuMuon();
                item.setMaTV(maThanhVien);
                item.setMaSach(maSach);
                item.setNgay(new Date());
                item.setGioMuonSach(new Date());
                item.setTienThue(tienThue);
                if (chkTraSach.isChecked()) {
                    item.setTraSach(1);
                } else {
                    item.setTraSach(0);
                }

                if (type == 0) {
                    if (phieuMuonDao.insert(item) > 0) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    item.setMaPM(Integer.parseInt(edMaPM.getText().toString()));
                    if (phieuMuonDao.update(item) > 0) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhatLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void xoa(final String ID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                phieuMuonDao.delete(ID);
                capNhatLv();
                dialog.cancel();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
