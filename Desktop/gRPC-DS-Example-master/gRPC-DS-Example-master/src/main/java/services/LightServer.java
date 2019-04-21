/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.example.grpc.LightGrpc;
import com.example.grpc.SwitchStatus;
import com.example.grpc.BrightRequest;
import com.example.grpc.BrightResponse;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author emmma
 */

public class LightServer {
   
   private static final Logger logger = Logger.getLogger(LightServer.class.getName());
   private Server server;
   private int port = 50021;
   
    private void start() throws Exception {
         server = ServerBuilder.forPort(port)
                .addService(new LightImpl())
                .build()
                .start();
         
        JmDNSRegistrationHelper helper = new JmDNSRegistrationHelper("Emma's", "_light._udp.local.", "", port);
        logger.info("Server started, listening on " + port);
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                LightServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
         
    }
    
     private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
   
     
     private void blockUntilShutdown() throws InterruptedException {
        if(server != null){
            server.awaitTermination();
        }
    }
   
    public static void main(String [] args) throws Exception, IOException, InterruptedException {
        //calling the implementation
        final LightServer server = new LightServer();
        server.start();
        server.blockUntilShutdown();
    }

    public class LightImpl extends LightGrpc.LightImplBase {
        
        //set on automatically
        private String lightStat;
        
        public LightImpl() {
            String name = "Emma's";
            String serviceType = "_light._udp.local.";
        }
    
        @Override
        public void brightness(BrightRequest request, io.grpc.stub.StreamObserver<BrightResponse> responseObserver ){
            System.out.println(request);
            responseObserver.onNext(BrightResponse.newBuilder()
                .setPercetnageNow("The lights should be : " + request.getPercentage() + "%")
            .build());
        
            responseObserver.onCompleted();
        }
      
        
        //@Override
        public void lightSwitch(com.google.protobuf.Empty request, io.grpc.stub.StreamObserver<SwitchStatus> responseObserver ){
            System.out.println(request);
            
            lightStat = "On";
            
            if(lightStat == "On"){
                responseObserver.onNext(SwitchStatus.newBuilder().setLightsOnOff("Off").build());
            }
            
            else{
                responseObserver.onNext(SwitchStatus.newBuilder().setLightsOnOff("On").build());
            }
            
            responseObserver.onCompleted();
        }
    }
 }
