package me.krithiyer.fbuinstagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProfileFragment extends Fragment {

    Button logout;

    interface Callback {
        void logOut();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        logout = (Button) view.findViewById(R.id.btFGLogOut);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ParseUser.logOut();
//                ParseUser currentUser = ParseUser.getCurrentUser();
//                if (currentUser == null) {
//                    Callback.logOut();
//                    Log.d("HomeActivity", "Logout successful!");
//                    final Intent i = new Intent(getContext(), MainActivity.class);
//                    startActivity(i);
//                    finish();
//                }
//            }
//        });


    }

}
