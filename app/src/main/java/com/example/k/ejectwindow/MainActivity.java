package com.example.k.ejectwindow;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    private AlertView mAlertViewExt;//窗口拓展例子
    private EditText etName;//拓展View内容
    private InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //拓展窗口
        mAlertViewExt = new AlertView("提示", "请完善你的个人资料！", "取消", null, new String[]{"完成"}, this, AlertView.Style.Alert, this);
        ViewGroup extView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form,null);
        etName = (EditText) extView.findViewById(R.id.etName);
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                boolean isOpen=imm.isActive();
                //输入框出来则往上移动
                mAlertViewExt.setMarginBottom(isOpen&&focus ? 200 :0);
            }
        });
        mAlertViewExt.addExtView(extView);
    }
    public void alertShowExt(View view) {
        mAlertViewExt.show();
    }
    @Override
    public void onItemClick(Object o,int position) {
        closeKeyboard();
        //判断是否是拓展窗口View，而且点击的是非取消按钮
        if(o == mAlertViewExt && position != AlertView.CANCELPOSITION){
            String name = etName.getText().toString();
            if(name.isEmpty()){
                Toast.makeText(this, "啥都没填呢", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "hello,"+name, Toast.LENGTH_SHORT).show();
            }

            return;
        }
        Toast.makeText(this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
    }
    private void closeKeyboard() {
        //关闭软键盘
        imm.hideSoftInputFromWindow(etName.getWindowToken(),0);
        //恢复位置
        mAlertViewExt.setMarginBottom(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getRepeatCount()==0){
            mAlertViewExt.setMarginBottom(0);
        }
        return super.onKeyDown(keyCode, event);
    }
}
