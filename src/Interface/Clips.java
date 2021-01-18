/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import static Interface.Cover.loadAllUsers;
import Pojos.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.CLIPSLoadException;
import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.FactAddressValue;


public class Clips extends javax.swing.JFrame implements ListSelectionListener{

    
    String assertIn = "";
            
    Environment clips;
    ArrayList<DiseaseUser> diseaseUserOrdered = new ArrayList();
    ArrayList<Disease> diseases = new ArrayList();
    
    DefaultListModel<String> modelList = new DefaultListModel<String>();            
    JList<String> listText = new JList<String>();
    
    int selectedIndex;
    String userName = "";
    String recommendations = "";
    List <Test> testList = new ArrayList <Test>();
    List<String> clipsRules = new ArrayList <String>();
    
    
    public Clips(ArrayList <DiseaseUser> diseaseUserOrdered) {

            initComponents();
            this.diseaseUserOrdered = diseaseUserOrdered;
            this.setSize(new Dimension(600,750));
            
            
            nameText.setEditable(false);
            issueText.setEditable(false);
            resultText.setEditable(false);
            recommendationText.setEditable(false);
            //setIcon();

            orderDiseaseUser();
            System.out.println("DiseaseUser ordered");
            orderDiseaseUser(); 
            System.out.println("Diseases ordered");
            
            System.out.println(diseaseUserOrdered.size());
            for(int j = 0; j < diseaseUserOrdered.size();j++)
            {
                System.out.println("I am inside the loop");
                modelList.addElement(diseaseUserOrdered.get(j).getNameTest());
            }
            
            /*Set information in boxes*/
            
            //Patient information
            nameText.setText(diseaseUserOrdered.get(0).getNameUser());
            userName = diseaseUserOrdered.get(0).getNameUser().replace("\"","");
            
               
            recommendations = userName + " has done a total of " + diseaseUserOrdered.size() + " tests";
            recommendations = recommendations + "\n";
            Iterator<DiseaseUser> it = diseaseUserOrdered.iterator();
            //The user wants to review tests.
            while(it.hasNext()){
                DiseaseUser du = (DiseaseUser)it.next();
                recommendations = recommendations + "Test " + du.getNameTest().replace("\"","") + " for " + du.getNameDisease().replace("\"","") + ", done in " + du.getLd() + ".\n";
            }
            recommendationText.setText(recommendations);

        
            listText.setCellRenderer(new setColor());
            testPane.setLayout(new BorderLayout());
            testPane.setVisible(true);
            //testPane.add(listText);
            testPane.add(listText, BorderLayout.CENTER);
            listText.addListSelectionListener(this);
            
            listText.setModel(modelList);
            listText.setFont(new Font("Tahoma",Font.PLAIN,18));
            listText.setVisible(true);
    }
    
    
    public Clips(List <Test> testList, List<String> clipsRules) {
         try {
            initComponents();
            this.testList = testList;
            this.clipsRules = clipsRules;
            this.setSize(new Dimension(600,750));
            recommendationText.setSize(new Dimension(475,100));
            
            nameText.setEditable(false);
            issueText.setEditable(false);
            resultText.setEditable(false);
            
            //setIcon();

            for(int i = 0; i < testList.size(); i++){
                clips = new Environment();
                System.out.println("Creating CLIPS environment...");
            
            
                clips.load(clipsRules.get(i));
                clips.reset(); //Load the defFacts, but we dont want to run yet.
                System.out.println("Loading the deffacts...");
                

                //jPanel1.setVisible(true);
                this.setVisible(true);

                assertUser(testList.get(i)); 
                System.out.println("Asserting our user with answers...");
                clipsResult();
                System.out.println("Getting the results...");
            }
            
            orderDiseaseUser();
            System.out.println("DiseaseUser ordered");
            orderDisease(diseases); 
            System.out.println("Diseases ordered");
            
            System.out.println(diseaseUserOrdered.size());
            for(int j = 0; j < diseaseUserOrdered.size();j++)
            {
                System.out.println("I am inside the loop");
                System.out.println(diseaseUserOrdered.get(j).getNameDisease());
                System.out.println(diseaseUserOrdered.get(0).getNameTest());
                modelList.addElement(diseaseUserOrdered.get(j).getNameTest());
                System.out.println(diseaseUserOrdered.get(j).getNameTest());
            }
            
            /*Set information in boxes*/
            
            //Patient information
            nameText.setText(testList.get(0).getUserName());
            userName = diseaseUserOrdered.get(0).getNameUser();
            
            recommendations = userName + " has done a total of " + diseaseUserOrdered.size() + " tests";
            recommendations = recommendations + "\n";
            Iterator<DiseaseUser> it = diseaseUserOrdered.iterator();
            
            while(it.hasNext()){
                DiseaseUser du = (DiseaseUser)it.next();
                recommendations = recommendations + "\n\nTest " + du.getNameTest().replace("\"","") + " for " + du.getNameDisease().replace("\"","") + ".\n";
                recommendations = recommendations + userName + " has obtained " + du.getPoints() + " points, which is a " + du.getPercentage() + " compatibility with the " +
                        du.getNameDisease().replace("\"","") + ".\n";
                String value = du.getNameDisease().replace("\"","");
                switch(value){
                    case "Anxiety Disorder": 
                        if(du.getPoints() <= 4){
                            recommendations = recommendations + "The results show that the patient may be suffering of MINIMAL ANXIETY.";
                        }else if(du.getPoints()>4 && du.getPoints()<= 9){
                            recommendations = recommendations + "The results show that the patient  may be suffering of MILD ANXIETY.\n" +
                                    "Some selfcare may be necessary";
                        }else if(du.getPoints()>9 && du.getPoints()<= 14){
                            recommendations = recommendations + " The results show that the patient may be suffering of MODERATE ANXIETY.\n" +
                                    "Please refer to a specialised doctor";
                        }else if(du.getPoints()>14){
                            recommendations = recommendations + " The results show that the patient may be suffering of SEVERE ANXIETY.\n" + 
                                    "Please refer to a specialised doctor as soon as possible";
                        }
                        break;
                    
                    case "Depressive Disorder": 
                        if(du.getPoints() <= 8){
                            recommendations = recommendations + "The results show that the patient may be suffering of MINIMAL DEPRESSION.\n"+
                                    "This implies nowmal behavour.";
                        }else if(du.getPoints()>8 && du.getPoints()<= 11){
                            recommendations = recommendations + "The results show that the patient  may be suffering of MODERATE DEPRESSION.\n" +
                                    "Please refer to aspecialist";
                        }else if(du.getPoints()>11){
                            recommendations = recommendations + " The results show that the patient may be suffering of SEVERE DEPRESSION.\n" + 
                                    "Please refer to a specialised doctor as soon as possible";
                        }
                        break;
                    case "Eating Disorder": 
                        if(du.getPoints() <= 20){
                            recommendations = recommendations + "The results show that the patient presents NORMAL BEHAVIOUR.";
                        }else if(du.getPoints()>20){
                            recommendations = recommendations + " The results show that the patient may be suffering of EATING DISORDER.\n" + 
                                    "Please refer to a specialised doctor as soon as possible";
                        }
                        break;
                    case "Obsessive Compulsive Disorder": 
                        if(du.getPoints() <= 20){
                            recommendations = recommendations + "The results show that the patient presents NORMAL BEHAVIOUR.";
                        }else if(du.getPoints()>20){
                            recommendations = recommendations + " The results show that the patient may be suffering of OBSESSIVE COMPULSIVE DISORDER.\n" + 
                                    "Please refer to a specialised doctor as soon as possible";
                        }
                        break;
                }
            }
            recommendationText.setText(recommendations);
            
            listText.setCellRenderer(new setColor());
            testPane.setLayout(new BorderLayout());
            testPane.setVisible(true);
            //testPane.add(listText);
            testPane.add(listText, BorderLayout.CENTER);
            listText.addListSelectionListener(this);
            
            listText.setModel(modelList);
            listText.setFont(new Font("Tahoma",Font.PLAIN,18));
            listText.setVisible(true);
            

            
        } catch (CLIPSLoadException ex) {
            Logger.getLogger(Clips.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CLIPSException ex) {
            Logger.getLogger(Clips.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
 public void assertUser(Test test)
    {
        try {
            //This method assert the user to the Clips enviroment, and run the clips program to produce the result
            clips.reset();
            String user = test.getUserName();
            List <Integer> results = test.getAnswerList();
            
            Iterator<Integer> it = results.iterator();
            assertIn = "(assert (user (name " + user + ")";
            int i = 1;
            while(it.hasNext()){
                assertIn = assertIn.concat("(answer" + i + " " + integer2String(it.next()) + ")");
                i++;
            }
            assertIn = assertIn.concat("))");
            
            
            //String assertIn = "(assert (user (name "+ user +")(answer1  "+ q1 +")(answer2  "+ q2 +")(answer3  "+ q3 +")(answer4  "+ q4 +")(answer5  "+ q5 +")(answer6  "+ q6 +")(answer7  "+ q7 +")))";
            System.out.println(assertIn);
            clips.eval(assertIn);
            System.out.println("Asserting the user");
            clips.run();
            System.out.println("Running CLIPS");
        } catch (CLIPSException ex) {
            ex.printStackTrace();
            //Logger.getLogger(Clips.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clipsResult()
    {
        try {
            List<FactAddressValue> findAllFactsDiseaseUser = clips.findAllFacts("diseaseUser");
            List<FactAddressValue> findAllFactsDisease = clips.findAllFacts("disease");
            
            loadDiseaseUsers(findAllFactsDiseaseUser, findAllFactsDisease);
            loadDisease(findAllFactsDisease);
            
        } catch (CLIPSException ex) {
            Logger.getLogger(Clips.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadDiseaseUsers(List<FactAddressValue> factsDU, List<FactAddressValue> factsD )
    {
        //This function returns the diseases ordered by percentage
        Iterator itDiseaseUser = factsDU.iterator();
        Iterator itDisease = factsD.iterator();
        String nameTest = null;
        while(itDiseaseUser.hasNext())
        {
            FactAddressValue fact = (FactAddressValue)itDiseaseUser.next();
            
            String nameUser = fact.getSlotValue("nameUser").toString();
            String nameDisease = fact.getSlotValue("nameDisease").toString();
            System.out.println("We are inside diseaseUser iterator: " + nameDisease);
            int points = Integer.parseInt(fact.getSlotValue("count").toString());
            int total = Integer.parseInt(fact.getSlotValue("total").toString()); 
            while(itDisease.hasNext()){
                
                FactAddressValue factDisease = (FactAddressValue)itDisease.next();
                
                String nameDiseaseCheck = factDisease.getSlotValue("nameDisease").toString();
                System.out.println("We are inside disease iterator: " + nameDiseaseCheck);
                if(nameDisease.equals(nameDiseaseCheck)){
                    nameTest = factDisease.getSlotValue("testName").toString();
                }
            }
            
            if(nameTest == null){
                
                nameTest = "";
            }
            float percentage = calculatePercentage(points, total);
            System.out.println(points);
            System.out.println(total);
            System.out.println(percentage);
            DiseaseUser user = new DiseaseUser(nameUser, nameDisease, nameTest, percentage, points, java.time.LocalDate.now());
            saveDiseaseUser(user);
            System.out.println("Storing the DiseaseUser into txt...");
            diseaseUserOrdered.add(user);
        }
    }    
    
    
    
    public float calculatePercentage(int x, int y){
        float percentage = ((float)x/(float) y)*100;
        return percentage;
    }
    
    public void orderDiseaseUser()
    {
        for (int i = 0; i < diseaseUserOrdered.size()-1; i++) 
        {
            for(int j = 0; j<diseaseUserOrdered.size()-1; j++)
            {
                if (diseaseUserOrdered.get(j).getPercentage() <= diseaseUserOrdered.get(j+1).getPercentage()) 
                {
                DiseaseUser swapTemp = diseaseUserOrdered.get(j+1);
                diseaseUserOrdered.set(j+1, diseaseUserOrdered.get(j));
                diseaseUserOrdered.set(j,swapTemp);
                }
            }
        
        }        
    }
    
    public ArrayList<Disease> loadDisease (List<FactAddressValue> facts)
    {
        ArrayList rests = new ArrayList();
        Iterator it = facts.iterator();
        while(it.hasNext())
        {
            FactAddressValue fact = (FactAddressValue) it.next();
            String nameDisease = fact.getSlotValue("nameDisease").toString();
            String testName = fact.getSlotValue("testName").toString();
            Disease d = new Disease(nameDisease, testName);
            diseases.add(d);
        }
        return rests;
    }
        
    public void orderDisease(ArrayList<Disease> d)
    {
        Iterator it = diseaseUserOrdered.iterator();
        while(it.hasNext())
        {
           String disease = ((DiseaseUser)it.next()).getNameDisease();
           d.add(searchDisease(disease, d));
        }
        
    }
    
    public void saveDiseaseUser(DiseaseUser DU){

        FileOutputStream fileOutputStream=null;
        ObjectOutputStream objectOutputStream=null;
        String nombreArchivo = "UserDiseases.txt";
        String directorio = "data";
        ArrayList<DiseaseUser> dUsers = loadAllDiseaseUser(nombreArchivo,directorio);

        File fichero = new File(directorio,nombreArchivo);
        
            try{
            
                fileOutputStream = new FileOutputStream(fichero);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                dUsers.add(DU);
                Iterator<DiseaseUser> it = dUsers.iterator();
                objectOutputStream.writeObject(dUsers.size());
                while(it.hasNext())
                {
                    DiseaseUser du = (DiseaseUser)it.next();

                    objectOutputStream.writeObject(du);
                }
    
            }catch(IOException ex)
            {
            } finally {
                    try {
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        }
                    try {
                        if (objectOutputStream != null) {
                            objectOutputStream.close();
                        }
                   } catch (IOException ex) {
                       ex.printStackTrace();
                   }
            }
        
    }   
    
     public static ArrayList loadAllDiseaseUser(String nombreArchivo,String directorio)
    {
        
        ArrayList <DiseaseUser> DU = new ArrayList();
        int lastTest;
        
        File archivo;
        
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        archivo = new File(directorio,nombreArchivo);
        if(!archivo.exists())
        {
            return DU;
        }
        try{

            fileInputStream = new FileInputStream(archivo);
            objectInputStream = new ObjectInputStream(fileInputStream);

            lastTest = (int) objectInputStream.readObject();
            
            for(int i=0;i<lastTest;i++)
            {
                DU.add((DiseaseUser)objectInputStream.readObject());
            } 
            
        }catch(IOException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
    return DU;
        
    }
    
    public Disease searchDisease(String diseaseName, ArrayList<Disease> d)
    {
        Iterator it = d.iterator();
        Disease disease = null;
        while(it.hasNext())
        {
            disease = (Disease) it.next();
            if(disease.getNameDisease().equals(diseaseName))
            {
                return disease;
            }
        }
        return disease;
    }
    

   /* public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Icons/logo_mni.jpg")));
        this.setTitle("LRG");
    }
    */
    public String integer2String(int i)
    {
        return String.valueOf(i);
        
    }
    
    public class setColor extends DefaultListCellRenderer{
        public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
            Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
         
            if (diseaseUserOrdered.get(index).getPercentage() > 75) {
                c.setBackground(Color.red.brighter());
            }else if(diseaseUserOrdered.get(index).getPercentage() < 20) {
                c.setBackground(Color.green.brighter());
            }else{
                c.setBackground(Color.yellow.brighter());
 
            }
            
            return c;
        }
    }
  
    public void valueChanged(ListSelectionEvent lse) {
        if(!lse.getValueIsAdjusting()){
            selectedIndex = listText.getSelectedIndex();
            if(selectedIndex<0){
                selectedIndex=0;
            }
        resultText.setText(Float.toString(diseaseUserOrdered.get(selectedIndex).getPercentage()));
        issueText.setText(diseaseUserOrdered.get(selectedIndex).getNameDisease().replace("\"","") + " in " + diseaseUserOrdered.get(selectedIndex).getLd());
       }    
    }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        resultText = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        nameText = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        issueText = new javax.swing.JTextField();
        repeatButton = new javax.swing.JToggleButton();
        testPane = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        recommendationText = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Patient information:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Tests applied:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Results:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Recommendation:");

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 48)); // NOI18N
        jLabel5.setText("RESULTS");

        resultText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        resultText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resultTextActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("%");

        nameText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Issue tested:");

        issueText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        issueText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issueTextActionPerformed(evt);
            }
        });

        repeatButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        repeatButton.setText("Take another test");
        repeatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repeatButtonActionPerformed(evt);
            }
        });

        testPane.setToolTipText("");

        javax.swing.GroupLayout testPaneLayout = new javax.swing.GroupLayout(testPane);
        testPane.setLayout(testPaneLayout);
        testPaneLayout.setHorizontalGroup(
            testPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        testPaneLayout.setVerticalGroup(
            testPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 73, Short.MAX_VALUE)
        );

        recommendationText.setColumns(20);
        recommendationText.setRows(5);
        jScrollPane1.setViewportView(recommendationText);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel3))
                                .addGap(88, 88, 88)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(resultText, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(issueText)
                                    .addComponent(testPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(42, 42, 42)
                                .addComponent(nameText))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(302, 302, 302)
                                        .addComponent(repeatButton))
                                    .addComponent(jScrollPane1))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(81, 81, 81))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(60, 60, 60))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(testPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(issueText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(resultText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(repeatButton)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resultTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resultTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resultTextActionPerformed

    private void nameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTextActionPerformed

    private void issueTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issueTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_issueTextActionPerformed

    private void repeatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repeatButtonActionPerformed
       TestSel testSelectionFrame = new TestSel(userName);
       testSelectionFrame.setLocationRelativeTo(null); 
       testSelectionFrame.setVisible(true); 
       this.dispose();
    }//GEN-LAST:event_repeatButtonActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField issueText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameText;
    private javax.swing.JTextArea recommendationText;
    private javax.swing.JToggleButton repeatButton;
    private javax.swing.JTextField resultText;
    private javax.swing.JPanel testPane;
    // End of variables declaration//GEN-END:variables
}
