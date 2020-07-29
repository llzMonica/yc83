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

public class WuziClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket=new Socket("127.0.0.1",8888);
		new Thread() {
			public void run() {
				
				//根据socket创建多线程类用于监听对方消息和发送消息给对方
				MutiThread mutiClient=new MutiThread(socket);
				new Thread(mutiClient,"客户端").start();
				mutiClient.setColor(1);  //设置客户端为黑棋，黑棋为先手
				
				//创建棋盘
				new WuziWin(new WuziGameImpl(),mutiClient,"客户端").start();
				
			}
		}.start();
		
	}

}
