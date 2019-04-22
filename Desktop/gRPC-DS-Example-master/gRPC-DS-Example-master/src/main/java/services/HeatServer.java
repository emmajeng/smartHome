/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.example.grpc.HeatGrpc;
import com.example.grpc.HeatStatus;
import com.example.grpc.ProviderRequest;
import com.example.grpc.ProviderResponse;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 *
 * @author emmma
 */
public class HeatServer {
    
   private static final Logger logger = Logger.getLogger(HeatServer.class.getName());
   private Server server;
   private int port = 50021;
    
   private void start() throws Exception {
         server = ServerBuilder.forPort(port)
                .addService(new HeatServer.HeatImpl())
                .build()
                .start();
         
        JmDNSRegistrationHelper helper = new JmDNSRegistrationHelper("Emma's", "_heat._udp.local.", "", port);
        logger.info("Server started, listening on " + port);
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HeatServer.this.stop();
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
        final HeatServer server = new HeatServer();
        server.start();
        server.blockUntilShutdown();
    }

    public class HeatImpl extends HeatGrpc.HeatImplBase {
        
        private int percent = 0;

        public HeatImpl() {
            String name = "Emma's";
            String serviceType = "_heat._udp.local.";
        }
    
       
        @Override
        public void heat(com.google.protobuf.Empty request, io.grpc.stub.StreamObserver<HeatStatus> responseObserver ){
            Timer t = new Timer();
            t.schedule(new RemindTask(responseObserver), 0, 2000);
        }
      
        
        //@Override
        public void getStatus(com.google.protobuf.Empty request, io.grpc.stub.StreamObserver<HeatStatus> responseObserver ){
            responseObserver.onNext(HeatStatus.newBuilder().setPercentageHeated(percent).build());
            responseObserver.onCompleted();
           
        }
        
        //COME BACK
        public void getProvider(ProviderRequest request, io.grpc.stub.StreamObserver<ProviderResponse> responseObserver){
            System.out.println(request);
            responseObserver.onNext(ProviderResponse.newBuilder()
                .setAcknowledgement("The provider is : " + request.getProviderName())
            .build());
            
            System.out.println("Acknowledged");
        }
        
        class RemindTask extends TimerTask {

            StreamObserver<HeatStatus> o;

            public RemindTask(StreamObserver<HeatStatus> j) {
                o = j;
            }

            @Override
            public void run() {
                if (percent < 22) {
                    percent += 2;
                    HeatStatus status = HeatStatus.newBuilder().setPercentageHeated(percent).build();
                    o.onNext(status);
                } else {
                    o.onCompleted();
                    this.cancel();
                }
            }
        }

    }

    
}
