package com.example.ftpwlan;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

class Server{
    ServerSocket server;
    boolean accept=true;
    public void close() {
        accept=false;
    }
    public void start() {
        accept=true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Server() throws IOException {
        try {
            Log.d("Server","Server listening");


            server=new ServerSocket(9999,0, InetAddress.getByName("192.168.43.1"));

            while(accept) {
                try(Socket client=server.accept()){
                    handleclient(client);
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleclient(Socket client) throws IOException {
        // TODO Auto-generated method stub
        OutputStream os;
        InputStream is;
        Log.d("Server","Client Connected");
        os=client.getOutputStream();
        is=client.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                is));
        StringBuilder sb=new StringBuilder();
        String line;
        while(!(line=in.readLine()).isEmpty()) {
            sb.append(line+"\r\n");
        }
        String request=sb.toString();

        String[] lines=request.split("\r\n");
        String[] l=lines[0].split(" ");
        String path=l[1];
        Log.d("Server",path);
        if(!path.equalsIgnoreCase("/favicon.ico")) {
            //Log.d("Server",request);
            sendresponse(os,path);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendresponse(OutputStream os, String path) throws IOException {

        os.write("HTTP/1.1 200 OK\r\n".getBytes());
        os.write("ContentType: text/html \r\n".getBytes());
        // TODO Auto-generated method stub

        String html="<!DOCTYPE html>\n"
                + "<html lang=\"en\" dir=\"ltr\">\n"
                + "  <head>\n"
                + "    <meta charset=\"utf-8\">\n"
                + "    <title>Rahul's &amp; Company</title>\n"
                + "  </head>\n"
                + "  <body>";

        String html2="  </body>\n"
                + "</html>";

        String path1=path;
        Log.d("Serverpath", path1);
        if(path1.equalsIgnoreCase("/")) {
            path1= Environment.getExternalStorageDirectory().getAbsolutePath();
            Log.d("Server",path1);
        }
        if(path1.contains("%20")) {
            path1=path1.replace("%20"," ");
        }
        File f=new File(path1);
        Log.d("File", f.isDirectory() + "");
        if(f.isFile()) {
            os.write(("Content-disposition: attachment;filename="+f.getName()+";").getBytes());

            os.write("\r\n".getBytes());
            os.write("\r\n".getBytes());

            Files.copy(Paths.get(path1), os);
        }else if(f.isDirectory()) {
            File[] files=f.listFiles();

            for(int i=0;i<files.length;i++) {
                html=html+writehtml(files[i]);

            }
            html=html+html2;
            //Log.d("Server",html);
            os.write("\r\n".getBytes());
            os.write("\r\n".getBytes());
            os.write(html.getBytes());

        }
        os.write("\r\n".getBytes());




        os.flush();
        os.close();

    }
    private String writehtml(File file) {
        // TODO Auto-generated method stub
        String foldericon="<svg version=\"1.1\" id=\"Capa_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\"\n"
                + "	 viewBox=\"0 0 468.293 468.293\" style=\"enable-background:new 0 0 468.293 468.293;\" xml:space=\"preserve\" width=\"20\" height=\"20\">\n"
                + "<path style=\"fill:#F6C358;\" d=\"M29.525,50.447h111.996c7.335,0,14.11,3.918,17.77,10.274l18.433,25.181\n"
                + "	c3.66,6.356,10.436,10.274,17.77,10.274h272.798v287.495c0,15.099-12.241,27.34-27.34,27.34H27.34\n"
                + "	C12.241,411.011,0,398.77,0,383.671V128.068c0-21.133,3.265-42.14,9.68-62.276l0,0C12.03,56.755,20.188,50.447,29.525,50.447z\"/>\n"
                + "<rect x=\"42.615\" y=\"91.473\" style=\"fill:#EBF0F3;\" width=\"359.961\" height=\"152.058\"/>\n"
                + "<path style=\"fill:#FCD462;\" d=\"M447.788,64.117H334.927c-8.026,0-15.315,4.683-18.65,11.983l-19.313,42.267\n"
                + "	c-3.336,7.3-10.624,11.983-18.65,11.983H0v260.155c0,15.099,12.241,27.34,27.34,27.34h413.613c15.099,0,27.34-12.241,27.34-27.34\n"
                + "	V84.622C468.293,73.298,459.112,64.117,447.788,64.117z\"/><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g></svg>\n"
                + "";
        String fileicon="<svg id=\"Layer_1\" enable-background=\"new 0 0 510 510\" height=\"20\" viewBox=\"0 0 510 510\" width=\"20\" xmlns=\"http://www.w3.org/2000/svg\"><g id=\"XMLID_2477_\"><path id=\"XMLID_2540_\" d=\"m60 0v465h60c6.254-8.887 106.246-150.981 112.5-159.868l7.5-152.566-7.5-152.566c-4.865 0-167.635 0-172.5 0z\" fill=\"#f4d844\"/><path id=\"XMLID_2520_\" d=\"m405 0c-4.865 0-167.635 0-172.5 0v305.132c9.583-13.618 162.917-231.514 172.5-245.132 0-7.259 0-52.693 0-60z\" fill=\"#ecbd2c\"/><path id=\"XMLID_2597_\" d=\"m120 60v450h165l15-225-15-225c-4.654 0-160.348 0-165 0z\" fill=\"#f9f9f9\"/><path id=\"XMLID_2595_\" d=\"m285 60v450h66.041l21.626-80.667 77.333-18.12s0-331.711 0-351.213c-4.654 0-160.348 0-165 0z\" fill=\"#e2dff4\"/><path id=\"XMLID_2506_\" d=\"m351.041 510c12.034-12.013 86.899-86.749 98.959-98.787-26.815 0-83.16 0-98.959 0z\" fill=\"#bebcdd\"/><g id=\"XMLID_2509_\"><g><path id=\"XMLID_730_\" d=\"m285 150c-28.278 0-88.527 0-105 0 0-10.492 0-19.508 0-30h105l15 15z\" fill=\"#4e6ba6\"/></g></g><g id=\"XMLID_2508_\"><g><path id=\"XMLID_185_\" d=\"m285 210c-28.278 0-88.527 0-105 0 0-10.492 0-19.508 0-30h105l15 15z\" fill=\"#a7cafc\"/></g></g><g id=\"XMLID_2507_\"><g><path id=\"XMLID_182_\" d=\"m285 270c-28.278 0-88.527 0-105 0 0-10.492 0-19.508 0-30h105l15 15z\" fill=\"#a7cafc\"/></g></g><g id=\"XMLID_2495_\"><g><path id=\"XMLID_180_\" d=\"m285 330c-28.278 0-88.527 0-105 0 0-10.492 0-19.508 0-30h105l15 15z\" fill=\"#a7cafc\"/></g></g><g id=\"XMLID_2609_\"><g><path id=\"XMLID_764_\" d=\"m390 150c-28.278 0-88.527 0-105 0 0-10.492 0-19.508 0-30h105z\" fill=\"#28487a\"/></g></g><g id=\"XMLID_2604_\"><g><path id=\"XMLID_369_\" d=\"m390 210c-28.278 0-88.527 0-105 0 0-10.492 0-19.508 0-30h105z\" fill=\"#8493fb\"/></g></g><g id=\"XMLID_2535_\"><g><path id=\"XMLID_366_\" d=\"m390 270c-28.278 0-88.527 0-105 0 0-10.492 0-19.508 0-30h105z\" fill=\"#8493fb\"/></g></g><g id=\"XMLID_2318_\"><g><path id=\"XMLID_365_\" d=\"m390 330c-28.278 0-88.527 0-105 0 0-10.492 0-19.508 0-30h105z\" fill=\"#8493fb\"/></g></g><g id=\"XMLID_2346_\"><g><path id=\"XMLID_692_\" d=\"m285 390c-28.278 0-88.527 0-105 0 0-10.492 0-19.508 0-30h105z\" fill=\"#dd4e4e\"/></g></g></g></svg>";
        String html="&emsp;<a href=\" "+file.getAbsolutePath()+"\">"+file.getName()+"</a><br><br>\n";

        if(file.isDirectory()) {
            return foldericon+html;
        }
        else {
            return fileicon+html;
        }
    }
}