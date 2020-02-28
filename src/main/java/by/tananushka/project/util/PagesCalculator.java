package by.tananushka.project.util;

public class PagesCalculator {

	private static final PagesCalculator instance = new PagesCalculator();
	private static final int ITEMS_PER_PAGE = 5;
	private static final int DEDUCTION = 1;
	private static final int SINGLE_ITEM_PER_PAGE = 1;

	private PagesCalculator() {
	}

	public static PagesCalculator getInstance() {
		return instance;
	}

	public int calculateItemsFrom(int pageNumber) {
		return ITEMS_PER_PAGE * (pageNumber - DEDUCTION);
	}

	public int calculateTotalPages(int itemsNumber) {
		return (itemsNumber + ITEMS_PER_PAGE - DEDUCTION) / ITEMS_PER_PAGE;
	}

	public int getItemsPerPage() {
		return ITEMS_PER_PAGE;
	}

	public int getSingleItemsPerPage() {
		return SINGLE_ITEM_PER_PAGE;
	}
}
