package weking.lib.game.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import weking.lib.game.R;


public abstract class BaseRecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<Integer> mList = new ArrayList<>();


    private static final int TYPE_ITEM = 0;


    public void setData(List<Integer> list) {
        mList.clear();
        addData(list);
    }

    public void addData(List<Integer> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    // RecyclerView的count设置为数据总条数+ 1（footerView）
    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return TYPE_ITEM;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.game_item_game_history, null);
        return new ItemViewHolder(view);

    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_left;
        TextView tv_middle;
        TextView tv_right;
        ImageView iv_new;

        public ItemViewHolder(View view) {
            super(view);

            tv_left = (TextView) view.findViewById(R.id.tv_left);
            tv_middle = (TextView) view.findViewById(R.id.tv_middle);
            tv_right = (TextView) view.findViewById(R.id.tv_right);
            iv_new = (ImageView) view.findViewById(R.id.iv_new);
        }
    }
}