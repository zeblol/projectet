package Presentation;

import Domain.Controller;
import Domain.Customer;
import Domain.Employee;
import Domain.Installer;
import Domain.Order;
import Domain.OrderDetail;
import Domain.Product;
import Domain.User;
import Domain.Vehicle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Riboe
 */
public class MainFrame extends javax.swing.JFrame {

    private ArrayList<OrderDetail> newOrderDetails;
    private Employee selectedInstaller;
    private ArrayList<Installer> addedInstallers;
    private Vehicle currentVehicle;
    private Order opdaterSelectedOrder;
    private BigDecimal tentativeTotal;
    private ArrayList<Vehicle> vMod; // Omdøb til ordreAddedVehicles
    private Controller control;
    private Order currentOrder;
    private OrderDetail ODSelectedOrderDetail; // Omdøb til ordreSelectedOrderDetail
    private Product selectedProduct;
    private HashMap<Integer, Integer> mapProducts;
    private ArrayList<Product> products; // Omdøb til opretProdukter
    private Installer INSelectedInstaller; // Omdøb til ordreSelectedInstaller
    private Vehicle vSelectedVehicle; // Omdøb til ordreSelectedVehicle
    private DefaultListModel modelOrdreProdukter;
    private ArrayList<OrderDetail> opdaterOrderDetail; // Omdøb til ordreDirtyDetails
    private ArrayList<Installer> ordreMontoerer;
    private DefaultListModel modelOrdreMontoerer;
    private DefaultListModel modelOrdreLastvogne;
    private ArrayList<Vehicle> ordreLastvogne;
    private ArrayList<Employee> montoerer; // Omdøb til opretMontoerer
    private Customer currentCustomer;
    private DefaultListModel modelKundeOrdrer;
    private String status;
    private ArrayList<OrderDetail> deletedOrderDetail; // Omdøb til ordreRemovedDetails
    private ArrayList<Installer> deletedInstaller; // Omdøb til ordreRemovedInstallers
    private ArrayList<Vehicle> deletedVehicle; // Omdøb til ordreRemovedVehicles
    private boolean orderChanged;
    private DefaultListModel modelOpretProdukter;
    private DefaultListModel modelOpretMontoerer;
    private DefaultListModel modelOpretLastvogne;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();

