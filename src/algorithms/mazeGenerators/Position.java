package algorithms.mazeGenerators;

public class Position {
    private int rowIndex, columnIndex;

    public Position(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position){
            Position posObj = (Position)obj;
            if(posObj.getColumnIndex() == this.getColumnIndex() && posObj.getRowIndex() == this.getRowIndex())
                return true;
        }
        return  false;
    }

    @Override
    public int hashCode() {
        return this.getRowIndex() / this.getColumnIndex();
    }

    @Override
    public String toString() {
        return "(" + getColumnIndex() + ", " + getRowIndex() + ")";
    }
}
