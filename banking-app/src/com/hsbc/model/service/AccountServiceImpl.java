package com.hsbc.model.service;

import com.hsbc.exception.AccountNotFoundException;
import com.hsbc.exception.InsufficientBalanceException;
import com.hsbc.model.beans.Account;
import com.hsbc.model.dao.AccountDao;
import com.hsbc.model.utility.Type;
import com.hsbc.model.utility.UserFactory;

public class AccountServiceImpl implements AccountService {
	
	private AccountDao dao;
	public AccountServiceImpl() {
		dao = (AccountDao)UserFactory.getInstance(Type.DAO);		
	}
	
	@Override
	public Account createAccount(Account account) {
		// TODO Auto-generated method stub
		
		Account newAccount = dao.store(account);
		return newAccount;
	}
	
	@Override
	public Account[] getAccount() {
	
		return dao.fetchAccounts();
	}

	@Override
	public Account getAccountById(int accountNumber) throws AccountNotFoundException {
		// TODO Auto-generated method stub
		Account account = dao.fetchAccountById(accountNumber);
		if(account == null)
			throw new AccountNotFoundException("Not found any account with account No. : "+accountNumber);
		return account;
	}

	@Override
	public void transfer(int from, int to, double amount) throws AccountNotFoundException , InsufficientBalanceException {
		// TODO Auto-generated method stub
		Account accountFrom = dao.fetchAccountById(from);
		Account accountTo = dao.fetchAccountById(to);
		
		double balance = accountFrom.getBalance();	
		double balance2 = accountTo.getBalance();	


		if(accountFrom == null)	
			throw new AccountNotFoundException("Not found any account with account No. : " +from);
		if(accountTo == null)	
			throw new AccountNotFoundException("Not found any account with account No. : " +to);
		
		if(amount > balance)
			throw new InsufficientBalanceException("Insufficient Balance in Account No. " + from);
		
		accountFrom.setBalance(balance-amount);
		
		accountTo.setBalance(balance2+amount);
		dao.updateAccount(from, accountFrom);
		dao.updateAccount(to, accountTo);

	}

}
