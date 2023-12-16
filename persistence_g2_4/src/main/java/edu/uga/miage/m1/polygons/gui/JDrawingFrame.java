package edu.uga.miage.m1.polygons.gui;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import edu.uga.miage.m1.g2_4.polygons.metier.persistence.ShapeJsonReader;
import edu.uga.miage.m1.g2_4.polygons.metier.persistence.ShapeJsonWriter;
import edu.uga.miage.m1.g2_4.polygons.metier.persistence.ShapeXmlReader;
import edu.uga.miage.m1.g2_4.polygons.metier.persistence.ShapeXmlWriter;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeFactory;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.ShapeType;
import edu.uga.miage.m1.g2_4.polygons.metier.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.command.AddShapeCommand;
import edu.uga.miage.m1.polygons.gui.command.Command;
import edu.uga.miage.m1.polygons.gui.command.MoveShapeCommand;

/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 * 
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public class JDrawingFrame extends JFrame
        implements MouseListener, MouseMotionListener {

    private Point mouseLastPosition;
    private static final long serialVersionUID = 1L;
    private JToolBar toolBar;
    private ShapeType shapeItemSelectedInToolBar;
    private JDrawingPanel jDrawingPanel;
    private JLabel label;
    private JMenuBar mMenuBar;
    private JMenu importMenu;
    private JMenu exportMenu;
    private JMenuItem importFromXmlMenuItem;
    private JMenuItem importFromJsonMenuItem;
    private JMenuItem exportToXmlMenuItem;
    private JMenuItem exportToJsonMenuItem;
    private transient ActionListener shapeActionListener = new ShapeActionListener();
    private transient ActionListener exportToJsonActionListener = new ExportToJsonActionListener("json");
    private transient ActionListener exportToXmlActionListener = new ExportToXmlActionListener("xml");
    private transient ActionListener importFromJsonActionListener = new ImportFromJsonActionListener("json");
    private transient ActionListener importFromXmlActionListener = new ImportFromXmlActionListener("xml");
    private transient ShapeJsonReader shapeJsonReader=new ShapeJsonReader();
    private transient ShapeJsonWriter shapeJsonWriter=new ShapeJsonWriter();
    private transient ShapeXmlWriter shapeXmlWriter=new ShapeXmlWriter();
    private transient ShapeXmlReader shapeXmlReader=new ShapeXmlReader();
    FileDialog fd;
    private transient ShapeFactory shapeFactory=ShapeFactory.getShapeFactory();
    private transient Logger logger = Logger.getLogger(JDrawingFrame.class.getName());

    /**
     * Tracks buttons to manage the background.
     */
    private EnumMap<ShapeType, JButton> buttons = new EnumMap<>(ShapeType.class);


    private transient DrawTool drawTool = new DrawTool();

    /**
     * Default constructor that populates the main window.
     * 
     * @param frameName
     **/
    public JDrawingFrame(String frameName) {
        super(frameName);
        mouseLastPosition = new Point();
        // Instantiates components
        mMenuBar = new JMenuBar();
        toolBar = new JToolBar("Toolbar");
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));

        importMenu = new JMenu("Importer");
        exportMenu = new JMenu("Exporter");

        importFromXmlMenuItem = new JMenuItem("Depuis XML");
        importFromJsonMenuItem = new JMenuItem("Depuis de JSON");

        importFromJsonMenuItem.addActionListener(importFromJsonActionListener);
        importFromXmlMenuItem.addActionListener(importFromXmlActionListener);

        exportToXmlMenuItem = new JMenuItem("Vers XML");
        exportToJsonMenuItem = new JMenuItem("Vers JSON");

        exportToJsonMenuItem.addActionListener(exportToJsonActionListener);
        exportToXmlMenuItem.addActionListener(exportToXmlActionListener);

        importMenu.add(importFromXmlMenuItem);
        importMenu.add(importFromJsonMenuItem);
        exportMenu.add(exportToXmlMenuItem);
        exportMenu.add(exportToJsonMenuItem);

        mMenuBar.add(importMenu);
        mMenuBar.add(exportMenu);

        setJMenuBar(mMenuBar);
        setPreferredSize(new Dimension(600, 600));

        jDrawingPanel = new JDrawingPanel();
        jDrawingPanel.setBackground(Color.WHITE);
        jDrawingPanel.setLayout(null);
        jDrawingPanel.setMinimumSize(new Dimension(400, 400));
        jDrawingPanel.addMouseListener(this);
        jDrawingPanel.addMouseMotionListener(this);
        label = new JLabel(" ", SwingConstants.LEFT);

        // Fills the panel
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(jDrawingPanel, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);

        // Add shapes in the menu
        addShape(ShapeType.SQUARE, new ImageIcon(getClass().getResource("images/square.png")));
        addShape(ShapeType.TRIANGLE, new ImageIcon(getClass().getResource("images/triangle.png")));
        addShape(ShapeType.CIRCLE, new ImageIcon(getClass().getResource("images/circle.png")));
        addShape(ShapeType.CUBE, new ImageIcon(getClass().getResource("images/underc.png")));

        // Add a focus listener to the frame to know when you are in the frame
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                requestFocusInWindow();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
                    drawTool.undoCommand();
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) {
                    drawTool.redoCommand();
                }
            }
        });

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        setPreferredSize(new Dimension(500, 500));
    }

    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     * 
     * @param name The name of the injected <tt>SimpleShape</tt>.
     * @param icon The icon associated with the injected <tt>SimpleShape</tt>.
     **/
    private void addShape(ShapeType shape, ImageIcon icon) {
        JButton shapeButton = new JButton(icon);
        shapeButton.setBorderPainted(false);
        buttons.put(shape, shapeButton);
        shapeButton.setActionCommand(shape.toString());
        shapeButton.addActionListener(shapeActionListener);

        if (shapeItemSelectedInToolBar == null) {
            shapeButton.doClick();
        }

        toolBar.add(shapeButton);
        toolBar.validate();
        repaint();
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to
     * draw the selected shape into the drawing canvas.
     * 
     * @param evt The associated mouse event.
     **/
    public void mouseClicked(MouseEvent evt) {
        // Do nothing because this operation is already handled via mousePressed and
        // mouseReleased
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     * 
     * @param evt The associated mouse event.
     **/
    public void mouseEntered(MouseEvent evt) {
        // Do nothing because this operation operation not supported
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     * 
     * @param evt The associated mouse event.
     **/
    public void mouseExited(MouseEvent evt) {
        label.setText(" ");
        label.repaint();
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to initiate
     * shape dragging.
     * 
     * @param evt The associated mouse event.
     **/
    public void mousePressed(MouseEvent evt) {
        // Do nothing because this operation operation not supported
        mouseLastPosition.setLocation(evt.getX(), evt.getY());
        if (jDrawingPanel.cursorOnJDrawingPanel(evt.getX(), evt.getY())
                && jDrawingPanel.isCursorOnAShape(evt.getX(), evt.getY())) {
            jDrawingPanel.selectShape(evt.getX(), evt.getY());

        }
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     * 
     * @param evt The associated mouse event.
     **/
    public void mouseReleased(MouseEvent evt) {
        jDrawingPanel.isCursorOnAShape((int) mouseLastPosition.getX(), (int) mouseLastPosition.getY());
        SimpleShape selectedShape = jDrawingPanel.getSelectedShape();
        if (mouseLastPosition.getX() == evt.getX() && mouseLastPosition.getY() == evt.getY()) {
            AddShapeCommand addShapeCommand = new AddShapeCommand(jDrawingPanel, shapeFactory.createShape(shapeItemSelectedInToolBar,
                    evt.getX(),
                    evt.getY()));
            drawTool.executeCommand(addShapeCommand);
        } else {
            if (selectedShape != null) {
                MoveShapeCommand moveShapeCommand = new MoveShapeCommand(selectedShape, jDrawingPanel);
                moveShapeCommand.setFinalPosition(evt.getX(), evt.getY());
                drawTool.executeCommand(moveShapeCommand);
            }
        }
        jDrawingPanel.unSelectShape();
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     * 
     * @param evt The associated mouse event.
     **/
    public void mouseDragged(MouseEvent evt) {
        // Do nothing because this operation is handeled via mousePressed and
        // mouseReleased
    }

    /**
     * Implements an empty method for the <tt>MouseMotionListener</tt>
     * interface.
     * 
     * @param evt The associated mouse event.
     **/
    public void mouseMoved(MouseEvent evt) {
        modifyLabel(evt);
    }

    private void modifyLabel(MouseEvent evt) {
        label.setText("(" + evt.getX() + "," + evt.getY() + ")");
    }

    /**
     * Simple action listener for shape tool bar buttons that sets
     * the drawing frame's currently selected shape when receiving
     * an action event.
     **/
    private class ShapeActionListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            // Itè¨¨re sur tous les boutons
            Iterator<ShapeType> keys = buttons.keySet().iterator();
            while (keys.hasNext()) {
                ShapeType shape = keys.next();
                JButton btn = buttons.get(shape);
                if (evt.getActionCommand().equals(shape.toString())) {
                    btn.setBorderPainted(true);
                    shapeItemSelectedInToolBar = shape;
                } else {
                    btn.setBorderPainted(false);
                }
                btn.repaint();
            }
        }
    }

    private abstract class ExportActionListener implements ActionListener {
        private String fileType;

        ExportActionListener(String fileType) {
            this.fileType = fileType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<SimpleShape> listOfShapeToPersist=jDrawingPanel.getShapesOnPanel();
            if (!listOfShapeToPersist.isEmpty()) {
                String defaultDirectory = "./src/main/resources/edu/uga/miage/m1/persistence_data";

            // Check if the default directory exists
            File directory = new File(defaultDirectory);
            if (!directory.exists() || !directory.isDirectory()) {
                // Directory does not exist or is not a directory, handle accordingly
                // You might want to choose a fallback directory or prompt the user to select
                // one
                // For simplicity, we'll use the user's home directory as a fallback
                defaultDirectory = System.getProperty("user.home");
            }
            String defaultFileName = "export-"
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS")) + "."
                    + fileType;
            fd = new FileDialog(JDrawingFrame.this, "Enregistrer un fichier", FileDialog.SAVE);
            fd.setDirectory(defaultDirectory);
            fd.setFile(defaultFileName);
            fd.setVisible(true);

            String filename = fd.getFile();
            String path = fd.getDirectory() + filename;

            Path pathTofile = Paths.get(path);

            if (filename == null) {
                JOptionPane.showMessageDialog(jDrawingPanel, "Vous n'avez rien sauvegardé©©");
            } else {
                persistShapeToFile(pathTofile.toFile(),listOfShapeToPersist);
                JOptionPane.showMessageDialog(jDrawingPanel, "Sauvegarde effectuee.");
            }
        } else {
                JOptionPane.showMessageDialog(jDrawingPanel,
                        "Aucune forme disponible a exporter. Pensez a en dessiner des maintenant.");
            }
        }

        public abstract void persistShapeToFile(File destinationFile,List<SimpleShape>listOfShapeToPersist) ;
    }

    private class ExportToJsonActionListener extends ExportActionListener {

        ExportToJsonActionListener(String fileType) {
            super(fileType);
        }

        @Override
        public void persistShapeToFile(File destinationFile,List<SimpleShape>listOfShapeToPersist)
        {
            try {
                shapeJsonWriter.saveShapesToFile(destinationFile, listOfShapeToPersist);
            } catch (IOException e) {
                logger.log(Level.INFO, "Echec tentative de sauvegarde des shapes dans un fichier json", e);
            }
        }
    }

    private class ExportToXmlActionListener extends ExportActionListener {

        ExportToXmlActionListener(String fileType) {
            super(fileType);
        }

        @Override
        public void persistShapeToFile(File destinationFile,List<SimpleShape> listOfShapeToPersist){
            try {
                shapeXmlWriter.saveShapesToFile(destinationFile, listOfShapeToPersist);
            } catch (IOException e) {
                logger.log(Level.INFO, "Echec tentative de sauvegarde des shapes dans un fichier xml", e);
            }
        }
    }

    private abstract class ImportActionListener implements ActionListener {
        private String fileType;

        ImportActionListener(String fileType) {
            this.fileType = fileType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            retrievePersistedData(fileType);
        }

        public void retrievePersistedData(String fileType) {
            fd = new FileDialog(JDrawingFrame.this, "Choisir un fichier",
                    FileDialog.LOAD);
            String filename;
            String path;
            fd.setDirectory("./src/main/resources/edu/uga/miage/m1/persistence_data");
            fd.setFile("*." + fileType);
            fd.setVisible(true);
            filename = fd.getFile();
            path = fd.getDirectory() + filename;
            Path pathToDataFile = Paths.get(path);
            if (filename == null) {
                JOptionPane.showMessageDialog(jDrawingPanel, "Tentative d'import abandonné©©");
            } else {
                List<SimpleShape> listOfPersistedShape=getPersistedShape(pathToDataFile.toFile());
                List<Command> commandsForReplayData = convertListOfShapeToAddShapeCommand(listOfPersistedShape);
                if (commandsForReplayData.isEmpty()) {
                    JOptionPane.showMessageDialog(jDrawingPanel,
                            "Echec dans l'import.Aucune donnée vialable n'a pu etre récupéré");
                }
                for(Command addShapeCommand:commandsForReplayData){
                    drawTool.executeCommand(addShapeCommand);
                }
            }
        }

        public List<Command> convertListOfShapeToAddShapeCommand(List<SimpleShape> listOfShape){
            List<Command> listOfCommands=new ArrayList<>();
            for(SimpleShape simpleShape : listOfShape){
                Command addShapeCommand=new AddShapeCommand(jDrawingPanel, simpleShape); 
                listOfCommands.add(addShapeCommand); 
            }
            return listOfCommands; 
        }

        public abstract List<SimpleShape> getPersistedShape(File sourceFile);
    }

    private class ImportFromJsonActionListener extends ImportActionListener {

        ImportFromJsonActionListener(String fileType) {
            super(fileType);
        }

        @Override
        public  List<SimpleShape> getPersistedShape(File sourceFile){
            return shapeJsonReader.uploadShapesFromFile(sourceFile);
        }
    }

    private class ImportFromXmlActionListener extends ImportActionListener {

        ImportFromXmlActionListener(String fileType) {
            super(fileType);
        }

        @Override
        public  List<SimpleShape> getPersistedShape(File sourceFile){
            return shapeXmlReader.uploadShapesFromFile(sourceFile);
        }

    }

    public Map<ShapeType, JButton> getButtons() {
        return buttons;
    }

    public JDrawingPanel getJDrawingPanel() {
        return jDrawingPanel;
    }
}