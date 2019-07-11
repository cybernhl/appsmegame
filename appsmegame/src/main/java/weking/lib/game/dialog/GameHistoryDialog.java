package weking.lib.game.dialog;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.adapter.BaseRecordListAdapter;
import weking.lib.game.adapter.DZRecordListAdapter;
import weking.lib.game.adapter.ZJHRecordListAdapter;
import weking.lib.game.api.GameHistoryApi;
import weking.lib.game.bean.GameHistoryRespond;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.utils.GameUtil;
import weking.lib.rxretrofit.exception.ApiException;
import weking.lib.rxretrofit.http.HttpManager;
import weking.lib.rxretrofit.listener.HttpOnNextListener;
import weking.lib.utils.LogUtils;

import static weking.lib.game.GameC.Game.GAME_TYPE_CRAZYCAR;
import static weking.lib.game.GameC.Game.GAME_TYPE_DFJ;
import static weking.lib.game.GameC.Game.GAME_TYPE_NIUNIU;
import static weking.lib.game.GameC.Game.GAME_TYPE_TTDZ;
import static weking.lib.game.GameC.Game.GAME_TYPE_ZJH;
import static weking.lib.game.GameC.Game.GAME_TYPE_ZWW;


public class GameHistoryDialog extends BaseTipDialog {


    private Context mContext;

    private LinearLayout headLayout;

    private RecyclerView mRecyclerView;
    BaseRecordListAdapter adapter;
    private int mGameType;

    public GameHistoryDialog(Context context, int gameType) {
        super(context);
        mContext = context;
        mGameType = gameType;
    }

    @Override
    protected void doBusiness() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        headLayout = (LinearLayout) findViewById(R.id.head);
        initView();
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        if(mGameType == GameC.Game.GAME_TYPE_CRAZYCAR){
            findViewById(R.id.title_layout).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.wk_dialog_head_appsme_background));
        }

        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

    }

    private void initView() {
        switch (mGameType) {
            case GAME_TYPE_ZJH:
                View headZJH = LayoutInflater.from(mContext).inflate(
                        R.layout.game_history_head_zjh, null);
                headLayout.addView(headZJH);
                adapter = new ZJHRecordListAdapter();
                break;
            case GAME_TYPE_TTDZ:
                View headDZ = LayoutInflater.from(mContext).inflate(
                        R.layout.game_history_head_dz, null);
                headLayout.addView(headDZ);
                adapter = new DZRecordListAdapter();
                break;
            case GAME_TYPE_NIUNIU:
                View headNiuNiu = LayoutInflater.from(mContext).inflate(
                        R.layout.game_history_head_zjh, null);
                headLayout.addView(headNiuNiu);
                adapter = new ZJHRecordListAdapter();
                break;
            case GAME_TYPE_ZWW:
                break;
            case GAME_TYPE_DFJ:
                break;
            case GAME_TYPE_CRAZYCAR:
                View headCAR = LayoutInflater.from(mContext).inflate(
                        R.layout.game_history_head_car, null);
                headLayout.addView(headCAR);
                adapter = new ZJHRecordListAdapter();
            default:
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.game_history;
    }

    public void getHistory() {

        GameHistoryApi gameHistoryApi = new GameHistoryApi();
        gameHistoryApi.setAccess_token(GameUtil.getAccessToken());
        gameHistoryApi.setLive_id(GameUtil.getRoomLiveId());
        HttpManager httpManager = new HttpManager(new HttpOnNextListener<GameHistoryRespond>() {
            @Override
            public void onNext(GameHistoryRespond o, String method) {
                LogUtils.d("胜负榜    "+o.getList().size());
                adapter.setData(o.getList());
            }

            @Override
            public void onError(ApiException e) {
                e.printStackTrace();
            }
        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
        httpManager.doHttpDeal(gameHistoryApi);

    }


}
