package com.aivie.aivie.user.presentation.icf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.data.user.UserIcfManagement;
import com.aivie.aivie.user.presentation.utils.ProgressDialogUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IcfListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ArrayList<HashMap> icfList = new ArrayList<HashMap>();
    ListView listViewIcf;
    IcfListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icf_list);

        getSupportActionBar().setTitle("eICF Document History");

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        listViewIcf = findViewById(R.id.listViewIcfHistory);
        adapter = new IcfListAdapter(this, icfList);
        listViewIcf.setAdapter(adapter);
        listViewIcf.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), IcfActivity.class);
                intent.putExtra(Constant.SIGNATURE_URI, icfList.get(i).get((Constant.FIRE_ICF_COLUMN_SIGNATURE_URL)).toString());
                if (icfList.get(i).get(Constant.FIRE_ICF_COLUMN_SIGNED).equals("true")) {
                    intent.putExtra(Constant.SIGNATURE_SIGNED, true);
                } else {
                    intent.putExtra(Constant.SIGNATURE_SIGNED, false);
                }
                startActivity(intent);
            }
        });

        displayIcfList();
    }

    private void addItem() {
        HashMap temp = new HashMap();
        temp.put(Constant.FIRE_ICF_COLUMN_DOC_ID, "0");
        temp.put(Constant.FIRE_ICF_COLUMN_SIGNED, Boolean.toString(false));
        temp.put(Constant.FIRE_ICF_COLUMN_SIGNED_DATE, "2020-01-01");
        icfList.add(temp);
    }

    private void displayIcfList() {
        getUserIcfHistory();
    }

    private void getUserIcfHistory() {

        ProgressDialogUtil.showProgressDialog(this);

        db.collection(Constant.FIRE_COLLECTION_USERS)
                .document(mAuth.getCurrentUser().getUid())
                .collection(Constant.FIRE_COLLECTION_ICF_HISTORY)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot queryDocumentSnapshot : Objects.requireNonNull(task.getResult())) {

                                // To ignore the initial one (index 0) in databse which is built when sign up
                                if (Integer.parseInt(queryDocumentSnapshot.getId()) > 0) {

                                    String icfID = (String) queryDocumentSnapshot.getData().get(Constant.FIRE_ICF_COLUMN_DOC_ID);
                                    boolean icfSigned = (boolean) queryDocumentSnapshot.getData().get(Constant.FIRE_ICF_COLUMN_SIGNED);

                                    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
                                    Timestamp tsBirthday = (Timestamp) queryDocumentSnapshot.getData().get(Constant.FIRE_ICF_COLUMN_SIGNED_DATE);
                                    Date dateBirthday = tsBirthday.toDate();
                                    String signedDate = sfd.format(dateBirthday);

                                    String signatureUrl = (String) queryDocumentSnapshot.getData().get(Constant.FIRE_ICF_COLUMN_SIGNATURE_URL);

                                    HashMap temp = new HashMap();
                                    temp.put(Constant.FIRE_ICF_COLUMN_DOC_ID, icfID);
                                    temp.put(Constant.FIRE_ICF_COLUMN_SIGNED, Boolean.toString(icfSigned));
                                    temp.put(Constant.FIRE_ICF_COLUMN_SIGNED_DATE, signedDate);
                                    temp.put(Constant.FIRE_ICF_COLUMN_SIGNATURE_URL, signatureUrl);
                                    icfList.add(temp);
                                    
                                    adapter.notifyDataSetChanged();

                                    ProgressDialogUtil.dismiss();
                                }
                            }
                        } else {
                            Log.d(Constant.TAG, "getUserIcfHistory", task.getException());
                        }
                    }
                });
    }
}
