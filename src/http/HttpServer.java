package http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

public class HttpServer {

	public static void main(String[] args) throws Throwable {
		ServerSocket ss = new ServerSocket(1995);
		while (true) {
			Socket s = ss.accept();
			System.err.println("Client accepted");
			new Thread(new SocketProcessor(s)).start();
		}
	}

	private static class SocketProcessor implements Runnable {
		private Socket s;

		private SocketProcessor(Socket s) throws Throwable {
			this.s = s;
		}

		public void run() {
			OutputStream outputStream;
			InputStream inputStream;

			try {
				inputStream = s.getInputStream();
				outputStream = s.getOutputStream();
				PrintStream printStream = new PrintStream(outputStream);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				byte[] aByte = new byte[1024 * 1024];
				int r = inputStream.read(aByte);
				String logs = new String(aByte, 0, r);
				System.out.println(logs);
				String message = logs;
				String postMess;
				if (message.contains("1.html")) {
					printStream.print(HtmlPage.PAGE1 + "\n");
					if (message.contains("POST")) {
						postMess = parsingMessage(message);
						printStream.print(postMess);
					}
				} else {
					if (message.contains("POST")) {
						postMess = parsingMessage(message);
						printStream.print(postMess);
					} else {
						printStream.print("404 NOT FOUND");
					}
				}
				bufferedReader.close();
				inputStream.close();
				outputStream.close();
				printStream.close();
				// hostThreadSocket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public String parsingMessage(String message) {
			try {
				String newMessage;
				int start = message.indexOf("Length: ");
				int l;
				String length = message.substring(start);
				start = length.indexOf(" ");
				String sourse = "";
				for (int i = start + 1;; i++) {
					if (isNumber(length.charAt(i))) {
						sourse += length.charAt(i);
					} else {
						l = Integer.parseInt(sourse);
						break;
					}
				}
				Integer point = message.length() - l;
				newMessage = message.substring(point, message.length());
				int number = 0;
				long checkSum = 0;
				String base64Code = "";
				// сохранить в файл, чекнуть сумму вернуть ответ
				int switcher = 0;
				sourse = "";
				for (int i = 0; i < newMessage.length(); i++) {
					if (newMessage.charAt(i) == '=') {
						switcher++;
						sourse = "";
					} else {
						if (newMessage.charAt(i) == '&') {
							switch (switcher) {
							case 1:
								number = Integer.parseInt(sourse);
								sourse = "";
								break;
							case 2:
								checkSum = Long.parseLong(sourse);
								sourse = "";
								break;
							default:
								break;
							}
						} else {
							if (newMessage.charAt(i)!='\n') {
							sourse += newMessage.charAt(i);
							}
						}
					}
				}
				base64Code = sourse;
				//SGVsbG8gV29ybGQ
				byte [] barr = Base64.getDecoder().decode(base64Code);
				//System.out.println(new String(barr));
				int x=calcCRC(barr);
				//System.out.println("THIS IS X="+x+"\n");
				if (x == checkSum)
					return HtmlPage.ERROR200;
				else
					return "Repeate please";
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return HtmlPage.ERROR404;
			}
		}
		
		public int calcCRC(byte[] data) {
			  int sum = 0;
			  for (int i = 0; i < data.length; ++i) {
				  sum += (data[i] ^ i);
			  }
			  return sum;
		  }

		private boolean isNumber(char x) {
			switch (x) {
			case '1':
				return true;
			case '2':
				return true;
			case '3':
				return true;
			case '4':
				return true;
			case '5':
				return true;
			case '6':
				return true;
			case '7':
				return true;
			case '8':
				return true;
			case '9':
				return true;
			case '0':
				return true;
			default:
				return false;
			}
		}
	}
}
