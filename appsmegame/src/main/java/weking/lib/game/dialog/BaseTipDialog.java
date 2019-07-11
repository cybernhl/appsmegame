package weking.lib.game.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import weking.lib.game.R;
import weking.lib.game.utils.GameUtil;


public abstract class BaseTipDialog extends Dialog {

    public static final int NORMAL_TYPE = 0;

    protected Context context;

    public static interface OnSweetClickListener {
        public void onClick(BaseTipDialog sweetAlertDialog);
    }

    public BaseTipDialog(Context context) {
        this(context, NORMAL_TYPE);
        this.context = context;
    }

    protected abstract void doBusiness();

    protected abstract int getContentView();

    public BaseTipDialog(Context context, int alertType) {
        super(context, R.style.VersionDialogStyle);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        doBusiness();
    }


}
