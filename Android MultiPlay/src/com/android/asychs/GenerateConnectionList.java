package com.android.asychs;

import java.util.Collection;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.extendedWidgets.lists.ElementOfConnectionsList;
import com.android.services.ConnectionHelper;

public class GenerateConnectionList extends AsyncTask<String, String, String> {
		 
	private Collection<ElementOfConnectionsList> listOfElements = null;
	private ProgressBar progressBar = null;
			
	public GenerateConnectionList(Collection<ElementOfConnectionsList> listOfElements, ProgressBar progressBar) {
		super();
		this.listOfElements = listOfElements;
		this.progressBar = progressBar;
		Log.d("connections","BT IS OK 1");
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
// aktualizacja stanu podczas dzia�ania, tutaj zmiana tresci textview na inny
// w values b�dziemy przesy�a� tylko jedna wartosc
		}
	 
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
// dzia�ania po wykonaniu zdania w doInBackground
// ta metoda dzia�a w UI, wi�c mo�esz zmienia� r�ne elementy!
		progressBar.setVisibility(ProgressBar.INVISIBLE);
	}
	 
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
// dzia�ania przed wykonaniem zada�
// ta metoda dzia�a w UI, wi�c mo�esz zmienia� r�ne elementy!
	}
	 
	@Override
	protected String doInBackground(String... params) {
// zadanie do wykonania
// tutaj nie mo�esz nic zmienia� w UI, tylko wykonywa� obliczenia i zadania
 
// Dla wydajno�ci zapisujemy wielkosc tablicy przed p�tl�
		Log.d("connections","BT IS OK 2");
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		        listOfElements.add(new ElementOfConnectionsList(
		        		"AAA"+device.getName(), device.getAddress(), ConnectionHelper.STATUS_NOT_IN_RANGE,
						ElementOfConnectionsList.STORED_NO,
						ConnectionHelper.CONNECTION_TYPE_BT));
		    }
		}
		Log.d("connections","BT IS OK 3");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Wykonano wszystkie dzia�ania, ��cznie ";
	}
}
