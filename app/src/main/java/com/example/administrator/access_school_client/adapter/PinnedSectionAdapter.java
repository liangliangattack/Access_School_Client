package com.example.administrator.access_school_client.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.Util.CommonAdapter;
import com.example.administrator.access_school_client.Util.CommonViewHolder;
import com.example.administrator.access_school_client.bean.CostRecordBean;
import com.example.administrator.access_school_client.widget.PinnedSectionListView;

import java.text.DecimalFormat;
import java.util.List;

public class PinnedSectionAdapter extends CommonAdapter<CostRecordBean> implements PinnedSectionListView.PinnedSectionListAdapter {

    private List<CostRecordBean> list;
    private ImageView ivType;
    private TextView tvType, tvDegrees, tvTotalPrice, tvDateMonth, tvTotalCost;
    private static final double WATER_PRICE = 100, ELEC_PRICE = 100;
    private List<Integer> layoutIds;

    public PinnedSectionAdapter(Context context, List<CostRecordBean> list, int layoutId) {
        super(context, list, layoutId);
        this.list = list;
    }

    public PinnedSectionAdapter(Context context, List<CostRecordBean> list, List<Integer> layoutIds) {
        super(context, list, layoutIds);
        this.list = list;
    }

    public void convert(CommonViewHolder commonViewHolder, int viewType, int position) {
        CostRecordBean costRecordBean = list.get(position);
        DecimalFormat df = new DecimalFormat("0.00");
        if (viewType == R.layout.listitem_water_elec_rent_no_pinned) {
            ivType = commonViewHolder.getView(R.id.iv_type);
            tvType = commonViewHolder.getView(R.id.tv_type);
            tvDegrees = commonViewHolder.getView(R.id.degrees);
            tvTotalPrice = commonViewHolder.getView(R.id.total_price);
            if (costRecordBean.getCostType() == 1) {
                ivType.setBackgroundResource(R.drawable.water);
                tvType.setText("水费");
                Log.i("waterPrice",String.valueOf(costRecordBean.getDegrees() * WATER_PRICE));
                tvTotalPrice.setText(df.format(costRecordBean.getDegrees() * WATER_PRICE) + "元");
            } else {
                ivType.setBackgroundResource(R.drawable.elec);
                tvType.setText("电费");
                tvDegrees.setText(df.format(costRecordBean.getDegrees()) + "度");
                tvTotalPrice.setText(df.format(costRecordBean.getDegrees() * WATER_PRICE) + "元");
            }
        } else {
            tvDateMonth = commonViewHolder.getView(R.id.tv_date_month);
            tvTotalCost = commonViewHolder.getView(R.id.tv_total_cost);
            tvDateMonth.setText(String.valueOf(costRecordBean.getMonth()) + " 月");
            tvTotalCost.setText(df.format(list.get(position + 1).getDegrees() * WATER_PRICE + list.get(position + 2).getDegrees() * ELEC_PRICE) + "元");
        }
    }

    public int getViewTypeCount() {
        return 2;
    }

    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }

    public int getItemLayoutIdForViewType(int position) {
        if (list.get(position).getViewType() == 1) {
            return R.layout.listitem_water_elec_rent_pinned;
        } else {
            return R.layout.listitem_water_elec_rent_no_pinned;
        }
    }

    public boolean isItemViewTypePinned(int viewType) {
        if (viewType == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void updateDataAndView(List<CostRecordBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }
}
