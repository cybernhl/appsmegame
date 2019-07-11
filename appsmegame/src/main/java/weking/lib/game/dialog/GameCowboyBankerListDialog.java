package weking.lib.game.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.api.ApplyBankerApi;
import weking.lib.game.api.CancelBankerApi;
import weking.lib.game.api.KanberListApi;
import weking.lib.game.bean.BanberListBean;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.utils.GameUtil;
import weking.lib.rxretrofit.api.BaseResultEntity;
import weking.lib.rxretrofit.exception.ApiException;
import weking.lib.rxretrofit.http.HttpManager;
import weking.lib.rxretrofit.listener.HttpOnNextListener;


public class GameCowboyBankerListDialog extends BaseTipDialog {


    private Context mContext;

    private List<BanberListBean.BanberBean> mBanberList;

    private RecyclerView mRecyclerView;
    CommonAdapter adapter;
    // 头颜色
    private final int mTitleColor;
    /**
     * 内容颜色
     */
    private final int mComColor;
    /**
     * 请求上下庄
     */
    private TextView mTv_banker;
    /**
     * 是否上庄
     */
    public boolean isApplyBanker;

    public GameCowboyBankerListDialog(Context context) {
        super(context);
        mContext = context;
        mTitleColor = ContextCompat.getColor(getContext(), R.color.colorWhite);
        mComColor = ContextCompat.getColor(getContext(), R.color.im_name);
        mBanberList = new ArrayList<>();
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_game_cowboy_banker_list;
    }

    @Override
    public void show() {
        super.show();
        if (isApplyBanker) {
            mTv_banker.setText(mContext.getString(R.string.game_cowboy_banker_request_cancel));
        } else {
            mTv_banker.setText(mContext.getString(R.string.game_cowboy_banker_request_apply));
        }
        GameUtil.setDialogWidth(this);
    }

    @Override
    protected void doBusiness() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mTv_banker = (TextView) findViewById(R.id.tv_banker);
        findViewById(R.id.rl_banker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isApplyBanker) {
                    cancelBanker();
                } else {
                    applyBanker();
                }

            }
        });

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

        adapter = new CommonAdapter<BanberListBean.BanberBean>(getContext(), R.layout.dialog_game_cowboy_banker_itme, mBanberList) {
            @Override
            protected void convert(ViewHolder viewHolder, BanberListBean.BanberBean bean, int i) {
                TextView tv_order = viewHolder.getView(R.id.tv_order);
                TextView tv_name = viewHolder.getView(R.id.tv_name);
                TextView tv_moeny = viewHolder.getView(R.id.tv_moeny);
                if (i == 1) {
                    tv_order.setTextColor(mComColor);
                    tv_name.setTextColor(mComColor);
                    tv_moeny.setTextColor(mComColor);
                } else {
                    tv_order.setTextColor(mTitleColor);
                    tv_name.setTextColor(mTitleColor);
                    tv_moeny.setTextColor(mTitleColor);
                }
                if (bean.isTitle()) {
                    tv_order.setText(getContext().getString(R.string.game_cowboy_banker_order));
                    tv_name.setText(getContext().getString(R.string.game_cowboy_banker_player));
                    tv_moeny.setText(GameObserver.getAppObserver().getStringText(GameC.str.game_gold));
                } else {
                    if (i == 1) {
                        tv_order.setText(getContext().getString(R.string.game_cowboy_banker_text));
                    } else {
                        tv_order.setText(i - 1 + "");
                    }
                    String money = GameUtil.formatBetText(bean.getTotal_diamond());
                    tv_moeny.setText(money);
                    tv_name.setText(bean.getNickname());
                }
            }
        };
    }

    public void getHistory() {
        KanberListApi gameHistoryApi = new KanberListApi();
        gameHistoryApi.setAccess_token(GameUtil.getAccessToken());
        gameHistoryApi.setLive_id(GameUtil.getRoomLiveId());
        HttpManager httpManager = new HttpManager(new HttpOnNextListener<BanberListBean>() {


            @Override
            public void onNext(BanberListBean o, String method) {

                mBanberList.clear();
                mBanberList.add(new BanberListBean.BanberBean(true));
                if (o.getCode() == 0 && o.getBanberList() != null) {
                    mBanberList.addAll(o.getBanberList());
                }
                adapter.notifyDataSetChanged();
//                mRecyclerView.notifyAll();
            }

            @Override
            public void onError(ApiException e) {
                e.printStackTrace();
                mBanberList.clear();
                mBanberList.add(new BanberListBean.BanberBean(true));
                adapter.notifyDataSetChanged();
            }
        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
        httpManager.doHttpDeal(gameHistoryApi);

    }

    /**
     * 下庄
     */
    public void cancelBanker() {
        CancelBankerApi gameHistoryApi = new CancelBankerApi();
        gameHistoryApi.setAccess_token(GameUtil.getAccessToken());
        gameHistoryApi.setLive_id(GameUtil.getRoomLiveId());
        HttpManager httpManager = new HttpManager(new HttpOnNextListener<BaseResultEntity>() {

            @Override
            public void onNext(BaseResultEntity o, String method) {
                if (o.getCode() == 0) {
                    isApplyBanker = false;
                    mTv_banker.setText(mContext.getString(R.string.game_cowboy_banker_request_apply));
                    dismiss();
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
     * 上庄
     */
    public void applyBanker() {
        ApplyBankerApi gameHistoryApi = new ApplyBankerApi();
        gameHistoryApi.setAccess_token(GameUtil.getAccessToken());
        gameHistoryApi.setLive_id(GameUtil.getRoomLiveId());
        HttpManager httpManager = new HttpManager(new HttpOnNextListener<BaseResultEntity>() {


            @Override
            public void onNext(BaseResultEntity o, String method) {
                if (o.getCode() == 0) {
                    isApplyBanker = true;
                    mTv_banker.setText(mContext.getString(R.string.game_cowboy_banker_request_cancel));
                    getHistory();
                }
            }

            @Override
            public void onError(ApiException e) {
                e.printStackTrace();
            }
        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
        httpManager.doHttpDeal(gameHistoryApi);

    }


}
