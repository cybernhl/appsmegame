package weking.lib.game.presenter;

import java.util.List;

import weking.lib.game.bean.AllBetPush;
import weking.lib.game.bean.GameKaiPaiPush;
import weking.lib.game.bean.JoinGameRespond;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.manager.GameSound;

/**
 * Created by Administrator on 2017/1/4.
 */

public interface IGame  {

    //设置发消息房间id
    void setTopic(String topic);

    void startGame();

    void startGame(int gameType);

    /**
     * 下注阶段
     *
     * @param gameSound
     * @param xiaZhuPush
     */
    void doXiaZhu(GameSound gameSound, XiaZhuPush xiaZhuPush);

    /**
     * 显示下注总信息
     *
     * @param allBetPush
     */
    void setAllBetText(AllBetPush allBetPush);

    /**
     * 显示自己下注
     *
     * @param who    哪手牌
     * @param myBet  下多少注
     * @param allBet 这手牌总注
     */
    void setMyBetText(int who, int myBet, int allBet);

    /**
     * 显示一副牌的下注数
     *
     * @param who    哪手牌
     * @param number 自己的注
     * @param allBet 所有的注数
     */
    void setOneBetText(int who, int number, int allBet);

    /**
     * 开牌阶段
     *
     * @param gamePush
     * @param gameSound
     */
    void doKaiPai(GameKaiPaiPush gamePush, GameSound gameSound);

    /**
     * 显示我的钻石
     *
     * @param diamonds
     */
    void setMoneyTextView(int diamonds, long delayed);

    void sendGift(int giftId, int giftType);

    /**
     * 下注阶段，加入游戏
     *
     * @param joinGameRespond
     */
    void joinGameXiaZhu(JoinGameRespond joinGameRespond);

    /**
     * 开牌阶段，加入游戏
     *
     * @param gameRespond
     */
    void joinGameKaiPai(JoinGameRespond gameRespond);

    /**
     * Set the enabled state of this VIEW.
     *
     * @param visibility One of VISIBLE, INVISIBLE or #GONE.
     */
    void setVisibility(int visibility);


    int getVisibility();


    void startGameDollData(List<List<XiaZhuPush.GameBaen>> game_info);

    void startGamestar_warsData(List<XiaZhuPush.GameBaen> star_wars);


//    void joinGame(int live_id, int gameType, GameAction.One l);


}
