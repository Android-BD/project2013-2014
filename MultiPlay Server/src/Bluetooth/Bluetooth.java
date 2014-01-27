package Bluetooth;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import CodeKey.N;
import Connect.BluetoothConfigurationClass;
import Connect.ConnectWifi;
import Wifi.Serverwifi;
/*
 * @author Piotr Baczkiewicz
 */
public class Bluetooth implements Runnable {
	
	private LocalDevice local = null;
	private StreamConnectionNotifier notifier = null;
	private StreamConnection connection = null;
	/*
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			waitForConnection();
		} catch (BluetoothStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * exception BluetoothStateException
	 */
	private void waitForConnection() throws BluetoothStateException {

		byte data;
		ConnectWifi connect = new ConnectWifi();
		System.out.println("Bluetooth Thread");

		local = LocalDevice.getLocalDevice();
		local.setDiscoverable(DiscoveryAgent.GIAC);
		System.out.println(LocalDevice.getLocalDevice().getBluetoothAddress());
		UUID uuid = new UUID(BluetoothConfigurationClass.Profiles.SSP.replace(
				"-", "") + LocalDevice.getLocalDevice().getBluetoothAddress(),
				false);
		while (true) {
			data = 0;
			System.out.println(uuid.toString());

			String url = "btspp://localhost:" + uuid.toString()
					+ ";name=RemoteBluetooth";
			try {
				notifier = (StreamConnectionNotifier) Connector.open(url);
				System.out.println("waiting for connection...");
				connection = notifier.acceptAndOpen();
				DataInputStream dis = new DataInputStream(
						connection.openDataInputStream());
				DataOutputStream dos = new DataOutputStream(
						connection.openDataOutputStream());
				data = dis.readByte();
				System.out.println("RUN");
				System.out.println("Read...");
				if (data == N.Signal.NEED_AUTHORIZATION) {
					System.out.println("Send back authorization code...");
					if (System.getProperty("os.name").startsWith("Win")) {
						dos.writeByte(N.Signal.encodeSignal(
								N.Signal.NEED_AUTHORIZATION, N.System.WINDOWS));
					} else if (System.getProperty("os.name").contains("Linux")) {
						dos.writeByte(N.Signal.encodeSignal(
								N.Signal.NEED_AUTHORIZATION, N.System.LINUX));

					} else if (System.getProperty("os.name").contains("BSD")) {
						dos.writeByte(N.Signal.encodeSignal(
								N.Signal.NEED_AUTHORIZATION, N.System.BSD));
					}
					Toolkit zestaw = Toolkit.getDefaultToolkit();
					Dimension wymiary = zestaw.getScreenSize();
					int wysokosc = wymiary.height;
					int szerokosc = wymiary.width;
					dos.writeInt(N.Helper.encodeSignal(N.Signal.DIMENSION,
							N.DeviceDataCounter.DOUBLE, wysokosc, szerokosc));
					dis.close();
					dos.close();
					connection.close();
					notifier.close();
					System.out.println("Over");
				} else if (data == N.Signal.NEED_CONNECTION) {
					int port = connect.getport();
					dos.writeInt(port);
					dis.close();
					dos.close();
					connection.close();
					notifier.close();
					System.out.println("Over");
					Serverbluetooth bt = new Serverbluetooth(uuid, url);
					bt.run();
				} else if (data == N.Signal.NEED_APPLICATIONS) {

				} else if (data == N.Signal.RUN_APPLICATION) {
					// Windows
					if (System.getProperty("os.name").startsWith("Win")) {
						// String name= dis.readUTF();
						// --------miejsce na odczyt z xml danych

						// ProcessBuilder pb = new ProcessBuilder("cmd",
						// "/c",file);

					} else if (System.getProperty("os.name").contains("Linux")) {
						// String name= dis.readUTF();
						// miejsce na odczyt z xml danych
						// String[] cmd = new String[] {"/bin/bash", "-c",
						// fileName};
						// Runtime.getRuntime().exec(cmd);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	
	}

}
