package com.poc.awss3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class S3Service {

    public static void main(String[] args) throws IOException{
        // Reading property file.
        S3Service obj = new S3Service();
        obj.loadPropertyFile();
        System.out.println("username: "+ obj.prop.getProperty("s3.accessKey"));

        final S3ConnectionFactory connFactory = new S3ConnectionFactory();

    }

    private Properties prop = null;

    public void loadPropertyFile(){

        InputStream is = null;
        try {
            prop = new Properties();
            is = this.getClass().getResourceAsStream("/application.properties");
            prop.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
