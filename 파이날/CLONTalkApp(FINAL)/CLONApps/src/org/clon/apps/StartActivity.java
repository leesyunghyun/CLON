package org.clon.apps;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.example.clonapps.R;

public class StartActivity extends Activity implements View.OnClickListener {
	public static EditText idinput, pwdinput;
	private Button loginbtn, exitbtn;
	private BufferedWriter bw;
	private BufferedReader in;
	private CheckBox savecheck;
	ConnectThread loginth;
	Database data;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startui);
		idinput = (EditText) findViewById(R.id.idinput);
		pwdinput = (EditText) findViewById(R.id.pwdinput);
		loginbtn = (Button) findViewById(R.id.loginbtn);
		exitbtn = (Button) findViewById(R.id.exitbtn);
		savecheck = (CheckBox) findViewById(R.id.savecheck);
		loginbtn.setOnClickListener(this);
		exitbtn.setOnClickListener(this);
		Dataselect();

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.loginbtn) {
			loginth = new ConnectThread(this, handler);
			if (savecheck.isChecked()) {
				save(true);
			} else {
				save(false);
			}

			loginth.start();

		} else {
			startActivity(new Intent(StartActivity.this, Setting.class));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	public void save(boolean flag) {
		if (flag) {
			data.open();
			String sql = "update saved set id = '"
					+ idinput.getText().toString() + "', pw = '"
					+ pwdinput.getText().toString() + "', flag = 'o';";
			data.exec(sql);
			data.close();
		} else {
			data.open();
			String sql = "update saved set id = '"
					+ idinput.getText().toString() + "', pw = '"
					+ pwdinput.getText().toString() + "', flag = 'x';";
			data.exec(sql);
			data.close();
		}
	}

	@Override
	protected void onDestroy() {
		data.open();
		if (savecheck.isChecked()) {
			save(true);
		} else {
			save(false);
		}
		super.onDestroy();
	}

	public void Dataselect() {
		data = Database.getData(this);
		if (data.open()) {
			String sql = "select * from saved where flag = 'o';";
			Cursor c1 = data.rawQuery(sql);
			c1.moveToFirst();
			if (c1.getCount() >= 1) {
				idinput.setText(c1.getString(0));
				pwdinput.setText(c1.getString(1));
				savecheck.setChecked(true);
				c1.close();
			} else {
				savecheck.setChecked(false);
				c1.close();

			}
		}
	}
}
