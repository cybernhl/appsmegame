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

public class CowboyRecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if ((holder instanceof ItemViewHolder)) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            String idS = ((String) this.mList.get(position));
            if (position == 0) {
                viewHolder.iv_new.setVisibility(View.VISIBLE);
            } else {
                viewHolder.iv_new.setVisibility(View.GONE);
            }
            String[] split = idS.split("_");
            for (int i = 0; i < split.length; i++) {
                int id = Integer.valueOf(split[i]).intValue();
                switch (i + 1) {
                    case 1:
                        if (id == 1) {
                            viewHolder.tv_middle.setVisibility(View.VISIBLE);
                            viewHolder.tv_left.setText(R.string.game_win);
                            viewHolder.tv_left.setBackgroundResource(R.drawable.game_win_log);
                        } else {
                            viewHolder.tv_middle.setVisibility(View.VISIBLE);
                            viewHolder.tv_left.setText(R.string.game_lose);
                             viewHolder.tv_left.setBackgroundResource(R.drawable.game_lose_log);
                        }
                        break;
                    case 2:
                        if (id == 1) {
                            viewHolder.tv_middle.setVisibility(View.VISIBLE);
                            viewHolder.tv_middle.setText(R.string.game_win);
                            viewHolder.tv_middle.setBackgroundResource(R.drawable.game_win_log);
                        } else {
                            viewHolder.tv_middle.setVisibility(View.VISIBLE);
                            viewHolder.tv_middle.setText(R.string.game_lose);
                            viewHolder.tv_middle.setBackgroundResource(R.drawable.game_lose_log);
                        }
                        break;
                    case 3:
                        if (id == 1) {
                            viewHolder.tv_middle.setVisibility(View.VISIBLE);
                            viewHolder.tv_right.setText(R.string.game_win);
                            viewHolder.tv_right.setBackgroundResource(R.drawable.game_win_log);
                        } else {
                            viewHolder.tv_middle.setVisibility(View.VISIBLE);
                            viewHolder.tv_right.setText(R.string.game_lose);
                            viewHolder.tv_right.setBackgroundResource(R.drawable.game_lose_log);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    protected List<String> mList = new ArrayList<>();


    private static final int TYPE_ITEM = 0;


    public void setData(List<String> list) {
        mList.clear();
        addData(list);
    }

    public void addData(List<String> list) {
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

