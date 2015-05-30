package cz.nkgroup;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class HelpActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_layout);
		TextView help = (TextView) findViewById(R.id.help_text);
		try {
	        Resources res = getResources();
	        InputStream in_s = res.openRawResource(R.raw.help);

	        byte[] b = new byte[in_s.available()];
	        in_s.read(b);
	        help.setText(Html.fromHtml(new String(b)));
	       
	    } catch (Exception e) {
	        // e.printStackTrace();
	    }

		
		
		
	}

	public void onBackPressed() {
		this.finish();
		Intent intent = new Intent(this, cz.nkgroup.MainActivity.class);
		startActivity(intent);
	}

}
