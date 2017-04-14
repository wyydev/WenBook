package com.example.wen.wenbook.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Gank;
import com.example.wen.wenbook.bean.TodayGank;
import com.example.wen.wenbook.common.util.ImageLoder;
import com.example.wen.wenbook.ui.activity.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wen on 2017/4/12.
 */

public class TodayGankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private TodayGank mTodayGank;
    private Context mContext;
    private final int TYPE_HEADER = 0;
    private final int TYPE_DATA = 1;
    private List<Gank> mGankList;

    public TodayGankAdapter(TodayGank todayGank, Context context) {
        this.mTodayGank = todayGank;
        this.mContext = context;
        handleGank(mTodayGank);
    }

    /**
     * 给每个分类添加头部
     * @param todayGank
     */
    private void handleGank(TodayGank todayGank) {
        mGankList = new ArrayList<>();
        if (todayGank.results.getAndroid() != null){
            addGankListWithHeader(todayGank.results.getAndroid());
        }
        if (todayGank.results.getiOS() != null){
            addGankListWithHeader(todayGank.results.getiOS());
        }
        if (todayGank.results.getWeb() != null){
            addGankListWithHeader(todayGank.results.getWeb());
        }
        if (todayGank.results.getApp() != null){
            addGankListWithHeader(todayGank.results.getApp());
        }
        if (todayGank.results.getExpand_resources() != null){
            addGankListWithHeader(todayGank.results.getExpand_resources());
        }
        if (todayGank.results.getRelax_video() != null){
            addGankListWithHeader(todayGank.results.getRelax_video());
        }
        if (todayGank.results.getRandom_recommend() != null){
            addGankListWithHeader(todayGank.results.getRandom_recommend());
        }
        if (todayGank.results.getWelfare() != null){
            addGankListWithHeader(todayGank.results.getWelfare());
        }

    }

    private void addGankListWithHeader(List<Gank> ganks) {
        Gank gank = new Gank(ganks.get(0).type);
        gank.isHeader = true;
        mGankList.add(gank);
        mGankList.addAll(ganks);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View headerView = View.inflate(mContext, R.layout.item_today_header, null);
        View itemView = View.inflate(mContext, R.layout.item_today_main, null);
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(headerView);
            case TYPE_DATA:
                return new GankViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
     /*   if (mTodayGank != null){
            return mTodayGank.getCategory().size() + mTodayGank.results.getAndroid().size()+mTodayGank.results.getiOS().size()
                    +mTodayGank.results.getRelax_video().size()+mTodayGank.results.getExpand_resources().size()+mTodayGank.results.getRandom_recommend().size()
                    +mTodayGank.results.getWelfare().size();
        } else {
            return 0;
        }*/
         return mGankList.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.bindViewHolder(mGankList.get(position));
        }
        if (holder instanceof GankViewHolder){
            GankViewHolder gankViewHolder = (GankViewHolder) holder;


            gankViewHolder.bindViewHolder(mGankList.get(position));
        }



     /*   if (position == 0){ //android
            HeaderViewHolder headerViewHolder1 = (HeaderViewHolder) holder;
            headerViewHolder1.mHeaderView.setText("Android");

        }else if (position >0 && position <= mTodayGank.results.Android.size()){ //android数据
            GankViewHolder gankViewHolder = (GankViewHolder) holder;
            gankViewHolder.mMainItemContent.setText(mTodayGank.results.Android.get(position-1).desc);

            //设置图片
            if (mTodayGank.results.Android.get(position-1).images != null){

                Glide.with(mContext).load(mTodayGank.results.Android.get(position-1).images.get(0)).asBitmap()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher).into(gankViewHolder.mMainItemImage);

                gankViewHolder.mMainItemImage.setVisibility(View.VISIBLE);
            }else {
                gankViewHolder.mMainItemImage.setVisibility(View.GONE);
            }



        }else if ( position == mTodayGank.results.Android.size() + 1){  //ios
            HeaderViewHolder headerViewHolder1 = (HeaderViewHolder) holder;
            headerViewHolder1.mHeaderView.setText("iOS");

        }else if (position >mTodayGank.results.Android.size() + 2  && position < mTodayGank.results.Android.size() + 1
                +mTodayGank.results.iOS.size() + 1 ){//ios数据
            GankViewHolder gankViewHolder = (GankViewHolder) holder;
            gankViewHolder.mMainItemContent.setText(mTodayGank.results.iOS.get(position-mTodayGank.results.Android.size() - 2).desc);
            gankViewHolder.mMainItemImage.setVisibility(View.GONE);

            //设置图片
            if (mTodayGank.results.iOS.get(position-mTodayGank.results.Android.size() - 2).images != null){

                Glide.with(mContext).load(mTodayGank.results.iOS.get(position-mTodayGank.results.Android.size() - 2).images.get(0)).asBitmap()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher).into(gankViewHolder.mMainItemImage);

                gankViewHolder.mMainItemImage.setVisibility(View.VISIBLE);
            }else {
                gankViewHolder.mMainItemImage.setVisibility(View.GONE);
            }

        }else if (position == mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1 ) {//休息视频
            HeaderViewHolder headerViewHolder1 = (HeaderViewHolder) holder;
            headerViewHolder1.mHeaderView.setText("休息视频");
        }else if (position > mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1
                && position<mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1 + mTodayGank.results.relax_video.size() +1){//休息视频数据

            GankViewHolder gankViewHolder = (GankViewHolder) holder;
            gankViewHolder.mMainItemContent
                    .setText(mTodayGank.results.relax_video.get(position-mTodayGank.results.Android.size()- mTodayGank.results.iOS.size()- 3).desc);


            //设置图片
            if (mTodayGank.results.relax_video.get(position-mTodayGank.results.Android.size() - mTodayGank.results.iOS.size()- 3).images != null){

                Glide.with(mContext).load(mTodayGank.results.relax_video.get(position-mTodayGank.results.Android.size() - mTodayGank.results.iOS.size()- 3).images.get(0)).asBitmap()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher).into(gankViewHolder.mMainItemImage);

                gankViewHolder.mMainItemImage.setVisibility(View.VISIBLE);
            }else {
                gankViewHolder.mMainItemImage.setVisibility(View.GONE);
            }

        }else if ( position == mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1 + mTodayGank.results.relax_video.size() + 1 ){//拓展资源
            HeaderViewHolder headerViewHolder1 = (HeaderViewHolder) holder;
            headerViewHolder1.mHeaderView.setText("拓展资源");

        }else if ( position > mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1 + mTodayGank.results.relax_video.size() + 1
                && position<mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1
                + mTodayGank.results.relax_video.size() +1 + mTodayGank.results.expand_resources.size() +1){//拓展资源数据
            GankViewHolder gankViewHolder = (GankViewHolder) holder;
            gankViewHolder.mMainItemContent
                    .setText(mTodayGank.results.expand_resources.get(position-mTodayGank.results.Android.size()- mTodayGank.results.iOS.size()
                            -mTodayGank.results.relax_video.size()- 4).desc);

            //设置图片
            if (mTodayGank.results.expand_resources.get(position-mTodayGank.results.Android.size() -
                    mTodayGank.results.iOS.size()-mTodayGank.results.relax_video.size()- 4).images != null){

                Glide.with(mContext).load(mTodayGank.results.expand_resources.get(position-mTodayGank.results.Android.size() -
                        mTodayGank.results.iOS.size()-mTodayGank.results.relax_video.size()- 4).images.get(0)).asBitmap()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher).into(gankViewHolder.mMainItemImage);

                gankViewHolder.mMainItemImage.setVisibility(View.VISIBLE);
            }else {
                gankViewHolder.mMainItemImage.setVisibility(View.GONE);
            }

        }else if (position == mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1
                + mTodayGank.results.relax_video.size() + 1 + mTodayGank.results.expand_resources.size() + 1){ //瞎推荐
            HeaderViewHolder headerViewHolder1 = (HeaderViewHolder) holder;
            headerViewHolder1.mHeaderView.setText("瞎推荐");
        }else if (position >mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1
                + mTodayGank.results.relax_video.size() + 1 + mTodayGank.results.expand_resources.size() + 1
                && position < mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1
                + mTodayGank.results.relax_video.size() + 1 + mTodayGank.results.expand_resources.size() + 1 + mTodayGank.results.random_recommend.size() + 1){//瞎推荐数据

            GankViewHolder gankViewHolder = (GankViewHolder) holder;
            gankViewHolder.mMainItemContent
                    .setText(mTodayGank.results.random_recommend.get(position-mTodayGank.results.Android.size()- mTodayGank.results.iOS.size()
                            -mTodayGank.results.relax_video.size() - mTodayGank.results.expand_resources.size() - 5).desc);

            //设置图片
            if (mTodayGank.results.random_recommend.get(position-mTodayGank.results.Android.size()- mTodayGank.results.iOS.size()
                    -mTodayGank.results.relax_video.size() - mTodayGank.results.expand_resources.size() - 5).images != null){

                Glide.with(mContext).load(mTodayGank.results.random_recommend.get(position-mTodayGank.results.Android.size()- mTodayGank.results.iOS.size()
                        -mTodayGank.results.relax_video.size() - mTodayGank.results.expand_resources.size() - 5).images.get(0)).asBitmap()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher).into(gankViewHolder.mMainItemImage);

                gankViewHolder.mMainItemImage.setVisibility(View.VISIBLE);
            }else {
                gankViewHolder.mMainItemImage.setVisibility(View.GONE);
            }


        }else if (position == mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1
                + mTodayGank.results.relax_video.size() + 1 + mTodayGank.results.expand_resources.size() + 1 + mTodayGank.results.random_recommend.size() + 1){ //福利
            HeaderViewHolder headerViewHolder1 = (HeaderViewHolder) holder;
            headerViewHolder1.mHeaderView.setText("福利");

        }else if (position > mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1
                + mTodayGank.results.relax_video.size() + 1 + mTodayGank.results.expand_resources.size() + 1 + mTodayGank.results.random_recommend.size() + 1
                && position < mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1
                + mTodayGank.results.relax_video.size() + 1 + mTodayGank.results.expand_resources.size() + 1 + mTodayGank.results.random_recommend.size() + 1
                +mTodayGank.results.welfare.size() + 1){//福利数据
            GankViewHolder gankViewHolder = (GankViewHolder) holder;

            gankViewHolder.mMainItemContent
                    .setText(mTodayGank.results.welfare.get(position-mTodayGank.results.Android.size()- mTodayGank.results.iOS.size()
                            -mTodayGank.results.relax_video.size() - mTodayGank.results.expand_resources.size() - mTodayGank.results.random_recommend.size() - 6).desc);
            //设置图片
            if (!TextUtils.isEmpty(mTodayGank.results.welfare.get(position-mTodayGank.results.Android.size()- mTodayGank.results.iOS.size()
                    -mTodayGank.results.relax_video.size() - mTodayGank.results.expand_resources.size() - mTodayGank.results.random_recommend.size() - 6).url) && mTodayGank.results.welfare.get(position-mTodayGank.results.Android.size()- mTodayGank.results.iOS.size()
                    -mTodayGank.results.relax_video.size() - mTodayGank.results.expand_resources.size() - mTodayGank.results.random_recommend.size() - 6).url.endsWith(".jpg")){
                Glide.with(mContext).load(mTodayGank.results.welfare.get(position-mTodayGank.results.Android.size()- mTodayGank.results.iOS.size()
                        -mTodayGank.results.relax_video.size() - mTodayGank.results.expand_resources.size() - mTodayGank.results.random_recommend.size() - 6).url).asBitmap()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher).into(gankViewHolder.mMainItemImage);

                gankViewHolder.mMainItemImage.setVisibility(View.VISIBLE);
            }else {
                gankViewHolder.mMainItemImage.setVisibility(View.GONE);
            }

        }*/


    }

    @Override
    public int getItemViewType(int position) {

    /*    if (position == 0 || position == mTodayGank.results.Android.size() + 1

                || position == mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1

                || position == mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1
                + mTodayGank.results.relax_video.size() + 1

                || position == mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1
                + mTodayGank.results.relax_video.size() + 1 + mTodayGank.results.expand_resources.size() + 1

                || position == mTodayGank.results.Android.size() + 1 + mTodayGank.results.iOS.size() + 1
                + mTodayGank.results.relax_video.size() + 1 + mTodayGank.results.expand_resources.size() + 1 + mTodayGank.results.random_recommend.size() + 1
                ) {
            return TYPE_HEADER;
        } else {
            return TYPE_DATA;
        }*/

        if(mGankList.get(position).isHeader){
            return TYPE_HEADER;
        }else {
            return TYPE_DATA;
        }

    }

    public class GankViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.main_item_content)
        TextView mMainItemContent;
        @BindView(R.id.main_item_image)
        ImageView mMainItemImage;
        private Gank mGank;


        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("gank",mGank);
                    mContext.startActivity(intent);
                }
            });

        }

        public void bindViewHolder(Gank gank){
            this.mGank = gank;

            mMainItemContent.setText(gank.desc);
            if (gank.images!=null){
                ImageLoder.loadImage(mContext,gank.images.get(0),mMainItemImage);
                mMainItemImage.setVisibility(View.VISIBLE);
            }else if (gank.type.equals("福利")){
                ImageLoder.loadImage(mContext,gank.url,mMainItemImage);
                mMainItemImage.setVisibility(View.VISIBLE);
            }
            else {
                mMainItemImage.setVisibility(View.GONE);
            }

        }


    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.main_header)
        TextView mHeaderView;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindViewHolder(Gank gank){
            mHeaderView.setText(gank.type);
        }

    }


}
