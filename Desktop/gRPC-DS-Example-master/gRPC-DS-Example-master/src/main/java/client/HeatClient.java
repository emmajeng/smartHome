/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientui.HeatClientGUI;
import com.example.grpc.HeatStatus;
import com.example.grpc.ProviderRequest;
import com.example.grpc.ProviderResponse;
import com.example.grpc.HeatGrpc;
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
public class HeatClient implements ServiceObserver  {
     private ManagedChannel channel;
    private HeatGrpc.HeatBlockingStub blockingStub;
    protected HeatClientGUI ui;
    protected ServiceDescription current;
    private final String serviceType;
    private final String name;
    
    private static final Logger logger = Logger.getLogger(GRPCHeatClient.class.getName());

    
    public HeatClient() {
        serviceType = "_heat._udp.local.";
        name = "Room";
        jmDNSServiceTracker clientManager = jmDNSServiceTracker.getInstance();
        clientManager.register(this);
        
        java.awt.EventQueue.invokeLater(new Runnable() {
             public void run() {
                 ui = new HeatClientGUI(HeatClient.this);
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
        blockingStub = HeatGrpc.newBlockingStub(channel);
    }
    
    public void heat() {
        try {

            new Thread() {
                public void run() {
                    Empty request = Empty.newBuilder().build();

                    Iterator<HeatStatus> response = blockingStub.heat(request);
                    while (response.hasNext()) {
                        System.out.println(response.next().toString());
                    }
                }
            }.start();

            Empty request = Empty.newBuilder().build();
            HeatStatus status = blockingStub.getStatus(request);
            System.out.println(status);

        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "RPC failed", e);
            return;
        }
    }
    
     public void getProvider(){
         com.example.grpc.ProviderRequest req = ProviderRequest.newBuilder()
         .setProviderName("Name")
         .setProviderAddress("Address")
         .setCostPerMonth("100 per month")
         .build();
    }
            
    public static void main(String[] args) throws Exception {
        HeatClient client = new HeatClient();
    }
}
