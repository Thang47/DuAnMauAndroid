package fpoly.thangldph47392.duanmau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;

import fpoly.thangldph47392.duanmau.R;
import fpoly.thangldph47392.duanmau.adapter.TopAdapter;
import fpoly.thangldph47392.duanmau.dao.ThongKeDao;
import fpoly.thangldph47392.duanmau.models.Top;

public class TopFragment extends Fragment {
    private ListView lvTop;
    private List<Top> list;
    private TopAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_top, container, false);
        lvTop = v.findViewById(R.id.lvTop);
        capNhatLV();
        return v;
    }

    void capNhatLV() {
        ThongKeDao thongKeDao = new ThongKeDao(getActivity());
        list = (List<Top>) thongKeDao.getTop();
        adapter = new TopAdapter(getActivity(), this, list);
        lvTop.setAdapter(adapter);
    }

}
