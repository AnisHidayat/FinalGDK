package com.dicoding.picodiploma.FinalGDK.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.dicoding.picodiploma.FinalGDK.DetailActivity;
import com.dicoding.picodiploma.FinalGDK.model.Movie;
import com.dicoding.picodiploma.FinalGDK.DetailActivity;
import com.dicoding.picodiploma.FinalGDK.R;
import com.dicoding.picodiploma.FinalGDK.model.Movie;

import java.util.Calendar;
import java.util.List;

import static com.dicoding.picodiploma.FinalGDK.utils.UtilsConstant.MOVIE_DETAIL;
import static com.dicoding.picodiploma.FinalGDK.utils.UtilsConstant.NOTIFICATION_CHANNEL_ID;
import static com.dicoding.picodiploma.FinalGDK.utils.UtilsConstant.NOTIFICATION_ID;

public class MovieUpcomingReceiver extends BroadcastReceiver{
    private static int notifId = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        String movieName = intent.getStringExtra("movietitle");
        int id = intent.getIntExtra("id", 0);
        int movieId = intent.getIntExtra("movieid", 0);
        String photo = intent.getStringExtra("movieposter");
        String back = intent.getStringExtra("movieback");
        String date = intent.getStringExtra("moviedate");
        String desc = intent.getStringExtra("movieover");
        Movie movieResult = new Movie(movieId, movieName, photo, back, date, desc);
        String desc1 = String.format(context.getString(R.string.release_today_msg), movieName);
        sendNotification(context, context.getString(R.string.app_name), desc1, id, movieResult);
    }

    private void sendNotification(Context context, String title, String desc, int id, Movie movieResult) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(MOVIE_DETAIL, movieResult);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.movie)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(id, builder.build());
    }

    public void setAlarm(Context context, List<Movie> movieResults) {
        int delay = 0;

        for (Movie movie : movieResults) {
            cancelAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, MovieUpcomingReceiver.class);
            intent.putExtra("movietitle", movie.getName());
            intent.putExtra("movieid", movie.getId());
            intent.putExtra("movieposter", movie.getPhoto());
            intent.putExtra("movieback", movie.getBackdrop());
            intent.putExtra("moviedate", movie.getDate());
            intent.putExtra("movieover", movie.getDesc());
            intent.putExtra("id", notifId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                );
            } else {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay, pendingIntent);
            }
            notifId += 1;
            delay += 3000;
            Log.v("title", movie.getName());
        }
    }
    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }
    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieUpcomingReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
