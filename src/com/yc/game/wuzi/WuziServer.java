package com.yc.game.wuzi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.yc.game.wuzi.core.WuziGameImpl;
import com.yc.game.wuzi.swing.WuziWin;

/**
 * 五子棋服务器端
 * @author 李玲芝
 *
 */
public class WuziServer {

	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket=new ServerSocket(8888);
		System.out.println("服务器启动成功，监听8888端口");
		Socket socket=serverSocket.accept();
		
		new Thread() {
			public void run() {
				System.out.println("IP:"+socket.getInetAddress()+"已连接");
				MutiThread mutiClient=new MutiThread(socket);
				mutiClient.setMyTurn(true); //设置当前轮到我为true
				new Thread(mutiClient,"服务器端").start();
				System.out.println(Thread.currentThread().getName());
				new WuziWin(new WuziGameImpl(),mutiClient).start();
			}
		}.start();
		
		
	}
	
}
