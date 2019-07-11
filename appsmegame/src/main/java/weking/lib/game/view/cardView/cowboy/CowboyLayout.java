package weking.lib.game.view.cardView.cowboy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import weking.lib.game.GameC;
import weking.lib.game.R;
import weking.lib.game.base.BaseCowboyCradView;
import weking.lib.game.bean.GameItemInfo;
import weking.lib.game.bean.JoinGameRespond;
import weking.lib.game.bean.NextBenkerPush;
import weking.lib.game.bean.Poker;
import weking.lib.game.bean.XiaZhuPush;
import weking.lib.game.bean.push.GameResultPush;
import weking.lib.game.event.CancelBankerEvent;
import weking.lib.game.manager.GameSound;
import weking.lib.game.manager.memory.GameCowboySound;
import weking.lib.game.observer.GameObserver;
import weking.lib.game.utils.GameAction;
import weking.lib.game.utils.GameAnimUtil;
import weking.lib.game.utils.GameUtil;
import weking.lib.game.view.fragment.CowboyAnimFragment;
import weking.lib.utils.ActivityUtils;
import weking.lib.utils.ToastUtil;

/**
 * 欢乐牛仔
 */

public class CowboyLayout extends BaseCowboyCradView {
    private static final String TAG = "NiuZaiLayout";


    //  左边开牌结果
    private ImageView result_left;
    //  中间开牌结果
    private ImageView result_ping;
    //  右边开牌结果
    private ImageView result_right;
    //  右边开牌结果
    private ImageView result_banker;


    private View rl_ping_pai;
    private View rl_left_pai;
    private View rl_right_pai;
    protected ImageView IV_money;


    // 9张牌
    private View card_1, card_2, card_3, card_4, card_5;  // 第一排
    private View card_6, card_7, card_8, card_9, card_10;  // 第二排
    private View card_11, card_12, card_13, card_14, card_15;  // 第三排
    private View card_16, card_17, card_18, card_19, card_20;  //  庄家


    private ArrayList<View> cardList = new ArrayList<>();
    private ArrayList<View> cardListOne = new ArrayList<>();
    private ArrayList<View> cardListTwo = new ArrayList<>();
    private ArrayList<View> cardListThree = new ArrayList<>();
    private ArrayList<View> cardListbanker = new ArrayList<>();

    private int paPaiIndex = 1;  //发牌的动画 index
    private int shouPaiIndex = 1; //收牌的动画 index
    //赢钱
    public static int KAIPAIWIN = 1;
    //输钱
    public static int KAIPAIFAIL = 2;


    public CowboyLayout(Context context, boolean isPublish) {
        super(context, null);
        this.isPublish = isPublish;
        init(context);
    }

