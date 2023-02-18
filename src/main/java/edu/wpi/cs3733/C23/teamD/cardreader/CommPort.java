package edu.wpi.cs3733.C23.teamD.cardreader;

import com.fazecast.jSerialComm.*;
import java.nio.charset.StandardCharsets;

public class CommPort {
  private SerialPort portActive = SerialPort.getCommPort("");

  public CommPort(String deviceName) {
    SerialPort portList[] = SerialPort.getCommPorts();
    String messageWrite = "Who are you?";
    byte[] writeBuffer = messageWrite.getBytes();
    for (int i = 0; i < 2; i++) {
      for (SerialPort port : portList) {
        try {
          // port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1500, 10);
          port.setBaudRate(9600);
          port.openPort();

          Thread.sleep(1500);
          System.out.println(
              "Pinging "
                  + port.getSystemPortName()
                  + " with "
                  + port.writeBytes(writeBuffer, writeBuffer.length)
                  + " bytes");
          Thread.sleep(1500);
          byte[] readBuffer = new byte[port.bytesAvailable()];
          System.out.println(port.readBytes(readBuffer, readBuffer.length) + " Bytes read");
          String messageRead = new String(readBuffer, StandardCharsets.UTF_8);
          System.out.println(messageRead);
          System.out.println(deviceName);
          if (messageRead.contains(deviceName)) {
            portActive = port;
          } else {
            System.out.println("No device detected on " + port.getSystemPortName());
            port.closePort();
          }
        } catch (Exception e) {
          System.out.println("No device detected on " + port.getSystemPortName());
        }
      }
    }
  }

  public SerialPort getPort() {
    return portActive;
  }

  public void closeSerial() {
    portActive.closePort();
  }

  public String readSerial() {
    byte[] readBuffer = new byte[portActive.bytesAvailable()];
    System.out.println(portActive.readBytes(readBuffer, readBuffer.length) + " Bytes read");
    String messageRead = new String(readBuffer, StandardCharsets.UTF_8);
    return messageRead;
  }
}
