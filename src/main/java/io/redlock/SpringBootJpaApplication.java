package io.redlock;

import static java.lang.System.exit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootJpaApplication {
  static private final Logger log = LoggerFactory.getLogger("spring boot");

  public static void main(String[] args) {
    log.info("Application started with option names : {}", Arrays.toString(args));
    CommandLine cmd = getCommandline(args);
    boolean startWebServer = !(cmd.getOptionValue("webserver") == null);
    String filePath = cmd.getOptionValue("file");
    if (filePath != null) {
      SpringCommandlineApp.mainFunction(args);
    }

    if (startWebServer) {
      SpringApplication.run(SpringBootJpaApplication.class, args);
    }
  }

  public static CommandLine getCommandline(String[] args) {
    Options options = new Options();
    Option webServerFlag = new Option("w", "webserver", false, "launch web app");
    webServerFlag.setRequired(false);
    options.addOption(webServerFlag);
    Option fileName = new Option("f", "file", true, "jacoco xml file");
    fileName.setRequired(false);
    options.addOption(fileName);
    Option coverageRunName = new Option("r", "runName", true, "sprint or run name");
    options.addOption(coverageRunName);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    try {
      return parser.parse(options, args);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("Jacoco Analyzer", options);
      System.exit(1);
    }
    return null;
  }

  @Bean ApplicationRunner init(CoverageRepository repository) {
    log.info("starting web server");
    return args -> {
      repository.findAll().forEach(System.out::println);
    };
  }

  @Autowired FileUploaderBean fileUploaderBean;
}