        initializeVariables();
        setupGUI();
        setupElements();
    }

    private void setupElements() {
        listOpretProdukter.setModel(modelOpretProdukter);
        ListSelectionListener lsl = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (listOpretProdukter.getSelectedValue() != null) {
                    selectedProduct = (Product) listOpretProdukter.getSelectedValue();
                    labelOpretProdukt.setText(selectedProduct.getpID() + "");
                    labelOpretPris.setText(selectedProduct.getPris() + "");
                    labelOpretODLedige.setText(selectedProduct.getAntalTotal() - mapProducts.get(selectedProduct.getpID()) + "");
                }
            }
        };
        ListSelectionModel listSelectionModel = listOpretProdukter.getSelectionModel();
        listSelectionModel.addListSelectionListener(lsl);

        listOpretMontoerer.setModel(modelOpretMontoerer);
        ListSelectionListener lslInstallers = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (listOpretMontoerer.getSelectedValue() != null) {
                    selectedInstaller = (Employee) listOpretMontoerer.getSelectedValue();
                }
            }
        };
        ListSelectionModel listSelectionModelInstallers = listOpretMontoerer.getSelectionModel();
        listSelectionModelInstallers.addListSelectionListener(lslInstallers);

        listOpretLastvogne.setModel(modelOpretLastvogne);
        ListSelectionListener lslVehicles = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (listOpretLastvogne.getSelectedValue() != null) {
                    currentVehicle = (Vehicle) listOpretLastvogne.getSelectedValue();
                }
            }
        };
        ListSelectionModel listSelectionModelVehicles = listOpretLastvogne.getSelectionModel();
        listSelectionModelVehicles.addListSelectionListener(lslVehicles);

        listOrdreOrdredetaljer.setModel(modelOrdreProdukter);
        ListSelectionListener lslOrdreProdukter = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (listOrdreOrdredetaljer.getSelectedValue() != null) {
                    ODSelectedOrderDetail = (OrderDetail) listOrdreOrdredetaljer.getSelectedValue();
                    labelOrdreProduktID.setText(ODSelectedOrderDetail.getpID() + "");
                    tfOrdreMaengde.setText(ODSelectedOrderDetail.getAmount() + "");
                }
            }
        };
        ListSelectionModel listSelectionModelOrdreProdukter = listOrdreOrdredetaljer.getSelectionModel();
        listSelectionModelOrdreProdukter.addListSelectionListener(lslOrdreProdukter);

        listOrdreMontoerer.setModel(modelOrdreMontoerer);
        ListSelectionListener lslOrdreMontoerer = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (listOrdreMontoerer.getSelectedValue() != null) {
                    INSelectedInstaller = (Installer) listOrdreMontoerer.getSelectedValue();
                }
            }
        };
        ListSelectionModel listSelectionModelOrdreMontoerer = listOrdreMontoerer.getSelectionModel();
        listSelectionModelOrdreMontoerer.addListSelectionListener(lslOrdreMontoerer);

        listOrdreLastvogne.setModel(modelOrdreLastvogne);
        ListSelectionListener lslOrdreLastvogne = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (listOrdreLastvogne.getSelectedValue() != null) {
                    vSelectedVehicle = (Vehicle) listOrdreLastvogne.getSelectedValue();
                }
            }
        };
        ListSelectionModel listSelectionModelOrdreLastvogne = listOrdreLastvogne.getSelectionModel();
        listSelectionModelOrdreLastvogne.addListSelectionListener(lslOrdreLastvogne);


        listKundeOrdrer.setModel(modelKundeOrdrer);
        ListSelectionListener lslKundeOrdrer = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (listKundeOrdrer.getSelectedValue() != null) {
                    currentOrder = (Order) listKundeOrdrer.getSelectedValue();
                    opdaterSelectedOrder = currentOrder;
                    modelOrdreLastvogne.clear();
                    modelOrdreProdukter.clear();
                    modelOrdreMontoerer.clear();
                    setOrdreFields();
                }
            }
        };
        ListSelectionModel listSelectionModelKundeOrdrer = listKundeOrdrer.getSelectionModel();
        listSelectionModelKundeOrdrer.addListSelectionListener(lslKundeOrdrer);
    }

    private void initializeVariables() {
        control = Controller.getInstance();
        newOrderDetails = new ArrayList();
        modelOpretLastvogne = new DefaultListModel();
        modelOpretMontoerer = new DefaultListModel();
        modelOpretProdukter = new DefaultListModel();
        products = control.getProducts();
        montoerer = new ArrayList();
        addedInstallers = new ArrayList();
        vSelectedVehicle = null;
        opdaterSelectedOrder = null;
        ODSelectedOrderDetail = null;
        INSelectedInstaller = null;
        deletedVehicle = new ArrayList();
        opdaterOrderDetail = new ArrayList();
        deletedOrderDetail = new ArrayList();
        deletedInstaller = new ArrayList();
        vMod = new ArrayList();
        currentOrder = null;
        modelOrdreProdukter = new DefaultListModel();
        ordreMontoerer = new ArrayList();
        modelOrdreMontoerer = new DefaultListModel();
        modelOrdreLastvogne = new DefaultListModel();
        ordreLastvogne = new ArrayList();
        currentCustomer = null;
        modelKundeOrdrer = new DefaultListModel();
        status = null;
        orderChanged = false;
    }

    private void setupGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        setResizable(false);
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        setLocation((screenWidth - getWidth()) / 2, (screenHeight - getHeight()) / 2);
        if (control.getUserLevel() == User.USER_LEVEL_NORMAL) {
            menuLager.setEnabled(false);
            menuBrugere.setEnabled(false);
        }
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void showMessage(String s) {
        JOptionPane.showMessageDialog(this, s);
    }

    private void setStatus(String s) {
        status = s;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                labelStatus.setText(status);
            }
        });
        t.start();
    }

    private void clearOrdreChanges() {
        opdaterSelectedOrder = null;
        opdaterOrderDetail.clear();
        deletedOrderDetail.clear();
        deletedInstaller.clear();
        deletedVehicle.clear();
        orderChanged = false;
    }

    private void clearOpretFields() {
        tfOpretFra.setText(null);
        tfOpretFraKlokken.setText(null);
        tfOpretMaengde.setText(null);
        tfOpretTil.setText(null);
        tfOpretTilKlokken.setText(null);
    }

    private void clearOrdreFields() {
        tfOrdreFra.setText(null);
        tfOrdreFraKlokken.setText(null);
        tfOrdreMaengde.setText(null);
        tfOrdreOrdrenr.setText(null);
        tfOrdreRabat.setText(null);
        tfOrdreTil.setText(null);
        tfOrdreTilKlokken.setText(null);
        cbOrdreDepositum.setSelected(false);
        labelOrdreDepositum.setText(null);
        labelOrdreTotalPris.setText(null);
        labelOrdreProduktID.setText(null);
        modelOrdreLastvogne.clear();
        modelOrdreMontoerer.clear();
        modelOrdreProdukter.clear();
    }

    private void setOrdreFields() {
        cbOrdreDepositum.setSelected(currentOrder.isDepositPaid());
        tfOrdreOrdrenr.setText(currentOrder.getOID() + "");
        tfOrdreFra.setText(currentOrder.getFromDate().toString());
        tfOrdreRabat.setText(currentOrder.getDiscount() + "");
        tfOrdreTil.setText(currentOrder.getToDate().toString());
        labelOrdreDepositum.setText(control.calcDeposit(currentOrder, 33) + "");
        labelOrdreTotalPris.setText(control.calcTotal(currentOrder) + "");
        opdaterOrderDetail = currentOrder.getOrderDetails();
        for (int i = 0; i < opdaterOrderDetail.size(); i++) {
            modelOrdreProdukter.addElement(opdaterOrderDetail.get(i));
        }
        ordreMontoerer = currentOrder.getInstallers();
        for (int i = 0; i < ordreMontoerer.size(); i++) {
            modelOrdreMontoerer.addElement(ordreMontoerer.get(i));
        }
        ordreLastvogne = currentOrder.getVehicles();
        for (int i = 0; i < ordreLastvogne.size(); i++) {
            modelOrdreLastvogne.addElement(ordreLastvogne.get(i));
        }
        if (!ordreMontoerer.isEmpty()) {
            tfOrdreFraKlokken.setText(control.cropTime(ordreMontoerer.get(0).getFrom()));
            tfOrdreTilKlokken.setText(control.cropTime(ordreMontoerer.get(0).getTo()));
        }
    }

    private void clearCustomerFields() {
        tfKundeEmail.setText(null);
        tfKundeBy.setText(null);
        tfKundeTelefon.setText(null);
        tfKundeKundenr.setText(null);
        tfKundeKundeNavn.setText(null);
        tfKundePostnr.setText(null);
        tfKundeBy.setText(null);
        tfKundeAddresse.setText(null);
        modelKundeOrdrer.clear();
    }

    private void setCustomerFields() {
        tfKundeEmail.setText(currentCustomer.getcEmail());
        tfKundeKundenr.setText("" + currentCustomer.getcID());
        tfKundeTelefon.setText("" + currentCustomer.getcPhoneNumber());
        tfKundeKundeNavn.setText(currentCustomer.getcName());
        tfKundeAddresse.setText(currentCustomer.getcAddress());
        tfKundePostnr.setText("" + currentCustomer.getcZip());
        tfKundeBy.setText(control.getCity(currentCustomer.getcZip()));
        ArrayList<Order> kundeOrdrer = control.getOrders(currentCustomer);
        for (int i = 0; i < kundeOrdrer.size(); i++) {
            modelKundeOrdrer.addElement(kundeOrdrer.get(i));
        }
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
        tfKundeKundenr = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfKundeKundeNavn = new javax.swing.JTextField();
        tfKundeAddresse = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfKundeEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfKundeTelefon = new javax.swing.JTextField();
        buttonKundeSoeg = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        tfKundePostnr = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tfKundeBy = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listKundeOrdrer = new javax.swing.JList();
        buttonKundeOpret = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        tfOrdreOrdrenr = new javax.swing.JTextField();
        buttonOrdreSoeg = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        tfOrdreFra = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tfOrdreTil = new javax.swing.JTextField();
        cbOrdreDepositum = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listOrdreOrdredetaljer = new javax.swing.JList();
        jLabel13 = new javax.swing.JLabel();
        labelOrdreProduktID = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        labelOrdreTotalPris = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        labelOrdreDepositum = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        tfOrdreRabat = new javax.swing.JTextField();
        tfOrdreMaengde = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listOrdreMontoerer = new javax.swing.JList();
        buttonOrdreSletProdukt = new javax.swing.JButton();
        buttonOrdreOpdaterDetalje = new javax.swing.JButton();
        buttonOrdreSletMontoer = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listOrdreLastvogne = new javax.swing.JList();
        buttonOrdreSletLastvogn = new javax.swing.JButton();
        tfOrdreFraKlokken = new javax.swing.JTextField();
        tfOrdreTilKlokken = new javax.swing.JTextField();
        buttonOrdreOpdaterOrdre = new javax.swing.JButton();
        buttonOrdreAnnuller = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        tfOpretFra = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        tfOpretTil = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        listOpretProdukter = new javax.swing.JList();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        labelOpretODLedige = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        labelOpretProdukt = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        labelOpretPris = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        tfOpretMaengde = new javax.swing.JTextField();
        buttonOpretTilfoejDetalje = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        listOpretMontoerer = new javax.swing.JList();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        listOpretLastvogne = new javax.swing.JList();
        buttonOpretMontoerer = new javax.swing.JButton();
        buttonOpretLastvogne = new javax.swing.JButton();
        tfOpretFraKlokken = new javax.swing.JTextField();
        tfOpretTilKlokken = new javax.swing.JTextField();
        buttonOpretOpretOrdre = new javax.swing.JButton();
        buttonOpretAnnuller = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        buttonOpretHentRessourcer = new javax.swing.JButton();
        labelStatus = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuOrdrer = new javax.swing.JMenu();
        menuItemNedskrivning = new javax.swing.JMenuItem();
        menuUdskriv = new javax.swing.JMenu();
        menuItemOrdreliste = new javax.swing.JMenuItem();
        menuItemPakkeliste = new javax.swing.JMenuItem();
        menuItemStatusLastvogne = new javax.swing.JMenuItem();
        menuItemSatusMontoerer = new javax.swing.JMenuItem();
        menuLager = new javax.swing.JMenu();
        menuItemOpdaterLager = new javax.swing.JMenuItem();
        menuBrugere = new javax.swing.JMenu();
        menuItemAdministrerBrugere = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kunde", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12))); // NOI18N

        jLabel1.setText("Kundenr.:");

        tfKundeKundenr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfKundeKundenrKeyPressed(evt);
            }
        });

        jLabel2.setText("Navn:");

        jLabel3.setText("Addresse:");

        jLabel4.setText("E-mail:");

        tfKundeEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfKundeEmailKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfKundeEmailKeyReleased(evt);
            }
        });

        jLabel5.setText("Telefon:");

        tfKundeTelefon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfKundeTelefonActionPerformed(evt);
            }
        });
        tfKundeTelefon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfKundeTelefonKeyPressed(evt);
            }
        });

        buttonKundeSoeg.setText("Søg");
        buttonKundeSoeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonKundeSoegActionPerformed(evt);
            }
        });

        jLabel6.setText("Postnr.:");

        jLabel7.setText("By:");

        jLabel8.setText("Kundens ordrer:");

        listKundeOrdrer.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(listKundeOrdrer);

        buttonKundeOpret.setText("Opret kunde");
        buttonKundeOpret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonKundeOpretActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(13, 13, 13)
                            .addComponent(tfKundeTelefon, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel4))
                            .addGap(5, 5, 5)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tfKundeKundenr)
                                .addComponent(tfKundeEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))))
                    .addComponent(buttonKundeSoeg, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfKundeAddresse)
                            .addComponent(tfKundeKundeNavn, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfKundePostnr)
                            .addComponent(tfKundeBy))))
                .addGap(80, 80, 80)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonKundeOpret, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfKundeKundenr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfKundeEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfKundeTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonKundeSoeg)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfKundeKundeNavn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfKundeAddresse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tfKundePostnr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(tfKundeBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonKundeOpret))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ordre", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12))); // NOI18N

        jLabel9.setText("Ordrenr.:");

        tfOrdreOrdrenr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfOrdreOrdrenrKeyPressed(evt);
            }
        });

        buttonOrdreSoeg.setText("Søg");
        buttonOrdreSoeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOrdreSoegActionPerformed(evt);
            }
        });

        jLabel10.setText("Fra:");

        jLabel11.setText("Til:");

        cbOrdreDepositum.setText("Depositum betalt? ");
        cbOrdreDepositum.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        cbOrdreDepositum.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbOrdreDepositumItemStateChanged(evt);
            }
        });

        jLabel12.setText("Produkter:");

        listOrdreOrdredetaljer.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(listOrdreOrdredetaljer);

        jLabel13.setText("Produkt ID:");

        jLabel14.setText("Mængde:");

        jLabel15.setText("Total pris:");

        labelOrdreTotalPris.setText(" ");

        jLabel16.setText("Depositum:");

        labelOrdreDepositum.setText(" ");

        jLabel17.setText("Rabat:");

        tfOrdreRabat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfOrdreRabatKeyReleased(evt);
            }
        });

        tfOrdreMaengde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfOrdreMaengdeActionPerformed(evt);
            }
        });

        jLabel18.setText("Montører:");

        listOrdreMontoerer.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(listOrdreMontoerer);

        buttonOrdreSletProdukt.setText("Slet produkt");
        buttonOrdreSletProdukt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOrdreSletProduktActionPerformed(evt);
            }
        });

        buttonOrdreOpdaterDetalje.setText("Opdater");
        buttonOrdreOpdaterDetalje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOrdreOpdaterDetaljeActionPerformed(evt);
            }
        });

        buttonOrdreSletMontoer.setText("Slet montør");
        buttonOrdreSletMontoer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOrdreSletMontoerActionPerformed(evt);
            }
        });

        jLabel19.setText("Lastvogne:");

        listOrdreLastvogne.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(listOrdreLastvogne);

        buttonOrdreSletLastvogn.setText("Slet lastvogn");
        buttonOrdreSletLastvogn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOrdreSletLastvognActionPerformed(evt);
            }
        });

        buttonOrdreOpdaterOrdre.setText("Opdater ordre");
        buttonOrdreOpdaterOrdre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOrdreOpdaterOrdreActionPerformed(evt);
            }
        });

        buttonOrdreAnnuller.setText("Annuller ændringer");
        buttonOrdreAnnuller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOrdreAnnullerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfOrdreTil)
                            .addComponent(tfOrdreOrdrenr, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(tfOrdreFra)
                            .addComponent(tfOrdreRabat))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttonOrdreSoeg, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .addComponent(tfOrdreFraKlokken)
                            .addComponent(tfOrdreTilKlokken)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(buttonOrdreOpdaterOrdre)
                        .addGap(10, 10, 10)
                        .addComponent(buttonOrdreAnnuller, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelOrdreDepositum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(12, 12, 12)
                                .addComponent(labelOrdreTotalPris, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addComponent(cbOrdreDepositum)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelOrdreProduktID))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(12, 12, 12)
                                .addComponent(tfOrdreMaengde, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(buttonOrdreOpdaterDetalje, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonOrdreSletProdukt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonOrdreSletMontoer, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19)
                    .addComponent(buttonOrdreSletLastvogn, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(labelOrdreProduktID))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(tfOrdreMaengde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonOrdreOpdaterDetalje)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonOrdreSletProdukt))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(buttonOrdreSletLastvogn)
                                    .addComponent(buttonOrdreSletMontoer)))
                            .addComponent(jScrollPane2)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(tfOrdreOrdrenr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonOrdreSoeg))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfOrdreFra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(tfOrdreFraKlokken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfOrdreTil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(tfOrdreTilKlokken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(tfOrdreRabat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(labelOrdreTotalPris))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(labelOrdreDepositum)
                            .addComponent(cbOrdreDepositum))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonOrdreOpdaterOrdre)
                            .addComponent(buttonOrdreAnnuller))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opret ordre", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12))); // NOI18N

        jLabel20.setText("Fra:");

        tfOpretFra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfOpretFraActionPerformed(evt);
            }
        });

        jLabel21.setText("Til:");

        listOpretProdukter.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(listOpretProdukter);

        jLabel22.setText("Produkter:");

        jLabel23.setText("Antal:");

        labelOpretODLedige.setText("Tal");

        jLabel24.setText("Produkt ID:");

        labelOpretProdukt.setText("pID");

        jLabel25.setText("Stykpris:");

        labelOpretPris.setText("komma");

        jLabel26.setText("Mængde:");

        buttonOpretTilfoejDetalje.setText("Tilføj produkt");
        buttonOpretTilfoejDetalje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOpretTilfoejDetaljeActionPerformed(evt);
            }
        });

        jLabel27.setText("Montører:");

        listOpretMontoerer.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(listOpretMontoerer);

        jLabel28.setText("Lastvogne:");

        listOpretLastvogne.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(listOpretLastvogne);

        buttonOpretMontoerer.setText("Tilføj montør");
        buttonOpretMontoerer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOpretMontoererActionPerformed(evt);
            }
        });

        buttonOpretLastvogne.setText("Tilføj lastvogn");
        buttonOpretLastvogne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOpretLastvogneActionPerformed(evt);
            }
        });

        buttonOpretOpretOrdre.setText("Opret ny ordre");
        buttonOpretOpretOrdre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOpretOpretOrdreActionPerformed(evt);
            }
        });

        buttonOpretAnnuller.setText("Annuller oprettelse");
        buttonOpretAnnuller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOpretAnnullerActionPerformed(evt);
            }
        });

        jLabel29.setText("Klokken:");

        jLabel30.setText("til");

        buttonOpretHentRessourcer.setText("Hent ressourcer");
        buttonOpretHentRessourcer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOpretHentRessourcerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(tfOpretFraKlokken, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfOpretTilKlokken, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tfOpretTil)
                            .addComponent(tfOpretFra)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(buttonOpretOpretOrdre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonOpretAnnuller))
                    .addComponent(buttonOpretHentRessourcer))
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel26)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfOpretMaengde))
                                .addComponent(buttonOpretTilfoejDetalje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel24)
                                        .addComponent(jLabel23))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(labelOpretODLedige)
                                        .addComponent(labelOpretProdukt))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addComponent(labelOpretPris)))))
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel27)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(buttonOpretMontoerer, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(buttonOpretLastvogne, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel24)
                                    .addComponent(labelOpretProdukt))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel23)
                                    .addComponent(labelOpretODLedige))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel25)
                                    .addComponent(labelOpretPris))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel26)
                                    .addComponent(tfOpretMaengde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(buttonOpretTilfoejDetalje)
                                    .addComponent(buttonOpretMontoerer)
                                    .addComponent(buttonOpretLastvogne)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(tfOpretFra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfOpretTil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(tfOpretFraKlokken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfOpretTilKlokken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonOpretHentRessourcer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonOpretAnnuller)
                            .addComponent(buttonOpretOpretOrdre))))
                .addContainerGap())
        );

        labelStatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        menuOrdrer.setText("Ordrer");

        menuItemNedskrivning.setText("Opret nedskrivning");
        menuItemNedskrivning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemNedskrivningActionPerformed(evt);
            }
        });
        menuOrdrer.add(menuItemNedskrivning);

        jMenuBar1.add(menuOrdrer);

        menuUdskriv.setText("Udskriv");

        menuItemOrdreliste.setText("Ordreliste");
        menuItemOrdreliste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemOrdrelisteActionPerformed(evt);
            }
        });
        menuUdskriv.add(menuItemOrdreliste);

        menuItemPakkeliste.setText("Pakkeliste");
        menuItemPakkeliste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPakkelisteActionPerformed(evt);
            }
        });
        menuUdskriv.add(menuItemPakkeliste);

        menuItemStatusLastvogne.setText("Status (Lastvogne)");
        menuItemStatusLastvogne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemStatusLastvogneActionPerformed(evt);
            }
        });
        menuUdskriv.add(menuItemStatusLastvogne);

        menuItemSatusMontoerer.setText("Status (Montører)");
        menuItemSatusMontoerer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSatusMontoererActionPerformed(evt);
            }
        });
        menuUdskriv.add(menuItemSatusMontoerer);

        jMenuBar1.add(menuUdskriv);

        menuLager.setText("Lager");

        menuItemOpdaterLager.setText("Opdater lager");
        menuItemOpdaterLager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemOpdaterLagerActionPerformed(evt);
            }
        });
        menuLager.add(menuItemOpdaterLager);

        jMenuBar1.add(menuLager);

        menuBrugere.setText("Brugere");

        menuItemAdministrerBrugere.setText("Administrer");
        menuItemAdministrerBrugere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAdministrerBrugereActionPerformed(evt);
            }
        });
        menuBrugere.add(menuItemAdministrerBrugere);

        jMenuBar1.add(menuBrugere);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(labelStatus)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfKundeTelefonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfKundeTelefonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfKundeTelefonActionPerformed

    private void tfOrdreMaengdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfOrdreMaengdeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfOrdreMaengdeActionPerformed

    private void buttonOrdreSletLastvognActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOrdreSletLastvognActionPerformed
        if (vSelectedVehicle != null) {
            orderChanged = true;
            deletedVehicle.add(vSelectedVehicle);
            opdaterSelectedOrder.removeVehicle(vSelectedVehicle);
            labelOrdreDepositum.setText("" + control.calcDeposit(opdaterSelectedOrder, 33));
            tentativeTotal = control.calcTotal(opdaterSelectedOrder, Integer.parseInt(tfOrdreRabat.getText()));
            labelOrdreTotalPris.setText("" + tentativeTotal);
            modelOrdreLastvogne.removeElement(vSelectedVehicle);
        }
    }//GEN-LAST:event_buttonOrdreSletLastvognActionPerformed

    private void tfOpretFraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfOpretFraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfOpretFraActionPerformed

    private void menuItemNedskrivningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemNedskrivningActionPerformed
        new NedskrivningFrame().setVisible(true);
    }//GEN-LAST:event_menuItemNedskrivningActionPerformed

    private void menuItemStatusLastvogneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemStatusLastvogneActionPerformed
        new StatusVehicleFrame().setVisible(true);
    }//GEN-LAST:event_menuItemStatusLastvogneActionPerformed

    private void buttonKundeSoegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonKundeSoegActionPerformed
        boolean b = false;
        setStatus("Søger efter kunde...");
        if (!tfKundeKundenr.getText().isEmpty() && isInteger(tfKundeKundenr.getText())) {
            currentCustomer = control.getCustomer(Integer.parseInt(tfKundeKundenr.getText()));
            if (currentCustomer == null) {
                showMessage("Ugyldigt kundenummer!");
                setStatus("Fejl - Ugyldigt kundenummer");
            } else {
                b = true;
            }
        } else if (!tfKundeEmail.getText().isEmpty() && tfKundeEmail.getText().contains("@")) {
            currentCustomer = control.getCustomerByEmail(tfKundeEmail.getText());
            if (currentCustomer == null) {
                showMessage("Ugyldig email!");
                setStatus("Fejl - Ugyldig email");
            } else {
                b = true;
            }
        } else if (!tfKundeTelefon.getText().isEmpty() && tfKundeTelefon.getText().length() == 8 && isInteger(tfKundeTelefon.getText())) {
            currentCustomer = control.getCustomerByPhone(Integer.parseInt(tfKundeTelefon.getText()));
            if (currentCustomer == null) {
                showMessage("Ugyldigt telefonnummer!");
                setStatus("Fejl - Ugyldigt telefonnummer");
            } else {
                b = true;
            }
        }
        if (b) {
            setStatus("Fandt kunde! Kundenr.: " + currentCustomer.getcID());
            modelKundeOrdrer.clear();
            modelOrdreLastvogne.clear();
            modelOrdreMontoerer.clear();
            modelOrdreProdukter.clear();
            clearOrdreFields();
            setCustomerFields();
        }
    }//GEN-LAST:event_buttonKundeSoegActionPerformed

    private void buttonKundeOpretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonKundeOpretActionPerformed
        String s = ("Kunne ikke oprette kunde.");
        if (tfKundeKundeNavn.getText().isEmpty()) {
            showMessage("Indtast kundens navn!");
            setStatus(s);
        } else if (!tfKundeEmail.getText().contains("@")) {
            showMessage("Ugyldig email-addresse!");
            setStatus(s);
        } else if (tfKundeAddresse.getText().isEmpty()) {
            showMessage("Indtast kundens addresse!");
            setStatus(s);
        } else if (tfKundeTelefon.getText().isEmpty()) {
            showMessage("Indtast kundens telefonnummer!");
            setStatus(s);
        } else if (!isInteger(tfKundeTelefon.getText())) {
            showMessage("Ugyldigt telefonnummer!");
            setStatus(s);
        } else if (tfKundePostnr.getText().isEmpty()) {
            showMessage("Indtast kundens postnummer!");
            setStatus(s);
        } else if (!isInteger(tfKundePostnr.getText())) {
            showMessage("Ugyldigt postnummer!");
            setStatus(s);
        } else {
            String name = tfKundeKundeNavn.getText();
            String email = tfKundeEmail.getText();
            String adresse = tfKundeAddresse.getText();
            int number = Integer.parseInt(tfKundeTelefon.getText());
            int zip = Integer.parseInt(tfKundePostnr.getText());
            currentCustomer = control.createCustomer(name, email, number, adresse, zip);
            if (currentCustomer != null) {
                control.saveCustomer();
                tfKundeKundenr.setText(currentCustomer.getcID() + "");
                tfKundeBy.setText(control.getCity(currentCustomer.getcZip()));
                setStatus("Kunde oprettet! Kundenummer: " + currentCustomer.getcID());
            }
        }
    }//GEN-LAST:event_buttonKundeOpretActionPerformed

    private void buttonOrdreSoegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOrdreSoegActionPerformed
        boolean b = false;
        int result = JOptionPane.NO_OPTION;
        if (orderChanged) {
            result = JOptionPane.showConfirmDialog(this, "Du har lavet ændringer der ikke er blevet gemt!\n"
                    + "Gem ændringer?");
        }
        if (result == JOptionPane.YES_OPTION) {
            buttonOrdreOpdaterOrdre.doClick(0);
        } else if (result == JOptionPane.NO_OPTION && !tfOrdreOrdrenr.getText().isEmpty() && isInteger(tfOrdreOrdrenr.getText())) {
            setStatus("Søger efter ordre...");
            currentOrder = control.getOrder(Integer.parseInt(tfOrdreOrdrenr.getText()));
            if (currentOrder == null) {
                showMessage("Ugyldigt ordrenummer!");
                setStatus("Fejl - Ugyldigt ordrenummer");
            } else {
                b = true;
            }
        }
        if (b) {
            setStatus("Fandt ordre! Arbejder...");
            clearOrdreChanges();
            opdaterSelectedOrder = currentOrder;
            currentCustomer = control.getCustomer(currentOrder.getCID());
            clearOrdreFields();
            clearCustomerFields();
            setCustomerFields();
            setOrdreFields();
            setStatus("Fandt ordre! Ordrenummer: " + currentOrder.getOID());
        }
    }//GEN-LAST:event_buttonOrdreSoegActionPerformed

    private void tfOrdreRabatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfOrdreRabatKeyReleased
        if (isInteger(tfOrdreRabat.getText())) {
            int rabat = Integer.parseInt(tfOrdreRabat.getText());
            if (rabat < 0) {
                rabat = 0;
            } else if (rabat > 100) {
                rabat = 100;
            }
            tentativeTotal = control.calcTotal(currentOrder, rabat);
            labelOrdreTotalPris.setText("" + tentativeTotal);
        } else {
            tentativeTotal = control.calcTotal(currentOrder, 0);
            labelOrdreTotalPris.setText("" + tentativeTotal);
        }
        orderChanged = true;
    }//GEN-LAST:event_tfOrdreRabatKeyReleased

    private void buttonOrdreOpdaterDetaljeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOrdreOpdaterDetaljeActionPerformed
        if (ODSelectedOrderDetail != null && isInteger(tfOrdreMaengde.getText())) {
            orderChanged = true;
            ODSelectedOrderDetail.setAmount(Integer.parseInt(tfOrdreMaengde.getText()));

            opdaterOrderDetail.add(ODSelectedOrderDetail);

            labelOrdreDepositum.setText("" + control.calcDeposit(currentOrder, 33));
            tentativeTotal = control.calcTotal(currentOrder, Integer.parseInt(tfOrdreRabat.getText()));
            labelOrdreTotalPris.setText("" + tentativeTotal);
            listOrdreOrdredetaljer.repaint();
        }
    }//GEN-LAST:event_buttonOrdreOpdaterDetaljeActionPerformed

    private void tfOrdreOrdrenrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfOrdreOrdrenrKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buttonOrdreSoeg.doClick();
        }
    }//GEN-LAST:event_tfOrdreOrdrenrKeyPressed

    private void tfKundeKundenrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfKundeKundenrKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buttonKundeSoeg.doClick();
        }
    }//GEN-LAST:event_tfKundeKundenrKeyPressed

    private void tfKundeEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfKundeEmailKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tfKundeEmailKeyReleased

    private void tfKundeEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfKundeEmailKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buttonKundeSoeg.doClick();
        }
    }//GEN-LAST:event_tfKundeEmailKeyPressed

    private void tfKundeTelefonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfKundeTelefonKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buttonKundeSoeg.doClick();
        }
    }//GEN-LAST:event_tfKundeTelefonKeyPressed

    private void buttonOrdreSletProduktActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOrdreSletProduktActionPerformed
        if (ODSelectedOrderDetail != null) {
            orderChanged = true;
            opdaterSelectedOrder.removeOrderDetail(ODSelectedOrderDetail);
            deletedOrderDetail.add(ODSelectedOrderDetail);
            labelOrdreDepositum.setText("" + control.calcDeposit(opdaterSelectedOrder, 33));
            tentativeTotal = control.calcTotal(opdaterSelectedOrder, Integer.parseInt(tfOrdreRabat.getText()));
            labelOrdreTotalPris.setText("" + tentativeTotal);
            modelOrdreProdukter.removeElement(ODSelectedOrderDetail);
        }
    }//GEN-LAST:event_buttonOrdreSletProduktActionPerformed

    private void buttonOrdreSletMontoerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOrdreSletMontoerActionPerformed
        // TODO add your handling code here:
        if (INSelectedInstaller != null) {
            orderChanged = true;
            opdaterSelectedOrder.removeInstaller(INSelectedInstaller);
            labelOrdreDepositum.setText("" + control.calcDeposit(opdaterSelectedOrder, 33));
            tentativeTotal = control.calcTotal(opdaterSelectedOrder, Integer.parseInt(tfOrdreRabat.getText()));
            labelOrdreTotalPris.setText("" + tentativeTotal);
            deletedInstaller.add(INSelectedInstaller);
            modelOrdreMontoerer.removeElement(INSelectedInstaller);
        }
    }//GEN-LAST:event_buttonOrdreSletMontoerActionPerformed

    private void buttonOrdreOpdaterOrdreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOrdreOpdaterOrdreActionPerformed
        if (orderChanged) {
            int showConfirmDialog = JOptionPane.showConfirmDialog(this, "Du er ved gemme ændringer i ordren.\n"
                    + "Er du sikker på at du vil gemme ændringerne?");
            if (showConfirmDialog == JOptionPane.YES_OPTION) {
                setStatus("Gemmer ændringer...");
                System.out.println((opdaterSelectedOrder == null) + "   " + cbOrdreDepositum.isSelected());
                opdaterSelectedOrder.setDepositPaid(cbOrdreDepositum.isSelected());
                opdaterSelectedOrder.setDiscount(Integer.parseInt(tfOrdreRabat.getText()));

                for (int i = 0; i < deletedOrderDetail.size(); i++) {
                    OrderDetail od = deletedOrderDetail.get(i);
                    control.registerRemovedOrderDetail(od);
                    control.saveDeletedOrderDetails();
                }
                for (int i = 0; i < deletedInstaller.size(); i++) {
                    Installer in = deletedInstaller.get(i);
                    control.registerRemovedInstaller(in);
                    control.saveDeletedInstallers();
                }
                for (int i = 0; i < opdaterOrderDetail.size(); i++) {
                    OrderDetail od = opdaterOrderDetail.get(i);
                    control.registerDirtyOrderDetail(od);
                    control.saveUpdatedOrderDetails();
                }
                for (int i = 0; i < deletedVehicle.size(); i++) {
                    Vehicle v = deletedVehicle.get(i);
                    control.registerRemovedVehicle(v);
                    control.saveRemovedVehicles();
                }
                control.registerDirtyOrder(opdaterSelectedOrder);
                control.saveOrder();
                clearOrdreChanges();
                clearOrdreFields();
                clearCustomerFields();
                orderChanged = false;
                setStatus("Dine ændringer er gemt!");
            }
        }
    }//GEN-LAST:event_buttonOrdreOpdaterOrdreActionPerformed

    private void buttonOpretHentRessourcerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOpretHentRessourcerActionPerformed
        String match = "\\d\\d-[a-zA-Z]{3}-\\d\\d\\d\\d";
        String match2 = "\\d\\d:\\d\\d";
        setStatus("Henter ledige produkter, montører og lastvogne...");
        if (tfOpretFra.getText().matches(match) && tfOpretTil.getText().matches(match)
                && tfOpretFraKlokken.getText().matches(match2) && tfOpretTilKlokken.getText().matches(match2)) {
            setStatus("Ledige produkter, montører og lastvogne fundet. Arbejder...");
            clearOrdreChanges();
            clearOrdreFields();
            modelOpretLastvogne.clear();
            modelOpretMontoerer.clear();
            modelOpretProdukter.clear();
            mapProducts = new HashMap();
            ArrayList<Order> ol = control.getOrders(tfOpretFra.getText(), tfOpretTil.getText());
            products = control.getProducts();
            for (int i = 0; i < products.size(); i++) {
                mapProducts.put(products.get(i).getpID(), 0);
            }
            ArrayList<OrderDetail> odl;
            for (int i = 0; i < ol.size(); i++) {
                odl = ol.get(i).getOrderDetails();
                for (int j = 0; j < odl.size(); j++) {
                    int key = odl.get(j).getpID();
                    int amount = mapProducts.get(key) + odl.get(j).getAmount();
                    mapProducts.put(key, amount);
                }
            }
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                modelOpretProdukter.addElement(product);
            }

            montoerer = control.getMontoerer();
            ArrayList<Installer> il = control.getInstallers(tfOpretFra.getText() + " " + tfOpretFraKlokken.getText(),
                    tfOpretTil.getText() + " " + tfOpretTilKlokken.getText());
            boolean b = false;
            for (int i = 0; i < montoerer.size(); i++) {
                for (int j = 0; j < il.size(); j++) {
                    if (montoerer.get(i).geteID() == il.get(j).geteID()) {
                        b = true;
                    }
                }
                if (!b) {
                    modelOpretMontoerer.addElement(montoerer.get(i));
                }
                b = false;
            }

            ArrayList<Vehicle> vl = control.getVehicles(tfOpretFra.getText() + " " + tfOpretFraKlokken.getText(),
                    tfOpretTil.getText() + " " + tfOpretTilKlokken.getText());
            for (int i = 0; i < vl.size(); i++) {
                Vehicle v = vl.get(i);
                modelOpretLastvogne.addElement(v);
            }
            setStatus("Ledige produkter, montører og lastvogne fundet.");
        } else {
            setStatus("Fejl - Ugyldig dato eller tidspunkt");
            showMessage("Ugyldig dato eller tidspunkt!");
        }
    }//GEN-LAST:event_buttonOpretHentRessourcerActionPerformed

    private void buttonOpretOpretOrdreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOpretOpretOrdreActionPerformed
        String match = "\\d\\d-[a-zA-Z]{3}-\\d\\d\\d\\d";
        String match2 = "\\d\\d:\\d\\d";
        setStatus("Opretter ordre...");
        if (!tfOpretFra.getText().matches(match) || !tfOpretTil.getText().matches(match)
                || !tfOpretFraKlokken.getText().matches(match2) || !tfOpretTilKlokken.getText().matches(match2)) {
            setStatus("Fejl - ugyldig dato eller tidspunkt");
            showMessage("Ugyldig dato eller tidspunkt!");
        } else if (!isInteger(tfKundeKundenr.getText())) {
            setStatus("Fejl - ugyldigt kundenummer");
            showMessage("Ugyldigt kundenummer!");
        } else {
            currentOrder = control.createOrder(Integer.parseInt(tfKundeKundenr.getText()), tfOpretFra.getText(), tfOpretTil.getText(), 0);
            for (int i = 0; i < newOrderDetails.size(); i++) {
                OrderDetail od = newOrderDetails.get(i);
                control.addOrderDetail(od.getpID(), od.getAmount());
            }
            for (int i = 0; i < addedInstallers.size(); i++) {
                Installer in = addedInstallers.get(i);
                control.addInstaller(in.geteID(), in.getFrom().toString(), in.getTo().toString());
            }
            for (int i = 0; i < vMod.size(); i++) {
                Vehicle v = (Vehicle) vMod.get(i);
                control.addVehicle(v.getvID(), tfOpretFra.getText() + " " + tfOpretFraKlokken.getText(),
                        tfOpretTil.getText() + " " + tfOpretTilKlokken.getText());
            }
            control.saveOrder();
            control.createPDF(currentOrder);
            newOrderDetails.clear();
            addedInstallers.clear();
            vMod.clear();
            setStatus("Ordre oprettet! Ordrenummer: " + currentOrder.getOID());
            modelKundeOrdrer.addElement(currentOrder);
            listKundeOrdrer.setSelectedValue(currentOrder, true);
            clearOrdreChanges();
            clearOpretFields();
            modelOpretLastvogne.clear();
            modelOpretMontoerer.clear();
            modelOpretProdukter.clear();
        }
    }//GEN-LAST:event_buttonOpretOpretOrdreActionPerformed

    private void buttonOpretAnnullerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOpretAnnullerActionPerformed
        // TODO add your handling code here:
        int i = JOptionPane.showConfirmDialog(this, "Du er ved at annullere oprettelsen af en ny ordre.\n Er du sikker?");
        if (i == JOptionPane.YES_OPTION) {
            clearOrdreChanges();
            clearOpretFields();
            modelOpretLastvogne.clear();
            modelOpretMontoerer.clear();
            modelOpretProdukter.clear();
            setStatus("Ordreoprettelse annulleret");
        }
    }//GEN-LAST:event_buttonOpretAnnullerActionPerformed

    private void buttonOpretTilfoejDetaljeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOpretTilfoejDetaljeActionPerformed
        if (isInteger(tfOpretMaengde.getText())) {
            int newAmount = selectedProduct.getAntalTotal() - Integer.parseInt(tfOpretMaengde.getText());
            int newRentedAmount = selectedProduct.getAntalUdlejet() + Integer.parseInt(tfOpretMaengde.getText());
            selectedProduct.setAntal(newAmount);
            selectedProduct.setAntalUdlejet(newRentedAmount);
            control.registerDirtyProduct(selectedProduct);
            OrderDetail newOrderDetail = new OrderDetail(-1, selectedProduct.getpID(), Integer.parseInt(tfOpretMaengde.getText()), 0);
            newOrderDetails.add(newOrderDetail);
            listOpretProdukter.setSelectedIndex(0);
            tfOpretMaengde.setText(null);
            setStatus("Produktet er tilføjet til ordren");
        } else {
            setStatus("Fejl - ugyldig mængde indstastet");
            showMessage("Ugyldig mængde produkter indtastet!");
        }
    }//GEN-LAST:event_buttonOpretTilfoejDetaljeActionPerformed

    private void buttonOpretMontoererActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOpretMontoererActionPerformed
        String from = tfOpretFra.getText() + " " + tfOpretFraKlokken.getText();
        String to = tfOpretTil.getText() + " " + tfOpretTilKlokken.getText();
        Installer newInstaller = new Installer(-1, selectedInstaller.geteID(), from, to, 0);
        addedInstallers.add(newInstaller);
        setStatus("Montøren er tilføjet til ordren");
    }//GEN-LAST:event_buttonOpretMontoererActionPerformed

    private void buttonOpretLastvogneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOpretLastvogneActionPerformed
        if (addedInstallers.size() != vMod.size() + 1) {
            setStatus("Fejl - Der er for få montører i forhold til lastvogne");
            showMessage("Der er for få montører i forhold til lastvogne."
                    + "\nAntal montører: " + addedInstallers.size());
        } else {
            vMod.add(currentVehicle);
            setStatus("Lastvognen er tilføjet til ordren");
        }
    }//GEN-LAST:event_buttonOpretLastvogneActionPerformed

    private void menuItemOrdrelisteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemOrdrelisteActionPerformed
        new OrdrelisteFrame().setVisible(true);
    }//GEN-LAST:event_menuItemOrdrelisteActionPerformed

    private void menuItemPakkelisteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPakkelisteActionPerformed
        new PakkelisteFrame().setVisible(true);
    }//GEN-LAST:event_menuItemPakkelisteActionPerformed

    private void menuItemSatusMontoererActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSatusMontoererActionPerformed
        new StatusInstallerFrame().setVisible(true);
    }//GEN-LAST:event_menuItemSatusMontoererActionPerformed

    private void menuItemOpdaterLagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemOpdaterLagerActionPerformed
        new ResourceFrame().setVisible(true);
    }//GEN-LAST:event_menuItemOpdaterLagerActionPerformed

    private void menuItemAdministrerBrugereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAdministrerBrugereActionPerformed
        new UsersFrame().setVisible(true);
    }//GEN-LAST:event_menuItemAdministrerBrugereActionPerformed

    private void cbOrdreDepositumItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbOrdreDepositumItemStateChanged
        orderChanged = true;
    }//GEN-LAST:event_cbOrdreDepositumItemStateChanged

    private void buttonOrdreAnnullerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOrdreAnnullerActionPerformed
        clearOrdreChanges();
        orderChanged = false;
        clearOrdreFields();
        setStatus("Opdatering af ordre annulleret.");
    }//GEN-LAST:event_buttonOrdreAnnullerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonKundeOpret;
    private javax.swing.JButton buttonKundeSoeg;
    private javax.swing.JButton buttonOpretAnnuller;
    private javax.swing.JButton buttonOpretHentRessourcer;
    private javax.swing.JButton buttonOpretLastvogne;
    private javax.swing.JButton buttonOpretMontoerer;
    private javax.swing.JButton buttonOpretOpretOrdre;
    private javax.swing.JButton buttonOpretTilfoejDetalje;
    private javax.swing.JButton buttonOrdreAnnuller;
    private javax.swing.JButton buttonOrdreOpdaterDetalje;
    private javax.swing.JButton buttonOrdreOpdaterOrdre;
    private javax.swing.JButton buttonOrdreSletLastvogn;
    private javax.swing.JButton buttonOrdreSletMontoer;
    private javax.swing.JButton buttonOrdreSletProdukt;
    private javax.swing.JButton buttonOrdreSoeg;
    private javax.swing.JCheckBox cbOrdreDepositum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel labelOpretODLedige;
    private javax.swing.JLabel labelOpretPris;
    private javax.swing.JLabel labelOpretProdukt;
    private javax.swing.JLabel labelOrdreDepositum;
    private javax.swing.JLabel labelOrdreProduktID;
    private javax.swing.JLabel labelOrdreTotalPris;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JList listKundeOrdrer;
    private javax.swing.JList listOpretLastvogne;
    private javax.swing.JList listOpretMontoerer;
    private javax.swing.JList listOpretProdukter;
    private javax.swing.JList listOrdreLastvogne;
    private javax.swing.JList listOrdreMontoerer;
    private javax.swing.JList listOrdreOrdredetaljer;
    private javax.swing.JMenu menuBrugere;
    private javax.swing.JMenuItem menuItemAdministrerBrugere;
    private javax.swing.JMenuItem menuItemNedskrivning;
    private javax.swing.JMenuItem menuItemOpdaterLager;
    private javax.swing.JMenuItem menuItemOrdreliste;
    private javax.swing.JMenuItem menuItemPakkeliste;
    private javax.swing.JMenuItem menuItemSatusMontoerer;
    private javax.swing.JMenuItem menuItemStatusLastvogne;
    private javax.swing.JMenu menuLager;
    private javax.swing.JMenu menuOrdrer;
    private javax.swing.JMenu menuUdskriv;
    private javax.swing.JTextField tfKundeAddresse;
    private javax.swing.JTextField tfKundeBy;
    private javax.swing.JTextField tfKundeEmail;
    private javax.swing.JTextField tfKundeKundeNavn;
    private javax.swing.JTextField tfKundeKundenr;
    private javax.swing.JTextField tfKundePostnr;
    private javax.swing.JTextField tfKundeTelefon;
    private javax.swing.JTextField tfOpretFra;
    private javax.swing.JTextField tfOpretFraKlokken;
    private javax.swing.JTextField tfOpretMaengde;
    private javax.swing.JTextField tfOpretTil;
    private javax.swing.JTextField tfOpretTilKlokken;
    private javax.swing.JTextField tfOrdreFra;
    private javax.swing.JTextField tfOrdreFraKlokken;
    private javax.swing.JTextField tfOrdreMaengde;
    private javax.swing.JTextField tfOrdreOrdrenr;
    private javax.swing.JTextField tfOrdreRabat;
    private javax.swing.JTextField tfOrdreTil;
    private javax.swing.JTextField tfOrdreTilKlokken;
    // End of variables declaration//GEN-END:variables
}
