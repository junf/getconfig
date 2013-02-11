package com.example.getconfig;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
		Configuration config = getResources().getConfiguration();
		Field[] fields = config.getClass().getDeclaredFields();
		StringBuilder str = new StringBuilder();
		try {
			for(Field field: fields) {
				final int modifier = field.getModifiers();
				if (!Modifier.isStatic(modifier)) {
					str.append(field.getName());
					str.append(":");
					str.append(field.get(config));
					str.append("\n");
				}
			}
		} catch (IllegalAccessException e) {
			//I don' think if throwing this exception is appropriate.
			throw new RuntimeException(e);
		}
		TextView tv = (TextView)findViewById(R.id.text_config);
		tv.setText(str.toString());
	}
}
