package com.example.watopoly.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.watopoly.activity.MainGameViewActivity;
import com.example.watopoly.model.Game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;

public class GameSaveManager {
    public static class LoadData extends AsyncTask<Void, Void, Void> {
        private WeakReference<MainGameViewActivity> activityWeakReference;

        public LoadData(MainGameViewActivity mainGameViewActivity){
            activityWeakReference = new WeakReference<>(mainGameViewActivity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                MainGameViewActivity mainGameViewActivity = activityWeakReference.get();
                if (mainGameViewActivity == null || mainGameViewActivity.isFinishing()) return null;

                FileInputStream inputStream = mainGameViewActivity.openFileInput("savedGameState");
                Game.loadGame(inputStream);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MainGameViewActivity mainActivity = activityWeakReference.get();
            if (mainActivity == null || mainActivity.isFinishing()) return;

            mainActivity.initGame();
        }
    }

    public static class SaveData extends AsyncTask<Void, Void, Void> {
        private WeakReference<MainGameViewActivity> activityWeakReference;
        public SaveData(MainGameViewActivity mainGameViewActivity) {
            activityWeakReference = new WeakReference<>(mainGameViewActivity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MainGameViewActivity mainGameViewActivity = activityWeakReference.get();
            if (mainGameViewActivity == null || mainGameViewActivity.isFinishing()) return null;

            try {
                FileOutputStream outputStream = mainGameViewActivity.openFileOutput("savedGameState", Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(Game.getInstance()); //May need thread safety
                objectOutputStream.close();
                Log.d("Save", "Game saved");
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MainGameViewActivity mainActivity = activityWeakReference.get();
            if (mainActivity == null || mainActivity.isFinishing()) return;
            mainActivity.onSaveComplete();
        }
    }
}
