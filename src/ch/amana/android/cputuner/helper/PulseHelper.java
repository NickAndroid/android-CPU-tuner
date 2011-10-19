package ch.amana.android.cputuner.helper;

import android.content.Context;
import android.content.Intent;
import ch.amana.android.cputuner.hw.PowerProfiles;
import ch.amana.android.cputuner.hw.PowerProfiles.ServiceType;
import ch.amana.android.cputuner.hw.ServicesHandler;
import ch.amana.android.cputuner.service.PulseService;

public class PulseHelper {

	private boolean pulsing = false;

	private boolean pulseOn = false;

	private final Context ctx;

	private boolean pulseBackgroundSyncState = false;

	private boolean pulseBluetoothState = false;

	private boolean pulseGpsState = false;

	private boolean pulseWifiState = false;

	private boolean pulseMobiledataConnectionState = false;

	private boolean pulseAirplanemodeState;

	private static PulseHelper instance;


	public static PulseHelper getInstance(Context ctx) {
		if (instance == null) {
			instance = new PulseHelper(ctx.getApplicationContext());
		}
		return instance;
	}

	static int p = 0;

	public void doPulse(boolean isOn) {
		if (!SettingsStorage.getInstance().isEnableProfiles()) {
			Logger.i("Not pulsing since profiles are not eabled.");
			return;
		}
		if (pulsing) {
			this.pulseOn = isOn;
			Notifier.notifyProfile(PowerProfiles.getInstance().getCurrentProfileName());
			ctx.sendBroadcast(new Intent(Notifier.BROADCAST_PROFILE_CHANGED));
			if ( pulseBackgroundSyncState ) {
				ServicesHandler.enableBackgroundSync(ctx, isOn);
			}
			if (pulseBluetoothState) {
				ServicesHandler.enableBluetooth(isOn);
			}
			if (pulseGpsState) {
				ServicesHandler.enableGps(ctx, isOn);
			}
			if (pulseWifiState) {
				ServicesHandler.enableWifi(ctx, isOn);
			}
			if (pulseAirplanemodeState) {
				ServicesHandler.enableAirplaneMode(ctx, isOn);
			}
			if (pulseMobiledataConnectionState) {
				if (SettingsStorage.getInstance().isPulseMobiledataOnWifi()) {
					ServicesHandler.enableMobileData(ctx, isOn);
				}else {
					if (isOn && ServicesHandler.isWifiConnected(ctx)) {
						Logger.i("Not pulsing mobiledata since wifi is connected");
					}else {
						ServicesHandler.enableMobileData(ctx, isOn);
					}
				}
			}
		}
	}

	private void doPulsing(boolean b) {
		if (pulsing == b) {
			return;
		}
		if (b) {
			pulsing = true;
			Notifier.notifyProfile(PowerProfiles.getInstance().getCurrentProfileName());
			PulseService.startService(ctx);
		} else {
			boolean someService = pulseBackgroundSyncState || pulseBluetoothState || pulseGpsState || pulseWifiState || pulseMobiledataConnectionState;
			if (!someService) {
				pulsing = false;
				Notifier.notifyProfile(PowerProfiles.getInstance().getCurrentProfileName());
				PulseService.stopService(ctx);
			}
		}
	}

	protected PulseHelper(Context context) {
		super();
		this.ctx = context;
	}

	public boolean isPulsing() {
		return pulsing;
	}

	public void pulse(ServiceType type, boolean b) {
		switch (type) {
		case wifi:
			pulseWifiState(b);
			break;
		case bluetooth:
			pulseBluetoothState(b);
			break;
		case mobiledataConnection:
			pulseMobiledataConnectionState(b);
			break;
		case backgroundsync:
			pulseBackgroundSyncState(b);
			break;
		case airplainMode:
			pulseAirplanemodeState(b);
			break;
		case gps:
			pulseGpsState(b);
			break;
		case mobiledata3g:
			Logger.w("Cannot pulse mobiledata 3G");
			break;
		default:
			Logger.e("Did not find service type " + type.toString() + " for pulsing.");
		}
	}

	public void pulseBackgroundSyncState(boolean b) {
		pulseBackgroundSyncState = b;
		doPulsing(b);
	}

	public void pulseBluetoothState(boolean b) {
		pulseBluetoothState = b;
		doPulsing(b);
	}

	public void pulseGpsState(boolean b) {
		pulseGpsState = b;
		doPulsing(b);
	}

	public void pulseWifiState(boolean b) {
		pulseWifiState = b;
		doPulsing(b);
	}

	public void pulseMobiledataConnectionState(boolean b) {
		pulseMobiledataConnectionState = b;
		doPulsing(b);
	}

	public boolean isOn() {
		return pulseOn;
	}

	public void pulseAirplanemodeState(boolean b) {
		pulseAirplanemodeState = b;
		doPulsing(b);
	}

}
