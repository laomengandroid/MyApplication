package com.example.administrator.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;
    private Button mButton;
    private Thread mThread;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //进行UI更新
        mImageView = (ImageView) findViewById(R.id.threadhandler_imageView);//显示图片的ImageView
        mButton = (Button) findViewById(R.id.threadhandler_download_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mThread == null) {
                    mThread = new Thread(new GameThread());
                    mThread.start();//线程启动
                    Log.d("laomeng", "线程开启");
                } else {
                    Log.d("laomeng","线程存在");
                }
            }
        });
    }
    // 实例化一个handler
    Handler myHandler   = new Handler()
    {
        //接收到消息后处理
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case  MainActivity.RESULT_OK:
                    mImageView.invalidate();        //刷新界面
                    break;
            }
            super.handleMessage(msg);
        }
    };
    class GameThread implements Runnable
    {
        public void run()
        {
            while (!Thread.currentThread().isInterrupted())
            {

                try {
                   URL   url = new URL("http://pic7.nipic.com/20100517/4945412_113951650422_2.jpg");
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.connect();
                    InputStream inputStream=urlConn.getInputStream();
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);
                    mImageView.setImageBitmap(bm);

                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

                Message message = new Message();
                message.what = MainActivity.RESULT_OK;
                //发送消息
                MainActivity.this.myHandler.sendMessage(message);
                try
                {
                    Thread.sleep(100);

                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }





            @Override
            public boolean onCreateOptionsMenu (Menu menu){
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_main, menu);

                return true;
            }

            @Override
            public boolean onOptionsItemSelected (MenuItem item){
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    System.out.print("你是我的第一个程序");
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }

        }

