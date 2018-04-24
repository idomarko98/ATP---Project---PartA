package algorithms.mazeGenerators;

import java.util.Objects;

/**
 * Class that describes a position
 * Data members: rowIndex - the row index(int), columnIndex - the column index(int)
 */
public class Position {
    private int rowIndex, columnIndex;

    /** Constructor
     * @param rowIndex - Row index
     * @param columnIndex - Column index
     */
    public Position(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    /** Copy Constructor
     * @param p - Position to be copied
     */
    public Position(Position p){
        this.rowIndex = p.getRowIndex();
        this.columnIndex = p.getColumnIndex();
    }

    /** Column index getter
     * @return Column index
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /** Row index getter
     * @return Row index
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /** Overrided function to check if one position is equal to another - done by comparing the row and column indexes
     * @param obj - object to check if equals
     * @return true if they're equals, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position){
            Position posObj = (Position)obj;
            if(posObj.getColumnIndex() == this.getColumnIndex() && posObj.getRowIndex() == this.getRowIndex())
                return true;
        }
        return  false;
    }

    /** Overrided function to generate hashcode. Hashes using row and column indexes
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, columnIndex);
    }

    /** Return Position in next format: "{rowIndex,columnIndex}"
     * @return String format of Position
     */
    @Override
    public String toString() {
        return "{" + getRowIndex() + "," + getColumnIndex() + "}";
    }
}
