package unicon.matthews.dataloader.processors.impl;

import java.util.Collection;

import unicon.matthews.caliper.Event;
import unicon.matthews.dataloader.MatthewsClient;
import unicon.matthews.dataloader.processors.EventProcessor;

public class DefaultEventProcessor implements EventProcessor {

  protected MatthewsClient matthewsClient;
  protected String defaultSensorName = "Unknown";

  public DefaultEventProcessor(MatthewsClient matthewsClient) {
    this.matthewsClient = matthewsClient;
  }

  @Override
  public void post(Event event) {
    matthewsClient.postEvent(event, defaultSensorName);

  }

  @Override
  public void post(Collection<Event> events) {
    matthewsClient.postEvents(events, defaultSensorName);
  }

}
