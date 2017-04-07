package mobilecomputing.twitterclient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobilecomputing.twitterclient.backend.EnrichedTweet;
import mobilecomputing.twitterclient.backend.TwitterInterface;
import mobilecomputing.twitterclient.database.DBHelper;


public class GroupDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private int title = -1;

    public List<String> screenNames;
    public ArrayList<EnrichedTweet> tweets;
    public DBHelper helper;

    public TweetRecyclerAdapter adapter;

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
        adapter = new TweetRecyclerAdapter(tweets);
        tweetList.setAdapter(adapter);

        TwitterInterface t = new TwitterInterface();

        return rootView;
    }

    public class TweetRecyclerAdapter extends RecyclerView.Adapter<TweetRecyclerAdapter.ViewHolder> {

        private final ArrayList<EnrichedTweet> tweets;

        public TweetRecyclerAdapter(ArrayList<EnrichedTweet> items) {
            tweets = items;
            TwitterInterface t = new TwitterInterface();
            new TwitterDataTask(tweets,screenNames,adapter).execute(t);
        }

        private class TwitterDataTask extends AsyncTask<TwitterInterface, Void, List<EnrichedTweet>> {
            List<EnrichedTweet> tweets;
            List<String> screenNames;
            TweetRecyclerAdapter adapter;

            public TwitterDataTask(List<EnrichedTweet> _tweets, List<String> _screenNames,TweetRecyclerAdapter _adapter) {
                this.tweets = _tweets;
                this.screenNames = _screenNames;
                this.adapter = _adapter;
            }

            @Override
            protected List<EnrichedTweet> doInBackground(TwitterInterface... twitterInterfaces) {
                List<EnrichedTweet> tweets = twitterInterfaces[0].getTopTweetsByDateSent(screenNames, 5);
                return tweets;
            }

            protected void onPostExecute(List<EnrichedTweet> result) {
                tweets.addAll(result);
                TweetRecyclerAdapter.this.notifyDataSetChanged();
            }


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_list_item, parent, false);
            return new TweetRecyclerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            EnrichedTweet tempData = tweets.get(position);

            holder.tweetInfo = tempData;

            new DownloadImageTask(holder.profileImage).execute(holder.tweetInfo.getSenderImageUrl());

            holder.userName.setText(tempData.getSenderScreenname());
            holder.tweetText.setText(tempData.getText());
            holder.dateCreated.setText(tempData.getDateCreated().toString());
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
            public final TextView dateCreated;
            public EnrichedTweet tweetInfo;

            public ViewHolder(View view) {
                super(view);
                _view = view;
                profileImage = (ImageView) view.findViewById(R.id.profileImage);
                userName = (TextView) view.findViewById(R.id.displayName);
                tweetText = (TextView) view.findViewById(R.id.tweetText);
                dateCreated = (TextView) view.findViewById(R.id.dateCreated);
            }
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
