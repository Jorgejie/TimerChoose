package sino.cargocome.carrier.timepicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import sino.cargocome.carrier.timepicker.timepicker.view.TimePickerView;

public class MainActivity extends AppCompatActivity {

    private TextView mTvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvTime = findViewById(R.id.tv_User);
    }

    public void showTime(View view) {
        //时间选择器
        TimePickerView pvTime = new TimePickerView(this, TimePickerView.Type.MONTH_DAY_HOUR_MIN);
        pvTime.setTime(new Date());
        pvTime.setTitle("girl friend NotFoundException");
        pvTime.setCyclic(false);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                mTvTime.setText(getTime(date));
            }

            @Override
            public void onCancel() {

            }
        });
        pvTime.show();
    }
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return format.format(date);
    }
}
