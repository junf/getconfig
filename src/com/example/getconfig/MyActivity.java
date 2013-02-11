package com.example.getconfig;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyActivity extends Activity {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Configuration config = getResources().getConfiguration();
		displayConfig();
	}

	private void displayConfig() {
		ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Configuration config = getResources().getConfiguration();
		Field[] fields = config.getClass().getDeclaredFields();
		try {
			for(Field field: fields) {
				final int modifier = field.getModifiers();
				if (!Modifier.isStatic(modifier)) {
					Map<String,String> map = new HashMap<String,String>();
					map.put("item_name", field.getName());
					map.put("item_value", field.get(config).toString());
					list.add(map);
				}
			}
		} catch (IllegalAccessException e) {
			//I don' think if throwing this exception is appropriate.
			throw new RuntimeException(e);
		}

		SimpleAdapter adapter = new SimpleAdapter(
				this,
				list,
				R.layout.config_row,
				new String[]{"item_name","item_value"},
				new int[]{R.id.item_name, R.id.item_value});
		ListView view = (ListView)findViewById(R.id.list_config);
		view.setAdapter(adapter);
	}
}
