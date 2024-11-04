package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    //initialize dao
    private AccountDAO accountDAO;

    //blank constructor
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    //constructor to call if dao variable is already initialized
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    //Service method to call dao method getAllAccounts
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    //Service Method to call dao method addAccount
    public Account addAccount(Account acc) {
        return accountDAO.addAccount(acc);
    }

    //Service method to call dao method getSingleAccount
    public Account getSingleAccount(Account acc){
        return accountDAO.getSingleAccount(acc);
    }

    //Service method to call dao method getSingleAccountByID
    public Account getSingleAccountByID(int i){
        return accountDAO.getSingleAccountByID(i);
    }
    
}
