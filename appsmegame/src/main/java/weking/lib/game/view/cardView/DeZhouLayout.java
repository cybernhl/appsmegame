package weking.lib.game.view.cardView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import weking.lib.game.R;
import weking.lib.game.GameC;
import weking.lib.game.bean.GameItemInfo;
import weking.lib.game.bean.JoinGameRespond;
import weking.lib.game.bean.Poker;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.manager.GameSound;
import weking.lib.game.utils.GameUIUtils;


/**
 * 德州扑克
 */
public class DeZhouLayout extends BaseCradView {
    private static final String TAG = "ZJHLayout";


    private View shadow_left;


    //  左边开牌结果
    private ImageView gf_bg_left;
    private ImageView gf_tv_left;
    //  中间开牌结果
//    private ImageView gf_bg_ping;
//    private ImageView gf_tv_ping;
    //  右边开牌结果
    private ImageView gf_bg_right;
    private ImageView gf_tv_right;

    private RelativeLayout rl_ping_pai;
    private View rl_left_pai;
    private View rl_right_pai;

    // 9张牌
    private View card_1, card_2, card_3, card_4, card_5, card_6, card_7, card_8, card_9;

    ArrayList<View> cardList = new ArrayList<>();

    public DeZhouLayout(Context context, boolean isPublish) {
        super(context, null);
        this.isPublish = isPublish;
        init(context);
    }


