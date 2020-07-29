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
	private boolean mySend=false;
	private boolean myGet=true;
	private boolean isNew=false;  //对方的坐标是否更新了
	private boolean isFirst=false; //是否是先手
	private boolean isMyTurn=false; //是否轮到我下子
	
	public MutiThread(Socket socket) {
		this.socket=socket;
	}


	/**
	 * 获取对手坐标
	 * @throws IOException
	 */
	public void reciveCoordinate() throws IOException {
		
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
	public void sendCoordinate() throws IOException {
		
		ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
		System.out.println("发送自己的坐标："+x+"|"+y);
		oos.writeUTF(x+"|"+y);
		oos.flush();
		
		
	}

	@Override
	public void run() {
		new Thread("客户端接收：") {
			public void run() {
				while (true) {
					synchronized (this) {
						try {
							reciveCoordinate();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				}
				
			}
		}.start();
		
		new Thread("客户端发送：") {
			public void run() {
				while (true) {
					//this是客户端发送 这个线程
					synchronized (this) {
						if(mySend==true) {
							try {
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
	
	
	public Socket getSocket() {
		return socket;
	}


	public void setSocket(Socket socket) {
		this.socket = socket;
	}


	public ObjectInputStream getOis() {
		return ois;
	}


	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}


	public FileOutputStream getOos() {
		return oos;
	}


	public void setOos(FileOutputStream oos) {
		this.oos = oos;
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


	public boolean isMySend() {
		return mySend;
	}


	public void setMySend(boolean mySend) {
		this.mySend = mySend;
	}


	public boolean isMyGet() {
		return myGet;
	}


	public void setMyGet(boolean myGet) {
		this.myGet = myGet;
	}


	public boolean isNew() {
		return isNew;
	}


	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}


	public boolean isFirst() {
		return isFirst;
	}


	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}


	public boolean isMyTurn() {
		return isMyTurn;
	}


	public void setMyTurn(boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}
	
	
	
}
