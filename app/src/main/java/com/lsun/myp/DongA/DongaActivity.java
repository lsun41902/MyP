package com.lsun.myp.DongA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lsun.myp.DongA.DongAItemMember;
import com.lsun.myp.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DongaActivity extends AppCompatActivity {
    RecyclerView recyclerViewDongA;
    ArrayList<DongAItemMember> dongAItemMembers=new ArrayList<>();
    DongAAdapter adapter;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donga);

        recyclerViewDongA=findViewById(R.id.recyclerview_donga);
        adapter=new DongAAdapter(dongAItemMembers,this);
        recyclerViewDongA.setAdapter(adapter);
        readRss();
        //스와이프 갱신하기
        refreshLayout=findViewById(R.id.swiper);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(DongaActivity.this, "새로고침", Toast.LENGTH_SHORT).show();
                dongAItemMembers.clear();
                adapter.notifyDataSetChanged();
                readRss();
            }
        });
    }
    void readRss(){
        try {
            URL url=new URL("https://rss.donga.com/national.xml");//https:// 만 허용하는
            //스트림연결하여 데이터 읽어오기 : 퍼미싱하기
            //network작업은 반드시 별도의 thread만 할수있음
            //별도의 thread객체생성
            RssFeedTask task=new RssFeedTask();
            task.execute(url);//자동으로 doInBackground 자동으로 발동 Thread start();와 같은 역활

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }//readRss Method
    class RssFeedTask extends AsyncTask<URL,Void,String> {
        //Thread의 run()메소드 와 같은 역활
        @Override
        protected String doInBackground(URL... urls) {//background에서 돌고있어라
            //URL...urls == 배열입니다
            //전달받은 URL 객체
            URL url=urls[0];
            //해임달(URL)에게 무지개 로드(Stream) 열어달라고
            try {
                InputStream is=url.openStream();
                //읽어온 xml를 파싱(분석)해주는 객체 생성
                XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
                XmlPullParser xpp=factory.newPullParser(); //XmPullParser만들어짐 next()가 가능해짐
                //무지개로드에 있는것을 읽어와라
                xpp.setInput(is,"utf-8");//utf-8 모든문자 가능한 인코딩 방식
                int eventType=xpp.getEventType();
                DongAItemMember item=null;
                String tagname=null;

                while (eventType!=XmlPullParser.END_DOCUMENT){
                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tagname=xpp.getName();
                            if(tagname.equals("item")){
                                item=new DongAItemMember();
                            }else if(tagname.equals("title")){
                                xpp.next();
                                if(item!=null){
                                    item.setTitle(xpp.getText());
                                }
                            }else if(tagname.equals("link")){
                                xpp.next();
                                if(item!=null){
                                    item.setLink(xpp.getText());
                                }
                            }else if(tagname.equals("description")){
                                xpp.next();
                                if(item!=null){
                                    item.setDesc(xpp.getText());
                                }
                            }else if(tagname.equals("image")){
                                xpp.next();
                                if(item!=null){
                                    item.setImgUrl(xpp.getText());
                                }
                            }else if(tagname.equals("pubDate")){
                                xpp.next();
                                if(item!=null){
                                    item.setDate(xpp.getText());
                                }
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            tagname=xpp.getName();
                            if(tagname.equals("item")){
                                //읽어온 기사 한덩어리를 대량의 데이터에 추가
                                dongAItemMembers.add(item);
                                item=null;
                                //리사이클러의 아답터에게 데이터가 변경되었다고 통지하기
                                //테스트로 toast로 보여주기

                                publishProgress();//onProgressupdate() 를 자동으로 호출
                            }
                            break;
                    }//switch
                    eventType=xpp.next();
                }//while
                //파싱작업이 완료되었습니다.
                //토스트 보이기 단, 별도 thread는 화면에 간섭할수 없다. UI작업 불가
                //그래서 runonUiThread()를 사용했었음
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return "파싱종료";
        }//doInbackground()
        //doInbackground()작업도중에
        //publishProgress()라는 메소드를 호출하면 자동으로 실행되는 메소드
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            adapter.notifyItemInserted(dongAItemMembers.size());//새로 생긴것만 새로고침하는것
            //이곳에서도 UI변경작업이 가능함

        }

        //doInBackground메소드가 종료된 후
        //Ui작업을 위해 자동실행되는 메소드
        //runonuiThread()와 비슷한 역활
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            refreshLayout.setRefreshing(false);

            //이 메소드 안에서는 ui변경이 가능함
            //자동으로 실행됨으로 따로 부르지 않아도됨

            //리사이클러에서 보여주는 데이터를 가진 아답터에게 데이터가 변경되었다고 공지
            //adapter.notifyDataSetChanged();
            Toast.makeText(DongaActivity.this, s+":"+dongAItemMembers.size(), Toast.LENGTH_SHORT).show();

        }
    }//RssFeedTask
}
