package org.clon.apps;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clonapps.R;

public class Setting extends Activity {
	private Button savebtn;
	private EditText ipinput, portinput;
	private Database data;
	private Cursor c1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settingui);
		savebtn = (Button) findViewById(R.id.savebtn);
		ipinput = (EditText) findViewById(R.id.ipinput);
		portinput = (EditText) findViewById(R.id.portinput);
		data = Database.getData(this);
		if (data.open()) {

		} else {
			Toast.makeText(getApplicationContext(), "내부 데이터베이스 에러입니다. 다시키세요",
					Toast.LENGTH_SHORT).show();
		}
		String sql = "select * from setting";
		c1 = data.rawQuery(sql);
		c1.moveToFirst();
		ipinput.setText(c1.getString(0));
		portinput.setText(String.valueOf(c1.getInt(1)));
		savebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String sql = "update setting set ip = '"
						+ ipinput.getText().toString() + "', port = "
						+ Integer.parseInt(portinput.getText().toString())
						+ ";";
				data.exec(sql);
				c1.close();
				finish();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

}
