package weking.lib.game.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import weking.lib.game.R;


/**
 * Created by fengrs on 2016/8/26.
 */
public class SwitchGameAdater extends PagerAdapter {
    private List<Integer> list;
    private Context context;
    private LayoutInflater inflater;

    public SwitchGameAdater(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.game_switch_item, container, false);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        iv.setImageResource(list.get(position));
//        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
