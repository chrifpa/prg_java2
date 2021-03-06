package gui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SimulationPanel extends JPanel {
	JButton loadButton, saveButton;
	JScrollPane scrollPane;
	JTextArea textArea;
	JPanel text, buttons, all;
	Application app;
	JLabel label;

	private Application getApp(){
		return app;
	}

	public SimulationPanel(Application app, boolean debug){
		super();

		this.app = app;

		if(debug) setBackground(Color.RED);

		saveButton = new JButton("save simulation result");
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JFileChooser chooser = new JFileChooser();
				final Application fapp = getApp();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt");
				chooser.setFileFilter(filter);
				chooser.setSelectedFile(new File(fapp.getCurrentModelName() + "_Simulationsergebnis.txt"));

				int option = chooser.showSaveDialog(fapp);
				if(option == JFileChooser.APPROVE_OPTION){
					try{
						BufferedWriter writer = new BufferedWriter(new FileWriter(chooser.getSelectedFile()));
						textArea.write(writer);
						writer.close();
						textArea.requestFocus();
					}
					catch(Exception e){
						System.out.println(e + "\ncould not open file.");
					}
				}
			}
		});

		textArea = new JTextArea("your result appears here.", 25, 30);
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		all = new JPanel(new BorderLayout());
		text = new JPanel();
		buttons = new JPanel();
		label = new JLabel(" ");
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(JLabel.CENTER);

		text.add(scrollPane);

		buttons.add(saveButton);
		all.add(text, BorderLayout.NORTH);
		all.add(label, BorderLayout.CENTER);
		all.add(buttons, BorderLayout.SOUTH);
		this.add(all);
	}

	public SimulationPanel(Application app){
		this(app, false);
	}

	public void setTextArea(File file){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			textArea.read(reader, null);
			reader.close();
			saveButton.requestFocus();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public JTextArea getTextArea(){
		return textArea;
	}
}