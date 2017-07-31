package com.example.karthik.mydashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class BottomSheetSettings extends BottomSheetDialogFragment {

    protected SharedPreferences sharedPref;
    protected EditText etUser;
    protected EditText etEmail;
    protected ImageView ivAvatar;
    protected Context context;
    protected List<AvatarList> avatars;
    protected TypedArray avatar;

    @NonNull @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        context = getActivity();
        avatars = new ArrayList<AvatarList>();
        avatar = getResources().obtainTypedArray(R.array.avatars);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final BottomSheetDialog d = (BottomSheetDialog) dialog;

                Button save = (Button) d.findViewById(R.id.savebutton);
                save.setTextAppearance(getContext(), getTheme());

                sharedPref = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
                etUser = (EditText) d.findViewById(R.id.username);
                etEmail = (EditText) d.findViewById(R.id.email_address);
                ivAvatar = (ImageView) d.findViewById(R.id.bg_avatar_image);

                String username = sharedPref.getString(getString(R.string.key_name),"");
                String emailAddress = sharedPref.getString(getString(R.string.key_email),"");
                int user_avatar = sharedPref.getInt(getString(R.string.key_avatar),0);

                etUser.setText(username);
                etEmail.setText(emailAddress);
                Log.w("Delete:", "User Image - " + user_avatar);
                ivAvatar.setImageResource(avatar.getResourceId(user_avatar,R.drawable.avatar_angry_bird));

                d.findViewById(R.id.savebutton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // To store contents to the shared preferences

                        String username = etUser.getText().toString();
                        String emailAddress = etEmail.getText().toString();

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.key_name), username);
                        editor.putString(getString(R.string.key_email), emailAddress);
                        editor.commit();
                        d.dismiss();

                    }
                });

                d.findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // To store contents to the shared preferences
                        Log.w("Delete:","Avatar clicked");

                        CharSequence options[] = new CharSequence[] {"Call", "SMS", "Email"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setCancelable(false);
                        builder.setTitle("Select your option:");
                        loadAvatars();
                        AvatarListAdaptor adapter = new AvatarListAdaptor(context, avatars);
                        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putInt(getString(R.string.key_avatar), which);
                                editor.commit();
                                ivAvatar.setImageResource(avatar.getResourceId(which,0));
                            }
                        });

                        builder.setNegativeButton(getString(R.string.calcel_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //the etUser clicked on Cancel
                            }
                        });
                        builder.show();

                    }
                });

            }
        });

        // Do something with your dialog like setContentView() or whatever
        return dialog;
    }

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog,style);
        View contentView = View.inflate(getContext(), R.layout.settings_bottomsheet, null);
        dialog.setContentView(contentView);

    }

    private void loadAvatars(){

        avatars.removeAll(avatars);
        TypedArray avatar = getResources().obtainTypedArray(R.array.avatars);
        String[] avatar_name = getResources().getStringArray(R.array.avatar_names);

        for (int count = 0; count < avatar_name.length; count++) {
            avatars.add(new AvatarList(avatar.getResourceId(count,R.drawable.avatar_angry_bird),avatar_name[count]));
        }

    }

}