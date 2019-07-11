package weking.lib.game.presenter;//package weking.lib.game.presenter;
//
//import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
//
//import weking.lib.game.api.GameFireApi;
//import weking.lib.game.api.GameStartApi;
//import weking.lib.game.api.JoinGameApi;
//import weking.lib.game.bean.GameFireRespond;
//import weking.lib.game.bean.GameKaiPaiPush;
//import weking.lib.game.bean.XiaZhuPush;
//import weking.lib.game.observer.GameObserver;
//import weking.lib.game.utils.GameAction;
//import weking.lib.game.utils.GameUtil;
//import weking.lib.rxretrofit.exception.ApiException;
//import weking.lib.rxretrofit.http.HttpManager;
//import weking.lib.rxretrofit.listener.HttpOnNextListener;
//
//
//public class Game2Preserter
//        implements Game2Contract.Presenter {
//    private final Game2Contract.View mView;
//
//    public Game2Preserter(Game2Contract.View mView) {
//        this.mView = mView;
//    }
//
//    public void fire(boolean hit, int giftId, int capital) {
//        GameFireApi api = new GameFireApi();
//        api.setAccess_token(GameUtil.getAccessToken());
//        api.setLive_id(GameUtil.getRoomLiveId());
//        api.setHit(hit);
//        api.setId(giftId);
//        api.setCapital(capital);
//
//
//        HttpManager httpManager = new HttpManager(new HttpOnNextListener<GameFireRespond>() {
//            public void onNext(GameFireRespond result, String method) {
//                if (result.getCode() == 0) {
//                    Game2Preserter.this.mView.showFireResult(result.hit, result.my_diamonds);
//                } else {
//                    Game2Preserter.this.mView.showFireError();
//                }
//            }
//
//            public void onError(ApiException e) {
//                Game2Preserter.this.mView.showFireError();
//            }
//        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
//        httpManager.doHttpDeal(api);
//    }
//
//    @Override
//    public void joinGame(int live_id, int gameType, final GameAction.One l) {
//        JoinGameApi api = new JoinGameApi();
//        api.setAccess_token(GameUtil.getAccessToken());
//        api.setLive_id(GameUtil.getRoomLiveId());
//
//
//        HttpManager httpManager = new HttpManager(new HttpOnNextListener<GameKaiPaiPush>() {
//            public void onNext(GameKaiPaiPush result, String method) {
//                if (result.getCode() == 0) {
//                    l.invoke(result);
//                }
//            }
//
//            public void onError(ApiException e) {
//            }
//        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
//        httpManager.doHttpDeal(api);
//    }
//
//    public void startGame(int gameType) {
//        GameStartApi api = new GameStartApi();
//        api.setAccess_token(GameUtil.getAccessToken());
//        api.setLive_id(GameUtil.getRoomLiveId());
//        api.setGame_type(gameType);
//
//
//        HttpManager httpManager = new HttpManager(new HttpOnNextListener<XiaZhuPush>() {
//            public void onNext(XiaZhuPush result, String method) {
//                if (result.getCode() == 0) {
//                    if (result.getDoll_game() != null) {
//                        Game2Preserter.this.mView.startGameDollData(result.getDoll_game());
//                    } else if (result.getStar_wars() != null) {
//                        Game2Preserter.this.mView.startGamestar_warsData(result.getStar_wars());
//                    }
//                }
//            }
//
//            public void onError(ApiException e) {
//                e.printStackTrace();
//            }
//        }, (RxAppCompatActivity) GameObserver.getAppObserver().getCurrentActivity());
//        httpManager.doHttpDeal(api);
//    }
//}
//
//
