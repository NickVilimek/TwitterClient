package mobilecomputing.twitterclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mobilecomputing.twitterclient.database.DBHelper;
import mobilecomputing.twitterclient.model.Group;

public class AddGroupFragment extends DialogFragment {

    public ArrayList<String> usersList = new ArrayList<>();
    public List<Group> groupList;

    public void setGroupList(ArrayList<Group> groups){
        groupList = groups;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_group_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText groupName = (EditText) view.findViewById(R.id.groupName);
        final EditText userName = (EditText) view.findViewById(R.id.userName);
        final TextView errorLabel = (TextView) view.findViewById(R.id.userError);
        final Button addButton = (Button) view.findViewById(R.id.addUser);
        final Button okButton = (Button) view.findViewById(R.id.okButton);
        final Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
        final TextView statusText = (TextView) view.findViewById(R.id.userUpdate);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Error check
                if(userName.getText().toString().equals("")){
                    errorLabel.setText("Must enter a username");
                } else {
                    usersList.add(userName.getText().toString());
                    statusText.setText("@"+ userName.getText().toString() + " added");

                    userName.setText("");
                    errorLabel.setText("");
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(groupName.getText().toString().equals("")){
                    errorLabel.setText("Must enter group name");
                } else if (usersList.isEmpty()){
                    errorLabel.setText("Must add at least one user");
                } else {
                    DBHelper helper = new DBHelper(getContext());
                    //
                    Group newGroup = new Group();
                    newGroup.groupName = groupName.getText().toString();
                    newGroup.numberOfMembers = usersList.size();
                    newGroup.id = (int) helper.AddGroup(newGroup);
                    groupList.add(newGroup);

                    helper.AddUsers(usersList,newGroup.id);

                    //
                    getDialog().dismiss();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }




}
