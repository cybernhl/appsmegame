package weking.lib.game.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import weking.lib.game.R;


public class MyBaseTipDialog extends Dialog {
    private TextView tv_cancel;
    private TextView tv_quit;
    private TextView tv_online;
    private TextView textView_title;
    private OnOkButtonListener onOkButtonListener;
    private OnCancelListener onCancelListener;

    public MyBaseTipDialog(Context context) {
        super(context, R.style.TransparentDialogStyle);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_live_room_end_live);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        textView_title = (TextView) findViewById(R.id.textView_title);
        tv_quit = (TextView) findViewById(R.id.tv_quit);
        tv_online = (TextView) findViewById(R.id.tv_online);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelListener != null) {
                    onCancelListener.onClick();
                }
                dismiss();
            }
        });
        tv_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOkButtonListener != null) {
                    onOkButtonListener.onClick();
                }
            }
        });
    }

    public void setMessage(int resId) {
        tv_online.setText(resId);
    }

    public void setMsgGravity(int gravity) {
        tv_online.setGravity(gravity);
    }

    public void setMessage(String msg) {
        tv_online.setText(msg);
    }

    public void setTitleTxt(int resId) {
        textView_title.setText(resId);
    }

    public void setOkButtonText(int resId) {
        tv_quit.setText(resId);
    }

    public void setCancelButtonText(int resId) {
        tv_cancel.setText(resId);
    }

    // 隐藏取消 ,只留确定
    public void setCancelButtonGone() {
        tv_cancel.setVisibility(View.GONE);
    }

    public  void setBackFutility(){
        setCanceledOnTouchOutside(false);//按返回键还起作用
        this. setCancelable(false);//按返回键不起作用
    }

    public void setOnOkButtonListener(OnOkButtonListener okButtonListener) {
        this.onOkButtonListener = okButtonListener;
    }

    public interface OnOkButtonListener {
        void onClick();
    }

    public interface OnCancelListener {
        void onClick();
    }

}
