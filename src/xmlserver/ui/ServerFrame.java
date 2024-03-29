/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlserver.ui;

/**
 *
 * @author Duru Dumebi Julian
 */

import java.awt.Dimension;

import java.util.Date;
import java.util.Observer;
import java.util.Observable;

import code.concurrent.ThreadExecutor;
import java.awt.event.WindowEvent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import xmlserver.net.Server;

public class ServerFrame extends javax.swing.JFrame implements Observer{

    /**
     * Creates new form ServerFrame
     */
    public ServerFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(700, 550));
        
        disconnectButton.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();
        connectButton = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();
        bottomPanel = new javax.swing.JPanel();
        timeLabel = new javax.swing.JLabel();
        desktopPane = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("DB Server");
        setPreferredSize(new java.awt.Dimension(900, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(250, 488));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Server Log");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        logArea.setEditable(false);
        logArea.setColumns(10);
        logArea.setLineWrap(true);
        logArea.setRows(5);
        logArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(logArea);

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        disconnectButton.setText("Disconnect");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(disconnectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(connectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(connectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(disconnectButton)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_END);

        bottomPanel.setPreferredSize(new java.awt.Dimension(778, 25));

        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomPanelLayout.createSequentialGroup()
                .addContainerGap(751, Short.MAX_VALUE)
                .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(timeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        getContentPane().add(bottomPanel, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(desktopPane, java.awt.BorderLayout.CENTER);

        fileMenu.setText("File");

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectButtonActionPerformed
        // TODO add your handling code here:
        ThreadExecutor.run(new Runnable(){
            @Override
            public void run(){
                try{
                    Server.stopServer();
                    
                    connectButton.setEnabled(true);
                    disconnectButton.setEnabled(false);
                }
                catch(Exception ex){
                    appendMessage("An error occured while attempting to stop server. \nERROR: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }//GEN-LAST:event_disconnectButtonActionPerformed

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        // TODO add your handling code here:
        final int[] details = PortDetailsDialog.newDialog(this);
        if (details != null){
            ThreadExecutor.run(new Runnable(){
                @Override
                public void run(){
                    try{
                        Server.runServer(details[0], details[1], ServerFrame.this);
                        Server server = Server.getInstance();
                        server.addObserver(ServerFrame.this);

                        disconnectButton.setEnabled(true);
                        connectButton.setEnabled(false);
                    }
                    catch(Exception ex){
                        appendMessage("Unable to initialize server. \nERROR: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            });
        }
    }//GEN-LAST:event_connectButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION) 
                == JOptionPane.YES_OPTION){
            setVisible(false);
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
        formWindowClosing(new WindowEvent(this, evt.getID()));
    }//GEN-LAST:event_exitMenuItemActionPerformed

    public void addClientConnectionFrame(ClientConnectionFrame f){
        ClientConnectionFrame cf = getSelectedFrame();
        if (cf != null){
            int x = cf.getX();
            int y = cf.getY();
            f.setLocation(x + 20, y + 20);
        }
        
        desktopPane.add(f);
        f.setVisible(true);
        
        desktopPane.setSelectedFrame(f);
    }
    
    public ClientConnectionFrame getSelectedFrame(){
        JInternalFrame jif = desktopPane.getSelectedFrame();
        if(jif instanceof ClientConnectionFrame)
            return (ClientConnectionFrame) desktopPane.getSelectedFrame();
        else
            return null;
    }
       
    @Override
    public void update(Observable o, Object arg){
        appendMessage((String)arg);
    }
    
    private void appendMessage(String msg){
        logArea.append(msg + "\n" + new Date(System.currentTimeMillis()) + "\n\n");
        logArea.setCaretPosition(logArea.getText().length());
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JButton connectButton;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logArea;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
