package com.yc.game.wuzi;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.yc.game.wuzi.core.WuziGameImpl;
import com.yc.game.wuzi.swing.WuziWin;

/**
 * 五子棋主程序
 * @author 廖彦
 */

public class WuziMain {
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket=new Socket("127.0.0.1",8888);
		new Thread() {
			public void run() {
				
				MutiThread mutiClient=new MutiThread(socket);
				mutiClient.setFirst(true);  //设置客户端为先手
				mutiClient.setMyTurn(true); //设置当前轮到我为true
				new Thread(mutiClient,"客户端1").start();
				System.out.println(Thread.currentThread().getName());
				new WuziWin(new WuziGameImpl(),mutiClient).start();
				
			}
		}.start();
		
	}

}
