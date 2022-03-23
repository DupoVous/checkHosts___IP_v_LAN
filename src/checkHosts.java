import java.net.*;
import java.io.*;
import java.util.*;

class AddressFinderLevel4 extends Thread{
    private String addmask;
    private Stack<String> stack;
    private int start, end;

    public AddressFinderLevel4(String addmask, Stack stack, int start, int end){
        this.addmask = addmask;
        this.stack = stack;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run(){
        try{
            int timeout=1000;
            for(int i = start; i <= end; i++){
                String host=addmask + "." + i;
                if (InetAddress.getByName(host).isReachable(timeout)){
                    stack.push(host);
                }
            }
        }catch(Exception ex){

        }
    }
}

class AddressFinderLevel3 extends Thread{
    private String addmask;
    private Stack<String> stack;
    private int start, end;
    private int packSize;

    public AddressFinderLevel3(String addmask, Stack stack, int packSize, int start, int end){
        this.addmask = addmask;
        this.stack = stack;
        this.start = start;
        this.end = end;
        this.packSize = packSize;
    }

    @Override
    public void run(){
        try{
            for(int i = start; i <= end; i++){
                int j = 1;
                String host =  addmask + "." + i;
                while(j<=255){
                    AddressFinderLevel4 addressFinderLevel4 = new AddressFinderLevel4(host, stack, j, j+packSize+5);
                    addressFinderLevel4.start();
                    j = j + packSize;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}



public class checkHosts {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting search!");
        Stack data = find();
        Thread.sleep(1000);
        System.out.println("Data found in 1000 miliseconds AAA");
        data.forEach(System.out::println);
        Thread.sleep(10000);
        System.out.println("Data found in 10000 miliseconds BBB");
        data.forEach(System.out::println);
        Thread.sleep(25000);
        System.out.println("Data found in 25000 miliseconds CCC");
        data.forEach(System.out::println);
    }

    public static Stack find(){
        Stack<String> stack = new Stack<String>();
        AddressFinderLevel3 finder = new AddressFinderLevel3("192.168", stack, 10, 0, 255);
        finder.start();
        return stack;
    }
}