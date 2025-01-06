package step0_static;

public class DesignationStatic {

	public static enum Column {YourReference, OurReference, Remarks, Description}
	
	/**
	 * Get the right Column. Understands the spaces and the lower/upper case
	 * @param _sColumnStr
	 * @return
	 */
	public static Column getColumnEnum(String _sColumnStr) {
		_sColumnStr = _sColumnStr.replaceAll(" ", "").toUpperCase();
		for (Column lColumn : Column.values()) {
			if (_sColumnStr.equals(lColumn.toString().toUpperCase())) {
				return lColumn;
			}
		}
		return null;
	}
	
	/*
	 * Getters & Setters
	 */

	
}
