package edu.csu.remote;

import edu.csu.mobiVod.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SelectPicPopupWindow extends Activity implements OnClickListener {

    private Button btn_delete_remote, btn_edit_remote, btn_cancel;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        btn_delete_remote = (Button) this.findViewById(R.id.btn_delete_remote);
        btn_edit_remote = (Button) this.findViewById(R.id.btn_edit_remote);
        btn_cancel = (Button) this.findViewById(R.id.btn_cancel);

        layout = (LinearLayout) findViewById(R.id.pop_layout);

        //���ѡ�񴰿ڷ�Χ�����������Ȼ�ȡ���㣬������ִ��onTouchEvent()��������������ط�ʱִ��onTouchEvent()��������Activity
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "��ʾ����������ⲿ�رմ��ڣ�",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //��Ӱ�ť����
        btn_cancel.setOnClickListener(this);
        btn_edit_remote.setOnClickListener(this);
        btn_delete_remote.setOnClickListener(this);
    }

    //ʵ��onTouchEvent���������������Ļʱ���ٱ�Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete_remote:
                break;
            case R.id.btn_edit_remote:
                break;
            case R.id.btn_cancel:
                break;
            default:
                break;
        }
        finish();
    }

}
