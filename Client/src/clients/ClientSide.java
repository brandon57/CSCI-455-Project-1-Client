package clients;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.regex.*;

public class ClientSide {

	private static Socket client = null;
	private static Scanner input = new Scanner(System.in);
	private static DataOutputStream toServer = null;
	private static BufferedReader fromServer = null;
	
	public static void main(String[] args) throws Exception {
		String textFromServer = "";
		//Socket client = null;
		String response = "";

		boolean valid_input = false;
		
		
		//System.out.println("What do you want to do?\n1. Connect a website\n2. Exit");
		
		//InetAddress
		
		//Asks the user if they want to connect or not
		while(valid_input == false)
		{
			System.out.println("What do you want to do?\n1. Connect to a website\n2. Exit");
			response = input.nextLine().toLowerCase();
			switch(response)
			{
				case "connect":
				case "1":
					connect();
					valid_input = true;
					break;
				case "exit":
				case "2":
					close();
					break;
				default:
					System.out.println("Your input is not valid\nTry again");
					break;
			}
			
//			if(response.equals("1") || response.equalsIgnoreCase("connect"))
//			{
//				break;
//			}
//			else if(response.equals("2") || response.equalsIgnoreCase("Exit"))
//			{
//				input.close();
//				close();
//			}
//			else
//			{
//				System.out.println("Your input is not valid\nWant to try again?\n1. Yes\n2. No");
//				response = input.nextLine();
//				while(true)
//				{
//					if(response.equals("1") || response.equalsIgnoreCase("yes"))
//					{
//						System.out.println("What do you want to do?\n1. Connect to a website\n2. Exit");
//						break;
//					}
//					else if(response.equals("2") || response.equalsIgnoreCase("no"))
//					{
//						input.close();
//						close();
//					}
//					else
//					{
//						System.out.println("Not a valid option\nYou want to try again?\n1. Yes\n2. No");
//						response = input.nextLine();
//					}
//				}
//			}
		}
		
		//Opens socket
//		while(true)
//		{
//			try
//			{
//				client = new Socket("localhost", 6789);
//				
//				break;
//				//client.close();
//			}
//			catch(Exception e)
//			{
//				System.out.println("Couldn't connect\nWant to try again?\n1. Yes\n2. No");
//				while(true)
//				{
//					response = input.nextLine();
//					if(response.equals("1") || response.equalsIgnoreCase("yes"))
//					{
//						break;
//					}
//					else if(response.equals("2") || response.equalsIgnoreCase("no"))
//					{
//						input.close();
//						close();
//					}
//					else
//					{
//						System.out.println("Your input is not valid\nWant to try again?\n1. Yes\n2. No");
//						response = input.nextLine();
//						while(true)
//						{
//							if(response.equals("1") || response.equalsIgnoreCase("yes"))
//							{
//								System.out.println("Want to try and connect again?\n1. Yes\n2. No");
//								break;
//							}
//							else if(response.equals("2") || response.equalsIgnoreCase("no"))
//							{
//								input.close();
//								close();
//							}
//							else
//							{
//								System.out.println("Not a valid option\nYou want to try again?\n1. Yes\n2. No");
//								response = input.nextLine();
//							}
//						}
//					}
//				}
//			}
//		}
		fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
		toServer = new DataOutputStream(client.getOutputStream());
		
		//The part where the User actually interacts with the Website
		System.out.println("");
		while(true)
		{		
			//This is how the User gets info from the Server
			try
			{
				while((textFromServer = fromServer.readLine()) != null)
				{
					if(textFromServer.equals("^^&^&^&"))
					{
						close();
					}
					else if(textFromServer.equalsIgnoreCase(" "))
					{
						break;
					}
					System.out.println(textFromServer);
				}
				toServer.writeBytes(input.nextLine() + "\n");
			}
			catch(Exception e)
			{
				System.out.println("You and the server have disconnected");
				close();
			}
		}
	}
	
	//This connects you to a website
	private static void connect() throws Exception
	{
		String IP = null;
		String port = null;
		while(true)
		{
			System.out.println("Where do you want to connect to? Type the ip address\nYour input should look like \"0-255.0-255.0-255.0-255\"\nYou can exit if you want");
			IP = input.nextLine();
			if(validIP(IP) == true)
			{
				break;
			}
			else if(IP.equalsIgnoreCase("exit"))
			{
				close();
			}
			else
			{
				System.out.println("That IP address is not valid. Try again");
			}
		}
		while(true)
		{
			System.out.println("What is the port number of the server? Your input should be between 0-65535\nYou can exit if you want");
			port = input.nextLine();
			if(validPort(port) == true)
			{
				break;
			}
			else if(port.equalsIgnoreCase("exit"))
			{
				close();
			}
			else
			{
				System.out.println("That port number is not valid");
			}
		}
		client = new Socket(IP, Integer.valueOf(port));
	}
	
	private static boolean validPort(String port)
	{
		Integer temp = 0;
		try
		{
			temp = Integer.valueOf(port);
			if(temp >= 0 && temp <= 65535)
			{
				return true;
			}
		}
		catch(Exception e)
		{
			return false;
		}
		return false;
	}
	
	private static boolean validIP(String IP)
	{
		String pattern = "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])";
		String IP_pattern = pattern + "." + pattern + "." + pattern + "." + pattern;
		Pattern temp = Pattern.compile(IP_pattern);
		Matcher check = temp.matcher(IP);
		return check.matches();
	}
	
	//Stops the program
	private static void close() throws Exception
	{
		if(client != null)
		{
			client.close();
		}
		if(input != null)
		{
			input.close();
		}
		System.out.println("Closing...");
		System.exit(0);
	}

}
