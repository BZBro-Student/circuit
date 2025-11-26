import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * 
 * Creates a GUI meant to display the best paths found by the algorithm ran in
 * CircuitTracer
 * 
 * @author Broden
 */
public class CircuitTracerGUI extends JFrame {
    private JPanel currGridPanel;

    public CircuitTracerGUI(CircuitBoard currBoard, ArrayList<TraceState> bestPaths) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        // Sets up original panel
        int rows = currBoard.numRows();
        int cols = currBoard.numCols();
        currGridPanel = GridPanelMaker(currBoard, rows, cols);
        this.add(currGridPanel, BorderLayout.CENTER);
        // Sets up solutions panel
        JPanel solutionPanel = new JPanel();
        solutionPanel.setLayout(new BoxLayout(solutionPanel, BoxLayout.Y_AXIS));
        int i = 1;
        for (TraceState paths : bestPaths) {
            JButton solButton = new JButton("Solution " + i);
            solButton.setBorderPainted(false);
            solButton.setContentAreaFilled(false);
            // Add listener to each button that will cause the GridPanel to update to
            // reflect the chosen solution
            solButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CircuitTracerGUI.this.remove(currGridPanel);
                    currGridPanel = GridPanelMaker(paths, rows, cols);
                    CircuitTracerGUI.this.add(currGridPanel, BorderLayout.CENTER);
                    CircuitTracerGUI.this.revalidate();
                    CircuitTracerGUI.this.repaint();

                }
            });
            solutionPanel.add(solButton);
            i++;
        }
        // Button to return to the original state
        JButton originalState = new JButton("Original");
        originalState.setBorderPainted(false);
        originalState.setContentAreaFilled(false);
        originalState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent z) {
                CircuitTracerGUI.this.remove(currGridPanel);
                currGridPanel = GridPanelMaker(currBoard, rows, cols);
                CircuitTracerGUI.this.add(currGridPanel, BorderLayout.CENTER);
                CircuitTracerGUI.this.revalidate();
                CircuitTracerGUI.this.repaint();
            }
        });
        solutionPanel.add(originalState);
        // Visual Modification
        solutionPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        this.add(solutionPanel, BorderLayout.WEST);
        // Creates the beginning state of the frame
        this.pack();
        this.setSize(800, 600);
        this.setVisible(true);
    }

    /**
     * Converts currBoard into a JPanel representation of the board, this panel
     * is composed of multiple JLabels
     * 
     * @param currBoard Board that is being converted to GUI
     * @param rows      rows in the board being converted to GUI
     * @param cols      columns in the board being converted to GUI
     * @return JPanel containing JLabels to reflect the input board
     */
    private JPanel GridPanelMaker(CircuitBoard currBoard, int rows, int cols) {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Font labelFont = new Font("Arial", Font.BOLD, 24);
                char currChar = currBoard.charAt(i, j);
                JLabel currLabel = new JLabel(String.valueOf(currChar), SwingConstants.CENTER);
                currLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                currLabel.setFont(labelFont);
                if (currChar == 'T') {
                    currLabel.setBackground(Color.GREEN);
                    currLabel.setOpaque(true);
                }
                gridPanel.add(currLabel);
            }
        }
        return gridPanel;
    }

    /**
     * Converts TraceState into a board and then converts the board into
     * a JPanel representation compaosed of multiple JLabels
     * 
     * @param bestState StateTrace that is being converted to GUI
     * @param rows      rows in the StateTrace being converted to GUI
     * @param cols      columns in the board being converted to GUI
     * @return JPanel containing JLabels to reflect the input board
     */
    private JPanel GridPanelMaker(TraceState bestState, int rows, int cols) {
        CircuitBoard stateBoard = bestState.getBoard();
        return GridPanelMaker(stateBoard, rows, cols);
    }
}
