package weking.lib.game.presenter;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.api.CrazyCarDataApi;
import weking.lib.game.api.GameBetApi;
import weking.lib.game.api.GameEndApi;
import weking.lib.game.api.GameFireApi;
import weking.lib.game.api.GameStartApi;
import weking.lib.game.api.SwitchGameApi;
import weking.lib.game.bean.BetRespond;
import weking.lib.game.bean.CrazyCarResult;
import weking.lib.game.bean.GameFireRespond;
import weking.lib.game.bean.GameKaiPaiPush;
import weking.lib.game.bean.SendGiftResult;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.event.PublishCarDataEvent;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.utils.GameAction;
import weking.lib.game.utils.GameUtil;
import weking.lib.rxretrofit.api.BaseResultEntity;
import weking.lib.rxretrofit.exception.ApiException;
import weking.lib.rxretrofit.http.HttpManager;
import weking.lib.rxretrofit.listener.HttpOnNextListener;
import weking.lib.utils.LogUtils;
import weking.lib.utils.ToastUtil;

public class GamePresenter implements GameContract.Presenter{
    private GameContract.View mView;
    private String accessToken;
    private int game_id;

    public GamePresenter(GameContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void sendGift(int giftId, String topic, int giftType) {
        GameObserver.getAppObserver().sendGift(giftId, topic, giftType, new GameAction().new OnResult<SendGiftResult>() {
            @Override
            public void invoke(SendGiftResult result) {
                GamePresenter.this.mView.sendGiftResult(result.my_diamonds);
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    public void bet(int live_id, int position_id, final int bet_num) {
        GameBetApi api = new GameBetApi();
        api.setAccess_token(GameUtil.getAccessToken());
        api.setLive_id(live_id);
        api.setBet_num(bet_num);
        api.setPosition_id(position_id);


        HttpManager httpManager = new HttpManager(new HttpOnNextListener<BetRespond>() {
            @Override
            public void onNext(BetRespond result, String method) {
                if (result.getCode() == 0) {
                    String key = GameObserver.getAppObserver().getStringText(GameC.str.MY_DIAMONDS);
                    result.setCurrentBet(bet_num);
                    GameObserver.getAppObserver().setObject(key, Integer.valueOf(result.getMy_diamonds()));
                    GamePresenter.this.mView.betResult(result);
                }
            }

            @Override
            public void onError(ApiException e) {
                e.printStackTrace();
            }
        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
        httpManager.doHttpDeal(api);
    }

    @Override
    public void startGame(final int gameType) {
        GameStartApi api = new GameStartApi();
        api.setAccess_token(GameUtil.getAccessToken());
        api.setLive_id(GameUtil.getRoomLiveId());
        api.setGame_type(gameType);

        HttpManager httpManager = new HttpManager(new HttpOnNextListener<XiaZhuPush>() {
            @Override
            public void onNext(XiaZhuPush result, String method) {
                if (gameType == GameC.Game.GAME_TYPE_DFJ || gameType == GameC.Game.GAME_TYPE_ZWW) {
                    if (result.getCode() == 0) {
                        if (result.getDoll_game() != null) {
                            mView.startGameDollData(result.getDoll_game());
                        } else if (result.getStar_wars() != null) {
                            mView.startGamestar_warsData(result.getStar_wars());
                        }
                    }else {
                        GamePresenter.this.mView.showStartGameError();
                    }
                } else {  // 棋牌数据
                    if (result.getCode() == 0) {
                        GamePresenter.this.game_id = result.getGame_id();
                        GamePresenter.this.mView.showXiaZhuPush(result);
                    } else {
                        GamePresenter.this.mView.showStartGameError();
                    }
                }
            }

            @Override

            public void onError(ApiException e) {
                e.printStackTrace();
                GamePresenter.this.mView.showStartGameError();
            }
        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
        httpManager.doHttpDeal(api);
    }

    @Override
    public void endGame(int gameType) {


        GameEndApi   api = new GameEndApi();
            api.setAccess_token(GameUtil.getAccessToken());
            api.setLive_id(GameUtil.getRoomLiveId());
            api.setGame_id(this.game_id);
            api.setGame_type(gameType);


        HttpManager httpManager = new HttpManager(new HttpOnNextListener<GameKaiPaiPush>() {
            @Override
            public void onNext(GameKaiPaiPush result, String method) {
                if (result.getCode() == 0) {
                    LogUtils.d("赛车   结束游戏接口成功");
                    GamePresenter.this.mView.showKaiPaiPush(result);
                } else {
                    GamePresenter.this.mView.showendGameError();
                }
            }

            @Override
            public void onError(ApiException e) {
                GamePresenter.this.mView.showendGameError();
            }
        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());

            httpManager.doHttpDeal(api);

    }

    @Override
    public void getCarResult() {
        CrazyCarDataApi crazyCarDataApi = new CrazyCarDataApi();
        crazyCarDataApi.setAccess_token(GameUtil.getAccessToken());
        crazyCarDataApi.setLive_id(GameUtil.getRoomLiveId());
        HttpManager httpManager = new HttpManager(new HttpOnNextListener<CrazyCarResult>() {
            @Override
            public void onNext(CrazyCarResult result, String s) {
                if(result!=null && result.getCode() == 0){
                    EventBus.getDefault().post(new PublishCarDataEvent(result.getSpeed_data(),result.getWin_id()));
                }else {
                    GamePresenter.this.mView.showCarDataError();
                }
            }
            @Override
            public void onError(ApiException e) {
                GamePresenter.this.mView.showCarDataError();
            }
        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
        httpManager.doHttpDeal(crazyCarDataApi);

    }

    //
//    @Override
//    public void joinGame(int live_id, int gameType, final GameAction.One l) {
//        JoinGameApi api = new JoinGameApi();
//        api.setAccess_token(GameUtil.getAccessToken());
//        api.setLive_id(GameUtil.getRoomLiveId());
//
//
//        HttpManager httpManager = new HttpManager(new HttpOnNextListener<JoinGameRespond>() {
//            public void onNext(JoinGameRespond result, String method) {
//                if (result.getCode() == 0) {
//                    l.invoke(result);
//                }
//            }
//
//            public void onError(ApiException e) {
//                e.printStackTrace();
//            }
//        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
//        httpManager.doHttpDeal(api);
//    }

    @Override
    public void fire(boolean hit, int giftId, int capital) {
        GameFireApi api = new GameFireApi();
        api.setAccess_token(GameUtil.getAccessToken());
        api.setLive_id(GameUtil.getRoomLiveId());
        api.setHit(hit);
        api.setId(giftId);
        api.setCapital(capital);


        HttpManager httpManager = new HttpManager(new HttpOnNextListener<GameFireRespond>() {
            @Override
            public void onNext(GameFireRespond result, String method) {
                if (result.getCode() == 0) {
                    mView.showFireResult(result.hit, result.my_diamonds);
                } else {
                    mView.showFireError();
                }
            }

            @Override
            public void onError(ApiException e) {
                mView.showFireError();
            }
        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
        httpManager.doHttpDeal(api);
    }

    public static void switchGame(int gameType) {
        SwitchGameApi api = new SwitchGameApi();
        api.setAccess_token(GameUtil.getAccessToken());
        api.setLive_id(GameUtil.getRoomLiveId());
        api.setGame_type(gameType);

        HttpManager httpManager = new HttpManager(new HttpOnNextListener<BaseResultEntity>() {
            @Override
            public void onNext(BaseResultEntity result, String method) {
                if (result.getCode() == 0) {

                } else {
                    ToastUtil.show(R.string.game_switch_game_error);
                }
            }

            @Override
            public void onError(ApiException e) {
                e.printStackTrace();
                ToastUtil.show(R.string.game_switch_game_error);
            }
        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
        httpManager.doHttpDeal(api);
    }
}
