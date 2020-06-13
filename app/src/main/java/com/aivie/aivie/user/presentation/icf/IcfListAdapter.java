package com.aivie.aivie.user.presentation.icf;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.Constant;

import java.util.ArrayList;
import java.util.HashMap;

public class IcfListAdapter extends BaseAdapter {

    private ArrayList<HashMap> list;
    private Activity activity;

    IcfListAdapter(Activity activity, ArrayList<HashMap> list) {
        this.activity = activity;
        this.list = list;
    }

    private class ViewHolder {
        TextView tvDocId;
        TextView tvIsSigned;
        TextView tvSignedAt;
    }

    @Override
    public int getCount() {
        // If return 0, then getView will NOT be called.
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (view == null) {
            view = inflater.inflate(R.layout.list_view_row_icf, null);

            holder = new ViewHolder();
            holder.tvDocId = view.findViewById(R.id.textViewId);
            holder.tvIsSigned = view.findViewById(R.id.textViewIsSigned);
            holder.tvSignedAt = view.findViewById(R.id.textViewSignedAt);

            view.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) view.getTag();
        }

        HashMap map = list.get(i);
        holder.tvDocId.setText((String) map.get(Constant.FIRE_ICF_COLUMN_DOC_ID));
        holder.tvIsSigned.setText((String) map.get(Constant.FIRE_ICF_COLUMN_SIGNED));
        holder.tvSignedAt.setText((String) map.get(Constant.FIRE_ICF_COLUMN_SIGNED_DATE));

        return view;
    }
}
