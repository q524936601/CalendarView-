package cn.edu.gdpt.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView chooseDate;
    
    private int[]cDate= CalendarUtil.getCurrentDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView title = (TextView)findViewById(R.id.title);
        chooseDate = findViewById(R.id.choose_date);
        /*String address = ""
        HttpUtils.sendOkhttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });*/
        calendarView = (CalendarView)findViewById(R.id.calendar);
        calendarView
                .setStartEndDate("1990.1","2099.12")
                .setDisableStartEndDate("1990.1","2099.12")
                .setInitDate(cDate[0]+"."+cDate[1])
                .setSingleDate(cDate[0]+"."+cDate[1]+"."+cDate[2])
                .init();
        
        title.setText(cDate[0]+"年"+cDate[1]+"月");
        chooseDate.setText("当前选中的日期："+cDate[0]+cDate[1]+"月"+cDate[2]+"日");
        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0]+"年"+date[1]+"月");
            }
        });
        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                title.setText(date.getSolar()[0]+"年"+date.getSolar()[1]+"月");
                if (date.getType()==1){
                    chooseDate.setText("当前选中的日期："+date.getSolar()[0]+"年"+date.getSolar()[1]+
                            "月"+date.getSolar()[2]+"日");
                }
            }
        });
    }
    public void someday(View v){
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.input_layout,null);
        final EditText year = (EditText)view.findViewById(R.id.year);
        final EditText month = (EditText)view.findViewById(R.id.month);
        final EditText day = (EditText)view.findViewById(R.id.day);
        
        new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(year.getText())
                        || TextUtils.isEmpty(month.getText())
                        || TextUtils.isEmpty(day.getText())) {
                            Toast.makeText(MainActivity.this, "请完善信息", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        boolean result = calendarView.toSpecifyDate(Integer.valueOf(year.getText().toString()),
                                Integer.valueOf(month.getText().toString()),
                                Integer.valueOf(day.getText().toString()));
                        if (!result){
                            Toast.makeText(MainActivity.this,"日期越界！",Toast.LENGTH_SHORT).show();
                        }else {
                            chooseDate.setText("当前选中的日期："+year.getText()+"年"+month.getText()+"月"+day.getText()+"日");
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消",null).show();
    }
    public void today(View view){
        calendarView.today();
        chooseDate.setText("当前选中的日期："+cDate[0]+"年"+cDate[1]+"月"+cDate[2]+"日");
    }
    public void lastMonth(View view){
        calendarView.lastMonth();
    }
    public void nextMonth(View view) {
        calendarView.nextMonth();
    }

    public void start(View view) {
        calendarView.toStart();
    }

    public void end(View view) {
        calendarView.toEnd();
    }

    public void lastYear(View view) {
        calendarView.lastYear();
    }

    public void nextYear(View view) {
        calendarView.nextYear();
    }
}
