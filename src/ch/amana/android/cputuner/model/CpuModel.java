package ch.amana.android.cputuner.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import ch.amana.android.cputuner.helper.Logger;
import ch.amana.android.cputuner.provider.db.DB;

public class CpuModel {

	public static final String NO_VALUE_STR = "None";

	public static final int NO_VALUE_INT = -1;

	private String profileName = NO_VALUE_STR;

	private long id = -1;

	private String gov = NO_VALUE_STR;
	private int maxFreq = NO_VALUE_INT;
	private int minFreq = NO_VALUE_INT;

	private int wifiState = 0;
	private int gpsState = 0;
	private int bluetoothState = 0;
	private int mobiledataState = 0;

	public CpuModel() {
		super();
	}

	public CpuModel(String gov, int maxFreq, int minFreq) {
		this();
		this.gov = gov;
		this.maxFreq = maxFreq;
		this.minFreq = minFreq;
	}

	public CpuModel(Cursor c) {
		this();
		this.id = c.getLong(DB.INDEX_ID);
		this.profileName = c.getString(DB.CpuProfile.INDEX_PROFILE_NAME);
		this.gov = c.getString(DB.CpuProfile.INDEX_GOVERNOR);
		this.maxFreq = c.getInt(DB.CpuProfile.INDEX_FREQUENCY_MAX);
		this.minFreq = c.getInt(DB.CpuProfile.INDEX_FREQUENCY_MIN);
		this.wifiState = c.getInt(DB.CpuProfile.INDEX_WIFI_STATE);
		this.gpsState = c.getInt(DB.CpuProfile.INDEX_GPS_STATE);
		this.bluetoothState = c.getInt(DB.CpuProfile.INDEX_BLUETOOTH_STATE);
		this.mobiledataState = c.getInt(DB.CpuProfile.INDEX_MOBILEDATA_STATE);
	}

	public CpuModel(Bundle bundle) {
		this();
		readFromBundle(bundle);
	}

	public void saveToBundle(Bundle bundle) {
		if (id > -1) {
			bundle.putLong(DB.NAME_ID, id);
		} else {
			bundle.putLong(DB.NAME_ID, -1);
		}
		bundle.putString(DB.CpuProfile.NAME_PROFILE_NAME, getProfileName());
		bundle.putString(DB.CpuProfile.NAME_GOVERNOR, getGov());
		bundle.putInt(DB.CpuProfile.NAME_FREQUENCY_MAX, getMaxFreq());
		bundle.putInt(DB.CpuProfile.NAME_FREQUENCY_MIN, getMinFreq());
		bundle.putInt(DB.CpuProfile.NAME_WIFI_STATE, getWifiState());
		bundle.putInt(DB.CpuProfile.NAME_GPS_STATE, getGpsState());
		bundle.putInt(DB.CpuProfile.NAME_BLUETOOTH_STATE, getBluetoothState());
		bundle.putInt(DB.CpuProfile.NAME_MOBILEDATA_STATE, getMobiledataState());
	}

	public void readFromBundle(Bundle bundle) {
		id = bundle.getLong(DB.NAME_ID);
		profileName = bundle.getString(DB.CpuProfile.NAME_PROFILE_NAME);
		gov = bundle.getString(DB.CpuProfile.NAME_GOVERNOR);
		maxFreq = bundle.getInt(DB.CpuProfile.NAME_FREQUENCY_MAX);
		minFreq = bundle.getInt(DB.CpuProfile.NAME_FREQUENCY_MIN);
		wifiState = bundle.getInt(DB.CpuProfile.NAME_WIFI_STATE);
		gpsState = bundle.getInt(DB.CpuProfile.NAME_GPS_STATE);
		bluetoothState = bundle.getInt(DB.CpuProfile.NAME_BLUETOOTH_STATE);
		mobiledataState = bundle.getInt(DB.CpuProfile.NAME_MOBILEDATA_STATE);
	}

	public ContentValues getValues() {
		ContentValues values = new ContentValues();
		if (id > -1) {
			values.put(DB.NAME_ID, id);
		} else {
			values.put(DB.NAME_ID, -1);
		}

		values.put(DB.CpuProfile.NAME_PROFILE_NAME, getProfileName());
		values.put(DB.CpuProfile.NAME_GOVERNOR, getGov());
		values.put(DB.CpuProfile.NAME_FREQUENCY_MAX, getMaxFreq());
		values.put(DB.CpuProfile.NAME_FREQUENCY_MIN, getMinFreq());
		values.put(DB.CpuProfile.NAME_WIFI_STATE, getWifiState());
		values.put(DB.CpuProfile.NAME_GPS_STATE, getGpsState());
		values.put(DB.CpuProfile.NAME_BLUETOOTH_STATE, getBluetoothState());
		values.put(DB.CpuProfile.NAME_MOBILEDATA_STATE, getMobiledataState());
		return values;
	}

	public String getGov() {
		return gov;
	}

	public void setGov(String gov) {
		this.gov = gov;
	}

	public int getMaxFreq() {
		return maxFreq;
	}

	public void setMaxFreq(int maxFreq) {
		this.maxFreq = maxFreq;
	}

	public int getMinFreq() {
		return minFreq;
	}

	public void setMinFreq(int minFreq) {
		this.minFreq = minFreq;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public long getDbId() {
		return id;
	}

	@Override
	public String toString() {
		return gov + "; " + convertFreq2GHz(minFreq) + " - " + convertFreq2GHz(maxFreq);
	}

	public static String convertFreq2GHz(int freq) {
		try {
			int i = Math.round(freq / 1000f);
			return i + " MHz";
		} catch (Exception e) {
			Log.w(Logger.TAG, "Cannot convert freq", e);
		}
		return "NaN";
	}

	public void setWifiState(int wifiState) {
		this.wifiState = wifiState;
	}

	public int getWifiState() {
		return wifiState;
	}

	public void setGpsState(int gpsState) {
		this.gpsState = gpsState;
	}

	public int getGpsState() {
		return gpsState;
	}

	public void setBluetoothState(int bluetoothState) {
		this.bluetoothState = bluetoothState;
	}

	public int getBluetoothState() {
		return bluetoothState;
	}

	public void setMobiledataState(int mobiledataState) {
		this.mobiledataState = mobiledataState;
	}

	public int getMobiledataState() {
		return mobiledataState;
	}

}
