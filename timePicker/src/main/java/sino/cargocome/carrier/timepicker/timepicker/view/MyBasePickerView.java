package sino.cargocome.carrier.timepicker.timepicker.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import sino.cargocome.carrier.timepicker.R;


/**
 * Created by Sai on 15/11/22.
 * 精仿iOSPickerViewController控件
 */
public class MyBasePickerView {
    private Context context;
    protected ViewGroup contentContainer;
    private View rootView;//附加View 的 根View
    private boolean dismissing;
    private final Display mDisplay;
    private Dialog mDialog;

    public MyBasePickerView(Context context){
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();
        initViews();
        initEvents();
    }

    protected void initViews(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        rootView = layoutInflater.inflate(R.layout.layout_basepickerview, null);
        rootView.setMinimumWidth(mDisplay.getWidth());
        contentContainer = (ViewGroup) rootView.findViewById(R.id.content_container);
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mDialog.setContentView(rootView);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 0;
        window.setAttributes(lp);
        setCancelable(false);
    }

    protected void initEvents() {
    }
    /**
     * 添加这个View到Activity的根视图
     */
    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
        dismissing = true;
    }
    //默认点击不可取消
    public void setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
    }


    public View findViewById(int id){
        return contentContainer.findViewById(id);
    }
}
