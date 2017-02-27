package com.nineinfosys.android.admobadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.clockbyte.admobadapter.AdmobAdapterWrapper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvMessages;
    AdmobAdapterWrapper adapterWrapper;
    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private static final String ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(getApplicationContext(), ADMOB_APP_ID);

        initListViewItems();
    }
    private void initListViewItems() {
        lvMessages = (ListView) findViewById(R.id.lvMessages);

        //creating your adapter, it could be a custom adapter as well
        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        //test devices' ids
        String[] testDevicesIds = new String[]{getString(R.string.testDeviceID), AdRequest.DEVICE_ID_EMULATOR};
        //when you'll be ready for release please use another ctor with admobReleaseUnitId instead.
        adapterWrapper = new AdmobAdapterWrapper(this, testDevicesIds);

        //By default both types of ads are loaded by wrapper.
        // To set which of them to show in the list you should use an appropriate ctor
        //adapterWrapper = new AdmobAdapterWrapper(this, null, EnumSet.of(EAdType.ADVANCED_INSTALLAPP));

        //wrapping your adapter with a AdmobAdapterWrapper.
        adapterWrapper.setAdapter(adapter);

        //inject your custom layout and strategy of binding for installapp/content  ads
        //here you should pass the extended NativeAdLayoutContext
        //by default it has a value InstallAppAdLayoutContext.getDefault()
        //adapterWrapper.setInstallAdsLayoutContext(...);
        //by default it has a value ContentAdLayoutContext.getDefault()
        //adapterWrapper.setContentAdsLayoutContext(...);

        //Sets the max count of ad blocks per dataset, by default it equals to 3 (according to the Admob's policies and rules)
        adapterWrapper.setLimitOfAds(50);

        //Sets the number of your data items between ad blocks, by default it equals to 10.
        //You should set it according to the Admob's policies and rules which says not to
        //display more than one ad block at the visible part of the screen,
        // so you should choose this parameter carefully and according to your item's height and screen resolution of a target devices
        adapterWrapper.setNoOfDataBetweenAds(5);

        //Sets the first ad block index (zero-based) in the adapter, by default it equals to 0
        adapterWrapper.setFirstAdIndex(5);

        lvMessages.setAdapter(adapterWrapper); // setting an AdmobAdapterWrapper to a ListView

        //preparing the collection of data
        final String sItem = "item #";
        ArrayList<String> lst = new ArrayList<String>(100);
        for(int i=1;i<=100;i++)
            lst.add(sItem.concat(Integer.toString(i)));

        //adding a collection of data to your adapter and rising the data set changed event
        adapter.addAll(lst);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //adapterWrapper.release();
    }

}
