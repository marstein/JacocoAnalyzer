package io.redlock;

import static io.redlock.SpringBootJpaApplication.getCommandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringCommandlineApp implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(SpringCommandlineApp.class);

  public static void mainFunction(String[] args) {
    log.info("STARTING THE APPLICATION");
    SpringApplication.run(SpringCommandlineApp.class, args);
    log.info("APPLICATION FINISHED");
  }

  @Autowired FileUploaderBean fileUploaderBean;

  @Override public void run(String... args) {
    log.info("EXECUTING : command line runner");
    CommandLine cmd = getCommandline(args);
    String filePath = cmd.getOptionValue("file");
    String coverageRunName = cmd.getOptionValue("runName");
    if (coverageRunName == null) {
      coverageRunName = (new Date()).toString();
    }

    try {
      File jacocoXmlFileToUpload = new File(filePath);
      log.info("importing file {}", jacocoXmlFileToUpload);
      fileUploaderBean.readAndUploadFile(jacocoXmlFileToUpload, coverageRunName);
    } catch (FileNotFoundException | XMLStreamException e) {
      log.error("could not open file {}", e.getMessage());
    }
    System.exit(0);
  }
}
