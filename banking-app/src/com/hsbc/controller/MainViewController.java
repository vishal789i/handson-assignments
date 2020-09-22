package com.hsbc.controller;

import java.util.Scanner;

import com.hsbc.exception.AccountNotFoundException;
import com.hsbc.exception.InsufficientBalanceException;
import com.hsbc.model.beans.Account;
import com.hsbc.model.service.AccountService;
import com.hsbc.model.utility.Type;
import com.hsbc.model.utility.UserFactory;

public class MainViewController {
	public static void main(String[] args) {
		int choice=0;
		Scanner scanner = new Scanner(System.in);
		AccountService service=(AccountService) UserFactory.getInstance(Type.SERVICE);

		do {
			System.out.println("*****************************************************************************************");
			System.out.println("1: Store Account 2: Fetch All Account 3: Find Account by id 4: Transfer Amount 5: Exit");
			System.out.println("*****************************************************************************************");
			choice = scanner.nextInt();
			
			switch(choice) {
			case 1:
				Account account = new Account();
				System.out.println("Enter Name");
				String name=scanner.next();
				System.out.println("Enter Balance");
				double balance=scanner.nextDouble();  
				System.out.println("Enter Contact Number");
				long phoneNumber=scanner.nextLong();
				
				account.setCustomerName(name);
				account.setBalance(balance);
				account.setContactNumber(phoneNumber);
				
				Account createdAccount = service.createAccount(account);
				System.out.println("Account created with account number : "+createdAccount.getAccountNumber());

				break;
			case 2:
				Account[] accountAll =service.getAccount();
				for(Account acc: accountAll) {
					if(acc !=null)
						System.out.println(acc);
				}
				break;
			case 3:
				System.out.println("Enter Account Id");
				int accountId = scanner.nextInt();
				try {
					Account accountFind = service.getAccountById(accountId);
					System.out.println(accountFind);
				}catch(AccountNotFoundException e) {
					System.err.println(e.getMessage());
				}

				break;
			case 4:
				try {
					System.out.println("Enter the Source Account Number : ");
					int sourceAccount=scanner.nextInt();
					System.out.println("Enter the Destination Account Number : ");
					int DestinationAccount=scanner.nextInt();
					System.out.println("Enter the amount to transfer : ");
					double amount=scanner.nextDouble();
					service.transfer(sourceAccount, DestinationAccount,amount);
					}catch(AccountNotFoundException e) {
						System.err.println(e.getMessage());
					}catch(InsufficientBalanceException e) {
						System.err.println(e.getMessage());
					}
				break;
			}
			
		}while(choice!=5);
		scanner.close();
	}
}
