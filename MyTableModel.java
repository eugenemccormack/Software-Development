package GroupB;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
	private String[] columnNames = {"Person's Name",
            "Team",
            "Score",
            "Bet Amount"};//same as before
	
	private ArrayList<MyPrediction> data;
	
	public MyTableModel(ArrayList<MyPrediction> dataRef) {
		data = dataRef;
	}
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}
	
	public String getColumnName(int col) {
	        return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		MyPrediction p = data.get(row);
	   	switch(col) {
    	case 0:
    		return p.getName();
       	case 1:
       		return p.getTeam();
       	case 2:
       		return p.getScore();
       	case 3:
       		return p.getBetAmount();
       	default: return null;
    	}
 
	}
	/*N.B. Implement this if you want registered cell rendering format, e.g. numbers will be right-justified, booleans represented as checkboxes,*/
	
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

  public boolean isCellEditable(int row, int col) {
	  	return true;
    }
	
	  /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
    	MyPrediction p = data.get(row);
	   	switch(col) {
    	case 0:
    		p.setName((String)value);
    		break;
       	case 1:
       		p.setTeam((String)value);
       		break;
       	case 2:
       		p.setScore((int)value);
       		break;
       	case 3:
       		p.setBetAmount((double) value);
       		break;
    	}
        fireTableCellUpdated(row, col);
    }


}
