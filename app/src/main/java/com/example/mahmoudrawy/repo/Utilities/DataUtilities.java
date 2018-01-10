package com.example.mahmoudrawy.repo.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.util.Log;

import com.example.mahmoudrawy.repo.Data.Owner;
import com.example.mahmoudrawy.repo.Data.Repository;
import com.example.mahmoudrawy.repo.DataBase.TaskContract;
import com.example.mahmoudrawy.repo.R;
import com.example.mahmoudrawy.repo.Views.URLItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahmoud rawy 01221240053 on 08/01/2018.
 */

/*
this class is an Utility to work with data clearly
 */
public class DataUtilities {
    private static List<Repository> repos;
    private static Repository repository;
    public static String FORKTRUE = "true";
    public static String FORFALSE = "false";


    public static List returnRepositoryResponse(String repositoryResponse, Context context) {
        repos = new ArrayList();
        try {
            if (repositoryResponse != null) {
                JSONArray repoJsonArray = new JSONArray(repositoryResponse);
                for (int i = 0; i < repoJsonArray.length(); i++) {
                    Owner repoOwner = new Owner();
                    JSONObject repoJsonObject = repoJsonArray.getJSONObject(i);
                    JSONObject repoOwnerObject = repoJsonObject.getJSONObject(context.getString(R.string.owner_key));
                    String repoName = repoJsonObject.getString(context.getString(R.string.name_key));
                    String repoHtmlURL = repoJsonObject.getString(context.getString(R.string.html_key));
                    String repoOwnerName = repoOwnerObject.getString(context.getString(R.string.user_name_key));
                    String repoOwnerHtmlUrl = repoOwnerObject.getString(context.getString(R.string.html_key));
                    String repoDescription = repoJsonObject.getString(context.getString(R.string.description_key));
                    boolean fork = repoJsonObject.getBoolean(context.getString(R.string.fork_key));
                    repoOwner.setHtmlUrl(repoOwnerHtmlUrl);
                    repoOwner.setLogin(repoOwnerName);
                    repository = new Repository();
                    repository.setName(repoName);
                    repository.setDescription(repoDescription);
                    repository.setHtmlUrl(repoHtmlURL);
                    repository.setFork(fork);
                    repository.setOwner(repoOwner);
                    repos.add(repository);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return repos;
    }

    public static List<URLItem> createHtmlUrlItems(Context context) {
        List<URLItem> items = new ArrayList<>();
        items.add(new URLItem(context.getString(R.string.repository_html_label), R.mipmap.ic_shortcut_language));
        items.add(new URLItem(context.getString(R.string.owner_html_label), R.mipmap.ic_shortcut_person));
        return items;


    }

    public static List<Repository> getDataFromDtaBase(Cursor cursor) {
        List<Repository> repos = new ArrayList<>();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            Repository repository = new Repository();
            Owner owner = new Owner();

            String name = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.NAME));
            String description = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.Description));
            String repoHtml = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.REPOHTML));
            String fork = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.FORK));
            String ownerUserName = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.OWNERUSERNAME));
            String ownerHtml = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.OWNERHTML));
            repository.setName(name);
            repository.setHtmlUrl(repoHtml);
            repository.setDescription(description);
            if (fork.equals(FORKTRUE))
                repository.setFork(true);
            else
                repository.setFork(false);
            owner.setLogin(ownerUserName);
            owner.setHtmlUrl(ownerHtml);
            repository.setOwner(owner);
            repos.add(repository);
            cursor.moveToNext();
        }

        return repos;
    }

    public static boolean checkNetworkConnection(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {

            return true;
        }

        return false;
    }

    public static String[] createProjection() {
        String[] projection = {TaskContract.TaskEntry.NAME, TaskContract.TaskEntry.Description,
                TaskContract.TaskEntry.OWNERUSERNAME, TaskContract.TaskEntry.FORK,
                TaskContract.TaskEntry.OWNERHTML, TaskContract.TaskEntry.REPOHTML};
        return projection;
    }


    public static SharedPreferences getSharedPrefrence(String TAG, Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedpreferences;
    }
}
