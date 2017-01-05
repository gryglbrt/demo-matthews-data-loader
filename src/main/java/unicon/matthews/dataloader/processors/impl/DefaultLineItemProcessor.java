package unicon.matthews.dataloader.processors.impl;

import java.util.Collection;

import unicon.matthews.dataloader.MatthewsClient;
import unicon.matthews.dataloader.processors.LineItemProcessor;
import unicon.matthews.oneroster.LineItem;

public class DefaultLineItemProcessor implements LineItemProcessor {

  protected MatthewsClient matthewsClient;

  public DefaultLineItemProcessor(MatthewsClient matthewsClient) {
    this.matthewsClient = matthewsClient;
  }

  @Override
  public void post(LineItem lineItem) {
    this.matthewsClient.postLineItem(lineItem);
  }

  @Override
  public void post(Collection<LineItem> lineItems) {
    for (LineItem li : lineItems) {
      post(li);
    }

  }

}
