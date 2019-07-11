package weking.lib.game.view.cardView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.bean.GameItemInfo;
import weking.lib.game.bean.JoinGameRespond;
import weking.lib.game.bean.Poker;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.manager.GameSound;
import weking.lib.game.utils.GameUIUtils;
import weking.lib.game.utils.GameUtil;


public class NiuNiuLayout extends BaseCradView {
    private static final String TAG = "ZJHLayout";

    //    private View shadow_right;
    private ImageView shadow_left;
//    private View shadow_center;

    //  左边开牌结果
    private ImageView gf_bg_left;
    private ImageView gf_tv_left;
    //  中间开牌结果
    private ImageView gf_bg_ping;
    private ImageView gf_tv_ping;
    //  右边开牌结果
    private ImageView gf_bg_right;
    private ImageView gf_tv_right;

    private View rl_ping_pai;
    private View rl_left_pai;
    private View rl_right_pai;

    // 9张牌
    private View card_1, card_2, card_3, card_4, card_5;  // 第一排
    private View card_6, card_7, card_8, card_9, card_10;  // 第二排
    private View card_11, card_12, card_13, card_14, card_15;  // 第三排


    private ArrayList<View> cardList = new ArrayList<>();
    private ArrayList<View> cardListOne = new ArrayList<>();
    private ArrayList<View> cardListTwo = new ArrayList<>();
    private ArrayList<View> cardListThree = new ArrayList<>();

    private int paPaiIndex = 1;  //发牌的动画 index
    private int shouPaiIndex = 1; //收牌的动画 index

    public NiuNiuLayout(Context context, boolean isPublish) {
        super(context, null);
        this.isPublish = isPublish;
        init(context);
    }

