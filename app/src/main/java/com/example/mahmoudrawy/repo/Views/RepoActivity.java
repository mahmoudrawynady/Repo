package com.example.mahmoudrawy.repo.Views;

import android.content.ContentValues;
import android.content.Context;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahmoudrawy.repo.Data.RepoAdapter;
import com.example.mahmoudrawy.repo.Data.Repository;
import com.example.mahmoudrawy.repo.DataBase.TaskContract;
import com.example.mahmoudrawy.repo.NetworkTasks.GitHupTask;
import com.example.mahmoudrawy.repo.R;
import com.example.mahmoudrawy.repo.Utilities.DataUtilities;
import com.example.mahmoudrawy.repo.Utilities.ViewsUtilites;

import java.util.List;

/**
 * Created by mahmoud rawy™ 01221240053 on 09/01/2018.
 */

public class RepoActivity extends AppCompatActivity implements RepoLisenter, LoaderManager.LoaderCallbacks<Cursor> {
    private final static String DATASATAUS = "status";
    private static final String CASHINGSTATUS = "cashingstatus";
    private Context context;
    private LinearLayout gitHupInfo;
    private RecyclerView reposRecycler;
    private ProgressBar repoProgressBar;
    public final static int LOADER_ID = 101;
    private SwipeRefreshLayout repoSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        gitHupInfo = (LinearLayout) findViewById(R.id.LI_gitHupInfo);
        reposRecycler = (RecyclerView) findViewById(R.id.RV_repoRecycler);
        LinearLayoutManager repoLayoutManager = new LinearLayoutManager(context);
        repoLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reposRecycler.setLayoutManager(repoLayoutManager);
        repoProgressBar = (ProgressBar) findViewById(R.id.PR_repoProgress);
        repoSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SW_refresh);
        this.context = this;
        repoSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (DataUtilities.checkNetworkConnection(context) == true)
                    refreshRepos();
                else {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                    repoSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        showRepos();


    }

    @Override
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        ViewsUtilites.displayOrHideGitHupInfo(gitHupInfo);
    }

    @Override
    public void showReposItems(List<Repository> repositoryList, RepoAdapter repoAdapter) {
        // ViewsUtilites.displayItems(repositoryList,reposRecycler,repoAdapter,getApplicationContext());
        reposRecycler.setAdapter(repoAdapter);
    }

    @Override
    public void showOrHideBar() {
        ViewsUtilites.displayOrHideProgressBar(repoProgressBar);
    }

    @Override
    public void showDialog(final Repository repository) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RepoActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.custom_dialog, null);
        alertDialog.setView(convertView);
        List<URLItem> itmes = DataUtilities.createHtmlUrlItems(context);
        DialogListAdapter dialogListAdapter = new DialogListAdapter(getBaseContext(), itmes);
        ListView lv = (ListView) convertView.findViewById(R.id.LV_dialog);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ViewsUtilites.openURL(repository.getHtmlUrl(), context);
                } else {
                    ViewsUtilites.openURL(repository.getOwner().getHtmlUrl(), context);


                }
            }
        });
        lv.setAdapter(dialogListAdapter);
        alertDialog.show();

    }

    @Override
    public void cashRepos(List<Repository> repos) {
        for (int i = 0; i < repos.size(); i++) {
            ContentValues values = new ContentValues();
            Repository repository = repos.get(i);
            values.put(TaskContract.TaskEntry.NAME, repository.getName());
            values.put(TaskContract.TaskEntry.Description, repository.getDescription());
            if (repository.getFork() == true)
                values.put(TaskContract.TaskEntry.FORK, DataUtilities.FORKTRUE);
            else
                values.put(TaskContract.TaskEntry.FORK, DataUtilities.FORFALSE);
            values.put(TaskContract.TaskEntry.OWNERUSERNAME, repository.getOwner().getLogin());
            values.put(TaskContract.TaskEntry.REPOHTML, repository.getHtmlUrl());
            values.put(TaskContract.TaskEntry.OWNERHTML, repository.getOwner().getHtmlUrl());
            getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, values);


        }

    }

    @Override
    public void fillSharedPrefrence(SharedPreferences sharedPreferences, String databaseStatus, String cashingStatus) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DATASATAUS, databaseStatus);
        editor.putString(CASHINGSTATUS, cashingStatus);
        editor.commit();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] prohection = DataUtilities.createProjection();


        CursorLoader cursorLoader = new CursorLoader(getApplicationContext()
                , TaskContract.TaskEntry.CONTENT_URI, prohection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
        List<Repository> repos = DataUtilities.getDataFromDtaBase(cursor);
        if (repos != null) {
            ViewsUtilites.displayOrHideProgressBar(repoProgressBar);
            RepoAdapter repoAdapter = new RepoAdapter(repos, this);
            reposRecycler.setAdapter(repoAdapter);
        } else
            ViewsUtilites.displayOrHideGitHupInfo(gitHupInfo);


        ;

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void showRepos() {
        if (DataUtilities.checkNetworkConnection(getApplicationContext()) == true) {
            GitHupTask gitHupTask = new GitHupTask(getApplicationContext(), this);
            gitHupTask.loadReoList();
        } else {
            SharedPreferences sharedPreferences = DataUtilities
                    .getSharedPrefrence(GitHupTask.REPOPREFRENCE, getApplicationContext());

            if (sharedPreferences != null) {
                if (sharedPreferences.getString(DATASATAUS, GitHupTask.NO).equals(GitHupTask.YES))
                    getSupportLoaderManager().initLoader(LOADER_ID, null, this);
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                    ViewsUtilites.displayOrHideProgressBar(repoProgressBar);
                    ViewsUtilites.displayOrHideGitHupInfo(gitHupInfo);
                }


            }


        }

    }

    public void refreshRepos() {
        getContentResolver().delete(TaskContract.TaskEntry.CONTENT_URI, null, null);
        SharedPreferences sharedPreferences = DataUtilities.
                getSharedPrefrence(GitHupTask.REPOPREFRENCE, getApplicationContext());
        fillSharedPrefrence(sharedPreferences, GitHupTask.NO, GitHupTask.NO);

        showRepos();
        repoSwipeRefreshLayout.setRefreshing(false);
        ViewsUtilites.displayOrHideProgressBar(repoProgressBar);


    }
}
