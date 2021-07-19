// Name: Miguel Nunez
// Date: 12/13/2020
// Description: Creates a Music Player
// File Name: Final.java
import java.awt.*;
import java.util.*;
// import java.lang.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.lang.Thread;

import java.awt.event.*;

import java.io.*;
import java.io.File; 
import java.io.IOException;

import javax.sound.sampled.*;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
 
import javax.swing.JFileChooser;

import javax.swing.UIManager;

public class Final
{	
	
	public static void main(String[] args) 
	{
		MusicGUI mGUI = new MusicGUI();
		mGUI.setSize(675, 525);
     	mGUI.setLocationRelativeTo(null);
     	mGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     	mGUI.setVisible(true);
	}

}
class MusicGUI extends JFrame implements java.io.Serializable
{
	// private data
	private Long currentFrame;
	private Clip clip;
	private int currentIndex;
	private double volume;
	private double vol;
	private float db;
	private boolean status;
	private boolean mute; 
	private String filePath;
	private String song1;
	private String song2;

	private ArrayList<String> songs = new ArrayList<String>();
	private FloatControl gainControl;
	private DefaultListModel<String> model;
	private AudioInputStream audioInputStream;
	private Long seconds;
	private Long minutes;	
	private Long startTime;
					
	//public data
	public JMenuBar menuBar;
	public JMenu menu;
	public JButton muteBtn;
	public ImageIcon img;
	public JList list;
	public JSlider slider;
	public JLabel songTitle, songElapse, songLength;
	public JPanel listPanel, addDelBtnPanel, songTitlePanel, playBtnsPanel, sliderPanel;
	// default constructor
	MusicGUI() 
	{	
		// initialize data to default values
		volume = 0.0;
		vol = 0.0;
		db = 0F;
		currentFrame = 0L;
		status = false;
		currentIndex = 0;
		mute = false;

		seconds = 0L;
		minutes = 0L;

		// create a list model that will hold a default list of songs
		model = new DefaultListModel<>();

		// initalize file paths - these songs will be in music player everytime
		song1 = "Crew - Goldlink.wav";
		song2 = "Lil Uzi Vert - XO Tour Life.wav";


		songs.add(song1);
		songs.add(song2);

		// add the songs to the model
		model.addElement(song1);
		model.addElement(song2);

		filePath = song1;

		// set the title of the GUI
		setTitle("Music Player by Miguel Nunez");

		// set the layout
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		// initialize icon image
		img = new ImageIcon(this.getClass().getResource("/images/music.png"));

		// add it to the GUI
		setIconImage(img.getImage());

		// create a menu bar and add a menu 
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menuBar.add(menu);
		setJMenuBar(menuBar);

		// get all of the panels ready
		listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(getWidth(), 155));
		listPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 12));
		listPanel.setLayout(new GridLayout(0,1));

		//UIManager.put("TabbedPane.contentOpaque", new java.awt.Color(255, 127, 80));
		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
		UIManager.put("TabbedPane.borderHightlightColor", new java.awt.Color(255, 255, 255));
		UIManager.put("TabbedPane.selected", new java.awt.Color(255, 255, 255));
		JTabbedPane tp = new JTabbedPane();  
    	//tp.setBounds(50,50,200,200);
    	tp.addTab("Playlist", listPanel);
    	

        addDelBtnPanel = new JPanel();
        addDelBtnPanel.setPreferredSize(new Dimension(getWidth(), -50));
        addDelBtnPanel.setBackground(Color.WHITE);
        addDelBtnPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        addDelBtnPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 

        songTitlePanel = new JPanel();
		songTitlePanel.setBackground(Color.WHITE);
        songTitlePanel.setLayout(new FlowLayout());
        songTitlePanel.setPreferredSize(new Dimension(getWidth(), -85));

        playBtnsPanel = new JPanel();
        playBtnsPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#f2f2f2"), 4));
        playBtnsPanel.setBackground(Color.WHITE);
		playBtnsPanel.setPreferredSize(new Dimension(getWidth(), -20));
		playBtnsPanel.setLayout(new FlowLayout());

		sliderPanel = new JPanel();
		sliderPanel.setBackground(Color.WHITE);
		sliderPanel.setPreferredSize(new Dimension(getWidth(), -85));
        
        // use helper methods to add components to their respective panel
        addMenuItem(this);
        addList(this, model);
        addBtn(this);
        delBtn(this);
        muteBtn(this);
        addSongTitle(this);
        addSongElapse(this);
        addSongLength(this);
        addSlider(this);
        addBackwardBtn(this);        
        addStopBtn(this);
        addPlayBtn(this);
        addPauseBtn(this);
        addForwardBtn(this);
        //addShuffleBtn(this);

        // set the alignment of the panels
        listPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addDelBtnPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        songTitlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playBtnsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // add each panel to the frame
        add(tp);
        add(addDelBtnPanel);
        add(songTitlePanel);
        add(sliderPanel);
        add(playBtnsPanel);      
	}
	public void addShuffleBtn(MusicGUI mGUI)
	{
		JButton shuffleBtn = new JButton();
		shuffleBtn.setIcon(new ImageIcon(this.getClass().getResource("/images/shuffle.png")));
		shuffleBtn.setBackground(Color.WHITE);
		shuffleBtn.setPreferredSize(new Dimension(36, 30));
		shuffleBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	System.out.print("hey");
          }
        });
		mGUI.getAddDelBtnPanel().add(shuffleBtn);
	}
	public void addMenuItem(MusicGUI mGUI)
	{
		JMenuItem m1 = new JMenuItem("Exit");
		m1.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	System.exit(0);
        	}
        });
		mGUI.getMenu().add(m1);
	}

	@SuppressWarnings("unchecked")
	public void addList(MusicGUI mGUI, DefaultListModel model)
	{
  		list = new JList(model);
  		list.setFont(new Font("Arimo", Font.BOLD, 13));
  		list.setForeground(Color.decode("#737373"));
  		list.setSelectedIndex(0);
		JScrollPane listScrollPane = new JScrollPane(list);
		mGUI.getListPanel().add(listScrollPane);
	}

	public void addBtn(MusicGUI mGUI)
	{
		JButton addBtn = new JButton();
		addBtn.setIcon(new ImageIcon(this.getClass().getResource("/images/add.png")));
		addBtn.setBackground(Color.WHITE);
		addBtn.setFocusPainted(false);
		addBtn.setPreferredSize(new Dimension(36, 30));
		addBtn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
	        JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		    int result = fileChooser.showOpenDialog(mGUI.getAddDelBtnPanel());
			    if(result == JFileChooser.APPROVE_OPTION)
			    {
			    File selectedFile = fileChooser.getSelectedFile();
			    // songs array list with full path
			    songs.add(selectedFile.toString());
			    // songs JList with half path
			    addSongToList(selectedFile.getName());
			    }
     		}
        });
		mGUI.getAddDelBtnPanel().add(addBtn);
	}

	public void delBtn(MusicGUI mGUI)
	{
		JButton delBtn = new JButton();
		delBtn.setIcon(new ImageIcon(this.getClass().getResource("/images/del.png")));
		delBtn.setBackground(Color.WHITE);
		delBtn.setFocusPainted(false);
		delBtn.setPreferredSize(new Dimension(36, 30));
		delBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	currentIndex = getList().getSelectedIndex();
        	model.remove(currentIndex);
        	songs.remove(currentIndex);
        	currentIndex = list.getModel().getSize() - 1;
          }
        });
		mGUI.getAddDelBtnPanel().add(delBtn);
	}

	public void muteBtn(MusicGUI mGUI)
	{
		muteBtn = new JButton();
		muteBtn.setIcon(new ImageIcon(this.getClass().getResource("/images/mute.png")));
		muteBtn.setBackground(Color.WHITE);
		muteBtn.setFocusPainted(false);
		muteBtn.setPreferredSize(new Dimension(36, 30));
		muteBtn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
	        	if(!mute)
		    		mute();
	        	else
	        		unmute();
     		}
        });
		mGUI.getAddDelBtnPanel().add(muteBtn);
	}

	public void mute()
	{
		db = (float) (Math.log(0)/ Math.log(10) * 20);
		gainControl.setValue(db);
		muteBtn.setIcon(new ImageIcon(this.getClass().getResource("/images/unmute.png")));
		mute = true;
	}

	public void unmute()
	{
		soundControl();
	    muteBtn.setIcon(new ImageIcon(this.getClass().getResource("/images/mute.png")));
	    mute = false;
	}

	public void addSongTitle(MusicGUI mGUI)
	{
		songTitle = new JLabel(filePath);
		songTitle.setFont(new Font("Arimo",Font.BOLD + Font.ITALIC, 13));
		mGUI.getSongTitlePanel().add(songTitle);
	}

	public void addSongElapse(MusicGUI mGUI)
    {
		songElapse = new JLabel("- 00:00");
		songElapse.setFont(new Font("Arimo",Font.BOLD + Font.ITALIC, 13));
		mGUI.getSongTitlePanel().add(songElapse);
    }

	public void addSongLength(MusicGUI mGUI)
    {
		songLength = new JLabel("- 00:00");
		songLength.setFont(new Font("Arimo",Font.BOLD + Font.ITALIC, 13));
		mGUI.getSongTitlePanel().add(songLength);
    }

    public void addSlider(MusicGUI mGUI)
	{
		slider = new JSlider();
		slider.setBackground(Color.WHITE);
		slider.addChangeListener(new ChangeListener(){
        public void stateChanged(ChangeEvent event){
        	try{
        		if(mute)
        			unmute();
        	soundControl();
        	}catch(Exception ex){}
         }
        });
		mGUI.getSliderPanel().add(slider);
	}

	public void addBackwardBtn(MusicGUI mGUI)
	{
		ImageIcon backwardPhoto = new ImageIcon(this.getClass().getResource("/images/backward.png"));
		JButton backwardBtn = new JButton(backwardPhoto);
		backwardBtn.setPreferredSize(new Dimension(42, 36));
		backwardBtn.setBackground(Color.WHITE);
		backwardBtn.setFocusPainted(false);
		backwardBtn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	try{
        	 stopMusic();
        	 doNothing();
        	 currentIndex--;
        	 if(currentIndex < 0)
        	 	currentIndex = 0;
        	 list.setSelectedIndex(currentIndex);
        	 filePath = list.getModel().getElementAt(currentIndex).toString();
        	 // create AudioInputStream object 
			 audioInputStream = 
			    AudioSystem.getAudioInputStream(new File(songs.get(currentIndex))); 
			 // create clip reference 
			 clip = AudioSystem.getClip(); 				    
			 // open audioInputStream to the clip 
			 clip.open(audioInputStream);
			 updateSongTitle();
			 soundControl();
			 updateSongLength(audioInputStream);
			 startTime = clip.getMicrosecondPosition();
			 if(mute)
				mute();
			 play();
			}catch(Exception ex){}
         }
        });
		mGUI.getPlayBtnsPanel().add(backwardBtn);
	}

	public void addStopBtn(MusicGUI mGUI)
	{
		ImageIcon stopPhoto = new ImageIcon(this.getClass().getResource("/images/stop.png"));
		JButton stopBtn = new JButton(stopPhoto);
		stopBtn.setPreferredSize(new Dimension(52, 50));
		stopBtn.setBackground(Color.WHITE);
		stopBtn.setFocusPainted(false);
		stopBtn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	stopMusic();
        	}
        });
		mGUI.getPlayBtnsPanel().add(stopBtn);
	}

    public void addPlayBtn(MusicGUI mGUI) 
	{
		ImageIcon playPhoto = new ImageIcon(this.getClass().getResource("/images/play.png"));
		JButton playBtn = new JButton(playPhoto);
		playBtn.setPreferredSize(new Dimension(76, 70));//width / height
		playBtn.setBackground(Color.WHITE);
		playBtn.setFocusPainted(false);
		playBtn.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	      	    try{
	      	  	// stop the current song before playing another one
	      	  		try{
				    	stopMusic();
				    }
				    catch(Exception ex){}
	                // IF YOU CLICK ON A SONG - WE PLAY THAT SONG
			      	filePath = getList().getSelectedValue().toString();
			      	// current track index
			      	currentIndex = getList().getSelectedIndex();

			      	// create AudioInputStream object 
					audioInputStream = 
						AudioSystem.getAudioInputStream(new File(songs.get(currentIndex))); 
					// create clip reference 
				    clip = AudioSystem.getClip(); 			    
				    // open audioInputStream to the clip 
				    clip.open(audioInputStream);

				    updateSongTitle();
				    soundControl();
				    startSongTimer();
				    updateSongLength(audioInputStream);
				    if(mute)
				    	mute();
				   	play();				 
	      	  	}  
		      	catch(Exception ex1){

		      		try{
		      			playDefault();
		      		}
		      		catch(Exception ex2){}	
	      	  	}	           
	      	}
        });
		mGUI.getPlayBtnsPanel().add(playBtn);
	}

	public void addPauseBtn(MusicGUI mGUI) 
	{
		ImageIcon pausePhoto = new ImageIcon(this.getClass().getResource("/images/pause.png"));
		JButton pauseBtn = new JButton(pausePhoto);
		pauseBtn.setPreferredSize(new Dimension(52, 50));
		pauseBtn.setBackground(Color.WHITE);
		pauseBtn.setFocusPainted(false);
		pauseBtn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
    		try{
    			if(status) 
				{ 
					mGUI.currentFrame = 
					mGUI.clip.getMicrosecondPosition(); 
					clip.stop(); 
					status = false;
					return;
				}
				else
  				{ 
	  				clip.setMicrosecondPosition(currentFrame); 
					play();
					status = true;
					return;	
  				}  
    	  	}
		    catch(Exception ex){}			          
        }
    });
	mGUI.getPlayBtnsPanel().add(pauseBtn);
	}

	public void addForwardBtn(MusicGUI mGUI)
	{
		JButton forwardBtn = new JButton();
		forwardBtn.setPreferredSize(new Dimension(42, 36));
		forwardBtn.setIcon(new ImageIcon(this.getClass().getResource("/images/forward.png")));
		forwardBtn.setBackground(Color.WHITE);
		forwardBtn.setFocusPainted(false);
		forwardBtn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        	forwardSong();
         	}
        });
		mGUI.getPlayBtnsPanel().add(forwardBtn);
	}

	public void forwardSong()
	{
        try{
		stopMusic();
		doNothing();
	 	currentIndex++;
	 	if(currentIndex == list.getModel().getSize())
	 		currentIndex = 0;

	 	list.setSelectedIndex(currentIndex);
	 	filePath = list.getModel().getElementAt(currentIndex).toString();
	 	// create AudioInputStream object 
	 	audioInputStream = 
	    	AudioSystem.getAudioInputStream(new File(songs.get(currentIndex))); 
	 	// create clip reference 
	 	clip = AudioSystem.getClip(); 				    
	 	// open audioInputStream to the clip 
		clip.open(audioInputStream);
		updateSongTitle();
		soundControl();
		updateSongLength(audioInputStream);
		startTime = clip.getMicrosecondPosition();
		if(mute)
			mute();
		play();

		}catch(Exception ex){}
	}

	public synchronized void startSongTimer(){
	Thread thread = new Thread(){
		public void run()
		{
			try{
				boolean isTrue = true;
				Long current;
				startTime = clip.getMicrosecondPosition();
				while(isTrue)
				{
					if(status)
				  	{
						doNothing();
						current = (clip.getMicrosecondPosition() + 1) - startTime;
						seconds = current/1000000;
						if(seconds == 60)
        				{
            				startTime = clip.getMicrosecondPosition();
            				seconds = 0L;
            				minutes++;
        				}
        				displaySongElapse(minutes, seconds);
				  	}
				  	else{
				  		doNothing();
				  	}
				  	
				}
			}catch(Exception ex){}
		}
	}; thread.start();
	}

	private void displaySongElapse(Long minutes, Long seconds)
	{
		if(seconds < 0)
			seconds = 0L;

		if(minutes < 10)
			if(seconds < 10)
		   		songElapse.setText(String.valueOf("- 0" + minutes + ":" + "0" + seconds));
			else
		   		songElapse.setText(String.valueOf("- 0" + minutes + ":" + seconds));
		else
			if(seconds < 10)
		   		songElapse.setText(String.valueOf("- " + minutes + ":" + "0" + seconds));
		   	else
		       	songElapse.setText(String.valueOf("- " + minutes + ":" + seconds));

		songElapse.setFont(new Font("Arimo",Font.BOLD + Font.ITALIC, 13));

		System.out.println(songElapse +" "+ songLength);
		if(songLength.getText().equals(songElapse.getText()))
			forwardSong();
			
	}

	private void updateSongLength(AudioInputStream ais)
	{
		AudioFormat format = ais.getFormat();
		long audioFileLength = new File(songs.get(currentIndex)).length();
		int frameSize = format.getFrameSize();
		float frameRate = format.getFrameRate();
		int durationInSeconds = Math.round(audioFileLength / (frameSize * frameRate));
		durationInSeconds--;
		Long s = Long.valueOf(durationInSeconds % 60);
		Long h = Long.valueOf(durationInSeconds / 60);
		Long m = h % 60;
		displaySongLength(m, s);
		
	}

	public void displaySongLength(Long minutes, Long seconds)
	{
		if(minutes < 10)
			if(seconds < 10)
		   		songLength.setText(String.valueOf("- 0" + minutes + ":" + "0" + seconds));
			else
		   		songLength.setText(String.valueOf("- 0" + minutes + ":" + seconds));
		else
			if(seconds < 10)
		   		songLength.setText(String.valueOf("- " + minutes + ":" + "0" + seconds));
		   	else
		       	songLength.setText(String.valueOf("- " + minutes + ":" + seconds));

		songLength.setFont(new Font("Arimo",Font.BOLD + Font.ITALIC, 13));
	}

    public void doNothing() throws InterruptedException
    {
    	Thread.sleep(300);
    }

	private void stopMusic()
	{
		currentFrame = 0L;				
		seconds = 0L;
		minutes = 0l;
		status = false;
        clip.stop();
        clip.close();
	}

	private void play() 
    {
    	clip.start();
    	status = true;    	    	 	     
    }

    private void playDefault()
    {
    	// IF YOU DON'T CLICK A SONG - WE PLAY THIS ONE BY DEFAULT ONE
    	try{
		// create AudioInputStream object 
		audioInputStream = 
			AudioSystem.getAudioInputStream(new File(songs.get(currentIndex))); 
		// create clip reference 
		clip = AudioSystem.getClip(); 
		// open audioInputStream to the clip 
		clip.open(audioInputStream); 
		clip.start();
		status = true;
		}catch(Exception ex){}
    }

    private void updateSongTitle()
	{
		songTitle.setText(filePath);
	}

	private void addSongToList(String song)
	{
	 	model.addElement(song);
	}

	private void soundControl()
	{
		volume = slider.getValue();
        vol = volume/ 100;
        gainControl = 
    		(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    	db = (float) (Math.log(vol)/ Math.log(10) * 20);
    	gainControl.setValue(db);
	}

	public JPanel getSliderPanel()
	{
		return sliderPanel;
	}

	public JPanel getListPanel()
	{
		return listPanel;
	}

	public JPanel getAddDelBtnPanel()
	{
		return addDelBtnPanel;
	}

	public JPanel getSongTitlePanel()
	{
		return songTitlePanel;
	}

    public JPanel getPlayBtnsPanel()
	{
		return playBtnsPanel;
	}

	public JLabel getSongLength()
	{
		return songLength;
	}

    public JList getList()
    {
    	return list;
    }

	public JMenu getMenu()
	{
		return menu;
	}

	public boolean getStatus()
	{
		return status;
	}

	public ArrayList<String> getSongsArray()
	{
		return songs;
	}
}
