/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientui.AlarmClientGUI;
import com.example.grpc.AlarmStatus;
import com.example.grpc.AlarmGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emmma
 */
public class AlarmClient implements ServiceObserver {
    
    private ManagedChannel channel;
    private AlarmGrpc.AlarmBlockingStub blockingStub;
    protected AlarmClientGUI ui;
    protected ServiceDescription current;
    private final String serviceType;
    private final String name;
    
    private static final Logger logger = Logger.getLogger(GRPCAlarmClient.class.getName());

    
    public AlarmClient() {
        serviceType = "_alarm._udp.local.";
        name = "Room";
        jmDNSServiceTracker clientManager = jmDNSServiceTracker.getInstance();
        clientManager.register(this);
        
        java.awt.EventQueue.invokeLater(new Runnable() {
             public void run() {
                 ui = new AlarmClientGUI(AlarmClient.this);
                 ui.setVisible(true);
             }
         });

        serviceAdded(new ServiceDescription("localhost", 50021));
        
                
    }
    
    String getServiceType() {
        return serviceType;
    }

    void disable() {
        // no services exist for this client type
    }
    
    public List<String> serviceInterests() {
        List<String> interests = new ArrayList<String>();
        interests.add(serviceType);
        return interests;
    }
    
    public boolean interested(String type) {
        return serviceType.equals(type);
    }
    
    public void switchService(String name) {
        // TODO
        
    }

    public String getName() {
        return name;
    }


    public void serviceAdded(ServiceDescription service) {
        System.out.println("service added");
        current = service;
        channel = ManagedChannelBuilder.forAddress(service.getAddress(), service.getPort())
                .usePlaintext(true)
                .build();
        blockingStub = AlarmGrpc.newBlockingStub(channel);
    }
            
    
     public void alarmSwitch(){
        try {
            Empty req = Empty.newBuilder().build();
            com.example.grpc.AlarmStatus rsp = blockingStub.alarmSwitch(req);
            System.out.println(rsp);
            
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "RPC failed", e);
        }

    }
     
     public void panicAlarm(){
        try {
            Empty req = Empty.newBuilder().build();
            com.example.grpc.AlarmStatus rsp = blockingStub.panicAlarm(req);
            System.out.println(rsp);
            
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "RPC failed", e);
        }

    }
    
    public static void main(String[] args) throws Exception {
        AlarmClient client = new AlarmClient();
    }

    
}

        