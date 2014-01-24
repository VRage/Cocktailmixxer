package projekt.Jakob;

import java.io.IOException;
import java.util.List;

import projekt.OwnList.CustomListViewAdapter;
import projekt.OwnList.RowItem;
import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Saft;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.cocktailmixxer.R;

//@ Project : Cocktailmixxer
//@ Date : 31.10.2013
//@ Author : Jakob Nisin
public class SelectSaftActivity extends Activity {
	CM_Status status;
	List<RowItem> safte;
	List<RowItem> safte_intern;
	ListView saftlist_all;
	CustomListViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_saft);
		status = (CM_Status) getApplicationContext();
		saftlist_all = (ListView) findViewById(R.id.selectsaft_saftlistall);
		safte = status.get_SaftList_all();
		safte_intern = status.get_SaftList_intern();
		adapter = new CustomListViewAdapter(this, R.layout.activity_listitem,
				safte);
		saftlist_all.setAdapter(adapter);

		saftlist_all.setOnItemClickListener(new OnItemClickListener() {

			Intent sender = getIntent();
			String extraData = sender.getExtras().getString("Bottlenumber");
			int bottlenumber = Integer.parseInt(extraData);

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				boolean hat = false;
				for (int j = 0; j < safte_intern.size(); j++) {
					if (status.get_SaftList_intern().contains(
							status.get_SaftList_all().get(arg2))) {
						Toast.makeText(getApplicationContext(),
								"Saft schon in der Liste enthalten",
								Toast.LENGTH_SHORT).show();
						hat = true;
						break;

					}
				}
					if (!hat) {
						safte_intern.set(bottlenumber - 1, status
								.get_SaftList_all().get(arg2));
						try {
							status.saveToSerFile("Saftintern", status.get_SaftList_intern());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						finish();
					}
				
			}
		});
	}

}
