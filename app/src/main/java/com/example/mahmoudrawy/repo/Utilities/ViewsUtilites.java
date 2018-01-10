package com.example.mahmoudrawy.repo.Utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.mahmoudrawy.repo.Data.RepoAdapter;
import com.example.mahmoudrawy.repo.Data.Repository;
import com.example.mahmoudrawy.repo.R;

import java.util.List;

/**
 * Created by mahmoud rawy™ 01221240053 on 09/01/2018.
 */

/*
this class to work with the views perfectly
 */
public class ViewsUtilites {
    public static void changeBackgroungColorForFork(LinearLayout topHave, int colorId) {
        topHave.setBackgroundResource(colorId);

    }

    public static void displayOrHideGitHupInfo(LinearLayout gitHupInfo) {
        if (gitHupInfo.getVisibility() == View.GONE) {
            gitHupInfo.setVisibility(View.VISIBLE);
        } else if (gitHupInfo.getVisibility() == View.VISIBLE) {
            gitHupInfo.setVisibility(View.GONE);
        }
    }

    public static void displayOrHideProgressBar(ProgressBar repoProgressBar) {
        if (repoProgressBar.getVisibility() == View.GONE) {
            repoProgressBar.setVisibility(View.VISIBLE);
        } else if (repoProgressBar.getVisibility() == View.VISIBLE) {
            repoProgressBar.setVisibility(View.GONE);
        }
    }

    public static void openURL(String url, Context context) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }
}
