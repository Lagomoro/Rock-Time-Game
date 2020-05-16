package per.lagomoro.rocktime.runtime;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import per.lagomoro.rocktime.controller.MusicController;
import per.lagomoro.rocktime.controller.NodeController;
import per.lagomoro.rocktime.ui.MainWindow;

public class Runtime {
	
	public static void main(String[] args) {
		
		NodeController.init();
		updateNodeController();

		@SuppressWarnings("serial")
		MainWindow mainWindow = new MainWindow() {
			@Override
			public void play() {
				super.play();
				JFileChooser fileChooser = new JFileChooser("./");
    			FileFilter filter = new FileNameExtensionFilter("乐谱文件", "txt");
    			fileChooser.addChoosableFileFilter(filter);
    			fileChooser.setAcceptAllFileFilterUsed(false);
				if(fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
					String filename = fileChooser.getSelectedFile().getPath();
					String foldername = fileChooser.getSelectedFile().getParent();
					NodeController.reset();
					NodeController.loadNotes(filename);
					MusicController.reset();
					MusicController.play(foldername + "\\" + NodeController.getFilename().trim() + ".wav");
				}
			}
			@Override
			public void pause() {
				super.pause();
				MusicController.pause();
			}
			@Override
			public void resume() {
				super.resume();
				MusicController.replay();
			}
			@Override
			public void stop() {
				super.stop();
				MusicController.stop();
				MusicController.reset();
			}
		};
		mainWindow.active();
		updateWindow(mainWindow);
    	
	}
	
    private static void updateNodeController() {
        long initialDelay = 0;
        long period = 1;
        TimeUnit unit = TimeUnit.MILLISECONDS;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
            	NodeController.update();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, initialDelay, period, unit);
    }
    
    private static void updateWindow(MainWindow window) {
        long initialDelay = 0;
        long period = 17;
        TimeUnit unit = TimeUnit.MILLISECONDS;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
            	window.update();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, initialDelay, period, unit);
    }
    
}
