package com.example.tikicloneapp.activities;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tikicloneapp.R;
import com.example.tikicloneapp.adapters.TabLayoutViewPagerAdapter;
import com.example.tikicloneapp.fragments.AdminTransactFragment;
import com.example.tikicloneapp.models.Transact;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.tikicloneapp.activities.AdminManagementActivity.httpHandler;

public class AdminTransactManagementActivity extends AppCompatActivity {

    private static final String TAG = AdminTransactManagementActivity.class.getSimpleName();

    private ImageButton ibBack, ibRefresh;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    TabLayoutViewPagerAdapter adapter;

    AdminTransactFragment notConfirmFragment;
    AdminTransactFragment pickingGoodsFragment;
    AdminTransactFragment deliveringFragment;
    AdminTransactFragment deliveredFragment;
    AdminTransactFragment cancelledFragment;

    ArrayList<Transact> notConfirmTransacts;
    ArrayList<Transact> pickingGoodsTransacts;
    ArrayList<Transact> deliveringTransacts;
    ArrayList<Transact> deliveredTransacts;
    ArrayList<Transact> cancelledTransacts;

    final int CODE_GET_ALL = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_transact_management);

        initWidget();

        setViews();
    }

    private void initWidget() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        ibBack = findViewById(R.id.imageButton_back);
        ibRefresh = findViewById(R.id.imageButton_refresh);
    }

    private void setViews() {
        setOnClickView();
        new FetchTransacts().execute(CODE_GET_ALL);
    }


    private void setOnClickView() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else finish();
            }
        });

        ibRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                refreshTransact(CODE_GET_ALL);
            }
        });

    }

    private void setViewPager() {
        adapter = new TabLayoutViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(notConfirmFragment, "Chưa xác nhận");
        adapter.addFragment(pickingGoodsFragment, "Đang lấy hàng");
        adapter.addFragment(deliveringFragment, "Đang giao hàng");
        adapter.addFragment(deliveredFragment, "Đã nhận hàng");
        adapter.addFragment(cancelledFragment, "Đã hủy");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class FetchTransacts extends AsyncTask<Integer, Void, Void> {


        @Override
        protected Void doInBackground(Integer... status) {
            switch (status[0]) {
                case Transact.STATUS_CANCEL:
                    cancelledTransacts = getTransacts(status[0]);
                    break;
                case Transact.STATUS_TIKI_RECEIVED:
                    notConfirmTransacts = getTransacts(status[0]);
                    pickingGoodsTransacts = getTransacts(status[0]);
                    break;
                case Transact.STATUS_PICKING_GOODS:
                    pickingGoodsTransacts = getTransacts(status[0]);
                    deliveringTransacts = getTransacts(status[0]);
                    break;
                case Transact.STATUS_DELIVERING:
                    deliveringTransacts = getTransacts(status[0]);
                    break;
                case Transact.STATUS_SUCCESS:
                    deliveredTransacts = getTransacts(status[0]);
                    break;
                case CODE_GET_ALL:
                    notConfirmTransacts = getTransacts(Transact.STATUS_TIKI_RECEIVED);
                    pickingGoodsTransacts = getTransacts(Transact.STATUS_PICKING_GOODS);
                    deliveringTransacts = getTransacts(Transact.STATUS_DELIVERING);
                    deliveredTransacts = getTransacts(Transact.STATUS_SUCCESS);
                    cancelledTransacts = getTransacts(Transact.STATUS_CANCEL);
                    break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notConfirmFragment = new AdminTransactFragment(notConfirmTransacts);
            pickingGoodsFragment = new AdminTransactFragment(pickingGoodsTransacts);
            deliveringFragment = new AdminTransactFragment(deliveringTransacts);
            deliveredFragment = new AdminTransactFragment(deliveredTransacts);
            cancelledFragment = new AdminTransactFragment(cancelledTransacts);
            setViewPager();
        }
    }

    private class ReFetchTransacts extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... status) {
            switch (status[0]) {
                case Transact.STATUS_CANCEL:
                    cancelledTransacts = getTransacts(status[0]);
                    break;
                case Transact.STATUS_TIKI_RECEIVED:
                    notConfirmTransacts = getTransacts(status[0]);
                    pickingGoodsTransacts = getTransacts(status[0]);
                    break;
                case Transact.STATUS_PICKING_GOODS:
                    pickingGoodsTransacts = getTransacts(status[0]);
                    deliveringTransacts = getTransacts(status[0]);
                    break;
                case Transact.STATUS_DELIVERING:
                    deliveringTransacts = getTransacts(status[0]);
                    break;
                case Transact.STATUS_SUCCESS:
                    deliveredTransacts = getTransacts(status[0]);
                    break;
                case CODE_GET_ALL:
                    notConfirmTransacts = getTransacts(Transact.STATUS_TIKI_RECEIVED);
                    pickingGoodsTransacts = getTransacts(Transact.STATUS_PICKING_GOODS);
                    deliveringTransacts = getTransacts(Transact.STATUS_DELIVERING);
                    deliveredTransacts = getTransacts(Transact.STATUS_SUCCESS);
                    cancelledTransacts = getTransacts(Transact.STATUS_CANCEL);
                    break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notConfirmFragment = new AdminTransactFragment(notConfirmTransacts);
            pickingGoodsFragment = new AdminTransactFragment(pickingGoodsTransacts);
            deliveringFragment = new AdminTransactFragment(deliveringTransacts);
            deliveredFragment = new AdminTransactFragment(deliveredTransacts);
            cancelledFragment = new AdminTransactFragment(cancelledTransacts);
            Log.d(TAG, /*"pickingGoodsTransacts: " + pickingGoodsTransacts.toString()*/
                     "; deliveringTransacts: " + deliveringTransacts.toString());

            adapter.notifyDataSetChanged();
        }
    }

    private void refreshTransact(Integer status) {
        new ReFetchTransacts().execute(status);
    }

    private ArrayList<Transact> getTransacts(int status) {
        ArrayList<Transact> _transact = new ArrayList<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("status", String.valueOf(status));
        String jsonStr = httpHandler.performPostCall(AdminManagementActivity.dbVolley.URL_GET_TRANSACT_BY_ADMIN, params);
        if (jsonStr != null) {
//            Log.d(TAG, "jsonStr: " + jsonStr);
            try {

                JSONArray jsonArray = new JSONArray(jsonStr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Transact transact = new Transact(
                                object
                        );
                        _transact.add(transact);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return _transact;
    }


}