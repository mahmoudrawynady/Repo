package com.example.mahmoudrawy.repo.Views;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;

import com.example.mahmoudrawy.repo.Data.RepoAdapter;
import com.example.mahmoudrawy.repo.Data.Repository;

import java.util.List;

/**
 * Created by mahmoud rawy™ 01221240053 on 09/01/2018.
 */
/*
this interface is a listener to facilitate the work with Activity and views
 */

public interface RepoLisenter {
    public void displayToast(String message);

    public void showReposItems(List<Repository> repositoryList, RepoAdapter repoAdapter);

    public void showOrHideBar();

    public void showDialog(Repository repository);

    public void cashRepos(List<Repository> repos);

    public void fillSharedPrefrence(SharedPreferences sharedPreferences, String databaseStatus, String cashingStatus);

}
