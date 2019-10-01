package GroupB;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;



import java.awt.event.*;
import java.awt.*;
import java.io.*;


public class SixNationsPredictionsGUI extends JFrame{

	// Menu structure
	JMenuBar myBar;
	JMenu fileMenu, recordMenu,  exitMenu;
	JMenuItem fileLoad, fileSaveAs, addPrediction, removePrediction, exitProgram;
	
		
	// Array of data types to be used in combo box when defining new structure
	private String[] teamsArr = {"Ireland", "England", "Wales", "France", "Scotland", "Italy"};
	private JComboBox teamComboBox = new JComboBox(teamsArr);

	// Table Model
	JPanel p;
	MyTableModel tm;
	JTable myTable;
	JScrollPane myPane;
	
	ArrayList<MyPrediction> predictions = new ArrayList<MyPrediction>(); 
	
	// Used to indicate whether data is already in a file
	File currentFile;
	JFileChooser jfc;
	public SixNationsPredictionsGUI(){  
		// Setting up menu
		createMenuBar();

		p = new JPanel();
		tm = new MyTableModel(predictions);
		myTable = new JTable(tm);
		myTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		myPane = new JScrollPane(myTable);
		myTable.setSelectionForeground(Color.white);
		myTable.setSelectionBackground(Color.red);
		myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
		//setup combobox
        setUpTeamColumn(myTable, myTable.getColumnModel().getColumn(1));

        // Associating event listeners with menu items
       
        //TODO:(1) File - Open - Loading the contents of a file into the table, including all warning messages
        // Use provided function: readDataFile() to save the data into the file
        jfc = new JFileChooser();

	    jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        //TODO:(2) Save the data from the table into the file. 
		// Use provided function: writeDataFile() to save the data into the file
		

        //TODO:(3) Prediction - Add Row
	    addPrediction.addActionListener(new ActionListener()
	    {
	    @Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == addPrediction) {
		
				MyPrediction pred = new MyPrediction();
				predictions.add(pred);
					
				tm.fireTableRowsInserted(predictions.size() - 1, predictions.size() - 1);
							
					}
					}
		    });

        //TODO:(4) Prediction - Remove Row including warning messages
	    removePrediction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				int rowselected = myTable.getSelectedRow();
				if(rowselected == -1){
					JOptionPane.showMessageDialog(SixNationsPredictionsGUI.this, "Must select a row to delete");
				}
				else
				{
					 Object[] options = {"Yes",
			                    "No",
			                    "Cancel"};
					 int n = JOptionPane.showOptionDialog(SixNationsPredictionsGUI.this,
							    "Are you sure you want to Delete the selected row?" ,
							    "Select an Option",
							    JOptionPane.YES_NO_CANCEL_OPTION,
							    JOptionPane.QUESTION_MESSAGE,
							    null,
							    options,
							    options[2]);
					 
					 if(n  == JOptionPane.YES_OPTION) {
						 
						 predictions.remove(rowselected);
							tm.fireTableDataChanged();
					 }
					 else if(n  == JOptionPane.NO_OPTION) {
						 System.out.print("Delete Option Cancelled");
					 }
					
				}
				
			}
        });
	    fileLoad.addActionListener(new ActionListener()
	    
		{
	    @Override
		public void actionPerformed(ActionEvent e) {
			int ret = jfc.showOpenDialog(SixNationsPredictionsGUI.this);
			 if (ret == JFileChooser.APPROVE_OPTION) { 
				 Object[] options = {"Yes",
		                    "No",
		                    "Cancel"};
				 int n = JOptionPane.showOptionDialog(SixNationsPredictionsGUI.this,
						    "This will replace the existing data.\n Are you sure you want to do this?" ,
						    "Select an Option",
						    JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null,
						    options,
						    options[2]);
				 
				 if(n  == JOptionPane.YES_OPTION) {
					 
					 File file = jfc.getSelectedFile(); 
				     
				     
				     System.out.println("Opening: " + file.getName() + "." + "\n"); 
				     readDataFile(file);
				 }
				 else if(n  == JOptionPane.NO_OPTION) {
					 System.out.println("Open command cancelled by user." + "\n"); 
				 }	
				 else
					 System.out.println("Open command cancelled by user." + "\n");
			   } 
	    }
		
	});
	    fileSaveAs.addActionListener(new ActionListener()
	    {
	    public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fileSaveAs) {
			if(predictions.size() <=0)
				JOptionPane.showMessageDialog(SixNationsPredictionsGUI.this, "You must have saved records");
			else {
				
				int returnVal = jfc.showSaveDialog(SixNationsPredictionsGUI.this);
				
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
					File fileSelected = jfc.getSelectedFile();
				     System.out.println("Saving Location: " + fileSelected.getAbsolutePath() + "." + "\n"); 
				     writeDataFile(fileSelected);   
			    } 
				   else { 
					   System.out.println("Save command cancelled by user." + "\n"); 			
			
                    tm.fireTableDataChanged();
					
			    }
			}
		}
	    }
	    });
	    
	   
	    

		// exits program from menu
		exitProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeDown();
			}
		});

		// exits program by closing window
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				closeDown();
			}
		}); // end windowlistener
		
		// Adding menu bar and table to panel
		this.setJMenuBar(myBar);
		this.add(myPane, BorderLayout.CENTER);
		
		this.setTitle("2019 Six Nations Rubgy");
		this.setVisible(true);
		this.pack();
	} // constructor

	private void createMenuBar() {
		fileLoad = new JMenuItem("Open");
		fileSaveAs = new JMenuItem("Save As");
		
		fileMenu = new JMenu("File");
		fileMenu.add(fileLoad);
		fileMenu.add(fileSaveAs);
		
		addPrediction = new JMenuItem("Add");
		removePrediction = new JMenuItem("Remove");
		
		recordMenu = new JMenu("Prediction");
		recordMenu.add(addPrediction);
		recordMenu.add(removePrediction);
		
		
		exitProgram = new JMenuItem("Exit Program");
		exitMenu = new JMenu("Exit");
		exitMenu.add(exitProgram);
		
		myBar = new JMenuBar();
		myBar.add(fileMenu);
		myBar.add(recordMenu);
		myBar.add(exitMenu);
	}

    public void setUpTeamColumn(JTable table,TableColumn teamColumn) {	  
      	  teamColumn.setCellEditor(new DefaultCellEditor(teamComboBox));
      	
          //Set up tool tips for the sport cells.
          DefaultTableCellRenderer renderer =
                  new DefaultTableCellRenderer();
          renderer.setToolTipText("Click for combo box");
          teamColumn.setCellRenderer(renderer);
      }
    
	public void writeDataFile(File f) {
		
				ObjectOutputStream out = null;
				try {
					
					FileOutputStream fs = new FileOutputStream(f);
					out = new ObjectOutputStream(fs);
					
					
					out.writeObject(predictions);;
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				finally {
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}

	public void readDataFile(File f) {
		ObjectInputStream in = null;
		try {
			FileInputStream fileInput = new FileInputStream(f);
            in = new ObjectInputStream(fileInput);
		 ArrayList<MyPrediction> tempArr = (ArrayList<MyPrediction>)in.readObject();
		 predictions.clear();
		 predictions.addAll(tempArr);
		 for(int i = 0; i<tempArr.size(); i++) {
			 tm.fireTableDataChanged();
			
		 }
		 }
		
		
		catch(FileNotFoundException e){
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		}

	public void closeDown() {
			System.exit(0);
	}

	public static void main (String args[]){
		new SixNationsPredictionsGUI();
	} // main
} //class