package mobilecomputing.twitterclient.model;

import java.util.ArrayList;

public class SampleData {

    public static ArrayList<Group> GenerateGroups(){

        ArrayList<Group> list = new ArrayList<>();

        Group group1 = new Group();
        group1.id = 1;
        group1.groupName = "Celebrities";

        Group group2 = new Group();
        group2.id = 2;
        group2.groupName = "Family";

        Group group3 = new Group();
        group3.id = 3;
        group3.groupName = "Group 3";

        Group group4 = new Group();
        group4.id = 4;
        group4.groupName = "Group 4";

        Group group5 = new Group();
        group5.id = 5;
        group5.groupName = "Group 5";

        list.add(group1);
        list.add(group2);
        list.add(group3);
        list.add(group4);
        list.add(group5);

        return list;
    }
    public static ArrayList<TweetInfo> GenerateTweets(){
        ArrayList<TweetInfo> list = new ArrayList<>();

        TweetInfo tweetInfo1 = new TweetInfo();
        tweetInfo1.id = 1;
        tweetInfo1.userName = "tweeter";
        tweetInfo1.tweetText = "tweet number 1";

        TweetInfo tweetInfo2 = new TweetInfo();
        tweetInfo2.id = 2;
        tweetInfo2.userName = "tweeter2";
        tweetInfo2.tweetText = "tweet number 2";

        TweetInfo tweetInfo3 = new TweetInfo();
        tweetInfo3.id = 3;
        tweetInfo3.userName = "tweeter3";
        tweetInfo3.tweetText = "tweet number 3";

        list.add(tweetInfo1);
        list.add(tweetInfo2);
        list.add(tweetInfo3);

        return list;
    }
}
