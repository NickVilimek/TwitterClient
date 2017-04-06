package mobilecomputing.twitterclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddGroupFragment extends DialogFragment {

    public List<String> usersList = new ArrayList<>();


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
                if(userName.getText().toString() == ""){
                    errorLabel.setText("Must enter a username");
                } else {
                    usersList.add(userName.getText().toString());
                    statusText.setText("@"+ userName.getText().toString() + " added");
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(groupName.getText().toString() == ""){
                    errorLabel.setText("Must enter group name");
                } else if (usersList.isEmpty()){
                    errorLabel.setText("Must add at least one user");
                } else {
                    //
                        //Save group
                        //Add group to adapter

                        //Save all users

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
