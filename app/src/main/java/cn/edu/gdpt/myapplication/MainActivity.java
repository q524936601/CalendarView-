package cn.edu.gdpt.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final int SUCCESS = 1;
    private static final int FAIL = -1;
    private CalendarView calendarView;
    private TextView chooseDate;


    private List<information> listdata;
    private static String address = "https://api.jisuapi.com/calendar/query?appkey=99e885df3ecc7add&date=";
    private int[] cDate = CalendarUtil.getCurrentDate();
    private MyHandler handler = new MyHandler();
    private ListView lv_information;
    private Button btn_huangli;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView title = (TextView) findViewById(R.id.title);
        lv_information = findViewById(R.id.lv_information);

        chooseDate = findViewById(R.id.choose_date);

        btn_huangli = findViewById(R.id.huangli);

        btn_huangli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,ZiActivity.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable("list", (Serializable) listdata);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });



   /*     String address = "https://api.jisuapi.com/calendar/query?appkey=99e885df3ecc7add&date=2019-6-18";
        HttpUtils.sendOkhttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                information = new Gson().fromJson(string, information.class);
                information.getResult().getHuangli();
            }
        });*/
        calendarView = (CalendarView) findViewById(R.id.calendar);
        calendarView
                .setStartEndDate("1990.1", "2099.12")
                .setDisableStartEndDate("1990.1", "2099.12")
                .setInitDate(cDate[0] + "." + cDate[1])
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                .init();

        title.setText(cDate[0] + "年" + cDate[1] + "月");





        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
            }
        });
        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                title.setText(date.getSolar()[0] + "年" + date.getSolar()[1] + "月");
                chooseDate.setText(date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
                String data = date.getSolar()[0] + "-" + date.getSolar()[1] + "-" + date.getSolar()[2];
                if (date.getType() == 1) {

                    HttpUtils.sendOkhttpRequest(address + data, new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String json = response.body().string();
                            Message message = new Message();
                            message.obj = json;
                            message.what = SUCCESS;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message message = new Message();
                            message.what = FAIL;
                            handler.sendMessage(message);
                        }
                    });
                }
            }
        });
    }

    public void someday(View v) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.input_layout, null);
        final EditText year = (EditText) view.findViewById(R.id.year);
        final EditText month = (EditText) view.findViewById(R.id.month);
        final EditText day = (EditText) view.findViewById(R.id.day);

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
                        if (!result) {
                            Toast.makeText(MainActivity.this, "日期越界！", Toast.LENGTH_SHORT).show();
                        } else {
                            chooseDate.setText("当前选中的日期：" + year.getText() + "年" + month.getText() + "月" + day.getText() + "日");
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    public void today(View view) {
        calendarView.today();
        chooseDate.setText( cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");
    }

    public void lastMonth(View view) {
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

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    listdata = new ArrayList<>();
                    listdata.clear();
                    String json = (String) msg.obj;
                    information information = new Gson().fromJson(json, cn.edu.gdpt.myapplication.information.class);
                    listdata.add(information);
                    lv_information.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return listdata.size();
                        }

                        @Override
                        public Object getItem(int position) {
                            return listdata.get(position);
                        }

                        @Override
                        public long getItemId(int position) {
                            return position;
                        }

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            ViewHolder viewHolder;
                            if (convertView == null) {
                                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item, null, false);
                                viewHolder = new ViewHolder(convertView);
                                convertView.setTag(viewHolder);
                            }else {
                                viewHolder = (ViewHolder) convertView.getTag();
                            }
                            cn.edu.gdpt.myapplication.information.ResultBean result = listdata.get(position).getResult();
                            viewHolder.layear.setText(result.getHuangli().getNongli());
                            viewHolder.week.setText(result.getWeek());
                            viewHolder.tiangan.setText(result.getGanzhi());
                            viewHolder.textDate.setText(result.getDay());
                            viewHolder.suici.setText(result.getHuangli().getSuici().toString());
                            return convertView;
                        }
                        class ViewHolder {
                            public View rootView;
                            public TextView layear;
                            public TextView week;
                            public TextView tiangan;
                            public TextView textDate;
                            public TextView suici;

                            public ViewHolder(View rootView) {
                                this.rootView = rootView;
                                this.layear = (TextView) rootView.findViewById(R.id.layear);
                                this.week = (TextView) rootView.findViewById(R.id.week);
                                this.tiangan=(TextView)rootView.findViewById(R.id.tiangan);
                                this.textDate=(TextView)rootView.findViewById(R.id.textDate);
                                this.suici=(TextView)rootView.findViewById(R.id.suici);
                            }
                        }
                    });
                    break;
                case FAIL:
                    Toast.makeText(getApplicationContext(), "获取数据失败，请检查网络连接！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
