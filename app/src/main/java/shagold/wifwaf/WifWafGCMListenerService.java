package shagold.wifwaf;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;

    public class WifWafGCMListenerService extends GcmListenerService {

        private static final String TAG = "MyGcmListenerService";

        /**
         * Called when message is received.
         *
         * @param from SenderID of the sender.
         * @param data Data bundle containing message data as key/value pairs.
         *             For Set of keys use data.keySet().
         */
        // [START receive_message]
        @Override
        public void onMessageReceived(String from, Bundle data) {
            String message = data.getString("TypeNotif");
            Log.d(TAG, "From: " + from);
            Log.d(TAG, "Message: " + message);
            String messageShow = null; //message à afficher soit dans le toast soit dans la notif
            if (message.equals("addWalk")){
                messageShow = "A new walk has been added!";
            }
            else if(message.equals("addParticipation")){
                String idwalk = data.getString("walk");
                messageShow = " Someone would like to participate to your walk. The id of this walk is" + idwalk;
            }
            else if(message.equals("validateParticipation")){
                String idwalk = data.getString("walk");
                messageShow = "Your participation has been validated! The id of this walk is" + idwalk;
            }
            else if(message.equals("refuseParticipation")){
                    String idwalk = data.getString("walk");
                    messageShow = "Your participation has been refused! The id of this walk is" + idwalk;
            }

            //Si appli lancée
            if (WifWafPreferences.isLaunched) {
                Vibrator vib = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(200);
            }
            else {
                sendNotification(messageShow);
            }
        }


        /**
         * Create and show a simple notification containing the received GCM message.
         *
         * @param message GCM message received.
         */
        private void sendNotification(String message) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("WifWaf")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }

    }

