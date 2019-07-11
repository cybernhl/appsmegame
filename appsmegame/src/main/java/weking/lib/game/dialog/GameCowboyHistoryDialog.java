package weking.lib.game.dialog;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import weking.lib.game.R;
import weking.lib.game.api.GameCowboyHistoryApi;
import weking.lib.game.bean.GameCowboyHistoryRespond;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.utils.GameUtil;
import weking.lib.game.view.cardView.cowboy.CowboyLayout;
import weking.lib.rxretrofit.exception.ApiException;
import weking.lib.rxretrofit.http.HttpManager;
import weking.lib.rxretrofit.listener.HttpOnNextListener;


public class GameCowboyHistoryDialog extends BaseTipDialog {


    private Context mContext;
    private List<ArrayList<Integer>> list;

    ArrayList<Integer> poker1;
    ArrayList<Integer> poker2;
    ArrayList<Integer> poker3;
    private RecyclerView mRecyclerView;
    CommonAdapter adapter;
    private int mGameType;
    private TextView tv_scale_tian;
    private TextView tv_scale_di;
    private TextView tv_scale_ren;


    public GameCowboyHistoryDialog(Context context, int gameType) {
        super(context);
        mContext = context;
        mGameType = gameType;

    }


    @Override
    public void show() {
        super.show();
        GameUtil.setDialogWidth(this);
    }
    @Override
    protected int getContentView() {
        return R.layout.dialog_game_cowboy_banker_history_list;
    }

    @Override
    protected void doBusiness() {
        initView();
        list = new ArrayList<>();
        poker1 = new ArrayList<>();
        poker2 = new ArrayList<>();
        poker3 = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        tv_scale_tian = (TextView) findViewById(R.id.tv_scale_tian);
        tv_scale_di = (TextView) findViewById(R.id.tv_scale_di);
        tv_scale_ren = (TextView) findViewById(R.id.tv_scale_ren);
        initView();
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    private void initView() {

        adapter = new CommonAdapter<ArrayList<Integer>>(getContext(), R.layout.dialog_game_cowboy_banker_history_itme, list) {
            @Override
            protected void convert(ViewHolder viewHolder, ArrayList<Integer> bean, int i) {
                if (list == null || list.size() == 0) {
                    return;
                }
                if (i == 0) {
                    viewHolder.setVisible(R.id.tv_news, true);
                } else {
                    viewHolder.setVisible(R.id.tv_news, false);
                }
                ImageView ivTian = (ImageView) viewHolder.getView(R.id.iv_tian);
                ImageView ivDi = (ImageView) viewHolder.getView(R.id.iv_di);
                ImageView ivRen = (ImageView) viewHolder.getView(R.id.iv_ren);
                if (bean.get(0) == CowboyLayout.KAIPAIWIN) {
                    ivTian.setImageResource(R.drawable.game_cowboy_history_win);
                } else {
                    ivTian.setImageResource(R.drawable.game_cowboy_history_lose);
                }
                if (bean.get(1) == CowboyLayout.KAIPAIWIN) {
                    ivDi.setImageResource(R.drawable.game_cowboy_history_win);
                } else {
                    ivDi.setImageResource(R.drawable.game_cowboy_history_lose);
                }
                if (bean.get(2) == CowboyLayout.KAIPAIWIN) {
                    ivRen.setImageResource(R.drawable.game_cowboy_history_win);
                } else {
                    ivRen.setImageResource(R.drawable.game_cowboy_history_lose);
                }
            }
        };
    }


    public void getHistory() {
        GameCowboyHistoryApi gameHistoryApi = new GameCowboyHistoryApi();
        gameHistoryApi.setAccess_token(GameUtil.getAccessToken());
        gameHistoryApi.setLive_id(GameUtil.getRoomLiveId());
        HttpManager httpManager = new HttpManager(new HttpOnNextListener<GameCowboyHistoryRespond>() {
            @Override
            public void onNext(GameCowboyHistoryRespond o, String method) {
                if (list == null || o == null || o.getList() == null) {
                    return;
                }
                if (o.getCode() == 0) {
                    list.clear();
                    list.addAll(formatList(o.getList()));
                    adapter.notifyDataSetChanged();
                    setPokerScale();
                }
            }

            @Override
            public void onError(ApiException e) {
                e.printStackTrace();
            }
        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
        httpManager.doHttpDeal(gameHistoryApi);

    }

    /**
     * 设置牌的胜率
     */
    private void setPokerScale() {
        tv_scale_tian.setText(getPokerScale(poker1));
        tv_scale_di.setText(getPokerScale(poker2));
        tv_scale_ren.setText(getPokerScale(poker3));
    }

    /**
     * 获取牌的胜率
     *
     * @param list
     * @return
     */
    private String getPokerScale(ArrayList<Integer> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        int index = 0;
        for (Integer scale : list) {
            if (scale == CowboyLayout.KAIPAIWIN) {
                index++;
            }
        }
        int i = index * 100 / list.size();
        return i + "%";
    }

    /**
     * 数据转换
     *
     * @param list
     * @return
     */
    private List<ArrayList<Integer>> formatList(List<String> list) {
        List<ArrayList<Integer>> rList = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return rList;
        }
        poker1.clear();
        poker2.clear();
        poker3.clear();
        for (String srt : list) {
            String[] split = srt.split("_");
            ArrayList<Integer> itmeList = new ArrayList<Integer>();
            for (int i = 0; i < split.length; i++) {
                if (i == 0) {
                    poker1.add(Integer.valueOf(split[i]).intValue());
                } else if (i == 1) {
                    poker2.add(Integer.valueOf(split[i]).intValue());
                } else if (i == 2) {
                    poker3.add(Integer.valueOf(split[i]).intValue());
                }
                itmeList.add(Integer.valueOf(split[i]).intValue());
            }
            rList.add(itmeList);
        }
        return rList;
    }


}
