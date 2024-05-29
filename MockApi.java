package com.example.ac2atv5;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MockApi {

    public interface OnSaveAlunoListener {
        void onSaveAlunoComplete(boolean success);
    }

    public interface OnGetAlunosListener {
        void onGetAlunos(ArrayList<Aluno> alunos);
    }

    public static void salvarAluno(Aluno aluno, OnSaveAlunoListener listener) {
        new AsyncTask<Aluno, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Aluno... params) {
                Aluno aluno = params[0];
                try {
                    URL url = new URL("https://66538b841c6af63f4674fc34.mockapi.io/aluno/aluno");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("ra", aluno.getRa());
                    jsonParam.put("nome", aluno.getNome());
                    jsonParam.put("cep", aluno.getCep());
                    jsonParam.put("logradouro", aluno.getLogradouro());
                    jsonParam.put("complemento", aluno.getComplemento());
                    jsonParam.put("bairro", aluno.getBairro());
                    jsonParam.put("cidade", aluno.getCidade());
                    jsonParam.put("uf", aluno.getUf());

                    DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                    os.writeBytes(jsonParam.toString());
                    os.flush();
                    os.close();

                    int responseCode = urlConnection.getResponseCode();
                    return responseCode == HttpURLConnection.HTTP_CREATED;
                } catch (IOException | JSONException e) {
                    Log.e("MockAPIUtil", "Erro ao salvar aluno", e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                listener.onSaveAlunoComplete(success);
            }
        }.execute(aluno);
    }

    public static void getAlunos(final OnGetAlunosListener listener) {
        new AsyncTask<Void, Void, ArrayList<Aluno>>() {
            @Override
            protected ArrayList<Aluno> doInBackground(Void... voids) {
                ArrayList<Aluno> alunos = new ArrayList<>();
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://66538b841c6af63f4674fc34.mockapi.io/aluno/aluno");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuilder builder = new StringBuilder();
                    if (inputStream == null) {
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line).append("\n");
                    }
                    if (builder.length() == 0) {
                        return null;
                    }
                    JSONArray jsonArray = new JSONArray(builder.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int ra = jsonObject.getInt("ra");
                        String nome = jsonObject.getString("nome");
                        String cep = jsonObject.getString("cep");
                        String logradouro = jsonObject.getString("logradouro");
                        String complemento = jsonObject.getString("complemento");
                        String bairro = jsonObject.getString("bairro");
                        String cidade = jsonObject.getString("cidade");
                        String uf = jsonObject.getString("uf");
                        Aluno aluno = new Aluno(ra, nome, cep, logradouro, complemento, bairro, cidade, uf);
                        alunos.add(aluno);
                    }
                } catch (IOException | JSONException e) {
                    Log.e("MockAPIUtil", "Erro ao obter alunos", e);
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            Log.e("MockAPIUtil", "Erro ao fechar o reader", e);
                        }
                    }
                }
                return alunos;
            }

            @Override
            protected void onPostExecute(ArrayList<Aluno> alunos) {
                listener.onGetAlunos(alunos);
            }
        }.execute();
    }
}
