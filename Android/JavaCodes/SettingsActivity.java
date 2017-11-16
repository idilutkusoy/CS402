package tr.edu.ozu.ozunaviclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    String url = "http://192.168.1.63:8080";
    private int userId;
    private List<Interest> interestList = new ArrayList<>();
    private RecyclerView recyclerView;
    private InterestAdapter adapter;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userId = getIntent().getIntExtra("user_id", 0);

        adapter = new InterestAdapter(interestList);
        recyclerView = (RecyclerView) findViewById(R.id.RecylclerView);
        doneButton= (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if(userId != 0){
            postXMLTask task = new postXMLTask(String.valueOf(userId));
            task.execute(new String[]{url + "/Ozu/UserIdInterest"});
        }
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class postXMLTask extends AsyncTask<String, Void, String> {

    private final String userId;

        public postXMLTask(String userId){
            this.userId = userId;
        }

        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
                output = getOutputFromUrl(url);
            }
            return output;
        }

        private String getOutputFromUrl(String url) {
            String output = null;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                ArrayList<NameValuePair> postParameters;


                postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("usr", userId));
                httpPost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));


                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                output = EntityUtils.toString(httpEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
            if(output != null){
                try {
                    JSONObject obj = new JSONObject(output);
                    if("0".equals(obj.getString("Status"))){
                        JSONArray array = obj.getJSONArray("interests");
                        interestList.clear();
                        for(int i = 0; i<array.length(); i++){
                            JSONObject item = array.getJSONObject(i);
                            Interest interest = null;
                            interest = new Interest(item.getInt("id"), item.getString("name"), item.getInt("rate"));
                            interestList.add(interest);
                        }
                        adapter = new InterestAdapter(interestList);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
