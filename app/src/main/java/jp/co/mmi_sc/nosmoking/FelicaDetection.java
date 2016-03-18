package jp.co.mmi_sc.nosmoking;

import java.util.Arrays;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.util.Log;

public class FelicaDetection {
	private static final String TAG = "FelicaDetection"; // TAG

	private Activity mActivity = null;

	private NfcAdapter mNfcAdapter = null;

	private byte[] mCardID = new byte[0];

	/** Status種別 */
	public static final int NFC_ADAPTER_NONE = 0x00; // Nfc Adapter is None.
	public static final int NFC_ADAPTER_DISABLE = 0x01; // Nfc Adapter is Disable.
	public static final int NFC_ADAPTER_ENABLE = 0x02; // Nfc Adapter is Enable.

	/**
	 * コンストラクタ
	 * 
	 * @param activity
	 *            カード検出を行うActivityオブジェクト
	 */
	FelicaDetection(Activity activity) {
		mActivity = activity;
		mNfcAdapter = NfcAdapter.getDefaultAdapter(mActivity);
	}

	/**
	 * NFCが存在する端末か
	 */
	public int isState() {
		if (mNfcAdapter != null) {
			if (mNfcAdapter.isEnabled() == true) {
				return NFC_ADAPTER_ENABLE;
			} else {
				return NFC_ADAPTER_DISABLE;
			}
		} else {
			return NFC_ADAPTER_NONE;
		}
	}

	/**
	 * 検出を有効にする。
	 * 
	 * @param strArray
	 *            検出対象のテクノロジリスト
	 * @param detectionTimeout
	 *            検出タイムアウト時間 (msec)
	 */
	public void enable() {
		if (mNfcAdapter != null) {
			Intent intent = new Intent(mActivity, mActivity.getClass());
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent pendingIntent = PendingIntent.getActivity(
					mActivity.getApplicationContext(), 0, intent, 0);

			mNfcAdapter.enableForegroundDispatch(mActivity, pendingIntent,
					null, null);
		}
	}

	/**
	 * 検出を無効にする。
	 */
	public void disable() {
		if (mNfcAdapter != null) {
			mNfcAdapter.disableForegroundDispatch(mActivity);
		}
	}

	/**
	 * アクティベーションしたデバイスのIDをintentから取得する。
	 * 
	 * @param intent
	 *            インテントオブジェクト
	 */
	public String getCardId(Intent intent) {
		String result = new String("");
		mCardID = new byte[0];

		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		if (tagFromIntent == null) {
			Log.e(TAG, "Tag not found.");
			return result;
		}

		String[] techList = tagFromIntent.getTechList();
		if (techList == null) {
			Log.e(TAG, "Technology list not found.");
			return result;
		}

		if (Arrays.asList(techList).contains(NfcF.class.getName())) {
			// NFCID2を取得
			mCardID = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
			if (mCardID.length > 0) {
				Log.d(TAG, "Card ID:{" + bytes2HexString(mCardID) + "}");
			} else {
				Log.d(TAG, "Card ID is None.");
			}
		}

		result = bytes2HexString(mCardID);
		return result;
	}

	/**
	 * Converter table from nibble to hex char.
	 */
	private static final char[] NIBBLE2CHAR = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * Convert a byte array to HEX dump string.
	 * 
	 * @param array
	 * @return
	 */
	private static String bytes2HexString(byte[] array) {
		if (array == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder(array.length * 3);
		for (int i = 0; i < array.length; i++) {
			byte b = array[i];
			if (i > 0) {
				sb.append(' ');
			}
			sb.append(NIBBLE2CHAR[0x0f & (b >> 4)]);
			sb.append(NIBBLE2CHAR[0x0f & b]);
		}
		return sb.toString();
	}
}