    public NiuNiuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context) {

        mApp = context;
        LayoutInflater.from(context).inflate(R.layout.game_layout_main_niuniu, this);
        super.init();

        shadow_left = (ImageView) findViewById(R.id.shadow_left);
        gf_bg_left = (ImageView) rl_bet_left.findViewById(R.id.gf_bg);
        gf_tv_left = (ImageView) rl_bet_left.findViewById(R.id.gf_tv);
        gf_bg_ping = (ImageView) rl_bet_ping.findViewById(R.id.gf_bg);
        gf_tv_ping = (ImageView) rl_bet_ping.findViewById(R.id.gf_tv);
        gf_bg_right = (ImageView) rl_bet_right.findViewById(R.id.gf_bg);
        gf_tv_right = (ImageView) rl_bet_right.findViewById(R.id.gf_tv);

        rl_ping_pai = rl_bet_ping.findViewById(R.id.rl_pai);
        rl_left_pai = rl_bet_left.findViewById(R.id.rl_pai);
        rl_right_pai = rl_bet_right.findViewById(R.id.rl_pai);

        card_1 = rl_bet_left.findViewById(R.id.card_1);
        card_2 = rl_bet_left.findViewById(R.id.card_2);
        card_3 = rl_bet_left.findViewById(R.id.card_3);
        card_4 = rl_bet_left.findViewById(R.id.card_4);
        card_5 = rl_bet_left.findViewById(R.id.card_5);
        cardListOne.add(card_1);
        cardListOne.add(card_2);
        cardListOne.add(card_3);
        cardListOne.add(card_4);
        cardListOne.add(card_5);
        cardList.addAll(cardListOne);

        card_6 = rl_bet_ping.findViewById(R.id.card_1);
        card_7 = rl_bet_ping.findViewById(R.id.card_2);
        card_8 = rl_bet_ping.findViewById(R.id.card_3);
        card_9 = rl_bet_ping.findViewById(R.id.card_4);
        card_10 = rl_bet_ping.findViewById(R.id.card_5);

        cardListTwo.add(card_6);
        cardListTwo.add(card_7);
        cardListTwo.add(card_8);
        cardListTwo.add(card_9);
        cardListTwo.add(card_10);
        cardList.addAll(cardListTwo);

        card_11 = rl_bet_right.findViewById(R.id.card_1);
        card_12 = rl_bet_right.findViewById(R.id.card_2);
        card_13 = rl_bet_right.findViewById(R.id.card_3);
        card_14 = rl_bet_right.findViewById(R.id.card_4);
        card_15 = rl_bet_right.findViewById(R.id.card_5);

        cardListThree.add(card_11);
        cardListThree.add(card_12);
        cardListThree.add(card_13);
        cardListThree.add(card_14);
        cardListThree.add(card_15);
        cardList.addAll(cardListThree);


        iv_poker_info_icon_left.setImageResource(R.drawable.game_card_poker_info_icon_bg_left);
        iv_poker_info_icon_ping.setImageResource(R.drawable.game_card_poker_info_icon_bg_center);
        iv_poker_info_icon_right.setImageResource(R.drawable.game_card_poker_info_icon_bg_right);


        GameUIUtils.setViewLayoutParamsWidth(rl_bet_left, mTreePokerWidth / 3);
        GameUIUtils.setViewLayoutParamsWidth(rl_bet_ping, mTreePokerWidth / 3);
        GameUIUtils.setViewLayoutParamsWidth(rl_bet_right, mTreePokerWidth / 3);
        int pokerMargin = (int) GameUtil.getDimension(R.dimen.game_djs_three_poker_margin);
        int mWindowWidthTO10 = mWindowWidth / 10;
        int mTreePokerWidthTo3 = (int) (mTreePokerWidth / 3) - mWindowWidthTO10-(pokerMargin*2);
        for (int i = 0; i < cardListOne.size(); i++) {
            float viewWidth = (mTreePokerWidthTo3 / cardListOne.size() * i);
            GameUIUtils.setViewLayoutParamsMargin(cardListOne.get(i), viewWidth /*+ pokerMargin*/);
        }
        for (int i = 0; i < cardListTwo.size(); i++) {
            float viewWidth = (mTreePokerWidthTo3 / cardListTwo.size() * i);
            GameUIUtils.setViewLayoutParamsMargin(cardListTwo.get(i), viewWidth /*+ pokerMargin*/);
        }
        for (int i = 0; i < cardListThree.size(); i++) {
            float viewWidth = (mTreePokerWidthTo3 / cardListThree.size() * i);
            GameUIUtils.setViewLayoutParamsMargin(cardListThree.get(i), viewWidth /*+ pokerMargin*/);
        }
    }


    /**
     * 下一轮之前，回复界面元素
     */
    @Override
    protected void beforeNext() {
        super.beforeNext();
        shadow_left.setVisibility(INVISIBLE);
        gf_bg_left.setVisibility(INVISIBLE);
        gf_bg_ping.setVisibility(INVISIBLE);
        gf_bg_right.setVisibility(INVISIBLE);
        gf_tv_left.setVisibility(INVISIBLE);
        gf_tv_ping.setVisibility(INVISIBLE);
        gf_tv_right.setVisibility(INVISIBLE);
        whoWin = -1;

    }

    /**
     * 中途加入房间
     *
     * @param joinGameRespond 游戏数据
     */
    @Override
    public void joinGameXiaZhu(final JoinGameRespond joinGameRespond) {
        super.joinGameXiaZhu(joinGameRespond);
        for (View view : cardList) {
            cardBackVisible(view);
        }
    }




    /**
     * 进入下注阶段
     *
     * @param gameSound
     * @param xiaZhuPush
     */
    @Override
    public void doXiaZhu(final GameSound gameSound, final XiaZhuPush xiaZhuPush) {
        super.doXiaZhu(gameSound, xiaZhuPush);

        // 发牌
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                game_state = GAME_STATE_FA_PAI;
                faPaiAnimAll(gameSound);
                djsAction(xiaZhuPush.getCountdown_time() - 1);
            }
        }, 800);

    }

    /**
     * 设置牌的点数花色
     *
     * @param gameInfo 游戏数据
     */
    @Override
    protected void initPokerInfo(List<GameItemInfo> gameInfo) {

        for (int i = 0; i < 5; i++) {
            View view = cardList.get(i);
            getAPokerToTarget(view, gameInfo.get(0).getPoker_info().get(i).getColor(),
                    gameInfo.get(0).getPoker_info().get(i).getNumber());

        }
        for (int i = 5; i < 10; i++) {
            View view = cardList.get(i);
            getAPokerToTarget(view, gameInfo.get(1).getPoker_info().get(i - 5).getColor(),
                    gameInfo.get(1).getPoker_info().get(i - 5).getNumber());
        }
        for (int i = 10; i < 15; i++) {
            View view = cardList.get(i);
            getAPokerToTarget(view, gameInfo.get(2).getPoker_info().get(i - 10).getColor(),
                    gameInfo.get(2).getPoker_info().get(i - 10).getNumber());
        }
    }

    /**
     * 发9张牌动画
     */
    Runnable faPaiRunable = new Runnable() {
        @Override
        public void run() {
            View view = cardList.get(paPaiIndex);
            if (view != null && cardList.size() - 1 > paPaiIndex) {
                faPaiAnim(card_fapai, view);
                mHandler.postDelayed(faPaiRunable, 100);
                paPaiIndex++;
            } else if (cardList.size() - 1 == paPaiIndex) {
                faPaiAnim(card_fapai, view);
                paPaiIndex = 0;
                mHandler.removeCallbacks(faPaiRunable);
            } else {
                paPaiIndex = 0;
                mHandler.removeCallbacks(faPaiRunable);
            }

        }
    };
    /**
     * 收9张牌动画
     */
    Runnable shouPaiRunable = new Runnable() {
        @Override
        public void run() {
            View view = cardList.get(shouPaiIndex);
            if (view != null && cardList.size() - 1 > shouPaiIndex) {
                shouPaiAnimWithPoker(view, card_fapai);
                view.setVisibility(INVISIBLE);
                mHandler.postDelayed(shouPaiRunable, 100);
                shouPaiIndex++;
            } else if (cardList.size() - 1 == shouPaiIndex) {
                shouPaiAnimWithPoker(view, card_fapai);
                view.setVisibility(INVISIBLE);
                shouPaiIndex = 0;
                mHandler.removeCallbacks(faPaiRunable);
            } else {
                shouPaiIndex = 0;
                mHandler.removeCallbacks(faPaiRunable);
            }

        }
    };

    @Override
    protected void showShouPaiView() {
        super.showShouPaiView();

        //隐藏高亮 和 开牌结果
        beforeNext();

        for (int i = 0; i < cardList.size(); i++) {
            View view = cardList.get(i);
            view.setVisibility(INVISIBLE);
//            shouPaiAnimWithPoker(view, card_fapai);
        }
    }

    /**
     * 发9张牌动画
     */
    protected void faPaiAnimAll(GameSound gameSound, Poker... poker) {
        super.faPaiAnimAll(1700);
        gameSound.playSound(GameSound.SOUND_FA_PAI, 0);
        faPaiAnim(card_fapai, card_1);
        mHandler.postDelayed(faPaiRunable, 100);
    }


    private void pokerTypeParser(ImageView view, GameItemInfo info) {
        // TODO: 2016/12/24 size判断
        // 解析牌型
        switch (info.getPoker_type()) {
            case Poker.NN_NIU0:
                view.setImageResource(R.drawable.ico_bull_mn);
                break;
            case Poker.NN_NIU1:
                view.setImageResource(R.drawable.ico_bull_n1);
                break;
            case Poker.NN_NIU2:
                view.setImageResource(R.drawable.ico_bull_n2);
                break;
            case Poker.NN_NIU3:
                view.setImageResource(R.drawable.ico_bull_n3);
                break;
            case Poker.NN_NIU4:
                view.setImageResource(R.drawable.ico_bull_n4);
                break;
            case Poker.NN_NIU5:
                view.setImageResource(R.drawable.ico_bull_n5);
                break;
            case Poker.NN_NIU6:
                view.setImageResource(R.drawable.ico_bull_n6);
                break;
            case Poker.NN_NIU7:
                view.setImageResource(R.drawable.ico_bull_n7);
                break;
            case Poker.NN_NIU8:
                view.setImageResource(R.drawable.ico_bull_n8);
                break;
            case Poker.NN_NIU9:
                view.setImageResource(R.drawable.ico_bull_n9);
                break;
            case Poker.NN_NIU10:
                view.setImageResource(R.drawable.ico_bull_nn);
                break;
            case Poker.NN_HUA4:
                view.setImageResource(R.drawable.ico_bull_shn);
                break;
            case Poker.NN_HUA5BIG:
                view.setImageResource(R.drawable.ico_bull_whn);
                break;
            case Poker.NN_HUA5SMALL:
                view.setImageResource(R.drawable.ico_bull_wxn);
                break;
            case Poker.NN_ZD:
                view.setImageResource(R.drawable.ico_bull_zd);
                break;
        }
    }

    /**
     * 开牌
     */
    @Override
    protected void kaiPai(List<GameItemInfo> gameInfo) {

        //
        pokerTypeParser(gf_tv_left, gameInfo.get(0));
        pokerTypeParser(gf_tv_ping, gameInfo.get(1));
        pokerTypeParser(gf_tv_right, gameInfo.get(2));
        setPokerVisible();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                kaiPaiOne();
            }
        }, 1200);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                kaiPaiTwo();
            }
        }, 3200);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                kaiPaiThree(10);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        winHighlight();
                    }
                }, 900);

            }
        }, 5200);
    }

    /**
     * 不带动画延迟的开牌
     */
    @Override
    protected void kaiPaiNoDelayed(List<GameItemInfo> gameInfo, int tipDelayedTime) {
        pokerTypeParser(gf_tv_left, gameInfo.get(0));
        pokerTypeParser(gf_tv_ping, gameInfo.get(1));
        pokerTypeParser(gf_tv_right, gameInfo.get(2));
        setPokerVisible();
        kaiPaiOne();
        kaiPaiTwo();
        kaiPaiThree(tipDelayedTime);
        winHighlight();
    }

    private void setPokerVisible() {
        for (View view : cardList) {
            view.setVisibility(VISIBLE);
        }
    }

    /**
     * 开第一副牌
     */
    private void kaiPaiOne() {
        for (int i = 0; i < 5; i++) {
            View view = cardList.get(i);
            view.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        }
        tipTextAnim(gf_bg_left, gf_tv_left);

    }

    /**
     * 开第二副牌
     */
    private void kaiPaiTwo() {
        for (int i = 5; i < 10; i++) {
            View view = cardList.get(i);
            view.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        }
        tipTextAnim(gf_bg_ping, gf_tv_ping);
    }

    /**
     * 开第三副牌
     */
    private void kaiPaiThree(int tipDelayedTime) {
        for (int i = 10; i < 15; i++) {
            View view = cardList.get(i);
            view.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        }
        tipTextAnim(gf_bg_right, gf_tv_right);

        // 启动收牌
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showShouPaiView();
            }
        }, tipDelayedTime * 1000);//默认10
    }

    /**
     * 赢牌高亮
     */
    private void winHighlight() {
//        shadow_right.setVisibility(VISIBLE);
//        shadow_right.bringToFront();
        shadow_left.setVisibility(VISIBLE);
        shadow_left.bringToFront();
//        shadow_center.setVisibility(VISIBLE);
//        shadow_center.bringToFront();

        if (whoWin == BET_LEFT) {
            rl_bet_left.bringToFront();
//            card_container_left.bringToFront();
            rl_left_pai.bringToFront();
            gf_bg_left.bringToFront();
            gf_tv_left.bringToFront();
        } else if (whoWin == BET_CENTER) {
            rl_bet_ping.bringToFront();
//            card_container_ping.bringToFront();
            rl_ping_pai.bringToFront();
            gf_bg_ping.bringToFront();
            gf_tv_ping.bringToFront();
        } else if (whoWin == BET_RIGHT) {
            rl_bet_right.bringToFront();
//            card_container_right.bringToFront();
            rl_right_pai.bringToFront();
            gf_bg_right.bringToFront();
            gf_tv_right.bringToFront();
        }
    }

    @Override
    protected int getGameType() {
        return GameC.Game.GAME_TYPE_NIUNIU;
    }


}
