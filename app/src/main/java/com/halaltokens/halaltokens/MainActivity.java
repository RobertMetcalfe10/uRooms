package com.halaltokens.halaltokens;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//import com.gargoylesoftware.htmlunit.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String str = "http://www.ucd.ie";
//        Object[] objects = new Object[1];
        DoSomeTask doSomeTask = new DoSomeTask();
        doSomeTask.execute();
    }

    class DoSomeTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

            Map<String, String> map = new HashMap<>();
            map.put("p_butn", "1");
            map.put("p_username", "15551647");
            map.put("p_password", "Destiny10");
            map.put("p_forward", "W_WEB_WELCOME_PAGE¬");
            map.put("p_lmet", "SISWEB");

            Document doc = null;
            try {
                Jsoup.connect("https://sisweb.ucd.ie/usis/W_HU_LOGIN.P_PROC_LOGINBUT")
                        .method(Connection.Method.POST)
                        .data(map)
                        .post();

//                doc = Jsoup.connect("https://sisweb.ucd.ie/usis/W_HU_REPORTING.P_LAUNCH_REPORT?p_report=RO100&p_parameters=17006CBF9C514FC6D1CD523658573B4A9DB637582BF464C41D2209E8EBFD4BC75BE2D0DF82FA1BFBEA16C64D0779B0E4060334B27914A43FF124664E686C18FE16B989841C29F10D2F988AB17FCBC89E").get();
                doc = Jsoup.connect("https://sisweb.ucd.ie/usis/W_HU_REPORTING.P_LAUNCH_REPORT?p_report=RB150&p_parameters=F3A3382D88851EADE7E4D8D868DFE4E707193DF31EE0DB300CDAF295EF6CCF6ACE64E4A248B055F5E9E67622EC8FD6F777088A93ED9D152F095CF1D3118D595D0AED3FDC91E75010CF0BEF7AEF064EE69C0C2C20A502A0A2A77C695A473D292A2FCFFF413DB976E71F68A87B7FFF423377AB0019669105A6060823401365D6A563D2D9EF994F35AF0897B6D81052458F6485068D5800DFC98B2347EBC5AF2A518A7DD91F6A36120D20C87EAD7CF3300E0BD3601D2361CA97A8E9554581F8736F7D53747E5A2E915243276B7F89E0D88FA1C4483D1AE88C9BB980DE16A022BB79C9BD8B7DD7E8EA88AEF55A30EE621773A17CAA0C95F808806753E55638D7B10847369BF13DA9CF32FB5B8F3EADED3C56132ED1F77029610364D55966D5A9691ED2F23156E2DD5373BAA7DCDA10E765FC39B8B67A48DCEF37F001D1B63FECCC62B00D1D19E1A3AE30029831BE4062EF73").get();
                for(int i=1; i<=10;i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0."+i).children());
                        System.out.println(roomInfo.getOrganization());
                    } catch (NullPointerException e) {
                        break;
                    }
                }
                doc = Jsoup.connect("https://sisweb.ucd.ie/usis/W_HU_REPORTING.P_LAUNCH_REPORT?p_report=RB150&p_parameters=F3A3382D88851EADE7E4D8D868DFE4E707193DF31EE0DB300CDAF295EF6CCF6ACE64E4A248B055F5E9E67622EC8FD6F777088A93ED9D152F095CF1D3118D595D0AED3FDC91E75010CF0BEF7AEF064EE69C0C2C20A502A0A2A77C695A473D292A2FCFFF413DB976E71F68A87B7FFF423377AB0019669105A6060823401365D6A563D2D9EF994F35AF0897B6D81052458F6485068D5800DFC98B2347EBC5AF2A518A7DD91F6A36120D20C87EAD7CF3300E0EA18AA971F10DCEA21D9DF89F4C17038454496E62BA9E566F0F4C77F3170FADBF858E5AC54A4D8EAC22592BEB05DEADD3BB65E187935CD3E7F88984CDB7E701DB8F1BB20C687031A69D92FC4A8B958BD03D5ABE138D4B8D5003896D1AFE71201BE46B34FA7FED626A69C2E6E856F2AF3E3B8851CD41F90C3E36870BD96A8AEF6EAB8A8049A644DC13ECA374407DAB9A60DA035C0AA309097D942A5B4CA93AFC").get();
                for(int i=1; i<=10;i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0."+i).children());
                        System.out.println(roomInfo.getOrganization());
                    } catch (NullPointerException e) {
                        break;
                    }
                }
                doc = Jsoup.connect("https://sisweb.ucd.ie/usis/W_HU_REPORTING.P_LAUNCH_REPORT?p_report=RB150&p_parameters=F3A3382D88851EADE7E4D8D868DFE4E707193DF31EE0DB300CDAF295EF6CCF6ACE64E4A248B055F5E9E67622EC8FD6F777088A93ED9D152F095CF1D3118D595D0AED3FDC91E75010CF0BEF7AEF064EE69C0C2C20A502A0A2A77C695A473D292A2FCFFF413DB976E71F68A87B7FFF423377AB0019669105A6060823401365D6A563D2D9EF994F35AF0897B6D81052458F6485068D5800DFC98B2347EBC5AF2A518A7DD91F6A36120D20C87EAD7CF3300E0EA18AA971F10DCEA21D9DF89F4C17038454496E62BA9E566F0F4C77F3170FADBF858E5AC54A4D8EAC22592BEB05DEADD3BB65E187935CD3E7F88984CDB7E701DB8F1BB20C687031A69D92FC4A8B958BD03D5ABE138D4B8D5003896D1AFE71201BE46B34FA7FED626A69C2E6E856F2AF3E3B8851CD41F90C3E36870BD96A8AEF6EAB8A8049A644DC13ECA374407DAB9A60DA035C0AA309097D942A5B4CA93AFC").get();
                for(int i=1; i<=10;i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0."+i).children());
                        System.out.println(roomInfo.getOrganization());
                    } catch (NullPointerException e) {
                        break;
                    }
                }
                doc = Jsoup.connect("https://sisweb.ucd.ie/usis/W_HU_REPORTING.P_LAUNCH_REPORT?p_report=RB150&p_parameters=F3A3382D88851EADE7E4D8D868DFE4E707193DF31EE0DB300CDAF295EF6CCF6ACE64E4A248B055F5E9E67622EC8FD6F777088A93ED9D152F095CF1D3118D595D0AED3FDC91E75010CF0BEF7AEF064EE69C0C2C20A502A0A2A77C695A473D292A2FCFFF413DB976E71F68A87B7FFF423377AB0019669105A6060823401365D6A563D2D9EF994F35AF0897B6D81052458F6485068D5800DFC98B2347EBC5AF2A518A7DD91F6A36120D20C87EAD7CF3300E0EA18AA971F10DCEA21D9DF89F4C17038454496E62BA9E566F0F4C77F3170FADBF858E5AC54A4D8EAC22592BEB05DEADD3BB65E187935CD3E7F88984CDB7E701DB8F1BB20C687031A69D92FC4A8B958BD03D5ABE138D4B8D5003896D1AFE71201BE46B34FA7FED626A69C2E6E856F2AF3E3B8851CD41F90C3E36870BD96A8AEF6EAB8A8049A644DC13ECA374407DAB9A60DA035C0AA309097D942A5B4CA93AFC").get();
                for(int i=1; i<=10;i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0."+i).children());
                        System.out.println(roomInfo.getOrganization());
                    } catch (NullPointerException e) {
                        break;
                    }
                }
                doc = Jsoup.connect("https://sisweb.ucd.ie/usis/W_HU_REPORTING.P_LAUNCH_REPORT?p_report=RB150&p_parameters=F3A3382D88851EADE7E4D8D868DFE4E707193DF31EE0DB300CDAF295EF6CCF6ACE64E4A248B055F5E9E67622EC8FD6F777088A93ED9D152F095CF1D3118D595D0AED3FDC91E75010CF0BEF7AEF064EE69C0C2C20A502A0A2A77C695A473D292A2FCFFF413DB976E71F68A87B7FFF423377AB0019669105A6060823401365D6A563D2D9EF994F35AF0897B6D81052458F6485068D5800DFC98B2347EBC5AF2A518A7DD91F6A36120D20C87EAD7CF3300E0EA18AA971F10DCEA21D9DF89F4C17038454496E62BA9E566F0F4C77F3170FADBF858E5AC54A4D8EAC22592BEB05DEADD3BB65E187935CD3E7F88984CDB7E701DB8F1BB20C687031A69D92FC4A8B958BD03D5ABE138D4B8D5003896D1AFE71201BE46B34FA7FED626A69C2E6E856F2AF3E3B8851CD41F90C3E36870BD96A8AEF6EAB8A8049A644DC13ECA374407DAB9A60DA035C0AA309097D942A5B4CA93AFC").get();
                for(int i=1; i<=10;i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0."+i).children());
                        System.out.println(roomInfo.getOrganization());
                    } catch (NullPointerException e) {
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
