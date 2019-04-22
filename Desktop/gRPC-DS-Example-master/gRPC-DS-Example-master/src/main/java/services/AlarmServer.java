/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.example.grpc.AlarmGrpc;
import com.example.grpc.AlarmStatus;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author emmma
 */
public class AlarmServer {
    
     private static final Logger logger = Logger.getLogger(LightServer.class.getName());
   private Server server;
   private int port = 50021;
   
    private void start() throws Exception {
         server = ServerBuilder.forPort(port)
                .addService(new AlarmImpl())
                .build()
                .start();
         
        JmDNSRegistrationHelper helper = new JmDNSRegistrationHelper("Emma's", "_alarm._udp.local.", "", port);
        logger.info("Server started, listening on " + port);
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                AlarmServer.this.stop();
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
        final AlarmServer server = new AlarmServer();
        server.start();
        server.blockUntilShutdown();
    }

    public class AlarmImpl extends AlarmGrpc.AlarmImplBase {
        
        //set on automatically
        private String alarmStat;
        
        public AlarmImpl() {
            String name = "Emma's";
            String serviceType = "_alarm._udp.local.";
        }
    

        //@Override
        public void alarmSwitch(com.google.protobuf.Empty request, io.grpc.stub.StreamObserver<AlarmStatus> responseObserver ){
            System.out.println(request);
            
            alarmStat = "On";
            
            if(alarmStat == "On"){
                responseObserver.onNext(AlarmStatus.newBuilder().setAlarmOnOff("Off").build());
            }
            
            else{
                responseObserver.onNext(AlarmStatus.newBuilder().setAlarmOnOff("On").build());
            }
            
            responseObserver.onCompleted();
        }
        
        public void panicAlarm(com.google.protobuf.Empty request, io.grpc.stub.StreamObserver<AlarmStatus> responseObserver ){
            System.out.println(request);
            
            alarmStat = "Gardai are on their way...";
            
            responseObserver.onNext(AlarmStatus.newBuilder().setAlarmOnOff(alarmStat).build());
            
            responseObserver.onCompleted();
        }
    }
    
}
