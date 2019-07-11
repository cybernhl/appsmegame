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


public class ZJHLayout extends BaseCradView {
    private static final String TAG = "ZJHLayout";

    //    private View shadow_right;
    private View shadow_left;
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
    private View card_1, card_2, card_3, card_4, card_5, card_6, card_7, card_8, card_9;
    ArrayList<View> cardList = new ArrayList<>();

    public ZJHLayout(Context context, boolean isPublish) {
        super(context, null);
        this.isPublish = isPublish;
        init(context);
    }

    public ZJHLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context) {

        mApp = context;
        LayoutInflater.from(context).inflate(R.layout.game_layout_main_zjh, this);
        super.init();
//        shadow_right = findViewById(R.id.shadow_right);
        shadow_left = findViewById(R.id.shadow_left);
//        shadow_center = findViewById(shadow_center);

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
        card_4 = rl_bet_ping.findViewById(R.id.card_1);
        card_5 = rl_bet_ping.findViewById(R.id.card_2);
        card_6 = rl_bet_ping.findViewById(R.id.card_3);
        card_7 = rl_bet_right.findViewById(R.id.card_1);
        card_8 = rl_bet_right.findViewById(R.id.card_2);
        card_9 = rl_bet_right.findViewById(R.id.card_3);

        cardList.add(card_1);
        cardList.add(card_2);
        cardList.add(card_3);
        cardList.add(card_4);
        cardList.add(card_5);
        cardList.add(card_6);
        cardList.add(card_7);
        cardList.add(card_8);
        cardList.add(card_9);

        iv_poker_info_icon_left.setImageResource(R.drawable.game_card_poker_info_icon_bg_left);
        iv_poker_info_icon_ping.setImageResource(R.drawable.game_card_poker_info_icon_bg_center);
        iv_poker_info_icon_right.setImageResource(R.drawable.game_card_poker_info_icon_bg_right);

        GameUIUtils.setViewLayoutParamsWidth(rl_bet_left, mTreePokerWidth / 3);
        GameUIUtils.setViewLayoutParamsWidth(rl_bet_ping, mTreePokerWidth / 3);
        GameUIUtils.setViewLayoutParamsWidth(rl_bet_right, mTreePokerWidth / 3);

//
//        ViewGroup.LayoutParams layoutParams_ping = rl_bet_ping.getLayoutParams();
//        layoutParams_ping.width = (int) (treePokerWidth /3);
//        rl_bet_ping.setLayoutParams(layoutParams_ping);
//
//        ViewGroup.LayoutParams layoutParams_right = rl_bet_right.getLayoutParams();
//        layoutParams_right.width = (int) (treePokerWidth /3);
//        rl_bet_right.setLayoutParams(layoutParams_right);
    }

    /**
     * 下一轮之前，回复界面元素
     */
    @Override
    protected void beforeNext() {
        super.beforeNext();
//        shadow_right.setVisibility(INVISIBLE);
        shadow_left.setVisibility(INVISIBLE);
//        shadow_center.setVisibility(INVISIBLE);
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
    public void joinGameXiaZhu(final JoinGameRespond joinGameRespond) {
        super.joinGameXiaZhu(joinGameRespond);

        cardBackVisible(card_1);
        cardBackVisible(card_2);
        cardBackVisible(card_3);
        cardBackVisible(card_4);
        cardBackVisible(card_5);
        cardBackVisible(card_6);
        cardBackVisible(card_7);
        cardBackVisible(card_8);
        cardBackVisible(card_9);

    }


    /**
     * 进入下注阶段
     *
     * @param gameSound
     * @param xiaZhuPush
     */
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
        getAPokerToTarget(card_1, gameInfo.get(0).getPoker_info().get(0).getColor(),
                gameInfo.get(0).getPoker_info().get(0).getNumber());
        getAPokerToTarget(card_2, gameInfo.get(0).getPoker_info().get(1).getColor(),
                gameInfo.get(0).getPoker_info().get(1).getNumber());
        getAPokerToTarget(card_3, gameInfo.get(0).getPoker_info().get(2).getColor(),
                gameInfo.get(0).getPoker_info().get(2).getNumber());
        getAPokerToTarget(card_4, gameInfo.get(1).getPoker_info().get(0).getColor(),
                gameInfo.get(1).getPoker_info().get(0).getNumber());
        getAPokerToTarget(card_5, gameInfo.get(1).getPoker_info().get(1).getColor(),
                gameInfo.get(1).getPoker_info().get(1).getNumber());
        getAPokerToTarget(card_6, gameInfo.get(1).getPoker_info().get(2).getColor(),
                gameInfo.get(1).getPoker_info().get(2).getNumber());
        getAPokerToTarget(card_7, gameInfo.get(2).getPoker_info().get(0).getColor(),
                gameInfo.get(2).getPoker_info().get(0).getNumber());
        getAPokerToTarget(card_8, gameInfo.get(2).getPoker_info().get(1).getColor(),
                gameInfo.get(2).getPoker_info().get(1).getNumber());
        getAPokerToTarget(card_9, gameInfo.get(2).getPoker_info().get(2).getColor(),
                gameInfo.get(2).getPoker_info().get(2).getNumber());
    }

    /**
     * 发9张牌动画
     */
    protected void faPaiAnimAll(GameSound gameSound, Poker... poker) {
        super.faPaiAnimAll(1000);
        gameSound.playSound(GameSound.SOUND_FA_PAI, 0);
        faPaiAnim(card_fapai, card_1);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                faPaiAnim(card_fapai, card_2);
            }
        }, 100);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                faPaiAnim(card_fapai, card_3);
            }
        }, 200);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                faPaiAnim(card_fapai, card_4);
            }
        }, 300);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                faPaiAnim(card_fapai, card_5);
            }
        }, 400);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                faPaiAnim(card_fapai, card_6);
            }
        }, 500);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                faPaiAnim(card_fapai, card_7);
            }
        }, 600);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                faPaiAnim(card_fapai, card_8);
            }
        }, 700);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                faPaiAnim(card_fapai, card_9);
            }
        }, 800);
    }

    /**
     * 收牌
     */
    @Override
    protected void showShouPaiView() {
        super.showShouPaiView();
        //隐藏高亮 和 开牌结果
        beforeNext();
        for (int i = 0; i < cardList.size(); i++) {
            View view = cardList.get(i);
            view.setVisibility(INVISIBLE);
            shouPaiAnimWithPoker(view, card_fapai);
        }
    }


    private void pokerTypeParser(ImageView view, GameItemInfo info) {
        // 2016/12/24 size判断
        // 解析牌型
        switch (info.getPoker_type()) {
            case Poker.ZJH_DAN_PAI:
                view.setImageResource(R.drawable.game_ico_gdfr_danpai);
                break;
            case Poker.ZJH_DUI_ZI:
                view.setImageResource(R.drawable.game_ico_gdfr_duizi);
                break;
            case Poker.ZJH_SHUN_ZI:
                view.setImageResource(R.drawable.game_ico_gdfr_shunzi);
                break;
            case Poker.ZJH_TONG_HUA:
                view.setImageResource(R.drawable.game_ico_gdfr_tonghua);
                break;
            case Poker.ZJH_BAO_ZI:
                view.setImageResource(R.drawable.game_ico_gdfr_baozi);
                break;
            case Poker.ZJH_TONG_HUA_SHUN:
                view.setImageResource(R.drawable.game_ico_gdfr_ths);
                break;
        }
    }

    /**
     * 开牌
     */
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
        card_1.setVisibility(VISIBLE);
        card_2.setVisibility(VISIBLE);
        card_3.setVisibility(VISIBLE);
        card_4.setVisibility(VISIBLE);
        card_5.setVisibility(VISIBLE);
        card_6.setVisibility(VISIBLE);
        card_7.setVisibility(VISIBLE);
        card_8.setVisibility(VISIBLE);
        card_9.setVisibility(VISIBLE);
    }

    /**
     * 开第一副牌
     */
    private void kaiPaiOne() {
        card_1.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        card_2.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        card_3.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        tipTextAnim(gf_bg_left, gf_tv_left);

    }

    /**
     * 开第二副牌
     */
    private void kaiPaiTwo() {
        card_4.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        card_5.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        card_6.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        tipTextAnim(gf_bg_ping, gf_tv_ping);
    }

    /**
     * 开第三副牌
     */
    private void kaiPaiThree(int tipDelayedTime) {
        card_7.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        card_8.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        card_9.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        tipTextAnim(gf_bg_right, gf_tv_right);

        // 启动收牌
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showShouPaiView();
            }
        }, tipDelayedTime * 1000);

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
        return GameC.Game.GAME_TYPE_ZJH;
    }


}
