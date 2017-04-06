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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mobilecomputing.twitterclient.backend.EnrichedTweet;
import mobilecomputing.twitterclient.backend.TwitterInterface;
import mobilecomputing.twitterclient.database.DBHelper;


public class GroupDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private int title = -1;

    public List<String> screenNames;
    public List<ArrayList> tweets;
    public DBHelper helper;

    public GroupDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenNames = new ArrayList<>();
        tweets = new ArrayList<>();
        helper = new DBHelper(getContext());

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            title = getArguments().getInt(ARG_ITEM_ID);
            screenNames = helper.GetUsers(title);

            TwitterInterface t = new TwitterInterface();
            List<EnrichedTweet> tweets = t.getTopTweetsByDateSent(screenNames, 50);

            /* Toolbar */
            Activity activity = this.getActivity();
            Toolbar toolbar = (Toolbar) activity.findViewById(R.id.detail_toolbar);
            if(toolbar!=null)
                toolbar.setTitle("Tweets for Group " + Integer.toString(title));

            /* Set up List */
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tweet_list_fragment, container, false);

        RecyclerView tweetList = (RecyclerView) rootView.findViewById(R.id.tweet_list);
        tweetList.setAdapter(new TweetRecyclerAdapter(new ArrayList<EnrichedTweet>()));

        return rootView;
    }

    public class TweetRecyclerAdapter extends RecyclerView.Adapter<TweetRecyclerAdapter.ViewHolder> {

        private final ArrayList<EnrichedTweet> tweets;

        public TweetRecyclerAdapter(ArrayList<EnrichedTweet> items) { tweets = items; }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_list_item, parent, false);
            return new TweetRecyclerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            EnrichedTweet tempData = tweets.get(position);

            holder.tweetInfo = tempData;
            holder.userName.setText(tempData.getSenderScreenname());
            holder.tweetText.setText(tempData.getText());
        }

        @Override
        public int getItemCount() {
            return tweets.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View _view;
            public final ImageView profileImage;
            public final TextView userName;
            public final TextView tweetText;
            public EnrichedTweet tweetInfo;

            public ViewHolder(View view) {
                super(view);
                _view = view;
                profileImage = (ImageView) view.findViewById(R.id.profileImage);
                userName = (TextView) view.findViewById(R.id.displayName);
                tweetText = (TextView) view.findViewById(R.id.tweetText);
            }
        }
    }
}
