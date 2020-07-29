package com.yc.game.wuzi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import sun.rmi.runtime.NewThreadAction;

/**
 * 线程类  用于发送棋子坐标和接收棋子坐标
 * @author 李玲芝
 *
 */
public class MutiThread implements Runnable{

	private Socket socket;
	private ObjectInputStream ois;
	private FileOutputStream oos;
	private int x=1;
	private int y=1;
	private boolean mySend=false; //我当前是否可以发送
	private boolean isNew=false;  //对方的坐标是否更新了
	//这个color是自己棋子的颜色不是对方的！！！
	private int color=2;  //黑子为1  白子为2  默认为白紫，谁是先手，就设置谁为黑子
	
	public MutiThread(Socket socket) {
		this.socket=socket;
	}


	/**
	 * 获取对手坐标
	 * @throws IOException
	 */
	public  void reciveCoordinate() throws IOException {
		
		ois=new ObjectInputStream(socket.getInputStream());
		
		String coordinateStr=ois.readUTF();
		System.out.println("获取对手坐标："+coordinateStr);
		String[] coordinate=coordinateStr.split("[|]");

		x=Integer.parseInt(coordinate[0]);
		y=Integer.parseInt(coordinate[1]); 
		
		setNew(true);
		System.out.println(isNew);
		
	}
	
	/**
	 * 发送自己的坐标
	 * @throws IOException
	 */
	public  void  sendCoordinate() throws IOException {
		
		ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
		System.out.println("发送自己的坐标："+x+"|"+y);
		oos.writeUTF(x+"|"+y);
		oos.flush();
		
		
	}

	@Override
	public void run() {
		new Thread("接收：") {
			public void run() {
				while (true) {
					synchronized (this) {
						try {
							//获取对方传送过来的坐标
							reciveCoordinate();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
						
				}
			}
		}.start();
		
		new Thread("发送：") {
			public void run() {
				while (true) {
					synchronized (this) {
						if(mySend==true) {
							try {
								//发送自己的坐标给对方
								sendCoordinate();
								
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				
			}
		}.start();
		
	}
	
	
	


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public  boolean isMySend() {
		return mySend;
	}


	public  void setMySend(boolean mySend) {
		this.mySend = mySend;
	}


	public boolean isNew() {
		return isNew;
	}


	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}


	public int getColor() {
		return color;
	}


	public void setColor(int color) {
		this.color = color;
	}
	
}
