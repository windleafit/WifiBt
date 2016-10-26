package com.yangyong.windleaf.wifi_bt.netty.learn;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.reactivex.netty.channel.Connection;
import io.reactivex.netty.protocol.tcp.client.TcpClient;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by WindLeaf on 2016/9/27.
 */
public class RxNettySocket {

    Connection<String, String> mConnection;

    public  void run() {
        connect("192.168.0.103",8234).subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Observable.timer(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if(mConnection != null){
                            mConnection.closeNow();
                            run();
                        }
                    }
                });
            }

            @Override
            public void onNext(Boolean aBoolean) {
                send("hello").subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        System.out.println("send success");
                        EventBus.getDefault().post("send success");
                    }
                });
                receive().subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Observable.timer(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                            @Override public void call(Long aLong) {
                                if(mConnection != null){
                                    mConnection.closeNow();
                                    run();
                                }
                                System.out.println("reconnect");
                                EventBus.getDefault().post("reconnect");
                            }
                        });
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("receive:" + s);
                        EventBus.getDefault().post("receive:" + s);
                    }
                });
            }
        });


    }

    public Observable<Boolean> connect(final String url, final int port) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {

                TcpClient.newClient(url, port)
                        .<String, String>
                                addChannelHandlerLast("decoder",
                                                      new Func0<ChannelHandler>() {
                                                          @Override
                                                          public ChannelHandler call() {
                                                              return new StringDecoder();
                                                          }
                                                      })
                        .<String, String>
                                addChannelHandlerLast("encoder",
                                                      new Func0<ChannelHandler>() {
                                                          @Override
                                                          public ChannelHandler call() {
                                                              return new StringEncoder();
                                                          }
                                                      })
                        .createConnectionRequest()
                        .subscribe(new Observer<Connection<String, String>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(Connection<String, String> connection) {
                                mConnection = connection;
                                subscriber.onNext(true);
                            }
                        });
            }
        });
    }

    public Observable<String> receive() {
        if (mConnection != null) {
            return mConnection.getInput();
        }
        return null;
    }


    public Observable<Void> send(String s) {
        return mConnection.writeString(Observable.just(s));
    }


}
