 package weking.lib.game.adapter;

 import android.support.v7.widget.RecyclerView;
 import android.view.View;

 import weking.lib.game.R;

 public class ZJHRecordListAdapter
   extends BaseRecordListAdapter
 {
   public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
   {
     if ((holder instanceof BaseRecordListAdapter.ItemViewHolder))
     {
       BaseRecordListAdapter.ItemViewHolder viewHolder = (BaseRecordListAdapter.ItemViewHolder)holder;
       int id = ((Integer)this.mList.get(position)).intValue();
       if (position == 0) {
         viewHolder.iv_new.setVisibility(View.VISIBLE);
       } else {
         viewHolder.iv_new.setVisibility(View.GONE);
       }
       switch (id)
       {
       case 1:
         viewHolder.tv_left.setText(R.string.game_win);
         viewHolder.tv_middle.setText(R.string.game_lose);
         viewHolder.tv_right.setText(R.string.game_lose);
         viewHolder.tv_left.setBackgroundResource(R.drawable.game_win_log);
         viewHolder.tv_middle.setBackgroundResource(R.drawable.game_lose_log);
         viewHolder.tv_right.setBackgroundResource(R.drawable.game_lose_log);
         break;
       case 2:
         viewHolder.tv_left.setText(R.string.game_lose);
         viewHolder.tv_middle.setText(R.string.game_win);
         viewHolder.tv_right.setText(R.string.game_lose);
         viewHolder.tv_left.setBackgroundResource(R.drawable.game_lose_log);
         viewHolder.tv_middle.setBackgroundResource(R.drawable.game_win_log);
         viewHolder.tv_right.setBackgroundResource(R.drawable.game_lose_log);
         break;
       case 3:
         viewHolder.tv_left.setText(R.string.game_lose);
         viewHolder.tv_middle.setText(R.string.game_lose);
         viewHolder.tv_right.setText(R.string.game_win);
         viewHolder.tv_left.setBackgroundResource(R.drawable.game_lose_log);
         viewHolder.tv_middle.setBackgroundResource(R.drawable.game_lose_log);
         viewHolder.tv_right.setBackgroundResource(R.drawable.game_win_log);
       }
     }
   }
 }
