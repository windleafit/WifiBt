package com.yangyong.windleaf.wifi_bt.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import me.yangyong.kity.android.toast.Toasty;
import me.yangyong.kity.java.callback.Back;
import me.yangyong.kity.java.thread.Thready;

/**
 * Created by WindLeaf on 2016/9/11.
 */
public class BlueToothTest {

    private String uuidString = "00001101-0000-1000-8000-00805F9B34FB";
    private UUID uuid = UUID.fromString(uuidString);

    private final BluetoothAdapter bluetoothAdapter;
    private Context context;


    public BlueToothTest setAutoOpenBlueTooth(boolean autoOpenBlueTooth) {
        this.autoOpenBlueTooth = autoOpenBlueTooth;
        return this;
    }

    private boolean autoOpenBlueTooth;


    public BlueToothTest(Context context) {
        this.context = context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                //找到设备
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                        Log.v("TAG", "find device:" + device.getName() + device.getAddress());
                    }
                }
                //搜索完成
                else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

                }
            }
        };
    }


    public void open() {
        if (bluetoothAdapter == null) {
            Toasty.holder.show("bluetoothAdapter == null");
            return;
        }
        if (!bluetoothAdapter.isEnabled()) {
            //如果蓝牙设备不可用的话,创建一个intent对象,该对象用于启动一个Activity,提示用户启动蓝牙适配器
            if (autoOpenBlueTooth) {
                bluetoothAdapter.enable();
            } else {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                context.startActivity(intent);
            }
        }

    }

    public void createService(final Back back) throws IOException {
        final String name = "server";
        final BluetoothServerSocket serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(name, uuid);
        Thready thready = new Thready(new Thready.Callback() {
            @Override
            public void once() {
                try {
                    BluetoothSocket socket = serverSocket.accept();
                    back.value("Service socket.isConnected() = " + socket.isConnected());
                    if (socket.isConnected()) {
                        OutputStream os = socket.getOutputStream();
                        os.write("hello baby".getBytes());
                    }
                    Log.d("BlueTooth", "Service socket.isConnected() = " + socket.isConnected());
                } catch (IOException e) {
                    back.value("Service error = " + e.toString());
                    e.printStackTrace();
                }
            }
        });
        thready.start();
    }

    public void createClient(BluetoothDevice bluetoothDevice, final Back back) throws IOException {
        final BluetoothSocket socket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
        Thready thready = new Thready(new Thready.Callback() {
            @Override
            public void once() {
                try {
                    socket.connect();
                    back.value("Client socket.isConnected() = " + socket.isConnected());
                    if (socket.isConnected()) {
                        InputStream is = socket.getInputStream();
                        byte[] buf = new byte[1024];
                        is.read(buf);
                        back.value("Client read = " + new String(buf, "UTF-8"));
                    }
                } catch (IOException e) {
                    back.value("Client error = " + e.toString());
                    e.printStackTrace();
                }
                Log.d("BlueTooth", "Client socket.isConnected() = " + socket.isConnected());

            }
        });
        thready.start();
    }

    public Set<BluetoothDevice> getDevice() {
        return bluetoothAdapter.getBondedDevices();
    }

    public void connect(BluetoothDevice bluetoothDevice) {
//        bluetoothDevice.createRfcommSocketToServiceRecord(bluetoothDevice.);
    }



}
