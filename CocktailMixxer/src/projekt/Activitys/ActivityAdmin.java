package projekt.Activitys;

import projekt.helpclasses.BluetoothSerialService;
import projekt.helpclasses.CM_Status;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.cocktailmixxer.R;

public class ActivityAdmin extends Activity{
	CM_Status status;
	BluetoothSerialService service;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		status = (CM_Status) getApplicationContext();
		service = status.getBTservice();
	}

	
	public void test(View view){
			String message = "teststring";
	        // Check that there's actually something to send
	        if (message.length() > 0) {
	            // Get the message bytes and tell the BluetoothChatService to write
	            byte[] send = message.getBytes();
	            service.write(send);

	        }
	    }
	
}
