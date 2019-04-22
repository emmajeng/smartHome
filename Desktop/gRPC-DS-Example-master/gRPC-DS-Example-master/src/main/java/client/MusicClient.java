/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientui.MusicClientGUI;
import com.example.grpc.Playlist;
import com.example.grpc.Song;
import com.example.grpc.MusicSystemGrpc;
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
public class MusicClient implements ServiceObserver {
    
    private ManagedChannel channel;
    private MusicSystemGrpc.MusicSystemBlockingStub blockingStub;
    protected MusicClientGUI ui;
    protected ServiceDescription current;
    private final String serviceType;
    private final String name;
    
    private static final Logger logger = Logger.getLogger(GRPCLightClient.class.getName());

    
    public MusicClient() {
        serviceType = "_music._udp.local.";
        name = "Room";
        jmDNSServiceTracker clientManager = jmDNSServiceTracker.getInstance();
        clientManager.register(this);
        
        java.awt.EventQueue.invokeLater(new Runnable() {
             public void run() {
                 ui = new MusicClientGUI(MusicClient.this);
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
        blockingStub = MusicSystemGrpc.newBlockingStub(channel);
    }
            
    
    
     public void streamPlays(){
       System.out.println("Streaming...");

    }
    
    public static void main(String[] args) throws Exception {
        MusicClient client = new MusicClient();
    }

    
}

        