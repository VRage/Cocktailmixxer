package projekt.Activitys;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cocktailmixxer.R;

public class ActivityBluetooth extends Activity {
	BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
	private static final int REQUEST_ENABLE = 0x1;
	private static final int REQUEST_DISCOVERABLE = 0x2;
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	private ArrayAdapter<String> adapter1;
	private ArrayAdapter<String> adapter2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_bluetooth);
		// deklaration der listviews
		ListView founddevices = (ListView) findViewById(R.id.bluetooth_listegefunden);
		ListView paireddevices = (ListView) findViewById(R.id.bluetooth_listpaired);
		// deklaration und zuweisung der Adapter für die listviews
		adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, 0);
		adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, 0);
		founddevices.setAdapter(adapter1);
		paireddevices.setAdapter(adapter2);
		
		Set<BluetoothDevice> pairedDevices = bluetooth.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
			// Loop through paired devices
			for (BluetoothDevice device : pairedDevices) {
				// Add the name and address to an array adapter to show in a
				// ListView
				adapter2.add(device.getName() + "\n" + device.getAddress());
				adapter2.notifyDataSetChanged();
			}
		}

		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);

		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		final Switch switchbluetooth = (Switch) findViewById(R.id.bluetoot_switch);
		switchbluetooth
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Intent enabler = new Intent(
									BluetoothAdapter.ACTION_REQUEST_ENABLE);
							startActivityForResult(enabler, REQUEST_ENABLE);
							
						} else {
							bluetooth.disable();
							
						}
					}
				});
		founddevices.setOnItemClickListener(mDeviceClickListener);
		switchbluetooth.setChecked(bluetooth.isEnabled());
		super.onCreate(savedInstanceState);
	}

	public void searchDevices(View view) {
		
		if (bluetooth.isEnabled()) {
			bluetooth.startDiscovery();
			Toast.makeText(this, "Scanning...", Toast.LENGTH_LONG).show();
		}
	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				adapter1.clear();
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// If it's already paired, skip it, because it's been listed
				// already
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					adapter1.add(device.getName() + "\n" + device.getAddress());
					adapter1.notifyDataSetChanged();
				}

			}
		}
	};

	public void goBack(View view) {
		Intent intent = new Intent(view.getContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Make sure we're not doing discovery anymore
		if (bluetooth != null) {
			bluetooth.cancelDiscovery();
		}

		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
	}
	
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            bluetooth.cancelDiscovery();
            
 
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            BluetoothDevice device = bluetooth.getRemoteDevice(address);
            ConnectThread test =new ConnectThread(device);
            test.start();
        }
    };


	public class ConnectThread extends Thread {
		private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;

		public ConnectThread(BluetoothDevice device) {
			// Use a temporary object that is later assigned to mmSocket,
			// because mmSocket is final
			BluetoothSocket tmp = null;
			mmDevice = device;

			// Get a BluetoothSocket to connect with the given BluetoothDevice
			try {
				// MY_UUID is the app's UUID string, also used by the server
				// code
				tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
			} catch (IOException e) {
			}
			mmSocket = tmp;
		}

		public void run() {
			// Cancel discovery because it will slow down the connection
			bluetooth.cancelDiscovery();

			try {
				// Connect the device through the socket. This will block
				// until it succeeds or throws an exception
				mmSocket.connect();
			} catch (IOException connectException) {
				// Unable to connect; close the socket and get out
				try {
					mmSocket.close();
				} catch (IOException closeException) {
				}
				return;
			}

			// Do work to manage the connection (in a separate thread)
			//manageConnectedSocket(mmSocket);
		}

		/** Will cancel an in-progress connection, and close the socket */
		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
			}
		}
	}
}