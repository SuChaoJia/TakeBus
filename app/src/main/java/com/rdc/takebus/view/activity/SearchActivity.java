package com.rdc.takebus.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rdc.takebus.R;
import com.rdc.takebus.model.adapter.SearchAdapter;
import com.rdc.takebus.presenter.ActivityPresenter.SearchPresenter;
import com.rdc.takebus.presenter.tbinterface.ListViewInterface;

import java.util.List;
import java.util.Map;

/**
 * Created by 梦涵 on 2016/5/10.
 */
public class SearchActivity extends BaseActivity<ListViewInterface, SearchPresenter>
implements ListViewInterface {
    private ListView lv;
    private TextView tv_station, tv_place;
    private SearchAdapter adapter;
    private EditText etSearch;
    private Button btn_cancle;
    private String mStrResult;
    private ImageView img_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }


    @Override
    protected void loadData() {
       //初始化数据
        mPresenter.initData();
       //滑动加载更多
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE && lv.getLastVisiblePosition() == lv.getCount() - 1){
                    mPresenter.loadData();
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        lv = (ListView) findViewById(R.id.lv);
        tv_station = (TextView) findViewById(R.id.tv_station);
        tv_place = (TextView) findViewById(R.id.tv_place);
        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.setHint(getIntent().getStringExtra("station"));
        img_history = (ImageView)findViewById(R.id.img_history);
        adapter = new SearchAdapter(this);
        btn_cancle = (Button) findViewById(R.id.btn_ok);
        btn_cancle.setOnClickListener(this);
    }

    @Override
    protected void initVariables() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                mStrResult = etSearch.getText().toString();
                if (mStrResult.length() != 0) {
                    Intent intent = new Intent();
                    intent.setClass(SearchActivity.this, MainActivity.class);
                    intent.putExtra("result", mStrResult);
                    SearchActivity.this.setResult(1,intent);
                    SearchActivity.this.finish();
                    overridePendingTransition(R.anim.translate_left_in,
                            R.anim.translate_right_out);
                } else {
                    startActivity(new Intent(this, SelectRouteActivity.class));
                    finish();
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void addData(List<Map<String, Object>> list) {
        adapter.setData(list);
        lv.setAdapter(adapter);
    }

    @Override
    public void moreData(List<Map<String, Object>> data) {
        adapter.moreData(data);
    }

}
