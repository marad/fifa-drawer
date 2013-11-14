package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import com.prondzyn.fifadrawer.lang.LoadingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PropertiesLoader {

  public static Properties loadFrom(String filepath) {

    Properties properties = new Properties();
    File file = new File(Properties.rootDirectory(), filepath);
    try (FileInputStream fis = new FileInputStream(file)) {
      properties.load(fis);
    } catch (FileNotFoundException ex) {
      throw new LoadingException("File '" + filepath + "' not found.", ex);
    } catch (IOException ex) {
      throw new LoadingException("There was a problem with reading file '" + filepath + "'.", ex);
    }
    return properties;
  }
}
