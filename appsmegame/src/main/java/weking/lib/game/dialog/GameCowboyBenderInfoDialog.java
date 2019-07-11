package weking.lib.game.dialog;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.api.BankerWinInfoApi;
import weking.lib.game.bean.BankerWinInfo;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.utils.GameUtil;
import weking.lib.rxretrofit.exception.ApiException;
import weking.lib.rxretrofit.http.HttpManager;
import weking.lib.rxretrofit.listener.HttpOnNextListener;

/**
 * 庄家流水
 */

public class GameCowboyBenderInfoDialog extends BaseTipDialog {


    private Context mContext;

    private ArrayList<BankerWinInfo.WinInfo> mBanberList;

    private RecyclerView mRecyclerView;
    CommonAdapter adapter;

    /**
     * 请求上下庄
     */
    private TextView mTv_banker;
    /**
     * 庄家的钱
     */
    private TextView tv_banker_moeny;
    /**
     * 是否上庄
     */
    private boolean isApplyBanker;

    @Override
    public void show() {
        super.show();
        GameUtil.setDialogWidth(this);
    }
    public GameCowboyBenderInfoDialog(Context context) {
        super(context);
        mContext = context;
        mBanberList = new ArrayList<>();
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_game_cowboy_banker_wininfo_list;
    }

    @Override
    protected void doBusiness() {

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mTv_banker = (TextView) findViewById(R.id.tv_banker);
        tv_banker_moeny = (TextView) findViewById(R.id.tv_banker_moeny);
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

        adapter = new CommonAdapter<BankerWinInfo.WinInfo>(getContext(), R.layout.dialog_game_cowboy_banker_wininfo_itme, mBanberList) {
            @Override
            protected void convert(ViewHolder viewHolder, BankerWinInfo.WinInfo bean, int i) {
                if (mBanberList == null || mBanberList.size() == 0) {
                    return;
                }
                if (i == 0) {
                    viewHolder.setVisible(R.id.tv_news, true);
                } else {
                    viewHolder.setVisible(R.id.tv_news, false);
                }
                ((TextView) viewHolder.getView(R.id.tv_order)).setText(mBanberList.size() - i + "");
                ((TextView) viewHolder.getView(R.id.tv_moeny)).setText(GameUtil.formatBetText(bean.getWin_money()));
                ((TextView) viewHolder.getView(R.id.tv_type)).setText(GameUtil.getCowboyCradType(bean.getCard_type()));
            }
        };
    }


    /**
     * 上庄
     */
    public void getHistory() {
        BankerWinInfoApi bankerWinInfoApi = new BankerWinInfoApi();
        bankerWinInfoApi.setAccess_token(GameUtil.getAccessToken());
        bankerWinInfoApi.setLive_id(GameUtil.getRoomLiveId());
        HttpManager httpManager = new HttpManager(new HttpOnNextListener<BankerWinInfo>() {


            @Override
            public void onNext(BankerWinInfo o, String method) {
                if (o.getCode() == 0) {
                    mBanberList.clear();
                    mBanberList.addAll(o.getList());
                    adapter.notifyDataSetChanged();
                    setWinInfo(o.getList());
                }
            }

            @Override
            public void onError(ApiException e) {
                e.printStackTrace();
            }
        }, GameObserver.getAppObserver().getCurrentActivity());
        httpManager.doHttpDeal(bankerWinInfoApi);

    }

    /**
     * 获取 输赢总钱
     *
     * @param list
     */
    private void setWinInfo(ArrayList<BankerWinInfo.WinInfo> list) {
        int moeny = 0;
        for (BankerWinInfo.WinInfo info : list) {
            moeny += info.getWin_money();
        }
        String string = mContext.getString(R.string.game_cowboy_banker_info);
        String stringText = GameObserver.getAppObserver().getStringText(GameC.str.game_gold);
        tv_banker_moeny.setText(string
                + stringText + ":" + GameUtil.formatBetText(moeny));
    }
}