    private void init(Context context) {
        //初始化 声音对象
        GameCowboySound.getInstance().initSoundPool(context, isPublish);
        mApp = context;
        LayoutInflater.from(context).inflate(R.layout.game_layout_main_cowboy, this);
        initCowboyFragent();
        super.init();


        IV_money = (ImageView) findViewById(R.id.money);
        iv_banker_icom = (ImageView) findViewById(R.id.iv_banker_icom);
        tv_banker_name = (TextView) findViewById(R.id.tv_banker_name);
        tv_banker_money = (TextView) findViewById(R.id.tv_banker_money);

        result_left = (ImageView) rl_bet_left.findViewById(R.id.gf_result_bg);
        result_ping = (ImageView) rl_bet_ping.findViewById(R.id.gf_result_bg);
        result_right = (ImageView) rl_bet_right.findViewById(R.id.gf_result_bg);
        result_banker = (ImageView) rl_bet_banker.findViewById(R.id.gf_result_bg);

        cbv_bet_left = (CowboyBetView) rl_bet_left.findViewById(R.id.cbv_bet);
        cbv_bet_ping = (CowboyBetView) rl_bet_ping.findViewById(R.id.cbv_bet);
        cbv_bet_right = (CowboyBetView) rl_bet_right.findViewById(R.id.cbv_bet);


        rl_ping_pai = rl_bet_ping.findViewById(R.id.rl_pai);
        rl_left_pai = rl_bet_left.findViewById(R.id.rl_pai);
        rl_right_pai = rl_bet_right.findViewById(R.id.rl_pai);


        v_ping_poker = rl_bet_ping.findViewById(R.id.v_poker);
        v_left_poker = rl_bet_left.findViewById(R.id.v_poker);
        v_right_poker = rl_bet_right.findViewById(R.id.v_poker);

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

        card_16 = rl_bet_banker.findViewById(R.id.card_1);
        card_17 = rl_bet_banker.findViewById(R.id.card_2);
        card_18 = rl_bet_banker.findViewById(R.id.card_3);
        card_19 = rl_bet_banker.findViewById(R.id.card_4);
        card_20 = rl_bet_banker.findViewById(R.id.card_5);

        cardListbanker.add(card_16);
        cardListbanker.add(card_17);
        cardListbanker.add(card_18);
        cardListbanker.add(card_19);
        cardListbanker.add(card_20);
        cardList.addAll(cardListbanker);

        rl_bet_left.setBackgroundResource(R.drawable.game_card_cowboy_info_bg_left);
        rl_bet_ping.setBackgroundResource(R.drawable.game_card_cowboy_info_bg_ping);
        rl_bet_right.setBackgroundResource(R.drawable.game_card_cowboy_info_bg_right);

    }

    public CowboyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 不带动画延迟的开牌
     */
    @Override
    protected void kaiPaiNoDelayed(List<GameItemInfo> gameInfo, int tipDelayedTime) {
        setPokerVisible();
        kaiPaibanker();
        kaiPaiOne();
        kaiPaiTwo();
        kaiPaiThree(tipDelayedTime * 1000, true);
        highlightPoker();
        pokerTypeParser(result_left, gameInfo.get(0));
        pokerTypeParser(result_ping, gameInfo.get(1));
        pokerTypeParser(result_right, gameInfo.get(2));
        bankerPokerTypeParser(result_banker, gameInfo.get(3));
    }

