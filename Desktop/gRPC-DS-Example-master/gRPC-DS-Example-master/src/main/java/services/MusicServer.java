/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import com.google.protobuf.Empty;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.example.grpc.MusicSystemGrpc;
import com.example.grpc.Playlist;
import com.example.grpc.Song;
import java.util.logging.Logger;
import services.JmDNSRegistrationHelper;

/**
 *
 * @author emmma
 */
public class MusicServer {
     /* The port on which the server should run */
   private static final Logger logger = Logger.getLogger(MusicServer.class.getName());
   private Server server;
   private int port = 50021;
   
    private void start() throws Exception {
         server = ServerBuilder.forPort(port)
                .addService(new MusicImpl())
                .build()
                .start();
         
        JmDNSRegistrationHelper helper = new JmDNSRegistrationHelper("Emma's", "_light._udp.local.", "", port);
        logger.info("Server started, listening on " + port);
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                MusicServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
        
    
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon
     * threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws Exception {
        final MusicServer server = new MusicServer();
        server.start();
        server.blockUntilShutdown();
        
    }

    private class MusicImpl extends MusicSystemGrpc.MusicSystemImplBase {

        private List<Song> songs;

        public MusicImpl() {
            songs = new ArrayList<Song>();
            Song song1 = Song.newBuilder().setTitle("Song1").setArtist("Artist1").setGenre("Awesome1").build();
            Song song2 = Song.newBuilder().setTitle("Song2").setArtist("Artist2").setGenre("Awesome2").build();
            Song song3 = Song.newBuilder().setTitle("Song3").setArtist("Artist3").setGenre("Awesome3").build();
            songs.add(song1);
            songs.add(song2);
            songs.add(song3);

        }

        /* issues with the add
        public void getPlaylist(Empty request,
                StreamObserver<Playlist> responseObserver) {
            Playlist all = Playlist.newBuilder().addGetPlaylist(songs).build();
            responseObserver.onNext(all);
            responseObserver.onCompleted();
            
        }*/

        public void streamPlays(Empty request,
            StreamObserver<Song> responseObserver) {
                        Timer t = new Timer();
                t.schedule(new StreamTask(responseObserver), 0, 2000);
        }
    
        class StreamTask extends TimerTask {

            StreamObserver<Song> o;
            int index = 0;

            public StreamTask(StreamObserver<Song> j) {
                o = j;
            }

            @Override
            public void run() {
                if (index == songs.size() ) {
                    o.onCompleted();
                    this.cancel();
                } else {
                    o.onNext(songs.get(index++));
                }
            }
        }
    }
}
