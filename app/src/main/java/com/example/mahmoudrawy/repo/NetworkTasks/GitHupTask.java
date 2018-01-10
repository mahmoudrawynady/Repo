package com.example.mahmoudrawy.repo.NetworkTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mahmoudrawy.repo.Data.RepoAdapter;
import com.example.mahmoudrawy.repo.Data.Repository;
import com.example.mahmoudrawy.repo.R;
import com.example.mahmoudrawy.repo.Utilities.DataUtilities;
import com.example.mahmoudrawy.repo.Views.RepoLisenter;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Mahmoud Rawy 01221240053 on 08/01/2018.
 */
/*
this class for network tasks to load data from the server
 */

public class GitHupTask {
    private static final String JSON_URL = "https://api.github.com/users/square/repos";
    public static final String REPOPREFRENCE = "Repo";
    private final static String DATASATAUS = "status";
    public final static String NO = "no";
    public final static String YES = "yes";


    private Context context;
    private RepoLisenter repoLisenter;

    public GitHupTask(Context context, RepoLisenter repoLisenter) {
        this.context = context;
        this.repoLisenter = repoLisenter;

    }

    public void loadReoList() {


        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            repoLisenter.displayToast(context.getString(R.string.error_message));
                            return;
                        }
                        List<Repository> reposList = DataUtilities.returnRepositoryResponse(response, context);
                        RepoAdapter repoAdapter = new RepoAdapter(reposList, repoLisenter);
                        repoLisenter.showReposItems(reposList, repoAdapter);
                        repoLisenter.showOrHideBar();
                        SharedPreferences sharedPreferences = DataUtilities.getSharedPrefrence(REPOPREFRENCE, context);
                        if (sharedPreferences.getString(DATASATAUS, NO).equals(NO)) {
                            repoLisenter.cashRepos(reposList);
                            repoLisenter.fillSharedPrefrence(sharedPreferences, YES, YES);


                        } else {
                            repoLisenter.fillSharedPrefrence(sharedPreferences, NO, NO);

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.error_message), Toast.LENGTH_LONG).
                                show();
                        repoLisenter.showOrHideBar();

                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(stringRequest);
    }
}
