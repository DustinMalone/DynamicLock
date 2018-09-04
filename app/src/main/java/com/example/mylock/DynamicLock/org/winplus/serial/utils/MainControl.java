package com.example.mylock.DynamicLock.org.winplus.serial.utils;


import android.util.Log;

import com.example.mylock.DynamicLock.org.winplus.serial.SerialPortCommunication;

public class MainControl implements SerialPortCommunication.ReceivedCallback {

	private static MainControl mainControl;
	private static SerialPortCommunication serialPortCommunication;
	private ReceiveHandle receiveHandle;

	public synchronized static MainControl getControl() {
		if (mainControl == null) {
			mainControl = new MainControl();
			serialPortCommunication = SerialPortCommunication.getInstance();
			serialPortCommunication.setReceivedCallback(mainControl);
		}
		return mainControl;
	}

	public void setReceiveHandle(ReceiveHandle receiveHandle) {
		this.receiveHandle = receiveHandle;
	}

	/**
	 * ��ʼ��
	 */
	public void ID() {
		byte[] b = { 0x02, 0x01, (byte) 0xC1, 0x10 };
		Log.v("link", "---����ID" + ConverterUtil.bytesToHex(b));
		serialPortCommunication.send(b);
	}

	/**
	 * ����֪ͨ����ģ��������������ò������� Z1 = 1 �ֽ� ��ʾ��������ţ�00~79���� ��Ӧ���� Y1 = 1 ���ֽ� ��ʾ�Ƿ������ɹ���0
	 * ��ʾ�ɹ������� 0 ʱ��ʾʧ�ܣ������ֵ����ʾʧ�ܵ�ԭ��1 ��ʾ��Ч�ĵ������ �ţ�2 ��ʾ��ǰ����һ������������У�3
	 * ��ʾ��һ̨�������ת�����δȡ��
	 */
	public void RUN(int position) {
		byte[] data = { 0x02, 0x05, (byte) position };
		int crc = CRCutil.calcCrc16(data);
		byte[] b = { 0x02, 0x05, (byte) position, (byte) CRCutil.LB(crc),
				(byte) CRCutil.UB(crc) };
		Log.v("link", "---����RUN" + ConverterUtil.bytesToHex(b));
		serialPortCommunication.send(b);
	}
	/**
	 * ��ѯ״ִ̬�У�POLL���ܻ�Ӧ�����������Ϣ�����û����Ϣ��������ģ��ط� ACK����Ӧ POLL
	 */
	public void POLL() {
		byte[] b = { 0x02, 0x03, 0x40, (byte) 0xD1 };
		Log.v("link", "---����POLL" + ConverterUtil.bytesToHex(b));
		serialPortCommunication.send(b);
	}
	/**
	 * ����֪ͨ����ģ�������Ѿ���ȡ�����ϴ����еĽ��
	 */
	public void ACK() {
		byte[] b = { 0x02, 0x06, (byte)0x80, (byte) 0xD2 };
		Log.v("link", "---����ACK" + ConverterUtil.bytesToHex(b));
		serialPortCommunication.send(b);
	}

	/**
	 * ���ݻش��ص�
	 * 
	 * @param buf
	 */
	@Override
	public void onReceived(byte[] buf) {
		System.out.println("---���մ������ݣ�" + ConverterUtil.bytesToHex(buf));
		if (buf != null && receiveHandle != null) {
			receiveHandle.process(buf);
		}
	}

	/**
	 * ���ݻص��ӿ�
	 */
	public interface ReceiveHandle {
		public void process(byte[] data);
	}
}
