import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class TurtleShower extends Panel implements  DocumentListener {
	
	private static final long serialVersionUID = 1;

	static final Dimension WINDOW_SIZE = new Dimension(1150, 1150);
	
	List<Line2D> board;
	final BoardMaker boardMaker;
	
	public TurtleShower() {
		boardMaker = new BoardMaker();
	}

	static public void main(String args[]) throws Exception {
		JFrame frame = new JFrame("Display image");
		JPanel panel = new JPanel();
		TurtleShower image = new TurtleShower();
		image.setPreferredSize(WINDOW_SIZE);
		JScrollPane textArea = makeTextArea(image);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(image);
		makeRight(panel, textArea);
		frame.setSize(WINDOW_SIZE);
		frame.addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent we){
	            System.exit(0);
	        }});
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	static void makeRight(JPanel panel,JComponent textArea) {
		JLabel label = new JLabel("Your program:");
		label.setPreferredSize(new Dimension(150, 20));
		JPanel right = new JPanel();
		textArea.setPreferredSize(new Dimension(150,500));
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.add(label);
		right.add(textArea);
		panel.add(right);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                  RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.white);
		g2d.setStroke(new BasicStroke(4));
		g.fillRect(0, 0, WINDOW_SIZE.width, WINDOW_SIZE.width);
		g2d.setTransform(AffineTransform.getTranslateInstance(7, 7));
		if(board == null) 
			return;
		g2d.setColor(Color.red);
		for(Line2D line : board) {
			g2d.draw(line);
		}
	}
	
	static JScrollPane makeTextArea(TurtleShower image) {
		JTextArea textArea = new JTextArea();
		textArea.getDocument().addDocumentListener(image);
		textArea.setVisible(true);
		JScrollPane areaScrollPane = new JScrollPane(textArea);
		areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
		return areaScrollPane;
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		changed(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changed(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changed(e);
	}
	
	void changed(DocumentEvent de) {
		String description;
		Document document = de.getDocument();
		try {
			description = document.getText(0, document.getLength());
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
	
		board = boardMaker.makeBoardFrom(description);

		this.repaint();
	}
}
