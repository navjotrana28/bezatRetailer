package com.bezatretailer.bezat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.bezatretailer.bezat.ClientRetrofit;
import com.bezatretailer.bezat.R;
import com.bezatretailer.bezat.api.contactusResponse.ContactUsRequest;
import com.bezatretailer.bezat.api.contactusResponse.ContactUsResponse;
import com.bezatretailer.bezat.interfaces.ContactUsSuccessResponse;


public class ContactUsFragment extends Fragment {
    ImageView imgBack;
    EditText name, email, comments;
    Button sendBtn;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        addViews(view);
        onClickSendButton();
        onClickBackButton();
        return view;
    }

    private void onClickSendButton() {
        sendBtn.setOnClickListener(v -> {
            ContactUsRequest request = new ContactUsRequest();
            request.setName(name.getText().toString());
            request.setEmail(email.getText().toString());
            request.setComments(comments.getText().toString());
            sendDataToServer(request);
        });
    }

    private void sendDataToServer(ContactUsRequest request) {
        ClientRetrofit clientRetrofit = new ClientRetrofit();
        clientRetrofit.SendDataViaApi(request, new ContactUsSuccessResponse() {
            @Override
            public void onSuccess(ContactUsResponse response) {
                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });

    }

    private void addViews(View view) {
        name = view.findViewById(R.id.contact_name_edit_text);
        email = view.findViewById(R.id.contact_email_edit_text);
        comments = view.findViewById(R.id.contact_comments_edit_text);
        sendBtn = view.findViewById(R.id.contact_button);
        imgBack = view.findViewById(R.id.img_back);

    }

    private void onClickBackButton() {
        imgBack.setOnClickListener(view1 -> getActivity().onBackPressed());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
