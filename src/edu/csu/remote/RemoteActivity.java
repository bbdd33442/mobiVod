package edu.csu.remote;

import java.net.InetAddress;
import java.net.SocketException;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

import edu.csu.mobiVod.R;


public class RemoteActivity extends Activity {
    //ǰ�����������һ�飬Ŀǰ���Կ��ƿ������LED��1��LED��2�ͼ̵�������������ң�����õ��������
    private Button btnLED1On = null;
    private Button btnLED1Off = null;
    private Button btnLED2On = null;
    private Button btnLED2Off = null;
    private Button btnLED3On = null;
    private Button btnLED3Off = null;
    private Button btnLED4On = null;
    private Button btnLED4Off = null;
    private EditText etIp = null;

    //������·������ʱ�����ң�ؿ������IP��ַ
    private String ipstr = "empty";

    private static final int UDP_SERVER_PORT = 8080;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote);

        etIp = (EditText) findViewById(R.id.EditText_ip);
        btnLED1On = (Button) findViewById(R.id.ButtonLED1On);
        btnLED1Off = (Button) findViewById(R.id.ButtonLED1Off);
        btnLED2On = (Button) findViewById(R.id.ButtonLED2On);
        btnLED2Off = (Button) findViewById(R.id.ButtonLED2Off);
        btnLED3On = (Button) findViewById(R.id.ButtonLED3On);
        btnLED3Off = (Button) findViewById(R.id.ButtonLED3Off);
        btnLED4On = (Button) findViewById(R.id.ButtonLED4On);
        btnLED4Off = (Button) findViewById(R.id.ButtonLED4Off);

        //�����س���  
        etIp.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                ipstr = etIp.getText().toString();
                return false;
            }
        });

        btnLED1On.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String udpMsg = "LED_OPEN1";
                sendMessage(udpMsg);
            }
        });

        btnLED1Off.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String udpMsg = "LED_CLOSE1";
                sendMessage(udpMsg);
            }
        });

        btnLED2On.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String udpMsg = "LED_OPEN2";
                sendMessage(udpMsg);
            }
        });

        btnLED2Off.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String udpMsg = "LED_CLOSE2";
                sendMessage(udpMsg);
            }
        });

        btnLED3On.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String udpMsg = "JDQ_OPEN";
                sendMessage(udpMsg);
            }
        });

        btnLED3Off.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String udpMsg = "JDQ_CLOSE";
                sendMessage(udpMsg);
            }
        });

        btnLED4On.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String udpMsg = "ON";
                sendMessage(udpMsg);
            }
        });

        btnLED4Off.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String udpMsg = "OFF";
                sendMessage(udpMsg);
            }
        });
    }

    public void sendMessage(String udpMsg) {
        DatagramSocket ds = null;
        ipstr = etIp.getText().toString();
        try {
            ds = new DatagramSocket();
            if (ipstr.equals("empty") || ipstr.equals("")) {
                Toast.makeText(RemoteActivity.this, "������ң����ip", Toast.LENGTH_SHORT).show();
            } else {
                InetAddress serverAddr = InetAddress.getByName(ipstr);
                ds.send(getDP(udpMsg, serverAddr));
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }

    public DatagramPacket getDP(String udpMsg, InetAddress serverAddr) {
        DatagramPacket dp;
        dp = new DatagramPacket(udpMsg.getBytes(), udpMsg.length(), serverAddr, UDP_SERVER_PORT);
        return dp;
    }
}