    public DeZhouLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context) {
        mApp = context;
        LayoutInflater.from(context).inflate(R.layout.game_layout_main_dz, this);
        super.init();

        shadow_left = findViewById(R.id.shadow_left);
        gf_bg_left = (ImageView) findViewById(R.id.gf_bg);
        gf_tv_left = (ImageView) findViewById(R.id.gf_tv);

        gf_bg_right = (ImageView) rl_bet_right.findViewById(R.id.gf_bg);
        gf_tv_right = (ImageView) rl_bet_right.findViewById(R.id.gf_tv);

        rl_ping_pai = (RelativeLayout) rl_bet_ping.findViewById(R.id.rl_pai);
        rl_left_pai = rl_bet_left.findViewById(R.id.rl_pai);
        rl_right_pai = rl_bet_right.findViewById(R.id.rl_pai);

        card_1 = rl_bet_ping.findViewById(R.id.card_1);
        card_2 = rl_bet_ping.findViewById(R.id.card_2);
        card_3 = rl_bet_ping.findViewById(R.id.card_3);
        card_4 = rl_bet_ping.findViewById(R.id.card_4);
        card_5 = rl_bet_ping.findViewById(R.id.card_5);

        card_6 = rl_bet_left.findViewById(R.id.card_1);
        card_7 = rl_bet_left.findViewById(R.id.card_2);
        card_8 = rl_bet_right.findViewById(R.id.card_1);
        card_9 = rl_bet_right.findViewById(R.id.card_2);

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
//        iv_poker_info_icon_ping.setImageResource(R.drawable.game_card_poker_info_icon_bg_center);
        iv_poker_info_icon_right.setImageResource(R.drawable.game_card_poker_info_icon_bg_right);


        GameUIUtils.setViewLayoutParamsWidth(rl_bet_left, mTreePokerWidth / 7*2);
        GameUIUtils.setViewLayoutParamsWidth(rl_bet_ping, mTreePokerWidth  / 7*3);
        GameUIUtils.setViewLayoutParamsWidth(rl_bet_right, mTreePokerWidth  / 7*2);
        int mTreePokerWidthTo4 = (int)( (mTreePokerWidth  / 7*3)-( mWindowWidth / 10));
        for (int i = 0; i < 5; i++) {
            float viewWidth = (mTreePokerWidthTo4 / 5 * i);
            GameUIUtils.setViewLayoutParamsMargin(cardList.get(i), viewWidth + 20);
        }

        tv_bei_shu_left.setText(" × 2 ");
        tv_bei_shu_ping.setText(" × 22 ");
        tv_bei_shu_right.setText(" × 2 ");
    }


    /**
     * 下一轮之前，回复界面元素
     */
    @Override
    protected void beforeNext() {
        super.beforeNext();

        shadow_left.setVisibility(INVISIBLE);

        gf_bg_left.setVisibility(INVISIBLE);

        gf_bg_right.setVisibility(INVISIBLE);
        gf_tv_left.setVisibility(INVISIBLE);

        gf_tv_right.setVisibility(INVISIBLE);


        whoWin = -1;


    }

    /**
     * 中途加入房间
     *
     * @param joinGameRespond
     */
    @Override
    public void joinGameXiaZhu(final JoinGameRespond joinGameRespond) {
        super.joinGameXiaZhu(joinGameRespond);

        cardVisible(card_1);
        if (joinGameRespond.getPre_pokers() != null &&
                joinGameRespond.getPre_pokers().size() >= 1) {
            getAPokerToTarget(card_1, joinGameRespond.getPre_pokers().get(0).getColor(),
                    joinGameRespond.getPre_pokers().get(0).getNumber());
        }

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
    @Override
    public void doXiaZhu(final GameSound gameSound, final XiaZhuPush xiaZhuPush) {
        super.doXiaZhu(gameSound, xiaZhuPush);

        if (xiaZhuPush.getPre_pokers() == null) {
            // 发牌
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    game_state = GAME_STATE_FA_PAI;
                    Poker poker = null;
                    faPaiAnimAll(gameSound, poker);
                    djsAction(xiaZhuPush.getCountdown_time() - 1);
                }
            }, 800);
        } else {
            // 发牌
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    game_state = GAME_STATE_FA_PAI;
                    faPaiAnimAll(gameSound, xiaZhuPush.getPre_pokers().get(0));
                    djsAction(xiaZhuPush.getCountdown_time() - 1);
                }
            }, 800);
        }

    }


    /**
     * 设置牌的点数花色+
     *
     *
     *
     * @param gameInfo
     */
    @Override
    protected void initPokerInfo(List<GameItemInfo> gameInfo) {

        getAPokerToTarget(card_1, gameInfo.get(1).getPoker_info().get(0).getColor(),
                gameInfo.get(1).getPoker_info().get(0).getNumber());

        getAPokerToTarget(card_2, gameInfo.get(1).getPoker_info().get(1).getColor(),
                gameInfo.get(1).getPoker_info().get(1).getNumber());

        getAPokerToTarget(card_3, gameInfo.get(1).getPoker_info().get(2).getColor(),
                gameInfo.get(1).getPoker_info().get(2).getNumber());

        getAPokerToTarget(card_4, gameInfo.get(1).getPoker_info().get(3).getColor(),
                gameInfo.get(1).getPoker_info().get(3).getNumber());

        getAPokerToTarget(card_5, gameInfo.get(1).getPoker_info().get(4).getColor(),
                gameInfo.get(1).getPoker_info().get(4).getNumber());

        getAPokerToTarget(card_6, gameInfo.get(0).getPoker_info().get(0).getColor(),
                gameInfo.get(0).getPoker_info().get(0).getNumber());

        getAPokerToTarget(card_7, gameInfo.get(0).getPoker_info().get(1).getColor(),
                gameInfo.get(0).getPoker_info().get(1).getNumber());

        getAPokerToTarget(card_8, gameInfo.get(2).getPoker_info().get(0).getColor(),
                gameInfo.get(2).getPoker_info().get(0).getNumber());

        getAPokerToTarget(card_9, gameInfo.get(2).getPoker_info().get(1).getColor(),
                gameInfo.get(2).getPoker_info().get(1).getNumber());
    }

    /**
     * 发9张牌动画
     */

    protected void faPaiAnimAll(GameSound gameSound, Poker... poker) {
        super.faPaiAnimAll(1000);
        gameSound.playSound(GameSound.SOUND_FA_PAI, 0);
        faPaiAnimWithPoker(card_fapai, card_1, poker[0]);
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
        // TODO: 2016/12/24 size判断
        // 解析牌型
        switch (info.getPoker_type()) {
            case Poker.DZ_GAO_PAI:
                view.setImageResource(R.drawable.game_dz_gaopai);
                break;
            case Poker.DZ_YI_DUI:
                view.setImageResource(R.drawable.game_dz_yidui);
                break;
            case Poker.DZ_LIANG_DUI:
                view.setImageResource(R.drawable.game_dz_liangdui);
                break;
            case Poker.DZ_SAN_TIAO:
                view.setImageResource(R.drawable.game_dz_st);
                break;
            case Poker.DZ_SHUN_ZI:
                view.setImageResource(R.drawable.game_dz_sz);
                break;
            case Poker.DZ_TONG_HUA:
                view.setImageResource(R.drawable.game_dz_tonghua);
                break;
            case Poker.DZ_HU_LU:
                view.setImageResource(R.drawable.game_dz_hulu);
                break;
            case Poker.DZ_SI_TIAO:
                view.setImageResource(R.drawable.game_dz_sitiao);
                break;
            case Poker.DZ_TONG_HUA_SHUN:
                view.setImageResource(R.drawable.game_dz_tonghuashun);
                break;
            case Poker.DZ_HJTHS:
                view.setImageResource(R.drawable.game_dz_hjths);
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
        pokerTypeParser(gf_tv_right, gameInfo.get(2));
        setPokerVisible();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                kaiPaiTwo();
            }
        }, 1200);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                kaiPaiOne();
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
    protected void kaiPaiNoDelayed(List<GameItemInfo> gameInfo, int  tipDelayedTime) {
        pokerTypeParser(gf_tv_left, gameInfo.get(0));
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
        card_6.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        card_7.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        tipTextAnim(gf_bg_left, gf_tv_left);

    }

    /**
     * 开第二副牌
     */
    private void kaiPaiTwo() {
        card_1.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        card_2.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        card_3.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        card_4.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        card_5.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);

    }

    /**
     * 开第三副牌
     */
    private void kaiPaiThree(int tipDelayedTime) {

        card_8.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        card_9.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        tipTextAnim(gf_bg_right, gf_tv_right);

        // 启动收牌
      mHandler. postDelayed(new Runnable() {
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

        shadow_left.setVisibility(VISIBLE);
        shadow_left.bringToFront();

        rl_ping_pai.bringToFront();

        if (whoWin == BET_LEFT) {
            rl_bet_left.bringToFront();

            rl_left_pai.bringToFront();
            gf_bg_left.bringToFront();
            gf_tv_left.bringToFront();

        } else if (whoWin == BET_CENTER) {
            rl_bet_ping.bringToFront();

        } else if (whoWin == BET_RIGHT) {
            rl_bet_right.bringToFront();

            rl_right_pai.bringToFront();
            gf_bg_right.bringToFront();
            gf_tv_right.bringToFront();

        }
    }


    @Override
    protected int getGameType() {
        return GameC.Game.GAME_TYPE_TTDZ;
    }

}
