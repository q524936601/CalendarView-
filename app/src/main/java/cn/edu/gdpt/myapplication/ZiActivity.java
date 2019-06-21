package cn.edu.gdpt.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ZiActivity extends AppCompatActivity {

    private static final int SUCCESS = 1;
    private static final int FAIL = -1;
    private List<information> listdata2;
    private ListView lv_information2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi);

        Bundle list = getIntent().getExtras();
        listdata2 = (List<information>) list.getSerializable("list");
        lv_information2 = findViewById(R.id.lv_information2);
        lv_information2.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return listdata2.size();
            }

            @Override
            public Object getItem(int position) {
                return listdata2.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(ZiActivity.this).inflate(R.layout.list_item2, null, false);
                    viewHolder = new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                information.ResultBean result = listdata2.get(position).getResult();
                viewHolder.lucky.setText(result.getHuangli().getJi().toString());  //吉
                viewHolder.fierce.setText(result.getHuangli().getXiongshen());     //凶
                viewHolder.yi.setText(result.getHuangli().getYi().toString());     //宜
                viewHolder.ji.setText(result.getHuangli().getJi().toString());     //忌

                return convertView;

            }

            class ViewHolder {
                public View rootView;
                public TextView lucky;
                public TextView fierce;
                public TextView yi;
                public TextView ji;


                public ViewHolder(View rootView) {
                    this.rootView = rootView;
                    this.lucky = (TextView) rootView.findViewById(R.id.lucky);
                    this.fierce = (TextView) rootView.findViewById(R.id.fierce);
                    this.yi = (TextView) rootView.findViewById(R.id.yi);
                    this.ji = (TextView) rootView.findViewById(R.id.ji);

                }
            }
        });

    }
}
