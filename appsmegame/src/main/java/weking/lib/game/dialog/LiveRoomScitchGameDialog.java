package weking.lib.game.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import weking.lib.game.R;
import weking.lib.game.adapter.SwitchGameAdater;
import weking.lib.game.event.ToolBarSwitchGameEvent;
import weking.lib.game.manager.memory.GameMemory;
import weking.lib.game.observer.GameObserver;

/**
 * Created by Administrator on 2017/8/14.
 */

public class LiveRoomScitchGameDialog extends DialogFragment implements View.OnClickListener {


    ViewPager vp_game;
    TextView tv_game_name;
    TextView tv_begin_game;
    TextView tv_game_introduce;
    private View mView;
    private int gamePos;
    private ArrayList<Integer> mList = new ArrayList<>();
    private ArrayList<Integer> mLive_list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.LoadingCenterDialogStyle);

    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        DisplayMetrics dm = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setLayout(dm.widthPixels - 80, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setAttributes(layoutParams);
        vp_game.setCurrentItem(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_room_select_game, null);
        this.mView = view;
        initView();
        loadData();
        initCardView();
        initViewPager();
        return view;
    }

    private void initViewPager() {

        tv_game_name.setText(nameRers[mLive_list.get(0)]);
        tv_game_introduce.setText(introduceRers[mLive_list.get(0)]);
        vp_game.setAdapter(new SwitchGameAdater(getContext(), mList));
        vp_game.setOffscreenPageLimit(mList.size());
//        vp_game.setPageTransformer(true, new AlphaTransformer());

        vp_game.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                gamePos = position;
            }

            @Override
            public void onPageSelected(int position) {
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    try {
                        String name = nameRers[mLive_list.get(gamePos)];
                        tv_game_name.setText(name);
                        tv_game_introduce.setText(introduceRers[mLive_list.get(gamePos)]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    int[] imageRers = {
            R.drawable.game_switch_live,
            R.drawable.game_switch_zjh,
            R.drawable.game_switch_dezhou,
            R.drawable.game_switch_niuniu,
            R.drawable.game_switch_zww,
            R.drawable.game_switch_dfj,
            R.drawable.game_switch_cowboy};
    String[] nameRers = {
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_name_live),
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_name_zjh),
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_name_dezhou),
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_name_niuniu),
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_name_zww),
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_name_dfj),
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_name_cowboy)};

    String[] introduceRers = {
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_introduce_live),
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_introduce_zjh),
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_introduce_dezhou),
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_introduce_niuniu),
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_introduce_zww),
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_introduce_dfj),
            GameObserver.getAppObserver().getApp().getString(R.string.game_switch_introduce_cowboy)};


    private void initCardView() {
        try {
            mList.clear();
            for (int i = 0; i < mLive_list.size(); i++) {

                mList.add(imageRers[mLive_list.get(i)]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadData() {
        List<Integer> list = GameMemory.getmSwitchGameList();
        if (list != null) {
            mLive_list.clear();
            mLive_list.addAll(list);
        }
        mLive_list.remove(mLive_list.indexOf(GameMemory.nowGameType));
    }

    private void initView() {
        vp_game = (ViewPager) mView.findViewById(R.id.vp_game);
        tv_game_name = (TextView) mView.findViewById(R.id.tv_game_name);
        tv_begin_game = (TextView) mView.findViewById(R.id.tv_begin_game);
        tv_game_introduce = (TextView) mView.findViewById(R.id.tv_game_introduce);
        tv_begin_game.setOnClickListener(this);
        mView.findViewById(R.id.iv_close).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_begin_game) {
            EventBus.getDefault().post(new ToolBarSwitchGameEvent(mLive_list.get(gamePos)));
            dismiss();

        } else if (i == R.id.iv_close) {
            dismiss();
        }
    }
}
