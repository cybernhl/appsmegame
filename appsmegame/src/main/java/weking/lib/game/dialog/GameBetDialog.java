package weking.lib.game.dialog;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import weking.lib.game.R;
import weking.lib.game.GameC;
import weking.lib.game.api.GetBetListApi;
import weking.lib.game.bean.GameBetListRespond;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.utils.GameUtil;
import weking.lib.rxretrofit.exception.ApiException;
import weking.lib.rxretrofit.http.HttpManager;
import weking.lib.rxretrofit.listener.HttpOnNextListener;


public class GameBetDialog extends BaseTipDialog {


    private Context mContext;

    private LinearLayout headLayout;

    private RecyclerView mRecyclerView;
    private CommonAdapter<GameBetListRespond.GameBet> mAdapter;
    private final String mContText;
    private final String game_gold;
    private final ForegroundColorSpan mIm_name_corol;


    public GameBetDialog(Context context) {
        super(context);
        mContext = context;
        mContText = context.getResources().getString(R.string.game_bet);
        game_gold = GameObserver.getAppObserver().getStringText(GameC.str.game_gold);
        mIm_name_corol = new ForegroundColorSpan(context.getResources().getColor(io.weking.anim.R.color.im_name));
    }

    List<GameBetListRespond.GameBet> mDatas = new ArrayList<>();


    @Override
    protected void doBusiness() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        headLayout = (LinearLayout) findViewById(R.id.head);

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new CommonAdapter<GameBetListRespond.GameBet>(getContext().getApplicationContext()
                , R.layout.item_gaem_bet, mDatas) {
            @Override
            protected void convert(ViewHolder holder, GameBetListRespond.GameBet baen, int position) {

                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_bet_text = holder.getView(R.id.tv_bet_text);
                ImageView iv_bet = holder.getView(R.id.iv_bet);
                ImageView iv_head = holder.getView(R.id.iv_head);
                GameObserver.getAppObserver().loaderImage(iv_head, baen.pic_head_low, 0);
                tv_name.setText(baen.nickname);
//
                String bet_num = mContText + " " + baen.bet_num + " " + game_gold;
//                SpannableStringBuilder builderBottom = new SpannableStringBuilder(bet_num);
//
//                builderBottom.setSpan(mIm_name_corol, mContText.length(), mContText.length() + (baen.bet_num + "").length(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//                ;
                tv_bet_text.setText(bet_num);
                switch (position) {
                    case 0:
                        iv_bet.setVisibility(View.VISIBLE);
                        iv_bet.setImageResource(R.drawable.game_bet1);
                        break;
                    case 1:
                        iv_bet.setVisibility(View.VISIBLE);
                        iv_bet.setImageResource(R.drawable.game_bet2);
                        break;
                    case 2:
                        iv_bet.setImageResource(R.drawable.game_bet3);
                        iv_bet.setVisibility(View.VISIBLE);
                        break;
                    default:
                        iv_bet.setVisibility(View.GONE);

                }

            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }


    protected int getContentView() {
        return R.layout.game_bet_dialog;
    }

   /* public void getHistory() {

        GetBetListApi gameHistoryApi = new GetBetListApi();
        gameHistoryApi.setAccess_token(SpUtil.getStringValue(C.sp.ACCESS_TOKEN, ""));
        gameHistoryApi.setLive_id(C.IN_ROOM_LIVE_ID);
        HttpManager httpManager = new HttpManager(new HttpOnNextListener<GameHistoryRespond>() {
            @Override
            public void onNext(GameHistoryRespond o, String method) {
                adapter.setData(o.getList());
            }

            @Override
            public void onError(ApiException e) {
                e.printStackTrace();
            }
        }, (RxAppCompatActivity) App.getAppContext().getCurrentActivity());
        httpManager.doHttpDeal(gameHistoryApi);

    }
*/

    /**
     * 获取竞猜榜
     */

    public void getHistory() {
        GetBetListApi api = new GetBetListApi();
        api.setAccess_token(GameUtil.getAccessToken());
        api.setLive_id(GameUtil.getRoomLiveId());
        HttpManager httpManager = new HttpManager(new HttpOnNextListener<GameBetListRespond>() {
            @Override
            public void onNext(GameBetListRespond result, String method) {
                if (result.getCode() == 0) {
                    GameBetDialog.this.mDatas.clear();
                    GameBetDialog.this.mDatas.addAll(result.list);
                    GameBetDialog.this.mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onError(ApiException e) {
                e.printStackTrace();
            }
        }, GameUtil.getAppCompatActivity());
        httpManager.doHttpDeal(api);
    }
}

