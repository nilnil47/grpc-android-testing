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
    private StreamObserver<Service.RequestEvent> requestStream;


    void startStreaming() {
        Log.d(TAG, "startStreaming");
//        blockingStub = MapleServiceGrpc.newBlockingStub(mChannel);
        requestStream = asyncStub.eventsStream(new StreamObserver<Service.ResponseEvent>() {
            @Override
            public void onNext(Service.ResponseEvent value) {
                Log.d(TAG, "onNext: ");
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        host = "192.168.1.29";
        port = 50051;
        mChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
//        client = new
        asyncStub = MapleServiceGrpc.newStub(mChannel);
        startStreaming();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestStream.onNext(
                                Service.RequestEvent.newBuilder().
                                        setDropItem(Service.DropItem.newBuilder().
                                                setId(111).
                                                setX(1).
                                                setY(3).build()
                                        ).build()
                        );
                    }
                }).start();
            }
        });






//        StartChatMessageStreaming.newBuilder.;
    }
}