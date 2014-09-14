/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlserver.ui;

/**
 *
 * @author Duru Dumebi Julian
 */

import java.io.IOException;

import java.util.Date;
import java.util.Observer;
import java.util.Observable;

import javax.swing.JOptionPane;

import xmlserver.net.ClientConnection;

public class ClientConnectionFrame extends javax.swing.JInternalFrame implements Observer{
    
    private ClientConnection connection;
    
    public ClientConnectionFrame(ClientConnection c) {
        setTitle("User" + c.getID() + "@" + c.getAddress());
        initComponents();
        
        connection = c;
    }
    
    @Override
    public void update(Observable o, Object arg){
        String str = (String)arg;
        
        if (str.equals("CLIENT_DISCONNECTED"))
            showDisconnected();
        else
            appendMessage(str);
    }
    
    private void showDisconnected(){
        appendMessage("LOG: Client has been disconnected from the server");
        disconnectButton.setEnabled(false);
    }
    
    private void appendMessage(String msg){
        logArea.append(msg + "\n" + new Date(System.currentTimeMillis()) + "\n\n");
        logArea.setCaretPosition(logArea.getText().length());
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        disconnectButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        disconnectButton.setText("Disconnect");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        logArea.setEditable(false);
        logArea.setColumns(20);
        logArea.setLineWrap(true);
        logArea.setRows(5);
        logArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(logArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(disconnectButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(disconnectButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectButtonActionPerformed
        doDisconnect();
    }//GEN-LAST:event_disconnectButtonActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        doDisconnect();
    }//GEN-LAST:event_formInternalFrameClosing

    private void doDisconnect(){        
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to disconnect this client?", "Confirm", 
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){            
            try { 
                connection.closeConnection();
                this.dispose();
            } catch (IOException ex) {
                appendMessage("ERROR: An error occured while attempting to close the client connection");
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton disconnectButton;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea logArea;
    // End of variables declaration//GEN-END:variables
}
