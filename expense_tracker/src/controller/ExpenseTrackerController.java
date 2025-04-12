package controller;

import view.ExpenseTrackerView;
import javax.swing.JOptionPane;

import java.util.List;

import model.TransactionFilter;
import model.ExpenseTrackerModel;
import model.Transaction;
import model.AmountFilter;
import model.CategoryFilter;

public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Set up view event handlers
    view.getAddTransactionBtn().addActionListener(e -> {
      double amount = view.getAmountField();
      String category = view.getCategoryField();

      if (addTransaction(amount, category)) {
        view.setAmountField(null);
        view.setCategoryField(null);
      } else {
        JOptionPane.showMessageDialog(null, "Invalid input");
      }
    });

    view.getFilterBtn().addActionListener(e -> {
      double amount = view.getAmountField();
      String category = view.getCategoryField();
    
      if (!category.isEmpty()) {
        if (!InputValidation.validateCategory(category)) {
          JOptionPane.showMessageDialog(null, "Invalid Category");
          return;
        }
        applyFilter(new CategoryFilter(category));
    
      } else if (amount > 0) {
        if (!InputValidation.validateAmount(amount)) {
          JOptionPane.showMessageDialog(null, "Invalid Amount");
          return;
        }
        applyFilter(new AmountFilter(amount));
    
      } else {
        refresh(); 
      }
    });
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }
  
  // Other controller methods

  public void applyFilter(TransactionFilter filter) {
        List<Transaction> filteredTransactions = filter.filter(model.getTransactions());
        view.refreshTable(filteredTransactions);
  }
}
