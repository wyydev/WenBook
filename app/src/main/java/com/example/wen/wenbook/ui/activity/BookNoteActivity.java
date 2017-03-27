package com.example.wen.wenbook.ui.activity;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.BookNoteBean;
import com.example.wen.wenbook.common.util.CameraUtils;
import com.example.wen.wenbook.ui.widget.RichEditor;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookNoteActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rich_editor)
    RichEditor mRichEditor;
    @BindView(R.id.btn_camera)
    Button mBtnCamera;
    @BindView(R.id.btn_gallery)
    Button mBtnGallery;
    private Unbinder mUnBinder;
    private static final String EXTRA_BOOK_NOTE_ID = "com.example.wen.criminalintent.book_note_id";
    private static final String EXTRA_BOOK_ISBN = "com.example.wen.criminalintent.book_isbn";
    private static final int TAKE_PHOTO = 1;
    private static final int GALLERY = 2;
    private static final int PERMISSION_PHOTO = 3;
    private static final int PERMISSION_ALBUM = 4;
    private BookNoteBean mBookNoteBean = null;
    private String mUUID;
    private String isbn;
    private Uri imageUri;
    public static final String mBitmapTag = "☆";
    private String mNewLineTag = "\n";




    //根据book_note_id返回一个带crimeId的Intent,用于确定某个具体的book_note
    public static Intent newIntent(Context packageContext, String book_note_id,String isbn) {
        Intent intent = new Intent(packageContext, BookNoteActivity.class);
        intent.putExtra(EXTRA_BOOK_NOTE_ID, book_note_id);
        intent.putExtra(EXTRA_BOOK_ISBN, isbn);
        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_note);
        mUnBinder =  ButterKnife.bind(this);


        init();

        initBtnClick();

    }



    private void init() {

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }


        //拿到传过来的uuid 和 isbn
        mUUID =  getIntent().getStringExtra(EXTRA_BOOK_NOTE_ID);

        isbn = getIntent().getStringExtra(EXTRA_BOOK_ISBN);

        Log.d("BookNoteActivity","传过来的mUUID为："+mUUID);
        Log.d("BookNoteActivity","传过来的isbn为："+isbn);

        //若uuid已存在，回显笔记
        List<BookNoteBean> bookNoteBeen = DataSupport.where("isbn=? and mNoteId = ?", isbn,mUUID).find(BookNoteBean.class);

    /*    for (BookNoteBean bookNote:bookNoteBeen){
            if (bookNote.getNoteId().equals(mUUID)){
                mBookNoteBean = bookNote;
                break;
            }
        }*/

        if (bookNoteBeen.size() > 0){
            Log.d("BookNoteActivity","回显时获取笔记的数目："+bookNoteBeen.size());
            mBookNoteBean = bookNoteBeen.get(0);
        }



        if (mBookNoteBean != null){
            //已经存在该笔记，并且笔记内容不为空，进行回显
            List<String> backUpContents = mBookNoteBean.getNoteContent();

             if (backUpContents != null){
                 for (String line: backUpContents){
                     if (line.endsWith(".jpg")){
                         Log.i("BookNoteActivity", "添加了图片 = " +line);
                         mRichEditor.insertBitmap(line);
                         continue;
                     }else {
                         SpannableString ss = new SpannableString(line);
                         Log.i("BookNoteActivity", "添加了文字 = " + line);
                         mRichEditor.append(ss);
                     }

                 }
             }

        }else {
            //不存在,根据传过来的uuid和isbn新建笔记
            mBookNoteBean = new BookNoteBean(mUUID);
            mBookNoteBean.setIsbn(isbn);
            //保存到数据库
            mBookNoteBean.save();
        }


    }


    /**
     * 处理拍照按钮和打开相册按钮
     */
    private void initBtnClick() {

        mBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //请求相机权限
                if (ContextCompat.checkSelfPermission(BookNoteActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BookNoteActivity.this,
                            new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_PHOTO);
                } else {
                    imageUri =  CameraUtils.takePhotoAndroid6(BookNoteActivity.this,TAKE_PHOTO);
                }

            }
        });


        mBtnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //请求存储权限
                if (ContextCompat.checkSelfPermission(BookNoteActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BookNoteActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_ALBUM);
                } else {
                    imageUri =  CameraUtils.getGalleryImg(BookNoteActivity.this,GALLERY);
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.book_note_menu, menu);
        //返回true才会显示overflow按钮
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                //提示是否取消本次编辑
                this.finish();

                return true;

            case R.id.save_note:

                //保存当前的笔记
                saveNote();

                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String s = mRichEditor.getText().toString().replaceAll(mNewLineTag, "");
        List<String> contents = new ArrayList<>();

        if (s.length() > 0 && s.contains(mBitmapTag)){ //有内容并且包含图片
            String[] split = s.split("☆");
            contents.clear();
                for (String split_string:split){

                    contents.add(split_string);

                  //  Log.i("BookNoteActivity", "split添加了文字 = " + split_string);

                }

        }else {
            //直接添加文字
            contents.add(s);
          //  Log.i("BookNoteActivity", "split else添加了文字 = " + s);
        }


        if (contents.size() > 0){
            mBookNoteBean.setNoteContent(contents);
            mBookNoteBean.updateAllAsync("mNoteId = ?",mUUID).listen(new UpdateOrDeleteCallback() {
                @Override
                public void onFinish(int rowsAffected) {
                    Toast.makeText(BookNoteActivity.this, "更新了"+rowsAffected, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    @Override
    public void onBackPressed() {
        BookNoteActivity.this.finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
                    //获取了权限

                 imageUri = CameraUtils.takePhotoAndroid6(BookNoteActivity.this,TAKE_PHOTO);

                } else {
                    Toast.makeText(this, "使用该功能前需要开启相机权限和存储权限", Toast.LENGTH_SHORT).show();
                    BookNoteActivity.this.finish();
                }
                return;

            case PERMISSION_ALBUM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //获取了权限

                    imageUri = CameraUtils.getGalleryImg(BookNoteActivity.this,GALLERY);

                } else {
                    Toast.makeText(this, "使用该功能前需要开启存储权限", Toast.LENGTH_SHORT).show();
                    BookNoteActivity.this.finish();
                }
                return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (resultCode == RESULT_CANCELED){
            //没有拍照或者没有选择图片
            return;

        }else if (resultCode == RESULT_OK){


            switch (requestCode){


                case TAKE_PHOTO:
                    Uri uri_take_photo = null;
                    if (data != null && data.getData() != null) {
                        uri_take_photo = data.getData();
                    } else {
                        //解决部分手机返回data为空问题
                        uri_take_photo  = imageUri;
                       // Toast.makeText(this,"data 为空", Toast.LENGTH_SHORT).show();
                    }

                    String imageAbsolutePath = CameraUtils.getRealFilePathByUri(BookNoteActivity.this, uri_take_photo);

                  //  Log.i("BookNoteActivity", "imageAbsolutePath = " + imageAbsolutePath);

                  //  Log.i("BookNoteActivity", "uri = " + uri_take_photo);

                    mRichEditor.insertBitmap(imageAbsolutePath);

                    break;


                case GALLERY:

                    Uri uri_gallery = null;

                    if (data != null && data.getData() != null) {
                        uri_gallery = data.getData();
                    } else {
                        //解决部分手机返回data为空问题
                        uri_gallery  = imageUri;
                        // Toast.makeText(this,"data 为空", Toast.LENGTH_SHORT).show();
                    }

                    String imageAbsolutePath_gallery = CameraUtils.getRealFilePathByUri(BookNoteActivity.this, uri_gallery);

                  //  Log.i("BookNoteActivity", "imageAbsolutePath = " + imageAbsolutePath_gallery);

                  //  Log.i("BookNoteActivity", "uri = " + uri_gallery);

                    mRichEditor.insertBitmap(imageAbsolutePath_gallery);

                    break;


                default:
                    break;




            }



        }else {

            //出错
            Toast.makeText(this, "获取图片失败，请检查是否开启权限", Toast.LENGTH_SHORT).show();


        }






    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        saveNote();

        if (mUnBinder != Unbinder.EMPTY){
            mUnBinder.unbind();
        }

    }
}
