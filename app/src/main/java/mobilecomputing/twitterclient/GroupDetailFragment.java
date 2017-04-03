package mobilecomputing.twitterclient;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mobilecomputing.twitterclient.model.SampleData;
import mobilecomputing.twitterclient.model.TweetInfo;


public class GroupDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";

    private int title = -1;

    public GroupDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            title = getArguments().getInt(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            Toolbar toolbar = (Toolbar) activity.findViewById(R.id.detail_toolbar);
            if(toolbar!=null)
                toolbar.setTitle(Integer.toString(title));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tweet_list_fragment, container, false);

        RecyclerView tweetList = (RecyclerView) rootView.findViewById(R.id.tweet_list);
        tweetList.setAdapter(new TweetRecyclerAdapter(SampleData.GenerateTweets()));

        return rootView;
    }

    public class TweetRecyclerAdapter extends RecyclerView.Adapter<TweetRecyclerAdapter.ViewHolder> {

        private final ArrayList<TweetInfo> tweets;

        public TweetRecyclerAdapter(ArrayList<TweetInfo> items) { tweets = items; }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_list_item, parent, false);
            return new TweetRecyclerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            TweetInfo tempData = tweets.get(position);

            holder.tweetInfo = tempData;
            holder.displayName.setText(tempData.displayName);
            holder.tweetText.setText(tempData.tweetText);
        }

        @Override
        public int getItemCount() {
            return tweets.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View _view;
            public final ImageView profileImage;
            public final TextView displayName;
            public final TextView tweetText;
            public TweetInfo tweetInfo;

            public ViewHolder(View view) {
                super(view);
                _view = view;
                profileImage = (ImageView) view.findViewById(R.id.profileImage);
                displayName = (TextView) view.findViewById(R.id.displayName);
                tweetText = (TextView) view.findViewById(R.id.tweetText);
            }
        }
    }
}