    /**
     * 显示牌的数据
     *
     * @param gameInfo
     */
    @Override
    protected void kaiPai(final List<GameItemInfo> gameInfo) {
        setPokerVisible();
        final int kaipaiTiem = mKaiPaiLoadingTime - mNextKaiPaiTiem;
        kaiPaibanker();
        bankerPokerTypeParser(result_banker, gameInfo.get(3));
        highlightPoker();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                kaiPaiOne();
                GameItemInfo gameItemInfo = gameInfo.get(0);
                pokerTypeParser(result_left, gameItemInfo);
                if (mWin_money_position != null && mWin_money_position.size() == 3) {// 下注了
                    Integer myMoeny = mWin_money_position.get(0);
                    if (myMoeny > 0) {
                        GameAnimUtil.showToTopAnim(result_left, " +" + myMoeny + " ", mTypeFace, R.color.game_app_theme_color);
                    } else if (myMoeny < 0) {
                        GameAnimUtil.showToTopAnim(result_left, myMoeny + " ", mTypeFace, R.color.normal_text);
                    }
                }
            }
        }, kaipaiTiem * 1 / 4);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                kaiPaiTwo();
                GameItemInfo gameItemInfo = gameInfo.get(1);
                pokerTypeParser(result_ping, gameItemInfo);
                if (mWin_money_position != null && mWin_money_position.size() == 3) {
                    Integer myMoeny = mWin_money_position.get(1);

                    if (myMoeny > 0) {
                        GameAnimUtil.showToTopAnim(result_ping, " +" + myMoeny + " ", mTypeFace, R.color.game_app_theme_color);
                    } else if (myMoeny < 0) {
                        GameAnimUtil.showToTopAnim(result_ping, myMoeny + " ", mTypeFace, R.color.normal_text);
                    }
                }

            }
        }, kaipaiTiem * 2 / 4);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                kaiPaiThree(kaipaiTiem, false);
                GameItemInfo gameItemInfo = gameInfo.get(2);
                pokerTypeParser(result_right, gameItemInfo);
                if (mWin_money_position != null && mWin_money_position.size() == 3) {
                    Integer myMoeny = mWin_money_position.get(2);

                    if (myMoeny > 0) {
                        GameAnimUtil.showToTopAnim(result_right, " +" + myMoeny + " ", mTypeFace, R.color.game_app_theme_color);
                    } else if (myMoeny < 0) {
                        GameAnimUtil.showToTopAnim(result_right, myMoeny + " ", mTypeFace, R.color.normal_text);
                    }
                    mWin_money_position.clear();
                }
            }
        }, kaipaiTiem * 3 / 4);

    }

    /**
     * 设置开牌数据
     *
     * @param gameInfo
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
        for (int i = 15; i < 20; i++) {
            View view = cardList.get(i);
            getAPokerToTarget(view, gameInfo.get(3).getPoker_info().get(i - 15).getColor(),
                    gameInfo.get(3).getPoker_info().get(i - 15).getNumber());
        }
    }


    // 动画的frament
    private void initCowboyFragent() {
        // Create the fragment
        mGowBoyFrament = CowboyAnimFragment.newInstance();
        ActivityUtils.replaceFragmentToActivity(
                GameObserver.getAppObserver().getCurrentActivity().getSupportFragmentManager(), mGowBoyFrament, R.id.cowboy_frame);

    }


    /**
     * 下一轮之前，回复界面元素
     */
    @Override
    protected void beforeNext() {
        super.beforeNext();
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
        doNextBenker(joinGameRespond);
        for (View view : cardList) {
            cardBackVisible(view);
        }
    }

    @Override
    public void joinGameKaiPai(JoinGameRespond gameRespond) {
        super.joinGameKaiPai(gameRespond);
        doNextBenker(gameRespond);
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
                //发牌
                faPaiAnimAll(gameSound, /*8*/xiaZhuPush.getCountdown_time() - 1);
            }
        }, 800);

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
        // 下注的倍数
        cbv_bet_left.colseImageList();
        cbv_bet_ping.colseImageList();
        cbv_bet_right.colseImageList();
        //隐藏高亮 和 开牌结果
        beforeNext();

        for (int i = 0; i < cardList.size(); i++) {
            final View view = cardList.get(i);
            AlphaAnimation animation = new AlphaAnimation(1, 0.2f);
            animation.setDuration(400);//设置动画持续时间
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

            }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(INVISIBLE);
                }


                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(animation);
        }
    }

    /**
     * 发9张牌动画
     */
    protected void faPaiAnimAll(GameSound gameSound, final int time) {
        super.faPaiAnimAll(1700);
        GameCowboySound.getInstance().playSound(getContext(), GameCowboySound.GAME_COWBOY_SHUFFLE, 0, isPublish);
        GameAnimUtil.faPaiAnimWithPokerPing(card_fapai, root_container, getGameType(), null, mHandler);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.faPaiAnimWithPokerPing(card_fapai, root_container, getGameType(), null, mHandler);
            }
        }, 100);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.faPaiAnimWithPokerPing(card_fapai, root_container, getGameType(), null, mHandler);
            }
        }, 200);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.faPaiAnimWithPokerPing(card_fapai, root_container, getGameType(), null, mHandler);
            }
        }, 300);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.faPaiAnimWithPokerPing(card_fapai, root_container, getGameType(), null, mHandler);
            }
        }, 400);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.faPaiAnimWithPokerPing(card_fapai, root_container, getGameType(), null, mHandler);
            }
        }, 500);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.faPaiAnimWithPokerPing(card_fapai, root_container, getGameType(), null, mHandler);
            }
        }, 600);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameAnimUtil.faPaiAnimWithPokerPing(card_fapai, root_container, getGameType(), new GameAction().new OnResult<ArrayList<View>>() {
                    @Override
                    public void invoke(final ArrayList<View> list) {
                        fapaiToInfo(list, time);
                    }

                    @Override
                    public void onError() {
                    }
                }, mHandler);
            }
        }, 700);
    }

    /**
     * 发牌到桌面
     *
     * @param list
     * @param time
     */
    private void fapaiToInfo(final ArrayList<View> list, final int time) {
        faPaiAnim(card_fapai, card_1);
        faPaiAnim(card_fapai, card_6);
        faPaiAnim(card_fapai, card_11);
        faPaiAnim(card_fapai, card_16);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                faPaiAnim(card_fapai, card_2);
                faPaiAnim(card_fapai, card_7);
                faPaiAnim(card_fapai, card_12);
                faPaiAnim(card_fapai, card_17);
            }
        }, 300);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                faPaiAnim(card_fapai, card_3);
                faPaiAnim(card_fapai, card_8);
                faPaiAnim(card_fapai, card_13);
                faPaiAnim(card_fapai, card_18);
            }
        }, 600);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                faPaiAnim(card_fapai, card_4);
                faPaiAnim(card_fapai, card_9);
                faPaiAnim(card_fapai, card_14);
                faPaiAnim(card_fapai, card_19);
            }
        }, 900);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                faPaiAnim(card_fapai, card_5);
                faPaiAnim(card_fapai, card_10);
                faPaiAnim(card_fapai, card_15);
                faPaiAnimWithPoker(card_fapai, card_20, null, new GameAction().new Void() {
                    @Override
                    public void invoke() {
                        //清空 中间的牌

                        game_state = 1;
                        mGowBoyFrament.shouBetArea();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                GameCowboySound.getInstance().playSound(getContext(), GameCowboySound.GAME_COWBOY_TIME_COUNTDOWN, 0, isPublish);
                                djsAction(time);
                            }
                        }, 1000);
                        if (list != null && list.size() != 0) {
                            for (View view : list) {
                                root_container.removeView(view);
                            }
                            list.clear();
                        }
                    }
                });
            }
        }, 1200);

    }

    /**
     * 显示所有的牌
     */
    private void setPokerVisible() {
        for (View view : cardList) {
            view.setVisibility(VISIBLE);
        }
    }

    /**
     * 开第一庄家
     */
    private void kaiPaibanker() {
        for (int i = 15; i < 20; i++) {
            View view = cardList.get(i);
            view.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
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
    }

    /**
     * 开第二副牌
     */
    private void kaiPaiTwo() {
        for (int i = 5; i < 10; i++) {
            View view = cardList.get(i);
            view.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        }
    }

    /**
     * 开第三副牌
     *
     * @param showResultTiem
     */
    private void kaiPaiThree(int showResultTiem, final boolean isOne) {
        for (int i = 10; i < 15; i++) {
            View view = cardList.get(i);
            view.findViewById(R.id.iv_bg).setVisibility(INVISIBLE);
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // 显示总共的钱
                if (mMyWinMoney > 0) {// 赢
                    GameCowboySound.getInstance().playSound(getContext(), GameCowboySound.GAME_COWBOY_WIN_MONEY, 0, isPublish);
                    mGowBoyFrament.shouNextGame(true, mBanker_win_num, mMyWinMoney, new GameAction().new One<View>() {
                        @Override
                        public void invoke(final View moenyView) {
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GameAnimUtil.startWinAnim(root_container, moenyView, IV_money);
                                }
                            }, 300);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GameAnimUtil.startWinAnim(root_container, moenyView, IV_money);
                                }
                            }, 400);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GameAnimUtil.startWinAnim(root_container, moenyView, IV_money);
                                }
                            }, 500);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GameAnimUtil.startWinAnim(root_container, moenyView, IV_money);
                                }
                            }, 600);
                        }
                    });
                } else if (mMyWinMoney == 0) {
                    if (!isOne) {
                        mGowBoyFrament.shouNextGame(true, mBanker_win_num, mMyWinMoney, null);
                    } else {
                        // 游戏即将开始
                        mGowBoyFrament.shouOneWaitNextGame();
                    }
                } else {//输
                    GameCowboySound.getInstance().playSound(getContext(), GameCowboySound.GAME_COWBOY_WIN_MONEY, 0, isPublish);
                    mGowBoyFrament.shouNextGame(false, mBanker_win_num, mMyWinMoney, new GameAction().new One<View>() {
                        @Override
                        public void invoke(final View moenyView) {

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GameAnimUtil.startWinAnim(root_container, IV_money, moenyView);
                                }
                            }, 300);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GameAnimUtil.startWinAnim(root_container, IV_money, moenyView);
                                }
                            }, 400);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GameAnimUtil.startWinAnim(root_container, IV_money, moenyView);
                                }
                            }, 500);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GameAnimUtil.startWinAnim(root_container, IV_money, moenyView);
                                }
                            }, 600);
                        }


                    });
                }
                mMyWinMoney = 0;
            }
        }, showResultTiem);

        //默认10       // 启动收牌
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //更新庄家钱
                tv_banker_money.setText(GameUtil.formatBetText(mBanker_diamond));
                mGowBoyFrament.heidResult();
                mGowBoyFrament.heidTime();
                result_left.setVisibility(GONE);
                result_ping.setVisibility(GONE);
                result_right.setVisibility(GONE);
                result_banker.setVisibility(GONE);
                showShouPaiView();
            }
        }, showResultTiem + 4000);//默认10
    }

    /**
     * 庄家上庄的数据
     *
     * @param nextBenker
     */
    @Override
    protected void doNextBenker(NextBenkerPush nextBenker) {
        super.doNextBenker(nextBenker);
        mBanker_diamond = nextBenker.getBanker_diamond();
        mBanker_account = nextBenker.getBanker_account();
        GameObserver.getAppObserver().loaderShortImage(iv_banker_icom, nextBenker.getBanker_header());
        tv_banker_money.setText(GameUtil.formatBetText(mBanker_diamond));
        tv_banker_name.setText(nextBenker.getBanker_nickname());
        ToastUtil.show(nextBenker.getBanekrMsg());
    }

    @Override
    protected void doGameResult(final GameResultPush gameResultPush) {
        super.doGameResult(gameResultPush);
        this.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gameResultPush.getWin_money() > 0 && !isPublish) {
                    if (gameResultPush.getGift_info() != null || gameResultPush.getGift_info().size() == 3) {
                        winTip(gameResultPush.getWin_money(), gameResultPush.getGift_info(), mGameSound, false);
                    }
                }
            }
        }, time_win_delay);
    }

    private void pokerTypeParser(ImageView view, GameItemInfo info) {
        if (view.getVisibility() != VISIBLE) {
            view.setVisibility(VISIBLE);
        }

        // 解析牌型

        //赢钱
        if (info.getWin() == KAIPAIWIN) {
            switch (info.getPoker_type()) {
                case Poker.NN_NIU0:
                    view.setImageResource(R.drawable.game_cowboy_no_bull_player_win);
                    break;
                case Poker.NN_NIU1:
                    view.setImageResource(R.drawable.game_cowboy_one_player_win);
                    break;
                case Poker.NN_NIU2:
                    view.setImageResource(R.drawable.game_cowboy_two_plaer_win);
                    break;
                case Poker.NN_NIU3:
                    view.setImageResource(R.drawable.game_cowboy_three_player_win);
                    break;
                case Poker.NN_NIU4:
                    view.setImageResource(R.drawable.game_cowboy_four_player_win);
                    break;
                case Poker.NN_NIU5:
                    view.setImageResource(R.drawable.game_cowboy_five_player_win);
                    break;
                case Poker.NN_NIU6:
                    view.setImageResource(R.drawable.game_cowboy_six_player_win);
                    break;
                case Poker.NN_NIU7:
                    view.setImageResource(R.drawable.game_cowboy_seven_player_win);
                    break;
                case Poker.NN_NIU8:
                    view.setImageResource(R.drawable.game_cowboy_eight_player_win);
                    break;
                case Poker.NN_NIU9:
                    view.setImageResource(R.drawable.game_cowboy_nine_player_win);
                    break;
                case Poker.NN_NIU10:
                    view.setImageResource(R.drawable.game_cowboy_ten_player_win);
                    break;
                default:
                    view.setImageResource(R.drawable.game_cowboy_no_bull_player_win);
                    break;
            }
        } else {
            //输钱
            switch (info.getPoker_type()) {
                case Poker.NN_NIU0:
                    view.setImageResource(R.drawable.game_cowboy_no_bull_player_fail);
                    break;
                case Poker.NN_NIU1:
                    view.setImageResource(R.drawable.game_cowboy_one_player_fail);
                    break;
                case Poker.NN_NIU2:
                    view.setImageResource(R.drawable.game_cowboy_two_player_fail);
                    break;
                case Poker.NN_NIU3:
                    view.setImageResource(R.drawable.game_cowboy_three_player_fail);
                    break;
                case Poker.NN_NIU4:
                    view.setImageResource(R.drawable.game_cowboy_four_player_fail);
                    break;
                case Poker.NN_NIU5:
                    view.setImageResource(R.drawable.game_cowboy_five_player_fail);
                    break;
                case Poker.NN_NIU6:
                    view.setImageResource(R.drawable.game_cowboy_six_player_fail);
                    break;
                case Poker.NN_NIU7:
                    view.setImageResource(R.drawable.game_cowboy_seven_player_fail);
                    break;
                case Poker.NN_NIU8:
                    view.setImageResource(R.drawable.game_cowboy_eight_player_fail);
                    break;
                case Poker.NN_NIU9:
                    view.setImageResource(R.drawable.game_cowboy_nine_player_fail);
                    break;
                case Poker.NN_NIU10:
                    view.setImageResource(R.drawable.game_cowboy_ten_player_fail);
                    break;
                default:
                    view.setImageResource(R.drawable.game_cowboy_no_bull_player_fail);
                    break;
            }
        }

        GameAnimUtil.toBigAnim(view);
    }


    /**
     * 庄家 结果
     *
     * @param view
     * @param info
     */
    private void bankerPokerTypeParser(ImageView view, GameItemInfo info) {
        if (view.getVisibility() != VISIBLE) {
            view.setVisibility(VISIBLE);
        }
        // 解析牌型
        switch (info.getPoker_type()) {
            case Poker.NN_NIU0:
                view.setImageResource(R.drawable.game_cowboy_no_bull_banker);
                break;
            case Poker.NN_NIU1:
                view.setImageResource(R.drawable.game_cowboy_one_banker);
                break;
            case Poker.NN_NIU2:
                view.setImageResource(R.drawable.game_cowboy_two_banker);
                break;
            case Poker.NN_NIU3:
                view.setImageResource(R.drawable.game_cowboy_three_banker);
                break;
            case Poker.NN_NIU4:
                view.setImageResource(R.drawable.game_cowboy_four_banker);
                break;
            case Poker.NN_NIU5:
                view.setImageResource(R.drawable.game_cowboy_five_banker);
                break;
            case Poker.NN_NIU6:
                view.setImageResource(R.drawable.game_cowboy_six_banker);
                break;
            case Poker.NN_NIU7:
                view.setImageResource(R.drawable.game_cowboy_seven_banker);
                break;
            case Poker.NN_NIU8:
                view.setImageResource(R.drawable.game_cowboy_eight_banker);
                break;
            case Poker.NN_NIU9:
                view.setImageResource(R.drawable.game_cowboy_nine_banker);
                break;
            case Poker.NN_NIU10:
                view.setImageResource(R.drawable.game_cowboy_ten_banker);
                break;
            default:
                view.setImageResource(R.drawable.game_cowboy_no_bull_banker);
                break;
        }
        GameAnimUtil.toBigAnim(view);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void oncancelBenkerEvent(CancelBankerEvent nextBenker) {
        if (mBankerListdialog != null) {
            mBankerListdialog.isApplyBanker = false;
        }
    }

    @Override
    protected int getGameType() {
        return GameC.Game.GAME_TYPE_HLNZ;
    }

    @Override
    protected void onDetachedFromWindow() {
        GameCowboySound.getInstance().soundDestroy(isPublish);
        super.onDetachedFromWindow();
    }
}
