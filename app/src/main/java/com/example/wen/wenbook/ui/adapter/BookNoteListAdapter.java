package com.example.wen.wenbook.ui.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.BookNoteBean;
import com.example.wen.wenbook.common.font.WenFont;
import com.mikepenz.iconics.IconicsDrawable;

import java.io.File;
import java.util.List;

/**
 * Created by wen on 2017/3/27.
 */

public class BookNoteListAdapter extends BaseQuickAdapter<BookNoteBean,BaseViewHolder> {

    private String imagePath = null;

    private String mContent;

    private String date;


    public BookNoteListAdapter() {
        super(R.layout.book_note_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookNoteBean item) {

        //绑定数据

        List<String> noteContent = item.getNoteContent();
        if (noteContent != null){
            for (String content:noteContent) {
                //找到笔记中第一张图片的路径
                if (content.endsWith(".jpg")){
                    imagePath = content;
                    break;
                }
            }
        }


        ImageView noteImage = helper.getView(R.id.img_book_note);
        noteImage.setVisibility(imagePath == null ? View.GONE : View.VISIBLE);

        //加载笔记图片
        if (imagePath != null){
            File image = new File(imagePath);
            Glide.with(noteImage.getContext())
                    .load(image)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(new IconicsDrawable(noteImage.getContext()).icon(WenFont.Icon.wen_book).color(Color.GRAY).paddingDp(10))
                    .into(noteImage);
        }

        if (noteContent != null){
            //加载笔记内容缩略简介
            for (String content:noteContent) {
                //找到笔记中第一段文字
                if ((!content.endsWith(".jpg")) && !TextUtils.isEmpty(content)){
                    mContent = content;
                    break;
                }
            }
        }



        String dateFormat = "MM月dd日";
        date = DateFormat.format(dateFormat, item.getDate()).toString();

        helper.setText(R.id.tv_book_note_title,mContent)
        .setText(R.id.tv_book_note_date,date);


    }

}
