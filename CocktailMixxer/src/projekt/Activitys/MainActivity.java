/*		Activity- 		For Activity Classes
 * 		btn_ - 			For Button Ids and Button Methods
 * 		button - 		For Button Strings
 * 
 */
package projekt.Activitys;

import java.util.List;

import projekt.Jakob.ActivityAdmin;
import projekt.Jakob.ActivityBluetooth;
import projekt.Jakob.ActivityUser;
import projekt.MAtze.ActivityCocktailList;
import projekt.MAtze.ActivityNewCocktail;
import projekt.OwnList.CustomListViewAdapter;
import projekt.OwnList.RowItem;
import projekt.helpclasses.BluetoothSerialService;
import projekt.helpclasses.CM_Status;
import projekt.helpclasses.User;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cocktailmixxer.R;

public class MainActivity extends Activity {
	
	
	// Layout view
	private TextView mTitle;
	
	// Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
    // Key names received from the BluetoothCommandService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
	
	// Name of the connected device
    private String mConnectedDeviceName = null;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for Bluetooth Command Service
    private BluetoothSerialService mCommandService = null;
	
	
	
	@Override
	protected void onStart() {
		super.onStart();
		if (mCommandService==null)
			setupCommand();
	}

	CM_Status status;
	CustomListViewAdapter adapter;
	Spinner spinnerUser;
	List<RowItem> users;
	public final static String APP_PATH_SD_APPLICATION = "/cocktailmixxer/";
	static String fullPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + APP_PATH_SD_APPLICATION;

	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.context_menu, menu);

		return true;// return true so to menu pop up is opens

	}

	@Override
	protected void onStop() {
		status.saveAll();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
		if (users.isEmpty()) {
			spinnerUser.setVisibility(View.INVISIBLE);
		} else {
			spinnerUser.setVisibility(View.VISIBLE);
			spinnerUser.setSelection(users.indexOf(status.get_ActiveUser()));
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
        // Set up the custom title
		mTitle = (TextView) findViewById(R.id.title_right_text);
        mTitle.setText(R.string.app_name);
  	
		status = (CM_Status) getApplicationContext();
		status.loadAll();
		users = status.get_UserList();
		spinnerUser = (Spinner) findViewById(R.id.main_spinnerUsers);
		adapter = new CustomListViewAdapter(this, R.layout.activity_listitem,
				users);
		spinnerUser.setAdapter(adapter);
		spinnerUser.setSelection(adapter.getPosition(status
				.get_ActiveUser()));
		// Define Button **Cocktails** and On Click Action
		Button btn_CocktailListe = (Button) findViewById(R.id.main_btnCocktails);
		btn_CocktailListe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this,
						ActivityCocktailList.class)); // btnPressedCockTailsList();
			}
		});

		Button btn_User = (Button) findViewById(R.id.btn_User);
		btn_User.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, ActivityUser.class)); // btnPressedCockTailsList();
			}
		});

		// Define Button **Connect** and On Click Action
		Button btn_Bluetooth = (Button) findViewById(R.id.main_btnConnect);
		btn_Bluetooth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivityForResult(new Intent(MainActivity.this,
						ActivityBluetooth.class), 1);
			}

		});
		spinnerUser.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {
					status.set_ActiveUser((User) spinnerUser.getSelectedItem());

				} catch (Exception e) {

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

		Button btn_NewCocktail = (Button) findViewById(R.id.main_btnNewCocktail);
		btn_NewCocktail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this,
						ActivityNewCocktail.class)); // btnPressedCockTailsList();
			}

		});

	}

	public void gotoBenutzer() {
		setContentView(R.layout.activity_user);
	}
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.admin:
        	startActivityForResult((new Intent(MainActivity.this, ActivityAdmin.class)),0);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }


//	public static boolean saveStatus(CM_Status status) {
//		try {
//			File statusFile = new File(fullPath, "cm_status.dat");
//			statusFile.createNewFile();
//			FileOutputStream fos = new FileOutputStream(statusFile);
//			ObjectOutputStream oos = new ObjectOutputStream(fos);
//			oos.writeObject(status);
//			oos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//
//		return true;
//	}
//
//	public static CM_Status getStatus() { // Changed new method
//		File statusFile = new File(fullPath, "cm_status.dat");
//		if (statusFile.exists()) {
//			try {
//
//				FileInputStream fis = new FileInputStream(statusFile);
//				ObjectInputStream is = new ObjectInputStream(fis);
//				Object readObject = is.readObject();
//				is.close();
//
//				if (readObject != null && readObject instanceof CM_Status) {
//					return (CM_Status) readObject;
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
	private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                switch (msg.arg1) {
                case BluetoothSerialService.STATE_CONNECTED:
                    mTitle.setText(R.string.title_connected_to);
                    mTitle.append(mConnectedDeviceName);
                    mTitle.setTextColor(Color.GREEN);
                    break;
                case BluetoothSerialService.STATE_CONNECTING:
                    mTitle.setText(R.string.title_connecting);
                    mTitle.setTextColor(Color.YELLOW);
                    break;
                case BluetoothSerialService.STATE_LISTEN:
                case BluetoothSerialService.STATE_NONE:
                    mTitle.setText(R.string.title_not_connected);
                    mTitle.setTextColor(Color.RED);
                    break;
                }
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(ActivityBluetooth.EXTRA_DEVICE_ADDRESS);
                // Get the BLuetoothDevice object
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                mCommandService.connect(device);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                setupCommand();
            } else {
                // User did not enable Bluetooth or an error occured
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
	private void setupCommand() {
		// Initialize the BluetoothChatService to perform bluetooth connections
        mCommandService = new BluetoothSerialService(this, mHandler);
        status.setBTservice(mCommandService);
	}
}