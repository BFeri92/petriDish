package hu.unideb.inf.prt.petriDish.view;

/**
 * User interfaces should implement this interface.
 * @author feri0
 *
 */
public interface View {
	/**
	 * Simulation will call this method on every world step.
	 * The implementation should update the user interface.
	 */
	public void render();

	/**
	 * The implementation should display an error message when this method is called.
	 * @param messg the error message to be displayed
	 */
	public void error(String messg);

	/**
	 * This method should close the user interface.
	 */
	public void close();
}
