package com.example.ac2atv5;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class ViaCep {

    public interface OnCEPResultListener {
        void onCEPResult(JSONObject result);
    }

    public static void buscarCEP(String cep, OnCEPResultListener listener) {
        new AsyncTask<String, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... params) {
                String cep = params[0];
                try {
                    URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        InputStream in = urlConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        return new JSONObject(stringBuilder.toString());
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException | JSONException e) {
                    Log.e("ViaCEPUtil", "Erro ao buscar CEP", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONObject result) {
                if (result != null) {
                    listener.onCEPResult(result);
                } else {
                    // Tratar erro**
                }
            }
        }.execute(cep);
    }
}
