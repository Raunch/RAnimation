package com.raunch.ranimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    
    private static long tempTime = System.currentTimeMillis();
    // 图片封装为一个数组
    private int[] icon = { R.drawable.hello, R.drawable.hello2,
    		R.drawable.hello, R.drawable.hello2,
    		R.drawable.hello, R.drawable.hello2,
    		R.drawable.hello, R.drawable.hello2};
    private String[] iconName = { "通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
            "设置"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gview = (GridView) findViewById(R.id.gview);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);
    }

    
    
    public List<Map<String, Object>> getData(){        
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
            
        return data_list;
    }



	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		
		final long currentTime;
		if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
			Log.i("fucking", "get it down");
			Log.i("fucking", "temp time is " + tempTime);
			currentTime = System.currentTimeMillis();
			Log.i("fucking", "current time is " + currentTime);
			Log.i("fucking","diff is " + (tempTime - currentTime));
			
			if (Math.abs(tempTime - currentTime) > 20) {
				new Thread() {
					public void run() {
						try {
							Instrumentation inst = new Instrumentation();
							inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
							tempTime = currentTime;
						} catch (Exception e) {
							Log.e("Exception when sendPointerSync", e.toString());
						}
					}
				}.start();
			}
			
			return true;
		} else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {
			Log.i("fucking", "get it up");
			new Thread() {
				public void run() {
					try {
						Instrumentation inst = new Instrumentation();
						inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_UP);
					} catch (Exception e) {
						Log.e("Exception when sendPointerSync", e.toString());
					}
				}
			}.start();
			return true;
		} else {
			return super.dispatchKeyEvent(event);
		}
	}
	
    
    
}