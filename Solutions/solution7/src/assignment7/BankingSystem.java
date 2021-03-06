package assignment7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankingSystem {

	protected List<Account> accountList;

	/**
	 * Initializes the BankingSystem:
	 * accountList is empty and totalMoneyInBank() should return 0.
	 */
	public BankingSystem() {
		setAccountList(new ArrayList<Account>());
	}

	/**
	 * Transfers Money from one account to another.
	 *
	 * @param from Account to transfer money from.
	 * @param to Account to transfer money to.
	 * @param amount Amount to transfer.
	 * @return True if Money was transferred successfully.
	 *         False if there was not enough balance in from.
	 */
	public  boolean transferMoney(Account from, Account to, int amount) {
		Account first, second;

		// Introduce lock ordering:
		// The account with the smaller id will be locked first.
		if (to.getId() > from.getId()) {
			first = from;
			second = to;
		} else {
			first = to;
			second = from;
		}

		/* To be able to take multiple locks in `sumAccounts`, we use explicit
		 * locks, more specifically Java's ReentrantLock. Each account has
		 * such a lock which can be accessed by Account.getLock().
		 *
		 * The lock is reentrant, so there is no problem if `from` == `to`.
		 *
		 * Alternative using intrinsic locks, if `sumAccounts` was solved
		 * differently (e.g. by using a global lock).
		 *
		 *		synchronized (first) {
		 *			synchronized (second) {
		 *				if (from.getBalance() < amount) {
		 *					return false;
		 *				} else {
		 *					from.withdraw(amount);
		 *					to.deposit(amount);
		 *				}
		 *			}
		 *		}
		 *
		 */
		first.getLock().lock();
		second.getLock().lock();
		try {
			// it is important that `from` is locked before this check,
			// otherwise we have a check-then-act issue (TOCTTOU).
			if (from.getBalance() < amount) {
				return false;
			} else {
				from.setBalance(from.getBalance() - amount);
				to.setBalance(to.getBalance() + amount);
			}
		} finally {
			// try-finally to ensure that the locks are always released,
			// even in case of uncaught exceptions.
			first.getLock().unlock();
			second.getLock().unlock();
		}

		return true;
	}

	/**
	 * Returns the sum of a given list of accounts.
	 */
	public int sumAccounts(List<Account> accounts) {
		// sort the accounts to ensure lock ordering
		List<Account> sortedAccounts = new ArrayList<Account>(accounts);
		Collections.sort(sortedAccounts);

		int sum = 0;
		for (Account a : sortedAccounts) {
			// lock each account, but do not release it until we summed up
			// all accounts in the list
			a.getLock().lock();

			sum += a.getBalance();
		}

		// release all accounts
		for (Account a : sortedAccounts) {
			a.getLock().unlock();
		}

		return sum;
	}

	/**
	 * Calculates the total amount of money in the bank at any point in time.
	 * @return The total amount of money.
	 */
	public int totalMoneyInBank() {
		return sumAccounts(getAccountList());
	}

	/**
	 * Adds a new account to the bank.
	 * The account needs to have a positive balance to be added to the system.
	 *
	 * @param a New account
	 * @return True if account was added successfully.
	 *         False if account could not be added to the system
	 *         (ie., account did not have enough balance).
	 */
	public boolean addAccount(Account a) {
		if (a.getBalance() >= 0) {
			getAccountList().add(a);
			return true;
		}
		else {
			return false;
		}

	}

	protected List<Account> getAccountList() {
		return accountList;
	}

	protected void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}

}
