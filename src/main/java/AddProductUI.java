//package edu.auburn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductUI {

    public JFrame view;

    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtProductID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtPrice = new JTextField(20);
    public JTextField txtTax = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);
    public JTextField txtVendor = new JTextField(20);
    public JTextField txtDescription = new JTextField(20);


    public AddProductUI()   {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Add Product");
        view.setSize(350, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        String[] labels = {"Product ID ", "Name ", "Price ", "Tax Rate", "Quantity ", "Vendor ", "Description "};
        JTextField[] textFields = {txtProductID, txtName, txtPrice, txtTax, txtQuantity, txtVendor, txtDescription};

        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i]);
            JPanel p = new JPanel(new FlowLayout(FlowLayout.TRAILING, 5, 5));
            p.add(l);
            JTextField field = textFields[i];
            l.setLabelFor(field);
            p.add(field);
            view.getContentPane().add(p);
        }


        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnCancel);
        view.getContentPane().add(panelButtons);

        btnAdd.addActionListener(new AddButtonListener());

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                view.dispose();
            }
        });

    }

    public void run() {
        view.setVisible(true);
    }

    class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();

            String id = txtProductID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "Product ID cannot be null!");
                return;
            }

            try {
                product.mProductID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Product ID is invalid!");
                return;
            }

            String name = txtName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Product name cannot be empty!");
                return;
            }

            product.mName = name;

            String price = txtPrice.getText();
            try {
                product.mPrice = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Price is invalid!");
                return;
            }

            String tax = txtTax.getText();
            try {
                product.mTaxRate = Double.parseDouble(tax);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Tax is invalid!");
                return;
            }

            String quant = txtQuantity.getText();
            try {
                product.mQuantity = Double.parseDouble(quant);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is invalid!");
                return;
            }

            String vendor = txtVendor.getText();
            if (vendor.length() > 0) {
                product.mVendor = vendor;
            }
            else {
                product.mVendor = "";
            }

            String description = txtDescription.getText();
            if (description.length() > 0) {
                product.mDescription = description;
            }
            else {
                product.mDescription = "";
            }

            switch (StoreManager.getInstance().getDataAdapter().saveProduct(product)) {
                case SQLiteDataAdapter.PRODUCT_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "Product NOT added successfully! Duplicate product ID!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Product added successfully! " + product);
                    view.dispose();
            }
        }
    }

}