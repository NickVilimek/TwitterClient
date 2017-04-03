package mobilecomputing.twitterclient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mobilecomputing.twitterclient.model.Group;
import mobilecomputing.twitterclient.model.SampleData;

public class GroupListActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create New Group
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.group_list);
        recyclerView.setAdapter(new GroupRecylerAdapter(SampleData.GenerateGroups())); //Hardcoded Data

        //If device has the correct amount of width it splits into a Master-Detail
        if (findViewById(R.id.group_detail_container) != null) {
            mTwoPane = true;
        }
    }

    /* Handles all the interaction and data with the list of groups */
    public class GroupRecylerAdapter extends RecyclerView.Adapter<GroupRecylerAdapter.ViewHolder> {

        private final ArrayList<Group> groups;

        public GroupRecylerAdapter(ArrayList<Group> items) {
            groups = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.group_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            Group currentGroup = groups.get(position);

            holder.mItem = currentGroup;
            holder.mIdView.setText(Integer.toString(currentGroup.id));
            holder.membersNum.setText(Integer.toString(currentGroup.numberOfMembers)+ " members");
            holder.mContentView.setText(currentGroup.groupName);

            //Pencil icon - click to edit group
            holder.editButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Toast.makeText(GroupListActivity.this, "hello", Toast.LENGTH_SHORT).show();
                }
            });

            //Red x - deletes group
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Are you sure you want to delete this group?");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    groups.remove(position);
                                    //Delete from database
                                    notifyDataSetChanged();
                                }
                            });
                    builder.setNegativeButton("Cancel",null);
                    builder.create().show();
                }
            });

            //If one paned then navigates to next activity and then set's up the fragment
            //If two paned then inflates the fragment in the child view
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        //Pass the Id of the group to the detail fragment
                        Bundle arguments = new Bundle();
                        arguments.putInt(GroupDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        GroupDetailFragment fragment = new GroupDetailFragment();
                        fragment.setArguments(arguments);

                        getSupportFragmentManager().beginTransaction().replace(R.id.group_detail_container, fragment).commit();

                    } else {

                        Context context = v.getContext();
                        Intent intent = new Intent(context, GroupDetailActivity.class);
                        //Passes Id of the group to the detail activity
                        intent.putExtra(GroupDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return groups.size();
        }

        //Elements of the group cells
        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final CardView mCardView;
            public final TextView mIdView;
            public final TextView mContentView;
            public final ImageButton editButton;
            public final ImageButton deleteButton;
            public final TextView membersNum;
            public Group mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mCardView= (CardView) view.findViewById(R.id.card_view);
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
                editButton = (ImageButton) view.findViewById(R.id.editButton);
                deleteButton = (ImageButton) view.findViewById(R.id.deleteButton);
                membersNum = (TextView) view.findViewById(R.id.members);
            }
        }
    }
}
