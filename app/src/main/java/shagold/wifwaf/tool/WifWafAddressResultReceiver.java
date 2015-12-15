package shagold.wifwaf.tool;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import shagold.wifwaf.Constants;
import shagold.wifwaf.R;

/**
 * Created by jimmy on 15/12/15.
 */
public class WifWafAddressResultReceiver extends ResultReceiver {

    private String adresse;

    public WifWafAddressResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == Constants.SUCCESS_RESULT) {
            adresse = resultData.getString(Constants.RESULT_DATA_KEY);
        }
    }

    public String getAdresse() {
        return adresse;
    }
}
