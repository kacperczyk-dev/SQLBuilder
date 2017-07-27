package com.sql;


import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;
import org.springframework.core.io.ClassPathResource;

import javax.swing.*;
import javax.xml.bind.JAXBElement;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by DKacperc on 2017-02-12.
 */
public class SaveClass {

    public static void saveFile(String s, int i){
        String fileName = "";
        Date date = new Date();
        if(i == 1){
            String file = date.toString().replace(" ", "_");
            file = file.replace(":", "-");
            fileName = "SELECT_" + file + ".sql";
        }
        else if(i == 2){
            String file = date.toString().replace(" ", "_");
            file = file.replace(":", "-");
            fileName = "UPDATE_" + file + ".sql";
        }
        else if(i == 3){
            String file = date.toString().replace(" ", "_");
            file = file.replace(":", "-");
            fileName = "INSERT_" + file + ".sql";
        }
        else if(i == 4){
            String file = date.toString().replace(" ", "_");
            file = file.replace(":", "-");
            fileName = "BACKUP_" + file + ".sql";
        }
        Path path = new File("C:\\Users\\" + System.getProperty("user.name").toLowerCase() + "\\Desktop\\" + fileName).toPath();
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            Files.write(path, s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveWe(String s){
        Date date = new Date();
        String file = date.toString().replace(" ", "_");
        file = file.replace(":", "-");
        String filename = "C:\\Users\\" + System.getProperty("user.name").toLowerCase() + "\\Desktop\\" + "Disclaimer_" + file + ".docx";
        try {
            URL f  = new ClassPathResource("Discl_We.docx").getURL();
            WordprocessingMLPackage template = WordprocessingMLPackage.load(f.openStream());

            String tixo = JOptionPane.showInputDialog("Enter ticket number");
            String cust = JOptionPane.showInputDialog("Enter customer name");
            String name = JOptionPane.showInputDialog("Enter your name");

            replacePlaceholder(template, cust, "[CUST.NAME]");
            replacePlaceholder(template, tixo, "[TIX.NO]");
            replacePlaceholder(template, name, "[CONS]");
            replacePlaceholder(template, s, "[SQL.SCRIPT]");
            writeDocxToStream(template, filename);
            JOptionPane.showMessageDialog(new Frame(), "Disclaimer saved onto your desktop. Remember to review the document before sending!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Docx4JException e) {
            e.printStackTrace();
        }

    }

    public static void saveCu(String s){
        Date date = new Date();
        String file = date.toString().replace(" ", "_");
        file = file.replace(":", "-");
        String filename = "C:\\Users\\" + System.getProperty("user.name").toLowerCase() + "\\Desktop\\" + "Disclaimer_" + file + ".docx";
        try {
            URL f  = new ClassPathResource("Discl_Cu.docx").getURL();
            WordprocessingMLPackage template = WordprocessingMLPackage.load(f.openStream());

            String tixo = JOptionPane.showInputDialog("Enter ticket number");
            String cust = JOptionPane.showInputDialog("Enter customer name");
            String name = JOptionPane.showInputDialog("Enter your name");

            replacePlaceholder(template, cust, "[CUST.NAME]");
            replacePlaceholder(template, tixo, "[TIX.NO]");
            replacePlaceholder(template, name, "[CONS]");
            replacePlaceholder(template, s, "[SQL.SCRIPT]");
            writeDocxToStream(template, filename);
            JOptionPane.showMessageDialog(new Frame(), "Disclaimer saved onto your desktop. Remember to review the document before sending!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }

    static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {

        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();
        if (obj.getClass().equals(toSearch))
        result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }
        }
        return result;
    }
    static void replacePlaceholder(WordprocessingMLPackage template, String name, String placeholder) {
        List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
        for (Object text : texts) {
            Text textElement = (Text) text;
            if (textElement.getValue().equals(placeholder)) {

                textElement.setValue(name);
            }
        }
    }

    static void writeDocxToStream(WordprocessingMLPackage template, String target) throws IOException, Docx4JException {
        File f = new File(target);
        template.save(f);
    }

}
