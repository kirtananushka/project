package by.tananushka.project.util;

/**
 * The type Pages calculator.
 */
public class PagesCalculator {

	private static final PagesCalculator instance = new PagesCalculator();
	private static final int ITEMS_PER_PAGE = 5;
	private static final int DEDUCTION = 1;
	private static final int SINGLE_ITEM_PER_PAGE = 1;

	private PagesCalculator() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static PagesCalculator getInstance() {
		return instance;
	}

	/**
	 * Calculate items from int.
	 *
	 * @param pageNumber the page number
	 * @return the int
	 */
	public int calculateItemsFrom(int pageNumber) {
		return ITEMS_PER_PAGE * (pageNumber - DEDUCTION);
	}

	/**
	 * Calculate total pages int.
	 *
	 * @param itemsNumber the items number
	 * @return the int
	 */
	public int calculateTotalPages(int itemsNumber) {
		return (itemsNumber + ITEMS_PER_PAGE - DEDUCTION) / ITEMS_PER_PAGE;
	}

	/**
	 * Gets items per page.
	 *
	 * @return the items per page
	 */
	public int getItemsPerPage() {
		return ITEMS_PER_PAGE;
	}

	/**
	 * Gets single items per page.
	 *
	 * @return the single items per page
	 */
	public int getSingleItemsPerPage() {
		return SINGLE_ITEM_PER_PAGE;
	}
}
