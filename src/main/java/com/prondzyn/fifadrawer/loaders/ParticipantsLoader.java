package com.prondzyn.fifadrawer.loaders;

import com.prondzyn.fifadrawer.Properties;
import static com.prondzyn.fifadrawer.Properties.DEFAULT_CHARSET;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.prondzyn.fifadrawer.entities.domain.Participant;
import com.prondzyn.fifadrawer.entities.holders.ParticipantsHolder;
import com.prondzyn.fifadrawer.lang.ApplicationException;
import com.prondzyn.fifadrawer.lang.LoadingException;
import com.prondzyn.fifadrawer.lang.ParticipantsFileException;
import com.prondzyn.fifadrawer.utils.BooleanUtils;
import com.prondzyn.fifadrawer.utils.IOUtils;
import com.prondzyn.fifadrawer.utils.StringUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ParticipantsLoader {

  private final Properties properties;

  public ParticipantsLoader(Properties properties) {
    this.properties = properties;
  }

  public ParticipantsHolder load() {

    ParticipantsHolder loaded = new ParticipantsHolder();
    FileInputStream fis = null;
    InputStreamReader ioReader = null;
    BufferedReader reader = null;
    File file = properties.getParticipantsFile();

    try {

      fis = new FileInputStream(file);
      ioReader = new InputStreamReader(fis, DEFAULT_CHARSET);
      reader = new BufferedReader(ioReader);
      int i = 0;
      String line;

      while ((line = reader.readLine()) != null) {

        i += 1;

        List<String> splitted = StringUtils.split(line);

        if (splitted.size() != 3) {
          throw new ParticipantsFileException("Incorrect columns number in line #" + i + " in '" + file + "'.");
        }

        String name = splitted.get(0);
        boolean active = BooleanUtils.parse(splitted.get(1));
        String email = splitted.get(2);

        loaded.add(new Participant(name, active, email));

      }

    } catch (FileNotFoundException | UnsupportedEncodingException ex) {
      throw new ApplicationException(ex);
    } catch (IOException ex) {
      throw new LoadingException("There was a problem with reading file '" + file + "'.", ex);
    } finally {
      IOUtils.closeQuietly(fis);
      IOUtils.closeQuietly(ioReader);
      IOUtils.closeQuietly(reader);
    }

    validate(loaded);

    return loaded;
  }

  private void validate(ParticipantsHolder participants) {
    if (participants.isEmpty()) {
      throw new ParticipantsFileException("Participants file is empty.");
    }
  }
}
