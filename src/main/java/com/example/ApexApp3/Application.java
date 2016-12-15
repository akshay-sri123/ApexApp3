/**
 * Put your copyright and license info here.
 */
package com.example.ApexApp3;

import com.datatorrent.api.DAG;
import com.datatorrent.api.StreamingApplication;
import com.datatorrent.api.annotation.ApplicationAnnotation;
import com.datatorrent.lib.appdata.schemas.SchemaUtils;
import com.datatorrent.lib.io.ConsoleOutputOperator;
import org.apache.hadoop.conf.Configuration;

import java.util.List;

@ApplicationAnnotation(name="MyFirstApplication")
public class Application implements StreamingApplication
{

    public static final String APP_NAME = "ApexApp2";
    public static final String EVENT_SCHEMA = "adsGenericEventSchema.json";

    public String appName = APP_NAME;
    public String eventSchemaLocation = EVENT_SCHEMA;
    public List<Object> advertisers;
    public InputGenerator<AdInfo> inputOperator;

  @Override
  public void populateDAG(DAG dag, Configuration conf)
  {
      String eventSchema = SchemaUtils.jarResourceFileToString(eventSchemaLocation);

      if (inputOperator == null) {
          InputItemGenerator input = dag.addOperator("InputGenerator", InputItemGenerator.class);
          input.advertiserName = advertisers;
          input.setEventSchemaJSON(eventSchema);
          inputOperator = input;
      } else {
          dag.addOperator("InputGenerator", inputOperator);
      }

      ConsoleOutputOperator consoleOutputOperator = dag.addOperator("ConsoleOutputOperator", new ConsoleOutputOperator());

      dag.addStream("InputOperator", inputOperator.getOutputPort(), consoleOutputOperator.input);
  }
}
