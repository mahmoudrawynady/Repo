package com.example.mahmoudrawy.repo.Data;


import android.content.Context;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoudrawy.repo.R;
import com.example.mahmoudrawy.repo.Utilities.ViewsUtilites;
import com.example.mahmoudrawy.repo.Views.RepoLisenter;

import java.util.List;


/**
 * Created by mahmoud rawy™ 01221240053 on 09/01/2018.
 */

/*
this class for Repo Adapter to publish data to items
 */
public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ReposViewHolder> {
    private List<Repository> repos;
    private Context context;
    RepoLisenter repoLisenter;

    public RepoAdapter(List<Repository> repos, RepoLisenter repoLisenter) {
        this.repos = repos;
        this.context = context;
        this.repoLisenter = repoLisenter;

    }

    public class ReposViewHolder extends RecyclerView.ViewHolder {
        public TextView reposName;
        public TextView repoOwnerUserName;
        public TextView repoDiscription;
        public LinearLayout itemTopHave;
        public CardView repoItem;

        public ReposViewHolder(View view) {
            super(view);
            reposName = (TextView) view.findViewById(R.id.TV_repoName);
            repoOwnerUserName = (TextView) view.findViewById(R.id.TV_userName);
            repoDiscription = (TextView) view.findViewById(R.id.TV_repoDescription);
            itemTopHave = (LinearLayout) view.findViewById(R.id.LI_topHave);
            repoItem = (CardView) view;


        }
    }


    @Override
    public ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_item, parent, false);

        return new ReposViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReposViewHolder holder, int position) {
        final Repository repository = repos.get(position);
        holder.reposName.setText(repository.getName());
        holder.repoOwnerUserName.setText(repository.getOwner().getLogin());
        holder.repoDiscription.setText(repository.getDescription());
        if (repository.getFork() == false) {
            ViewsUtilites.changeBackgroungColorForFork(holder.itemTopHave, R.color.lightGreen);
        } else if (repository.getFork() == true) {
            ViewsUtilites.changeBackgroungColorForFork(holder.itemTopHave, R.color.lightWight);

        }
        holder.repoItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                repoLisenter.showDialog(repository);
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return repos.size();
    }
}


