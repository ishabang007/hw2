// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;


public class TestExample {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private ExpenseTrackerController controller;

  @Before
  public void setup() {
    model = new ExpenseTrackerModel();
    view = new ExpenseTrackerView();
    controller = new ExpenseTrackerController(model, view);
  }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }
    


    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
        assertTrue(controller.addTransaction(50.00, "food"));
    
        // Post-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Check the contents of the list
        assertEquals(50.00, getTotalCost(), 0.01);
    }


    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add and remove a transaction
        Transaction addedTransaction = new Transaction(50.00, "Groceries");
        model.addTransaction(addedTransaction);
    
        // Pre-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);
    
        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());
    
        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }

    @Test
    public void testInvalidInputHandling() {
        assertEquals(0, model.getTransactions().size());
      
        assertFalse(controller.addTransaction(-10.00, "food")); 
        assertFalse(controller.addTransaction(20.00, "invalidCategory")); 
        assertFalse(controller.addTransaction(30.00, "")); 
        assertFalse(controller.addTransaction(40.00, null)); 
    
        assertEquals(0, model.getTransactions().size());
        assertEquals(0.00, getTotalCost(), 0.01);
    }

    @Test
    public void testFilterByAmount() {
        controller.addTransaction(50.00, "food");
        controller.addTransaction(75.00, "food");
        controller.addTransaction(50.00, "entertainment");
    
        List<Transaction> filteredTransactions = model.getTransactionsByAmount(50.00);
    
        assertEquals(2, filteredTransactions.size());
        assertEquals(50.00, filteredTransactions.get(0).getAmount(), 0.01);
        assertEquals(50.00, filteredTransactions.get(1).getAmount(), 0.01);
    }

    @Test
    public void testFilterByCategory() {
        controller.addTransaction(50.00, "food");
        controller.addTransaction(75.00, "food");
        controller.addTransaction(50.00, "entertainment");
    
        List<Transaction> filteredTransactions = model.getTransactionsByCategory("food");
    
        assertEquals(2, filteredTransactions.size());
        assertEquals("food", filteredTransactions.get(0).getCategory());
        assertEquals("food", filteredTransactions.get(1).getCategory());
    }
  
    @Test
    public void testAddValidTransaction() {
        assertEquals(0, model.getTransactions().size());
      
        assertTrue(controller.addTransaction(100.00, "travel"));
      
        assertEquals(1, model.getTransactions().size());
      
        assertEquals(100.00, getTotalCost(), 0.01);
    }
    
}
