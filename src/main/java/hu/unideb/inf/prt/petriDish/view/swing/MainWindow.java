package hu.unideb.inf.prt.petriDish.view.swing;

import hu.unideb.inf.prt.petriDish.Entity;
import hu.unideb.inf.prt.petriDish.Game;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridLayout;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JScrollBar;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.SwingConstants;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnSimulation;
	private JMenuItem mntmStart;
	private JMenuItem mntmPause;
	private JSeparator separator;
	private JMenuItem mntmLoadConfiguration;
	private JMenuItem mntmLoadWorldDescriptor;
	private JMenuItem mntmSaveSimulation;
	private JSeparator separator_1;
	private JMenuItem mntmExit;
	private PDDisplayPanel panel_1;
	private JPanel panel;
	private JLabel lblSimulationSpeed;
	private JSlider slider;
	private JSeparator separator_2;
	private JLabel lblGeneration;
	private JLabel lblWorldAge;
	private JLabel lblEnitiesAlive;
	
	private class onExitClicked implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			Game.getInstance().stopSimulation();
			Game.getInstance().getUI().close();
		}
	}
	
	private class onSliderChanged implements ChangeListener
	{

		public void stateChanged(ChangeEvent e) {
			Game.getInstance().setDelay(slider.getValue());
		}
		
	}
	
	private class onDisplayKeyPressed implements KeyListener
	{

		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
		}

		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
		}

		public void keyTyped(KeyEvent arg0) {
			if (arg0.getKeyChar() == 's')
			{
				panel_1.moveDown();
			}
			if (arg0.getKeyChar() == 'w')
			{
				panel_1.moveUp();
			}
			if (arg0.getKeyChar() == 'a')
			{
				panel_1.moveLeft();
			}
			if (arg0.getKeyChar() == 'd')
			{
				panel_1.moveRight();
			}
		}
		
	}
	
	private class onLoadConfigurationClicked implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			if ( fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION )
			{
				Game.getInstance().loadGameConfiguration(fileChooser.getSelectedFile().getPath());
				Game.getInstance().startSimulation();
			}
		}
		
	}
	
	private class onLoadWorldDescriptorClicked implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			if ( fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION )
			{
				Game.getInstance().loadWorldDescriptor(fileChooser.getSelectedFile().getPath());
				Game.getInstance().startSimulation();
			}			
		}
		
	}
	
	private class onStartClicked implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			if (Game.getInstance().hasSimulationLoaded())
			{
				Game.getInstance().startSimulation();
			} else {
				Game.getInstance().loadDefaultConfiguration();
				Game.getInstance().startSimulation();
			}
		}
	}
	
	private class onPauseClicked implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			Game.getInstance().pauseSimulation();
		}
	}
	
	private class onSaveSimulationClicked implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			if ( fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION )
			{
				Game.getInstance().saveSimulation(fileChooser.getSelectedFile().getPath());
			}
		}
		
	}
	
	private class onDisplayClick implements MouseListener
	{

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			panel_1.requestFocus();
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	public void useEntityList(List<Entity> l)
	{
		panel_1.useEntityList(l);
	}
	
	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnSimulation = new JMenu("Simulation");
		menuBar.add(mnSimulation);
		
		mntmStart = new JMenuItem("Start");
		mntmStart.addActionListener(new onStartClicked());
		mnSimulation.add(mntmStart);
		
		mntmPause = new JMenuItem("Pause");
		mntmPause.addActionListener(new onPauseClicked());
		mnSimulation.add(mntmPause);
		
		separator = new JSeparator();
		mnSimulation.add(separator);
		
		mntmLoadConfiguration = new JMenuItem("Load configuration");
		mntmLoadConfiguration.addActionListener(new onLoadConfigurationClicked());
		mnSimulation.add(mntmLoadConfiguration);
		
		mntmLoadWorldDescriptor = new JMenuItem("Load world descriptor");
		mntmLoadWorldDescriptor.addActionListener(new onLoadWorldDescriptorClicked());
		mnSimulation.add(mntmLoadWorldDescriptor);
		
		mntmSaveSimulation = new JMenuItem("Save simulation");
		mntmSaveSimulation.addActionListener(new onSaveSimulationClicked());
		mnSimulation.add(mntmSaveSimulation);
		
		separator_1 = new JSeparator();
		mnSimulation.add(separator_1);
		
		mntmExit = new JMenuItem("Exit");
		
		mntmExit.addActionListener(new onExitClicked());
		
		mnSimulation.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new PDDisplayPanel();
		panel_1.addKeyListener(new onDisplayKeyPressed());
		panel_1.addMouseListener(new onDisplayClick());
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		lblSimulationSpeed = new JLabel("Simulation speed");
		panel.add(lblSimulationSpeed);
		
		slider = new JSlider();
		slider.setMinimum(3);
		slider.setMaximum(500);
		slider.setInverted(true);
		slider.setValue(100);
		slider.addChangeListener(new onSliderChanged());
		panel.add(slider);
		
		separator_2 = new JSeparator();
		panel.add(separator_2);
		
		lblGeneration = new JLabel("Generation");
		lblGeneration.setAlignmentX(RIGHT_ALIGNMENT);
		panel.add(lblGeneration);
		
		lblWorldAge = new JLabel("World age");
		lblWorldAge.setAlignmentX(RIGHT_ALIGNMENT);
		panel.add(lblWorldAge);
		
		lblEnitiesAlive = new JLabel("Enities alive");
		lblEnitiesAlive.setAlignmentX(RIGHT_ALIGNMENT);
		panel.add(lblEnitiesAlive);
	}
	
	public void fillWorldInformation(int generation, long age, int alive)
	{
		lblWorldAge.setText("World age: "+Long.toString(age));
		lblEnitiesAlive.setText("Alive: "+Integer.toString(alive));
		lblGeneration.setText("Generation: "+Integer.toString(generation));
	}
}
