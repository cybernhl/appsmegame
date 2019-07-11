package weking.lib.game.presenter;

import java.util.List;

import weking.lib.game.bean.BetRespond;
import weking.lib.game.bean.GameKaiPaiPush;
import weking.lib.game.bean.XiaZhuPush;

/**
 * 创建时间 2017/3/24.
 * 创建人 zhengb
 * 功能描述 游戏的Contract
 */

public interface GameContract {
    public interface View {
        public void sendGiftResult(int my_diamonds);

        public void betResult(BetRespond betRespond);

        // 结束一局游戏（主播调用）
        void showKaiPaiPush(GameKaiPaiPush result);

        // 结束游戏 开始游戏 重新连接(主播调用)
        void showStartGameError();

        // 开始下一局游戏（主播调用）
        void showXiaZhuPush(XiaZhuPush result);

        // 开牌（主播调用）
        void showendGameError();

        // 获取赛车数据(主播调用)
        void showCarDataError();


        public abstract void showFireResult(boolean paramBoolean, int paramInt);

        public abstract void showFireError();

        public abstract void startGameDollData(List<List<XiaZhuPush.GameBaen>> paramList);

        public abstract void startGamestar_warsData(List<XiaZhuPush.GameBaen> paramList);

    }

    public interface Presenter {
        public void sendGift(int giftId, String topic, int giftType);

        public void bet(int live_id,
                        int position_id, int bet_num);

        public void startGame(int gameType);

        public void endGame(int gameType);

        public void getCarResult();

//        void joinGame(int live_id, int gameType, GameAction.One l);

        public abstract void fire(boolean paramBoolean, int paramInt1, int paramInt2);


        
    }
}
