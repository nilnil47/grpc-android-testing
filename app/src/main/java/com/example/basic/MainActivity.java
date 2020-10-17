package com.example.basic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import messaging.MapleServiceGrpc;
import messaging.Service;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "test";
    private String host;
    private int port;
    private MapleServiceGrpc.MapleServiceStub asyncStub;
    private MapleServiceGrpc.MapleServiceBlockingStub blockingStub;
    private ManagedChannel mChannel;


    void startStreaming() {
        Log.d(TAG, "startStreaming");
        blockingStub = MapleServiceGrpc.newBlockingStub(mChannel);
        Service.Empty res = blockingStub.sendChatMessage(Service.ChatMessage.newBuilder().setId(1).setMessage("aaa").build());
        Log.d(TAG, "startStreaming: " + res);
//        asyncStub.sendChatMessage(Service.ChatMessage.newBuilder().setId(1).setMessage("aaa").build(),
//                new StreamObserver<Service.Empty>() {
//
//                    @Override
//                    public void onNext(Service.Empty value) {
//                        Log.d(TAG, "onNext: ");
//
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        Log.d(TAG, "onError: " + t);
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, "onComplete: ");
//
//                    }
//                });
//        Service.Empty request = Service.Empty.newBuilder().build();

//        Service.Empty empty = blockingStub.sendChatMessage(Service.ChatMessage.newBuilder().setId(1).setMessage("aaa").build());
//        asyncStub.startChatMessageStreaming(request, new StreamObserver<Service.ChatMessage>() {
//            @Override
//            public void onNext(Service.ChatMessage value) {
//                Log.d(TAG, "onNext: " + value.getMessage());
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Log.d(TAG, "onError: " + t);
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.d(TAG, "onCompleted: ");
//            }
//        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        savedInstanceState.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        host = "192.168.1.29";
        port = 50051;
        mChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
//        client = new
//        asyncStub = MapleServiceGrpc.newStub(mChannel);



        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startStreaming();
                    }
                }).start();
            }
        });






//        StartChatMessageStreaming.newBuilder.;
    }
}