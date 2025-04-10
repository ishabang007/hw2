public class CategoryFilter implements TransactionFilter {

    private String category;

    public CategoryFilter(String category) {
        this.category = category;
    }

    public List<Transaction> filter(List<Transaction> transactions) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getCategory().toLowerCase().equals(category.toLowerCase())) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
}
