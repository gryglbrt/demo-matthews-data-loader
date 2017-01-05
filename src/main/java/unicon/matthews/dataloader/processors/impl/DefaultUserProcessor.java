package unicon.matthews.dataloader.processors.impl;

import java.util.Collection;

import unicon.matthews.dataloader.MatthewsClient;
import unicon.matthews.dataloader.processors.UserProcessor;
import unicon.matthews.entity.UserMapping;
import unicon.matthews.oneroster.User;

public class DefaultUserProcessor implements UserProcessor {

  protected MatthewsClient matthewsClient;

  public DefaultUserProcessor(MatthewsClient matthewsClient) {
    this.matthewsClient = matthewsClient;
  }

  @Override
  public void post(User user) {
    this.matthewsClient.postUser(user);
  }

  @Override
  public void post(Collection<User> users) {
    for (User user : users) {
      post(user);
    }
  }

  @Override
  public void post(UserMapping userMapping) {
    // TODO Auto-generated method stub

  }

}
