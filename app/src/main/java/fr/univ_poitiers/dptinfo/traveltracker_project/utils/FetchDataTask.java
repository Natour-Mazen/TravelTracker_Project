package fr.univ_poitiers.dptinfo.traveltracker_project.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchDataTask extends AsyncTask<String, Void, JsonObject> {

    private static final String APP_TAG = "FetchDataTask" ;
    private final String baseUrl;
    private final Context myContext;


    public FetchDataTask(String baseUrl, Context context) {
        this.baseUrl = baseUrl;
        this.myContext = context;
    }

    @Override
    protected JsonObject doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + params[0];
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Code inattendu " + response.code());
            }
            assert response.body() != null;
            String responseBody = response.body().string();
            Gson gson = new Gson();
            return gson.fromJson(responseBody, JsonObject.class);
        } catch (IOException e) {
            LogHelper.logError(APP_TAG, "Erreur réseau : " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(JsonObject jsonObject) {
        if (jsonObject != null) {
            processResponseData(jsonObject);
        } else {
            String message = "Données non trouvées";

            ToastHelper.showLongToast(myContext ,message);
        }
    }

    private void processResponseData(JsonObject thejsonObject) {
        // Traitement des données récupérées
        // Ce code dépendra de la structure des données que vous récupérez
       // Glide.with(myContext).load(imageUrl).into(pokeImage);
    }
}
