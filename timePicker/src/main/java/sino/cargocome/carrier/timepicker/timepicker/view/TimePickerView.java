package sino.cargocome.carrier.timepicker.timepicker.view;

import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import sino.cargocome.carrier.timepicker.R;
import sino.cargocome.carrier.timepicker.timepicker.listener.OnYearChanged;


/**
 * 时间选择器
 * Created by Sai on 15/11/22.
 */
public class TimePickerView extends MyBasePickerView implements View.OnClickListener {

    private Button mBtTitle;
    private ImageView mBtCancle;
    private Button mBtOk;
    private ImageView mReduceYear;
    public TextView mTvYear;
    private ImageView mAddYear;
    private int mStartYear=1900;
    private int mEndYear=2050;
    private int mCurrentYear;
    private OnYearChanged mOnYearChanged;


    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN , YEAR_MONTH
    }// 四种选择模式，年月日时分，年月日，时分，月日时分

    WheelTime wheelTime;
//    private View btnSubmit, btnCancel;
//    private Button tvTitle;
    private static final String btntime = "btntime";
    private static final String TAG_CANCEL = "cancel";
    private OnTimeSelectListener timeSelectListener;

    public TimePickerView(Context context, Type type) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.pickerview_time, contentContainer);
        // -----确定和取消按钮
        initView();
        initListener();
        //顶部标题
        // ----时间转轮
        final View timepickerview = findViewById(R.id.timepicker);
        wheelTime = new WheelTime(timepickerview, type);

    }

    private void initView() {
        mBtTitle = (Button) findViewById(R.id.btTitle);
        mBtCancle = (ImageView) findViewById(R.id.btnCancel);
        mBtOk = (Button) findViewById(R.id.bt_ok);
        mReduceYear = (ImageView) findViewById(R.id.reduce_year);
        mTvYear = (TextView) findViewById(R.id.tv_year);
        mAddYear = (ImageView) findViewById(R.id.add_year);
        Time t =new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        mCurrentYear = t.year;
    }

    private void initListener() {
        mBtCancle.setOnClickListener(this);
        mBtOk.setOnClickListener(this);
        mReduceYear.setOnClickListener(this);
        mAddYear.setOnClickListener(this);
    }

    /**
     * 设置可以选择的时间范围
     * 要在setTime之前调用才有效果
     * @param startYear 开始年份
     * @param endYear 结束年份
     */
    public void setRange(int startYear, int endYear) {
        mStartYear = startYear;
        mEndYear = endYear;
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnCancel) {
            dismiss();
            timeSelectListener.onCancel();
            return;
        } else if (id == R.id.bt_ok) {
            if (timeSelectListener != null) {
                try {
                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                    timeSelectListener.onTimeSelect(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dismiss();
            return;
        } else if (id == R.id.reduce_year) {
            reduceYear();
        } else if (id == R.id.add_year) {
            addYear();
        }
    }

    public void addYear() {
        if (mCurrentYear < mEndYear) {
            mCurrentYear++;
        }
        setCurrentYear(mCurrentYear);
    }

    public void reduceYear() {
        if (mCurrentYear > mStartYear) {
            mCurrentYear--;
        }
        setCurrentYear(mCurrentYear);
    }

    public void setCurrentYear(int currentYear) {
        mTvYear.setText(currentYear + "年");
        mOnYearChanged.OnYearChanged(currentYear);
    }

    public void setOnYearChanged(OnYearChanged onYearChanged) {
        mOnYearChanged = onYearChanged;
    }

    /**
     * 设置选中时间
     * @param date 时间
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mTvYear.setText(mCurrentYear + "年");
        wheelTime.setPicker(mCurrentYear, month, day, hours, minute,this);
    }

//    /**
//     * 指定选中的时间，显示选择器
//     *
//     * @param date
//     */
//    public void show(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        if (date == null)
//            calendar.setTimeInMillis(System.currentTimeMillis());
//        else
//            calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int hours = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//        wheelTime.setPicker(year, month, day, hours, minute);
//        show();
//    }

    /**
     * 设置是否循环滚动
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }



    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
        void onCancel();
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    public void setTitle(String title){
        mBtTitle.setText(title);
    }

    public void setTitleColor(int colorId) {
        mBtTitle.setTextColor(colorId);
    }
}
